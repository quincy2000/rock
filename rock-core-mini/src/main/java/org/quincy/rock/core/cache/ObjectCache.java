package org.quincy.rock.core.cache;

/**
 * <b>对象缓冲。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 用来封装要缓存的对象值。
 * 不要缓存空值。
 * 当缓存值超时或被垃圾收集后会失效。
 * 如果缓存值是失效的则get方法返回null。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年12月5日 上午11:01:43</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface ObjectCache<T> extends HasAccessTime {
	/**
	 * <b>获得缓存值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存值,如果缓存值无效则返回null
	 */
	public T get();

	/**
	 * <b>获得缓存值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不会更新访问时间。
	 * @return 缓存值(即使缓存值过期也会返回有效的缓存值)
	 */
	public T seek();
	
	/**
	 * <b>返回缓存值超时毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存值超时毫秒数
	 */
	public int timeout();

	/**
	 * <b>设置缓存值超时毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timeout 缓存值超时毫秒数
	 */
	public ObjectCache<T> timeout(int timeout);

	/**
	 * <b>返回缓存值是否有效。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存值是否有效
	 */
	public boolean isValid();

	/**
	 * <b>使缓存值无效。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void invalidate();
}
