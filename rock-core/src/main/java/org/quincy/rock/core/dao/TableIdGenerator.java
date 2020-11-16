package org.quincy.rock.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.quincy.rock.core.exception.RockException;
import org.quincy.rock.core.id.IdentifierGenerator;
import org.quincy.rock.core.util.IOUtil;

/**
 * <b>表主键ID生成器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 将表主键id存储在数据库表中，该ID发生器有自己独立的事务。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年1月13日 下午2:30:26</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class TableIdGenerator implements IdentifierGenerator<String, Long> {
	/**
	 * 数据源。
	 */
	private DataSource dataSource;
	/**
	 * ID缓冲个数。
	 */
	private int cache = 5;
	/**
	 * 表名。
	 */
	private String tableName;
	/**
	 * key字段名。
	 */
	private String keyFieldName;
	/**
	 * value字段名。
	 */
	private String valueFieldName;
	/**
	 * 初始值。
	 */
	private Long initialValue = 1000L;

	/**
	 * 缓存id值。
	 */
	private final Map<String, Stack<Long>> cacheMap = new HashMap<>();

	/**
	 * <b>获得数据源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 数据源
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * <b>设置数据源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dataSource 数据源
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * <b>ID缓冲个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 必须大于0。
	 * @return ID缓冲个数。
	 */
	public int getCache() {
		return cache;
	}

	/**
	 * <b>ID缓冲个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 必须大于0。
	 * @param cache ID缓冲个数。
	 */
	public void setCache(int cache) {
		this.cache = cache;
	}

	/**
	 * <b>获得表名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 表名
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * <b>设置表名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tableName 表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * <b>获得key字段名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return key字段名
	 */
	public String getKeyFieldName() {
		return keyFieldName;
	}

	/**
	 * <b>设置key字段名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param keyFieldName key字段名
	 */
	public void setKeyFieldName(String keyFieldName) {
		this.keyFieldName = keyFieldName;
	}

	/**
	 * <b>获得value字段名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return value字段名
	 */
	public String getValueFieldName() {
		return valueFieldName;
	}

	/**
	 * <b>设置value字段名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param valueFieldName value字段名
	 */
	public void setValueFieldName(String valueFieldName) {
		this.valueFieldName = valueFieldName;
	}

	/**
	 * <b>获得初始值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 初始值
	 */
	public Long getInitialValue() {
		return initialValue;
	}

	/**
	 * <b>设置初始值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param initialValue 初始值
	 */
	public void setInitialValue(Long initialValue) {
		this.initialValue = initialValue;
	}

	/** 
	 * generate。
	 * @see org.quincy.rock.core.id.IdentifierGenerator#generate(java.lang.Object)
	 */
	@Override
	@Transactional
	public Long generate(String key) {
		Stack<Long> stack = this.getCacheStack(key);
		Long id;
		synchronized (stack) {
			if (stack.isEmpty()) {
				Connection conn = this.getConnection();
				Boolean autoCommit = null;
				try {
					autoCommit = conn.getAutoCommit();
					conn.setAutoCommit(false);
					//取得id
					Long firstId = this.queryId(conn, getSelectSql(), key);
					Long lastId;
					if (firstId == null) {
						firstId = getInitialValue();
						lastId = firstId + cache;
						this.updateId(conn, getInsertSql(), key, lastId);
					} else {
						lastId = firstId + cache;
						this.updateId(conn, getUpdateSql(), lastId, key);
					}
					conn.commit();
					//入栈缓存
					for (long i = lastId - 1, first = firstId; i >= first; i--) {
						stack.push(i);
					}
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (Exception ex) {
					}
					throw new RockException(e.getMessage(), e);
				} finally {
					if (autoCommit != null) {
						try {
							conn.setAutoCommit(autoCommit);
						} catch (Exception e) {
						}
					}
					IOUtil.closeQuietly(conn);
				}
			}
			id = stack.pop();
		}
		return id;
	}

	//获得缓存id的堆栈
	private synchronized Stack<Long> getCacheStack(String key) {
		Stack<Long> stack = this.cacheMap.get(key);
		if (stack == null) {
			stack = new Stack<>();
			this.cacheMap.put(key, stack);
		}
		return stack;
	}

	private Long queryId(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0, l = params.length; i < l; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rs = ps.executeQuery();
			return rs.next() ? rs.getLong(1) : null;
		} finally {
			IOUtil.closeQuietly(rs);
			IOUtil.closeQuietly(ps);
		}
	}

	private void updateId(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0, l = params.length; i < l; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ps.executeUpdate();
		} finally {
			IOUtil.closeQuietly(ps);
		}
	}

	private String getSelectSql() {
		if (selectSql == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append(valueFieldName);
			sql.append(" from ");
			sql.append(tableName);
			sql.append(" where ");
			sql.append(keyFieldName);
			sql.append(" = ?");
			selectSql = sql.toString();
		}
		return selectSql;
	}

	private String getInsertSql() {
		if (insertSql == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into ");
			sql.append(tableName);
			sql.append(" (");
			sql.append(keyFieldName);
			sql.append(",");
			sql.append(valueFieldName);
			sql.append(") values (?,?)");
			insertSql = sql.toString();
		}
		return insertSql;
	}

	private String getUpdateSql() {
		if (updateSql == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("update ");
			sql.append(tableName);
			sql.append(" set ");
			sql.append(valueFieldName);
			sql.append(" = ? where ");
			sql.append(keyFieldName);
			sql.append(" = ?");
			updateSql = sql.toString();
		}
		return updateSql;
	}

	private Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RockException(e.getMessage(), e);
		}
	}

	private String selectSql;
	private String insertSql;
	private String updateSql;
}
