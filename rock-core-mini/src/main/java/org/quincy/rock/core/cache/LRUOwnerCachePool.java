package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <b>LRUOwnerCachePool。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月9日 下午2:37:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LRUOwnerCachePool<V> extends DefaultOwnerCachePool<V> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public LRUOwnerCachePool() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param softCache 是否使用软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 */
	public LRUOwnerCachePool(boolean softCache) {
		super(softCache);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用软引用模式(在内存不够时会被强制垃圾收集以释放缓存)。
	 * @param queue 软引用使用的拦截队列
	 */
	public LRUOwnerCachePool(ReferenceQueue<? super V> queue) {
		super(queue);
	}

	/** 
	 * clearCache。
	 * @see org.quincy.rock.core.cache.DefaultOwnerCachePool#clearCache(java.util.Map, boolean)
	 */
	@Override
	protected boolean clearCache(Map<Object, ObjectCache<V>> ownerCache, boolean ifFull) {
		super.clearCache(ownerCache, ifFull);
		return true;
	}

	/** 
	 * clearCache。
	 * @see org.quincy.rock.core.cache.DefaultOwnerCachePool#clearCache(boolean)
	 */
	@Override
	protected boolean clearCache(boolean ifFull) {
		super.clearCache(ifFull);
		return true;
	}

	/** 
	 * createCachedMap。
	 * @see org.quincy.rock.core.cache.DefaultOwnerCachePool#createCachedMap()
	 */
	@Override
	protected Map<Object, Map<Object, ObjectCache<V>>> createCachedMap() {
		return new LinkedHashMap() {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry eldest) {
				return this.size() >= getCacheSize();
			}

		};
	}

	/** 
	 * createOwnerCachedMap。
	 * @see org.quincy.rock.core.cache.DefaultOwnerCachePool#createOwnerCachedMap()
	 */
	@Override
	protected Map<Object, ObjectCache<V>> createOwnerCachedMap() {
		return new LinkedHashMap() {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry eldest) {
				return this.size() >= getOwnerCacheSize();
			}

		};
	}

}
