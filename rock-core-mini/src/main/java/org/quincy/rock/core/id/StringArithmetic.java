package org.quincy.rock.core.id;

import org.quincy.rock.core.util.StringUtil;

/**
 * <b>ID自增长算法 for 字符串 。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2007-8-9 上午10:47:33</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class StringArithmetic implements IdentifierArithmetic<String> {
	/** 
	 * getNextId。
	 * @see org.quincy.rock.core.id.IdentifierArithmetic#getNextId(java.lang.Object)
	 */
	public final String getNextId(String curId) {
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isBlank(curId)) {
			sb.append("ID");
			sb.append(getNextId(null));
		} else {
			int pos = findDigit(curId);
			if (pos == -1) {
				sb.append(curId);
				sb.append(getNextId(null));
			} else {
				sb.append(curId.substring(0, pos));
				sb.append(getNextDigitId(curId.substring(pos)));
			}
		}
		return sb.toString();
	}

	/**
	 * <b>获得下一个数字id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param digit 数字字符串
	 * @return 下一个数字id
	 */
	protected Object getNextDigitId(String digit) {
		if (digit == null) {
			return 1000;
		} else {
			long id = Long.parseLong(digit);
			if (id == Long.MAX_VALUE)
				return 1000;
			else
				return id + 1;
		}
	}

	/**
	 * <b>发现最后一个数字开始位置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 
	 * 无。
	 * @param id 字符串id
	 * @return 最后一个数字开始位置
	 */
	private int findDigit(String id) {
		int index = -1;
		for (int i = id.length() - 1; i > 0; i--) {
			char ch = id.charAt(i);
			if (Character.isDigit(ch)) {
				index = i;
			} else
				break;
		}
		return index;
	}
}
