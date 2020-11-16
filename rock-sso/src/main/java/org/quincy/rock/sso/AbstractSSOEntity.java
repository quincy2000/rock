package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

import org.quincy.rock.core.vo.BaseEntity;

/**
 * <b>AbstractSSOEntity。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月24日 下午3:09:42</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class AbstractSSOEntity extends BaseEntity<Object> {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -6258668581292671571L;

	/**
	 * id。
	 */
	private Object id;

	/**
	 * 代码。
	 */
	private String code;

	/**
	 * 名称。
	 */
	private String name;

	/**
	 * 说明。
	 */
	private String descr;

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.BaseEntity#id()
	 */
	@Override
	public Object id() {
		return id == null ? code : id;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.BaseEntity#id(java.lang.Object)
	 */
	@Override
	public AbstractSSOEntity id(Object id) {
		this.id = id;
		return this;
	}

	/**
	 * <b>获得代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * <b>设置代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * <b>获得名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>设置名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <b>获得说明。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 说明
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * <b>设置说明。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param descr 说明
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.core.vo.BaseEntity#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("code", "name"));
		return cs;
	}
}
