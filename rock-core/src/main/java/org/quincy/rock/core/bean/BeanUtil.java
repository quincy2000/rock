package org.quincy.rock.core.bean;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ClassUtils;
import org.quincy.rock.core.exception.IllegalClassException;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.vo.BaseEntity;
import org.slf4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.lang.Nullable;

/**
 * <b>BeanUtil工具类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2009-5-7 上午11:07:17</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class BeanUtil {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(BeanUtil.class);

	/**
	 * <b>查询类型兼容。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clazz 类型
	 * @param types 兼容列表
	 * @return 兼容类型
	 */
	public static Class<?> compatibleOf(Class<?> clazz, Class<?>... types) {
		for (Class<?> type : types) {
			if (ClassUtils.isAssignable(clazz, type)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * <b>将bean中符合条件的属性复制到新的map中。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bean bean
	 * @return 新Map
	 */
	public static Map<String, Object> toMap(Object bean, boolean copyNullValue, Class<?>... types) {
		Map<String, Object> map = new HashMap<>();
		if (bean instanceof Map) {
			Map<String, Object> beanWrapper = (Map<String, Object>) bean;
			for (String key : beanWrapper.keySet()) {
				Object value = beanWrapper.get(key);
				if (value != null) {
					if (compatibleOf(value.getClass(), types) != null)
						map.put(key, value);
				} else if (copyNullValue) {
					map.put(key, value);
				}
			}
		} else {
			BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
			for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
				String name = pd.getName();
				if (beanWrapper.isReadableProperty(name) && compatibleOf(pd.getPropertyType(), types) != null) {
					Object value = beanWrapper.getPropertyValue(name);
					if (copyNullValue || value != null)
						map.put(name, value);
				}
			}
		}
		return map;
	}

	/**
	 * <b>将bean包装成map返回。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果bean本来就是map类型，则直接返回。
	 * @param bean 要被包装的bean
	 * @return 包装后的Map
	 */
	public static Map<String, Object> toMap(Object bean) {
		return (bean instanceof Map) ? (Map) bean : new BeanMap(bean);
	}

	/**
	 * <b>两个bean属性做差集。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 以第一个bean为基准，仅保留两个bean属性比较之后的差异值。
	 * @param <V>
	 * @param bean1 第一个bean
	 * @param bean2 第二个bean
	 * @param cls 新bean类型
	 * @param exclude 排除的属性
	 * @return 新bean
	 */
	public static <V> V minus(Object bean1, Object bean2, Class<V> cls, String... exclude) {
		V v = null;
		Map<String, Object> map1 = toMap(bean1);
		Map<String, Object> map2 = toMap(bean2);
		try {
			v = (V) cls.newInstance();
		} catch (Exception e) {
			throw new IllegalClassException(e.getMessage(), e);
		}
		Map<String, Object> dest = toMap(v);
		boolean isBean = dest instanceof BeanMap;
		if (exclude == null)
			exclude = new String[0];
		Arrays.sort(exclude);

		//循环每个
		for (Map.Entry<String, Object> entry : map1.entrySet()) {
			String key = entry.getKey();
			if (isBean && !dest.containsKey(key))
				continue;
			if (Arrays.binarySearch(exclude, key) != -1)
				continue;
			Object value1 = entry.getValue();
			Object value2 = map2.get(key);
			if (!Objects.equals(value1, value2)) {
				try {
					dest.put(key, value1);
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
		return v;
	}

	/**
	 * <b>两个bean属性做交集。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 仅把两个bean相同的属性值拷贝到新bean中。
	 * @param <V>
	 * @param bean1 第一个bean
	 * @param bean2 第二个bean
	 * @param cls 新bean类型
	 * @return 新bean
	 */
	public static <V> V intersect(Object bean1, Object bean2, Class<V> cls) {
		V v = null;
		Map<String, Object> map1 = toMap(bean1);
		Map<String, Object> map2 = toMap(bean2);
		try {
			v = (V) cls.newInstance();
		} catch (Exception e) {
			throw new IllegalClassException(e.getMessage(), e);
		}
		Map<String, Object> dest = toMap(v);
		boolean isBean = dest instanceof BeanMap;
		//循环每个
		for (Map.Entry<String, Object> entry : map1.entrySet()) {
			String key = entry.getKey();
			if (isBean && !dest.containsKey(key))
				continue;
			if (!map2.containsKey(key))
				continue;
			Object value1 = entry.getValue();
			Object value2 = map2.get(key);
			if (Objects.equals(value1, value2)) {
				try {
					dest.put(key, value1);
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
		return v;
	}

	/**
	 * <b>两个bean属性做并集。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 以第一个bean为基准，将第一个bean为null而第二个bean不为null的属性合并到第一个bean。
	 * @param <V>
	 * @param bean1 第一个bean
	 * @param bean2 第二个bean
	 * @param cls 新bean类型
	 * @return 新bean
	 */
	public static <V> V union(Object bean1, Object bean2, Class<V> cls) {
		V v = null;
		Map<String, Object> map1 = toMap(bean1);
		Map<String, Object> map2 = toMap(bean2);
		try {
			v = (V) cls.newInstance();
		} catch (Exception e) {
			throw new IllegalClassException(e.getMessage(), e);
		}
		Map<String, Object> dest = toMap(v);
		boolean isBean = dest instanceof BeanMap;
		//循环每个
		for (Map.Entry<String, Object> entry : map1.entrySet()) {
			String key = entry.getKey();
			if (isBean && !dest.containsKey(key))
				continue;
			Object value = entry.getValue();
			if (value == null) {
				value = map2.get(key);
			}
			//set
			try {
				dest.put(key, value);
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
		return v;
	}

	/**
	 * <b>将源Bean中的属性值拷贝到目标Bean中。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果属性值相同将不会被拷贝。
	 * @param src 源Bean
	 * @param dest 目标Bean
	 * @param copyNullValue 是否拷贝Null值
	 * @param strict 是否严格匹配(必须具有相同的属性才拷贝)
	 * @param excludeProperties 不打算拷贝的属性名
	 */
	public static void copy(Object src, Object dest, boolean copyNullValue, boolean strict,
			String... excludeProperties) {
		if (excludeProperties == null)
			excludeProperties = new String[0];
		Arrays.sort(excludeProperties);
		Map<String, Object> srcBeanMap = toMap(src);
		Map<String, Object> destBeanMap = toMap(dest);
		boolean isBean = destBeanMap instanceof BeanMap;
		for (String name : srcBeanMap.keySet()) {
			if (Arrays.binarySearch(excludeProperties, name) >= 0 || (strict && !destBeanMap.containsKey(name)))
				continue;
			Object newValue = srcBeanMap.get(name);
			if (newValue instanceof BaseEntity) {
				if (!BaseEntity.hasId((BaseEntity) newValue))
					newValue = null;
			}
			if (copyNullValue || newValue != null) {
				boolean canRead = !isBean || ((BeanMap) destBeanMap).canRead(name);
				if (canRead) {
					Object oldValue = destBeanMap.get(name);
					if (Objects.equals(newValue, oldValue))
						continue;
				}
				destBeanMap.put(name, newValue);
			}
		}
	}

	/**
	 * <b>将bean对象列表转换成map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param beans bean对象列表
	 * @param keyProperty 指定bean属性作为键
	 * @param valueProperty 指定bean属性作为值,如果为null则返回bean本身
	 * @param keep 是否保持原始顺序
	 * @return map
	 */
	public static Map<?, ?> getMap(List<?> beans, String keyProperty, @Nullable String valueProperty, boolean keep) {
		Map map = keep ? new LinkedHashMap<>() : new HashMap<>();
		for (Object bean : beans) {
			if (bean instanceof Map) {
				Map<String, Object> wrapper = (Map) bean;
				Object key = wrapper.get(keyProperty);
				Object value = valueProperty == null ? bean : wrapper.get(valueProperty);
				map.put(key, value);
			} else {
				BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
				Object key = wrapper.getPropertyValue(keyProperty);
				Object value = valueProperty == null ? bean : wrapper.getPropertyValue(valueProperty);
				map.put(key, value);
			}
		}
		return map;
	}
}
