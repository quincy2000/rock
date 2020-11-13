package org.quincy.rock.core.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.quincy.rock.core.exception.BlcokingException;
import org.quincy.rock.core.exception.RockException;

/**
 * <b>使用阻塞队列缓存的数据处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月25日 下午3:56:41</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class QueueProcessService<K, V> extends ProcessService<K, V> {
	/**
	 * 阻塞超时秒数。
	 */
	private int timeout = 0;
	/**
	 * 最大处理线程数。
	 */
	private int maxThreadCount;

	/**
	 * 线程池。
	 */
	private final ThreadPoolExecutor threadPool;
	/**
	 * 执行者线程组。
	 */
	private final Executor<DataClosure<K, V>>[] executors = new Executor[512];

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 * @param capacity 每个线程持有队列容量
	 */
	public QueueProcessService(int maxThreadCount) {
		threadPool = new ThreadPoolExecutor(executors.length, executors.length, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		this.maxThreadCount = Math.min(maxThreadCount, executors.length);
	}

	/**
	 * <b>创建阻塞队列。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 阻塞队列
	 */
	protected abstract BlockingQueue<DataClosure<K, V>> createBlockingQueue();

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
	 * <b>返回指定线程的待处理数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadindex 线程索引
	 * @return 队列中待处理数据条数
	 */
	public final int count(int threadIndex) {
		Executor<DataClosure<K, V>> executor = executors[threadIndex];
		return executor == null ? 0 : executor.count();
	}

	/**
	 * <b>返回指定线程的数据等待秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指示数据在队列中等待多久会被处理,反映线程的处理效率。
	 * @return 指定线程的数据等待秒数
	 */
	public final int waitSeconds(int threadIndex) {
		Executor<DataClosure<K, V>> executor = executors[threadIndex];
		return executor == null ? 0 : executor.waitSeconds();
	}

	/**
	 * <b>>返回指定线程的单条数据处理毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 指定线程的单条数据处理毫秒数
	 */
	public final long processMillis(int threadIndex) {
		Executor<DataClosure<K, V>> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.processMillis();
	}

	/**
	 * <b>>返回指定线程的处理数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 指定线程的处理数据条数
	 */
	public final long processCount(int threadIndex) {
		Executor<DataClosure<K, V>> executor = executors[threadIndex];
		return (executor == null) ? 0 : executor.processCount();
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
	 * handleDataClosure。
	 * @see org.quincy.rock.core.concurrent.ProcessService#handleDataClosure(org.quincy.rock.core.concurrent.ProcessService.DataClosure)
	 */
	@Override
	protected final void handleDataClosure(DataClosure<K, V> dataClosure) throws BlcokingException {
		int threadIndex = assignThreadExecutorIndex(dataClosure.key(), getMaxThreadCount());
		getValidExecutor(threadIndex).put(dataClosure);
	}

	/**
	 * <b>返回有效的执行者线程。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadIndex 线程索引
	 * @return 执行者线程
	 */
	private synchronized Executor<DataClosure<K, V>> getValidExecutor(int threadIndex) {
		if (executors[threadIndex] == null) {
			executors[threadIndex] = new Executor<>(threadIndex, createBlockingQueue());
			threadPool.submit(executors[threadIndex]);
		}
		return executors[threadIndex];
	}

	/**
	 * <b>分配线程执行者索引。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 键
	 * @param totalCount 总共线程数
	 * @return 线程执行者索引
	 */
	protected int assignThreadExecutorIndex(K key, int totalCount) {
		int v = (key instanceof Number) ? ((Number) key).intValue() : String.valueOf(key).hashCode();
		return Math.abs(v) % totalCount;
	}

	/** 
	 * awaitTermination。
	 * @see org.quincy.rock.core.concurrent.ProcessService#awaitTermination()
	 */
	@Override
	protected void awaitTermination() throws Exception {
		for (int i = 0; i < executors.length; i++) {
			if (executors[i] != null)
				executors[i].stop();
		}
		threadPool.shutdown();
		threadPool.awaitTermination(30, TimeUnit.MINUTES);
	}

	//执行者
	private class Executor<T extends DataClosure<K, V>> implements Runnable {
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
		 * 数据等待秒数。
		 */
		private int waitSeconds;
		/**
		 * 单条数据处理毫秒数。
		 */
		private long processMillis;
		/**
		 * 处理数据条数。
		 */
		private long processCount;
		
		/**
		 * <b>构造方法。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param index 所在索引
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
					T closure = queue().poll(1, TimeUnit.SECONDS);
					if (closure != null) {
						long ibegin = System.currentTimeMillis();
						this.waitSeconds = (int) (ibegin - closure.timestamp()) / 1000;
						processDataClosure(closure);
						this.processMillis = System.currentTimeMillis() - ibegin;
						this.processCount++;
					}
					if (stopped() && queue.isEmpty()) {
						//退出并销毁线程
						recorder.write("QueueProcessService[{0}]:Quit the queue[{1}].", getName(), threadIndex);
						break;
					}
				} catch (Exception e) {
					recorder.write(e, e.getMessage());
				}
			}
			executors[threadIndex] = null;
		}

		/**
		 * <b>放入一条数据。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param closure 数据
		 */
		public void put(T closure) {
			try {
				if (stopped() || timeout > 0 ? !queue().offer(closure, timeout, TimeUnit.SECONDS)
						: !queue().offer(closure))
					throw new BlcokingException();
			} catch (InterruptedException e) {
				throw new RockException(e.getMessage(), e);
			}
		}

		/**
		 * <b>返回队列中待处理的数据个数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 队列中待处理的数据个数
		 */
		public int count() {
			return queue.size();
		}

		/**
		 * <b>数据等待秒数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 指示数据在队列中等待多久会被处理。
		 * @return 数据等待秒数
		 */
		public int waitSeconds() {
			return this.waitSeconds;
		}

		/**
		 * <b>单条数据处理毫秒数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 单条数据处理毫秒数
		 */
		public long processMillis() {
			return this.processMillis;
		}

		/**
		 * <b>处理数据条数。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 处理数据条数
		 */
		public long processCount() {
			return this.processCount;
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
