package org.quincy.rock.core.dao;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.quincy.rock.core.util.CoreUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * <b>手动Jpa数据访问对象支持类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月26日 下午12:11:57</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ManuaJpaDaoSupport {
	/**
	 * EntityManager。
	 */
	private final EntityManager em;

	/**
	 * sql仓库。
	 */
	private JpaNamedSqlStorage namedSqlStorage;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param em EntityManager
	 * @param namedSqlStorage sql仓库
	 */
	public ManuaJpaDaoSupport(EntityManager em, @Nullable JpaNamedSqlStorage namedSqlStorage) {
		this.em = em;
		this.namedSqlStorage = namedSqlStorage;
	}

	/**
	 * <b>返回实体管理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return EntityManager
	 */
	public EntityManager em() {
		return em;
	}

	/**
	 * <b>返回实体信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @return 实体信息
	 */
	public <S> JpaEntityInformation<S, ?> entityInfo(Class<S> type) {
		return JpaEntityInformationSupport.getEntityInformation(type, em);
	}

	/**
	 * <b>获得命名sql。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 * @param arguments 格式化参数
	 * @return sql
	 * @see MessageFormat
	 */
	public String getNamedSql(String name, Object... arguments) {
		return namedSqlStorage == null ? null : namedSqlStorage.getNamedSql(name, arguments);
	}

	/**
	 * <b>createQuery。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jpql 查询语句
	 * @return Query
	 */
	public Query createQuery(String jpql) {
		return em.createQuery(jpql);
	}

	/**
	 * <b>createQuery。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jpql 查询语句
	 * @param rtnClass 查询语句的返回值类型
	 * @return TypedQuery
	 */
	public <T> TypedQuery<T> createQuery(String jpql, Class<T> rtnClass) {
		return em.createQuery(jpql, rtnClass);
	}

	/**
	 * <b>插入一个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 * @return 实体
	 */
	public <S> S insert(S entity) {
		em.persist(entity);
		return entity;
	}

	/**
	 * <b>更新一个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 * @return 实体
	 */
	public <S> S update(S entity) {
		return em.merge(entity);
	}

	/**
	 * <b>刷新。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void flush() {
		em.flush();
	}

	/**
	 * <b>更新实体指定属性内容。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param id 要更新的实体id
	 * @param partMap 更新数据
	 * @return 更新后的实体
	 */
	public <S> S updateSpecific(Class<S> type, Object id, Map<String, Object> partMap) {
		String idName = entityInfo(type).getIdAttribute().getName();
		S entity = findById(type, id).get();
		BeanWrapper bean = PropertyAccessorFactory.forBeanPropertyAccess(entity);
		for (String key : partMap.keySet()) {
			if (!key.equals(idName)) {
				Object newValue = partMap.get(key);
				boolean canWrite = bean.isWritableProperty(key);
				if (canWrite && bean.isReadableProperty(key)) {
					Object oldValue = bean.getPropertyValue(key);
					if (ObjectUtils.nullSafeEquals(newValue, oldValue)) {
						canWrite = false;
					}
				}
				if (canWrite) {
					bean.setPropertyValue(key, newValue);
				}
			}
		}
		return update(entity);
	}

	/**
	 * <b>删除指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 */
	public <S> void delete(S entity) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	/**
	 * <b>删除指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param id 实体id
	 */
	public <S> void deleteById(Class<S> type, Object id) {
		S entity = findById(type, id).get();
		this.delete(entity);
	}

	/**
	 * <b>删除指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param ids 主键id列表
	 */
	public <S> void deleteAllById(Class<S> type, Iterable<?> ids) {
		for (S entity : findAllById(type, ids, Sort.unsorted())) {
			this.delete(entity);
		}
	}

	/**
	 * <b>删除所有的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 */
	public <S> void deleteAll(Class<S> type) {
		for (S entity : findAll(type)) {
			delete(entity);
		}
	}

	/**
	 * <b>删除所有指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一条sql语句删除。
	 * @param entities 实体列表
	 * @return 删除数据条数
	 */
	public <S> int deleteInBatch(Iterable<S> entities) {
		Iterator<S> it = entities.iterator();
		if (!it.hasNext())
			return 0;
		int i = 1;
		S s = it.next();
		Class type = s.getClass();
		StringBuilder sql = new StringBuilder("delete from ");
		sql.append(entityInfo(type).getEntityName());
		sql.append(" a where a=?");
		sql.append(i++);
		for (; it.hasNext(); it.next()) {
			sql.append(" or a=?");
			sql.append(i++);
		}
		Query query = em.createQuery(sql.toString());
		for (i = 1, it = entities.iterator(); it.hasNext(); i++) {
			query.setParameter(i, it.next());
		}
		//
		return query.executeUpdate();
	}

	/**
	 * <b>删除所有指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一条sql语句删除。
	 * @param type 实体类型
	 * @param ids id列表
	 * @return 删除数据条数
	 */
	public <S> int deleteInBatchById(Class<S> type, Iterable<?> ids) {
		ByIdsSpecification spec = ByIdsSpecification.of(entityInfo(type), CoreUtil.getSize(ids));
		Query query = getDeleteQuery(type, spec);
		spec.injectParameter(query, ids);
		return query.executeUpdate();
	}

	/**
	 * <b>根据条件删除所有的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一条sql语句删除。
	 * @param type 实体类型
	 * @param spec 删除条件
	 * @return 删除数据条数
	 */
	public <S> int deleteAllInBatch(Class<S> type, @Nullable Specification<S> spec) {
		Query query = getDeleteQuery(type, spec);
		return query.executeUpdate();
	}

	/**
	 * <b>返回总数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @return 总数据条数
	 */
	public <S> long count(Class<S> type) {
		TypedQuery<Long> query = getCountQuery(type, null);
		return query.getSingleResult();
	}

	/**
	 * <b>查询指定id的实体是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param ids 主键id
	 * @return 指定id的实体是否存在
	 */
	public <S> boolean existsById(Class<S> type, Object id) {
		ByIdsSpecification<S> spec = ByIdsSpecification.of(entityInfo(type), 1);
		TypedQuery<Integer> query = getExistsQuery(type, spec);
		spec.injectParameter(query, id);
		return query.getSingleResult() != null;
	}

	/**
	 * <b>查询指定id的实体是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只要有一个id存在即返回true。
	 * @param type 实体类型
	 * @param ids 主键列表
	 * @return 指定id的实体是否存在
	 */
	public <S> boolean existsById(Class<S> type, Iterable<?> ids) {
		ByIdsSpecification<S> spec = ByIdsSpecification.of(entityInfo(type), CoreUtil.getSize(ids));
		TypedQuery<Integer> query = getExistsQuery(type, spec);
		spec.injectParameter(query, ids);
		return query.getSingleResult() != null;
	}

	/**
	 * <b>查询指定id的实体是否全部存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param ids 主键列表
	 * @return 指定id的实体是否全部存在
	 */
	public <S> boolean existsAllById(Class<S> type, Iterable<?> ids) {
		ByIdsSpecification<S> spec = ByIdsSpecification.of(entityInfo(type), CoreUtil.getSize(ids));
		TypedQuery<Long> query = getCountQuery(type, spec);
		spec.injectParameter(query, ids);
		return query.getSingleResult() == spec.count();
	}

	/**
	 * <b>查询指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param id 主键id
	 * @return 实体
	 */
	public <S> Optional<S> findById(Class<S> type, Object id) {
		return Optional.ofNullable(em.find(type, id));
	}

	/**
	 * <b>查询指定id的多个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param ids 主键id列表
	 * @param sort 排序规则
	 * @return 实体列表
	 */
	public <S> Iterable<S> findAllById(Class<S> type, Iterable<?> ids, Sort sort) {
		ByIdsSpecification<S> spec = ByIdsSpecification.of(entityInfo(type), CoreUtil.getSize(ids));
		TypedQuery<S> query = getQuery(type, spec, sort);
		spec.injectParameter(query, ids);
		return query.getResultList();
	}

	/**
	 * <b>查询所有数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @return 实体列表
	 */
	public <S> Iterable<S> findAll(Class<S> type) {
		return getQuery(type, null, Sort.unsorted()).getResultList();
	}

	/**
	 * <b>查询所有数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param sort 排序规则
	 * @return 实体列表
	 */
	public <S> Iterable<S> findAll(Class<S> type, Sort sort) {
		return getQuery(type, null, sort).getResultList();
	}

	/**
	 * <b>查询所有数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param pageable 分页条件
	 * @return 实体列表
	 */
	public <S> Iterable<S> findAll(Class<S> type, Pageable pageable) {
		return getQuery(type, null, pageable).getResultList();
	}

	/**
	 * <b>查询符合条件的数据是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param type 实体类型 
	 * @param spec 查询条件
	 * @return 符合条件的数据是否存在
	 */
	public <S> boolean exists(Class<S> type, @Nullable Specification<S> spec) {
		TypedQuery<Integer> query = getExistsQuery(type, spec);
		return query.getSingleResult() != null;
	}

	/**
	 * <b>count。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param spec 查询条件
	 * @return 符合条件的数据条数
	 */
	public <S> long count(Class<S> type, @Nullable Specification<S> spec) {
		TypedQuery<Long> query = getCountQuery(type, spec);
		return query.getSingleResult();
	}

	/**
	 * <b>查询所有符合条件的数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param spec 查询条件
	 * @return 实体列表
	 */
	public <S> Iterable<S> findAll(Class<S> type, @Nullable Specification<S> spec) {
		return getQuery(type, spec, Sort.unsorted()).getResultList();
	}

	/**
	 * <b>查询所有符合条件的数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param spec 查询条件
	 * @param sort 排序规则
	 * @return 实体列表
	 */
	public <S> Iterable<S> findAll(Class<S> type, @Nullable Specification<S> spec, Sort sort) {
		TypedQuery<S> query = getQuery(type, spec, sort);
		return query.getResultList();
	}

	/**
	 * <b>查询所有符合条件的数据(分页查询)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param spec 查询条件
	 * @param pageable 分页条件
	 * @return 一页实体列表
	 */
	public <S> Page<S> findAll(Class<S> type, @Nullable Specification<S> spec, Pageable pageable) {
		TypedQuery<S> query = getQuery(type, spec, pageable);
		return pageable.isPaged()
				? PageableExecutionUtils.getPage(query.getResultList(), pageable,
						() -> getCountQuery(type, spec).getSingleResult())
				: new PageImpl<S>(query.getResultList());
	}

	/**
	 * <b>查询一个单一结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 实体类型
	 * @param spec 查询条件
	 * @return 单一结果值
	 */
	public <S> Optional<S> findOne(Class<S> type, @Nullable Specification<S> spec) {
		TypedQuery<S> query = getQuery(type, spec, Sort.unsorted());
		return Optional.ofNullable(query.getSingleResult());
	}

	private <S> Query getDeleteQuery(Class<S> type, @Nullable Specification<S> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaDelete<S> query = builder.createCriteriaDelete(type);
		Root<S> root = query.from(type);
		if (spec != null) {
			Predicate predicate = spec.toPredicate(root, null, builder);
			if (predicate != null) {
				query.where(predicate);
			}
		}
		return em.createQuery(query);
	}

	private <S> TypedQuery<S> getQuery(Class<S> type, @Nullable Specification<S> spec, Pageable pageable) {
		TypedQuery<S> query = getQuery(type, spec, pageable.getSort());
		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		return query;
	}

	private <S> TypedQuery<S> getQuery(Class<S> type, @Nullable Specification<S> spec, @Nullable Sort sort) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<S> query = builder.createQuery(type);
		Root<S> root = query.from(type);
		if (spec != null) {
			Predicate predicate = spec.toPredicate(root, query, builder);
			if (predicate != null) {
				query.where(predicate);
			}
		}
		query.select(root);
		if (sort != null && sort.isSorted()) {
			query.orderBy(QueryUtils.toOrders(sort, root, builder));
		}
		return em.createQuery(query);
	}

	private <S> TypedQuery<Long> getCountQuery(Class<S> type, @Nullable Specification<S> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<S> root = query.from(type);
		if (spec != null) {
			Predicate predicate = spec.toPredicate(root, query, builder);
			if (predicate != null) {
				query.where(predicate);
			}
		}
		if (query.isDistinct()) {
			query.select(builder.countDistinct(root));
		} else {
			query.select(builder.count(root));
		}
		query.orderBy(Collections.emptyList());
		return em.createQuery(query);
	}

	private <S> TypedQuery<Integer> getExistsQuery(Class<S> type, @Nullable Specification<S> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
		Root<S> root = query.from(type);
		if (spec != null) {
			Predicate predicate = spec.toPredicate(root, query, builder);
			if (predicate != null) {
				query.where(predicate);
			}
		}
		query.select(builder.literal(1)).orderBy(Collections.emptyList());
		return em.createQuery(query).setFirstResult(0).setMaxResults(1);
	}

	/**
	 * <b>执行命名查询更新操作。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于执行批量数据插入、更新和删除。
	 * @param name 命名查询的name
	 * @param paramMap 注入的参数
	 * @return 影响的数据条数
	 */
	public int executeNamedQuery(String name, Map<String, Object> paramMap) {
		Query query = em.createNamedQuery(name);
		if (!CollectionUtils.isEmpty(paramMap)) {
			for (String key : paramMap.keySet()) {
				query.setParameter(key, paramMap.get(key));
			}
		}
		return query.executeUpdate();
	}

	/**
	 * <b>使用命名查询来查询单值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 命名查询的name
	 * @param paramMap 注入的参数
	 * @return 返回值
	 */
	public <T> T findByNamedQuery(String name, Map<String, Object> paramMap) {
		Query query = em.createNamedQuery(name);
		if (!CollectionUtils.isEmpty(paramMap)) {
			for (String key : paramMap.keySet()) {
				query.setParameter(key, paramMap.get(key));
			}
		}
		return (T) query.getSingleResult();
	}

	/**
	 * <b>命名查询。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 命名查询的name
	 * @param paramMap 注入的参数
	 * @return 数据列表
	 */
	public <T> Iterable<T> findAllByNamedQuery(String name, Map<String, Object> paramMap) {
		Query query = em.createNamedQuery(name);
		if (!CollectionUtils.isEmpty(paramMap)) {
			for (String key : paramMap.keySet()) {
				query.setParameter(key, paramMap.get(key));
			}
		}
		return query.getResultList();
	}

	public <T> Page<T> findAllByNamedQuery(String name, String countName, Map<String, Object> paramMap, int page,
			int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Query query = em.createNamedQuery(name);
		if (!CollectionUtils.isEmpty(paramMap)) {
			for (String key : paramMap.keySet()) {
				query.setParameter(key, paramMap.get(key));
			}
		}
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		List<T> list = query.getResultList();
		//记录数
		Number totalCount = (Number) this.findByNamedQuery(countName, paramMap);
		//
		return PageableExecutionUtils.getPage(list, pageable, () -> totalCount.longValue());
	}

	public static class JpaNamedSqlStorage extends NamedSqlStorage {

	}

	//ManuaJpaDaoSupport 注册器
	public static class Registrar implements ImportBeanDefinitionRegistrar {

		/**
		 * <b>获得要注册的Bean类型。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return Bean类型
		 */
		protected Class<? extends ManuaJpaDaoSupport> getBeanClass() {
			return ManuaJpaDaoSupport.class;
		}

		/** 
		 * registerBeanDefinitions。
		 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 */
		@Override
		public final void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
				BeanDefinitionRegistry registry) {
			Class<? extends ManuaJpaDaoSupport> beanClass = getBeanClass();
			if (!registry.containsBeanDefinition(beanClass.getName())) {
				GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
				beanDefinition.setBeanClass(beanClass);
				beanDefinition.setPrimary(true);
				beanDefinition.setAutowireCandidate(true);
				beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_CONSTRUCTOR);
				beanDefinition.setRole(GenericBeanDefinition.ROLE_INFRASTRUCTURE);
				registry.registerBeanDefinition(beanClass.getName(), beanDefinition);
			}
		}

	}
}
