package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

/**
 * <b>用户类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月24日 下午3:26:46</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOUser extends AbstractSSOEntity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7520988358544774112L;

	/**
	 * 部门代码。
	 */
	private String deptCode;

	/**
	 * 部门名称。
	 */
	private String deptName;

	/**
	 * 直接领导代码。
	 */
	private String leaderCode;

	/**
	 * 直接领导名称。
	 */
	private String leaderName;
	/**
	 * 头衔。
	 */
	private String honor;
	/**
	 * tag。
	 */
	private String tag;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public SSOUser() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param user SSOUser
	 */
	public SSOUser(SSOUser user) {
		this.setCode(user.getCode());
		this.setName(user.getName());
		this.setDescr(user.getDescr());
		this.setDeptCode(user.getDeptCode());
		this.setDeptName(user.getDeptName());
		this.setLeaderCode(user.getLeaderCode());
		this.setLeaderName(user.getLeaderName());
		this.setHonor(user.getHonor());
		this.setTag(user.getTag());
	}

	/**
	 * <b>获得头衔。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 头衔
	 */
	public String getHonor() {
		return honor;
	}

	/**
	 * <b>设置头衔。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param honor 头衔
	 */
	public void setHonor(String honor) {
		this.honor = honor;
	}

	/**
	 * <b>获得部门代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 部门代码
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * <b>设置部门代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param deptCode 部门代码
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * <b>获得部门名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * <b>设置部门名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param deptName 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * <b>获得直接领导代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 直接领导代码
	 */
	public String getLeaderCode() {
		return leaderCode;
	}

	/**
	 * <b>设置直接领导代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param leaderCode 直接领导代码
	 */
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}

	/**
	 * <b>获得直接领导名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 直接领导名称
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * <b>设置直接领导名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param leaderName 直接领导名称
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

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.core.sso.AbstractSSOEntity#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("deptCode", "deptName"));
		return cs;
	}
}
