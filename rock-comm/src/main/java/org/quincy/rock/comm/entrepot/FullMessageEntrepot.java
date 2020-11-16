package org.quincy.rock.comm.entrepot;

import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.concurrent.ProcessService;

/**
 * <b>支持分块报文全双工传输的报文中转驿站。</b>
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
public abstract class FullMessageEntrepot extends DelayMessageEntrepot {
	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cachePool 报文缓存池
	 */
	public FullMessageEntrepot(OwnerCachePool<?> cachePool) {
		super(cachePool);
	}

	/** 
	 * sendContext。
	 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot#sendContext(org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send, int)
	 */
	@Override
	protected Map<String, Object> sendContext(MessageJoiner4Send joiner, int index) {
		Map<String, Object> ctx = joiner.sendContext(index);
		if (joiner.hasConsumer() && (joiner.chunkCount() - index) == 1 && joiner.repeat(index) == 0) {
			//是最后一块
			ctx.put(CommUtils.COMM_ASYNC_CALLBACK_KEY, joiner.consumer());
		}
		return ctx;
	}

	/** 
	 * firstSendChunkMessage。
	 * @see org.quincy.rock.comm.entrepot.DelayMessageEntrepot#firstSendChunkMessage(java.lang.String, org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send)
	 */
	@Override
	protected void firstSendChunkMessage(String termKey, MessageJoiner4Send joiner) {
		for (int i = 0, l = joiner.chunkCount(); i < l; i++) {
			ProcessService ps = i == 0 ? chunkSendService() : delayChunkSendService();
			ps.put(termKey, proxy(joiner, i));
		}
	}

	/** 
	 * receivedChunkMessage。
	 * @see org.quincy.rock.comm.entrepot.DelayMessageEntrepot#receivedChunkMessage(org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Receive, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected int receivedChunkMessage(MessageJoiner4Receive joiner, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx, Integer pkgTotal, Integer pkgIndex) {
		if (joiner == null) {
			//无分包,继续走简单流程
			return -1;
		} else if (joiner.isDone()) {
			//丢掉多余的数据块
			return -2;
		} else {
			//有分包
			if (pkgTotal == null)
				pkgTotal = joiner.chunkCount();
			if (pkgIndex == null)
				pkgIndex = joiner.current();
			//
			if (pkgTotal != joiner.chunkCount()) {
				return -2; //总包数不一致，丢掉
			} else if (pkgIndex < 0 || pkgIndex >= pkgTotal) {
				return -2; //包索引错误，丢掉
			} else {
				joiner.current(pkgIndex + 1);
				return pkgIndex;
			}
		}
	}

	/** 
	 * receivedResponeMessage。
	 * @see org.quincy.rock.comm.entrepot.DelayMessageEntrepot#receivedResponeMessage(org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected int receivedResponeMessage(MessageJoiner4Send joiner, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx, Integer pkgTotal, Integer pkgIndex) {
		if (joiner.isDone())
			return -2; //丢弃该报文
		else {
			if (pkgTotal == null)
				pkgTotal = joiner.chunkCount();
			if (pkgIndex == null)
				pkgIndex = joiner.current();
			if (pkgTotal != joiner.chunkCount() || !joiner.valid(pkgIndex))
				return -2; //丢弃该报文
			else {
				joiner.success(pkgIndex);
				if (joiner.isDone())
					return -1; //需要进入处理器
				else {
					return -2; //丢弃该报文
				}
			}
		}
	}
}
