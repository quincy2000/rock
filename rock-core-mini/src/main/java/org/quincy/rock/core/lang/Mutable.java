package org.quincy.rock.core.lang;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

import org.quincy.rock.core.vo.CloneMe;

/**
 * <b>可变对象。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月26日 下午3:24:31</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Mutable<T> implements Getter<T>, Setter<T>, Comparable<Object>, Serializable, CloneMe, Cloneable {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 7057843625204971360L;

	/**
	 * value。
	 */
	private T value;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public Mutable() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 值
	 */
	public Mutable(T value) {
		this.value = value;
	}

	/** 
	 * set。
	 * @see org.quincy.rock.core.lang.Setter#set(java.lang.Object)
	 */
	@Override
	public void set(T v) {
		this.value = v;
	}

	/** 
	 * get。
	 * @see org.quincy.rock.core.lang.Getter#get()
	 */
	@Override
	public T get() {
		return value;
	}

	/**
	 * <b>非空。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 非空
	 */
	public boolean isPresent() {
		return value != null;
	}

	/**
	 * <b>如果非空则回调。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param consumer 回调Consumer
	 */
	public void ifPresent(Consumer<? super T> consumer) {
		if (value != null)
			consumer.accept(value);
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
			Comparable v1 = (Comparable) value;
			Object v2 = (o instanceof Mutable) ? ((Mutable) o).value : o;
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
	 * cloneMe。
	 * @see org.quincy.rock.core.vo.CloneMe#cloneMe()
	 */
	@Override
	public final <M> M cloneMe() {
		try {
			return (M) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/** 
	* equals。
	* @see java.lang.Object#equals(java.lang.Object)
	*/
	@Override
	public boolean equals(Object obj) {
		return this == obj ? true : Objects.equals(value, (obj instanceof Mutable) ? ((Mutable) obj).value : obj);
	}

	/** 
	* hashCode。
	* @see java.lang.Object#hashCode()
	*/
	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toString(value);
	}

	/**
	 * <b>返回值的可变值实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 值
	 * @return 可变值
	 */
	public static <T> Mutable<T> of(T value) {
		return new Mutable<T>(value);
	}
}
