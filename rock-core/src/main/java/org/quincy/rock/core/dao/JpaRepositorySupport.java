package org.quincy.rock.core.dao;

import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>JpaRepositorySupport。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月5日 下午6:30:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Transactional(readOnly = true)
@EnableJpaRepositories(repositoryBaseClass = JpaRepositorySupport.class)
public class JpaRepositorySupport<T, ID> extends SimpleJpaRepository<T, ID> implements Dao<T, ID> {
	/**
	 * daoSupport。
	 */
	private final ManuaJpaDaoSupport daoSupport;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param domainClass
	 * @param em
	 */
	public JpaRepositorySupport(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param entityInformation
	 * @param entityManager
	 */
	public JpaRepositorySupport(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.daoSupport = new ManuaJpaDaoSupport(entityManager, null);
	}

	protected ManuaJpaDaoSupport daoSupport() {
		return daoSupport;
	}

	/** 
	 * existsById。
	 * @see org.quincy.rock.core.dao.Dao#existsById(java.lang.Iterable)
	 */
	@Override
	public boolean existsById(Iterable<ID> ids) {
		return daoSupport.existsById(getDomainClass(), ids);
	}

	/** 
	 * existsAllById。
	 * @see org.quincy.rock.core.dao.Dao#existsAllById(java.lang.Iterable)
	 */
	@Override
	public boolean existsAllById(Iterable<ID> ids) {
		return daoSupport.existsAllById(getDomainClass(), ids);
	}

	/** 
	 * deleteInBatchById。
	 * @see org.quincy.rock.core.dao.Dao#deleteInBatchById(java.lang.Iterable)
	 */
	@Override
	@Transactional
	public void deleteInBatchById(Iterable<ID> ids) {
		daoSupport.deleteInBatchById(getDomainClass(), ids);
	}

	/** 
	 * updateSpecific。
	 * @see org.quincy.rock.core.dao.Dao#updateSpecific(java.lang.Object, java.util.Map)
	 */
	@Override
	@Transactional
	public T updateSpecific(ID id, Map<String, Object> partMap) {
		return daoSupport.updateSpecific(getDomainClass(), id, partMap);
	}

	/** 
	 * executeNamedQuery。
	 * @see org.quincy.rock.core.dao.Dao#executeNamedQuery(java.lang.String, java.util.Map)
	 */
	@Override
	@Transactional
	public int executeNamedQuery(String name, Map<String, Object> paramMap) {
		return daoSupport.executeNamedQuery(name, paramMap);
	}

	/** 
	 * findAllByNamedQuery。
	 * @see org.quincy.rock.core.dao.Dao#findAllByNamedQuery(java.lang.String, java.util.Map)
	 */
	@Override
	public <S> Iterable<S> findAllByNamedQuery(String name, Map<String, Object> paramMap) {
		return daoSupport.findAllByNamedQuery(name, paramMap);
	}

	/** 
	 * findAllByNamedQuery。
	 * @see org.quincy.rock.core.dao.Dao#findAllByNamedQuery(java.lang.String, java.lang.String, java.util.Map, int, int)
	 */
	@Override
	public <S> Page<S> findAllByNamedQuery(String name, String countName, Map<String, Object> paramMap, int page,
			int pageSize) {
		return daoSupport.findAllByNamedQuery(name, countName, paramMap, page, pageSize);
	}

	/** 
	 * findByNamedQuery。
	 * @see org.quincy.rock.core.dao.Dao#findByNamedQuery(java.lang.String, java.util.Map)
	 */
	@Override
	public <S> S findByNamedQuery(String name, Map<String, Object> paramMap) {
		return daoSupport.findByNamedQuery(name, paramMap);
	}

	/** 
	 * findAllById。
	 * @see org.quincy.rock.core.dao.Dao#findAllById(java.lang.Iterable, org.springframework.data.domain.Sort)
	 */
	@Override
	public Iterable<T> findAllById(Iterable<ID> ids, Sort sort) {
		return daoSupport.findAllById(getDomainClass(), ids, sort);
	}

	public boolean exists(Specification<T> spec) {
		return daoSupport.exists(getDomainClass(), spec);
	}

	@Transactional
	public void deleteAllInBatch(Specification<T> spec) {
		daoSupport.deleteAllInBatch(getDomainClass(), spec);
	}
}
