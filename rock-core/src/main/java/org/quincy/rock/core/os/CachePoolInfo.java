package org.quincy.rock.core.os;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>缓冲池信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月9日 下午3:16:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CachePoolInfo extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -5643074716374000588L;

	/**
	 * 缓冲池id。
	 */
	private final String id;
	/**
	 * 缓冲池名称。
	 */
	private final String name;
	/**
	 * 缓冲池描述。
	 */
	private String describe;
	/**
	 * 缓存值超时毫秒数(-1代表永不超时)
	 */
	private int timeout;
	/**
	 * 缓冲池大小
	 */
	private int cacheSize;
	/**
	 * 缓冲池中对象个数。
	 */
	private int count;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 缓冲池id
	 * @param name 缓冲池名称
	 */
	public CachePoolInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return getId();
	}

	/**
	 * <b>获得缓冲池id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池id
	 */
	public String getId() {
		return id;
	}

	/**
	 * <b>获得缓冲池名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池名称
	 */
	public String getName() {
		return name;
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
	 * <b>获得缓冲池中对象个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池中对象个数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <b>设置缓冲池中对象个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param count 缓冲池中对象个数
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
