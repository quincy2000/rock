package org.quincy.rock.core.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import org.quincy.rock.core.exception.ParseException;

/**
 * <b>LocalDateUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月25日 上午11:56:38</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class LocalDateUtil {

	//
	public static final DateTimeFormatter DEFAULT_DATETIME_PATTERN_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DEFAULT_DATETIME_PATTERN);
	public static final DateTimeFormatter DEFAULT_DATE_PATTERN_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DEFAULT_DATE_PATTERN);
	public static final DateTimeFormatter DEFAULT_TIME_PATTERN_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DEFAULT_TIME_PATTERN);

	//
	public static final DateTimeFormatter DATE_PATTERN_LINE_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATE_PATTERN_LINE);
	public static final DateTimeFormatter DATE_PATTERN_SLASH_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATE_PATTERN_SLASH);
	public static final DateTimeFormatter TIME_PATTERN_FMT = DateTimeFormatter.ofPattern(DateUtil.TIME_PATTERN);
	public static final DateTimeFormatter TIME_PATTERN_S_FMT = DateTimeFormatter.ofPattern(DateUtil.TIME_PATTERN_S);
	public static final DateTimeFormatter TIME_PATTERN_S_DOT_FMT = DateTimeFormatter
			.ofPattern(DateUtil.TIME_PATTERN_S_DOT);
	public static final DateTimeFormatter DATETIME_PATTERN_LINE_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATETIME_PATTERN_LINE);
	public static final DateTimeFormatter DATETIME_PATTERN_LINE_S_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATETIME_PATTERN_LINE_S);
	public static final DateTimeFormatter DATETIME_PATTERN_LINE_S_DOT_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATETIME_PATTERN_LINE_S_DOT);
	public static final DateTimeFormatter DATETIME_PATTERN_SLASH_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATETIME_PATTERN_SLASH);
	public static final DateTimeFormatter DATETIME_PATTERN_SLASH_S_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATETIME_PATTERN_SLASH_S);
	public static final DateTimeFormatter DATETIME_PATTERN_SLASH_S_DOT_FMT = DateTimeFormatter
			.ofPattern(DateUtil.DATETIME_PATTERN_SLASH_S_DOT);
	//
	public static final DateTimeFormatter[] DEFAULT_DATE_PATTERNS = new DateTimeFormatter[] { DATE_PATTERN_LINE_FMT,
			DATE_PATTERN_SLASH_FMT, DATETIME_PATTERN_LINE_FMT, DATETIME_PATTERN_SLASH_FMT, DATETIME_PATTERN_LINE_S_FMT,
			DATETIME_PATTERN_SLASH_S_FMT, DATETIME_PATTERN_LINE_S_DOT_FMT, DATETIME_PATTERN_SLASH_S_DOT_FMT,
			TIME_PATTERN_FMT, TIME_PATTERN_S_FMT, TIME_PATTERN_S_DOT_FMT };
	public static final DateTimeFormatter[] DEFAULT_TIME_PATTERNS = new DateTimeFormatter[] { TIME_PATTERN_FMT,
			TIME_PATTERN_S_FMT, TIME_PATTERN_S_DOT_FMT, DATETIME_PATTERN_LINE_FMT, DATETIME_PATTERN_LINE_S_FMT,
			DATETIME_PATTERN_LINE_S_DOT_FMT, DATETIME_PATTERN_SLASH_FMT, DATETIME_PATTERN_SLASH_S_FMT,
			DATETIME_PATTERN_SLASH_S_DOT_FMT, DATE_PATTERN_LINE_FMT, DATE_PATTERN_SLASH_FMT };
	public static final DateTimeFormatter[] DEFAULT_DATETIME_PATTERNS = new DateTimeFormatter[] {
			DATETIME_PATTERN_LINE_FMT, DATETIME_PATTERN_SLASH_FMT, DATE_PATTERN_LINE_FMT, DATE_PATTERN_SLASH_FMT,
			DATETIME_PATTERN_LINE_S_FMT, DATETIME_PATTERN_SLASH_S_FMT, DATETIME_PATTERN_LINE_S_DOT_FMT,
			DATETIME_PATTERN_SLASH_S_DOT_FMT, TIME_PATTERN_FMT, TIME_PATTERN_S_FMT, TIME_PATTERN_S_DOT_FMT };

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	private LocalDateUtil() {
	}

	/**
	 * <b>获得毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param temporal Temporal
	 * @return 毫秒数
	 */
	public static long getTime(TemporalAccessor temporal) {
		return Instant.from(temporal).toEpochMilli();
	}

	/**
	 * <b>解析日期串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 日期串
	 * @param pattern 日期格式串
	 * @return 日期
	 */
	public static <T extends TemporalAccessor> T parse(String str, String pattern) {
		if (StringUtil.isBlank(str))
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return (T) formatter.parse(str);
	}

	/**
	 * <b>解析日期串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 日期串
	 * @param pattern 日期格式串
	 * @param locale 本地化对象
	 * @return 日期
	 */
	public static <T extends TemporalAccessor> T parse(String str, String pattern, Locale locale) {
		if (StringUtil.isBlank(str))
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
		return (T) formatter.parse(str);
	}

	/**
	 * <b>格式化日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dt 日期对象
	 * @param pattern 日期格式串
	 * @return 日期字符串
	 */
	public static String format(TemporalAccessor temporal, String pattern) {
		if (temporal == null)
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return formatter.format(temporal);
	}

	/**
	 * <b>格式化日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dt 日期对象
	 * @param pattern 日期格式串
	 * @param locale 本地化对象
	 * @return 日期字符串
	 */
	public static String format(TemporalAccessor temporal, String pattern, Locale locale) {
		if (temporal == null)
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
		return formatter.format(temporal);
	}

	private static <T extends TemporalAccessor> T parseDateTime(String str, DateTimeFormatter[] fmts) {
		for (DateTimeFormatter fmt : fmts) {
			try {
				return (T) fmt.parse(str);
			} catch (Exception e) {

			}
		}
		throw new ParseException("Unable to parse the date: " + str);
	}

	/**
	 * <b>解析日期时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return 日期时间
	 */
	public static <T extends TemporalAccessor> T parseDateTime(String str) {
		return parseDateTime(str, DEFAULT_DATETIME_PATTERNS);
	}

	/**
	 * <b>解析日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return 日期
	 */
	public static <T extends TemporalAccessor> T parseDate(String str) {
		return parseDateTime(str, DEFAULT_DATE_PATTERNS);
	}

	/**
	 * <b>解析时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return 时间
	 */
	public static <T extends TemporalAccessor> T parseTime(String str) {
		return parseDateTime(str, DEFAULT_TIME_PATTERNS);
	}
}
