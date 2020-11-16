package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

/**
 * <b>通道类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月24日 下午5:32:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSORouteway extends SSONode<SSORouteway> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -3485197543784946110L;

	/**
	 * 是否是必须的。
	 */
	private boolean required;
	
	/**
	 * 通道tag。
	 */
	private String tag;

	/**
	 * 通道对应的功能。
	 */
	private SSOAction action;

	
	/**
	 * <b>是否是必须的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是必须的
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * <b>是否是必须的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param required 是否是必须的
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * <b>获得通道tag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * <b>设置通道tag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tag 通道tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * <b>获得通道对应的功能。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道对应的功能
	 */
	public SSOAction getAction() {
		return action;
	}

	/**
	 * <b>设置通道对应的功能。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param action 通道对应的功能
	 */
	public void setAction(SSOAction action) {
		this.action = action;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.sso.SSONode#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("required", "tag"));
		return cs;
	}
}
