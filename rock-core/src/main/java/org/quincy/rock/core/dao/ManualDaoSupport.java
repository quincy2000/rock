package org.quincy.rock.core.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <b>手动Dao支持基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月17日 下午3:03:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class ManualDaoSupport implements ApplicationContextAware {
	/**
	 * context。
	 */
	protected ApplicationContext context;

	/** 
	 * setApplicationContext。
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	/**
	 * <b>getDao。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type
	 * @return
	 */
	public final <T> T getDao(Class<T> type) {
		return context.getBean(type);
	}

	/**
	 * <b>jdbcDaoSupport。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public final ManualJdbcDaoSupport jdbcDaoSupport() {
		return getDao(ManualJdbcDaoSupport.class);
	}

	/**
	 * <b>jpaDaoSupport。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public final ManuaJpaDaoSupport jpaDaoSupport() {
		return getDao(ManuaJpaDaoSupport.class);
	}
}
