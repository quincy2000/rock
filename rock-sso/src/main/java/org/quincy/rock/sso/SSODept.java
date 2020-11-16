package org.quincy.rock.sso;

/**
 * <b>SSODept。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月24日 下午2:58:29</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSODept extends SSONode<SSODept> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -708970391569760553L;

	/**
	 * 部门领导代码。
	 */
	private String leaderCode;

	/**
	 * 部门领导名称。
	 */
	private String leaderName;
	/**
	 * tag。
	 */
	private String tag;

	/**
	 * <b>获得部门领导代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 部门领导代码
	 */
	public String getLeaderCode() {
		return leaderCode;
	}

	/**
	 * <b>设置部门领导代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param leaderCode 部门领导代码
	 */
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}

	/**
	 * <b>获得部门领导名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 部门领导名称
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * <b>设置部门领导名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param leaderName 部门领导名称
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * <b>getTag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * <b>setTag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
}
