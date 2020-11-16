package org.quincy.rock.core.os;

import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.quincy.rock.core.cache.CachePoolRef;
import org.quincy.rock.core.vo.Vo;

import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

/**
 * <b>服务器基本信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月6日 上午10:17:41</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ServerInfo extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 951901223081411357L;

	private final OperatingSystem os;
	private final HardwareAbstractionLayer hal;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param os OperatingSystem
	 * @param hal HardwareAbstractionLayer
	 */
	public ServerInfo(OperatingSystem os, HardwareAbstractionLayer hal) {
		this.os = os;
		this.hal = hal;
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
	 * <b>获得服务器物理名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器物理名称
	 */
	public String getName() {
		return SystemUtils.getHostName();
	}

	/**
	 * <b>获得IP地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return IP地址数组
	 */
	public String[] getAddress() {
		List<NetInterface> list = OSUtil.getNetInterfaces();
		int size = list.size();
		String[] ss = new String[size];
		for (int i = 0; i < size; i++) {
			ss[i] = list.get(i).getAddress();
		}
		return ss;
	}

	/**
	 * <b>获得操作系统名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统名称
	 */
	public String getOsName() {
		return os.toString();
	}

	/**
	 * <b>获得操作系统版本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统版本
	 */
	public String getOsVersion() {
		return os.getVersionInfo().getVersion();
	}

	/**
	 * <b>获得操作系统架构。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统架构
	 */
	public String getOsArch() {
		return SystemUtils.OS_ARCH;
	}

	/**
	 * <b>获得服务器操作系统时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器操作系统时间
	 */
	public long getOsTime() {
		return System.currentTimeMillis();
	}

	/**
	 * <b>获得操作系统启动时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统启动时间
	 */
	public long getOsBootTime() {
		return os.getSystemBootTime() * 1000;
	}

	/**
	 * <b>获得JavaHome。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return JavaHome
	 */
	public String getJavaHome() {
		return SystemUtils.getJavaHome().getAbsolutePath();
	}

	/**
	 * <b>获得java版本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return java版本
	 */
	public String getJavaVersion() {
		return SystemUtils.JAVA_VERSION;
	}

	/**
	 * <b>获得缓冲池数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓冲池数量
	 */
	public int getCachePoolCount() {
		return CachePoolRef.cpCount();
	}

	/**
	 * <b>获得操作系统运行线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统运行线程数
	 */
	public int getThreadCount() {
		return os.getThreadCount();
	}

	/**
	 * <b>获得操作系统运行进程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统运行进程数
	 */
	public int getProcessCount() {
		return os.getProcessCount();
	}

	/**
	 * <b>获得当前进程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前进程id
	 */
	public int getProcessId() {
		return os.getProcessId();
	}

	/**
	 * <b>获得当前进程名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前进程名称
	 */
	public String getProcessName() {
		OSProcess p = os.getProcess(os.getProcessId());
		return p.getName();
	}

	/**
	 * <b>获得处理器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理器名称
	 */
	public String getProcessorName() {
		return hal.getProcessor().getProcessorIdentifier().getName();
	}

	/**
	 * <b>获得处理器频率。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理器频率
	 */
	public long getProcessorFreq() {
		return hal.getProcessor().getMaxFreq();
	}

	/**
	 * <b>获得物理内核数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物理内核数量
	 */
	public int getPhyProcessors() {
		return hal.getProcessor().getPhysicalProcessorCount();
	}

	/**
	 * <b>获得逻辑内核数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 逻辑内核数量
	 */
	public int getLogicProcessors() {
		return hal.getProcessor().getLogicalProcessorCount();
	}

	/**
	 * <b>获得内存信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 内存信息
	 */
	public MemoryInfo getMemoryInfo() {
		return new MemoryInfo(hal.getMemory());
	}
}
