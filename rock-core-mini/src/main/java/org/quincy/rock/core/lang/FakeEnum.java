package org.quincy.rock.core.lang;

import java.io.Serializable;

/**
 * <b>模拟枚举类型。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年1月6日 下午4:07:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class FakeEnum<E extends FakeEnum<E>> implements Comparable<E>, Serializable {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3900089140648526368L;

	/**
	 * 序号。
	 */
	private final int ordinal;
	/**
	 * 名称。
	 */
	private final String name;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ordinal 序号
	 * @param name 名称
	 */
	protected FakeEnum(int ordinal, String name) {
		this.name = name;
		this.ordinal = ordinal;
	}

	/**
	 * <b>返回序号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 序号
	 */
	public int ordinal() {
		return this.ordinal;
	}

	/**
	 * <b>名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 名称
	 */
	public String name() {
		return this.name;
	}

	/** 
	 * compareTo。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(E o) {
		return this.ordinal() - o.ordinal();
	}

	/** 
	 * hashCode。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(ordinal);
	}

	/** 
	 * equals。
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/** 
	 * clone。
	 * @see java.lang.Object#clone()
	 */
	protected final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/** 
	 * finalize。
	 * @see java.lang.Object#finalize()
	 */
	protected final void finalize() {
	}
}
