package org.quincy.rock.sso.util;

import org.quincy.rock.core.id.IdentifierArithmetic;

/**
 * <b>TicketIdentifierArithmetic。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月23日 下午3:52:50</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class TicketArithmetic implements IdentifierArithmetic<String> {
	/**
	 * INSTANCE。
	 */
	public static final TicketArithmetic INSTANCE = new TicketArithmetic();

	/** 
	 * getNextId。
	 * @see org.quincy.rock.core.id.IdentifierArithmetic#getNextId(java.lang.Object)
	 */
	@Override
	public String getNextId(String curId) {
		long time = System.currentTimeMillis() / 1000 - 1387200000L;
		StringBuilder sb = new StringBuilder("T");
		sb.append(Long.toString(time, 36));
		sb.append("T");
		sb.append(parseId(curId) + 1);
		return sb.toString();
	}

	//截取最后的数字部分
	private int parseId(String curId) {
		if (curId == null)
			return 1;
		int id = Integer.parseInt(curId.substring(curId.lastIndexOf('T') + 1));
		if (id == Integer.MAX_VALUE)
			id = 1;
		return id;
	}
}
