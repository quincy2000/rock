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

/**
 * <b>数据类型转换工具类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 下午1:28:44</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ConvertUtil {

	/**
	 * <b>将对象值转成Byte值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Byte值
	 */
	public static Byte obj2Byte(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Byte) {
			return (Byte) v;
		} else if (v instanceof Number) {
			return ((Number) v).byteValue();
		} else if (v instanceof Boolean) {
			return (byte) CoreUtil.boolean2int((Boolean) v);
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : Byte.valueOf(str);
		}
	}

	/**
	 * <b>将对象值转成byte值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return byte值
	 */
	public static byte obj2Byte(Object v, int defaultValue) {
		try {
			Byte result = obj2Byte(v);
			return result == null ? (byte) defaultValue : result.byteValue();
		} catch (Exception ex) {
			return (byte) defaultValue;
		}
	}

	/**
	 * <b>将对象值转成Short值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Short值
	 */
	public static Short obj2Short(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Short) {
			return (Short) v;
		} else if (v instanceof Number) {
			return ((Number) v).shortValue();
		} else if (v instanceof Boolean) {
			return (short) CoreUtil.boolean2int((Boolean) v);
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : Short.valueOf(str);
		}
	}

	/**
	 * <b>将对象值转成short值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return short值
	 */
	public static short obj2Short(Object v, int defaultValue) {
		try {
			Short result = obj2Short(v);
			return result == null ? (short) defaultValue : result.shortValue();
		} catch (Exception ex) {
			return (short) defaultValue;
		}
	}

	/**
	 * <b>将对象值转成Integer值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Integer值
	 */
	public static Integer obj2Integer(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Integer) {
			return (Integer) v;
		} else if (v instanceof Number) {
			return ((Number) v).intValue();
		} else if (v instanceof TemporalAccessor) {
			return (int) Instant.from((TemporalAccessor) v).toEpochMilli();
		} else if (v instanceof java.util.Date) {
			return (int) ((java.util.Date) v).getTime();
		} else if (v instanceof Boolean) {
			return CoreUtil.boolean2int((Boolean) v);
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : Integer.valueOf(str);
		}
	}

	/**
	 * <b>将对象值转成int值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return int值
	 */
	public static int obj2Integer(Object v, int defaultValue) {
		try {
			Integer result = obj2Integer(v);
			return result == null ? defaultValue : result.intValue();
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	/**
	 * <b>将对象值转成Long值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Long值
	 */
	public static Long obj2Long(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Long) {
			return (Long) v;
		} else if (v instanceof Number) {
			return ((Number) v).longValue();
		} else if (v instanceof TemporalAccessor) {
			return Instant.from((TemporalAccessor) v).toEpochMilli();
		} else if (v instanceof java.util.Date) {
			return ((java.util.Date) v).getTime();
		} else if (v instanceof Boolean) {
			return (long) CoreUtil.boolean2int((Boolean) v);
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : Long.valueOf(str);
		}
	}

	/**
	 * <b>将对象值转成long值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return long值
	 */
	public static long obj2Long(Object v, long defaultValue) {
		try {
			Long result = obj2Long(v);
			return result == null ? defaultValue : result.longValue();
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	/**
	 * <b>将对象值转成Double值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Double值
	 */
	public static Double obj2Double(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Double) {
			return (Double) v;
		} else if (v instanceof Float) {
			BigDecimal dec = new BigDecimal(v.toString());
			return dec.doubleValue();
		} else if (v instanceof Number) {
			return ((Number) v).doubleValue();
		} else if (v instanceof TemporalAccessor) {
			return (double) Instant.from((TemporalAccessor) v).toEpochMilli();
		} else if (v instanceof java.util.Date) {
			return (double) ((java.util.Date) v).getTime();
		} else if (v instanceof Boolean) {
			return (double) CoreUtil.boolean2int((Boolean) v);
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : Double.valueOf(str);
		}
	}

	/**
	 * <b>将对象值转成double值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return double值
	 */
	public static double obj2Double(Object v, double defaultValue) {
		try {
			Double result = obj2Double(v);
			return result == null ? defaultValue : result.doubleValue();
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	/**
	 * <b>将对象值转成Float值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Float值
	 */
	public static Float obj2Float(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Float) {
			return (Float) v;
		} else if (v instanceof Number) {
			return ((Number) v).floatValue();
		} else if (v instanceof TemporalAccessor) {
			return (float) Instant.from((TemporalAccessor) v).toEpochMilli();
		} else if (v instanceof java.util.Date) {
			return (float) ((java.util.Date) v).getTime();
		} else if (v instanceof Boolean) {
			return (float) CoreUtil.boolean2int((Boolean) v);
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : Float.valueOf(str);
		}
	}

	/**
	 * <b>将对象值转成float值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return float值
	 */
	public static float obj2Float(Object v, double defaultValue) {
		try {
			Float result = obj2Float(v);
			return result == null ? (float) defaultValue : result.floatValue();
		} catch (Exception ex) {
			return (float) defaultValue;
		}
	}

	/**
	 * <b>将对象值转成BigDecimal值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return BigDecimal值
	 */
	public static BigDecimal obj2BigDecimal(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof BigDecimal) {
			return (BigDecimal) v;
		} else if (v instanceof BigInteger) {
			return new BigDecimal((BigInteger) v);
		} else if (v instanceof Float) {
			return new BigDecimal(v.toString());
		} else if (v instanceof Number) {
			return BigDecimal.valueOf(((Number) v).doubleValue());
		} else if (v instanceof TemporalAccessor) {
			return BigDecimal.valueOf(Instant.from((TemporalAccessor) v).toEpochMilli());
		} else if (v instanceof java.util.Date) {
			return BigDecimal.valueOf(((java.util.Date) v).getTime());
		} else if (v instanceof Boolean) {
			return BigDecimal.valueOf(CoreUtil.boolean2int((Boolean) v));
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : new BigDecimal(str);
		}
	}

	/**
	 * <b>将对象值转成BigInteger值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return BigInteger值
	 */
	public static BigInteger obj2BigInteger(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof BigInteger) {
			return (BigInteger) v;
		} else if (v instanceof BigDecimal) {
			return ((BigDecimal) v).toBigInteger();
		} else if (v instanceof Number) {
			return BigInteger.valueOf(((Number) v).longValue());
		} else if (v instanceof TemporalAccessor) {
			return BigInteger.valueOf(Instant.from((TemporalAccessor) v).toEpochMilli());
		} else if (v instanceof java.util.Date) {
			return BigInteger.valueOf(((java.util.Date) v).getTime());
		} else if (v instanceof Boolean) {
			return BigInteger.valueOf(CoreUtil.boolean2int((Boolean) v));
		} else {
			String str = v.toString();
			return StringUtil.isBlank(str) ? null : new BigInteger(str);
		}
	}

	/**
	 * <b>将对象值转成Boolean值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Boolean值
	 */
	public static Boolean obj2Boolean(Object v) {
		Boolean r = null;
		if (v instanceof Boolean) {
			r = (Boolean) v;
		} else if (v instanceof Number) {
			r = CoreUtil.int2boolean(((Number) v).intValue());
		} else if (v != null) {
			r = CoreUtil.string2Boolean(v.toString());
		}
		return r;
	}

	/**
	 * <b>将对象值转成boolean值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param defaultValue 默认值
	 * @return boolean值
	 */
	public static boolean obj2Boolean(Object v, boolean defaultValue) {
		try {
			Boolean result = obj2Boolean(v);
			return result == null ? defaultValue : result.booleanValue();
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	/**
	 * <b>将对象值转成String值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return String值
	 */
	public static String obj2String(Object v) {
		String r = null;
		if (v instanceof String) {
			r = (String) v;
		} else if (v instanceof TemporalAccessor) {
			r = LocalDateUtil.DEFAULT_DATETIME_PATTERN_FMT.format((TemporalAccessor) v);
		} else if (v instanceof Date || v instanceof Time || v instanceof Timestamp) {
			r = v.toString();
		} else if (v instanceof java.util.Date) {
			r = DateUtil.DEFAULT_DATETIME_PATTERN_FMT.format((java.util.Date) v);
		} else if (v instanceof Number) {
			r = v.toString();
		} else if (v != null) {
			r = v.toString();
		}
		return r;
	}

	/**
	 * <b>将对象值转成String值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @param format 格式字符串
	 * @return String值
	 */
	public static String obj2String(Object v, String format) {
		String r = null;
		if (v instanceof String) {
			r = (String) v;
		} else if (v instanceof TemporalAccessor) {
			r = format == null ? LocalDateUtil.DEFAULT_DATETIME_PATTERN_FMT.format((TemporalAccessor) v)
					: LocalDateUtil.format((TemporalAccessor) v, format);
		} else if (v instanceof Date || v instanceof Time || v instanceof Timestamp) {
			if (format != null)
				r = DateUtil.format((java.util.Date) v, format);
			else
				r = v.toString();
		} else if (v instanceof java.util.Date) {
			if (format != null)
				r = DateUtil.format((java.util.Date) v, format);
			else
				r = DateUtil.DEFAULT_DATETIME_PATTERN_FMT.format((java.util.Date) v);
		} else if (v instanceof Number) {
			if (format != null)
				r = NumberUtil.formatNumber((Number) v, format);
			else
				r = v.toString();
		} else if (v != null) {
			r = v.toString();
		}
		return r;
	}

	/**
	 * <b>将对象值转成Date值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Date值
	 */
	public static Date obj2Date(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof TemporalAccessor) {
			return new Date(LocalDateUtil.getTime(((TemporalAccessor) v)));
		} else if (v instanceof Date) {
			return (Date) v;
		} else if (v instanceof java.util.Date) {
			return new Date(((java.util.Date) v).getTime());
		} else if (v instanceof Number) {
			return new Date(((Number) v).longValue());
		} else {
			String str = v.toString();
			if (StringUtil.isBlank(str))
				return null;
			else {
				long time = DateUtil.parseDate(str).getTime();
				return new Date(time);
			}
		}
	}

	/**
	 * <b>将对象值转成Time值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Time值
	 */
	public static Time obj2Time(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof TemporalAccessor) {
			return new Time(LocalDateUtil.getTime(((TemporalAccessor) v)));
		} else if (v instanceof Time) {
			return (Time) v;
		} else if (v instanceof java.util.Date) {
			return new Time(((java.util.Date) v).getTime());
		} else if (v instanceof Number) {
			return new Time(((Number) v).longValue());
		} else {
			String str = v.toString();
			if (StringUtil.isBlank(str))
				return null;
			else {
				long time = DateUtil.parseTime(str).getTime();
				return new Time(time);
			}
		}
	}

	/**
	 * <b>将对象值转成Timestamp值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Timestamp值
	 */
	public static Timestamp obj2Timestamp(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof TemporalAccessor) {
			return new Timestamp(LocalDateUtil.getTime(((TemporalAccessor) v)));
		} else if (v instanceof Timestamp) {
			return (Timestamp) v;
		} else if (v instanceof java.util.Date) {
			return new Timestamp(((java.util.Date) v).getTime());
		} else if (v instanceof Number) {
			return new Timestamp(((Number) v).longValue());
		} else {
			String str = v.toString();
			if (StringUtil.isBlank(str))
				return null;
			else {
				long time = DateUtil.parseDateTime(str).getTime();
				return new Timestamp(time);
			}
		}
	}

	/**
	 * <b>将对象值转成DateTime值。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return DateTime值
	 */
	public static java.util.Date obj2DateTime(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof TemporalAccessor) {
			return new java.util.Date(LocalDateUtil.getTime(((TemporalAccessor) v)));
		} else if (v instanceof java.util.Date) {
			return (java.util.Date) v;
		} else if (v instanceof Number) {
			return new java.util.Date(((Number) v).longValue());
		} else {
			String str = v.toString();
			if (StringUtil.isBlank(str))
				return null;
			else {
				return DateUtil.parseDateTime(str);
			}
		}
	}

	/**
	 * <b>将对象转成新日期类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return 新的日期时间值
	 */
	public static TemporalAccessor obj2Temporal(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof TemporalAccessor) {
			return (TemporalAccessor) v;
		} else if (v instanceof java.util.Date) {
			return ((java.util.Date) v).toInstant();
		} else if (v instanceof Number) {
			return Instant.ofEpochMilli(((Number) v).longValue());
		} else {
			String str = v.toString();
			if (StringUtil.isBlank(str))
				return null;
			else {
				return LocalDateUtil.parseDateTime(str);
			}
		}
	}

	/**
	 * <b>将对象转成新日期类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return 新的日期值
	 */
	public static LocalDate obj2LocalDate(Object v) {
		return toLocalDate(obj2Temporal(v));
	}

	/**
	 * <b>将对象转成新时间类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return 新的时间值
	 */
	public static LocalTime obj2LocalTime(Object v) {
		return toLocalTime(obj2Temporal(v));
	}

	/**
	 * <b>将对象转成新日期时间类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return 新的日期时间值
	 */
	public static LocalDateTime obj2LocalDateTime(Object v) {
		return toLocalDateTime(obj2Temporal(v));
	}

	/**
	 * <b>将对象转成Instant类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return Instant
	 */
	public static Instant obj2Instant(Object v) {
		return toInstant(obj2Temporal(v));
	}

	/**
	 * <b>toDate。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value java.util.Date
	 * @return java.sql.Date
	 */
	public static Date toDate(java.util.Date value) {
		if (value == null)
			return null;
		else if (value instanceof Date)
			return (Date) value;
		else
			return new Date(value.getTime());
	}

	/**
	 * <b>toTime。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value java.util.Date
	 * @return java.sql.Time
	 */
	public static Time toTime(java.util.Date value) {
		if (value == null)
			return null;
		else if (value instanceof Time)
			return (Time) value;
		else
			return new Time(value.getTime());
	}

	/**
	 * <b>toTimestamp。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value java.util.Date
	 * @return java.sql.Timestamp
	 */
	public static Timestamp toTimestamp(java.util.Date value) {
		if (value == null)
			return null;
		else if (value instanceof Timestamp)
			return (Timestamp) value;
		else
			return new Timestamp(value.getTime());
	}

	/**
	 * <b>toLocalDate。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value TemporalAccessor
	 * @return java.time.LocalDate
	 */
	public static LocalDate toLocalDate(TemporalAccessor value) {
		if (value == null)
			return null;
		else if (value instanceof LocalDate)
			return (LocalDate) value;
		else
			return LocalDate.from(value);
	}

	/**
	 * <b>toLocalTime。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value TemporalAccessor
	 * @return java.time.LocalTime
	 */
	public static LocalTime toLocalTime(TemporalAccessor value) {
		if (value == null)
			return null;
		else if (value instanceof LocalTime)
			return (LocalTime) value;
		else
			return LocalTime.from(value);
	}

	/**
	 * <b>toLocalDateTime。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value TemporalAccessor
	 * @return java.time.LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(TemporalAccessor value) {
		if (value == null)
			return null;
		else if (value instanceof LocalDateTime)
			return (LocalDateTime) value;
		else
			return LocalDateTime.from(value);
	}

	/**
	 * <b>toInstant。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value TemporalAccessor
	 * @return java.time.Instant
	 */
	public static Instant toInstant(TemporalAccessor value) {
		if (value == null)
			return null;
		else if (value instanceof Instant)
			return (Instant) value;
		else
			return Instant.from(value);
	}
}