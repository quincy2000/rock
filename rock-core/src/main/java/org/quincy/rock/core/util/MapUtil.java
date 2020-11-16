package org.quincy.rock.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.Map;

/**
 * <b>MapUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年1月14日 上午11:38:17</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MapUtil {

	/**
	 * <b>获得对象值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return 对象值
	 */
	public static <T> T getObject(Map map, Object key) {
		Object value = map.get(key);
		return (T) value;
	}

	/**
	 * <b>获得对象值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return 对象值
	 */
	public static <T> T getObject(Map map, Object key, Object defValue) {
		Object value = map.get(key);
		return (T) (value == null ? defValue : value);
	}

	/**
	 * <b>获得字符串值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return 字符串值
	 */
	public static String getString(Map map, Object key) {
		return ConvertUtil.obj2String(map.get(key));
	}

	/**
	 * <b>获得字符串值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return 字符串值
	 */
	public static String getString(Map map, Object key, String defValue) {
		String value = ConvertUtil.obj2String(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得Boolean值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Boolean值
	 */
	public static Boolean getBoolean(Map map, Object key) {
		return ConvertUtil.obj2Boolean(map.get(key));
	}

	/**
	 * <b>获得Boolean值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Boolean值
	 */
	public static Boolean getBoolean(Map map, Object key, Boolean defValue) {
		Boolean value = ConvertUtil.obj2Boolean(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得Boolean值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Boolean值
	 */
	public static boolean getBoolean(Map map, Object key, boolean defValue) {
		return ConvertUtil.obj2Boolean(map.get(key), defValue);
	}

	/**
	 * <b>获得Byte值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Byte值
	 */
	public static Byte getByte(Map map, Object key) {
		return ConvertUtil.obj2Byte(map.get(key));
	}

	/**
	 * <b>获得Byte值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Byte值
	 */
	public static Byte getByte(Map map, Object key, Byte defValue) {
		Byte value = ConvertUtil.obj2Byte(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得byte值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return byte值
	 */
	public static byte getByte(Map map, Object key, int defValue) {
		return ConvertUtil.obj2Byte(map.get(key), defValue);
	}

	/**
	 * <b>获得Short值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Short值
	 */
	public static Short getShort(Map map, Object key) {
		return ConvertUtil.obj2Short(map.get(key));
	}

	/**
	 * <b>获得Short值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Short值
	 */
	public static Short getShort(Map map, Object key, Short defValue) {
		Short value = ConvertUtil.obj2Short(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得short值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return short值
	 */
	public static short getShort(Map map, Object key, int defValue) {
		return ConvertUtil.obj2Short(map.get(key), defValue);
	}

	/**
	 * <b>获得Integer值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Integer值
	 */
	public static Integer getInteger(Map map, Object key) {
		return ConvertUtil.obj2Integer(map.get(key));
	}

	/**
	 * <b>获得Integer值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Integer值
	 */
	public static Integer getInteger(Map map, Object key, Integer defValue) {
		Integer value = ConvertUtil.obj2Integer(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得int值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return int值
	 */
	public static int getInteger(Map map, Object key, int defValue) {
		return ConvertUtil.obj2Integer(map.get(key), defValue);
	}

	/**
	 * <b>获得Long值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Long值
	 */
	public static Long getLong(Map map, Object key) {
		return ConvertUtil.obj2Long(map.get(key));
	}

	/**
	 * <b>获得Long值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Long值
	 */
	public static Long getLong(Map map, Object key, Long defValue) {
		Long value = ConvertUtil.obj2Long(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得long值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return long值
	 */
	public static long getLong(Map map, Object key, long defValue) {
		return ConvertUtil.obj2Long(map.get(key), defValue);
	}

	/**
	 * <b>获得Float值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Float值
	 */
	public static Float getFloat(Map map, Object key) {
		return ConvertUtil.obj2Float(map.get(key));
	}

	/**
	 * <b>获得Float值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Float值
	 */
	public static Float getFloat(Map map, Object key, Float defValue) {
		Float value = ConvertUtil.obj2Float(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得float值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return float值
	 */
	public static float getFloat(Map map, Object key, double defValue) {
		return ConvertUtil.obj2Float(map.get(key), defValue);
	}

	/**
	 * <b>获得Double值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Double值
	 */
	public static Double getDouble(Map map, Object key) {
		return ConvertUtil.obj2Double(map.get(key));
	}

	/**
	 * <b>获得Double值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Double值
	 */
	public static Double getDouble(Map map, Object key, Double defValue) {
		Double value = ConvertUtil.obj2Double(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得double值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return double值
	 */
	public static double getDouble(Map map, Object key, double defValue) {
		return ConvertUtil.obj2Double(map.get(key), defValue);
	}

	/**
	 * <b>获得BigInteger值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return BigInteger值
	 */
	public static BigInteger getBigInteger(Map map, Object key) {
		return ConvertUtil.obj2BigInteger(map.get(key));
	}

	/**
	 * <b>获得BigInteger值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return BigInteger值
	 */
	public static BigInteger getBigInteger(Map map, Object key, BigInteger defValue) {
		BigInteger value = ConvertUtil.obj2BigInteger(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得BigInteger值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return BigInteger值
	 */
	public static BigInteger getBigInteger(Map map, Object key, long defValue) {
		BigInteger value = ConvertUtil.obj2BigInteger(map.get(key));
		return value == null ? BigInteger.valueOf(defValue) : value;
	}

	/**
	 * <b>获得BigDecimal值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return BigDecimal值
	 */
	public static BigDecimal getBigDecimal(Map map, Object key) {
		return ConvertUtil.obj2BigDecimal(map.get(key));
	}

	/**
	 * <b>获得BigDecimal值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return BigDecimal值
	 */
	public static BigDecimal getBigDecimal(Map map, Object key, BigDecimal defValue) {
		BigDecimal value = ConvertUtil.obj2BigDecimal(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得BigDecimal值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return BigDecimal值
	 */
	public static BigDecimal getBigDecimal(Map map, Object key, double defValue) {
		BigDecimal value = ConvertUtil.obj2BigDecimal(map.get(key));
		return value == null ? BigDecimal.valueOf(defValue) : value;
	}

	/**
	 * <b>获得Date值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Date值
	 */
	public static Date getDate(Map map, Object key) {
		return ConvertUtil.obj2Date(map.get(key));
	}

	/**
	 * <b>获得Date值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Date值
	 */
	public static Date getDate(Map map, Object key, java.util.Date defValue) {
		java.util.Date value = ConvertUtil.obj2Date(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toDate(value);
	}

	/**
	 * <b>获得Time值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Time值
	 */
	public static Time getTime(Map map, Object key) {
		return ConvertUtil.obj2Time(map.get(key));
	}

	/**
	 * <b>获得Time值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Time值
	 */
	public static Time getTime(Map map, Object key, java.util.Date defValue) {
		java.util.Date value = ConvertUtil.obj2Time(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toTime(value);
	}

	/**
	 * <b>获得Timestamp值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Timestamp值
	 */
	public static Timestamp getTimestamp(Map map, Object key) {
		return ConvertUtil.obj2Timestamp(map.get(key));
	}

	/**
	 * <b>获得Timestamp值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Timestamp值
	 */
	public static Timestamp getTimestamp(Map map, Object key, java.util.Date defValue) {
		java.util.Date value = ConvertUtil.obj2Timestamp(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toTimestamp(value);
	}

	/**
	 * <b>获得java.util.Date值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return java.util.Date值
	 */
	public static java.util.Date getDateTime(Map map, Object key) {
		return ConvertUtil.obj2DateTime(map.get(key));
	}

	/**
	 * <b>获得java.util.Date值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return java.util.Date值
	 */
	public static java.util.Date getDateTime(Map map, Object key, java.util.Date defValue) {
		java.util.Date value = ConvertUtil.obj2DateTime(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得新日期值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return 新日期值
	 */
	public static TemporalAccessor getTemporal(Map map, Object key) {
		return ConvertUtil.obj2Temporal(map.get(key));
	}

	/**
	 * <b>获得新日期值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return 新日期值
	 */
	public static TemporalAccessor getTemporal(Map map, Object key, TemporalAccessor defValue) {
		TemporalAccessor value = ConvertUtil.obj2Temporal(map.get(key));
		return value == null ? defValue : value;
	}

	/**
	 * <b>获得新日期值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return 新日期值
	 */
	public static LocalDate getLocalDate(Map map, Object key) {
		return ConvertUtil.obj2LocalDate(map.get(key));
	}

	/**
	 * <b>获得新日期值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return 新日期值
	 */
	public static LocalDate getLocalDate(Map map, Object key, TemporalAccessor defValue) {
		TemporalAccessor value = ConvertUtil.obj2Temporal(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toLocalDate(value);
	}

	/**
	 * <b>获得新时间值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return 新时间值
	 */
	public static LocalTime getLocalTime(Map map, Object key) {
		return ConvertUtil.obj2LocalTime(map.get(key));
	}

	/**
	 * <b>获得新时间值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return 新时间值
	 */
	public static LocalTime getLocalTime(Map map, Object key, TemporalAccessor defValue) {
		TemporalAccessor value = ConvertUtil.obj2Temporal(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toLocalTime(value);
	}

	/**
	 * <b>获得新日期时间值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return 新日期时间值
	 */
	public static LocalDateTime getLocalDateTime(Map map, Object key) {
		return ConvertUtil.obj2LocalDateTime(map.get(key));
	}

	/**
	 * <b>获得新日期时间值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return 新日期时间值
	 */
	public static LocalDateTime getLocalDateTime(Map map, Object key, TemporalAccessor defValue) {
		TemporalAccessor value = ConvertUtil.obj2Temporal(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toLocalDateTime(value);
	}

	/**
	 * <b>获得Instant值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @return Instant值
	 */
	public static Instant getInstant(Map map, Object key) {
		return ConvertUtil.obj2Instant(map.get(key));
	}

	/**
	 * <b>获得Instant值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @param key key
	 * @param defValue 默认值
	 * @return Instant值
	 */
	public static Instant getInstant(Map map, Object key, TemporalAccessor defValue) {
		TemporalAccessor value = ConvertUtil.obj2Temporal(map.get(key));
		value = value == null ? defValue : value;
		return ConvertUtil.toInstant(value);
	}
}
