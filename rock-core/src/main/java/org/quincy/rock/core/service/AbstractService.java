package org.quincy.rock.core.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.quincy.rock.core.dao.Dao;
import org.quincy.rock.core.util.CoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>AbstractService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月5日 下午1:49:26</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public abstract class AbstractService<T, ID> implements Service<T, ID> {

	/**
	 * dao。
	 */
	@Autowired
	protected Dao<T, ID> dao;

	/**
	 * <b>获得Dao。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Dao
	 */
	public <D extends Dao<T, ID>> D getDao() {
		return (D) dao;
	}

	/** 
	 * save。
	 * @see org.quincy.rock.core.service.Service#save(java.lang.Object)
	 */
	@Override
	@Transactional
	public T save(T entity) {
		T t = this.getDao().save(entity);
		return t;
	}

	/** 
	 * update。
	 * @see org.quincy.rock.core.service.Service#update(java.lang.Object)
	 */
	@Override
	@Transactional
	public T update(T entity) {
		T t = this.getDao().save(entity);
		return t;
	}

	/** 
	 * updateSpecific。
	 * @see org.quincy.rock.core.service.Service#updateSpecific(java.lang.Object, java.util.Map)
	 */
	@Override
	@Transactional
	public T updateSpecific(ID id, Map<String, Object> partMap) {
		return this.getDao().updateSpecific(id, partMap);
	}

	/** 
	 * findAll。
	 * @see org.quincy.rock.core.service.Service#findAll(org.springframework.data.domain.Sort, java.util.function.Consumer)
	 */
	@Override
	public List<T> findAll(Sort sort, Consumer<T> callback) {
		List<T> list = CoreUtil.toList(this.getDao().findAll(sort));
		if (callback != null) {
			list.forEach(callback);
		}
		return list;
	}

	/** 
	 * findAll。
	 * @see org.quincy.rock.core.service.Service#findAll(org.springframework.data.domain.Pageable, java.util.function.Consumer)
	 */
	@Override
	public Page<T> findAll(Pageable pageable, Consumer<T> callback) {
		Page<T> page = this.getDao().findAll(pageable);
		if (callback != null) {
			page.forEach(callback);
		}
		return page;
	}

	/** 
	 * findAllById。
	 * @see org.quincy.rock.core.service.Service#findAllById(java.lang.Iterable, org.springframework.data.domain.Sort, java.util.function.Consumer)
	 */
	@Override
	public List<T> findAllById(Iterable<ID> ids, Sort sort, Consumer<T> callback) {
		List<T> list = CoreUtil.toList(this.getDao().findAllById(ids, sort));
		if (callback != null) {
			list.forEach(callback);
		}
		return list;
	}

	/** 
	 * findAllById。
	 * @see org.quincy.rock.core.service.Service#findAllById(java.lang.Iterable, java.util.function.Consumer)
	 */
	@Override
	public List<T> findAllById(Iterable<ID> ids, Consumer<T> callback) {
		List<T> list = CoreUtil.toList(this.getDao().findAllById(ids));
		if (callback != null) {
			list.forEach(callback);
		}
		return list;
	}

	/** 
	 * findById。
	 * @see org.quincy.rock.core.service.Service#findById(java.lang.Object, java.util.function.Consumer)
	 */
	@Override
	public T findById(ID id, Consumer<T> callback) {
		Optional<T> o = this.getDao().findById(id);
		T t = null;
		if (o.isPresent()) {
			t = o.get();
		}
		if (t != null && callback != null) {
			callback.accept(t);
		}
		return t;
	}

	/** 
	 * deleteAllById。
	 * @see org.quincy.rock.core.service.Service#deleteAllById(java.lang.Iterable)
	 */
	@Override
	@Transactional
	public void deleteAllById(Iterable<ID> ids) {
		this.getDao().deleteInBatchById(ids);
	}

	/** 
	 * deleteById。
	 * @see org.quincy.rock.core.service.Service#deleteById(java.lang.Object)
	 */
	@Override
	@Transactional
	public T deleteById(ID id) {
		Dao<T, ID> dao = this.getDao();
		Optional<T> o = dao.findById(id);
		T t = null;
		if (o.isPresent()) {
			t = o.get();
			dao.delete(t);
		}
		return t;
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.service.Service#count()
	 */
	@Override
	public long count() {
		return this.getDao().count();
	}

	/** 
	 * existsById。
	 * @see org.quincy.rock.core.service.Service#existsById(java.lang.Object)
	 */
	@Override
	public boolean existsById(ID id) {
		return this.getDao().existsById(id);
	}

	/** 
	 * existsById。
	 * @see org.quincy.rock.core.service.Service#existsById(java.lang.Iterable)
	 */
	@Override
	public boolean existsById(Iterable<ID> ids) {
		return this.getDao().existsById(ids);
	}

	/** 
	 * existsAllById。
	 * @see org.quincy.rock.core.service.Service#existsAllById(java.lang.Iterable)
	 */
	@Override
	public boolean existsAllById(Iterable<ID> ids) {
		return this.getDao().existsAllById(ids);
	}
}
