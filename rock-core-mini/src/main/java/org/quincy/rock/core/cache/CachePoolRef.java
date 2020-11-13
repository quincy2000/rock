package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

import org.quincy.rock.core.util.StringUtil;

/**
 * <b>缓存池引用类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2019年3月28日 下午12:00:22</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class CachePoolRef {
	/**
	 * poolRefMap。
	 */
	private static final WeakHashMap<String, CachePoolRef> poolRefMap = new WeakHashMap();

	/**
	 * 池的唯一id。
	 */
	private String id = StringUtil.getUniqueIdentifierName("p");

	/**
	 * 缓冲池大小
	 */
	private int cacheSize = Integer.MAX_VALUE;
	/**
	 * 缓冲池名称。
	 */
	private String name;
	/**
	 * 缓冲池描述。
	 */
	private String describe;
	/**
	 * 缓存值超时毫秒数(-1代表永不超时)
	 */
	private int timeout = -1;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public CachePoolRef() {
		poolRefMap.put(id, this);
	}

	/**
	 * <b>获得缓冲池唯一id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池唯一id
	 */
	public String getId() {
		return new String(id.getBytes());
	}

	/**
	 * <b>获得缓冲池名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池名称
	 */
	public String getName() {
		return name == null ? this.getClass().getSimpleName() : name;
	}

	/**
	 * <b>设置缓冲池名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 缓冲池名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <b>获得缓冲池描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池描述
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * <b>设置缓冲池描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param describe 缓冲池描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * <b>获得缓冲池大小。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池大小
	 */
	public int getCacheSize() {
		return cacheSize;
	}

	/**
	 * <b>设置缓冲池大小。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cacheSize 缓冲池大小
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/**
	 * <b>获得缓存值超时毫秒数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * -1代表永不超时。
	 * @return 缓存值超时毫秒数
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <b>设置缓存值超时毫秒数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * -1代表永不超时。
	 * @param timeout 缓存值超时毫秒数
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * <b>缓冲池是否是否是空的。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池是否是否是空的
	 */
	public boolean isEmpty() {
		return count() == 0;
	}

	/**
	 * <b>缓冲池是否已经满了。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池是否已经满了
	 */
	public boolean isFull() {
		return count() >= getCacheSize();
	}

	/**
	 * <b>返回缓存对象数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存对象数量
	 */
	public abstract int count();

	/**
	 * <b>清除缓冲中过期的值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无论清除是否成功，都不会引发异常。
	 * @param ifFull 如果满了才清除缓存
	 * @return 是否有空闲的位置可以插入新元素
	 */
	protected abstract boolean clearCache(boolean ifFull);

	/**
	 * <b>清除缓冲池中的过期对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public synchronized static void clearCachePool() {
		//copy
		List<CachePoolRef> list = new ArrayList(poolRefMap.size());
		try {
			for (CachePoolRef pool : poolRefMap.values()) {
				list.add(pool);
			}
		} catch (Exception ex) {
		}
		//clear
		for (CachePoolRef pool : list) {
			try {
				pool.clearCache(false);
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * <b>创建对象缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 缓存值
	 * @param softCache 是否是软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 * @return 对象缓存
	 */
	public static <T> ObjectCache<T> createObjectCache(T value, boolean softCache) {
		return softCache ? new SoftObjectCacheImpl<>(value) : new ObjectCacheImpl<>(value);
	}

	/**
	 * <b>创建对象缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 缓存值
	 * @param softCache 是否是软引用(在内存不够时会被强制垃圾收集以释放缓存)
	 * @param timeout 缓存值超时毫秒数
	 * @return 对象缓存
	 */
	public static <T> ObjectCache<T> createObjectCache(T value, boolean softCache, int timeout) {
		return createObjectCache(value, softCache).timeout(timeout);
	}

	/**
	 * <b>创建对象缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 缓存值
	 * @param queue 引用队列
	 * @return 对象缓存
	 */
	public static <T> ObjectCache<T> createObjectCache(T value, ReferenceQueue<? super T> queue) {
		return new SoftObjectCacheImpl<>(value, queue);
	}

	/**
	 * <b>创建对象缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 缓存值
	 * @param timeout 缓存值超时毫秒数
	 * @param queue 引用队列
	 * @return 对象缓存
	 */
	public static <T> ObjectCache<T> createObjectCache(T value, int timeout, ReferenceQueue<? super T> queue) {
		return createObjectCache(value, queue).timeout(timeout);
	}

	/**
	 * <b>获得所有缓冲池列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池列表
	 */
	public static Collection<CachePoolRef> getAllCachePool() {
		List<CachePoolRef> list = new ArrayList<>(poolRefMap.size());
		for (CachePoolRef pool : poolRefMap.values()) {
			list.add(pool);
		}
		return list;
	}

	/**
	 * <b>获得缓冲池。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 缓冲池id
	 * @return 缓冲池实例
	 */
	public static CachePoolRef getCachePool(String id) {
		return poolRefMap.get(id);
	}

	/**
	 * <b>返回缓冲池数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池数量
	 */
	public static int cpCount() {
		return poolRefMap.size();
	}
}
