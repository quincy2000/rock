package org.quincy.rock.core.vo;

import org.quincy.rock.core.util.StringUtil;

/**
 * <b>选项。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月17日 下午2:43:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class Option<V> extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -8409263452583798536L;

	/**
	 * 空选项。
	 */
	@SuppressWarnings("serial")
	private static final Option<?> EMPTY_OPTION = new Option<Object>() {

		@Override
		public void setName(String name) {
		}

		@Override
		public void setValue(Object value) {
		}

	};

	/**
	 * 名称。
	 */
	private String name;
	/**
	 * 值。
	 */
	private V value;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public Option() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 */
	public Option(String name) {
		this.name = name;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 * @param value 值 
	 */
	public Option(String name, V value) {
		this.name = name;
		this.value = value;
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
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return name;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id(java.lang.Object)
	 */
	@Override
	public Option<V> id(String id) {
		this.setName(id);
		return this;
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
	 * <b>获得值 。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 值 
	 */
	public V getValue() {
		return value;
	}

	/**
	 * <b>设置值 。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 值 
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{name:");
		sb.append(name);
		sb.append(",value:");
		sb.append(value);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * <b>是否有值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有值
	 */
	public boolean hasValue() {
		return this.value != null;
	}

	/**
	 * <b>空选项。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是空选项
	 */
	public boolean emptyOption() {
		return StringUtil.isBlank(this.name);
	}

	/**
	 * <b>返回空选项实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 空选项实例
	 */
	public static Option<?> EMPTY_OPTION() {
		return EMPTY_OPTION;
	}
}
