package org.quincy.rock.core.concurrent;

import org.quincy.rock.core.exception.NotFoundException;

/**
 * <b>DefaultDelayQueueProcessService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年4月16日 下午4:02:21</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DefaultDelayQueueProcessService<K, V> extends DelayQueueProcessService<K, V> {

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
	public DefaultDelayQueueProcessService() {
		super(2);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 */
	public DefaultDelayQueueProcessService(int maxThreadCount) {
		super(maxThreadCount);
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
	 * getProcessor。
	 * @see org.quincy.rock.core.concurrent.ProcessService#getProcessor(java.lang.Object)
	 */
	@Override
	protected Processor<V> getProcessor(K key) throws NotFoundException {
		return this.processor;
	}
}
