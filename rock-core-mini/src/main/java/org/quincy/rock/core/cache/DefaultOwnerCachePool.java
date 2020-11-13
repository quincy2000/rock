package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <b>物主对象缓冲池。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年12月5日 上午11:18:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultOwnerCachePool<V> extends OwnerCachePool<V> {
	/**
	 * 存放缓冲值的Map
	 */
	private Map<Object, Map<Object, ObjectCache<V>>> cachedMap1;

	/**
	 * 是否使用软引用。
	 */
	private boolean softCache;
	/**
	 * 引用队列。
	 */
	private ReferenceQueue<? super V> queue;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DefaultOwnerCachePool() {
		this(false);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param softCache 是否使用软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 */
	public DefaultOwnerCachePool(boolean softCache) {
		this.softCache = softCache;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用软引用模式(在内存不够时会被强制垃圾收集以释放缓存)。
	 * @param queue 软引用使用的拦截队列
	 */
	public DefaultOwnerCachePool(ReferenceQueue<? super V> queue) {
		this(true);
		this.queue = queue;
	}

	/** 
	 * putBufferValue。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#putBufferValue(java.lang.Object, java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public boolean putBufferValue(Object owner, Object key, V value, int timeout) {
		boolean ok = false;
		if (value == null)
			throw new NullPointerException("Null values cannot be cached");
		else {
			Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
			Map<Object, ObjectCache<V>> ocMap = cachedMap.get(owner);
			if (ocMap == null && (!isFull() || clearCache(true))) {
				ocMap = createOwnerCachedMap();
				cachedMap.put(owner, ocMap);
			}
			if (ocMap != null && (ocMap.size() < getOwnerCacheSize() || clearCache(ocMap, true))) {
				ocMap.put(key, queue == null ? createObjectCache(value, softCache, timeout)
						: createObjectCache(value, timeout, queue));
				ok = true;
			}
		}
		return ok;
	}

	/** 
	 * getBufferValue。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#getBufferValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V getBufferValue(Object owner, Object key) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		Map<Object, ObjectCache<V>> ocMap = cachedMap.get(owner);
		if (ocMap != null) {
			ObjectCache<V> oc = ocMap.get(key);
			if (oc == null)
				return null;
			else {
				V vo = oc.get();
				if (vo == null)
					ocMap.remove(key); //移走无效缓存值
				return vo;
			}
		}
		return null;
	}

	/** 
	 * seekBufferValue。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#seekBufferValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V seekBufferValue(Object owner, Object key) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		Map<Object, ObjectCache<V>> ocMap = cachedMap.get(owner);
		if (ocMap != null) {
			ObjectCache<V> oc = ocMap.get(key);
			if (oc == null)
				return null;
			else if (oc.isValid()) {
				return oc.seek();
			} else {
				ocMap.remove(key); //移走无效缓存值
			}
		}
		return null;
	}

	/** 
	 * removeBufferValue。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#removeBufferValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void removeBufferValue(Object owner, Object key) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		Map<Object, ObjectCache<V>> ocMap = cachedMap.get(owner);
		if (ocMap != null)
			ocMap.remove(key);
		if (ocMap.isEmpty())
			cachedMap.remove(owner);
	}

	/** 
	 * removeOwner。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#removeOwner(java.lang.Object)
	 */
	@Override
	public void removeOwner(Object owner) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		cachedMap.remove(owner);
	}

	/** 
	 * hasOwner。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#hasOwner(java.lang.Object)
	 */
	@Override
	public boolean hasOwner(Object owner) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		return cachedMap.containsKey(owner);
	}

	/** 
	 * owners。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#owners()
	 */
	@Override
	public Iterable<?> owners() {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		List<?> list = new ArrayList<>(cachedMap.keySet());
		return list;
	}

	/** 
	 * owners。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#owners(int, int)
	 */
	@Override
	public Iterable<?> owners(int fromIndex, int length) {
		int remaining = count() - fromIndex; //剩余个数
		if (remaining > 0 && length != 0) {
			Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
			length = (length < 0) ? remaining : Math.min(remaining, length);
			List list = new ArrayList<>(length);
			int i = 0, toIndex = fromIndex + length;
			for (Object key : cachedMap.keySet()) {
				if (i >= toIndex)
					break;
				if (i >= fromIndex)
					list.add(key);
				i++;
			}
			return list;
		} else
			return Collections.EMPTY_LIST;
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.cache.CachePoolRef#count()
	 */
	@Override
	public int count() {
		return cachedMap().size();
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#count(java.lang.Object)
	 */
	@Override
	public int count(Object owner) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		Map<Object, ObjectCache<V>> map = cachedMap.get(owner);
		return map == null ? 0 : map.size();
	}

	/** 
	 * keys。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#keys(java.lang.Object)
	 */
	@Override
	public Iterable<?> keys(Object owner) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		Map<Object, ObjectCache<V>> map = cachedMap.get(owner);
		List<?> list = (map == null) ? Collections.EMPTY_LIST : new ArrayList<>(map.keySet());
		return list;
	}

	/** 
	 * keys。
	 * @see org.quincy.rock.core.cache.OwnerCachePool#keys(java.lang.Object, int, int)
	 */
	@Override
	public Iterable<?> keys(Object owner, int fromIndex, int length) {
		Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
		Map<Object, ObjectCache<V>> map = cachedMap.get(owner);
		if (map == null)
			return Collections.EMPTY_LIST;
		int remaining = map.size() - fromIndex; //剩余个数
		if (remaining > 0 && length != 0) {
			length = (length < 0) ? remaining : Math.min(remaining, length);
			List list = new ArrayList<>(length);
			int i = 0, toIndex = fromIndex + length;
			for (Object key : map.keySet()) {
				if (i >= toIndex)
					break;
				if (i >= fromIndex)
					list.add(key);
				i++;
			}
			return list;
		} else
			return Collections.EMPTY_LIST;
	}

	/**
	 * <b>清除每个物主的缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ownerCache 物主对象缓存
	 * @param ifFull 如果满了才清除缓存
	 * @return 是否有空闲的位置可以插入新元素
	 */
	protected boolean clearCache(Map<Object, ObjectCache<V>> ownerCache, boolean ifFull) {
		synchronized (ownerCache) {
			if (ownerCache.isEmpty() || (ifFull && ownerCache.size() < getOwnerCacheSize()))
				return true;
			//推测大概清掉的数据条数
			int size = Math.max(50, ownerCache.size() / 100);
			List<Object> invalidList = new ArrayList<>(size);
			for (Entry<Object, ObjectCache<V>> entry : ownerCache.entrySet()) {
				if (!entry.getValue().isValid())
					invalidList.add(entry.getKey());
			}
			//清除无效缓存值
			for (Object key : invalidList) {
				ownerCache.remove(key);
			}
			return ownerCache.size() < getOwnerCacheSize();
		}
	}

	/** 
	 * 清除物主。
	 * @see org.quincy.rock.core.cache.CachePoolRef#clearCache(boolean)
	 */
	@Override
	protected boolean clearCache(boolean ifFull) {
		synchronized (this) {
			if (isEmpty() || (ifFull && !isFull()))
				return true;
			//copy list
			Map<Object, Map<Object, ObjectCache<V>>> cachedMap = cachedMap();
			List<Pair<WeakReference, WeakReference<Map>>> copyList = new ArrayList();
			try {
				for (Entry<Object, Map<Object, ObjectCache<V>>> entry : cachedMap.entrySet()) {
					copyList.add(
							new ImmutablePair(new WeakReference(entry.getKey()), new WeakReference(entry.getValue())));
				}
			} catch (Exception ex) {
			}
			//clear
			for (Pair<WeakReference, WeakReference<Map>> pair : copyList) {
				Object owner = pair.getKey().get();
				Map<Object, ObjectCache<V>> ownerCache = pair.getValue().get();
				if (owner != null && ownerCache != null) {
					clearCache(ownerCache, false);
					if (ownerCache.isEmpty())
						cachedMap.remove(owner);
				}
			}
			//
			return cachedMap().size() < getCacheSize();
		}
	}

	/**
	 * <b>创建缓存Map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存Map
	 */
	protected Map<Object, Map<Object, ObjectCache<V>>> createCachedMap() {
		return new HashMap<>();
	}

	/**
	 * <b>创建物主缓存Map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物主缓存Map
	 */
	protected Map<Object, ObjectCache<V>> createOwnerCachedMap() {
		return new HashMap<>();
	}

	private Map<Object, Map<Object, ObjectCache<V>>> cachedMap() {
		if (cachedMap1 == null) {
			cachedMap1 = createCachedMap();
		}
		return cachedMap1;
	}
}
