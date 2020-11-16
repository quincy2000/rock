package org.quincy.rock.core.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;

/**
 * <b>手动JdbcDao支持基类(SpringJdbc)。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月23日 下午3:06:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ManualJdbcDaoSupport {
	/**
	 * daoSupport。
	 */
	private final NamedParameterJdbcDaoSupport daoSupport;

	/**
	 * sql仓库。
	 */
	private JdbcNamedSqlStorage namedSqlStorage;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jdbcTemplate spring jdbc 模板
	 * @param namedSqlStorage sql仓库
	 */
	public ManualJdbcDaoSupport(JdbcTemplate jdbcTemplate, @Nullable JdbcNamedSqlStorage namedSqlStorage) {
		daoSupport = new NamedParameterJdbcDaoSupport();
		daoSupport.setJdbcTemplate(jdbcTemplate);
		this.namedSqlStorage = namedSqlStorage;
	}

	/**
	 * <b>getJdbcTemplate。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return JdbcTemplate
	 */
	public final JdbcTemplate template() {
		return daoSupport.getJdbcTemplate();
	}

	/**
	 * <b>getNamedParameterJdbcTemplate。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return NamedParameterJdbcTemplate
	 */
	public final NamedParameterJdbcTemplate npTemplate() {
		return daoSupport.getNamedParameterJdbcTemplate();
	}

	/**
	 * <b>获得命名sql。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 * @param arguments 格式化参数
	 * @return sql
	 */
	public final String getNamedSql(String name, Object... arguments) {
		return namedSqlStorage == null ? null : namedSqlStorage.getNamedSql(name, arguments);
	}

	/**
	 * <b>执行sql语句。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param args 参数数组
	 * @return 影响的数据条数
	 * @see SqlParameterValue
	 */
	public int execute(String sql, Object... args) {
		return ArrayUtils.isEmpty(args) ? template().update(sql) : template().update(sql, args);
	}

	/**
	 * <b>执行sql语句。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param pss 参数设置器
	 * @return 影响的数据条数
	 */
	public int execute(String sql, PreparedStatementSetter pss) {
		return template().update(sql, pss);
	}

	/**
	 * <b>执行命名sql语句。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param paramMap 参数Map
	 * @return 影响的数据条数
	 * @see SqlParameterValue
	 */
	public int executeByNaming(String sql, Map<String, ?> paramMap) {
		SqlParameterSource param = new MapSqlParameterSource(paramMap);
		return npTemplate().update(sql, param);
	}

	/**
	 * <b>执行命名sql语句。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param bean 参数Bean
	 * @return 影响的数据条数
	 */
	public int executeByNaming(String sql, Object bean) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(bean);
		return npTemplate().update(sql, param);
	}

	/**
	 * <b>批处理。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句数组
	 * @return 每条sql语句影响的数据条数
	 */
	public int[] batch(String... sql) {
		return template().batchUpdate(sql);
	}

	/**
	 * <b>批处理。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param batchArgs 记录数组
	 * @return 每条sql语句影响的数据条数
	 */
	public int[] batch(String sql, List<Object[]> batchArgs) {
		return template().batchUpdate(sql, batchArgs);
	}

	/**
	 * <b>批处理。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param batchArgs 记录数组
	 * @param argTypes 参数类型
	 * @return 每条sql语句影响的数据条数
	 */
	public int[] batch(String sql, List<Object[]> batchArgs, int[] argTypes) {
		return template().batchUpdate(sql, batchArgs, argTypes);
	}

	/**
	 * <b>批处理。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param pss 参数设置器
	 * @return 每条sql语句影响的数据条数
	 */
	public int[] batch(String sql, BatchPreparedStatementSetter pss) {
		return template().batchUpdate(sql, pss);
	}

	/**
	 * <b>批处理。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param batchArgs 记录集合
	 * @return 每条sql语句影响的数据条数
	 */
	public int[] batchByNaming(String sql, Collection<Map<String, ?>> batchValues) {
		SqlParameterSource[] batch = new SqlParameterSource[batchValues.size()];
		int i = 0;
		for (Map<String, ?> value : batchValues) {
			batch[i++] = new MapSqlParameterSource(value);
		}
		return npTemplate().batchUpdate(sql, batch);
	}

	/**
	 * <b>批处理。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql sql语句
	 * @param beans 记录列表
	 * @return 每条sql语句影响的数据条数
	 */
	public int[] batchByNaming(String sql, List<?> beans) {
		int len = beans.size();
		SqlParameterSource[] batch = new SqlParameterSource[len];
		for (int i = 0; i < len; i++) {
			Object bean = beans.get(i);
			batch[i] = (bean instanceof Map) ? new MapSqlParameterSource((Map) bean)
					: new BeanPropertySqlParameterSource(bean);
		}
		return npTemplate().batchUpdate(sql, batch);
	}

	/**
	 * <b>查询单值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param requiredType 请求类型
	 * @param defaultValue 默认值
	 * @param args 参数值数组
	 * @return 单值
	 * @see SqlParameterValue
	 */
	public <T> T querySimple(String sql, Class<T> requiredType, T defaultValue, Object... args) {
		RowMapper<T> mapper = new SingleColumnRowMapper<>(requiredType);
		List<T> list = ArrayUtils.isEmpty(args) ? template().query(sql, mapper) : template().query(sql, args, mapper);
		T value = DataAccessUtils.singleResult(list);
		return value == null ? defaultValue : value;
	}

	/**
	 * <b>查询单值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param pss 参数设置器
	 * @param requiredType 请求类型
	 * @param defaultValue 默认值
	 * @return 单值
	 */
	public <T> T querySimple(String sql, PreparedStatementSetter pss, Class<T> requiredType, T defaultValue) {
		RowMapper<T> mapper = new SingleColumnRowMapper<>(requiredType);
		List<T> list = template().query(sql, pss, mapper);
		T value = DataAccessUtils.singleResult(list);
		return value == null ? defaultValue : value;
	}

	/**
	 * <b>查询单值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param requiredType 请求类型
	 * @param defaultValue 默认值
	 * @param paramMap 参数Map
	 * @return 单值
	 * @see SqlParameterValue
	 */
	public <T> T querySimpleByNaming(String sql, Class<T> requiredType, T defaultValue, Map<String, ?> paramMap) {
		RowMapper<T> mapper = new SingleColumnRowMapper<>(requiredType);
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		List<T> list = npTemplate().query(sql, source, mapper);
		T value = DataAccessUtils.singleResult(list);
		return value == null ? defaultValue : value;
	}

	/**
	 * <b>查询一条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param args 参数数组
	 * @return 一条记录
	 * @see SqlParameterValue
	 */
	public Map<String, Object> queryMap(String sql, Object... args) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		List<Map<String, Object>> list = ArrayUtils.isEmpty(args) ? template().query(sql, mapper)
				: template().query(sql, mapper, args);
		return DataAccessUtils.singleResult(list);
	}

	/**
	 * <b>查询一条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param pss 参数设置器
	 * @return 一条记录
	 */
	public Map<String, Object> queryMap(String sql, PreparedStatementSetter pss) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		List<Map<String, Object>> list = template().query(sql, pss, mapper);
		return DataAccessUtils.singleResult(list);
	}

	/**
	 * <b>查询一条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param paramMap 参数Map
	 * @return 一条记录
	 * @see SqlParameterValue
	 */
	public Map<String, Object> queryMapByNaming(String sql, Map<String, ?> paramMap) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		List<Map<String, Object>> list = npTemplate().query(sql, source, mapper);
		return DataAccessUtils.singleResult(list);
	}

	/**
	 * <b>查询一条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param beanType 请求类型
	 * @param args 参数数组
	 * @return 一条记录
	 * @see SqlParameterValue
	 */
	public <T> T queryBean(String sql, Class<T> beanType, Object... args) {
		RowMapper<T> mapper = new BeanPropertyRowMapper<T>(beanType);
		List<T> list = ArrayUtils.isEmpty(args) ? template().query(sql, mapper) : template().query(sql, mapper, args);
		return DataAccessUtils.singleResult(list);
	}

	/**
	 * <b>查询一条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param pss 参数设置器
	 * @param beanType 请求类型
	 * @return 一条记录
	 */
	public <T> T queryBean(String sql, PreparedStatementSetter pss, Class<T> beanType) {
		RowMapper<T> mapper = new BeanPropertyRowMapper<T>(beanType);
		List<T> list = template().query(sql, pss, mapper);
		return DataAccessUtils.singleResult(list);
	}

	/**
	 * <b>查询一条记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param beanType 请求类型
	 * @param paramMap 参数Map
	 * @return 一条记录
	 * @see SqlParameterValue
	 */
	public <T> T queryBeanByNaming(String sql, Class<T> beanType, Map<String, ?> paramMap) {
		RowMapper<T> mapper = new BeanPropertyRowMapper<T>(beanType);
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		List<T> list = npTemplate().query(sql, source, mapper);
		return DataAccessUtils.singleResult(list);
	}

	/**
	 * <b>查询值列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param requiredType 请求类型
	 * @param args 参数数组
	 * @return 一列值列表
	 * @see SqlParameterValue
	 */
	public <T> List<T> querySimpleList(String sql, Class<T> requiredType, Object... args) {
		RowMapper<T> mapper = new SingleColumnRowMapper<>(requiredType);
		List<T> list = ArrayUtils.isEmpty(args) ? template().query(sql, mapper) : template().query(sql, mapper, args);
		return list;
	}

	/**
	 * <b>查询值列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param pss 参数设置器
	 * @param requiredType 请求类型
	 * @return 一列值列表
	 */
	public <T> List<T> querySimpleList(String sql, PreparedStatementSetter pss, Class<T> requiredType) {
		RowMapper<T> mapper = new SingleColumnRowMapper<>(requiredType);
		List<T> list = template().query(sql, pss, mapper);
		return list;
	}

	/**
	 * <b>查询值列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param requiredType 请求类型
	 * @param paramMap 参数Map
	 * @return 一列值列表
	 * @see SqlParameterValue
	 */
	public <T> List<T> querySimpleListByNaming(String sql, Class<T> requiredType, Map<String, ?> paramMap) {
		RowMapper<T> mapper = new SingleColumnRowMapper<>(requiredType);
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		List<T> list = npTemplate().query(sql, source, mapper);
		return list;
	}

	/**
	 * <b>查询值列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param countSql 查记录数sql语句
	 * @param requiredType 请求类型
	 * @param paramMap 参数Map
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return 一列值列表
	 * @see SqlParameterValue
	 */
	public <T> Page<T> querySimpleListByNaming(String sql, String countSql, Class<T> requiredType,
			Map<String, ?> paramMap, int page, int pageSize) {
		Long count = this.querySimpleByNaming(countSql, Long.class, 0L, paramMap);
		Pageable pageable = PageRequest.of(page, pageSize);
		Map<String, Object> map = new HashMap<>(paramMap);
		if (!map.containsKey("offset"))
			map.put("offset", pageable.getOffset());
		if (!map.containsKey("pageSize"))
			map.put("pageSize", pageSize);
		List<T> list = this.querySimpleListByNaming(sql, requiredType, map);
		Page<T> ps = new PageImpl<>(list, pageable, count);
		return ps;
	}

	/**
	 * <b>查询数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param args 参数数组
	 * @return 数据列表
	 * @see SqlParameterValue
	 */
	public List<Map<String, Object>> queryList(String sql, Object... args) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		List<Map<String, Object>> list = ArrayUtils.isEmpty(args) ? template().query(sql, mapper)
				: template().query(sql, mapper, args);
		return list;
	}

	/**
	 * <b>查询数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param pss 参数设置器
	 * @return 数据列表
	 */
	public List<Map<String, Object>> queryList(String sql, PreparedStatementSetter pss) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		List<Map<String, Object>> list = template().query(sql, pss, mapper);
		return list;
	}

	/**
	 * <b>查询数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param paramMap 参数Map
	 * @return 数据列表
	 * @see SqlParameterValue
	 */
	public List<Map<String, Object>> queryListByNaming(String sql, Map<String, ?> paramMap) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		List<Map<String, Object>> list = npTemplate().query(sql, source, mapper);
		return list;
	}

	/**
	 * <b>查询数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param beanType 请求类型
	 * @param args 参数数组
	 * @return 数据列表
	 * @see SqlParameterValue
	 */
	public <T> List<T> queryList(String sql, Class<T> beanType, Object... args) {
		RowMapper<T> mapper = new BeanPropertyRowMapper<T>(beanType);
		List<T> list = ArrayUtils.isEmpty(args) ? template().query(sql, mapper) : template().query(sql, mapper, args);
		return list;
	}

	/**
	 * <b>查询数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param pss 参数设置器
	 * @param beanType 请求类型
	 * @return 数据列表
	 */
	public <T> List<T> queryList(String sql, PreparedStatementSetter pss, Class<T> beanType) {
		RowMapper<T> mapper = new BeanPropertyRowMapper<T>(beanType);
		List<T> list = template().query(sql, pss, mapper);
		return list;
	}

	/**
	 * <b>查询数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询语句
	 * @param beanType 请求类型
	 * @param paramMap 参数Map
	 * @return 数据列表
	 * @see SqlParameterValue
	 */
	public <T> List<T> queryListByNaming(String sql, Class<T> beanType, Map<String, ?> paramMap) {
		RowMapper<T> mapper = new BeanPropertyRowMapper<T>(beanType);
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		List<T> list = npTemplate().query(sql, source, mapper);
		return list;
	}

	/**
	 * <b>查询一页数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询sql语句
	 * @param countSql 查记录数sql语句
	 * @param paramMap 参数Map
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return 一页数据列表
	 * @see SqlParameterValue
	 */
	public Page<Map<String, Object>> queryListByNaming(String sql, String countSql, Map<String, ?> paramMap, int page,
			int pageSize) {
		Long count = this.querySimpleByNaming(countSql, Long.class, 0L, paramMap);
		Pageable pageable = PageRequest.of(page, pageSize);
		Map<String, Object> map = new HashMap<>(paramMap);
		if (!map.containsKey("offset"))
			map.put("offset", pageable.getOffset());
		if (!map.containsKey("pageSize"))
			map.put("pageSize", pageSize);
		List<Map<String, Object>> list = this.queryListByNaming(sql, map);
		Page<Map<String, Object>> ps = new PageImpl<>(list, pageable, count);
		return ps;
	}

	/**
	 * <b>查询一页数据列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询sql语句
	 * @param countSql 查记录数sql语句
	 * @param beanType 请求类型
	 * @param paramMap 参数Map
	 * @param page 页码
	 * @param pageSize 页大小
	 * @return 一页数据列表
	 * @see SqlParameterValue
	 */
	public <T> Page<T> queryListByNaming(String sql, String countSql, Class<T> beanType, Map<String, ?> paramMap,
			int page, int pageSize) {
		Long count = this.querySimpleByNaming(countSql, Long.class, 0L, paramMap);
		Pageable pageable = PageRequest.of(page, pageSize);
		Map<String, Object> map = new HashMap<>(paramMap);
		if (!map.containsKey("offset"))
			map.put("offset", pageable.getOffset());
		if (!map.containsKey("pageSize"))
			map.put("pageSize", pageSize);
		List<T> list = this.queryListByNaming(sql, beanType, map);
		Page<T> ps = new PageImpl<>(list, pageable, count);
		return ps;
	}

	/**
	 * <b>查询数据字典。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询sql语句
	 * @param keyName key列名
	 * @param valueName value列名
	 * @param keyType key类型
	 * @param valueType value类型
	 * @param args 参数数组
	 * @return 数据字典Map
	 * @see SqlParameterValue
	 */
	public <K, V> Map<K, V> queryDict(String sql, String keyName, String valueName, Class<K> keyType,
			Class<V> valueType, Object... args) {
		KeyValueResultSetExtractor<K, V> resultSetExtractor = new KeyValueResultSetExtractor<>(keyName, valueName,
				keyType, valueType);
		Map<K, V> result = ArrayUtils.isEmpty(args) ? template().query(sql, resultSetExtractor)
				: template().query(sql, args, resultSetExtractor);
		return result;
	}

	/**
	 * <b>查询数据字典。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询sql语句
	 * @param pss 参数设置器
	 * @param keyName key列名
	 * @param valueName value列名
	 * @param keyType key类型
	 * @param valueType value类型
	 * @return 数据字典Map
	 */
	public <K, V> Map<K, V> queryDict(String sql, PreparedStatementSetter pss, String keyName, String valueName,
			Class<K> keyType, Class<V> valueType) {
		KeyValueResultSetExtractor<K, V> resultSetExtractor = new KeyValueResultSetExtractor<>(keyName, valueName,
				keyType, valueType);
		Map<K, V> result = template().query(sql, pss, resultSetExtractor);
		return result;
	}

	/**
	 * <b>查询数据字典。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sql 查询sql语句
	 * @param keyName key列名
	 * @param valueName value列名
	 * @param keyType key类型
	 * @param valueType value类型
	 * @param paramMap 参数Map
	 * @return 数据字典Map
	 * @see SqlParameterValue
	 */
	public <K, V> Map<K, V> queryDictByNaming(String sql, String keyName, String valueName, Class<K> keyType,
			Class<V> valueType, Map<String, ?> paramMap) {
		KeyValueResultSetExtractor<K, V> resultSetExtractor = new KeyValueResultSetExtractor<>(keyName, valueName,
				keyType, valueType);
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		Map<K, V> result = npTemplate().query(sql, source, resultSetExtractor);
		return result;
	}

	public static class JdbcNamedSqlStorage extends NamedSqlStorage {

	}

	//ManuaJdbcDaoSupport 注册器
	public static class Registrar implements ImportBeanDefinitionRegistrar {

		/**
		 * <b>获得要注册的Bean类型。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return Bean类型
		 */
		protected Class<? extends ManualJdbcDaoSupport> getBeanClass() {
			return ManualJdbcDaoSupport.class;
		}

		/** 
		 * registerBeanDefinitions。
		 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 */
		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
				BeanDefinitionRegistry registry) {
			Class<? extends ManualJdbcDaoSupport> beanClass = getBeanClass();
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
