package org.quincy.rock.comm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.quincy.rock.comm.communicate.IChannel;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.bean.CascadeMap;
import org.quincy.rock.core.concurrent.Waiter;
import org.quincy.rock.core.exception.NotUniqueException;
import org.quincy.rock.core.exception.TimeoutException;
import org.quincy.rock.core.function.Consumer;

/**
 * <b>RpcMessageService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * checkRpcResponeFlag说明:
 * 指的是检查通道的request属性，如果UChannel.isRequest是明确被设置的，则检查该标志可以更准确的判断报文是否是响应报文。
 * channel.request属性一般是在报文头解析器中进行设置。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月28日 上午11:50:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RpcMessageService<K, UChannel> extends DefaultMessageService<K, UChannel>
		implements RpcMessageSender<K, UChannel> {
	/**
	 * rpc调用超时。
	 */
	private int rpcTimeOut = CommUtils.DEFAULT_RPC_INVOKE_TIMEOUT;
	/**
	 * 是否检查RPC调用返回响应标志。
	 */
	private boolean checkRpcResponeFlag;
	/**
	 * 使用功能代码代替流水号作为对键。
	 */
	private boolean useFuncCode;

	/**
	 * rpcMap。
	 */
	private CascadeMap<UChannel, Object, Waiter<?, CommunicateException>> rpcMap = new CascadeMap<>(new HashMap<>(),
			true);

	/**
	 * <b>获得rpc调用超时。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return rpc调用超时
	 */
	public int getRpcTimeOut() {
		return rpcTimeOut;
	}

	/**
	 * <b>设置rpc调用超时。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param rpcTimeOut rpc调用超时
	 */
	public void setRpcTimeOut(int rpcTimeOut) {
		this.rpcTimeOut = rpcTimeOut;
	}

	/**
	 * <b>是否检查RPC调用返回响应标志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 当客户端和服务器流水号存在重复的情况时需要设置该标志。
	 * @return 是否检查RPC调用返回响应标志
	 */
	public boolean isCheckRpcResponeFlag() {
		return checkRpcResponeFlag;
	}

	/**
	 * <b>设置是否检查RPC调用返回响应标志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 当客户端和服务器流水号存在重复的情况时需要设置该标志。
	 * @param checkRpcResponeFlag 是否检查RPC调用返回响应标志
	 */
	public void setCheckRpcResponeFlag(boolean checkRpcResponeFlag) {
		this.checkRpcResponeFlag = checkRpcResponeFlag;
	}

	/**
	 * <b>使用功能代码代替流水号作为对键。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 使用功能代码代替流水号作为对键
	 */
	public boolean isUseFuncCode() {
		return useFuncCode;
	}

	/**
	 * <b>使用功能代码代替流水号作为对键。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param useFuncCode 使用功能代码代替流水号作为对键
	 */
	public void setUseFuncCode(boolean useFuncCode) {
		this.useFuncCode = useFuncCode;
	}

	/** 
	 * channelClosed。
	 * @see org.quincy.rock.comm.DefaultMessageService#channelClosed(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	protected void channelClosed(UChannel channel, Throwable e) {
		Map<Object, Waiter<?, CommunicateException>> map = rpcMap.get(channel);
		if (map != null) {
			for (Waiter waiter : map.values()) {
				waiter.lock();
				try {
					waiter.failed(new CommunicateException("Channel closed!"));
				} catch (Exception ex) {
					//不影响完成循环
				} finally {
					waiter.unlock();
				}
			}
			rpcMap.remove(channel);
			map.clear();
		}
		super.channelClosed(channel, e);
	}

	/** 
	 * messageArrived。
	 * @see org.quincy.rock.comm.DefaultMessageService#messageArrived(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	protected void messageArrived(UChannel channel, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx) {
		boolean checkRpc = !checkRpcResponeFlag || !(channel instanceof IChannel) || !((IChannel) channel).isRequest();
		if (checkRpc) {
			Object key = isUseFuncCode() ? functionCode : msgId;
			//检查是否是PRC返回值
			Map<Object, Waiter<?, CommunicateException>> map = rpcMap.get(channel);
			if (map != null) {
				Waiter waiter = map.get(key);
				if (waiter != null) {
					waiter.lock();
					try {
						waiter.succeed(content);
						rpcMap.removeValue(channel, key);
					} finally {
						waiter.unlock();
					}
					ctx.put(CommUtils.COMM_MSG_PROCESS_DONE_KEY, Boolean.TRUE);
				}
			}
		}
		super.messageArrived(channel, terminalId, msgId, functionCode, content, ctx);
	}

	/** 
	 * sendRpcMessage。
	 * @see org.quincy.rock.comm.RpcMessageSender#sendRpcMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> T sendRpcMessage(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment) throws CommunicateException {
		UChannel channel = findSendChannel(terminalId);
		checkSendChannel(channel);
		return sendRpcMessage(channel, getTerminalId(channel, terminalId), msgId, functionCode, content, attachment,
				null, null);
	}

	/** 
	 * sendRpcMessage。
	 * @see org.quincy.rock.comm.RpcMessageSender#sendRpcMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, org.quincy.rock.core.function.Consumer, org.quincy.rock.core.function.Consumer)
	 */
	@Override
	public void sendRpcMessage(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, Consumer<?> succeed, Consumer<Exception> failed)
			throws CommunicateException {
		UChannel channel = findSendChannel(terminalId);
		checkSendChannel(channel);
		sendRpcMessage(channel, getTerminalId(channel, terminalId), msgId, functionCode, content, attachment, succeed,
				failed);
	}

	/** 
	 * sendRpcMessageByChannel。
	 * @see org.quincy.rock.comm.RpcMessageSender#sendRpcMessageByChannel(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, org.quincy.rock.core.function.Consumer, org.quincy.rock.core.function.Consumer)
	 */
	@Override
	public void sendRpcMessageByChannel(UChannel channel, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, Consumer<?> succeed, Consumer<Exception> failed)
			throws CommunicateException {
		channel = getSendChannel(channel);
		checkSendChannel(channel);
		sendRpcMessage(channel, getTerminalId(channel, null), msgId, functionCode, content, attachment, succeed,
				failed);
	}

	/** 
	 * sendRpcMessageByChannel。
	 * @see org.quincy.rock.comm.RpcMessageSender#sendRpcMessageByChannel(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> T sendRpcMessageByChannel(UChannel channel, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment) throws CommunicateException {
		channel = getSendChannel(channel);
		checkSendChannel(channel);
		return sendRpcMessage(channel, getTerminalId(channel, null), msgId, functionCode, content, attachment, null,
				null);
	}

	/**
	 * <b>发送rpc消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道，必须是一个可用的精确发送通道（包含原始通道）
	 * @param terminalId 终端唯一标识
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @param succeed 成功回调Consumer
	 * @param failed 失败回调Consumer
	 * @param 返回值
	 * @exception CommunicateException
	 */
	protected <T> T sendRpcMessage(UChannel channel, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, Consumer<T> succeed, Consumer<Exception> failed)
			throws CommunicateException {
		Object key = isUseFuncCode() ? functionCode : msgId;
		if (rpcMap.containsKey(channel, key)) {
			throw new NotUniqueException("Rpc key:" + key);
		}
		//是否是同步发送，如果是同步发送则一直等待返回值，异步则使用回调
		Waiter<T, CommunicateException> waiter = new Waiter<>(succeed == null);
		waiter.setSucceed(succeed);
		waiter.setFailed(failed);
		//
		waiter.lock();
		try {
			rpcMap.put(channel, key, waiter);
			final AtomicBoolean sendOk = new AtomicBoolean(false);
			sendMessage(channel, terminalId, msgId, functionCode, content, attachment, false, new Consumer<Boolean>() {

				@Override
				public void call(Boolean ok) {
					sendOk.set(ok);
				}
			});
			if (sendOk.get() == false)
				throw new CommunicateException("Sending message failed!");
			if (!waiter.await(rpcTimeOut, TimeUnit.SECONDS))
				throw new TimeoutException("Timeout seconds:" + rpcTimeOut);
		} catch (Exception e) {
			rpcMap.removeValue(channel, key);
			CommunicateException ex = (e instanceof CommunicateException) ? (CommunicateException) e
					: new CommunicateException(e.getMessage(), e);
			waiter.failed(ex);
		} finally {
			waiter.unlock();
		}
		if (waiter.isSync()) {
			CommunicateException ex = waiter.getException();
			if (ex != null)
				throw ex;
			return waiter.getResult();
		} else
			return null;
	}
}
