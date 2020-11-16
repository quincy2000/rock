package org.quincy.rock.core.os;

import java.util.ArrayList;
import java.util.List;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>处理服务线程信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月13日 下午5:38:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ProcessServiceInfo extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 5070721878196237516L;
	/**
	 * 处理服务id。
	 */
	private final String id;
	/**
	 * 处理服务名称。
	 */
	private final String name;
	/**
	 * 处理服务描述。
	 */
	private String describe;
	/**
	 * 最大线程数。
	 */
	private int maxThreadCount;
	/**
	 * 当前线程数。
	 */
	private int threadCount;
	/**
	 * 阻塞超时秒数。
	 */
	private int timeout;
	/**
	 * 每个线程持有报文队列容量。
	 */
	private int capacity;
	/**
	 * 处理线程队列信息。
	 */
	private final List<ProcessThreadInfo> processThreads = new ArrayList<>();

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 服务id
	 * @param name 报文服务器名称
	 */
	public ProcessServiceInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return getId();
	}

	/**
	 * <b>获得服务id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * <b>获得报文服务器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文服务器名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>获得处理服务描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理服务描述
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * <b>设置处理服务描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param describe 处理服务描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * <b>获得最大线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大线程数
	 */
	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	/**
	 * <b>设置最大线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 */
	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	/**
	 * <b>获得当前线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前线程数
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * <b>设置当前线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadCount 当前线程数
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	/**
	 * <b>获得阻塞超时秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 阻塞超时秒数
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <b>设置阻塞超时秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timeout 阻塞超时秒数
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * <b>获得每个线程持有报文队列容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 每个线程持有报文队列容量
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * <b>设置每个线程持有报文队列容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param capacity 每个线程持有报文队列容量
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * <b>获得处理线程队列信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理线程队列信息
	 */
	public List<ProcessThreadInfo> getProcessThreads() {
		return processThreads;
	}

	/**
	 * <b>设置处理线程队列信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processThreads 处理线程队列信息
	 */
	public void setProcessThreads(List<ProcessThreadInfo> processThreads) {
		this.processThreads.clear();
		this.processThreads.addAll(processThreads);
	}

	/**
	 * <b>添加处理线程队列信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processThread 处理线程队列信息
	 */
	public void addProcessThread(ProcessThreadInfo processThread) {
		this.processThreads.add(processThread);
	}
}
