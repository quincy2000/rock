package org.quincy.rock.core.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * <b>使用延迟队列缓存的数据处理服务(延迟处理)。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年3月12日 上午9:18:50</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DelayQueueProcessService<K, V> extends QueueProcessService<K, V> implements HasDelay {
	/**
	 * 延迟毫秒数。
	 */
	private int delayMillis;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 */
	public DelayQueueProcessService(int maxThreadCount) {
		super(maxThreadCount);
	}

	/**
	 * <b>获得延迟毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 延迟毫秒数
	 */
	public int getDelayMillis() {
		return delayMillis;
	}

	/**
	 * <b>设置延迟毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delayMillis 延迟毫秒数
	 */
	public void setDelayMillis(int delayMillis) {
		this.delayMillis = delayMillis;
	}

	/** 
	 * createBlockingQueue。
	 * @see org.quincy.rock.core.concurrent.QueueProcessService#createBlockingQueue()
	 */
	@Override
	protected BlockingQueue<DataClosure<K, V>> createBlockingQueue() {
		return new DelayQueue();
	}

	/** 
	 * createDataClosure。
	 * @see org.quincy.rock.core.concurrent.ProcessService#createDataClosure(java.lang.Object, java.lang.Object, org.quincy.rock.core.concurrent.Processor)
	 */
	@Override
	protected DataClosure<K, V> createDataClosure(K key, V data, Processor<V> processor) {
		return new DataClosure<K, V>(key, data, processor, delayMillis);
	}
}
