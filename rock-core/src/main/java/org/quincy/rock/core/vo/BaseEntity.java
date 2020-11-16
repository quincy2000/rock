package org.quincy.rock.core.vo;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * <b>实体基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月12日 上午10:55:03</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "serial" })
public abstract class BaseEntity<ID> extends Vo<ID> {

	/**
	 * <b>返回业务主键。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务主键
	 */
	public Object pk() {
		return id();
	}

	/** 
	 * toString。
	 * @see org.quincy.rock.core.vo.Vo#toString()
	 */
	@Override
	public String toString() {
		Collection<String> propNames = propertyNames4ToString();
		if (propNames == null || propNames.isEmpty())
			propNames = Arrays.asList(propertyNames());
		StringBuilder buffer = new StringBuilder();
		buffer.append("{pk=(");
		buffer.append(pk());
		for (String propName : propNames) {
			buffer.append("),");
			Object propValue;
			try {
				propValue = beanWrapper().getPropertyValue(propName);
			} catch (Exception e) {
				propValue = e.getMessage();
			}
			buffer.append(propName);
			buffer.append("=(");
			buffer.append(propValue);
		}
		buffer.append(")}");
		return buffer.toString();
	}

	/**
	 * <b>返回bean属性名称数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 属性名称数组
	 */
	public final String[] propertyNames() {
		PropertyDescriptor[] pds = beanWrapper().getPropertyDescriptors();
		String[] propertyNames = new String[pds.length - 1];
		for (int i = 0, j = 0; i < pds.length; i++) {
			if ("class".equals(pds[i].getName()))
				j++;
			else
				propertyNames[i - j] = pds[i].getName();
		}
		return propertyNames;
	}

	/**
	 * 返回属性名称数组 for toString
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 属性名称数组
	 */
	protected Collection<String> propertyNames4ToString() {
		return new HashSet<>();
	}

	/**
	 * <b>beanWrapper。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return beanWrapper
	 */
	public final synchronized BeanWrapper beanWrapper() {
		if (beanWrapper == null) {
			beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(this);
		}
		return beanWrapper;
	}

	private transient BeanWrapper beanWrapper;
}
