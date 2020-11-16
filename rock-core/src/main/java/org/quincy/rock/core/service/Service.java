package org.quincy.rock.core.service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * <b>Service。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月5日 下午12:27:18</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface Service<T, ID> {
	/**
	 * <b>保存新实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 新实体
	 * @return 保存后的实体
	 */
	T save(T entity);

	/**
	 * <b>更新一个已经存在的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entity 实体
	 * @return 更新后的实体
	 */
	T update(T entity);

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
	 * <b>查询所有的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sort 排序规则
	 * @return 实体列表
	 */
	default List<T> findAll(Sort sort) {
		return this.findAll(sort, null);
	}

	/**
	 * <b>查询所有的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sort 排序规则
	 * @param callback 回调，可以为null。
	 * @return 实体列表
	 */
	List<T> findAll(Sort sort, Consumer<T> callback);

	/**
	 * <b>查询一页实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageable 分页及排序规则
	 * @return 一页实体列表
	 */
	default Page<T> findAll(Pageable pageable) {
		return this.findAll(pageable, null);
	}

	/**
	 * <b>查询一页实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageable 分页及排序规则
	 * @param callback 回调，可以为null。
	 * @return 一页实体列表
	 */
	Page<T> findAll(Pageable pageable, Consumer<T> callback);

	/**
	 * <b>根据id列表查询对应的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 实体id列表
	 * @param sort 排序规则
	 * @return 查询到的实体列表
	 */
	default List<T> findAllById(Iterable<ID> ids, Sort sort) {
		return this.findAllById(ids, sort, null);
	}

	/**
	 * <b>根据id列表查询对应的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 实体id列表
	 * @param sort 排序规则
	 * @param callback 回调，可以为null。
	 * @return 查询到的实体列表
	 */
	List<T> findAllById(Iterable<ID> ids, Sort sort, Consumer<T> callback);

	/**
	 * <b>根据id列表查询对应的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 实体id列表
	 * @return 查询到的实体列表
	 */
	default List<T> findAllById(Iterable<ID> ids) {
		return this.findAllById(ids, (Consumer) null);
	}

	/**
	 * <b>根据id列表查询对应的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 实体id列表
	 * @param callback 回调，可以为null。
	 * @return 查询到的实体列表
	 */
	List<T> findAllById(Iterable<ID> ids, Consumer<T> callback);

	/**
	 * <b>根据id查找一个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 实体id
	 * @return 查询到的实体，如果没有找到则返回null
	 */
	default T findById(ID id) {
		return this.findById(id, null);
	}

	/**
	 * <b>根据id查找一个实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 实体id
	 * @param callback 回调，可以为null。
	 * @return 查询到的实体，如果没有找到则返回null
	 */
	T findById(ID id, Consumer<T> callback);

	/**
	 * <b>删除指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 要删除的实体id列表
	 */
	void deleteAllById(Iterable<ID> ids);

	/**
	 * <b>删除指定id的实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 要删除的实体id
	 * @return 实体,如果不存在则返回null
	 */
	T deleteById(ID id);

	/**
	 * <b>返回数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 数据条数
	 */
	long count();

	/**
	 * <b>查看实体是否存在。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 实体的id
	 * @return 实体是否存在
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
}
