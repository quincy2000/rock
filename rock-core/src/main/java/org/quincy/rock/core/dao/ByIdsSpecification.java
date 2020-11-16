package org.quincy.rock.core.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.quincy.rock.core.lang.Mutable;
import org.quincy.rock.core.util.CoreUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

/**
 * <b>ByIdsSpecification。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月28日 下午2:23:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ByIdsSpecification<S> implements Specification<S> {

	private static final long serialVersionUID = -6479883393585904231L;

	/**
	 * 实体信息。
	 */
	private final JpaEntityInformation<S, ?> entityInfo;
	/**
	 * 使用in关键字。
	 */
	private final boolean useIn;
	/**
	 * 参数个数。
	 */
	private final int count;
	/**
	 * 主键id参数表达式数组(id=?)。
	 */
	private ParameterExpression[] params;
	/**
	 * 主键id参数值数组。
	 */
	private List<?> idValues;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entityInfo 实体信息
	 * @param useIn 是否使用in关键字
	 * @param count 参数个数
	 */
	private ByIdsSpecification(JpaEntityInformation<S, ?> entityInfo, boolean useIn, int count) {
		this.entityInfo = entityInfo;
		this.useIn = useIn;
		this.count = count;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entityInfo 实体信息
	 * @param useIn 是否使用in关键字
	 * @param ids 主键id参数值列表
	 */
	private ByIdsSpecification(JpaEntityInformation<S, ?> entityInfo, boolean useIn, List<?> ids) {
		this(entityInfo, useIn, ids.size());
		this.idValues = ids;
	}

	/** 
	 * toPredicate。
	 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
	 */
	@Override
	public Predicate toPredicate(Root<S> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Path<?> path = root.get(entityInfo.getIdAttribute());
		String idName = entityInfo.getIdAttribute().getName() + "_";
		Predicate p = null;
		if (isParameterized()) {
			if (useIn) {
				params = new ParameterExpression[1];
				params[0] = cb.parameter(Iterable.class, idName);
				p = path.in(params[0]);
			} else {
				Class<?> idClass = path.getJavaType();
				params = new ParameterExpression[count];
				Predicate[] ps = new Predicate[count];
				for (int i = 0; i < count; i++) {
					params[i] = cb.parameter(idClass, idName + i);
					ps[i] = cb.equal(path, params[i]);
				}
				p = cb.or(ps);
			}
		} else {
			if (useIn) {
				p = path.in(idValues);
			} else {
				Predicate[] ps = new Predicate[count];
				for (int i = 0; i < count; i++) {
					ps[i] = cb.equal(path, idValues.get(i));
				}
				p = cb.or(ps);
			}
		}
		return p;
	}

	/**
	 * <b>注入参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param query Query
	 * @param ids 主键id参数值列表
	 */
	public void injectParameter(Query query, Iterable<?> ids) {
		if (isParameterized()) {
			if (useIn) {
				query.setParameter(params[0], ids);
			} else {
				int i = 0;
				for (Object id : ids) {
					query.setParameter(params[i++], id);
				}
			}
		}
	}

	/**
	 * <b>注入参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param query Query
	 * @param ids 主键id参数值数组
	 */
	public void injectParameter(Query query, Object... ids) {
		if (isParameterized()) {
			if (useIn) {
				query.setParameter(params[0], Arrays.asList(ids));
			} else {
				for (int i = 0, l = ids.length; i < l; i++) {
					query.setParameter(params[i], ids[i]);
				}
			}
		}
	}

	/**
	 * <b>返回id参数个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return id参数个数
	 */
	public int count() {
		return count;
	}

	/**
	 * <b>是参数化的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是参数化的
	 */
	public boolean isParameterized() {
		return idValues == null;
	}

	/**
	 * <b>生成where条件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param path ID字段Path
	 * @param ids ID列表
	 * @param callback 回调
	 * @return Predicate
	 */
	public static <ID> Predicate whereByIdIn(Path<?> path, Iterable<ID> ids, Consumer<Mutable<ID>> callback) {
		List list = new ArrayList<>(CoreUtil.getSize(ids, 10));
		for (ID id : ids) {
			if (callback != null) {
				Mutable<ID> mutable = Mutable.of(id);
				callback.accept(mutable);
				id = mutable.get();
			}
			if (id != null)
				list.add(id);
		}
		return path.in(list);
	}

	/**
	 * <b>生成where条件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cb CriteriaBuilder
	 * @param path ID字段Path
	 * @param ids ID列表
	 * @param callback 回调
	 * @return Predicate
	 */
	public static <ID> Predicate whereById(CriteriaBuilder cb, Path<?> path, Iterable<ID> ids,
			Consumer<Mutable<ID>> callback) {
		List<Predicate> list = new ArrayList<>(CoreUtil.getSize(ids, 10));
		for (ID id : ids) {
			if (callback != null) {
				Mutable<ID> mutable = Mutable.of(id);
				callback.accept(mutable);
				id = mutable.get();
			}
			if (id != null)
				list.add(cb.equal(path, id));
		}
		return cb.or(list.toArray(new Predicate[list.size()]));
	}

	/**
	 * <b>创建ByIdsSpecification。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entityInfo 实体信息
	 * @param count 参数个数
	 * @return ByIdsSpecification
	 */
	public static <S> ByIdsSpecification of(JpaEntityInformation<S, ?> entityInfo, int count) {
		return new ByIdsSpecification(entityInfo, false, count);
	}

	/**
	 * <b>创建ByIdsSpecification。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entityInfo 实体信息
	 * @param ids 主键id参数值列表
	 * @return ByIdsSpecification
	 */
	public static <S> ByIdsSpecification of(JpaEntityInformation<S, ?> entityInfo, List<?> ids) {
		return new ByIdsSpecification(entityInfo, false, ids);
	}

	/**
	 * <b>创建ByIdsSpecification。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用in关键字。
	 * @param entityInfo 实体信息
	 * @param count 参数个数
	 * @return ByIdsSpecification
	 */
	public static <S> ByIdsSpecification in(JpaEntityInformation<S, ?> entityInfo, int count) {
		return new ByIdsSpecification(entityInfo, true, count);
	}

	/**
	 * <b>创建ByIdsSpecification。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用in关键字。
	 * @param entityInfo 实体信息
	 * @param ids 主键id参数值列表
	 * @return ByIdsSpecification
	 */
	public static <S> ByIdsSpecification in(JpaEntityInformation<S, ?> entityInfo, List<?> ids) {
		return new ByIdsSpecification(entityInfo, true, ids);
	}
}
