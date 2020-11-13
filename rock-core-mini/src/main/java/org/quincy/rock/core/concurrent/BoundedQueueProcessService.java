package org.quincy.rock.core.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * <b>使用有界阻塞队列缓存的数据处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年4月16日 下午4:16:04</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class BoundedQueueProcessService<K, V> extends QueueProcessService<K, V> implements Bounded {
	/**
	 * 每个线程持有队列容量。
	 */
	private int capacity;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 * @param capacity 每个线程持有队列容量
	 */
	public BoundedQueueProcessService(int maxThreadCount, int capacity) {
		super(maxThreadCount);
		this.capacity = capacity;
	}

	/**
	 * 返回每个线程持有队列容量
	 * @return 每个线程持有队列容量
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

	/** 
	 * createBlockingQueue。
	 * @see org.quincy.rock.core.concurrent.QueueProcessService#createBlockingQueue()
	 */
	@Override
	protected final BlockingQueue<DataClosure<K, V>> createBlockingQueue() {
		return this.createBlockingQueue(getCapacity());
	}

	/**
	 * <b>创建阻塞队列。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param capacity 队列容量
	 * @return 阻塞队列
	 */
	protected abstract BlockingQueue<DataClosure<K, V>> createBlockingQueue(int capacity);
}
