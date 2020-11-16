package org.quincy.rock.core.vo;

import java.lang.reflect.Field;

import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.ReflectUtil;
import org.springframework.util.ReflectionUtils;

/**
 * <b>值对象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 下午2:50:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public abstract class BaseVo<E extends BaseEntity<ID>, ID> extends BaseEntity<ID> {
	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.BaseEntity#id()
	 */
	@Override
	public final ID id() {
		E e = entity();
		return e == null ? null : e.id();
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id(java.lang.Object)
	 */
	@Override
	public BaseVo<E, ID> id(ID id) {
		entity().id(id);
		return this;
	}

	/**
	 * <b>设置要包裹的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体对象
	 */
	public void entity(E entity) {
		Class<E> entityType = (Class<E>) ReflectUtil.getGenericActualType(this.getClass(), 0);
		Field f = ReflectUtil.findField(this.getClass(), entityType);
		ReflectionUtils.makeAccessible(f);
		ReflectionUtils.setField(f, this, entity);
	}

	/**
	 * <b>拆解包裹。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 实体对象
	 */
	public E entity() {
		Class<E> entityType = (Class<E>) ReflectUtil.getGenericActualType(this.getClass(), 0);
		Field f = ReflectUtil.findField(this.getClass(), entityType);
		ReflectionUtils.makeAccessible(f);
		E entity = (E) ReflectionUtils.getField(f, this);
		if (entity == null) {
			entity = CoreUtil.constructor(entityType);
			ReflectionUtils.setField(f, this, entity);
		}
		return entity;
	}
}
