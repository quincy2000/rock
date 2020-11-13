package org.quincy.rock.core.lang;

/**
 * <b>子串。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月28日 下午11:07:46</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SubString implements CharSequence {
	/**
	 * str。
	 */
	private final CharSequence str;
	/**
	 * 开始索引。
	 */
	private final int start;
	/**
	 * 结束索引。
	 */
	private final int end;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @param start 开始索引
	 */
	public SubString(CharSequence str, int start) {
		this(str, start, -1);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param str 字符串
	 * @param start 开始索引
	 * @param end 结束索引
	 */
	public SubString(CharSequence str, int start, int end) {
		this.str = str;
		this.start = start;
		this.end = end;
	}

	/** 
	 * length。
	 * @see java.lang.CharSequence#length()
	 */
	@Override
	public int length() {
		return (end == -1 ? str.length() : end) - start;
	}

	/** 
	 * charAt。
	 * @see java.lang.CharSequence#charAt(int)
	 */
	@Override
	public char charAt(int index) {
		return str.charAt(start + index);
	}

	/** 
	 * subSequence。
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		start += this.start;
		if (end != -1)
			end += this.start;
		return new SubString(str, start, end);
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return str.subSequence(start, end == -1 ? str.length() : end).toString();
	}
}
