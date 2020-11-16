package org.quincy.rock.comm.entrepot;

import java.util.HashMap;
import java.util.Map;

import org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.concurrent.ProcessService;
import org.quincy.rock.core.concurrent.Processor;
import org.quincy.rock.core.exception.BlcokingException;
import org.quincy.rock.core.exception.CacheException;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.function.ChunkJoiner;
import org.quincy.rock.core.function.ChunkJoinerProxy;
import org.quincy.rock.core.function.Consumer;
import org.quincy.rock.core.function.IChunkJoiner;

/**
 * <b>支持报文分拆的报文中转站。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月8日 下午2:06:54</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SplitMessageEntrepot extends AbstractMessageEntrepot implements Processor<MessageJoiner4Send> {
	/**
	 * KEY_FOR_NULL。
	 */
	private static final String KEY_FOR_NULL = "none_id";

	/**
	 * 分块报文的直接发送服务。
	 */
	private final ProcessService<Object, MessageJoiner4Send> directChunkSendService = new ProcessService<Object, MessageJoiner4Send>() {

		@Override
		protected void handleDataClosure(DataClosure<Object, MessageJoiner4Send> dataClosure) throws BlcokingException {
			dataClosure.process();
		}

		@Override
		protected Processor<MessageJoiner4Send> getProcessor(Object key) throws NotFoundException {
			return SplitMessageEntrepot.this;
		}

		@Override
		protected void awaitTermination() throws Exception {

		}
	};

	/**
	 * 报文缓存池。
	 */
	protected final OwnerCachePool<MessageJoiner> cachePool;
	/**
	 * 报文拆分器工厂。
	 */
	private MessageSplitterFactory messageSplitterFactory;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cachePool 驿站报文缓存池
	 */
	public SplitMessageEntrepot(OwnerCachePool<?> cachePool) {
		this.cachePool = (OwnerCachePool) cachePool;
		this.directChunkSendService.start();
	}

	/**
	 * 获得报文拆分器工厂
	 * @return 报文拆分器工厂
	 */
	public MessageSplitterFactory getMessageSplitterFactory() {
		return messageSplitterFactory;
	}

	/**
	 * 设置报文拆分器工厂
	 * @param messageSplitterFactory 报文拆分器工厂
	 */
	public void setMessageSplitterFactory(MessageSplitterFactory messageSplitterFactory) {
		this.messageSplitterFactory = messageSplitterFactory;
	}

	/** 
	 * removeTerminal。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#removeTerminal(java.lang.Object)
	 */
	@Override
	public void removeTerminal(Object terminalId) {
		Object nonNullkey = nonNullkey(terminalId);
		cachePool.removeOwner(nonNullkey);
	}

	/** 
	 * process。
	 * @see org.quincy.rock.core.concurrent.Processor#process(java.lang.Object)
	 */
	@Override
	public final void process(MessageJoiner4Send joiner) {
		int currIndex = joiner.current();
		Map<String, Object> currCtx = this.sendContext(joiner, currIndex);
		Object currChunk = joiner.chunk(currIndex);
		sendChunkMessage(joiner.terminalId(), joiner.msgId(), joiner.cmdCode(), currChunk, currCtx, joiner.chunkCount(),
				currIndex);
		fireToSentMessageAddedEvent(joiner.terminalId(), joiner.msgId(), joiner.cmdCode(), currChunk, currCtx);
	}

	/**
	 * <b>返回指定索引数据块的发送上下文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joiner MessageJoiner4Send
	 * @param index 索引
	 * @return 数据块发送上下文
	 */
	protected abstract Map<String, Object> sendContext(MessageJoiner4Send joiner, int index);

	/**
	 * <b>准备发送分块报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端id
	 * @param msgId 消息id
	 * @param cmdCode 功能码
	 * @param content 正文原始内容
	 * @param ctx 报文上下文对象
	 * @param pkgTotal 总包数
	 * @param pkgIndex 当前包索引
	 */
	protected void sendChunkMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx, Integer pkgTotal, Integer pkgIndex) {

	}

	/**
	 * <b>获得报文分块发送服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该发送服务会将报文立即同步发送出去。
	 * @return 报文分块发送服务
	 */
	protected final ProcessService<Object, MessageJoiner4Send> chunkSendService() {
		return directChunkSendService;
	}

	/**
	 * <b>将MessageJoiner放入缓存池。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termKey 终端key
	 * @param cacheKey 缓存key
	 * @param joiner MessageJoiner的子类
	 * @see MessageJoiner4Send
	 * @see MessageJoiner4Receive
	 */
	protected void putJoinerToCP(String termKey, String cacheKey, MessageJoiner joiner) {
		if (!cachePool.putBufferValue(termKey, cacheKey, joiner)) {
			throw new CacheException("Cache pool overflow!");
		}
	}

	/**
	 * <b>从缓存中取出MessageJoiner。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termKey 终端key
	 * @param cacheKey 缓存key
	 * @return MessageJoiner的子类
	 * @see MessageJoiner4Send
	 * @see MessageJoiner4Receive
	 */
	protected final <T extends MessageJoiner> T getJoinerFromCP(String termKey, String cacheKey) {
		T joiner = (T) cachePool.getBufferValue(termKey, cacheKey);
		if (joiner != null) {
			joiner.updateTimestamp();
		}
		return joiner;
	}

	/**
	 * <b>检查并重发滞留报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tarryTime 滞留时间(秒)，超过滞留时间的会被重发
	 * @param maxret 最大重发次数
	 */
	protected void checkAndResendTarryMessage(int tarryTime, int maxret) {
		for (Object owner : cachePool.owners()) {
			for (Object key : cachePool.keys(owner)) {
				MessageJoiner joiner = cachePool.seekBufferValue(owner, key);
				if (joiner instanceof MessageJoiner4Send && joiner.elapsedSecond() > tarryTime && !joiner.isDone()) {
					this.resendTarryMessage((MessageJoiner4Send) joiner, maxret);
					joiner.updateTimestamp();
				}
			}
		}
	}

	/**
	 * <b>重发滞留报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joiner MessageJoiner4Send
	 * @param maxret 最大重发次数
	 */
	protected void resendTarryMessage(MessageJoiner4Send joiner, int maxret) {

	}

	/**
	 * <b>创建并初始化报文全部发送ChunkJoiner。。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param context 上下文对象
	 * @param content 报文原始正文
	 * @param consumer 报文发送完毕后的异步回调函数
	 * @return MessageJoiner4Send
	 */
	protected final MessageJoiner4Send createMsgSentDoneJoiner(Object terminalId, Object msgId, Object cmdCode,
			Object content, Map<String, Object> context, Consumer<Boolean> consumer) {
		Map<String, Object> ctx = new HashMap<>(context);
		ctx.remove(CommUtils.COMM_ASYNC_CALLBACK_KEY);
		return new MessageJoiner4SendImpl(terminalId, msgId, cmdCode, content, ctx, consumer);
	}

	/**
	 * <b>创建并初始化报文全部到达ChunkJoiner。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param context 上下文对象
	 * @param pkgTotal 总包数
	 * @param consumer 报文接收完毕后的异步回调函数
	 * @return MessageJoiner4Receive
	 */
	protected final MessageJoiner4Receive createMsgArriveDoneJoiner(Object terminalId, Object msgId, Object cmdCode,
			Map<String, Object> context, int pkgTotal, Consumer<Boolean> consumer) {
		Map<String, Object> ctx = new HashMap<>(context);
		ctx.remove(CommUtils.COMM_MSG_PKG_INDEX_KEY);
		ctx.remove(CommUtils.COMM_MSG_PKG_TOTAL_KEY);
		return new MessageJoiner4ReceiveImpl(terminalId, msgId, cmdCode, ctx, pkgTotal, consumer);
	}

	/**
	 * <b>返回终端非空键。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termId 终端标识
	 * @return Key
	 */
	protected String nonNullkey(Object termId) {
		return termId == null ? KEY_FOR_NULL : termId.toString();
	}

	/**
	 * <b>返回报文缓存key。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msgId 流水号
	 * @param cmdCode 指令码
	 * @param forSend 为缓存发送(或接收)报文生成缓存key
	 * @return 报文缓存key
	 */
	protected String cacheKey(Object msgId, Object cmdCode, boolean forSend) {
		return (forSend ? "s_" : "r_") + msgId;
	}

	/**
	 * <b>创建代理MessageJoiner4Send。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joiner MessageJoiner4Send
	 * @param current 指定当前块索引
	 * @return 代理MessageJoiner4Send
	 */
	protected final MessageJoiner4Send proxy(MessageJoiner4Send joiner, int current) {
		MessageJoiner4SendProxy proxy = new MessageJoiner4SendProxy(joiner);
		proxy.current(current);
		return proxy;
	}

	/**
	 * <b>创建接收报文的底层Joiner。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param ctx 上下文对象
	 * @param chunkCount 块数
	 * @return IChunkJoiner
	 */
	protected IChunkJoiner createBaseJoiner4Receive(Object terminalId, Object msgId, Object cmdCode,
			Map<String, Object> ctx, int chunkCount) {
		return new ChunkJoiner(chunkCount);
	}

	/**
	 * <b>创建发送报文的底层Joiner。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 报文内容
	 * @param ctx 上下文对象
	 * @return IChunkJoiner
	 */
	protected IChunkJoiner createBaseJoiner4Send(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		MessageSplitter splitter = getMessageSplitterFactory()
				.getMessageSplitter((String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY));
		return new ChunkJoiner(splitter.splitMessage(cmdCode, content));
	}

	//多包消息合并实体接口
	protected static interface MessageJoiner extends IChunkJoiner {
		/**
		 * <b>返回终端id。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 终端id
		 */
		public Object terminalId();

		/**
		 * <b>返回消息id。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 消息id
		 */
		public Object msgId();

		/**
		 * <b>返回指令代码。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 指令代码
		 */
		public Object cmdCode();

		/**
		 * <b>返回报文上下文Map。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 报文上下文Map
		 */
		public Map<String, Object> ctx();

		/**
		 * <b>返回发送或接收完成后调用的Consumer。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 发送或接收完成后调用的Consumer
		 */
		public Consumer<Boolean> consumer();

		/**
		 * <b>设置发送或接收完成后调用的Consumer。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param consumer 发送或接收完成后调用的Consumer
		 */
		public void consumer(Consumer<Boolean> consumer);

		/**
		 * <b>是否有回调Consumer。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 是否有回调Consumer
		 */
		public boolean hasConsumer();

		/**
		 * <b>返回失败次数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param index 块索引
		 * @return 失败次数
		 */
		public int repeat(int index);

		/**
		 * <b>设置失败次数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param index 块索引
		 * @param repeat 失败次数
		 */
		public void repeat(int index, int repeat);

		/**
		 * <b>失败次数加1。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param index 块索引
		 */
		public void increpeat(int index);

		/**
		 * <b>返回当前发送块索引。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 当前发送块索引
		 */
		public int current();

		/**
		 * <b>设置当前发送块索引。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param current 当前发送块索引
		 */
		public void current(int current);
	}

	//接收多包消息合并实体接口
	protected static interface MessageJoiner4Receive extends MessageJoiner {

	}

	//发送多包消息合并实体接口
	protected static interface MessageJoiner4Send extends MessageJoiner {
		/**
		 * <b>返回指定索引数据块的发送上下文。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param index 索引
		 * @return 数据块发送上下文
		 */
		public Map<String, Object> sendContext(int index);
	}

	//MessageJoiner4Send代理类
	private static class MessageJoiner4SendProxy extends ChunkJoinerProxy implements MessageJoiner4Send {
		/**
		 * 当前块指针。
		 */
		private int current;

		private MessageJoiner4SendProxy(MessageJoiner4Send joiner) {
			super(joiner);
		}

		/** 
		 * terminalId。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#terminalId()
		 */
		@Override
		public Object terminalId() {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.terminalId();
		}

		/** 
		 * msgId。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#msgId()
		 */
		@Override
		public Object msgId() {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.msgId();
		}

		/** 
		 * cmdCode。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#cmdCode()
		 */
		@Override
		public Object cmdCode() {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.cmdCode();
		}

		/** 
		 * ctx。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#ctx()
		 */
		@Override
		public Map<String, Object> ctx() {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.ctx();
		}

		/** 
		 * consumer。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#consumer()
		 */
		@Override
		public Consumer<Boolean> consumer() {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.consumer();
		}

		/** 
		 * consumer。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#consumer(org.quincy.rock.core.function.Consumer)
		 */
		@Override
		public void consumer(Consumer<Boolean> consumer) {
			MessageJoiner4Send joiner = this.joiner();
			joiner.consumer(consumer);
		}

		/** 
		 * hasConsumer。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#hasConsumer()
		 */
		@Override
		public boolean hasConsumer() {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.hasConsumer();
		}

		/** 
		 * repeat。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#repeat(int)
		 */
		@Override
		public int repeat(int index) {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.repeat(index);
		}

		/** 
		 * repeat。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#repeat(int, int)
		 */
		@Override
		public void repeat(int index, int repeat) {
			MessageJoiner4Send joiner = this.joiner();
			joiner.repeat(index, repeat);
		}

		/** 
		 * increpeat。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#increpeat(int)
		 */
		@Override
		public void increpeat(int index) {
			MessageJoiner4Send joiner = this.joiner();
			joiner.increpeat(index);
		}

		/** 
		 * sendContext。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send#sendContext(int)
		 */
		@Override
		public Map<String, Object> sendContext(int index) {
			MessageJoiner4Send joiner = this.joiner();
			return joiner.sendContext(index);
		}

		/** 
		 * current。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#current()
		 */
		@Override
		public int current() {
			return this.current;
		}

		/** 
		 * current。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#current(int)
		 */
		@Override
		public void current(int current) {
			this.current = current;
		}
	}

	private abstract class AbstractMessageJoiner extends ChunkJoinerProxy implements MessageJoiner, Consumer<Boolean> {
		private Object terminalId;
		private Object msgId;
		private Object cmdCode;
		private Map<String, Object> ctx;
		private Consumer<Boolean> consumer;
		private int[] repeat;
		/**
		 * 当前正在发送的块索引
		 */
		private int current;

		public AbstractMessageJoiner(Object terminalId, Object msgId, Object cmdCode, Map<String, Object> ctx,
				int pkgTotal, Consumer<Boolean> consumer) {
			super(createBaseJoiner4Receive(terminalId, msgId, cmdCode, ctx, pkgTotal));
			this.init(terminalId, msgId, cmdCode, ctx, consumer);
		}

		public AbstractMessageJoiner(Object terminalId, Object msgId, Object cmdCode, Object content,
				Map<String, Object> ctx, Consumer<Boolean> consumer) {
			//拆分报文
			super(createBaseJoiner4Send(terminalId, msgId, cmdCode, content, ctx));
			this.init(terminalId, msgId, cmdCode, ctx, consumer);
		}

		private void init(Object terminalId, Object msgId, Object cmdCode, Map<String, Object> ctx,
				Consumer<Boolean> consumer) {
			joinConsumer(this);
			this.terminalId = terminalId;
			this.msgId = msgId;
			this.cmdCode = cmdCode;
			this.ctx = ctx;
			this.consumer = consumer;
			this.repeat = new int[this.chunkCount()];
		}

		/** 
		 * terminalId。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#terminalId()
		 */
		@Override
		public Object terminalId() {
			return this.terminalId;
		}

		/** 
		 * msgId。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#msgId()
		 */
		@Override
		public Object msgId() {
			return this.msgId;
		}

		/** 
		 * cmdCode。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#cmdCode()
		 */
		@Override
		public Object cmdCode() {
			return this.cmdCode;
		}

		/** 
		 * ctx。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#ctx()
		 */
		@Override
		public Map<String, Object> ctx() {
			return this.ctx;
		}

		/** 
		 * consumer。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#consumer()
		 */
		@Override
		public Consumer<Boolean> consumer() {
			return this.consumer;
		}

		/** 
		 * consumer。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#consumer(org.quincy.rock.core.function.Consumer)
		 */
		@Override
		public void consumer(Consumer<Boolean> consumer) {
			this.consumer = consumer;
		}

		/** 
		 * hasConsumer。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#hasConsumer()
		 */
		@Override
		public boolean hasConsumer() {
			return this.consumer != null;
		}

		/** 
		 * repeat。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#repeat(int)
		 */
		@Override
		public int repeat(int index) {
			return this.repeat[index];
		}

		/** 
		 * repeat。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#repeat(int, int)
		 */
		@Override
		public void repeat(int index, int repeat) {
			this.repeat[index] = repeat;
		}

		/** 
		 * increpeat。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#increpeat(int)
		 */
		@Override
		public void increpeat(int index) {
			this.repeat[index] += 1;
		}

		/** 
		 * current。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#current()
		 */
		@Override
		public int current() {
			return this.current;
		}

		/** 
		 * current。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner#current(int)
		 */
		@Override
		public void current(int current) {
			this.current = current;
		}
	}

	//多包发送消息实体
	private class MessageJoiner4SendImpl extends AbstractMessageJoiner implements MessageJoiner4Send {

		/**
		 * 原始报文。
		 */
		private Object content;

		public MessageJoiner4SendImpl(Object terminalId, Object msgId, Object cmdCode, Object content,
				Map<String, Object> ctx, Consumer<Boolean> consumer) {
			super(terminalId, msgId, cmdCode, content, ctx, consumer);
			this.content = content;
		}

		/** 
		 * sendContext。
		 * @see org.quincy.rock.comm.entrepot.SplitMessageEntrepot.MessageJoiner4Send#sendContext(int)
		 */
		@Override
		public Map<String, Object> sendContext(int index) {
			Map<String, Object> myCtx = new HashMap<>(this.ctx());
			myCtx.put(CommUtils.COMM_MSG_PKG_TOTAL_KEY, this.chunkCount());
			myCtx.put(CommUtils.COMM_MSG_PKG_INDEX_KEY, index);
			return myCtx;
		}

		/** 
		 * call。
		 * @see org.quincy.rock.core.function.Consumer#call(java.lang.Object)
		 */
		@Override
		public void call(Boolean success) {
			//发送完成
			cachePool.removeBufferValue(nonNullkey(terminalId()), cacheKey(msgId(), cmdCode(), true));
			fireToSentMessageAddDoneEvent(terminalId(), msgId(), cmdCode(), content, ctx(), success);
			if (hasConsumer()) {
				consumer().call(success);
			}
		}

	}

	//接收多包消息合并实体接口实现类
	private class MessageJoiner4ReceiveImpl extends AbstractMessageJoiner implements MessageJoiner4Receive {

		public MessageJoiner4ReceiveImpl(Object terminalId, Object msgId, Object cmdCode, Map<String, Object> ctx,
				int pkgTotal, Consumer<Boolean> consumer) {
			super(terminalId, msgId, cmdCode, ctx, pkgTotal, consumer);
		}

		@Override
		public void call(Boolean success) {
			//接收完成
			cachePool.removeBufferValue(nonNullkey(terminalId()), cacheKey(msgId(), cmdCode(), false));
			if (hasConsumer()) {
				consumer().call(success);
			}
			//拼完整报文
			Object content = getMessageSplitterFactory()
					.getMessageSplitter((String) ctx().get(CommUtils.COMM_MSG_TYPE_KEY))
					.joinMessage(cmdCode(), chunks());
			fireArrivedMessageAddDoneEvent(terminalId(), msgId(), cmdCode(), content, ctx());
		}
	}
}
