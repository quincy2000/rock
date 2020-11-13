package org.quincy.rock.core.util;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;
import org.quincy.rock.core.exception.ConversionException;
import org.quincy.rock.core.exception.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * <b>JsonUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月9日 下午5:44:09</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public final class JsonUtil {
	/**
	 * JSON。
	 */
	private final static ObjectMapper JSON = new ObjectMapper();

	static {
		JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private JsonUtil() {

	}

	/**
	 * <b>将对象转换成json字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param obj 对象
	 * @return json字符串
	 */
	public static String toJson(Object obj) {
		try {
			return JSON.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

	/**
	 * <b>判断json串是否是数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @return json串是否是数组
	 */
	public static boolean isArray(String json) {
		return StringUtil.startsWithChar(json, '[');
	}

	/**
	 * <b>判断json串是否是对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @return json串是否是对象
	 */
	public static boolean isObject(String json) {
		return StringUtil.startsWithChar(json, '{');
	}

	/**
	 * <b>猜测是否有可能是json字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param str 要猜测的字符串
	 * @return 是否有可能是json字符串
	 */
	public static boolean maybeJson(String str) {
		return isObject(str) || isArray(str);
	}

	/**
	 * <b>检查是否是json格式字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果不是json字符串则抛出异常。
	 * @param str 字符串
	 * @throws ParseException
	 */
	public static void checkJson(String str) throws ParseException {
		if (!maybeJson(str)) {
			throw new ParseException(str);
		}
	}

	/**
	 * <b>将json字符串转换成对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @param constructor 参数化类型构造器
	 * @return 对象
	 */
	public static <T> T fromJson(String json, Function<TypeFactory, JavaType> constructor) {
		JavaType javaType = constructor.apply(JSON.getTypeFactory());
		try {
			return JSON.readValue(json, javaType);
		} catch (IOException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

	/**
	 * <b>将json字符串转换成对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @param type 对象类型
	 * @return 对象
	 */
	public static <T> T fromJson(String json, Class<T> type, Class<?>... parameterClasses) {
		try {
			if (ArrayUtils.isEmpty(parameterClasses))
				return JSON.readValue(json, type);
			else {
				JavaType javaType = JSON.getTypeFactory().constructParametricType(type, parameterClasses);
				return JSON.readValue(json, javaType);
			}
		} catch (IOException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

	/**
	 * <b>将json字符串转换成对象数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @param type 数组元素类型
	 * @return 对象列表
	 */
	public static <T> List<T> fromJsonArray(String json, Class<T> type) {
		JavaType javaType = JSON.getTypeFactory().constructParametricType(List.class, type);
		try {
			return JSON.readValue(json, javaType);
		} catch (IOException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

}
