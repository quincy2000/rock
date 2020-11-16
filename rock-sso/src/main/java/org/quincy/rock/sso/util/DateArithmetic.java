package org.quincy.rock.sso.util;

import java.text.SimpleDateFormat;

import org.quincy.rock.core.id.StringArithmetic;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>依据日期字符串的ID生成算法。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月23日 下午1:33:50</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DateArithmetic extends StringArithmetic {
	/**
	 * DATE_PATTERN。
	 */
	private static final SimpleDateFormat DATE_PATTERN = new SimpleDateFormat("yy-MM-dd");

	/** 
	 * getNextDigitId。
	 * @see org.quincy.rock.core.id.StringArithmetic#getNextDigitId(java.lang.String)
	 */
	@Override
	protected final Object getNextDigitId(String digit) {
		String today = DATE_PATTERN.format(DateUtil.date());
		StringBuilder sb = new StringBuilder(today);
		if (digit == null) {
			sb.append(getNextDigit(0L));
		} else {
			String day = digit.substring(0, 6);
			if (day.equals(today)) {
				String seq = digit.substring(6);
				sb.append(getNextDigit(StringUtil.isBlank(seq) ? 0L : Long.valueOf(seq)));
			} else {
				sb.append(getNextDigit(0L));
			}
		}
		return sb.toString();
	}

	/**
	 * <b>获得下一个数字。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param digit 当前数字
	 * @return 下一个数字
	 */
	protected Object getNextDigit(Long digit) {
		return (digit == null || digit == Long.MAX_VALUE) ? 1 : (digit + 1);
	}
}
