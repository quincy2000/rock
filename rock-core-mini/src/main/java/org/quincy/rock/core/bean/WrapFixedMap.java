package org.quincy.rock.core.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b>包装内容固定的Map。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 对内容固定Map的一个封装Map。
 * 内容固定是指不可以新增Key。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>王昆山</td><td>2013-4-26 下午06:10:00</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author 王昆山
 * @since 1.0
 */
public class WrapFixedMap<K, V> implements Map<K, V> {
	/**
	 * 内容固定的Map。
	 */
	private Map<K, V> fixedMap;
	/**
	 * 内容可编辑的Map。
	 */
	private Map<K, V> editedMap;
	/**
	 * 优先读取内容固定的Map。
	 */
	private boolean fixPriority;

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fixedMap 内容固定的Map
	 * @param fixPriority 优先读取内容固定的Map
	 */
	public WrapFixedMap(Map<K, V> fixedMap, boolean fixPriority) {
		this(fixedMap, null, fixPriority);
	}

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fixedMap 内容固定的Map
	 * @param editedMap 内容可编辑的Map
	 * @param fixPriority 优先读取内容固定的Map
	 */
	public WrapFixedMap(Map<K, V> fixedMap, Map<K, V> editedMap, boolean fixPriority) {
		this.fixedMap = fixedMap;
		this.editedMap = editedMap;
		this.fixPriority = fixPriority;
	}

	/**
	 * <b>获得内容可编辑的Map。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 内容可编辑的Map
	 */
	private Map<K, V> getEditedMap() {
		if (editedMap == null) {
			editedMap = new HashMap<>();
		}
		return editedMap;
	}

	/** 
	 * <b>size。</b>  
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		int size = fixedMap.size();
		if (editedMap != null) {
			size += editedMap.size();
		}
		return size;
	}

	/** 
	 * <b>isEmpty。</b>  
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/** 
	 * <b>containsKey。</b>  
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		if (fixedMap.containsKey(key))
			return true;
		if (editedMap != null)
			return editedMap.containsKey(key);
		return false;
	}

	/** 
	 * <b>containsValue。</b>  
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		if (fixedMap.containsValue(value))
			return true;
		if (editedMap != null)
			return editedMap.containsValue(value);
		return false;
	}

	/** 
	 * <b>get。</b>  
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		if (fixPriority) {
			if (fixedMap.containsKey(key))
				return fixedMap.get(key);
			else
				return editedMap == null ? null : editedMap.get(key);
		} else {
			if (editedMap != null && editedMap.containsKey(key))
				return editedMap.get(key);
			else
				return fixedMap.get(key);
		}
	}

	/** 
	 * <b>put。</b>  
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		Map<K, V> editedMap = this.getEditedMap();
		if (fixPriority) {
			if (fixedMap.containsKey(key)) {
				return fixedMap.put(key, value);
			} else {
				return editedMap.put(key, value);
			}
		} else {
			if (editedMap.containsKey(key)) {
				return editedMap.put(key, value);
			} else {
				return fixedMap.put(key, value);
			}
		}
	}

	/** 
	 * <b>remove。</b>  
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		Map<K, V> editedMap = this.getEditedMap();
		if (fixPriority) {
			if (fixedMap.containsKey(key)) {
				return fixedMap.remove(key);
			} else {
				return editedMap.remove(key);
			}
		} else {
			if (editedMap.containsKey(key)) {
				return editedMap.remove(key);
			} else {
				return fixedMap.remove(key);
			}
		}
	}

	/** 
	 * <b>putAll。</b>  
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/** 
	 * <b>clear。</b>  
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		try {
			fixedMap.clear();
		} catch (Exception e) {
		}
		if (editedMap != null)
			editedMap.clear();
	}

	/** 
	 * <b>keySet。</b>  
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<K>();
		set.addAll(fixedMap.keySet());
		if (editedMap != null) {
			set.addAll(editedMap.keySet());
		}
		return set;
	}

	/** 
	 * <b>values。</b>
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		List<V> list = new ArrayList<V>();
		list.addAll(fixedMap.values());
		if (editedMap != null) {
			list.addAll(editedMap.values());
		}
		return list;
	}

	/** 
	 * <b>entrySet。</b>  
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> set = new HashSet<java.util.Map.Entry<K, V>>();
		set.addAll(fixedMap.entrySet());
		if (editedMap != null) {
			set.addAll(editedMap.entrySet());
		}
		return set;
	}
}
