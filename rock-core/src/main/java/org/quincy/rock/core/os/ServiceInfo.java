package org.quincy.rock.core.os;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>OS后台服务信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月8日 上午11:53:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ServiceInfo extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -5806226903423512994L;

	/**
	 * 服务名称。
	 */
	private String name;
	/**
	 * 是否正在运行。
	 */
	private boolean running;
	/**
	 * 进程id。
	 */
	private int processID;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 服务名称
	 * @param running 是否正在运行
	 * @param processID 进程id
	 */
	public ServiceInfo(String name, boolean running, int processID) {		
		this.name = name;
		this.running = running;
		this.processID = processID;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return getName();
	}

	/**
	 * <b>获得服务名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>是否正在运行。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否正在运行
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * <b>获得进程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 进程id
	 */
	public int getProcessID() {
		return processID;
	}

}
