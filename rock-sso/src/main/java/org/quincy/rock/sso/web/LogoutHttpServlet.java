package org.quincy.rock.sso.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSOTicket;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>注销HttpServlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 注销HttpServlet会注销当前会话,并跳转到指定url,如果没有指定跳转页，则跳转到入口页。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月7日 下午3:19:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class LogoutHttpServlet extends ForwardHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 组织机构标识。
	 */
	private String organization;

	/**
	 * <b>设置组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 */
	public void setOrganization(String org) {
		this.organization = org;
	}

	/** 
	 * execute。
	 * @see org.quincy.rock.sso.web.AbstractHttpServlet#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String portalPage = this.invalidate(req, resp);
		//跳转
		if (hasForward()) {
			this.forward(req, resp, "forward");
		} else if (StringUtil.isBlank(portalPage)) {
			resp.sendRedirect(req.getContextPath());
		} else {
			resp.sendRedirect(portalPage);
		}
	}

	private String invalidate(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String org = this.organization;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			SSOTicket ticket = adapter.getTicket();
			if (ticket != null)
				org = ticket.getOrganization();
			Map<String, Object> map = adapter.portalMis();
			boolean discard = Boolean.parseBoolean(req.getParameter("discard"));
			adapter.logout(discard, "User logout");
			return map == null ? null : (String) map.get("homepage");
		} catch (Exception e) {
			return null;
		} finally {
			session.invalidate();
			if (org != null) {
				SSOUtils.clearTicketFromCookie(org, resp);
			}
		}
	}
}
