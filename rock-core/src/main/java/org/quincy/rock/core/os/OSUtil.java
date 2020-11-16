package org.quincy.rock.core.os;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quincy.rock.core.cache.CachePoolRef;
import org.quincy.rock.core.cache.ObjectCachePool;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.concurrent.Bounded;
import org.quincy.rock.core.concurrent.ProcessService;
import org.quincy.rock.core.concurrent.QueueProcessService;
import org.quincy.rock.core.util.DateUtil;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OSService;
import oshi.software.os.OSService.State;
import oshi.software.os.OperatingSystem;

/**
 * <b>OSUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月7日 下午4:03:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public final class OSUtil {
	/**
	 * 系统信息。
	 */
	private static final SystemInfo SYSTEM_INFO = new SystemInfo();

	/**
	 * <b>获得操作系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作系统
	 */
	static OperatingSystem getOperatingSystem() {
		return SYSTEM_INFO.getOperatingSystem();
	}

	/**
	 * <b>获得硬件系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 硬件系统
	 */
	static HardwareAbstractionLayer getHardware() {
		return SYSTEM_INFO.getHardware();
	}

	/**
	 * <b>获得服务器基本信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器基本信息
	 */
	public static ServerInfo getServerInfo() {
		return new ServerInfo(getOperatingSystem(), getHardware());
	}

	/**
	 * <b>获得CPU信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CPU信息列表
	 */
	public static CpuInfo getCpuInfo() {
		HardwareAbstractionLayer hal = getHardware();
		CentralProcessor cp = hal.getProcessor();
		long[] prevTicks = cp.getSystemCpuLoadTicks();
		long[][] prevCorecTicks = cp.getProcessorCpuLoadTicks();
		DateUtil.sleep(1000);
		long[] ticks = cp.getSystemCpuLoadTicks();
		long[][] corecTicks = cp.getProcessorCpuLoadTicks();
		CpuInfo cpu = new CpuInfo(cp.getProcessorIdentifier().getName());
		cpu.setFreq(cp.getMaxFreq());
		calculateCpuPercent(ticks, prevTicks, cpu);
		for (int i = 0, l = corecTicks.length; i < l; i++) {
			CpuCoreInfo cci = new CpuCoreInfo(i);
			cci.setFreq(cp.getCurrentFreq()[i]);
			calculateCpuPercent(corecTicks[i], prevCorecTicks[i], cci);
			cpu.addCpuCoreInfo(cci);
		}
		return cpu;
	}

	/**
	 * <b>获得本机的物理网卡列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物理网卡列表
	 */
	public static List<NetInterface> getNetInterfaces() {
		List<NetInterface> list = new ArrayList<>();
		HardwareAbstractionLayer hal = getHardware();
		NetworkIF[] ns = hal.getNetworkIFs();
		for (NetworkIF n : ns) {
			String name = n.getDisplayName();
			String[] addr = n.getIPv4addr();
			if (addr.length > 0 && !name.contains("Virtual Adapter")) {
				n.updateAttributes();
				NetInterface ni = new NetInterface(n);
				list.add(ni);
			}
		}
		return list;
	}

	/**
	 * <b>获得磁盘分区列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘分区列表
	 */
	public static List<DiskPartition> getDiskPartitions() {
		List<DiskPartition> list = new ArrayList<>();
		HardwareAbstractionLayer hal = getHardware();
		OperatingSystem os = getOperatingSystem();
		HWDiskStore[] disks = hal.getDiskStores();
		OSFileStore[] fss = os.getFileSystem().getFileStores(true);
		for (OSFileStore fs : fss) {
			DiskPartition dp = new DiskPartition(fs);
			list.add(dp);
		}
		//
		for (HWDiskStore disk : disks) {
			HWPartition[] ps = disk.getPartitions();
			for (HWPartition p : ps) {
				DiskPartition dp = findDiskPartition(list, p.getUuid());
				if (dp != null) {
					dp.setDiskName(disk.getName());
					dp.setDiskInfo(p.getIdentification());
				}
			}
		}
		return list;
	}

	/**
	 * <b>获得磁盘信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘信息列表
	 */
	public static List<Disk> getDisks() {
		List<Disk> list = new ArrayList<>();
		HWDiskStore[] disks = getHardware().getDiskStores();
		for (HWDiskStore disk : disks) {
			list.add(new Disk(disk));
		}
		return list;
	}

	/**
	 * <b>获得内存信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 内存信息
	 */
	public static MemoryInfo getMemoryInfo() {
		return new MemoryInfo(getHardware().getMemory());
	}

	/**
	 * <b>获得OS后台服务信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return OS后台服务信息列表
	 */
	public static List<ServiceInfo> getServiceInfos() {
		OperatingSystem os = getOperatingSystem();
		OSService[] osss = os.getServices();
		List<ServiceInfo> list = new ArrayList<>(osss.length);
		for (OSService oss : osss) {
			ServiceInfo si = new ServiceInfo(oss.getName(), oss.getState() == State.RUNNING, oss.getProcessID());
			list.add(si);
		}
		return list;
	}

	/**
	 * <b>获得OS进程信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return OS进程信息列表
	 */
	public static List<ProcessInfo> getProcessInfos() {
		OperatingSystem os = getOperatingSystem();
		OSProcess[] ps = os.getProcesses();
		List<ProcessInfo> list = new ArrayList<>(ps.length);
		for (OSProcess p : ps) {
			ProcessInfo pi = new ProcessInfo(p.getProcessID(), p.getName());
			pi.setUser(p.getUser());
			pi.setPath(p.getPath());
			pi.setOpenFiles(p.getOpenFiles());
			pi.setThreadCount(p.getThreadCount());
			pi.setStartTime(p.getStartTime());
			pi.setRunTime(p.getUpTime());
			pi.setParentId(p.getParentProcessID());
			pi.setMemory(p.getResidentSetSize());
			pi.setCpuPercent(((int) (p.getProcessCpuLoadCumulative() * 100)) / 100.0);
			pi.setDiskReads(p.getBytesRead());
			pi.setDiskWrites(p.getBytesWritten());
			pi.setBitness(p.getBitness());
			list.add(pi);
		}
		return list;
	}

	/**
	 * <b>获得所有的处理服务信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 所有的处理服务信息
	 */
	public static List<ProcessServiceInfo> getAllProcessServiceInfo() {
		Collection<ProcessService<?, ?>> c = ProcessService.getAllProcessService();
		List<ProcessServiceInfo> list = new ArrayList<>();
		for (ProcessService<?, ?> ps : c) {
			if (ps instanceof QueueProcessService) {
				list.add(createProcessServiceInfo((QueueProcessService) ps));
			}
		}
		return list;
	}

	/**
	 * <b>获得所有的缓存池信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缓存池信息列表
	 */
	public static List<CachePoolInfo> getAllCachePoolInfo() {
		Collection<CachePoolRef> c = CachePoolRef.getAllCachePool();
		List<CachePoolInfo> list = new ArrayList<>(c.size());
		for (CachePoolRef cp : c) {
			if (cp instanceof ObjectCachePool) {
				CachePoolInfo cpi = new CachePoolInfo(cp.getId(), cp.getName());
				cpi.setDescribe(cp.getDescribe());
				cpi.setTimeout(cp.getTimeout());
				cpi.setCacheSize(cp.getCacheSize());
				cpi.setCount(cp.count());
				list.add(cpi);
			}
		}
		return list;
	}

	/**
	 * <b>获得所有的物主缓存池信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param 预警临界点(0-100)
	 * @return 物主缓存池信息列表
	 */
	public static List<OwnerCachePoolInfo> getAllOwnerCachePoolInfo(int critical) {
		Collection<CachePoolRef> c = CachePoolRef.getAllCachePool();
		List<OwnerCachePoolInfo> list = new ArrayList<>(c.size());
		for (CachePoolRef cp : c) {
			if (cp instanceof OwnerCachePool) {
				OwnerCachePool ocp = (OwnerCachePool) cp;
				OwnerCachePoolInfo cpi = new OwnerCachePoolInfo(ocp.getId(), ocp.getName());
				cpi.setDescribe(ocp.getDescribe());
				cpi.setTimeout(ocp.getTimeout());
				cpi.setCacheSize(ocp.getCacheSize()); //一级池大小				
				cpi.setOwnerCacheSize(ocp.getOwnerCacheSize()); //二级池大小
				int total = 0, ownerCount = 0, emptyCount = 0, fullCount = 0, criticalCount = 0,
						ocSize = ocp.getOwnerCacheSize(), count;
				for (Object owner : ocp.owners()) {
					ownerCount++; //物主数量
					if (ocp.isEmpty(owner))
						emptyCount++; //空池物主数量
					if (ocp.isFull(owner))
						fullCount++; //满池物主数量
					count = ocp.count(owner); //物主所拥有的缓存对象数量
					if ((100.0 * count / ocSize) >= critical)
						criticalCount++; //预警数量
					total += count; //缓存对象总数量
				}
				cpi.setAvgCount(total / ownerCount);
				cpi.setCount(ownerCount);
				cpi.setEmptyCount(emptyCount);
				cpi.setFullCount(fullCount);
				cpi.setCriticalCount(criticalCount);
				list.add(cpi);
			}
		}
		return list;
	}

	/**
	 * <b>找到指定id的DiskPartition。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dps 分区列表
	 * @param id 分区id
	 * @return DiskPartition
	 */
	private static DiskPartition findDiskPartition(List<DiskPartition> dps, String id) {
		for (DiskPartition dp : dps) {
			if (id.equals(dp.id())) {
				return dp;
			}
		}
		return null;
	}

	/**
	 * <b>创建ProcessServiceInfo。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ps QueueProcessService
	 * @return ProcessServiceInfo
	 */
	private static ProcessServiceInfo createProcessServiceInfo(QueueProcessService<?, ?> ps) {
		ProcessServiceInfo psi = new ProcessServiceInfo(ps.getId(), ps.getName());
		psi.setDescribe(ps.getDescribe());
		psi.setMaxThreadCount(ps.getMaxThreadCount());
		psi.setThreadCount(ps.activeThreadCount());
		psi.setTimeout(ps.getTimeout());
		for (int i = 0; i < psi.getThreadCount(); i++) {
			ProcessThreadInfo pti = new ProcessThreadInfo(i);
			pti.setWaitCount(ps.count(i));
			pti.setWaitSecond(ps.waitSeconds(i));
			pti.setProcessMillis(ps.processMillis(i));
			pti.setProcessCount(ps.processCount(i));
			psi.addProcessThread(pti);
		}
		if (ps instanceof Bounded) {
			psi.setCapacity(((Bounded) ps).getCapacity());
		} else {
			psi.setCapacity(Integer.MAX_VALUE);
		}
		return psi;
	}

	//计算cpu利用率
	private static void calculateCpuPercent(long[] ticks, long[] prevTicks, Cpu<?> cpu) {
		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		long softIrq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		double total = nice + irq + softIrq + steal + iowait + sys + user + idle;
		double sysPercent = ((int) (10000 * sys / total)) / 100.0;
		double userPercent = ((int) (10000 * user / total)) / 100.0;
		double idlePercent = ((int) (10000 * idle / total)) / 100.0;
		double percent = ((int) (10000 * (total - idle) / total)) / 100.0;
		cpu.setPercent(percent);
		cpu.setSysPercent(sysPercent);
		cpu.setUserPercent(userPercent);
		cpu.setIdlePercent(idlePercent);
	}
}
