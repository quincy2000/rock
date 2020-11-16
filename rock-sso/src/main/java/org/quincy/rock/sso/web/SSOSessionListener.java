package org.quincy.rock.sso.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>SSO会话监听器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月3日 下午5:02:10</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOSessionListener implements HttpSessionListener {

	/**
	 * <b>sessionCreated。</b>
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		String contextPath = SSOUtils.getContextPath(session.getServletContext());
		session.setAttribute("contextPath", contextPath);
	}

	/**
	 * <b>sessionDestroyed。</b>
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext ctx = session.getServletContext();
		String ssoAdapterName = ctx.getInitParameter("ssoAdapterName");
		if (ssoAdapterName == null)
			ssoAdapterName = SSOUtils.SSO_ADAPTER_VAR_NAME;
		//
		SSOAdapter adapter = (SSOAdapter) session.getAttribute(ssoAdapterName);
		if (adapter != null) {
			adapter.close();
		}
	}
}
