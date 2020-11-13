package org.quincy.rock.core.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * <b>对象缓冲。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 不要缓存空值。
 * 当缓存值超时或被垃圾收集后会失效。
 * 如果缓存值是失效的则get方法返回null。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年12月5日 上午11:01:43</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
class SoftObjectCacheImpl<T> extends SoftReference<T> implements ObjectCache<T> {
	/**
	 * 最后访问时间。
	 */
	private long lastAccessTime = System.currentTimeMillis();
	/**
	 * 缓存值超时毫秒数。
	 */
	private int timeout = -1;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 缓冲值
	 */
	public SoftObjectCacheImpl(T value) {
		super(value);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 缓冲值
	 * @param queue 引用队列
	 */
	public SoftObjectCacheImpl(T value, ReferenceQueue<? super T> queue) {
		super(value, queue);
	}

	/** 
	 * lastAccessTime。
	 * @see org.quincy.rock.core.cache.HasAccessTime#lastAccessTime()
	 */
	@Override
	public long lastAccessTime() {
		return lastAccessTime;
	}

	/** 
	 * updateAccessTime。
	 * @see org.quincy.rock.core.cache.HasAccessTime#updateAccessTime()
	 */
	@Override
	public void updateAccessTime() {
		this.lastAccessTime = System.currentTimeMillis();
	}

	/** 
	 * get。
	 * @see java.lang.ref.SoftReference#get()
	 */
	@Override
	public T get() {
		if (timeouted(timeout))
			return null;
		else {
			T value = super.get();
			if (value != null)
				updateAccessTime();
			return value;
		}
	}

	/** 
	 * seek。
	 * @see org.quincy.rock.core.cache.ObjectCache#seek()
	 */
	@Override
	public T seek() {
		return super.get();
	}

	/** 
	 * timeout。
	 * @see org.quincy.rock.core.cache.ObjectCache#timeout()
	 */
	@Override
	public final int timeout() {
		return timeout;
	}

	/** 
	 * timeout。
	 * @see org.quincy.rock.core.cache.ObjectCache#timeout(int)
	 */
	@Override
	public final ObjectCache<T> timeout(int timeout) {
		this.timeout = timeout < 0 ? -1 : timeout;
		return this;
	}

	/** 
	 * isValid。
	 * @see org.quincy.rock.core.cache.ObjectCache#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean invalid = timeouted(timeout) || super.get() == null;
		return !invalid;
	}

	/** 
	 * invalidate。
	 * @see org.quincy.rock.core.cache.ObjectCache#invalidate()
	 */
	@Override
	public void invalidate() {
		this.lastAccessTime = 0;
	}

	//缓存值是否超时
	private boolean timeouted(int timeout) {
		return timeout == -1 ? false : (System.currentTimeMillis() > (lastAccessTime + timeout));
	}
}
