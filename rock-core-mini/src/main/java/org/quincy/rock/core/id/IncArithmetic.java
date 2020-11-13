package org.quincy.rock.core.id;

/**
 * <b>ID自增长算法。</b>
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
public class IncArithmetic implements IdentifierArithmetic<Long> {
	/** 
	 * getNextId。
	 * @see org.quincy.rock.core.id.IdentifierArithmetic#getNextId(java.lang.Object)
	 */
	public Long getNextId(Long curId) {
		return curId == null ? 1 : curId.longValue() + 1;
	}
}
