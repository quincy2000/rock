package org.quincy.rock.sso.web;

import java.util.List;
import java.util.function.Function;

import org.quincy.rock.core.cache.ObjectCachePool;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.os.CachePoolInfo;
import org.quincy.rock.core.os.CpuInfo;
import org.quincy.rock.core.os.Disk;
import org.quincy.rock.core.os.DiskPartition;
import org.quincy.rock.core.os.MemoryInfo;
import org.quincy.rock.core.os.NetInterface;
import org.quincy.rock.core.os.OSUtil;
import org.quincy.rock.core.os.OwnerCachePoolInfo;
import org.quincy.rock.core.os.ProcessInfo;
import org.quincy.rock.core.os.ProcessServiceInfo;
import org.quincy.rock.core.os.ServerInfo;
import org.quincy.rock.core.os.ServiceInfo;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>OSController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 实现和OSHttpServlet一样的功能，在spring环境下使用。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月7日 下午5:33:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Controller
@RequestMapping("os")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OSController {
	/**
	 * logger。
	 */
	private static Logger logger = RockUtil.getLogger(OSController.class);

	/**
	 * 列表信息裁剪拦截器。
	 */
	private Function<List, List> cropInterceptor;

	/**
	 * <b>设置列表信息裁剪拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cropInterceptor 列表信息裁剪拦截器
	 */
	public void setCropInterceptor(Function<List, List> cropInterceptor) {
		this.cropInterceptor = cropInterceptor;
	}

	/**
	 * <b>获得服务器基本信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器基本信息
	 */
	@RequestMapping(value = "/getServerInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<ServerInfo> getServerInfo() {
		Result<ServerInfo> result;
		try {
			result = Result.toResult(OSUtil.getServerInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getServerInfo", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得服务器CPU信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器CPU信息
	 */
	@RequestMapping(value = "/getCpuInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<CpuInfo> getCpuInfo() {
		Result<CpuInfo> result;
		try {
			result = Result.toResult(OSUtil.getCpuInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getCpuInfo", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得服务器内存信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器内存信息
	 */
	@RequestMapping(value = "/getMemoryInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<MemoryInfo> getMemoryInfo() {
		Result<MemoryInfo> result;
		try {
			result = Result.toResult(OSUtil.getMemoryInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getMemoryInfo", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得服务器网卡信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器网卡信息
	 */
	@RequestMapping(value = "/getNetInterfaces", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<List<NetInterface>> getNetInterfaces() {
		Result<List<NetInterface>> result;
		try {
			result = Result.toResult(OSUtil.getNetInterfaces());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getNetInterfaces", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得磁盘信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘信息列表
	 */
	@RequestMapping(value = "/getDisks", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<List<Disk>> getDisks() {
		Result<List<Disk>> result;
		try {
			result = Result.toResult(OSUtil.getDisks());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getDisks", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得服务器磁盘分区信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器磁盘分区信息
	 */
	@RequestMapping(value = "/getDiskPartitions", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<List<DiskPartition>> getDiskPartitions() {
		Result<List<DiskPartition>> result;
		try {
			result = Result.toResult(OSUtil.getDiskPartitions());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getDiskPartitions", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得OS后台服务信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return OS后台服务信息列表
	 */
	@RequestMapping(value = "/getServiceInfos", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<PageSet<ServiceInfo>> getServiceInfos(
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<ServiceInfo>> result;
		try {
			List<ServiceInfo> list = OSUtil.getServiceInfos();
			if (cropInterceptor != null) {
				list = cropInterceptor.apply(list);
			}
			result = Result.toResult(PageSet.toPageSet(list, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getServiceInfos", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得OS进程信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return OS进程信息列表
	 */
	@RequestMapping(value = "/getProcessInfos", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<PageSet<ProcessInfo>> getProcessInfos(
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<ProcessInfo>> result;
		try {
			List<ProcessInfo> list = OSUtil.getProcessInfos();
			if (cropInterceptor != null) {
				list = cropInterceptor.apply(list);
			}
			result = Result.toResult(PageSet.toPageSet(list, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getProcessInfos", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得所有的处理服务信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 所有的处理服务信息
	 */
	@RequestMapping(value = "/getAllProcessServiceInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<PageSet<ProcessServiceInfo>> getAllProcessServiceInfo(
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<ProcessServiceInfo>> result;
		try {
			List<ProcessServiceInfo> list = OSUtil.getAllProcessServiceInfo();
			if (cropInterceptor != null) {
				list = cropInterceptor.apply(list);
			}
			result = Result.toResult(PageSet.toPageSet(list, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getAllProcessServiceInfo", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得所有的对象缓存池信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 所有的对象缓存池信息
	 * @see ObjectCachePool
	 */
	@RequestMapping(value = "/getAllCachePoolInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<PageSet<CachePoolInfo>> getAllCachePoolInfo(
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<CachePoolInfo>> result;
		try {
			List<CachePoolInfo> list = OSUtil.getAllCachePoolInfo();
			if (cropInterceptor != null) {
				list = cropInterceptor.apply(list);
			}
			result = Result.toResult(PageSet.toPageSet(list, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getAllCachePoolInfo", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得所有的物主缓存池信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @param critical 预警临界点(0-100)
	 * @return 所有的物主缓存池信息
	 * @see OwnerCachePool
	 */
	@RequestMapping(value = "/getAllOwnerCachePoolInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody Result<PageSet<OwnerCachePoolInfo>> getAllOwnerCachePoolInfo(
			@RequestParam(required = true, name = "critical") int critical,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<OwnerCachePoolInfo>> result;
		try {
			List<OwnerCachePoolInfo> list = OSUtil.getAllOwnerCachePoolInfo(critical);
			if (cropInterceptor != null) {
				list = cropInterceptor.apply(list);
			}
			result = Result.toResult(PageSet.toPageSet(list, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getAllOwnerCachePoolInfo", e.getMessage(), e);
		}
		return result;
	}
}
