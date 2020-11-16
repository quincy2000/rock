package org.quincy.rock.comm.entrepot;

import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.concurrent.DefaultDelayQueueProcessService;
import org.quincy.rock.core.concurrent.ProcessService;
import org.quincy.rock.core.concurrent.Processor;
import org.quincy.rock.core.function.Consumer;

/**
 * <b>支持延迟发送分块报文的驿站。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 支持分块报文的拦截。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月7日 下午5:10:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class DelayMessageEntrepot extends SplitMessageEntrepot {
	/**
	 * 分块报文的延迟发送服务。
	 */
	private DefaultDelayQueueProcessService<Object, MessageJoiner4Send> delayChunkSendService;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cachePool 报文缓存池
	 */
	public DelayMessageEntrepot(OwnerCachePool<?> cachePool) {
		super(cachePool);
	}

	/**
	 * <b>设置分块报文的延迟发送服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delayChunkSendService 分块报文的延迟发送服务
	 */
	public void setDelayChunkSendService(DefaultDelayQueueProcessService<?, ?> delayChunkSendService) {
		if (this.delayChunkSendService != delayChunkSendService) {
			if (delayChunkSendService != null)
				delayChunkSendService.setProcessor((Processor) this);
			if (this.delayChunkSendService != null)
				this.delayChunkSendService.setProcessor(null);
			//set
			this.delayChunkSendService = (DefaultDelayQueueProcessService) delayChunkSendService;
		}
	}

	/** 
	 * addToSentMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#addToSentMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void addToSentMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		Consumer<Boolean> consumer = (Consumer) ctx.get(CommUtils.COMM_ASYNC_CALLBACK_KEY);
		if (this.canPutMessageIntoCP(terminalId, msgId, cmdCode, content, ctx)) {
			MessageJoiner4Send joiner = this.createMsgSentDoneJoiner(terminalId, msgId, cmdCode, content, ctx,
					consumer);
			String termKey = nonNullkey(terminalId);
			String cacheKey4Send = cacheKey(msgId, cmdCode, true);
			this.putJoinerToCP(termKey, cacheKey4Send, joiner);
			this.firstSendChunkMessage(termKey, joiner);
		} else {
			//不需要进缓存池
			Consumer<Boolean> myConsumer = createMsgSentDoneConsumer(terminalId, msgId, cmdCode, content, ctx,
					consumer);
			ctx.put(CommUtils.COMM_ASYNC_CALLBACK_KEY, myConsumer);
			//触发事件,发送指令
			this.fireToSentMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
		}
	}

	/** 
	 * addArrivedMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#addArrivedMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void addArrivedMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		String termKey = nonNullkey(terminalId);
		String cacheKey4Send = cacheKey(msgId, cmdCode, true);
		boolean continuing = true; //是否继续处理
		MessageJoiner4Send joiner4Send = this.getJoinerFromCP(termKey, cacheKey4Send);
		if (joiner4Send != null) {
			//是已发送分块报文的响应报文			
			int nextIndex = this.receivedResponeMessage(joiner4Send, msgId, cmdCode, content, ctx,
					(Integer) ctx.get(CommUtils.COMM_MSG_PKG_TOTAL_KEY),
					(Integer) ctx.get(CommUtils.COMM_MSG_PKG_INDEX_KEY));
			if (joiner4Send.valid(nextIndex)) {
				//发送下一块
				this.delayChunkSendService().put(termKey, proxy(joiner4Send, nextIndex));
				continuing = false; //不需要继续处理了
			} else {
				continuing = nextIndex == -1;
			}
		}
		//继续处理
		if (continuing) {
			Integer pkgTotal = (Integer) ctx.get(CommUtils.COMM_MSG_PKG_TOTAL_KEY);
			Integer pkgIndex = (Integer) ctx.get(CommUtils.COMM_MSG_PKG_INDEX_KEY);
			boolean isPage = pkgTotal != null && pkgTotal > 1; //是否有分包
			String cacheKey4Receive = cacheKey(msgId, cmdCode, false);
			MessageJoiner4Receive joiner = this.getJoinerFromCP(termKey, cacheKey4Receive);
			if (joiner == null && isPage) {
				joiner = createMsgArriveDoneJoiner(terminalId, msgId, cmdCode, ctx, pkgTotal, null);
				this.putJoinerToCP(termKey, cacheKey4Receive, joiner);
			}
			//拦截
			int currIndex = receivedChunkMessage(joiner, msgId, cmdCode, content, ctx, pkgTotal, pkgIndex);
			if (joiner != null && currIndex > -1) {
				//分块报文
				fireArrivedMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
				joiner.append(currIndex, content);
			} else if (currIndex == -1) {
				//简单报文
				fireArrivedMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
				fireArrivedMessageAddDoneEvent(terminalId, msgId, cmdCode, content, ctx);
			}
		}
	}

	/**
	 * <b>返回分块报文的延迟发送服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果没有配置延迟发送服务则返回默认发送服务。
	 * @return 分块报文的延迟发送服务
	 */
	protected ProcessService<Object, MessageJoiner4Send> delayChunkSendService() {
		return delayChunkSendService == null ? this.chunkSendService() : delayChunkSendService;
	}

	/**
	 * <b>是否需要将发送报文放入缓存池。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端id
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 正文原始内容
	 * @param ctx 报文上下文数据
	 * @return 是否放入缓存池
	 */
	protected boolean canPutMessageIntoCP(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		MessageSplitter splitter = this.getMessageSplitterFactory()
				.getMessageSplitter((String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY));
		return splitter.canSplit(cmdCode, content);
	}

	/**
	 * <b>接收到分块报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joiner MessageJoiner4Receive,无分包情况下为null
	 * @param msgId 消息id
	 * @param cmdCode 功能码
	 * @param content 正文原始内容
	 * @param ctx 报文上下文对象
	 * @param pkgTotal 总包数
	 * @param pkgIndex 当前包索引
	 * @return 返回合法索引(joiner非空且非越界)代表确认接收到的块索引,返回-1代表继续处理该报文,返回-2代表该报文已处理完毕,不需要后续处理。
	 */
	protected abstract int receivedChunkMessage(MessageJoiner4Receive joiner, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx, Integer pkgTotal, Integer pkgIndex);

	/**
	 * <b>接收到分块响应报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joiner MessageJoiner4Send，不能为空
	 * @param msgId 消息id
	 * @param cmdCode 功能码
	 * @param content 正文原始内容
	 * @param ctx 报文上下文对象
	 * @param pkgTotal 总包数
	 * @param pkgIndex 当前包索引
	 * @return 返回合法索引(非越界)代表继续发送的块索引,返回-1代表继续处理该响应报文,返回-2代表该响应报文已处理完毕,不需要后续处理。
	 */
	protected abstract int receivedResponeMessage(MessageJoiner4Send joiner, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx, Integer pkgTotal, Integer pkgIndex);

	/**
	 * <b>首次发送数据块报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termKey 终端key
	 * @param joiner MessageJoiner4Send
	 */
	protected abstract void firstSendChunkMessage(String termKey, MessageJoiner4Send joiner);
}
