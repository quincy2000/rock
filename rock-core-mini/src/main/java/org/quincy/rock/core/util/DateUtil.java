package org.quincy.rock.core.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.exception.ParseException;
import org.quincy.rock.core.function.Function;

/**
 * <b>DateUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 下午12:58:41</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DateUtil {
	/**
	 * SECOND_UNIT_MINUTE。
	 */
	public static final int SECOND_UNIT_MINUTE = 60;
	/**
	 * SECOND_UNIT_HOUR。
	 */
	public static final int SECOND_UNIT_HOUR = SECOND_UNIT_MINUTE * 60;
	/**
	 * SECOND_UNIT_HOUR。
	 */
	public static final int SECOND_UNIT_DAY = SECOND_UNIT_HOUR * 24;
	/**
	 * SECOND_UNIT_HOUR。
	 */
	public static final int SECOND_UNIT_WEEK = SECOND_UNIT_DAY * 7;
	/**
	 * MILLISECOND_UNIT_SECOND。
	 */
	public static final int MILLISECOND_UNIT_SECOND = 1000;
	/**
	 * MILLISECOND_UNIT_MINUTE。
	 */
	public static final int MILLISECOND_UNIT_MINUTE = SECOND_UNIT_MINUTE * MILLISECOND_UNIT_SECOND;
	/**
	 * MILLISECOND_UNIT_HOUR。
	 */
	public static final int MILLISECOND_UNIT_HOUR = SECOND_UNIT_HOUR * MILLISECOND_UNIT_SECOND;
	/**
	 * MILLISECOND_UNIT_DAY。
	 */
	public static final int MILLISECOND_UNIT_DAY = SECOND_UNIT_DAY * MILLISECOND_UNIT_SECOND;
	/**
	 * MILLISECOND_UNIT_WEEK。
	 */
	public static final int MILLISECOND_UNIT_WEEK = SECOND_UNIT_WEEK * MILLISECOND_UNIT_SECOND;

	/**
	 * DEFAULT_WEEKNAMES。
	 */
	public static final String[] DEFAULT_WEEKNAMES = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday" };

	/**
	 * DEFAULT_MONTHNAMES。
	 */
	public static final String[] DEFAULT_MONTHNAMES = new String[] { "January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December" };

	//
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	//
	public static final String DATE_PATTERN_LINE = "yyyy-M-d";
	public static final String DATE_PATTERN_SLASH = "yyyy/M/d";
	public static final String TIME_PATTERN = "H:m:s";
	public static final String TIME_PATTERN_S = "H:m:s S";
	public static final String TIME_PATTERN_S_DOT = "H:m:s.S";
	public static final String DATETIME_PATTERN_LINE = "yyyy-M-d H:m:s";
	public static final String DATETIME_PATTERN_LINE_S = "yyyy-M-d H:m:s S";
	public static final String DATETIME_PATTERN_LINE_S_DOT = "yyyy-M-d H:m:s.S";
	public static final String DATETIME_PATTERN_SLASH = "yyyy/M/d H:m:s";
	public static final String DATETIME_PATTERN_SLASH_S = "yyyy/M/d H:m:s S";
	public static final String DATETIME_PATTERN_SLASH_S_DOT = "yyyy/M/d H:m:s.S";
	//
	public static SimpleDateFormat DEFAULT_DATETIME_PATTERN_FMT = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
	public static SimpleDateFormat DEFAULT_DATE_PATTERN_FMT = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
	public static SimpleDateFormat DEFAULT_TIME_PATTERN_FMT = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
	//
	public static final SimpleDateFormat DATE_PATTERN_LINE_FMT = new SimpleDateFormat(DATE_PATTERN_LINE);
	public static final SimpleDateFormat DATE_PATTERN_SLASH_FMT = new SimpleDateFormat(DATE_PATTERN_SLASH);
	public static final SimpleDateFormat TIME_PATTERN_FMT = new SimpleDateFormat(TIME_PATTERN);
	public static final SimpleDateFormat TIME_PATTERN_S_FMT = new SimpleDateFormat(TIME_PATTERN_S);
	public static final SimpleDateFormat TIME_PATTERN_S_DOT_FMT = new SimpleDateFormat(TIME_PATTERN_S_DOT);
	public static final SimpleDateFormat DATETIME_PATTERN_LINE_FMT = new SimpleDateFormat(DATETIME_PATTERN_LINE);
	public static final SimpleDateFormat DATETIME_PATTERN_LINE_S_FMT = new SimpleDateFormat(DATETIME_PATTERN_LINE_S);
	public static final SimpleDateFormat DATETIME_PATTERN_LINE_S_DOT_FMT = new SimpleDateFormat(
			DATETIME_PATTERN_LINE_S_DOT);
	public static final SimpleDateFormat DATETIME_PATTERN_SLASH_FMT = new SimpleDateFormat(DATETIME_PATTERN_SLASH);
	public static final SimpleDateFormat DATETIME_PATTERN_SLASH_S_FMT = new SimpleDateFormat(DATETIME_PATTERN_SLASH_S);
	public static final SimpleDateFormat DATETIME_PATTERN_SLASH_S_DOT_FMT = new SimpleDateFormat(
			DATETIME_PATTERN_SLASH_S_DOT);

	//
	public static final SimpleDateFormat[] DEFAULT_DATE_PATTERNS = new SimpleDateFormat[] { DATE_PATTERN_LINE_FMT,
			DATE_PATTERN_SLASH_FMT, DATETIME_PATTERN_LINE_FMT, DATETIME_PATTERN_SLASH_FMT, DATETIME_PATTERN_LINE_S_FMT,
			DATETIME_PATTERN_SLASH_S_FMT, DATETIME_PATTERN_LINE_S_DOT_FMT, DATETIME_PATTERN_SLASH_S_DOT_FMT,
			TIME_PATTERN_FMT, TIME_PATTERN_S_FMT, TIME_PATTERN_S_DOT_FMT };
	public static final SimpleDateFormat[] DEFAULT_TIME_PATTERNS = new SimpleDateFormat[] { TIME_PATTERN_FMT,
			TIME_PATTERN_S_FMT, TIME_PATTERN_S_DOT_FMT, DATETIME_PATTERN_LINE_FMT, DATETIME_PATTERN_LINE_S_FMT,
			DATETIME_PATTERN_LINE_S_DOT_FMT, DATETIME_PATTERN_SLASH_FMT, DATETIME_PATTERN_SLASH_S_FMT,
			DATETIME_PATTERN_SLASH_S_DOT_FMT, DATE_PATTERN_LINE_FMT, DATE_PATTERN_SLASH_FMT };
	public static final SimpleDateFormat[] DEFAULT_DATETIME_PATTERNS = new SimpleDateFormat[] {
			DATETIME_PATTERN_LINE_FMT, DATETIME_PATTERN_SLASH_FMT, DATE_PATTERN_LINE_FMT, DATE_PATTERN_SLASH_FMT,
			DATETIME_PATTERN_LINE_S_FMT, DATETIME_PATTERN_SLASH_S_FMT, DATETIME_PATTERN_LINE_S_DOT_FMT,
			DATETIME_PATTERN_SLASH_S_DOT_FMT, TIME_PATTERN_FMT, TIME_PATTERN_S_FMT, TIME_PATTERN_S_DOT_FMT };

	/**
	 * wordTimes。
	 */
	private static Map<String, Function<Date, Long>> wordTimes = new HashMap();

	static {
		wordTimes.put("now", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				return now.getTime();
			}
		});
		wordTimes.put("date", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				return clearTime(now).getTime();
			}
		});
		wordTimes.put("today", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				return clearTime(now).getTime();
			}
		});
		wordTimes.put("today_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				return clearTime(now).getTime();
			}
		});
		wordTimes.put("today_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.set(Calendar.HOUR, 59);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.set(Calendar.MILLISECOND, 999);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("time", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				return clearDate(now).getTime();
			}
		});
		wordTimes.put("yesterday", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.DATE, -1);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("yesterday_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.DATE, -1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("yesterday_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("tomorrow", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.DATE, 1);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("tomorrow_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("tomorrow_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.DATE, 2);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("month_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("month_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.MONTH, 1);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("lastmonth", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.MONTH, -1);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("lastmonth_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("lastmonth_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("nextmonth", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.MONTH, 1);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("nextmonth_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.MONTH, 1);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("nextmonth_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("year_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.clear(Calendar.MONTH);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("year_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR, 1);
				c.clear(Calendar.MONTH);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("lastyear", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR, -1);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("lastyear_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR, -1);
				c.clear(Calendar.MONTH);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("lastyear_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.clear(Calendar.MONTH);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
		wordTimes.put("nextyear", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR, 1);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("nextyear_begin", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR, 1);
				c.clear(Calendar.MONTH);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis();
			}
		});
		wordTimes.put("nextyear_end", new Function<Date, Long>() {
			@Override
			public Long call(Date now) {
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				c.add(Calendar.YEAR, 2);
				c.clear(Calendar.MONTH);
				c.set(Calendar.DATE, 1);
				clearTime(c);
				return c.getTimeInMillis() - 1;
			}
		});
	}

	/**
	 * <b>clearTime。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param d
	 * @return
	 */
	public static Date clearTime(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c = clearTime(c);
		return c.getTime();
	}

	/**
	 * <b>clearTime。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param c
	 * @return
	 */
	public static Calendar clearTime(Calendar c) {
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * <b>clearDate。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param d
	 * @return
	 */
	public static Date clearDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		clearDate(c);
		return c.getTime();
	}

	/**
	 * <b>clearDate。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param c
	 * @return
	 */
	public static Calendar clearDate(Calendar c) {
		c.clear(Calendar.YEAR);
		c.clear(Calendar.MONTH);
		c.set(Calendar.DATE, 1);
		return c;
	}

	/**
	 * <b>根据英文单词获得时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 支持以下word:<br>
	 * now/date/time/today/yesterday/tomorrow/lastmonth/nextmonth/
	 * lastyear/nextyear/month_begin/month_end。<br>
	 * today_begin/today_end/yesterday_begin/yesterday_end/
	 * tomorrow_begin/tomorrow_end/lastmonth_begin/lastmonth_end/
	 * nextmonth_begin/nextmonth_end/lastyear_begin/lastyear_end/
	 * nextyear_begin/nextyear_end
	 * @param word 单词(now/today/date/time ...)
	 * @return 单词对应的日期long
	 */
	public static long getTimeByWord(String word) {
		return getTimeByWord(word, new Date());
	}

	/**
	 * <b>根据英文单词获得时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 支持以下word:<br>
	 * now/date/time/today/yesterday/tomorrow/lastmonth/nextmonth/
	 * lastyear/nextyear/month_begin/month_end/year_begin/year_end。<br>
	 * today_begin/today_end/yesterday_begin/yesterday_end/
	 * tomorrow_begin/tomorrow_end/lastmonth_begin/lastmonth_end/
	 * nextmonth_begin/nextmonth_end/lastyear_begin/lastyear_end/
	 * nextyear_begin/nextyear_end
	 * @param word 单词(now/today/date/time ...)
	 * @param now 基准日期
	 * @return 单词对应的日期long
	 * @throws IllegalArgumentException
	 */
	public static long getTimeByWord(String word, Date now) {
		return wordTimes.get(word).call(now);
	}

	/**
	 * <b>根据英文单词获得日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 请参考getTimeByWord方法。
	 * @param word 单词(now/today/date/time ...)
	 * @return 单词对应的日期
	 */
	public static Date getDateByWord(String word) {
		return getDateByWord(word, new Date());
	}

	/**
	 * <b>根据英文单词获得日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 请参考getTimeByWord方法。
	 * @param word 单词(now/today/date/time ...)
	 * @param now 基准日期
	 * @return 单词对应的日期
	 */
	public static Date getDateByWord(String word, Date now) {
		Long time = getTimeByWord(word, now);
		return new Date(time);
	}

	/**
	 * <b>解析日期串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dtStr 日期串
	 * @param pattern 日期格式串
	 * @return 日期
	 */
	public static Date parse(String dtStr, String pattern) {
		if (dtStr == null || dtStr.trim().length() == 0)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dtStr);
		} catch (Exception e) {
			throw new ParseException(e.getMessage(), e);
		}
	}

	/**
	 * <b>解析日期串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dtStr 日期串
	 * @param pattern 日期格式串
	 * @param locale 本地化对象
	 * @return 日期
	 */
	public static Date parse(String dtStr, String pattern, Locale locale) {
		if (dtStr == null || dtStr.trim().length() == 0)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		try {
			return sdf.parse(dtStr);
		} catch (Exception e) {
			throw new ParseException(e.getMessage(), e);
		}
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
	public static String format(Date dt, String pattern) {
		if (dt == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(dt);
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
	public static String format(Date dt, String pattern, Locale locale) {
		if (dt == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(dt);
	}

	/**
	 * <b>解析日期时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return Date
	 */
	public static Date parseDateTime(String str) {
		for (SimpleDateFormat fmt : DEFAULT_DATETIME_PATTERNS) {
			try {
				return fmt.parse(str);
			} catch (Exception e) {

			}
		}
		throw new ParseException("Unable to parse the date: " + str);
	}

	/**
	 * <b>解析日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return Date
	 */
	public static Date parseDate(String str) {
		for (SimpleDateFormat fmt : DEFAULT_DATE_PATTERNS) {
			try {
				return fmt.parse(str);
			} catch (Exception e) {

			}
		}
		throw new ParseException("Unable to parse the date: " + str);
	}

	/**
	 * <b>解析时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return Date
	 */
	public static Date parseTime(String str) {
		for (SimpleDateFormat fmt : DEFAULT_TIME_PATTERNS) {
			try {
				return fmt.parse(str);
			} catch (Exception e) {

			}
		}
		throw new ParseException("Unable to parse the date: " + str);
	}

	/**
	 * <b>sleep。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param millis 毫秒数
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * <b>返回现在日期时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 现在日期时间
	 */
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * <b>返回现在日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 现在日期
	 */
	public static java.sql.Date date() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * <b>返回现在时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 现在时间
	 */
	public static Time time() {
		return new java.sql.Time(System.currentTimeMillis());
	}

	/**
	 * <b>将字符串转成日期。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return 日期
	 */
	public static java.sql.Date toDate(String str) {
		if (StringUtil.isBlank(str))
			return null;
		else if (StringUtils.indexOfAny(str, '-', '/') == -1)
			return new java.sql.Date(Long.parseLong(str));
		else
			return new java.sql.Date(parseDate(str).getTime());
	}

	/**
	 * <b>将字符串转成日期时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return 日期时间
	 */
	public static Timestamp toTimestamp(String str) {
		if (StringUtil.isBlank(str))
			return null;
		else if (StringUtils.indexOfAny(str, '-', '/', ':', '.') == -1)
			return new Timestamp(Long.parseLong(str));
		else
			return new Timestamp(parseDateTime(str).getTime());
	}

	/**
	 * <b>将字符串转成时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @return 时间
	 */
	public static Time toTime(String str) {
		if (StringUtil.isBlank(str))
			return null;
		else if (StringUtils.indexOfAny(str, ':', '.') == -1)
			return new Time(Long.parseLong(str));
		else
			return new Time(parseTime(str).getTime());
	}
}
