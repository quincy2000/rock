package org.quincy.rock.core.bean;

import java.beans.PropertyDescriptor;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * <b>BeanMap。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * BeanMap的目的是把java bean封装成Map来使用。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2009-5-8 下午10:24:01</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BeanMap extends AbstractMap<String, Object> {
	/**
	 * beanWrapper。
	 */
	private BeanWrapper beanWrapper;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bean 要封装的bean
	 */
	public BeanMap(Object bean) {
		beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
	}

	/**
	 * <b>beanWrapper。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public BeanWrapper beanWrapper() {
		return beanWrapper;
	}

	/**
	 * <b>wrappedInstance。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public Object wrappedInstance() {
		return beanWrapper == null ? null : beanWrapper.getWrappedInstance();
	}

	/**
	 * <b>containsKey。</b>
	 * @see java.util.AbstractMap#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object name) {
		return keySet().contains(name);
	}

	/**
	 * <b>entrySet。</b>
	 * @see java.util.AbstractMap#entrySet()
	 */
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		Set<Map.Entry<String, Object>> set = new AbstractSet<Map.Entry<String, Object>>() {

			public Iterator<Map.Entry<String, Object>> iterator() {
				final Iterator<String> iter = keySet().iterator();
				return new Iterator<Map.Entry<String, Object>>() {
					public boolean hasNext() {
						return iter.hasNext();
					}

					public Map.Entry<String, Object> next() {
						String key = iter.next();
						return new Entry(BeanMap.this, key);
					}

					public void remove() {
						throw new UnsupportedOperationException("remove() not supported for BeanMap");
					}
				};
			}

			public int size() {
				return keySet().size();
			}

		};
		return set;
	}

	/**
	 * <b>toString。</b>
	 * @see java.util.AbstractMap#toString()
	 */
	public String toString() {
		return "BeanMap<" + String.valueOf(wrappedInstance()) + ">";
	}

	/**
	 * <b>get。</b>
	 * @see java.util.AbstractMap#get(java.lang.Object)
	 */
	public Object get(Object key) {
		return beanWrapper.getPropertyValue((String) key);
	}

	/**
	 * <b>put。</b>
	 * @see java.util.AbstractMap#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(String key, Object value) {
		Object oldValue = canRead(key) ? beanWrapper.getPropertyValue(key) : null;
		beanWrapper.setPropertyValue(key, value);
		return oldValue;
	}

	/**
	 * <b>size。</b>
	 * @see java.util.AbstractMap#size()
	 */
	public int size() {
		return keySet().size();
	}

	/**
	 * <b>keySet。</b>
	 * @see java.util.AbstractMap#keySet()
	 */
	public synchronized Set<String> keySet() {
		if (keySet == null) {
			keySet = new HashSet<>();
			for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
				if (!"class".equals(pd.getName()))
					keySet.add(pd.getName());
			}
			keySet = Collections.unmodifiableSet(keySet);
		}
		return keySet;
	}

	private transient Set<String> keySet;

	/**
	 * <b>values。</b>
	 * @see java.util.AbstractMap#values()
	 */
	public Collection<Object> values() {
		Set<String> keys = keySet();
		ArrayList list = new ArrayList(keys.size());
		for (String key : keys) {
			list.add(get(key));
		}
		return Collections.unmodifiableCollection(list);
	}

	protected static class Entry implements Map.Entry<String, Object> {
		private BeanMap owner;
		private String key;

		public Entry(BeanMap owner, String key) {
			this.owner = owner;
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public Object getValue() {
			return owner.get(key);
		}

		public Object setValue(Object value) {
			return owner.put(key, value);
		}
	}

	/**
	 * 属性是否可读
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 属性名
	 * @return 属性是否可读
	 */
	public boolean canRead(String name) {
		return beanWrapper.isReadableProperty(name);
	}

	/**
	 * 属性是否可写
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 属性名
	 * @return 属性是否可写
	 */
	public boolean canWrite(String name) {
		return beanWrapper.isWritableProperty(name);
	}
}
