package org.quincy.rock.sso.web;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>登录检查过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月31日 下午11:51:29</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CheckLogonFilter extends AbstractFilter {
	/**
	 * 检查失败跳转页。
	 */
	private String goPage = "/login.html";
	/**
	 * 存储到会话中的当前登录用户变量名。
	 */
	private String logonUserName = "logonUser";

	/** 
	 * doFilter。
	 * @see org.quincy.rock.sso.web.AbstractFilter#doFilter(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(String requestPath, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception {
		//登录检查错误时要转向的页面
		String goPage = this.getGoPage();
		if (goPage.startsWith("/"))
			goPage = request.getContextPath() + goPage;
		//当前的uri路径
		String uri = request.getRequestURI();
		if (!(uri.equals(goPage))) {
			Object obj = request.getSession().getAttribute(this.getLogonUserName());
			if (obj == null) {
				this.log(this.getFilterName() + " goto page:" + goPage);
				response.sendRedirect(goPage);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * <b>获得检查失败跳转页。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 检查失败跳转页
	 */
	public String getGoPage() {
		return goPage;
	}

	/**
	 * <b>设置检查失败跳转页。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param goPage 检查失败跳转页
	 */
	public void setGoPage(String goPage) {
		this.goPage = goPage;
	}

	/**
	 * <b>获得存储到会话中的当前登录用户变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 存储到会话中的当前登录用户变量名
	 */
	public String getLogonUserName() {
		return logonUserName;
	}

	/**
	 * <b>设置存储到会话中的当前登录用户变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param logonUserName 存储到会话中的当前登录用户变量名
	 */
	public void setLogonUserName(String logonUserName) {
		this.logonUserName = logonUserName;
	}
}
