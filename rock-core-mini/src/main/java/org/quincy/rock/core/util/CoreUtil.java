package org.quincy.rock.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.quincy.rock.core.exception.ConstructorException;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.function.EachConsumer;
import org.quincy.rock.core.function.Function;
import org.quincy.rock.core.lang.TwoString;
import org.quincy.rock.core.vo.PageSet;

/**
 * <b>CoreUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月9日 下午5:23:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CoreUtil {
	/**
	 * boolMap
	 */
	private static final Map<String, Boolean> boolMap;

	static {
		boolMap = new HashMap<String, Boolean>();
		boolMap.put("1", Boolean.TRUE);
		boolMap.put("0", Boolean.FALSE);
		boolMap.put("yes", Boolean.TRUE);
		boolMap.put("no", Boolean.FALSE);
		boolMap.put("on", Boolean.TRUE);
		boolMap.put("off", Boolean.FALSE);
		boolMap.put("true", Boolean.TRUE);
		boolMap.put("false", Boolean.FALSE);
	}

	/**
	 * 将字符串值转换成Boolean值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 字符串值
	 * @return Boolean值
	 */
	public static Boolean string2Boolean(String v) {
		if (StringUtil.isBlank(v))
			return null;
		Boolean r = boolMap.get(v.toLowerCase());
		if (r == null)
			r = Boolean.valueOf(v);
		return r;
	}

	/**
	 * 将字符串值转换成boolean值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 字符串值
	 * @param defaultValue 缺省值
	 * @return boolean值
	 */
	public static boolean string2Boolean(String v, boolean defaultValue) {
		Boolean result = string2Boolean(v);
		return result == null ? defaultValue : result.booleanValue();
	}

	/**
	 * 将整型值转换成布尔值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 整型值
	 * @return  布尔值
	 */
	public static boolean int2boolean(int v) {
		return v == 1;
	}

	/**
	 * 将布尔值转换成整型值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 布尔值
	 * @return 整型值
	 */
	public static int boolean2int(boolean v) {
		return v ? 1 : 0;
	}

	/**
	 * <b>将表示16进制值的字符串转换为byte数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param hex 需要转换的字符串
	 * @return 转换后的byte数组
	 */
	public static byte[] hexString2ByteArray(CharSequence hex) {
		if (hex == null)
			return null;
		//修正长度必须是2的倍数
		int len = hex.length();
		if ((len % 2) != 0) {
			hex = new TwoString("0", hex);
			len++;
		}
		//两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		len = len >>> 1;
		byte[] arrOut = new byte[len];
		int hi, lo;
		for (int i = 0, j = 0; i < len; i++) {
			hi = Character.digit(hex.charAt(j++), 16);
			lo = Character.digit(hex.charAt(j++), 16);
			if (hi != 0)
				hi = hi << 4;
			arrOut[i] = (byte) (hi | lo);
		}
		return arrOut;
	}

	/**
	 * <b>将byte数组转换为表示16进制值的字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param arrB 需要转换的byte数组
	 * @return 转换后的字符串
	 */
	public static String byteArray2HexString(byte[] arrB) {
		if (arrB == null)
			return null;
		int len = arrB.length, v;
		char c;
		//每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(len << 1);
		for (int i = 0; i < len; i++) {
			v = arrB[i] & 0xFF;
			c = Character.forDigit(v >>> 4, 16);
			sb.append(Character.toUpperCase(c));
			c = Character.forDigit(v & 0x0F, 16);
			sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}

	/**
	 * <b>将16进制字符串转换成二进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param hex 16进制字符串
	 * @return 二进制字符串
	 */
	public static String hexToBinary(CharSequence hex) {
		if (hex == null)
			return null;
		int len = hex.length();
		if ((len % 2) != 0) {
			hex = new TwoString("0", hex);
			len++;
		}
		StringBuilder sb = new StringBuilder(len << 2);
		for (int i = 0; i < len; i++) {
			String bin = Integer.toBinaryString(Character.digit(hex.charAt(i), 16));
			sb.append(StringUtil.leftPad(bin, 4, StringUtil.CHAR_0));
		}
		return sb.toString();
	}

	/**
	 * <b>将二进制字符串转换成16进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bin 二进制字符串
	 * @return 16进制字符串
	 */
	public static String binaryToHex(CharSequence bin) {
		if (bin == null)
			return null;
		//长度,余数
		int len = bin.length(), rem = len % 8;
		char c;
		if (rem != 0) {
			int i = 8 - rem;
			bin = StringUtil.repeat(StringUtil.CHAR_0, i).concat(bin.toString());
			len += i;
		}
		len = len >>> 2;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0, j = 0; i < len; i++) {
			//binaryToHex
			c = Character.forDigit(Integer.parseInt(bin.subSequence(j, j = j + 4).toString(), 2), 16);
			sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}

	/**
	 * <b>将数字转换成16进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 数字值
	 * @return 16进制字符串
	 */
	public static String toHexString(int value) {
		String hex = Integer.toHexString(value).toUpperCase();
		if (hex.length() % 2 != 0)
			hex = StringUtil.CHAR_0 + hex;
		return hex;
	}

	/**
	 * <b>是否是16进制。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 16进制以0x开头：0xFF。
	 * @param s 要判断的字符串
	 * @return 是否是16进制
	 */
	public static boolean isHex(String s) {
		if (s != null && s.length() > 2) {
			return s.charAt(0) == StringUtil.CHAR_0
					&& (s.charAt(1) == StringUtil.CHAR_X || s.charAt(1) == StringUtil.CHAR_x);
		}
		return false;
	}

	/**
	 * <b>toString。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v
	 * @return
	 */
	public static String toString(Object v) {
		return v == null ? null : v.toString();
	}

	/**
	 * <b>将字符串数组合并成字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ss 字符串数组
	 * @return 字符串
	 */
	public static String toString(String[] ss) {
		if (ss == null) {
			return null;
		} else if (ss.length == 0) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (int i = 0, l = ss.length; i < l; i++) {
				if (i != 0)
					sb.append(StringUtil.CHAR_COMMA);
				sb.append(StringUtil.CHAR_DOUBLE_QUOTE);
				sb.append(ss[i]);
				sb.append(StringUtil.CHAR_DOUBLE_QUOTE);
			}
			sb.append("]");
			return sb.toString();
		}
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Boolean[] toObject(boolean[] vs) {
		int len = vs.length;
		Boolean[] rtn = new Boolean[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Character[] toObject(char[] vs) {
		int len = vs.length;
		Character[] rtn = new Character[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Byte[] toObject(byte[] vs) {
		int len = vs.length;
		Byte[] rtn = new Byte[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Short[] toObject(short[] vs) {
		int len = vs.length;
		Short[] rtn = new Short[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Integer[] toObject(int[] vs) {
		int len = vs.length;
		Integer[] rtn = new Integer[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Long[] toObject(long[] vs) {
		int len = vs.length;
		Long[] rtn = new Long[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Float[] toObject(float[] vs) {
		int len = vs.length;
		Float[] rtn = new Float[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>toObject。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param vs
	 * @return
	 */
	public static Double[] toObject(double[] vs) {
		int len = vs.length;
		Double[] rtn = new Double[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = vs[i];
		}
		return rtn;
	}

	/**
	 * <b>将对象转换成对象数组。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param v 对象值
	 * @return 对象数组
	 */
	public static Object[] toObjectArray(Object v) {
		if (v == null)
			return null;
		else if (v instanceof Object[])
			return (Object[]) v;
		else if (v.getClass().isArray()) {
			if (v instanceof int[]) {
				return toObject((int[]) v);
			} else if (v instanceof double[]) {
				return toObject((double[]) v);
			} else if (v instanceof long[]) {
				return toObject((long[]) v);
			} else if (v instanceof float[]) {
				return toObject((float[]) v);
			} else if (v instanceof char[]) {
				return toObject((char[]) v);
			} else if (v instanceof boolean[]) {
				return toObject((boolean[]) v);
			} else if (v instanceof short[]) {
				return toObject((short[]) v);
			} else if (v instanceof byte[]) {
				return toObject((byte[]) v);
			} else {
				int len = Array.getLength(v);
				Object[] arr = new Object[len];
				for (int i = 0; i < len; i++) {
					arr[i] = Array.get(v, i);
				}
				return arr;
			}
		} else if (v instanceof Collection) {
			return ((Collection<?>) v).toArray();
		} else
			return new Object[] { v };
	}

	/**
	 * <b>获得集合大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param iterable Iterable
	 * @param defaultSize 缺省大小
	 * @return 集合大小
	 */
	public static int getSize(Iterable<?> iterable, int defaultSize) {
		return (iterable instanceof Collection) ? ((Collection<?>) iterable).size() : defaultSize;
	}

	/**
	 * <b>获得集合大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param iterable Iterable
	 * @return 集合大小
	 */
	public static int getSize(Iterable<?> iterable) {
		if (iterable instanceof Collection)
			return ((Collection<?>) iterable).size();
		else {
			int i = 0;
			for (Iterator<?> it = iterable.iterator(); it.hasNext(); it.next())
				i++;
			return i;
		}
	}

	/**
	 * <b>将键值对放入Map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果键值对存在不会替换掉之前的，会使用列表存放该键所有的值。
	 * @param map Map
	 * @param key 键
	 * @param value 值
	 * @return Map
	 */
	public static Map put(Map map, Object key, Object value) {
		if (map.containsKey(key)) {
			Object v = map.get(key);
			if (v instanceof List) {
				((List) v).add(value);
			} else {
				List<Object> list = new ArrayList<>();
				list.add(v);
				list.add(value);
				map.put(key, list);
			}
		} else {
			map.put(key, value);
		}
		return map;
	}

	/**
	 * <b>将对象转成List。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param obj 要转成List的对象
	 * @return List 数据列表
	 */
	public static <T> List<T> toList(Object obj) {
		if (obj instanceof Iterable) {
			return toList((Iterable) obj);
		} else if (obj instanceof Iterator) {
			Iterator it = (Iterator) obj;
			List list = new ArrayList<>();
			while (it.hasNext())
				list.add(it.next());
			return list;
		} else if (isArray(obj)) {
			List list = new ArrayList<>();
			for (int i = 0, l = Array.getLength(obj); i < l; i++)
				list.add(Array.get(obj, i));
			return list;
		} else {
			return (List) Arrays.asList(obj);
		}
	}

	/**
	 * <b>将迭代接口转成List接口。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param iterable 迭代接口数据
	 * @return List 数据列表
	 */
	public static <T> List<T> toList(Iterable<T> iterable) {
		if (iterable instanceof List) {
			return (List<T>) iterable;
		} else {
			List<T> list = new ArrayList<>();
			for (T s : iterable) {
				list.add(s);
			}
			return list;
		}
	}

	/**
	 * <b>封装分页数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param iterable 数据列表
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @param function 回调函数
	 * @return 分页数据
	 */
	public static <T, R> PageSet<R> toPageSet(Iterable<T> iterable, int page, int pageSize, Function<T, R> function) {
		List<R> rs = new ArrayList<>(pageSize);
		PageSet<R> ps = new PageSet<>(page, pageSize);
		ps.setContent(rs);
		int iBegin = ps.getBeginIndex();
		int iEnd = ps.getEndIndex();
		if (iterable instanceof List) {
			List<T> list = (List<T>) iterable;
			ps.setTotalCount(list.size());
			for (int i = iBegin, len = Math.min(iEnd, list.size()); i < len; i++) {
				rs.add(function.call(list.get(i)));
			}
		} else {
			int i = 0;
			for (T ele : iterable) {
				i++;
				if (i > iBegin && i <= iEnd)
					rs.add(function.call(ele));
			}
			ps.setTotalCount(i);
		}
		return ps;
	}

	/**
	 * <b>拆分逗号分割的元素到列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param eles 以逗号分隔的字符串
	 * @param conv Function
	 * @return 列表
	 */
	public static <T> List<T> toList(String eles, final Function<CharSequence, T> conv) {
		final List<T> list = new ArrayList<>();
		if (eles != null) {
			StringUtil.split(eles, StringUtil.CHAR_COMMA, new EachConsumer<CharSequence>() {

				@Override
				public void each(int index, CharSequence ele) {
					list.add(conv.call(ele));
				}
			});
		}
		return list;
	}

	/**
	 * <b>findClass。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className 类名
	 * @return Class
	 */
	public static Class<?> findClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new NotFoundException(e.getMessage(), e);
		}
	}

	/**
	 * <b>findClass。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className 类名
	 * @param loader ClassLoader
	 * @return Class
	 */
	public static Class<?> findClass(String className, ClassLoader loader) {
		try {
			return Class.forName(className, true, loader);
		} catch (ClassNotFoundException e) {
			throw new NotFoundException(e.getMessage(), e);
		}
	}

	/**
	 * <b>构造新实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clazz 类型
	 * @return 新实例
	 * @throws ConstructorException
	 */
	public static <T> T constructor(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ConstructorException(e.getMessage(), e);
		}
	}

	/**
	 * <b>构造新实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className 类名
	 * @return 新实例
	 * @throws ConstructorException
	 */
	public static <T> T constructor(String className) {
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new ConstructorException(e.getMessage(), e);
		}
	}

	/**
	 * <b>构造新实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className 类名
	 * @param loader ClassLoader
	 * @return 新实例
	 * @throws ConstructorException
	 */
	public static <T> T constructor(String className, ClassLoader loader) {
		try {
			return (T) Class.forName(className, true, loader).newInstance();
		} catch (Exception e) {
			throw new ConstructorException(e.getMessage(), e);
		}
	}

	/**
	 * <b>判断Map是否为空。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map Map
	 * @return Map是否为空
	 */
	public static boolean isEmpty(Map map) {
		return map == null || map.isEmpty();
	}

	/**
	 * <b>判断集合是否为空。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param c 集合
	 * @return 集合是否为空
	 */
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.isEmpty();
	}

	/**
	 * <b>对象是否是数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param obj 对象
	 * @return 是否是数组
	 */
	public static boolean isArray(Object obj) {
		return obj != null && TypeUtils.isArrayType(obj.getClass());
	}
}
