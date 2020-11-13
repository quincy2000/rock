package org.quincy.rock.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;

import org.quincy.rock.core.exception.ParseException;

/**
 * <b>NumberUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月18日 下午4:58:08</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class NumberUtil {
	/**
	 * default_decimal_pattern。
	 */
	public static String default_decimal_pattern = "#,##0.00";
	/**
	 * default_integer_pattern。
	 */
	public static String default_integer_pattern = "#,##0";

	/**
	 * DEFAULT_DECIMAL_PATTREN。
	 */
	public static final DecimalFormat DEFAULT_DECIMAL_PATTREN = new DecimalFormat(default_decimal_pattern);
	/**
	 * DEFAULT_INTEGER_PATTREN。
	 */
	public static final DecimalFormat DEFAULT_INTEGER_PATTREN = new DecimalFormat(default_integer_pattern);

	//数字的类型
	public enum NumberType {
		/**
		 * 整数
		 */
		ntInteger,
		/**
		 * 长整数
		 */
		ntLong,
		/**
		 * 数字
		 */
		ntDecimal,
		/**
		 * 大数字
		 */
		ntBigDecimal,
	}

	/**
	 * mathContext。
	 */
	private static MathContext mathContext = MathContext.DECIMAL64;

	/**
	 * <b>getMathContext。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return MathContext
	 */
	public static MathContext getMathContext() {
		return mathContext;
	}

	/**
	 * <b>setMathContext。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mathContext MathContext
	 */
	public static void setMathContext(MathContext mathContext) {
		NumberUtil.mathContext = mathContext;
	}

	/**
	 * <b>判断是否为0。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 要判断的数字
	 * @return 是否为0
	 */
	public static boolean isZero(Number num) {
		return num != null && num.intValue() == 0;
	}

	/**
	 * <b>判断是否是正数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 要判断的数字
	 * @param fast 快速检查
	 * @return 是否是正数
	 */
	public static boolean isPositive(Number num, boolean fast) {
		if (num == null || isZero(num))
			return false;
		if (fast)
			return num.longValue() > 0;
		else {
			if (num instanceof BigDecimal)
				return ((BigDecimal) num).compareTo(BigDecimal.ZERO) > 0;
			else if (num instanceof BigInteger)
				return ((BigInteger) num).compareTo(BigInteger.ZERO) > 0;
			else if (num instanceof Float || num instanceof Double)

				return num.doubleValue() > 0;
			else
				return num.longValue() > 0;
		}
	}

	/**
	 * <b>判断是否是负数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 要判断的数字
	 * @param fast 快速检查
	 * @return 是否是负数
	 */
	public static boolean isNegative(Number num, boolean fast) {
		if (num == null || isZero(num))
			return false;
		if (fast)
			return num.longValue() < 0;
		else {
			if (num instanceof BigDecimal)
				return ((BigDecimal) num).compareTo(BigDecimal.ZERO) < 0;
			else if (num instanceof BigInteger)
				return ((BigInteger) num).compareTo(BigInteger.ZERO) < 0;
			else if (num instanceof Float || num instanceof Double)

				return num.doubleValue() < 0;
			else
				return num.longValue() < 0;
		}
	}

	/**
	 * 是否是整数
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 数字
	 * @return 是否是整数(包括大整数)
	 */
	public static boolean isInteger(Number num) {
		return !(num instanceof Float || num instanceof Double || num instanceof BigDecimal);
	}

	/**
	 * 是否是大数字
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 数字
	 * @return 是否是大数字
	 */
	public static boolean isBigNumber(Number num) {
		return num instanceof BigInteger || num instanceof BigDecimal;
	}

	/**
	 * <b>是否是浮点数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 数字
	 * @return 是否是浮点数
	 */
	public static boolean isFloat(Number num) {
		return num instanceof Float || num instanceof Double || num instanceof BigDecimal;
	}

	/**
	 * 解析数字
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 数字串
	 * @param fmt 格式字符串
	 * @return 数字值
	 */
	public static Number parseNumber(String str, String fmt) {
		if (StringUtil.isBlank(str))
			return null;

		DecimalFormat format = new DecimalFormat(fmt);
		try {
			return format.parse(str);
		} catch (Exception e) {
			throw new ParseException(e.getMessage(), e);
		}
	}

	/**
	 * 格式化数字
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 数字值
	 * @param fmt 格式字符串
	 * @return 数字串
	 */
	public static String formatNumber(Number v, String fmt) {
		if (v == null)
			return null;
		DecimalFormat format = new DecimalFormat(fmt);
		return format.format(v);
	}

	/**
	 * <b>获得数字类型。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param num 数字
	 * @return 数字类型
	 */
	public static NumberType getNumberType(Number num) {
		NumberType nt = null;
		if (num instanceof Long)
			nt = NumberType.ntLong;
		else if (num instanceof Float || num instanceof Double)
			nt = NumberType.ntDecimal;
		else if (num instanceof BigInteger || num instanceof BigDecimal)
			nt = NumberType.ntBigDecimal;
		else
			nt = NumberType.ntInteger;
		return nt;
	}

	/**
	 * <b>获得最大的数字类型。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param nts 数字类型数组
	 * @return 最大的数字类型
	 */
	public static NumberType max(NumberType... nts) {
		NumberType r = NumberType.ntInteger;
		int max = NumberType.ntBigDecimal.ordinal();
		for (NumberType nt : nts) {
			if (nt.ordinal() > r.ordinal())
				r = nt;
			if (r.ordinal() == max)
				break;
		}
		return r;
	}

	/**
	 * <b>返回最小正数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 忽略负数。
	 * @param a 数a
	 * @param b 数b
	 * @return 最小正数
	 */
	public static int minCount(int a, int b) {
		if (a < 0)
			a = Integer.MAX_VALUE;
		if (b < 0)
			b = Integer.MAX_VALUE;
		return Math.min(a, b);
	}
}
