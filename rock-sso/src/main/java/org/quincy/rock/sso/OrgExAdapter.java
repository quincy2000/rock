package org.quincy.rock.sso;

/**
 * <b>组织机构扩展适配器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 增加了密码验证方法。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年11月28日 下午3:05:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface OrgExAdapter extends OrgAdapter {
	/**
	 * <b>检查用户口令是否正确。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 要检查的用户代码
	 * @param password 要检查的口令(未加密)
	 * @return 如果口令正确则返回用户对象，否额返回null或抛出异常
	 */
	public SSOUser checkPassword(String code, String password);
}
