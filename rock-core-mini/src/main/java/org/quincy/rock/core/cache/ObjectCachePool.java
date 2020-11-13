package org.quincy.rock.core.cache;

/**
 * <b>对象缓存池。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2019年3月28日 下午12:18:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class ObjectCachePool<V> extends CachePoolRef {

	/**
	 * <b>将值放入缓存池。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 值的key
	 * @param value 值
	 * @return 是否成功放入缓存
	 */
	public boolean putBufferValue(Object key, V value) {
		return this.putBufferValue(key, value, getTimeout());
	}

	/**
	 * <b>将值放入缓存池。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 值的key
	 * @param value 值
	 * @param timeout 超时毫秒数
	 * @return 是否成功放入缓存
	 */
	public abstract boolean putBufferValue(Object key, V value, int timeout);

	/**
	 * <b>获得缓冲值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 值的key
	 * @return 缓存的值
	 */
	public abstract V getBufferValue(Object key);

	/**
	 * <b>检索缓冲值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法不会更新访问时间。
	 * @param key 值的key
	 * @return 缓存的值
	 */
	public abstract V seekBufferValue(Object key);
	
	/**
	 * <b>移走缓冲值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 值的key
	 */
	public abstract void removeBufferValue(Object key);

	/**
	 * <b>是否有缓存值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 值的key
	 * @return 是否有缓存值
	 */
	public boolean hasBufferValue(Object key) {
		return getBufferValue(key) != null;
	}

	/**
	 * <b>返回缓存的所有key。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存的所有key
	 */
	public abstract Iterable<?> keys();

	/**
	 * <b>返回缓存的切片key。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fromIndex 开始索引
	 * @param length 切片长度(-1代表不限制长度)
	 * @return 缓存的切片key
	 */
	public abstract Iterable<?> keys(int fromIndex, int length);
}
