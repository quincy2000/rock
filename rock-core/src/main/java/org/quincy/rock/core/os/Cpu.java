package org.quincy.rock.core.os;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>Cpu。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月6日 下午4:55:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class Cpu<ID> extends Vo<ID> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1890508800121650588L;

	/**
	 * cpu频率。
	 */
	private long freq;
	/**
	 * cpu总利用率。
	 */
	private double percent;
	/**
	 * 用户cpu利用率。
	 */
	private double userPercent;
	/**
	 * 系统cpu利用率。
	 */
	private double sysPercent;
	/**
	 * cpu空闲率。
	 */
	private double idlePercent;

	/**
	 * <b>获得cpu频率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return cpu频率
	 */
	public long getFreq() {
		return freq;
	}

	/**
	 * <b>设置cpu频率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param freq cpu频率
	 */
	public void setFreq(long freq) {
		this.freq = freq;
	}

	/**
	 * <b>获得cpu总利用率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return cpu总利用率
	 */
	public double getPercent() {
		return percent;
	}

	/**
	 * <b>设置cpu总利用率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param percent cpu总利用率
	 */
	public void setPercent(double percent) {
		this.percent = percent;
	}

	/**
	 * <b>获得用户cpu利用率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户cpu利用率
	 */
	public double getUserPercent() {
		return userPercent;
	}

	/**
	 * <b>设置用户cpu利用率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userPercent 用户cpu利用率
	 */
	public void setUserPercent(double userPercent) {
		this.userPercent = userPercent;
	}

	/**
	 * <b>获得系统cpu利用率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 系统cpu利用率
	 */
	public double getSysPercent() {
		return sysPercent;
	}

	/**
	 * <b>设置系统cpu利用率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sysPercent 系统cpu利用率
	 */
	public void setSysPercent(double sysPercent) {
		this.sysPercent = sysPercent;
	}

	/**
	 * <b>获得cpu空闲率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return cpu空闲率
	 */
	public double getIdlePercent() {
		return idlePercent;
	}

	/**
	 * <b>设置cpu空闲率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param idlePercent cpu空闲率
	 */
	public void setIdlePercent(double idlePercent) {
		this.idlePercent = idlePercent;
	}
}
