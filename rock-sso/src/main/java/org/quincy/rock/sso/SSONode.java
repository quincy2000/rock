package org.quincy.rock.sso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.quincy.rock.core.util.HasChildren;
import org.quincy.rock.core.util.HasParent;

/**
 * <b>SSO节点类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月18日 上午10:14:04</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class SSONode<T extends SSONode<T>> extends AbstractSSOEntity implements HasParent<T>, HasChildren<T> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -5808532960139740938L;

	/**
	 * 节点名称。
	 */
	private String nodeName;
	/**
	 * 级别(顶级节点为1,依次类推)。
	 */
	private Integer level;
	/**
	 * 父节点。
	 */
	private T parent;
	/**
	 * 子节点列表。
	 */
	private Collection<T> children;

	/**
	 * <b>获得节点名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 节点名称
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * <b>设置节点名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param nodeName 节点名称
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/** 
	 * getLevel。
	 * @see org.quincy.rock.core.util.HasLevel#getLevel()
	 */
	@Override
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * <b>设置级别(顶级节点为1,依次类推)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param level 级别(顶级节点为1,依次类推)
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/** 
	 * getParent。
	 * @see org.quincy.rock.core.util.HasParent#getParent()
	 */
	@Override
	public T getParent() {
		return parent;
	}

	/**
	 * <b>设置父节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param parent 父节点
	 */
	public void setParent(T parent) {
		this.parent = parent;
	}

	/**
	 * <b>替换父对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param parent 父对象
	 */
	public void replaceParent(T parent) {
		if (this.parent != parent) {
			if (this.parent != null)
				this.parent.removeChild((T) this);
			if (parent != null)
				parent.addChild((T) this, false);
			this.parent = parent;
		}
	}

	/** 
	 * hasParent。
	 * @see org.quincy.rock.core.util.HasParent#hasParent()
	 */
	@Override
	public boolean hasParent() {
		return parent != null;
	}

	/** 
	 * getChildren。
	 * @see org.quincy.rock.core.util.HasChildren#getChildren()
	 */
	@Override
	public Collection<T> getChildren() {
		if (children == null) {
			children = new ArrayList<>();
		}
		return children;
	}

	/**
	 * <b>设置子节点列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param children 子节点列表
	 */
	public void setChildren(Collection<T> children) {
		this.children = children;
	}

	/** 
	 * hasChildren。
	 * @see org.quincy.rock.core.util.HasChildren#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	/**
	 * <b>返回是否有该子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param node 子节点
	 * @return 返回是否有该子节点
	 */
	public boolean containsChild(T node) {
		return children != null && children.contains(node);
	}

	/**
	 * <b>添加一个子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param node 节点对象
	 * @param setParent 是否设置父关系
	 */
	public void addChild(T node, boolean setParent) {
		Collection<T> list = getChildren();
		if (!list.contains(node)) {
			list.add(node);
			if (setParent)
				node.replaceParent((T) this);
		}
	}

	/**
	 * <b>移走一个子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param node 子节点
	 */
	public void removeChild(T node) {
		SSONode<T> child = (SSONode<T>) node;
		Collection<T> list = getChildren();
		if (list.contains(node)) {
			list.remove(node);
			if (child.parent == this)
				child.parent = null;
		}
	}

	/**
	 * <b>清除所有的子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void clearChild() {
		if (hasChildren()) {
			for (T child : getChildren()) {
				removeChild(child);
			}
		}
	}

	/** 
	 * clone。
	 * @see org.quincy.rock.core.vo.Vo#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		SSONode<T> me = (SSONode<T>) super.clone();
		me.children = null;
		me.parent = null;
		if (this.hasChildren()) {
			//克隆子对象
			Collection<T> myChildren = me.getChildren();
			for (T child : this.getChildren()) {
				SSONode<T> myChild = child.cloneMe();
				myChild.parent = (T) me;
				myChildren.add(child);
			}
		}
		return me;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.core.vo.BaseEntity#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("nodeName", "level"));
		return cs;
	}
}
