package org.quincy.rock.core.os;

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
public class ProcessThreadInfo extends Vo<Integer> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -761896760725008351L;

	/**
	 * 线程id。
	 */
	private int id;
	/**
	 * 指定线程的待处理报文条数。
	 */
	private int waitCount;
	/**
	 * 指定线程的报文等待秒数。
	 */
	private int waitSecond;
	/**
	 * 单条数据处理毫秒数。
	 */
	private long processMillis;
	/**
	 * 处理数据条数。
	 */
	private long processCount;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 线程id
	 */
	public ProcessThreadInfo(int id) {
		this.id = id;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Integer id() {
		return id;
	}

	/**
	 * <b>获得线程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 线程id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <b>获得指定线程的待处理报文条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指定线程的待处理报文条数
	 */
	public int getWaitCount() {
		return waitCount;
	}

	/**
	 * <b>设置指定线程的待处理报文条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param waitCount 指定线程的待处理报文条数
	 */
	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}

	/**
	 * <b>获得指定线程的报文等待秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指定线程的报文等待秒数
	 */
	public int getWaitSecond() {
		return waitSecond;
	}

	/**
	 * <b>设置指定线程的报文等待秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param waitSecond 指定线程的报文等待秒数
	 */
	public void setWaitSecond(int waitSecond) {
		this.waitSecond = waitSecond;
	}

	/**
	 * <b>获得单条数据处理毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 单条数据处理毫秒数
	 */
	public long getProcessMillis() {
		return processMillis;
	}

	/**
	 * <b>设置单条数据处理毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processMillis 单条数据处理毫秒数
	 */
	public void setProcessMillis(long processMillis) {
		this.processMillis = processMillis;
	}

	/**
	 * <b>获得处理数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理数据条数
	 */
	public long getProcessCount() {
		return processCount;
	}

	/**
	 * <b>设置处理数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processCount 处理数据条数
	 */
	public void setProcessCount(long processCount) {
		this.processCount = processCount;
	}
}
