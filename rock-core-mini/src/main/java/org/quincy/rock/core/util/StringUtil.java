package org.quincy.rock.core.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.quincy.rock.core.exception.ConversionException;
import org.quincy.rock.core.function.EachConsumer;
import org.quincy.rock.core.function.EachFunction;

/**
 * <b>StringUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 上午9:08:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class StringUtil {
	/**
	 * EMPTY。
	 */
	public static final String EMPTY = "";
	/**
	 * 空格。
	 */
	public static final char CHAR_SPACE = ' ';
	/**
	 * 冒号。
	 */
	public static final char CHAR_COLON = ':';
	/**
	 * 点。
	 */
	public static final char CHAR_DOT = '.';
	/**
	 * 逗号。
	 */
	public static final char CHAR_COMMA = ',';
	/**
	 * 单引号。
	 */
	public static final char CHAR_SINGLE_QUOTE = '\'';
	/**
	 * 双引号。
	 */
	public static final char CHAR_DOUBLE_QUOTE = '"';
	/**
	 * 斜杠。
	 */
	public static final char CHAR_SLASH = '/';
	/**
	 * 反斜杠。
	 */
	public static final char CHAR_BACKSLASH = '\\';
	/**
	 * 下划线。
	 */
	public static final char CHAR_UNDERLINE = '_';
	/**
	 * CHAR_0。
	 */
	public static final char CHAR_0 = '0';
	/**
	 * CHAR_x。
	 */
	public static final char CHAR_x = 'x';
	/**
	 * CHAR_X。
	 */
	public static final char CHAR_X = 'X';
	/**
	 * CHAR_z。
	 */
	public static final char CHAR_z = 'z';
	/**
	 * CHAR_Z。
	 */
	public static final char CHAR_Z = 'z';

	/**
	 * UTF_8。
	 */
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	/**
	 * ISO_8859_1。
	 */
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
	/**
	 * DEFAULT_ENCODING。
	 */
	public static final Charset DEFAULT_ENCODING = UTF_8;

	/**
	 * <b>判断字符串是否为空白。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果字符串是null，或者trim后为空字符，则返回true，否则返回false。
	 * @param s 要判断的字符串
	 * @return 如果字符串是null，或者trim后为空字符，则返回true。
	 */
	public static boolean isBlank(CharSequence s) {
		if (s != null && s.length() > 0) {
			for (int i = 0, len = s.length(); i < len; i++) {
				if (s.charAt(i) > CHAR_SPACE) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <b>判断字符串是否为空。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 要判断的字符串
	 * @return 如果字符串是null，或者长度为0，则返回true。
	 */
	public static boolean isEmpty(CharSequence s) {
		return s == null || s.length() == 0;
	}

	/**
	 * <b>判断字符串是空白或者是零。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 要判断的字符串
	 * @return 字符串是空白或者是零
	 */
	public static boolean isBlankOrZero(CharSequence s) {
		return isBlank(s) || s.charAt(0) == CHAR_0;
	}

	/**
	 * <b>使用匹配符判断字符串是否模糊匹配like。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 要判断的字符串
	 * @param like 使用了匹配符的字符串
	 * @param question 单字符匹配字符
	 * @param star 多字符匹配字符
	 * @return 字符串是否模糊匹配like
	 */
	public static boolean isLiked(String s, String like, String question, String star) {
		if (s == null)
			return false;
		like = like.replace(question, "[\\s\\S]").replace(star, "[\\s\\S]*");
		boolean ok = s.matches(like);
		return ok;
	}

	/**
	 * <b>转换字符编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s　要转换的字符串
	 * @param srcEncoding　原来的编码
	 * @param destEncoding　目标编码
	 * @return 转换完成的字符串
	 */
	public static String convertEncoding(String s, String srcEncoding, String destEncoding) {
		try {
			return isBlank(s) ? s : new String(s.getBytes(srcEncoding), destEncoding);
		} catch (Exception e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

	/**
	 * 比较两个字符串的局部
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str1 字符串1
	 * @param pos1 开始位置1
	 * @param str2 字符串2
	 * @param pos2 开始位置2
	 * @param len 比较长度
	 * @return 两个字符串的局部是否相等
	 */
	public static boolean equalsPart(CharSequence str1, int pos1, CharSequence str2, int pos2, int len) {
		if (len == 0) {
			return true;
		} else if (pos1 + len > str1.length() || pos2 + len > str2.length()) {
			return false;
		} else {
			for (int i = 0; i < len; i++) {
				if (str1.charAt(pos1 + i) != str2.charAt(pos2 + i))
					return false;
			}
			return true;
		}
	}

	/**
	 * <b>根据分隔符拆分字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不处理转义。
	 * @param str 字符串
	 * @param seperator 分隔符
	 * @param eacher 回调函数
	 * @return 元素个数
	 */
	public static int split(CharSequence str, char seperator, EachConsumer<CharSequence> eacher) {
		StringBuilder buffer = new StringBuilder();
		int index = 0;
		for (int i = 0, l = str.length(); i < l; i++) {
			char c = str.charAt(i);
			if (c == seperator) {
				eacher.each(index++, buffer);
				buffer = new StringBuilder();
			} else
				buffer.append(c);
		}
		eacher.each(index++, buffer);
		return index;
	}

	/**
	 * 根据分隔符拆分字符串
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 会处理转义字符。
	 * @param str 字符串
	 * @param beginIndex 开始位置索引
	 * @param endIndex 结束位置索引,-1代表结尾
	 * @param seperator 分隔符
	 * @param eacher 回调函数,如果返回true表示处理完毕
	 * @return 指针位置索引
	 */
	public static int split(CharSequence str, int beginIndex, int endIndex, char seperator,
			EachFunction<CharSequence, Boolean> eacher) {
		endIndex = endIndex == -1 ? str.length() : endIndex;
		boolean escapeNext = false;
		char quoteChar = 0;
		StringBuilder buffer = new StringBuilder();
		int index = 0;
		for (int i = beginIndex; i < endIndex; i++) {
			char c = str.charAt(i);
			if (quoteChar != 0) {
				//在引号里面
				if (escapeNext) {
					escapeNext = false;
				} else if (c == CHAR_BACKSLASH) {
					//转义下一个字符
					escapeNext = true;
				} else if (c == quoteChar) //引号结束
				{
					quoteChar = 0;
				}
				buffer.append(c);
			} else if (escapeNext) {
				buffer.append(c);
				escapeNext = false;
			} else if (isQuote(c)) {
				buffer.append(c);
				quoteChar = c;
			} else if (c == CHAR_BACKSLASH) {
				escapeNext = true;
			} else if (c == seperator) {
				if (eacher.each(index++, buffer))
					return ++i;
				buffer = new StringBuilder();
			} else
				buffer.append(c);
		}
		eacher.each(index++, buffer);
		return endIndex;
	}

	/**
	 * 根据分隔符拆分字符串
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 会处理转义字符。
	 * @param str 字符串
	 * @param beginIndex 开始位置索引
	 * @param endIndex 结束位置索引
	 * @param seperators 多个分隔符
	 * @param eacher 回调函数,如果返回true表示处理完毕
	 * @return 指针位置索引
	 */
	public static int split(CharSequence str, int beginIndex, int endIndex, char[] seperators,
			EachFunction<CharSequence, Boolean> eacher) {
		Arrays.sort(seperators);
		endIndex = endIndex == -1 ? str.length() : endIndex;
		boolean escapeNext = false;
		char quoteChar = 0;
		StringBuilder buffer = new StringBuilder();
		int index = 0;
		for (int i = beginIndex; i < endIndex; i++) {
			char c = str.charAt(i);
			if (quoteChar != 0) {
				//在引号里面
				if (escapeNext) {
					escapeNext = false;
				} else if (c == CHAR_BACKSLASH) {
					//转义下一个字符
					escapeNext = true;
				} else if (c == quoteChar) //引号结束
				{
					quoteChar = 0;
				}
				buffer.append(c);
			} else if (escapeNext) {
				buffer.append(c);
				escapeNext = false;
			} else if (isQuote(c)) {
				buffer.append(c);
				quoteChar = c;
			} else if (c == CHAR_BACKSLASH) {
				escapeNext = true;
			} else if (Arrays.binarySearch(seperators, c) != -1) {
				if (eacher.each(index++, buffer))
					return ++i;
				buffer = new StringBuilder();
			} else
				buffer.append(c);
		}
		eacher.each(index++, buffer);
		return endIndex;
	}

	/**
	 * 根据分隔符拆分一行字符串
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param strLine 一行字符串
	 * @param seperators 多个分隔符
	 * @param escape 转义字符,如:'\\'
	 * @param eacher 回调函数
	 * @return 元素个数
	 */
	public static int splitLine(CharSequence strLine, char[] seperators, char escape,
			EachConsumer<CharSequence> eacher) {
		Arrays.sort(seperators);
		boolean escapeNext = false;
		char quoteChar = 0;
		boolean inQuoteChar = false;
		StringBuilder buffer = new StringBuilder();
		int index = 0;
		for (int i = 0, l = strLine.length(); i < l; i++) {
			char c = strLine.charAt(i);
			if (escapeNext) {
				buffer.append(c);
				escapeNext = false;
			} else if (c == escape) {
				escapeNext = true;
			} else if (quoteChar != 0) {
				//在引号里面
				if (c == quoteChar) {
					quoteChar = 0;
				} else {
					buffer.append(c);
				}
			} else if (isQuote(c)) {
				quoteChar = c;
				inQuoteChar = true;
			} else if (Arrays.binarySearch(seperators, c) != -1) {
				if (inQuoteChar) {
					inQuoteChar = false;
				} else if (buffer.length() == 4 && buffer.charAt(0) == 'n' && buffer.charAt(1) == 'u'
						&& buffer.charAt(2) == 'l' && buffer.charAt(3) == 'l') {
					buffer = null;
				}
				eacher.each(index++, buffer);
				buffer = new StringBuilder();
			} else
				buffer.append(c);
		}
		if (inQuoteChar) {
			inQuoteChar = false;
		} else if (buffer.length() == 4 && buffer.charAt(0) == 'n' && buffer.charAt(1) == 'u' && buffer.charAt(2) == 'l'
				&& buffer.charAt(3) == 'l') {
			buffer = null;
		}
		eacher.each(index++, buffer);
		return index;
	}

	/**
	 * 根据分隔符拆分一行字符串
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param strLine 一行字符串
	 * @param seperator 分隔符
	 * @param escape 转义字符,如:'\\'
	 * @param eacher 回调函数
	 * @return 元素个数
	 */
	public static int splitLine(CharSequence strLine, char seperator, char escape, EachConsumer<CharSequence> eacher) {
		boolean escapeNext = false;
		char quoteChar = 0;
		boolean inQuoteChar = false;
		StringBuilder buffer = new StringBuilder();
		int index = 0;
		for (int i = 0, l = strLine.length(); i < l; i++) {
			char c = strLine.charAt(i);
			if (escapeNext) {
				buffer.append(c);
				escapeNext = false;
			} else if (c == escape) {
				escapeNext = true;
			} else if (quoteChar != 0) {
				//在引号里面
				if (c == quoteChar) {
					quoteChar = 0;
				} else {
					buffer.append(c);
				}
			} else if (isQuote(c)) {
				quoteChar = c;
				inQuoteChar = true;
			} else if (c == seperator) {
				if (inQuoteChar) {
					inQuoteChar = false;
				} else if (buffer.length() == 4 && buffer.charAt(0) == 'n' && buffer.charAt(1) == 'u'
						&& buffer.charAt(2) == 'l' && buffer.charAt(3) == 'l') {
					buffer = null;
				}
				eacher.each(index++, buffer);
				buffer = new StringBuilder();
			} else
				buffer.append(c);
		}
		if (inQuoteChar) {
			inQuoteChar = false;
		} else if (buffer.length() == 4 && buffer.charAt(0) == 'n' && buffer.charAt(1) == 'u' && buffer.charAt(2) == 'l'
				&& buffer.charAt(3) == 'l') {
			buffer = null;
		}
		eacher.each(index++, buffer);
		return index;
	}

	/**
	 * 根据分隔符拆分一行字符串
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param strLine 一行字符串
	 * @param strSeperator 分隔符字符串
	 * @param escape 转义字符,如:'\\'
	 * @param eacher 回调函数
	 * @return 元素个数
	 */
	public static int splitLine(CharSequence strLine, String strSeperator, char escape,
			EachConsumer<CharSequence> eacher) {
		boolean escapeNext = false;
		char seperator = strSeperator.charAt(0);
		int len = strSeperator.length() - 1;
		char quoteChar = 0;
		boolean inQuoteChar = false;
		StringBuilder buffer = new StringBuilder();
		int index = 0;
		for (int i = 0, l = strLine.length(); i < l; i++) {
			char c = strLine.charAt(i);
			if (escapeNext) {
				buffer.append(c);
				escapeNext = false;
			} else if (c == escape) {
				escapeNext = true;
			} else if (quoteChar != 0) {
				//在引号里面
				if (c == quoteChar) {
					quoteChar = 0;
				} else {
					buffer.append(c);
				}
			} else if (isQuote(c)) {
				quoteChar = c;
				inQuoteChar = true;
			} else if (c == seperator && equalsPart(strLine, i + 1, strSeperator, 1, len)) {
				if (inQuoteChar) {
					inQuoteChar = false;
				} else if (buffer.length() == 4 && buffer.charAt(0) == 'n' && buffer.charAt(1) == 'u'
						&& buffer.charAt(2) == 'l' && buffer.charAt(3) == 'l') {
					buffer = null;
				}
				eacher.each(index++, buffer);
				buffer = new StringBuilder();
				if (len > 0)
					i += len;
			} else
				buffer.append(c);
		}
		if (inQuoteChar) {
			inQuoteChar = false;
		} else if (buffer.length() == 4 && buffer.charAt(0) == 'n' && buffer.charAt(1) == 'u' && buffer.charAt(2) == 'l'
				&& buffer.charAt(3) == 'l') {
			buffer = null;
		}
		eacher.each(index++, buffer);
		return index;
	}

	/**
	 * <b>根据单词首字母分隔字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 单词首字母必须大写。
	 * @param s 符合Pascal标准或Camel标准的名称字符串
	 * @return 分隔后的字符串数组
	 */
	public static String[] splitWithUpper(String s) {
		if (isBlank(s))
			return new String[] { s };
		//
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append(s.charAt(0));
		for (int i = 1, len = s.length(); i < len; i++) {
			char c = s.charAt(i);
			if (Character.isUpperCase(c)) {
				list.add(sb.toString());
				sb = new StringBuilder();
			}
			sb.append(c);
		}
		list.add(sb.toString());
		return list.toArray(new String[list.size()]);
	}

	/**
	 * <b>toPascalString。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 例如将字符串"emP_nAmE"转换成"EmpName"。
	 * @param s 使用分隔符分割的字符串
	 * @param delimiter 分隔符
	 * @return 去掉分隔符并将字符串转成Pascal字符串
	 */
	public static String toPascalString(String s, char delimiter) {
		return toPascal((CharSequence) s, delimiter).toString();
	}

	//
	private static StringBuilder toPascal(CharSequence cs, char delimiter) {
		final StringBuilder sb = new StringBuilder();
		split(cs, 0, cs.length(), delimiter, new EachFunction<CharSequence, Boolean>() {
			@Override
			public Boolean each(int index, CharSequence ele) {
				appendPascal(ele, sb);
				return false;
			}
		});
		return sb;
	}

	//
	private static void appendPascal(CharSequence ele, StringBuilder buffer) {
		int l = ele.length();
		if (l > 0) {
			buffer.append(Character.toUpperCase(ele.charAt(0)));
			for (int i = 1; i < l; i++) {
				buffer.append(Character.toLowerCase(ele.charAt(i)));
			}
		}
	}

	/**
	 * <b>toCamelString。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 首字母小写。
	 * 例如将字符串"EmP_nAmE"转换成"empName"。
	 * @param s 使用分隔符分割的字符串
	 * @param delimiter 分隔符
	 * @return 去掉分隔符并将字符串转成Camel字符串
	 */
	public static String toCamelString(String s, char delimiter) {
		StringBuilder sb = toPascal((CharSequence) s, delimiter);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}

	private static final int _maxIndex = Integer.MAX_VALUE >> 8;
	private static int _index = _maxIndex;

	private static synchronized int _nextIndex() {
		if (_index == _maxIndex) {
			_index = RANDOM.nextInt(Short.MAX_VALUE >> 4);
		}
		return _index++;
	}

	/**
	 * <b>返回随机数Random。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Random
	 */
	public static Random random() {
		return RANDOM;
	}

	private final static Random RANDOM = new Random();

	/**
	 * <b>获得唯一的标识符名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法只保证返回的标识符是唯一的。名称可能是杂乱无章的。
	 * @param prefix 前缀
	 * @return 标识符名称
	 */
	public static String getUniqueIdentifierName(Object prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(CHAR_UNDERLINE);
		long time = System.currentTimeMillis() / 1000 - 1387200000L;
		sb.append(Long.toString(time, 36));
		sb.append(CHAR_UNDERLINE);
		sb.append(Long.toString(_nextIndex(), 36));
		return sb.toString();
	}

	/**
	 * <b>>将字符串inner用双引号包围。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param inner 被包围的字符串
	 * @return 包围后的字符串
	 */
	public static CharSequence surround(CharSequence inner) {
		return surround(inner, CHAR_DOUBLE_QUOTE, CHAR_DOUBLE_QUOTE);
	}

	/**
	 * <b>将字符串inner包围在c中间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param inner 被包围的字符串
	 * @param c 外围字符
	 * @return 包围后的字符串
	 */
	public static CharSequence surround(CharSequence inner, char c) {
		return surround(inner, c, c);
	}

	/**
	 * <b>将字符串inner包围在left和right中间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param inner 被包围的字符串
	 * @param left 左外围字符
	 * @param right 右外围字符
	 * @return 包围后的字符串
	 */
	public static CharSequence surround(CharSequence inner, char left, char right) {
		if (inner == null) {
			return null;
		}
		{
			StringBuilder sb = new StringBuilder();
			sb.append(left);
			sb.append(inner);
			sb.append(right);
			return sb.toString();
		}
	}

	/**
	 * <b>将字符串inner包围在outer中间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param inner 被包围的字符串
	 * @param outer 外围字符串
	 * @return 包围后的字符串
	 */
	public static CharSequence surround(CharSequence inner, CharSequence outer) {
		return surround(inner, outer, outer);
	}

	/**
	 * <b>将字符串inner包围在left和right中间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param inner 被包围的字符串
	 * @param left 左外围字符串
	 * @param right 右外围字符串
	 * @return 包围后的字符串
	 */
	public static CharSequence surround(CharSequence inner, CharSequence left, CharSequence right) {
		if (inner == null) {
			return null;
		}
		{
			StringBuilder sb = new StringBuilder();
			sb.append(left);
			sb.append(inner);
			sb.append(right);
			return sb.toString();
		}
	}

	/**
	 * <b>从字符串中解析出数字。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果字符串中不包含数字，则返回null。
	 * @param s 容纳数字的字符串
	 * @param  digitBegin 设置第一个字符是否必须是数字
	 * @return 数字
	 */
	public static Number parseNumber(String s, boolean digitBegin) {
		if (!isBlank(s)) {
			char[] cc = s.toCharArray();
			if (digitBegin && !Character.isDigit(cc[0]))
				return null;
			int bIndex = -1;
			int eIndex = cc.length;
			for (int i = 0; i < cc.length; i++) {
				if (bIndex == -1 && Character.isDigit(cc[i]))
					bIndex = i;
				else if (bIndex != -1 && !Character.isDigit(cc[i])) {
					eIndex = i;
					break;
				}
			}
			if (bIndex != -1) {
				s = s.substring(bIndex, eIndex);
				return Long.parseLong(s);
			}
		}
		return null;
	}

	/**
	 * <b>去掉数组中每个字符串的首尾空格。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param arr 字符串数组
	 * @return 结果数组
	 */
	public static String[] trim(List<String> arr) {
		int l = arr.size();
		String[] ss = new String[l];
		for (int i = 0; i < l; i++) {
			String s = arr.get(i);
			ss[i] = s == null ? null : s.trim();
		}
		return ss;
	}

	/**
	 * 返回字符串是否以c开始
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 字符串
	 * @param c 字符
	 * @return 字符串是否以c开始
	 */
	public static boolean startsWithChar(CharSequence s, char c) {
		return StringUtil.isEmpty(s) ? false : c == s.charAt(0);
	}

	/**
	 * 返回字符串是否以c结束
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 字符串
	 * @param c 字符
	 * @return 字符串是否以c结束
	 */
	public static boolean endsWithChar(CharSequence s, char c) {
		return StringUtil.isEmpty(s) ? false : c == s.charAt(s.length() - 1);
	}

	/**
	 * 返回字符串是否以引号开始
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 字符串
	 * @return 字符串是否以引号开始
	 */
	public static boolean startsWithQuote(CharSequence s) {
		if (isBlank(s))
			return false;
		char c = s.charAt(0);
		return c == CHAR_SINGLE_QUOTE || c == CHAR_DOUBLE_QUOTE;
	}

	/**
	 * 返回字符串是否以引号结束
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 字符串
	 * @return 字符串是否以引号结束
	 */
	public static boolean endsWithQuote(CharSequence s) {
		if (isBlank(s))
			return false;
		char c = s.charAt(s.length() - 1);
		return c == CHAR_SINGLE_QUOTE || c == CHAR_DOUBLE_QUOTE;
	}

	/**
	 * <b>判断字符串是否是以斜杠结束。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 字符串
	 * @return 是否是以斜杠(/或\)结束
	 */
	public static boolean endsWithSlash(CharSequence s) {
		if (isBlank(s))
			return false;
		char c = s.charAt(s.length() - 1);
		return c == CHAR_SLASH || c == CHAR_BACKSLASH;
	}

	/**
	 * <b>判断字符串是否是以斜杠开始。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param s 字符串
	 * @return 是否是以斜杠(/或\)开始
	 */
	public static boolean startsWithSlash(CharSequence s) {
		if (isBlank(s))
			return false;
		char c = s.charAt(0);
		return c == CHAR_SLASH || c == CHAR_BACKSLASH;
	}

	/**
	 * <b>是否是引号。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param c 字符
	 * @return 是否是引号
	 */
	public static boolean isQuote(char c) {
		return (c == CHAR_SINGLE_QUOTE || c == CHAR_DOUBLE_QUOTE);
	}

	/**
	 * <b>判断字符序列是否是重复字符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cs 字符序列
	 * @param c 重复字符
	 * @return 字符序列是否是重复字符
	 */
	public static boolean repeat(CharSequence cs, char c) {
		for (int i = 0, l = cs.length(); i < l; i++) {
			if (cs.charAt(i) != c)
				return false;
		}
		return true;
	}

	/**
	 * <b>生成重复字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch 字符
	 * @param repeat 重复字数
	 * @return 字符串
	 */
	public static String repeat(final char ch, final int repeat) {
		if (repeat <= 0) {
			return EMPTY;
		}
		final char[] buf = new char[repeat];
		for (int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
	 * <b>填充字符串到指定长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @param size 指定长度
	 * @param padChar 填充字符
	 * @return 指定长度的字符串
	 */
	public static String leftPad(final String str, final int size, final char padChar) {
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		return repeat(padChar, pads).concat(str);
	}

	/**
	 * <b>填充字符串到指定长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 字符串
	 * @param size 指定长度
	 * @param padChar 填充字符
	 * @return 指定长度的字符串
	 */
	public static String rightPad(final String str, final int size, final char padChar) {
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		return str.concat(repeat(padChar, pads));
	}

	/**
	 * <b>连接字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cs0 字符串0
	 * @param cs1 字符串1
	 * @param css 字符串数组
	 * @return 连接后的字符串
	 */
	public static CharSequence concat(CharSequence cs0, CharSequence cs1, CharSequence... css) {
		int len = cs0.length() + cs1.length();
		for (int i = 0; i < css.length; i++)
			len += css[i].length();
		StringBuilder sb = new StringBuilder(len);
		sb.append(cs0);
		sb.append(cs1);
		for (int i = 0; i < css.length; i++)
			sb.append(css[i]);
		return sb;
	}

	/**
	 * <b>将Ascii字符串转换成16进制串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ascii Ascii字符串
	 * @return 16进制串
	 */
	public static String str2hex(String ascii) {
		if (ascii == null)
			return null;
		else if (ascii.length() == 0)
			return StringUtil.EMPTY;
		else
			return CoreUtil.byteArray2HexString(ascii.getBytes(DEFAULT_ENCODING));
	}

	/**
	 * <b>将16进制串转换成Ascii字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param hex 16进制串
	 * @return Ascii字符串
	 */
	public static String hex2Str(String hex) {
		if (hex == null)
			return null;
		else if (hex.length() == 0)
			return StringUtil.EMPTY;
		else
			return new String(CoreUtil.hexString2ByteArray(hex), DEFAULT_ENCODING);
	}
}
