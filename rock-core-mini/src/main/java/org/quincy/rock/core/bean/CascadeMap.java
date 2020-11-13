package org.quincy.rock.core.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.quincy.rock.core.exception.ConstructorException;

/**
 * <b>级联Map。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月24日 上午8:45:50</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CascadeMap<K1, K2, V> extends ProxyMap<K1, Map<K2, V>> {
	/**
	 * eleMapClazz。
	 */
	private Class<? extends Map> eleMapClazz;
	/**
	 * 自动移走空的Map。
	 */
	private boolean autoRemove;

	/**
	 * 构造方法。
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public CascadeMap() {
		this(new HashMap(), HashMap.class, false);
	}

	/**
	 * 构造方法。
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param customMap 定制Map
	 * @param autoRemove 自动移走空的Map
	 */
	public CascadeMap(Map customMap, boolean autoRemove) {
		this(customMap, HashMap.class, autoRemove);
	}

	/**
	 * 构造方法。
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param customMap 定制Map
	 * @param eleMapClazz 元素类型
	 * @param autoRemove 自动移走空的Map
	 */
	public CascadeMap(Map customMap, Class eleMapClazz, boolean autoRemove) {
		super(customMap);
		this.eleMapClazz = eleMapClazz;
		this.autoRemove = autoRemove;
	}

	/**
	 * 创建元素Map实例
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 元素Map实例
	 */
	protected Map<K2, V> createEleMap() {
		try {
			return eleMapClazz.newInstance();
		} catch (Exception e) {
			throw new ConstructorException(e.getMessage(), e);
		}
	}

	/**
	 * put值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param key2
	 * @param value
	 * @return 值
	 */
	public V put(K1 key1, K2 key2, V value) {
		Map<K2, V> map = get(key1);
		if (map == null) {
			map = createEleMap();
			put(key1, map);
		}
		return map.put(key2, value);
	}

	/**
	 * get值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param key2
	 * @return 值
	 */
	public V get(K1 key1, K2 key2) {
		Map<K2, V> map = get(key1);
		return map == null ? null : map.get(key2);
	}

	/**
	 * 获得元素Map
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param create 如果没有则创建
	 * @return
	 */
	public Map<K2, V> get(K1 key1, boolean create) {
		Map<K2, V> map = get(key1);
		if (map == null && create) {
			map = createEleMap();
			put(key1, map);
		}
		return map;
	}

	/**
	 * 移走值
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param key2
	 * @return
	 */
	public V removeValue(K1 key1, K2 key2) {
		Map<K2, V> map = get(key1);
		if (map == null) {
			return null;
		} else {
			V v = map.remove(key2);
			if (map.isEmpty())
				remove(key1);
			return v;
		}
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @return
	 */
	public boolean isEmpty(K1 key1) {
		Map<K2, V> map = get(key1);
		return map == null ? true : map.isEmpty();
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @return
	 */
	public int size(K1 key1) {
		Map<K2, V> map = get(key1);
		return map == null ? 0 : map.size();
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 */
	public void clear(K1 key1) {
		if (autoRemove) {
			remove(key1);
		} else {
			Map<K2, V> map = get(key1);
			if (map != null) {
				map.clear();
			}
		}
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param key2
	 * @return
	 */
	public boolean containsKey(K1 key1, K2 key2) {
		Map<K2, V> map = get(key1);
		return map == null ? false : map.containsKey(key2);
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param value
	 * @return
	 */
	public boolean containsValue(K1 key1, V value) {
		Map<K2, V> map = get(key1);
		return map == null ? false : map.containsValue(value);
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param defaultValue
	 * @return
	 */
	public Set<K2> keySet(K1 key1, Set<K2> defaultValue) {
		Map<K2, V> map = get(key1);
		return map == null ? defaultValue : map.keySet();
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param defaultValue
	 * @return
	 */
	public Set<Entry<K2, V>> entrySet(K1 key1, Set<Entry<K2, V>> defaultValue) {
		Map<K2, V> map = get(key1);
		return map == null ? defaultValue : map.entrySet();
	}

	/**
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key1
	 * @param defaultValue
	 * @return
	 */
	public Collection<V> values(K1 key1, Collection<V> defaultValue) {
		Map<K2, V> map = get(key1);
		return map == null ? defaultValue : map.values();
	}
}
