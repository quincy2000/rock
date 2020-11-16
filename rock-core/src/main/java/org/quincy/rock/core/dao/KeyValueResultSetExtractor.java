package org.quincy.rock.core.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.NumberUtils;

/**
 * <b>KeyValueResultSetExtractor。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月17日 下午4:31:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
class KeyValueResultSetExtractor<K, V> implements ResultSetExtractor<Map<K, V>> {
	/**
	 * keyName。
	 */
	private final String keyName;
	/**
	 * valueName。
	 */
	private final String valueName;
	/**
	 * keyClass。
	 */
	private final Class<?> keyClass;
	/**
	 * valueClass。
	 */
	private final Class<?> valueClass;

	/**
	 * conversionService。
	 */
	private ConversionService conversionService = DefaultConversionService.getSharedInstance();

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param keyClass
	 * @param valueClass
	 */
	KeyValueResultSetExtractor(String keyName, String valueName, Class<K> keyClass, Class<V> valueClass) {
		this.keyName = keyName;
		this.valueName = valueName;
		this.keyClass = ClassUtils.resolvePrimitiveIfNecessary(keyClass);
		this.valueClass = ClassUtils.resolvePrimitiveIfNecessary(valueClass);
	}

	/** 
	 * extractData。
	 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
	 */
	@Override
	public Map<K, V> extractData(ResultSet rs) throws SQLException, DataAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		int keyIndex = getColumnIndex(rsmd, keyName, columnCount);
		int valueIndex = getColumnIndex(rsmd, valueName, columnCount);
		Map<K, V> result = new LinkedHashMap<>();
		while (rs.next()) {
			K key = getColumnValue(rs, keyIndex, keyName, keyClass);
			V value = getColumnValue(rs, valueIndex, valueName, valueClass);
			result.put(key, value);
		}
		return result;
	}
	
	protected <T> T getColumnValue(ResultSet rs, int colIndex, String colName, Class<?> requiredType)
			throws SQLException {
		Object value = JdbcUtils.getResultSetValue(rs, colIndex, requiredType);
		if (value != null && requiredType != null && !requiredType.isInstance(value)) {
			// Extracted value does not match already: try to convert it.
			try {
				value = convertValueToRequiredType(value, requiredType);
			} catch (IllegalArgumentException ex) {
				throw new TypeMismatchDataAccessException(colName, ex);
			}
		}
		return (T) value;
	}

	protected static int getColumnIndex(ResultSetMetaData rsmd, String name, int maxIndex) throws SQLException {
		int index = -1;
		for (int i = 1; i <= maxIndex; i++) {
			String column = JdbcUtils.lookupColumnName(rsmd, i);
			if (name.equalsIgnoreCase(column)) {
				index = i;
				break;
			}
		}
		if (index == -1)
			throw new SQLException("Column not found for " + name);
		else
			return index;
	}

	protected Object convertValueToRequiredType(Object value, Class<?> requiredType) {
		if (String.class == requiredType) {
			return value.toString();
		} else if (Number.class.isAssignableFrom(requiredType)) {
			if (value instanceof Number) {
				// Convert original Number to target Number class.
				return NumberUtils.convertNumberToTargetClass(((Number) value), (Class<Number>) requiredType);
			} else {
				// Convert stringified value to target Number class.
				return NumberUtils.parseNumber(value.toString(), (Class<Number>) requiredType);
			}
		} else if (this.conversionService != null
				&& this.conversionService.canConvert(value.getClass(), requiredType)) {
			return this.conversionService.convert(value, requiredType);
		} else {
			throw new IllegalArgumentException("Value [" + value + "] is of type [" + value.getClass().getName()
					+ "] and cannot be converted to required type [" + requiredType.getName() + "]");
		}
	}

}
