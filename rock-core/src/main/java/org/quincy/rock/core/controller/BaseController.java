package org.quincy.rock.core.controller;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quincy.rock.core.exception.ConstructorException;
import org.quincy.rock.core.service.Service;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.DaoUtil;
import org.quincy.rock.core.vo.BaseEntity;
import org.quincy.rock.core.vo.BaseVo;
import org.quincy.rock.core.vo.PageSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;

/**
 * <b>MVC控制器基接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * BaseController和Spring自动注入机制相结合可以实现模型Service的自动装配。
 * 唯一的限制条件是模型Service必须也使用了和控制器相同的BaseEntity实现类，并且保证容器中符合该条件的模型Service有且只有一个。<br>
 * BaseController将DTO(数据传输值对象)进行了分离，模型层(也包含DAO层)使用BaseEntity实现类，控制层使用BaseVo实现类。
 * 这样可以防止视图层的传输需求污染模型层和DAO层。<br>
 * BaseController类提供了方法将BaseEntity封装成BaseVo实现类。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月12日 下午11:45:04</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class BaseController<E extends BaseEntity<ID>, T extends BaseVo<E, ID>, ID> {

	public static final String ORDER_KEY = "_order";
	public static final String PAGE_KEY = "_page";
	public static final String PAGE_SIZE_KEY = "_pageSize";
	public static final String ID_KEY = "id";

	/**
	 * service。
	 */
	@Autowired
	private Service<E, ID> service;

	/**
	 * voClass。
	 */
	private transient Class<T> voClass;

	/**
	 * <b>获得Service。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Service
	 */
	protected <S extends Service<E, ID>> S getService() {
		return (S) this.service;
	}

	/**
	 * <b>将id从字符串转换成实际类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 字符串id
	 * @return id
	 */
	protected abstract ID toId(String id);

	//
	private synchronized Class<T> voClass() {
		if (this.voClass == null) {
			TypeVariable type = BaseController.class.getTypeParameters()[1];
			Map<TypeVariable, Type> map = GenericTypeResolver.getTypeVariableMap(this.getClass());
			this.voClass = (Class<T>) map.get(type);
		}
		return this.voClass;
	}

	/**
	 * <b>将实体包裹成值对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体对象
	 * @return 值对象
	 */
	protected T wrap(E entity) {
		if (entity == null)
			return null;
		try {
			T vo = voClass().newInstance();
			vo.entity(entity);
			return vo;
		} catch (Exception e) {
			throw new ConstructorException(e.getMessage(), e);
		}
	}

	/**
	 * <b>将实体列表包裹成值对象列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param list 实体列表
	 * @return 值对象列表
	 */
	protected final List<T> wrap(List<E> list) {
		List<T> voList = new ArrayList<>(list.size());
		for (E entity : list) {
			voList.add(wrap(entity));
		}
		return voList;
	}

	/**
	 * <b>toPageSet。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page
	 * @return
	 */
	protected final PageSet<T> toPageSet(Page<E> page) {
		return DaoUtil.toPageSet(page, (ele) -> wrap(ele));
	}

	/**
	 * <b>toIds。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids
	 * @return
	 */
	protected final List<ID> toIds(String ids) {
		return CoreUtil.toList(ids, (ele) -> toId(ele.toString()));
	}
}
