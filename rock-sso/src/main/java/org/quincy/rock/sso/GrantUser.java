package org.quincy.rock.sso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.quincy.rock.core.util.ClosureUtil;

/**
 * <b>授权用户。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月21日 下午3:18:11</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class GrantUser extends SSOUser {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4923934561576415788L;
	
	/**
	 * 组织机构标识。
	 */
	private String org;
	
	/**
	 * 是否是系统管理员。
	 */
	private boolean admin;
	/**
	 * 是否是超级管理员。
	 */
	private boolean superAdmin;
	/**
	 * 是否是SSO系统管理员。
	 */
	private boolean ssoAdmin;
	/**
	 * 用户类型(业务系统自定义)。
	 */
	private Integer userType;
	/**
	 * 是否支持数据权限。
	 */
	private boolean supportDataRole;
	
	/**
	 * 用户角色集合。
	 */
	private Collection<SSORole> roles;

	/**
	 * 用户服务范围集合。
	 */
	private Collection<SSOScope> scopes;

	/**
	 * 数据权限角色集合。
	 */
	private Collection<SSODataRole> dataRoles;

	/**
	 * 扁平的用户功能集合。
	 */
	private transient Collection<SSOAction> flatActions;

	/**
	 * 扁平的用户服务范围集合。
	 */
	private transient Collection<SSOScope> flatScopes;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public GrantUser() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param user SSOUser
	 */
	public GrantUser(SSOUser user) {
		super(user);
		if (user instanceof GrantUser) {
			GrantUser gu = (GrantUser) user;
			this.setAdmin(gu.isAdmin());
			this.setUserType(gu.getUserType());
			if (gu.roles != null)
				this.getRoles().addAll(gu.roles);
			if (gu.scopes != null)
				this.getScopes().addAll(gu.scopes);
		}
	}

	/**
	 * <b>获得组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 组织机构标识
	 */
	public String getOrg() {
		return org;
	}

	/**
	 * <b>设置组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 */
	public void setOrg(String org) {
		this.org = org;
	}

	/**
	 * <b>是否是系统管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是系统管理员
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * <b>设置是否是系统管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param admin 是否是系统管理员
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * <b>是否是超级管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是超级管理员
	 */
	public boolean isSuperAdmin() {
		return superAdmin;
	}

	/**
	 * <b>设置是否是超级管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param superAdmin 是否是超级管理员
	 */
	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	/**
	 * <b>是否是SSO系统管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是SSO系统管理员
	 */
	public boolean isSsoAdmin() {
		return ssoAdmin;
	}

	/**
	 * <b>是否是SSO系统管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ssoAdmin 是否是SSO系统管理员
	 */
	public void setSsoAdmin(boolean ssoAdmin) {
		this.ssoAdmin = ssoAdmin;
	}

	/**
	 * <b>获得用户类型(自定义)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户类型(自定义)
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * <b>设置用户类型(自定义)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userType 用户类型(自定义)
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * <b>是否支持数据权限。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否支持数据权限
	 */
	public boolean isSupportDataRole() {
		return supportDataRole;
	}

	/**
	 * <b>设置是否支持数据权限。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param supportDataRole 是否支持数据权限
	 */
	public void setSupportDataRole(boolean supportDataRole) {
		this.supportDataRole = supportDataRole;
	}

	/**
	 * <b>获得用户角色集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户角色集合
	 */
	public Collection<SSORole> getRoles() {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}

	/**
	 * <b>设置用户角色集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param roles 用户角色集合
	 */
	public void setRoles(Collection<SSORole> roles) {
		this.roles = roles;
		this.flatActions = null;
	}

	/**
	 * <b>获得用户服务范围集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户服务范围集合
	 */
	public Collection<SSOScope> getScopes() {
		if (scopes == null) {
			scopes = new ArrayList<>();
		}
		return scopes;
	}

	/**
	 * <b>设置用户服务范围集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param scopes 用户服务范围集合
	 */
	public void setScopes(Collection<SSOScope> scopes) {
		this.scopes = scopes;
		this.flatScopes = null;
	}

	/**
	 * <b>获得数据权限角色集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 数据权限角色集合
	 */
	public Collection<SSODataRole> getDataRoles() {
		if (dataRoles == null) {
			dataRoles = new ArrayList<>();
		}
		return dataRoles;
	}

	/**
	 * <b>设置数据权限角色集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dataRoles 数据权限角色集合
	 */
	public void setDataRoles(Collection<SSODataRole> dataRoles) {
		this.dataRoles = dataRoles;
	}

	/**
	 * <b>是否有赋予的角色。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 有赋予的角色
	 */
	public boolean hasRole() {
		return roles != null && !roles.isEmpty();
	}

	/**
	 * <b>是否有赋予的服务范围。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有赋予的服务范围
	 */
	public boolean hasScope() {
		return scopes != null && !scopes.isEmpty();
	}

	/**
	 * <b>是否有赋予的数据权限。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有赋予的数据权限
	 */
	public boolean hasDataRole() {
		return dataRoles != null && !dataRoles.isEmpty();
	}

	/**
	 * <b>获得用户所拥有的扁平的功能集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 扁平的用户功能集合
	 */
	protected Collection<SSOAction> flatActions() {
		if (flatActions == null) {
			Set<SSOAction> set = new TreeSet<>();
			for (SSORole r : this.getRoles()) {
				ClosureUtil.addToSet(set, r.getActions());
			}
			flatActions = set;
		}
		return flatActions;
	}

	/**
	 * <b>返回扁平的用户服务范围集合。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 扁平的用户服务范围集合。
	 */
	protected Collection<SSOScope> flatScopes() {
		if (flatScopes == null) {
			Set<SSOScope> set = new TreeSet<>();
			ClosureUtil.addToSet(set, this.getScopes());
			flatScopes = set;
		}
		return flatScopes;
	}

	/**
	 * <b>clone。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 克隆对象
	 * @throws CloneNotSupportedException
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		GrantUser me = (GrantUser) super.clone();
		me.roles = null;
		me.scopes = null;
		me.dataRoles = null;
		me.flatActions = null;
		me.flatScopes = null;

		if (this.hasRole()) {
			Collection<SSORole> myRoles = me.getRoles();
			for (SSORole role : this.getRoles()) {
				myRoles.add(role.cloneMe());
			}
		}
		if (this.hasScope()) {
			Collection<SSOScope> myScopes = me.getScopes();
			for (SSOScope scope : this.getScopes()) {
				myScopes.add(scope.cloneMe());
			}
		}
		if (this.hasDataRole()) {
			Collection<SSODataRole> myDataRoles = me.getDataRoles();
			for (SSODataRole dataRole : this.getDataRoles()) {
				myDataRoles.add(dataRole.cloneMe());
			}
		}
		return me;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.sso.sso.org.SSOUser#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("admin", "userType"));
		return cs;
	}

}
