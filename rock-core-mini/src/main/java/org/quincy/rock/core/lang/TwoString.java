package org.quincy.rock.core.lang;

/**
 * <b>拼接字符串。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月27日 下午11:48:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class TwoString implements CharSequence {
	/**
	 * left。
	 */
	private CharSequence left;
	/**
	 * right。
	 */
	private CharSequence right;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param left 左边字符串
	 * @param right 右边字符串
	 */
	public TwoString(CharSequence left, CharSequence right) {
		this.left = left;
		this.right = right;
	}

	/** 
	 * length。
	 * @see java.lang.CharSequence#length()
	 */
	@Override
	public int length() {
		return left.length() + right.length();
	}

	/** 
	 * charAt。
	 * @see java.lang.CharSequence#charAt(int)
	 */
	@Override
	public char charAt(int index) {
		return index < left.length() ? left.charAt(index) : right.charAt(index - left.length());
	}

	/** 
	 * subSequence。
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return new SubString(this, start, end);
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return left.toString() + right.toString();
	}
}
