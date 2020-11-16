package org.quincy.rock.comm.entrepot;

import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.cache.DefaultOwnerCachePool;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.function.Consumer;

/**
 * <b>缺省的报文中转驿站。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 支持报文分包，但不支持分包拦截、分包响应和重发。
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DefaultMessageEntrepot extends SplitMessageEntrepot {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DefaultMessageEntrepot() {
		this(new DefaultOwnerCachePool<>());
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cachePool 驿站报文缓存池
	 */
	public DefaultMessageEntrepot(OwnerCachePool<?> cachePool) {
		super(cachePool);
	}

	/** 
	 * addToSentMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#addToSentMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void addToSentMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		Consumer<Boolean> consumer = (Consumer) ctx.get(CommUtils.COMM_ASYNC_CALLBACK_KEY);
		MessageSplitter splitter = getMessageSplitterFactory()
				.getMessageSplitter((String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY));
		if (splitter.canSplit(cmdCode, content)) {
			//需要拆分
			MessageJoiner4Send joiner = this.createMsgSentDoneJoiner(terminalId, msgId, cmdCode, content, ctx,
					consumer);
			//立即把所有数据块按顺序发送出去(不需要缓存)
			Object termkey = nonNullkey(terminalId);
			for (int i = 0, l = joiner.chunkCount(); i < l; i++) {
				//委托发送服务进行发送
				this.chunkSendService().put(termkey, proxy(joiner, i));
			}
		} else {
			//不需要拆分
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
		Integer pkgTotal = (Integer) ctx.get(CommUtils.COMM_MSG_PKG_TOTAL_KEY);
		Integer pkgIndex = (Integer) ctx.get(CommUtils.COMM_MSG_PKG_INDEX_KEY);
		boolean isPage = pkgTotal != null && pkgTotal > 1; //是否有分包
		if (isPage) {
			String termKey = nonNullkey(terminalId);
			String cacheKey4Receive = cacheKey(msgId, cmdCode, false);
			MessageJoiner4Receive joiner = this.getJoinerFromCP(termKey, cacheKey4Receive);
			if (joiner == null) {
				joiner = createMsgArriveDoneJoiner(terminalId, msgId, cmdCode, ctx, pkgTotal, null);
				this.putJoinerToCP(termKey, cacheKey4Receive, joiner);
			}
			fireArrivedMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
			joiner.append(pkgIndex, content);
		} else {
			//简单报文
			fireArrivedMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
			fireArrivedMessageAddDoneEvent(terminalId, msgId, cmdCode, content, ctx);
		}
	}

	/** 
	 * sendContext。
	 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot#sendContext(org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send, int)
	 */
	@Override
	protected Map<String, Object> sendContext(final MessageJoiner4Send joiner, final int index) {
		Map<String, Object> ctx = joiner.sendContext(index);
		ctx.put(CommUtils.COMM_ASYNC_CALLBACK_KEY, new Consumer<Boolean>() {
			private MessageJoiner4Send myJoiner = joiner;
			private int myIndex = index;

			@Override
			public void call(Boolean ok) {
				if (Boolean.TRUE.equals(ok))
					myJoiner.success(myIndex);
				else
					myJoiner.failure(myIndex);
			}
		});
		return ctx;
	}

}
