package org.quincy.rock.core.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.quincy.rock.core.exception.NotFoundException;

/**
 * <b>使用链表阻塞队列缓存的数据处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年4月22日 下午4:50:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class LinkedQueueProcessService<K, V> extends BoundedQueueProcessService<K, V> {
	/**
	 * 处理器。
	 */
	private Processor<V> processor;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public LinkedQueueProcessService() {
		this(2, 1024);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 * @param capacity 每个线程持有队列容量
	 */
	public LinkedQueueProcessService(int maxThreadCount, int capacity) {
		super(maxThreadCount, capacity);
	}

	/**
	 * <b>获得处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理器
	 */
	public Processor<V> getProcessor() {
		return processor;
	}

	/**
	 * <b>设置处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processor 处理器
	 */
	public void setProcessor(Processor<V> processor) {
		this.processor = processor;
	}

	/** 
	 * createBlockingQueue。
	 * @see org.quincy.rock.core.concurrent.BoundedQueueProcessService#createBlockingQueue(int)
	 */
	@Override
	protected BlockingQueue<DataClosure<K, V>> createBlockingQueue(int capacity) {
		return new LinkedBlockingDeque<>(capacity);
	}

	/** 
	 * getProcessor。
	 * @see org.quincy.rock.core.concurrent.ProcessService#getProcessor(java.lang.Object)
	 */
	@Override
	protected Processor<V> getProcessor(K key) throws NotFoundException {
		return this.processor;
	}

}
