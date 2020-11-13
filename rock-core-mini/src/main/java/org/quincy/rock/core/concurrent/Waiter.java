package org.quincy.rock.core.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.quincy.rock.core.function.Consumer;

/**
 * <b>Waiter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月13日 下午12:00:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class Waiter<T, EX extends RuntimeException> {
	/**
	 * 互斥锁。
	 */
	private ReentrantLock lock;
	/**
	 * 是否有响应值返回。
	 */
	private Condition hasRespone;

	/**
	 * 成功回调过程闭包。
	 */
	private Consumer<T> succeed;

	/**
	 * 失败回调过程闭包。
	 */
	private Consumer<Exception> failed;

	/**
	 * 结果值。
	 */
	private T result;
	/**
	 * 异常对象。
	 */
	private EX exception;
	/**
	 * 是否正在等待结果值。
	 */
	private boolean waiting;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public Waiter() {
		this(true);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param async 是否是同步的
	 */
	public Waiter(boolean sync) {
		if (sync) {
			lock = new ReentrantLock();
			hasRespone = lock.newCondition();
		}
	}

	/**
	 * <b>设置成功回调Consumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param succeed 成功回调Consumer
	 */
	public void setSucceed(Consumer<T> succeed) {
		this.succeed = succeed;
	}

	/**
	 * <b>设置失败回调Consumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param failed 失败回调Consumer
	 */
	public void setFailed(Consumer<Exception> failed) {
		this.failed = failed;
	}

	/**
	 * <b>是否是同步的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是同步的
	 */
	public boolean isSync() {
		return lock != null;
	}

	/**
	 * <b>加锁。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void lock() {
		if (isSync()) {
			lock.lock();
		}
	}

	/**
	 * <b>解锁。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void unlock() {
		if (isSync()) {
			lock.unlock();
		}
	}

	/**
	 * <b>等待信号量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timeout 超时数
	 * @param unit 时间单位
	 * @return 是否成功，超时返回false
	 * @throws InterruptedException
	 */
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		if (isSync()) {
			this.waiting = true;
			return hasRespone.await(timeout, unit);
		}
		return true;
	}

	/**
	 * <b>成功返回值时调用。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 结果值
	 */
	public void succeed(T result) {
		this.result = result;
		if (isSync() && isWaiting()) {
			hasRespone.signal();
		} else if (succeed != null) {
			succeed.call(this.result);
		}
	}

	/**
	 * <b>失败时调用。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ex 失败异常
	 */
	public void failed(EX ex) {
		this.exception = ex;
		if (isSync() && isWaiting()) {
			hasRespone.signal();
		} else if (failed != null) {
			failed.call(this.exception);
		} else {
			throw ex;
		}
	}

	/**
	 * <b>获得结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 结果值
	 */
	public T getResult() {
		return result;
	}

	/**
	 * <b>获得异常对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 异常对象
	 */
	public EX getException() {
		return exception;
	}

	/**
	 * <b>是否正在等待结果值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否正在等待结果值
	 */
	public boolean isWaiting() {
		return waiting;
	}

	/**
	 * <b>reset。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void reset() {
		this.waiting = false;
		this.result = null;
		this.exception = null;
	}
}