package org.quincy.rock.core.cache;

import java.io.Serializable;

/**
 * <b>计数器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>王昆山</td><td>2013-6-8 上午10:44:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author 王昆山
 * @since 1.0
 */
public class Counter<T> implements Serializable {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 7125367760833343192L;

	/**
	 * 对象值。
	 */
	protected T value;

	/**
	 * 计数。
	 */
	private int count = 0;

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 对象值
	 */
	public Counter(T value) {
		this.value = value;
	}

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param counter 计数器
	 */
	public Counter(Counter<T> counter) {
		this.value = counter.value;
		this.count = counter.count;
	}

	/**
	 * <b>增加计数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param 对象值
	 */
	public T inc() {
		++count;
		return value;
	}

	/**
	 * <b>减少计数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否计数变为0
	 */
	public boolean dec() {
		return --count == 0;
	}

	/**
	 * <b>计数是否为0。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 计数是否为0
	 */
	public boolean isZero() {
		return count == 0;
	}

	/**
	 * <b>获得计数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 计数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <b>获得对象值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 对象值
	 */
	public T getValue() {
		return value;
	}
}
