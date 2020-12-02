package org.quincy.rock.comm.process;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.core.exception.BlcokingException;

/**
 * <b>使用数组阻塞队列缓存的报文处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月9日 下午3:17:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class QueueMessageProcessService<K> extends MessageProcessService<K> {
	/**
	 * 阻塞超时秒数。
	 */
	private int timeout = 10;

	/**
	 * 最大处理线程数。
	 */
	private int maxThreadCount;
	/**
	 * 每个线程持有报文队列容量。
	 */
	private int capacity;

	/**
	 * 线程池。
	 */
	private ThreadPoolExecutor threadPool;
	/**
	 * 执行者线程组。
	 */
	private Executor<QueueMessage>[] executors = new Executor[512];

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public QueueMessageProcessService() {
		this(2, 1024);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 * @param capacity 每个线程持有报文队列容量
	 */
	public QueueMessageProcessService(int maxThreadCount, int capacity) {
		this.init(maxThreadCount, capacity);
	}

	private void init(int maxThreadCount, int capacity) {
		threadPool = new ThreadPoolExecutor(executors.length, executors.length, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		this.maxThreadCount = Math.min(maxThreadCount, executors.length);
		this.capacity = capacity;
	}

	/** 
	 * reset。
	 * @see org.quincy.rock.comm.process.MessageProcessService#reset()
	 */
	@Override
	public void reset() {
		this.destroy();
		this.init(maxThreadCount, capacity);
		this.start();
	}

	/**
	 * <b>创建阻塞队列。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param capacity 每个线程持有报文队列容量
	 * @return 阻塞队列
	 */
	protected BlockingQueue<QueueMessage> createBlockingQueue(int capacity) {
		return new ArrayBlockingQueue<>(capacity);
	}

	/**
	 * <b>返回当前运行线程个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前运行线程个数
	 */
	public final int activeThreadCount() {
		return threadPool.getActiveCount();
	}

	/**
	 * <b>返回指定线程的待处理报文条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadindex 线程索引
	 * @return 队列中待处理报文条数
	 */
	public final int count(int threadIndex) {
		Executor<QueueMessage> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.count();
	}

	/**
	 * <b>返回指定线程的报文等待秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指示报文在队列中等待多久会被处理,反应线程处理报文的效率。
	 * @param threadIndex 线程索引
	 * @return 指定线程的报文等待秒数
	 */
	public final int waitSeconds(int threadIndex) {
		Executor<QueueMessage> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.waitSeconds();
	}

	/**
	 * <b>>返回指定线程的处理报文条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 指定线程的处理报文条数
	 */
	public final long processCount(int threadIndex) {
		Executor<QueueMessage> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.processCount();
	}

	/**
	 * <b>>返回指定线程的单个报文处理毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 指定线程的单个报文处理毫秒数
	 */
	public final long processMillis(int threadIndex) {
		Executor<QueueMessage> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.processMillis();
	}

	/**
	 * <b>>返回指定线程的单个报文解析毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 指定线程的单个报文解析毫秒数
	 */
	public final long resolveMillis(int threadIndex) {
		Executor<QueueMessage> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.resolveMillis();
	}

	/**
	 * <b>>返回指定线程的单个报文传输毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 指定线程的单个报文传输毫秒数
	 */
	public final long transferMillis(int threadIndex) {
		Executor<QueueMessage> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.transferMillis();
	}

	/**
	 * <b>获得最大处理线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大处理线程数
	 */
	public final int getMaxThreadCount() {
		return this.maxThreadCount;
	}

	/**
	 * 返回每个线程持有报文队列容量
	 * @return 每个线程持有报文队列容量
	 */
	public final int getCapacity() {
		return capacity;
	}

	/**
	 * 返回阻塞超时秒数
	 * @return 阻塞超时秒数
	 */
	public final int getTimeout() {
		return timeout;
	}

	/**
	 * 设置阻塞超时秒数
	 * @param timeout 阻塞超时秒数
	 */
	public final void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/** 
	 * handleArrivedMessage。
	 * @see org.quincy.rock.comm.process.MessageProcessService#handleArrivedMessage(org.quincy.rock.comm.process.QueueMessage)
	 */
	@Override
	protected final void handleArrivedMessage(QueueMessage queueMessage) {
		int threadIndex = assignThreadExecutorIndex(queueMessage.terminalId(), getMaxThreadCount());
		getValidExecutor(threadIndex).put(queueMessage);
	}

	/**
	 * <b>返回有效的执行者线程。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 执行者线程
	 */
	private synchronized Executor<QueueMessage> getValidExecutor(int threadIndex) {
		if (executors[threadIndex] == null) {
			executors[threadIndex] = new Executor<>(threadIndex, createBlockingQueue(capacity));
			threadPool.submit(executors[threadIndex]);
		}
		return executors[threadIndex];
	}

	/**
	 * <b>分配线程执行者索引。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @param totalCount 总共线程数
	 * @return 线程执行者索引
	 */
	protected int assignThreadExecutorIndex(Object terminalId, int totalCount) {
		int v = (terminalId instanceof Number) ? ((Number) terminalId).intValue() : terminalId.toString().hashCode();
		return Math.abs(v) % totalCount;
	}

	/** 
	 * awaitTermination。
	 * @see org.quincy.rock.comm.process.MessageProcessService#awaitTermination()
	 */
	@Override
	protected void awaitTermination() throws Exception {
		for (int i = 0; i < executors.length; i++) {
			if (executors[i] != null) {
				executors[i].stop();
			}
		}
		threadPool.shutdown();
		threadPool.awaitTermination(30, TimeUnit.MINUTES);
	}

	//执行者
	private class Executor<T extends QueueMessage> implements Runnable {
		/**
		 * threadIndex。
		 */
		private final int threadIndex;
		/**
		 * queue。
		 */
		private BlockingQueue<T> queue;
		/**
		 * 已经停止执行者。
		 */
		private boolean stopped;
		/**
		 * 报文传输毫秒数。
		 */
		private long transferMillis;
		/**
		 * 报文解析毫秒数。
		 */
		private long resolveMillis;
		/**
		 * 单个报文处理毫秒数。
		 */
		private long processMillis;
		/**
		 * 处理报文条数。
		 */
		private long processCount;
		/**
		 * 报文等待秒数。
		 */
		private int waitSeconds;

		/**
		 * <b>构造方法。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param index
		 * @param queue 队列
		 */
		public Executor(int index, BlockingQueue<T> queue) {
			this.threadIndex = index;
			this.queue = queue;
		}

		/**
		 * <b>queue。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return
		 */
		private synchronized BlockingQueue<T> queue() {
			return queue;
		}

		/** 
		 * run。
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while (true) {
				try {
					T msg = queue().poll(1, TimeUnit.SECONDS);
					if (msg != null) {
						recorder.write("Retrieve the message from the queue:{0},term:{1}.", threadIndex,
								msg.terminalId());
						long ibegin = System.currentTimeMillis();
						this.waitSeconds = (int) (ibegin - msg.timestamp()) / 1000;
						processQueueMessage(msg);
						this.processMillis = System.currentTimeMillis() - ibegin;
						this.processCount++;
					}
					if (stopped() && queue.isEmpty()) {
						//退出并销毁线程			
						recorder.write("Quit the queue:{0}.", threadIndex);
						break;
					}
				} catch (Exception e) {
					recorder.write(e, e.getMessage());
				}
			}
			executors[threadIndex] = null;
		}

		/**
		 * <b>放入一条队列报文。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param msg 队列报文
		 */
		public void put(T msg) {
			try {
				this.resolveMillis = System.currentTimeMillis() - msg.timestamp(); //报文解析时间
				Long messageTime = msg.messageTime();
				if (messageTime != null)
					this.transferMillis = msg.timestamp() - messageTime; //报文传输时间
				if (stopped() || !queue().offer(msg, timeout, TimeUnit.SECONDS)) {
					BlcokingException ex = new BlcokingException("terminalId:" + msg.terminalId());
					recorder.write(ex, ex.getMessage());
					throw ex;
				} else if (recorder.canWrite()) {
					recorder.write("The message has been put on the queue:{0},term:{1}.", threadIndex,
							msg.terminalId());
				}
			} catch (InterruptedException e) {
				throw new CommunicateException(e.getMessage(), e);
			}
		}

		/**
		 * <b>返回队列中待处理报文个数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 队列中待处理报文个数
		 */
		public int count() {
			return queue.size();
		}

		/**
		 * <b>报文等待秒数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 指示报文在队列中等待多久会被处理。
		 * @return 报文等待秒数
		 */
		public int waitSeconds() {
			return this.waitSeconds;
		}

		/**
		 * <b>处理报文条数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 处理报文条数
		 */
		public long processCount() {
			return this.processCount;
		}

		/**
		 * <b>单个报文处理毫秒数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 单个报文处理毫秒数
		 */
		public long processMillis() {
			return this.processMillis;
		}

		/**
		 * <b>报文解析毫秒数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 报文解析毫秒数
		 */
		public long resolveMillis() {
			return this.resolveMillis;
		}

		/**
		 * <b>报文传输毫秒数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 报文传输毫秒数
		 */
		public long transferMillis() {
			return this.transferMillis;
		}

		/**
		 * <b>是否已经停止。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 是否已经停止
		 */
		public boolean stopped() {
			return this.stopped;
		}

		/**
		 * <b>通知停止执行者线程。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 */
		public void stop() {
			stopped = true;
		}
	}
}
