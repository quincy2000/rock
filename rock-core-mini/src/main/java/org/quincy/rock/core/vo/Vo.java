package org.quincy.rock.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.quincy.rock.core.exception.ConstructorException;
import org.quincy.rock.core.exception.NotImplementedException;
import org.quincy.rock.core.util.JsonUtil;

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
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public abstract class Vo<ID> implements Serializable, Comparable<Object>, CloneMe, Cloneable {
	/**
	 * id=0是否代表新值。
	 */
	public static boolean ZERO_IS_NEW = true;

	/**
	 * <b>返回id主键。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return id主键
	 */
	public abstract ID id();

	/**
	 * <b>设置id主键。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id id主键
	 * @return this指针
	 */
	public Vo<ID> id(ID id) {
		throw new NotImplementedException("id()");
	}

	/** 
	 * hashCode。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		Object v = id();
		return Objects.hashCode(v);
	}

	/** 
	 * equals。
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		} else if (anObject != null) {
			if (anObject.getClass() == this.getClass()) {
				Object v = ((Vo) anObject).id();
				return Objects.equals(this.id(), v);
			} else if (loose()) {
				Object v = (anObject instanceof Vo) ? ((Vo) anObject).id() : anObject;
				return Objects.equals(this.id(), v);
			}
		}
		return false;
	}

	/** 
	 * compareTo。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		} else {
			Comparable v1 = (Comparable) id();
			Object v2;
			if (loose())
				v2 = (o instanceof Vo) ? ((Vo) o).id() : o;
			else if (o.getClass() == this.getClass())
				v2 = ((Vo) o).id();
			else
				throw new IllegalArgumentException(o.getClass().getName());
			if (v1 == v2) {
				return 0;
			} else if (v1 == null) {
				return -1;
			} else if (v2 == null) {
				return 1;
			} else {
				return v1.compareTo(v2);
			}
		}
	}

	/** 
	 * clone。
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * <b>克隆。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 对象副本
	 */
	public final <M> M cloneMe() {
		try {
			return (M) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * <b>toString。</b>
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Class<?> clazz;
		try {
			clazz = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
		} catch (ClassNotFoundException e) {
			clazz = null;
		}
		if (clazz != null) {
			return JsonUtil.toJson(this);
		} else {
			return String.valueOf(id());
		}
	}

	/**
	 * <b>是否是新的值对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * id未赋值表示是一个新值对象。
	 * @return 是否是新的值对象
	 */
	public boolean noId() {
		Object id = this.id();
		return (id == null) ? true
				: (id instanceof Number)
						? ((ZERO_IS_NEW) ? ((Number) id).longValue() <= 0 : ((Number) id).longValue() < 0)
						: id.toString().length() == 0;
	}

	/**
	 * <b>是否进行宽松比较。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 影响equals方法和compareTo方法。
	 * 严格比较时双方的类型必须严格一致。
	 * 宽松比较时不要求双方的类型必须严格一致。
	 * @return 是否进行宽松比较
	 */
	public boolean loose() {
		return false;
	}

	/**
	 * <b>生成对象列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cls 对象类型
	 * @param ids id列表
	 * @return 对象列表
	 */
	public static <ID, T extends Vo<ID>> List<T> asList(Class<T> cls, ID... ids) {
		List<T> list = new ArrayList<>(ids.length);
		try {
			for (ID id : ids) {
				T t = cls.newInstance();
				t.id(id);
				list.add(t);
			}
		} catch (Exception e) {
			throw new ConstructorException(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * <b>值对象是否有一个有效的id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vo 值对象
	 * @return 值对象是否有一个有效的id
	 */
	public static <T extends Vo> boolean hasId(T vo) {
		return vo != null && !vo.noId();
	}
}
