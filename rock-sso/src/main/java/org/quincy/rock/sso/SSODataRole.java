package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

/**
 * <b>SSODataRole。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月18日 上午10:03:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSODataRole extends SSONode<SSODataRole> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7522760998974542973L;
	/**
	 * 是否是叶子节点(1-叶子,2-枝干,3-空枝干)。
	 */
	private int leaf;
	/**
	 * 节点类型(业务系统自定义)。
	 */
	private int type;

	/**
	 * <b>是否是叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 1-叶子,2-枝干,3-空枝干。
	 * @return 是否是叶子节点
	 */
	public int getLeaf() {
		return leaf;
	}

	/**
	 * <b>是否是叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 1-叶子,2-枝干,3-空枝干。
	 * @param leaf 是否是叶子节点
	 */
	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	/**
	 * <b>获得节点类型(业务系统自定义)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 节点类型(业务系统自定义)
	 */
	public int getType() {
		return type;
	}

	/**
	 * <b>设置节点类型(业务系统自定义)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 节点类型(业务系统自定义)
	 */
	public void setType(int type) {
		this.type = type;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.sso.SSONode#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("leaf", "type"));
		return cs;
	}

}
