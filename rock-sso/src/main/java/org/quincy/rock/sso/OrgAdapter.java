package org.quincy.rock.sso;

import java.util.List;

/**
 * <b>组织机构适配器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月24日 下午4:53:16</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface OrgAdapter {
	/**
	 * <b>获得组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 组织机构标识
	 */
	public String getOrganization();

	/**
	 * <b>获得所有部门列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tree 是否返回Tree形式的部门列表
	 * @return 部门列表
	 */
	public List<SSODept> findAllDepts(boolean tree);

	/**
	 * <b>获得所有的直接下属部门。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param deptCode 部门代码,如果为null则获得顶层部门列表
	 * @return 直接下属部门列表
	 */
	public List<SSODept> findAllChildren(String deptCode);

	/**
	 * <b>通过代码获得部门信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 部门代码
	 * @return 部门信息,如果没有则返回null
	 */
	public SSODept findDept(String code);

	/**
	 * <b>获得部门中的所有用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param deptCodes 部门代码数组，如果为空则返回所有用户
	 * @return 部门中的所有用户列表
	 */
	public List<SSOUser> findAllUsers(String... deptCodes);

	/**
	 * <b>获得符合条件的所有用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param codes 用户代码列表
	 * @return 用户列表
	 */
	public List<SSOUser> findAllUsers(Iterable<String> codes);

	/**
	 * <b>通过代码获得用户信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 用户代码
	 * @return 用户信息,如果没有则返回null
	 */
	public SSOUser findUser(String code);

	/**
	 * <b>修改密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param code 用户代码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 是否修改成功
	 */
	public boolean changePassword(String code, String oldPwd, String newPwd);
}
