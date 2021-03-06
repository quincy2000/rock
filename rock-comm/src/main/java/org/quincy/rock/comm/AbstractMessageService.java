package org.quincy.rock.comm;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.quincy.rock.comm.cmd.CommandStation;
import org.quincy.rock.comm.cmd.DefaultCommandStation;
import org.quincy.rock.comm.communicate.Adviser;
import org.quincy.rock.comm.communicate.IChannel;
import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalChannelMapping;
import org.quincy.rock.core.function.Consumer;

/**
 * <b>报文服务抽象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月9日 下午12:28:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractMessageService<K, UChannel> implements MessageService<K, UChannel> {
	/**
	 * listeners。
	 */
	private List<MessageListener<K>> listeners = new Vector<>();

	/**
	 * 指令站台。
	 */
	private CommandStation<?> commandStation = new DefaultCommandStation();

	/**
	 * 通道终端映射器。
	 */
	private TerminalChannelMapping<UChannel> terminalChannelMapping;

	/** 
	 * addMessageListener。
	 * @see org.quincy.rock.comm.MessageService#addMessageListener(org.quincy.rock.comm.MessageListener)
	 */
	@Override
	public void addMessageListener(MessageListener<K> listener) {
		listeners.add(listener);
	}

	/** 
	 * removeMessageListener。
	 * @see org.quincy.rock.comm.MessageService#removeMessageListener(org.quincy.rock.comm.MessageListener)
	 */
	@Override
	public void removeMessageListener(MessageListener<K> listener) {
		listeners.remove(listener);
	}

	/** 
	 * removeAllMessageListener。
	 * @see org.quincy.rock.comm.MessageService#removeAllMessageListener()
	 */
	@Override
	public void removeAllMessageListener() {
		listeners.clear();
	}

	/** 
	 * hasMessageListener。
	 * @see org.quincy.rock.comm.MessageService#hasMessageListener(org.quincy.rock.comm.MessageListener)
	 */
	@Override
	public boolean hasMessageListener(MessageListener<K> listener) {
		return listeners.contains(listener);
	}

	/**
	 * <b>触发终端上线事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 */
	protected void fireTerminalOnlineEvent(Object terminalId) {
		for (MessageListener<K> listener : listeners) {
			listener.terminalOnline(this, terminalId);
		}
	}

	/**
	 * <b>触发终端下线事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 */
	protected void fireTerminalOfflineEvent(Object terminalId) {
		for (MessageListener<K> listener : listeners) {
			listener.terminalOffline(this, terminalId);
		}
	}

	/**
	 * <b>触发报文消息到达事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	protected void fireMessageArrivedEvent(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx) {
		for (MessageListener<K> listener : listeners) {
			listener.messageArrived(this, terminalId, msgId, functionCode, content, ctx);
		}
	}

	/**
	 * <b>触发报文出发事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 * @param success 是否发送成功
	 */
	protected void fireMessageSendedEvent(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx, boolean success) {
		for (MessageListener<K> listener : listeners) {
			listener.messageSended(this, terminalId, msgId, functionCode, content, ctx, success);
		}
	}

	/**
	 * <b>触发报文头解析异常事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 报文数据
	 * @param ctx 报文上下文数据
	 * @param e 异常
	 */
	protected void fireMessageHeadParserException(Object data, Map<String, Object> ctx, Throwable e) {
		for (MessageListener<K> listener : listeners) {
			listener.messageHeadParserException(this, data, ctx, e);
		}
	}

	/**
	 * <b>触发合法性检查异常事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文原始数据
	 * @param ctx 报文上下文数据
	 */
	protected void fireCheckLegalityException(Object msgId, K functionCode, Object content, Map<String, Object> ctx) {
		for (MessageListener<K> listener : listeners) {
			listener.checkLegalityException(this, msgId, functionCode, content, ctx);
		}
	}

	/**
	 * <b>触发报文正文解析异常事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 * @param e 异常
	 */
	protected void fireMessageParserException(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx, Throwable e) {
		for (MessageListener<K> listener : listeners) {
			listener.messageParserException(this, terminalId, msgId, functionCode, content, ctx, e);
		}
	}

	/**
	 * <b>获得指令站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指令站台
	 */
	public CommandStation<?> getCommandStation() {
		return commandStation;
	}

	/**
	 * <b>设置指令站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param commandStation 指令站台
	 */
	public void setCommandStation(CommandStation<?> commandStation) {
		this.commandStation = commandStation;
	}

	/**
	 * 获得通道终端映射器
	 * @return 通道终端映射器
	 */
	public TerminalChannelMapping<UChannel> getTerminalChannelMapping() {
		return terminalChannelMapping;
	}

	/**
	 * 设置
	 * @param terminalChannelMapping 通道终端映射器
	 */
	public void setTerminalChannelMapping(TerminalChannelMapping<UChannel> terminalChannelMapping) {
		this.terminalChannelMapping = terminalChannelMapping;
	}

	/** 
	 * commandStation。
	 * @see org.quincy.rock.comm.MessageSender#commandStation()
	 */
	@Override
	public <T> CommandStation<T> commandStation() {
		return (CommandStation<T>) commandStation;
	}

	/** 
	 * isOnline。
	 * @see org.quincy.rock.comm.MessageSender#isOnline(java.lang.Object)
	 */
	@Override
	public boolean isOnline(Object terminalId) {
		return getTerminalChannelMapping().hasTerminal(terminalId);
	}

	/** 
	 * sendMessage。
	 * @see org.quincy.rock.comm.MessageSender#sendMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, boolean, java.util.function.Consumer)
	 */
	@Override
	public final void sendMessage(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, boolean async, Consumer<Boolean> consumer) {
		UChannel channel = findSendChannel(terminalId);
		this.checkSendChannel(channel);
		sendMessage(channel, getTerminalId(channel, terminalId), msgId, functionCode, content, attachment, async,
				consumer);
	}

	/** 
	 * sendMessageByChannel。
	 * @see org.quincy.rock.comm.MessageService#sendMessageByChannel(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, boolean, java.util.function.Consumer)
	 */
	@Override
	public final void sendMessageByChannel(UChannel ch, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, boolean async, Consumer<Boolean> consumer) {
		UChannel channel = getSendChannel(ch);
		this.checkSendChannel(channel);
		sendMessage(channel, getTerminalId(channel, null), msgId, functionCode, content, attachment, async, consumer);
	}

	/**
	 * <b>发送消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 终端和通道必须一致。
	 * @param channel 通道，必须是一个有效的发送通道（包含原始通道）
	 * @param terminalId 终端唯一标识,不允许模式终端,但允许为null。
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @param async 是否是异步发送，指示是立即返回还是发送完成后再返回
	 * @param consumer 回调Consumer,指示发送是否成功,可以为null
	 */
	protected abstract void sendMessage(UChannel channel, Object terminalId, Object msgId, K functionCode,
			Object content, Map<String, Object> attachment, boolean async, Consumer<Boolean> consumer);

	/**
	 * <b>为终端查找一个可用的有效发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一标识
	 * @return 返回一个可用的有效发送通道,如果没有则返回null
	 */
	protected UChannel findSendChannel(Object terminalId) {
		UChannel channel = getTerminalChannelMapping().findChannel(terminalId);
		if (channel instanceof IChannel) {
			channel = ((IChannel) channel).newSendChannel(terminalId instanceof Adviser ? (Adviser) terminalId : null);
		}
		return channel;
	}

	/**
	 * <b>获得一个可用的有效发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch 通道
	 * @return 返回一个可用的有效发送通道,如果没有则返回null
	 */
	protected UChannel getSendChannel(UChannel ch) {
		Object terminalId = getTerminalChannelMapping().findTerminal(ch);
		if (terminalId != null) {
			UChannel channel = getTerminalChannelMapping().findChannel(terminalId);
			if (channel instanceof IChannel) {
				channel = ((IChannel) channel).newSendChannel(ch instanceof Adviser ? (Adviser) ch : null);
			}
			return channel;
		} else
			return null;
	}

	/**
	 * <b>根据通道获得终端标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param defTerm 缺省终端标识
	 * @return 终端标识
	 */
	protected Object getTerminalId(UChannel channel, Object defTerm) {
		Object term = null;
		if (channel instanceof TerminalChannel) {
			term = ((TerminalChannel) channel).remote();
		} else {
			term = getTerminalChannelMapping().findTerminal(channel);
		}
		return term == null ? defTerm : term;
	}

	/**
	 * <b>检查发送通道合法性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 发送通道
	 */
	protected void checkSendChannel(UChannel channel) {
		if (channel == null)
			throw new CommunicateException("No suitable channel was found to send the message!");
		if (channel instanceof IChannel) {
			IChannel ch = (IChannel) channel;
			if (!ch.isValidChannel() || !ch.isSendChannel())
				throw new CommunicateException("This is not a valid send channel!");
		}
	}
}
