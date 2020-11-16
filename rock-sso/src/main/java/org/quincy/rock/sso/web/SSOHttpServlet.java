package org.quincy.rock.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>SSOHttpServlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 实现和SSOController一样的功能，在非spring环境下使用。
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
public class SSOHttpServlet extends DispatchHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 8708886077554960461L;

	/**
	 * ssoController。
	 */
	private final SSOController ssoController = new SSOController();

	/**
	 * <b>是否缓存查询结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否缓存查询结果
	 */
	public boolean isCache() {
		return ssoController.isCache();
	}

	/**
	 * <b>设置是否缓存查询结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cache 是否缓存查询结果
	 */
	public void setCache(boolean cache) {
		ssoController.setCache(cache);
	}

	/** 
	 * getMethodName。
	 * @see org.quincy.rock.sso.web.DispatchHttpServlet#getMethodName(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getMethodName(HttpServletRequest req) {
		return SSOUtils.getPathMethodName(req);
	}

	public void isAdmin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.isAdmin(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void isSuperAdmin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.isSuperAdmin(req.getSession());
		SSOUtils.writeJson(resp, result);
	}
	
	public void isSsoAdmin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.isSsoAdmin(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void userType(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.userType(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void user(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.user(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void organization(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.organization(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void misid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.misid(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void misName(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.misName(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void homepage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.homepage(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void tonken(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.tonken(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void portalMis(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.portalMis(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void accessibleMis(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.accessibleMis(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void onlineUserCount(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.onlineUserCount(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void onlineUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = ssoController.onlineUsers(page, pageSize, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void writeOplog(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String opCode = req.getParameter("opCode");
		String opName = req.getParameter("opName");
		String opDescr = req.getParameter("opDescr");
		Result<?> result = ssoController.writeOplog(opCode, opName, opDescr, req);
		SSOUtils.writeJson(resp, result);
	}

	public void oplogByCode(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String opCode = req.getParameter("opCode");
		String beginDate = req.getParameter("beginDate");
		String endDate = req.getParameter("endDate");
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = ssoController.oplogByCode(opCode, beginDate, endDate, page, pageSize, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void oplogByOwner(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String owner = req.getParameter("owner");
		String beginDate = req.getParameter("beginDate");
		String endDate = req.getParameter("endDate");
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = ssoController.oplogByOwner(owner, beginDate, endDate, page, pageSize, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void sequences(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String key = req.getParameter("name");
		String value = req.getParameter("count");
		Result<?> result = ssoController.sequences(key, Integer.parseInt(value), req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void setParam(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String key = req.getParameter("key");
		String value = req.getParameter("value");
		Result<?> result = ssoController.setParam(key, value, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void getParam(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String key = req.getParameter("key");
		Result<?> result = ssoController.getParam(key, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void allParam(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.allParam(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void routeways(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String key = req.getParameter("key");
		String value = req.getParameter("isAll");
		Result<?> result = ssoController.getRouteways(key, value == null ? true : Boolean.parseBoolean(value),
				req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findAllDepts(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String tree = req.getParameter("tree");
		String org = req.getParameter("org");
		Result<?> result = ssoController.findAllDepts(org, Boolean.parseBoolean(tree), req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findSubDepts(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code = req.getParameter("code");
		String org = req.getParameter("org");
		Result<?> result = ssoController.findSubDepts(org, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findDept(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code = req.getParameter("code");
		String org = req.getParameter("org");
		Result<?> result = ssoController.findDept(org, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findAllUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code = req.getParameter("code");
		String org = req.getParameter("org");
		Result<?> result = ssoController.findAllUsers(org, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code = req.getParameter("code");
		String org = req.getParameter("org");
		Result<?> result = ssoController.findUser(org, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void changePassword(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code = req.getParameter("code");
		String org = req.getParameter("org");
		String oldPwd = req.getParameter("oldPwd");
		String newPwd = req.getParameter("newPwd");
		Result<?> result = ssoController.changePassword(org, code, oldPwd, newPwd, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void timestamp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Result<?> result = ssoController.timestamp(req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String discard = req.getParameter("discard");
		Result<?> result = ssoController.logout(Boolean.parseBoolean(discard), req, resp);
		SSOUtils.writeJson(resp, result);
	}

	public void supportDataRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		Result<?> result = ssoController.supportDataRole(misid, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void supportLeaf(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		Result<?> result = ssoController.supportLeaf(misid, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findAllLimbs(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		String tree = req.getParameter("tree");
		Result<?> result = ssoController.findAllLimbs(misid, Boolean.parseBoolean(tree), req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findSubLimbs(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		String code = req.getParameter("code");
		Result<?> result = ssoController.findSubLimbs(misid, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findLimb(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		String code = req.getParameter("code");
		Result<?> result = ssoController.findLimb(misid, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findAllLeafs(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		String code = req.getParameter("code");
		int page = Integer.valueOf(req.getParameter("page"));
		int pageSize = Integer.valueOf(req.getParameter("pageSize"));
		Result<?> result = ssoController.findAllLeafs(misid, code, page, pageSize, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

	public void findLeaf(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String misid = req.getParameter("misid");
		String code = req.getParameter("code");
		Result<?> result = ssoController.findLeaf(misid, code, req.getSession());
		SSOUtils.writeJson(resp, result);
	}

}
