package org.quincy.rock.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>OSHttpServlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 实现和OSController一样的功能，在非spring环境下使用。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月10日 上午11:04:18</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class OSHttpServlet extends DispatchHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1616123220069584668L;

	/**
	 * osController。
	 */
	private final OSController osController = new OSController();

	/**
	 * <b>设置列表信息裁剪拦截器类名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 列表信息裁剪拦截器必须实现java.util.function.Function<List, List>接口。
	 * @param clazzNazme 列表信息裁剪拦截器类名
	 */
	public void setCropInterceptor(String clazzNazme) {
		osController.setCropInterceptor(CoreUtil.constructor(clazzNazme));
	}

	/** 
	 * getMethodName。
	 * @see org.quincy.rock.sso.web.DispatchHttpServlet#getMethodName(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getMethodName(HttpServletRequest req) {
		return SSOUtils.getPathMethodName(req);
	}

	public void getServerInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = osController.getServerInfo();
		SSOUtils.writeJson(resp, result);
	}

	public void getCpuInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = osController.getCpuInfo();
		SSOUtils.writeJson(resp, result);
	}

	public void getMemoryInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = osController.getMemoryInfo();
		SSOUtils.writeJson(resp, result);
	}

	public void getNetInterfaces(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = osController.getNetInterfaces();
		SSOUtils.writeJson(resp, result);
	}

	public void getDisks(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = osController.getDisks();
		SSOUtils.writeJson(resp, result);
	}

	public void getDiskPartitions(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = osController.getDiskPartitions();
		SSOUtils.writeJson(resp, result);
	}

	public void getServiceInfos(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = osController.getServiceInfos(page, pageSize);
		SSOUtils.writeJson(resp, result);
	}

	public void getProcessInfos(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = osController.getProcessInfos(page, pageSize);
		SSOUtils.writeJson(resp, result);
	}

	public void getAllProcessServiceInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = osController.getAllProcessServiceInfo(page, pageSize);
		SSOUtils.writeJson(resp, result);
	}

	public void getAllCachePoolInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = osController.getAllCachePoolInfo(page, pageSize);
		SSOUtils.writeJson(resp, result);
	}

	public void getAllOwnerCachePoolInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		int critical = Integer.valueOf(req.getParameter("critical"));
		Result<?> result = osController.getAllOwnerCachePoolInfo(critical, page, pageSize);
		SSOUtils.writeJson(resp, result);
	}

}
