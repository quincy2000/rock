package org.quincy.rock.core.cache;

/**
 * <b>物主对象缓冲池。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 每个物主拥有自己独立的缓存池。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年12月5日 上午11:02:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class OwnerCachePool<V> extends CachePoolRef {
	/**
	 * 物主的缓存池大小。
	 */
	private int ownerCacheSize = Integer.MAX_VALUE;

	/**
	 * <b>获得物主的缓存池大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物主的缓存池大小
	 */
	public int getOwnerCacheSize() {
		return ownerCacheSize;
	}

	/**
	 * <b>设置物主的缓存池大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ownerCacheSize 物主的缓存池大小
	 */
	public void setOwnerCacheSize(int ownerCacheSize) {
		this.ownerCacheSize = ownerCacheSize;
	}

	/**
	 * <b>将值放入缓存池。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @param key 值的key
	 * @param value 值
	 * @return 是否成功放入缓存
	 */
	public boolean putBufferValue(Object owner, Object key, V value) {
		return putBufferValue(owner, key, value, getTimeout());
	}

	/**
	 * <b>将值放入缓存池。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @param key 值的key
	 * @param value 值
	 * @param timeout 超时毫秒数
	 * @return 是否成功放入缓存
	 */
	public abstract boolean putBufferValue(Object owner, Object key, V value, int timeout);

	/**
	 * <b>获得缓冲值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @param key 值的key
	 * @return 缓存的值
	 */
	public abstract V getBufferValue(Object owner, Object key);

	/**
	 * <b>检索缓冲值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法不会更新访问时间。
	 * @param owner 物主对象
	 * @param key 值的key
	 * @return 缓存的值
	 */
	public abstract V seekBufferValue(Object owner, Object key);
	
	/**
	 * <b>移走缓冲值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @param key 值的key
	 */
	public abstract void removeBufferValue(Object owner, Object key);

	/**
	 * <b>是否有缓存值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @param key 值的key
	 * @return 是否有缓存值
	 */
	public boolean hasBufferValue(Object owner, Object key) {
		return getBufferValue(owner, key) != null;
	}

	/**
	 * <b>移走物主。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主
	 */
	public abstract void removeOwner(Object owner);

	/**
	 * <b>返回是否存在物主。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主
	 * @return 是否存在物主
	 */
	public abstract boolean hasOwner(Object owner);

	/**
	 * <b>返回缓存的物主列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存的物主列表
	 */
	public abstract Iterable<?> owners();

	/**
	 * <b>返回缓存的切片物主列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fromIndex 开始索引
	 * @param length 切片长度(-1代表不限制长度)
	 * @return 缓存的切片物主列表
	 */
	public abstract Iterable<?> owners(int fromIndex, int length);

	/**
	 * <b>物主对象的缓冲池是否是否是空的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @return 物主对象的缓冲池是否是否是空的
	 */
	public boolean isEmpty(Object owner) {
		return count(owner) == 0;
	}

	/**
	 * <b>物主对象的缓冲池是否已经满了。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @return 物主对象的缓冲池是否已经满了
	 */
	public boolean isFull(Object owner) {
		return count(owner) >= getOwnerCacheSize();
	}

	/**
	 * <b>物主对象的缓冲池中缓存对象的数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @return 物主对象的缓冲池中缓存对象的数量
	 */
	public abstract int count(Object owner);

	/**
	 * <b>返回物主对象的缓冲池中的所有key。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @return 物主对象的缓冲池中的所有key
	 */
	public abstract Iterable<?> keys(Object owner);

	/**
	 * <b>返回物主对象的缓冲池中的切片key。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 物主对象
	 * @param fromIndex 开始索引
	 * @param length 切片长度(-1代表不限制长度)
	 * @return 物主对象的缓冲池中的切片key
	 */
	public abstract Iterable<?> keys(Object owner, int fromIndex, int length);
}
