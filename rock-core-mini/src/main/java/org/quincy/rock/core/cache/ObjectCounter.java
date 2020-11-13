package org.quincy.rock.core.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <b>对象计数器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>王昆山</td><td>2013-6-8 上午11:08:18</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author 王昆山
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ObjectCounter<T> implements Serializable {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7663261811383773007L;

	/**
	 * 计数器Map。
	 */
	private final Map<T, Counter<T>> counterMap;

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public ObjectCounter() {
		this(false);
	}

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param keepOrder 是否保持数据的原始顺序
	 */	
	public ObjectCounter(boolean keepOrder) {
		this.counterMap = keepOrder ? new LinkedHashMap() : new HashMap();
	}

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param oc 对象计数器
	 */
	public ObjectCounter(ObjectCounter<T> oc) {
		this(oc.counterMap instanceof LinkedHashMap);
		for (Counter<T> counter : oc.counterMap.values()) {
			counterMap.put(counter.value, new Counter<T>(counter));
		}
	}

	/**
	 * <b>放入对象。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param o 对象
	 * @return 经过优化的对象
	 */
	public T putObject(T o) {
		Counter<T> counter = counterMap.get(o);
		if (counter == null) {
			counter = new Counter<T>(o);
			counterMap.put(o, counter);
		}
		return counter.inc();
	}

	/**
	 * <b>移走对象。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param o 对象
	 * @return 经过优化的对象或null
	 */
	public T removeObject(Object o) {
		Counter<T> counter = counterMap.get(o);
		if (counter != null) {
			if (counter.dec()) {
				counterMap.remove(counter.value);
			}
			return counter.value;
		} else
			return null;
	}

	/**
	 * <b>获得对象个数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 对象个数
	 */
	public int getSize() {
		return counterMap.size();
	}

	/**
	 * <b>返回对象的集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 对象的集合
	 */
	public Set<T> objectSet() {
		return counterMap.keySet();
	}

	/**
	 * <b>获得计数器的集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 计数器的集合
	 */
	public Collection<Counter<T>> counters() {
		return counterMap.values();
	}

	/**
	 * <b>获得总计数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 总计数
	 */
	public int getCount() {
		int count = 0;
		for (Counter<T> counter : counterMap.values()) {
			count += counter.getCount();
		}
		return count;
	}

	/**
	 * <b>获得对象计数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param o 对象
	 * @return 对象计数
	 */
	public int getCount(Object o) {
		Counter<T> counter = counterMap.get(o);
		return counter == null ? 0 : counter.getCount();
	}

	/**
	 * <b>清空对象计数器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void clear() {
		counterMap.clear();
	}

	/**
	 * <b>清空对象计数。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param o 对象
	 * @return 清空的对象
	 */
	public T clear(Object o) {
		Counter<T> counter = counterMap.remove(o);
		return counter == null ? null : counter.value;
	}

	/**
	 * <b>是否有对象。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param o 对象
	 * @return 是否有对象
	 */
	public boolean hasObject(Object o) {
		return counterMap.containsKey(o);
	}
}
