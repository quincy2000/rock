package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <b>缺省的对象缓冲池。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年12月10日 下午3:10:06</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultObjectCachePool<V> extends ObjectCachePool<V> {
	/**
	 * 存放缓冲值的Map
	 */
	private Map<Object, ObjectCache<V>> cachedMap;
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
	public DefaultObjectCachePool() {
		this(false);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param softCache 是否使用软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 */
	public DefaultObjectCachePool(boolean softCache) {
		this.softCache = softCache;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用软引用模式(在内存不够时会被强制垃圾收集以释放缓存)。
	 * @param queue 软引用使用的拦截队列
	 */
	public DefaultObjectCachePool(ReferenceQueue<? super V> queue) {
		this(true);
		this.queue = queue;
	}

	/** 
	 * putBufferValue。
	 * @see org.quincy.rock.core.cache.ObjectCachePool#putBufferValue(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public boolean putBufferValue(Object key, V value, int timeout) {
		boolean ok = false;
		if (value == null)
			throw new NullPointerException("Null values cannot be cached");
		else if (!isFull() || this.clearCache(true)) {
			Map<Object, ObjectCache<V>> map = cachedMap();
			map.put(key, queue == null ? createObjectCache(value, softCache, timeout)
					: createObjectCache(value, timeout, queue));
			ok = true;
		}
		return ok;
	}

	/** 
	 * getBufferValue。
	 * @see org.quincy.rock.core.cache.ObjectCachePool#getBufferValue(java.lang.Object)
	 */
	@Override
	public V getBufferValue(Object key) {
		Map<Object, ObjectCache<V>> map = cachedMap();
		ObjectCache<V> oc = map.get(key);
		if (oc == null)
			return null;
		else {
			V vo = oc.get();
			if (vo == null) {
				oc = map.remove(key); //移走无效缓存值
				this.hookRemoveBufferValue(key, oc);
			}
			return vo;
		}
	}

	/** 
	 * seekBufferValue。
	 * @see org.quincy.rock.core.cache.ObjectCachePool#seekBufferValue(java.lang.Object)
	 */
	@Override
	public V seekBufferValue(Object key) {
		Map<Object, ObjectCache<V>> map = cachedMap();
		ObjectCache<V> oc = map.get(key);
		if (oc == null)
			return null;
		else if (oc.isValid()) {
			return oc.seek();
		} else {
			oc = map.remove(key); //移走无效缓存值
			this.hookRemoveBufferValue(key, oc);
			return null;
		}
	}

	/** 
	 * removeBufferValue。
	 * @see org.quincy.rock.core.cache.ObjectCachePool#removeBufferValue(java.lang.Object)
	 */
	@Override
	public void removeBufferValue(Object key) {
		ObjectCache<V> oc = cachedMap().remove(key);
		this.hookRemoveBufferValue(key, oc);
	}

	/**
	 * <b>移走缓冲值时调用。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 值的key
	 * @param oc 对象缓存值
	 */
	protected void hookRemoveBufferValue(Object key, ObjectCache<V> oc) {

	}

	/** 
	 * isEmpty。
	 * @see org.quincy.rock.core.cache.CachePoolRef#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return cachedMap().isEmpty();
	}

	/** 
	 * isFull。
	 * @see org.quincy.rock.core.cache.CachePoolRef#isFull()
	 */
	@Override
	public boolean isFull() {
		return cachedMap().size() >= getCacheSize();
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
	 * keys。
	 * @see org.quincy.rock.core.cache.ObjectCachePool#keys()
	 */
	@Override
	public Iterable<?> keys() {
		List<?> list = new ArrayList<>(cachedMap().keySet());
		return list;
	}

	/** 
	 * keys。
	 * @see org.quincy.rock.core.cache.ObjectCachePool#keys(int, int)
	 */
	@Override
	public Iterable<?> keys(int fromIndex, int length) {
		int remaining = count() - fromIndex; //剩余个数
		if (remaining > 0 && length != 0) {
			Map<Object, ObjectCache<V>> map = cachedMap();
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
	 * clearCache。
	 * @see org.quincy.rock.core.cache.CachePoolRef#clearCache(boolean)
	 */
	@Override
	protected boolean clearCache(boolean ifFull) {
		synchronized (this) {
			if (isEmpty() || (ifFull && !isFull()))
				return true;
			//推测大概清掉的数据条数
			Map<Object, ObjectCache<V>> map = cachedMap();
			int size = Math.max(50, map.size() / 100);
			List<Object> invalidList = new ArrayList<>(size);
			for (Entry<Object, ObjectCache<V>> entry : map.entrySet()) {
				if (!entry.getValue().isValid())
					invalidList.add(entry.getKey());
			}
			//清除无效缓存值
			for (Object key : invalidList) {
				ObjectCache<V> oc = map.remove(key);
				this.hookRemoveBufferValue(key, oc);
			}
			return true;
		}
	}

	/**
	 * <b>创建缓存Map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存Map
	 */
	protected Map<Object, ObjectCache<V>> createCacheMap() {
		return new HashMap<>();
	}

	//返回缓存Map
	private Map<Object, ObjectCache<V>> cachedMap() {
		if (cachedMap == null) {
			cachedMap = createCacheMap();
		}
		return cachedMap;
	}
}
