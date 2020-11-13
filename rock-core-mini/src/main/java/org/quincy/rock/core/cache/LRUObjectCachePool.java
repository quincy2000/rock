package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <b>LRUObjectCachePool。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月9日 下午2:29:49</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LRUObjectCachePool<V> extends DefaultObjectCachePool<V> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public LRUObjectCachePool() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param softCache 是否使用软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 */
	public LRUObjectCachePool(boolean softCache) {
		super(softCache);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用软引用模式(在内存不够时会被强制垃圾收集以释放缓存)。
	 * @param queue 软引用使用的拦截队列
	 */
	public LRUObjectCachePool(ReferenceQueue<? super V> queue) {
		super(queue);
	}

	/** 
	 * clearCache。
	 * @see org.quincy.rock.core.cache.DefaultObjectCachePool#clearCache(boolean)
	 */
	@Override
	protected boolean clearCache(boolean ifFull) {
		super.clearCache(ifFull);
		return true;
	}

	/** 
	 * createCacheMap。
	 * @see org.quincy.rock.core.cache.DefaultObjectCachePool#createCacheMap()
	 */
	@Override
	protected Map<Object, ObjectCache<V>> createCacheMap() {
		return new LinkedHashMap() {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry eldest) {
				//保证新的会冲掉最老的数据
				return this.size() >= getCacheSize();
			}
		};
	}

}
