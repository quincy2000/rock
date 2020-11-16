package org.quincy.rock.sso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <b>角色类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月21日 下午1:45:57</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSORole extends AbstractSSOEntity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 2437641420911132499L;

	/**
	 * 角色拥有的功能集合。
	 */
	private Collection<SSOAction> actions;

	/**
	 * <b>获得角色拥有的功能集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 角色拥有的功能集合
	 */
	public Collection<SSOAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<>();
		}
		return actions;
	}

	/**
	 * <b>设置角色拥有的功能集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param actions 角色拥有的功能集合
	 */
	public void setActions(Collection<SSOAction> actions) {
		for (SSOAction action : actions) {
			addAction(action);
		}
	}

	/**
	 * <b>返回是否拥有指定的功能。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param action 功能Action
	 * @return 是否拥有指定的功能
	 */
	public boolean containsAction(SSOAction action) {
		return actions != null && actions.contains(action);
	}

	/**
	 * <b>添加一个功能Action。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param action 功能Action
	 */
	public void addAction(SSOAction action) {
		Collection<SSOAction> list = getActions();
		if (!list.contains(action)) {
			list.add(action);
		}
	}

	/**
	 * <b>移走一个功能Action。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param action 功能Action
	 */
	public void removeAction(SSOAction action) {
		getActions().remove(action);
	}

	/**
	 * <b>清空所有的功能Action。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void clearAction() {
		this.actions = null;
	}

	/**
	 * <b>是否有Action。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有Action
	 */
	public boolean hasAction() {
		return actions != null && !actions.isEmpty();
	}

	/** 
	 * clone。
	 * @see org.quincy.rock.core.vo.BaseEntity#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		SSORole me = (SSORole) super.clone();
		me.actions = null;
		if (this.hasAction()) {
			Collection<SSOAction> myActions = me.getActions();
			for (SSOAction action : this.getActions()) {
				myActions.add(action.cloneMe());
			}
		}
		return me;
	}
}
