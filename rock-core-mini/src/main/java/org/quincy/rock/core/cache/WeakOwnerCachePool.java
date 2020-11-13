package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <b>弱引用的物主对象缓冲池。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 弱引用的owner对象，当owner对象被垃圾收集后，相应的缓冲值也将被垃圾收集。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2019年4月24日 下午2:19:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WeakOwnerCachePool<V> extends DefaultOwnerCachePool<V> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public WeakOwnerCachePool() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param softCache 是否使用软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 */
	public WeakOwnerCachePool(boolean softCache) {
		super(softCache);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用软引用模式(在内存不够时会被强制垃圾收集以释放缓存)。
	 * @param queue 软引用使用的拦截队列
	 */
	public WeakOwnerCachePool(ReferenceQueue<? super V> queue) {
		super(queue);
	}

	/** 
	 * createCachedMap。
	 * @see org.quincy.rock.core.cache.DefaultOwnerCachePool#createCachedMap()
	 */
	@Override
	protected Map<Object, Map<Object, ObjectCache<V>>> createCachedMap() {
		return new WeakHashMap();
	}
}
