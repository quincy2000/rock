package org.quincy.rock.sso.web;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>SSO授权检查过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月12日 下午4:02:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractCheckGrantFilter extends AbstractFilter {
	/**
	 * 权限拒绝错误页面。
	 */
	private String errorPage = SSOUtils.SSO_DENIED_PAGE;
	/**
	 * 要求返回json错误码的变量名。
	 */
	private String jsonName = SSOUtils.SSO_JSON_VAR_NAME;
	/**
	 * 未授权json错误码。
	 */
	private String nograntCode = "nogrant";

	/**
	 * <b>获得错误页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 错误页面
	 */
	public String getErrorPage() {
		return errorPage;
	}

	/**
	 * <b>设置错误页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param errorPage 错误页面
	 */
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	/**
	 * <b>获得要求返回json错误码的变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 要求返回json错误码的变量名
	 */
	public String getJsonName() {
		return jsonName;
	}

	/**
	 * <b>设置要求返回json错误码的变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jsonName 要求返回json错误码的变量名
	 */
	public void setJsonName(String jsonName) {
		this.jsonName = jsonName;
	}

	/**
	 * <b>获得未授权json错误码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 未授权json错误码
	 */
	public String getNograntCode() {
		return nograntCode;
	}

	/**
	 * <b>设置未授权json错误码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param nograntCode 未授权json错误码
	 */
	public void setNograntCode(String nograntCode) {
		this.nograntCode = nograntCode;
	}

	/** 
	 * doFilter。
	 * @see org.quincy.rock.sso.web.AbstractFilter#doFilter(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public final void doFilter(String requestPath, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception {
		String errorPage = this.getErrorPage();
		if (errorPage.startsWith("/"))
			errorPage = request.getContextPath() + errorPage;
		//检查授权
		boolean passed = false;
		if (errorPage.equals(request.getRequestURI()))
			passed = true; //当前是错误页，不需要拦截
		else {
			passed = checkGrant(requestPath, request, response);
		}
		//
		if (passed)
			chain.doFilter(request, response);
		else {
			boolean reqJson = Boolean.parseBoolean(request.getParameter(jsonName)); //请求返回json结果
			if (reqJson) {
				//返回一个json
				SSOUtils.writeJson(response, Result.toResult(nograntCode, errorPage));
			} else {
				response.sendRedirect(errorPage);
			}
		}
	}

	/**
	 * <b>检查授权。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param requestPath 请求路径
	 * @param request 请求HttpServlet
	 * @param response 响应HttpServlet
	 * @return 是否是授权的操作
	 */
	protected abstract boolean checkGrant(String requestPath, HttpServletRequest request, HttpServletResponse response);
}
