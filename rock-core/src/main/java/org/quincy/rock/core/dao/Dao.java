package org.quincy.rock.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

/**
 * <b>数据访问基接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年1月13日 下午10:31:30</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface Dao<T, ID> extends Repository<T, ID> {

	/**
	 * <b>刷新缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	void flush();

	/**
	 * <b>保存实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体对象
	 * @return 保存后的实体
	 */
	<S extends T> S save(S entity);

	/**
	 * <b>保存多个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体对象列表
	 * @return 保存后的实体列表
	 */
	default <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		List<S> result = new ArrayList<S>();
		for (S entity : entities) {
			result.add(save(entity));
		}
		return result;
	}

	/**
	 * <b>删除指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体对象
	 */
	void delete(T entity);

	/**
	 * <b>删除指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 主键id
	 */
	void deleteById(ID id);

	/**
	 * <b>删除所有指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 每个实体执行一次delete语句。
	 * @param ids id列表
	 */
	default void deleteAllById(Iterable<ID> ids) {
		for (T entity : findAllById(ids)) {
			this.delete(entity);
		}
	}

	/**
	 * <b>删除所有指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 每个实体执行一次delete语句。
	 * @param entities 实体列表
	 */
	default void deleteAll(Iterable<? extends T> entities) {
		for (T entity : entities) {
			this.delete(entity);
		}
	}

	/**
	 * <b>删除所有的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 每个实体执行一次delete语句。
	 */
	default void deleteAll() {
		for (T entity : findAll()) {
			this.delete(entity);
		}
	}

	/**
	 * <b>删除所有指定的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一条sql语句删除。
	 * @param entities 实体列表
	 */
	void deleteInBatch(Iterable<T> entities);

	/**
	 * <b>删除所有指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一条sql语句删除。
	 * @param ids id列表
	 */
	void deleteInBatchById(Iterable<ID> ids);

	/**
	 * <b>删除所有的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一条sql语句删除。
	 */
	void deleteAllInBatch();

	/**
	 * <b>更新实体指定属性内容。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 要更新的实体id
	 * @param partMap 更新数据
	 * @return 更新后的实体
	 */
	T updateSpecific(ID id, Map<String, Object> partMap);

	/**
	 * <b>执行命名查询更新操作。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于执行单条插入,批量数据更新和删除。
	 * @param name 命名查询的name
	 * @param paramMap 注入的参数
	 * @return 影响的数据条数
	 */
	int executeNamedQuery(String name, Map<String, Object> paramMap);

	/**
	 * <b>查询数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 数据条数
	 */
	long count();

	/**
	 * <b>查询指定id的实体是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * @param id 主键id
	 * @return 指定id的实体是否存在
	 */
	boolean existsById(ID id);

	/**
	 * <b>查询指定id的实体是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只要有一个id存在即返回true。
	 * @param ids 主键列表
	 * @return 指定id的实体是否存在
	 */
	boolean existsById(Iterable<ID> ids);

	/**
	 * <b>查询指定id的实体是否全部存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 主键列表
	 * @return 指定id的实体是否全部存在
	 */
	boolean existsAllById(Iterable<ID> ids);

	/**
	 * <b>查询指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 主键id
	 * @return 实体,如果没有发现则返回null
	 */
	default T find(ID id) {
		return findById(id).orElse(null);
	}

	/**
	 * <b>查询指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 主键id
	 * @return 实体,如果没有发现则抛出异常
	 * @throws NoSuchElementException
	 */
	default T get(ID id) {
		return findById(id).get();
	}
	
	/**
	 * <b>查询指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 主键id
	 * @return 实体
	 */
	Optional<T> findById(ID id);

	/**
	 * <b>查询指定id的多个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 主键id列表
	 * @return 实体列表
	 */
	Iterable<T> findAllById(Iterable<ID> ids);

	/**
	 * <b>查询指定id的多个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 主键id列表
	 * @param sort 排序规则
	 * @return 实体列表
	 */
	Iterable<T> findAllById(Iterable<ID> ids, Sort sort);

	/**
	 * <b>查询所有数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 实体列表
	 */
	default Iterable<T> findAll() {
		return findAll(Sort.unsorted());
	}

	/**
	 * <b>查询所有数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sort 排序规则
	 * @return 实体列表
	 */
	Iterable<T> findAll(Sort sort);

	/**
	 * <b>查询所有数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageable 分页条件
	 * @return 实体列表
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * <b>命名查询。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 命名查询的name
	 * @param paramMap 注入的参数
	 * @return 数据列表
	 */
	<S> Iterable<S> findAllByNamedQuery(String name, Map<String, Object> paramMap);

	/**
	 * <b>命名查询。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 命名查询的分页查询必须提供两个查询语句，一个语句查询数据，一个语句查询数据条数。
	 * @param name 命名查询的name
	 * @param name 获得总记录数命名查询的name
	 * @param paramMap 注入的参数
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return 一页数据列表
	 */
	<S> Page<S> findAllByNamedQuery(String name, String countName, Map<String, Object> paramMap, int page,
			int pageSize);

	/**
	 * <b>使用命名查询来查询单值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 命名查询的name
	 * @param paramMap 注入的参数
	 * @return 返回值
	 */
	<S> S findByNamedQuery(String name, Map<String, Object> paramMap);
}
