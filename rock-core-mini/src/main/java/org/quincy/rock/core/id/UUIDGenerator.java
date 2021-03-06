package org.quincy.rock.core.id;

import java.util.UUID;

/**
 * <b>UUIDGenerator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2009-4-22 下午10:08:32</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class UUIDGenerator implements IdentifierGenerator<Object, String> {

	/** 
	 * generate。
	 * @see org.quincy.rock.core.id.IdentifierGenerator#generate(java.lang.Object)
	 */
	public String generate(Object key) {
		return UUID.randomUUID().toString();
	}
}
