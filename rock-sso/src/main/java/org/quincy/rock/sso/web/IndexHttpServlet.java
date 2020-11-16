package org.quincy.rock.sso.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>主页HttpServlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 主页HttpServlet是一个跳转HttpServlet，会跳转到指定的url，如果没有指定url，则返回json结果。
 * 不过，如果在请求参数中发现凭证，则该HttpServlet会到客户端转一圈后再回来(使用过渡页保存cookie到客户端)。
 * 开发人员可以自定义过渡页，但过渡页必须再次提交到该IndexHttpServlet以完成跳转。
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
public class IndexHttpServlet extends ForwardHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -2335190288002827062L;

	/**
	 * 凭证变量名。
	 */
	private String tonkenName = SSOUtils.SSO_TONKEN_VAR_NAME;
	/**
	 * 要求返回json消息的变量名。
	 */
	private String jsonName = SSOUtils.SSO_JSON_VAR_NAME;
	/**
	 * 过渡页面。
	 */
	private String interimPage;

	/**
	 * <b>获得凭证变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证变量名
	 */
	public String getTonkenName() {
		return tonkenName;
	}

	/**
	 * <b>设置凭证变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tonkenName 凭证变量名
	 */
	public void setTonkenName(String tonkenName) {
		this.tonkenName = tonkenName;
	}

	/**
	 * <b>获得要求返回json消息的变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 要求返回json消息的变量名
	 */
	public String getJsonName() {
		return jsonName;
	}

	/**
	 * <b>设置要求返回json消息的变量名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jsonName 要求返回json消息的变量名
	 */
	public void setJsonName(String jsonName) {
		this.jsonName = jsonName;
	}

	/**
	 * <b>获得过渡页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 过渡页面
	 */
	public String getInterimPage() {
		return interimPage;
	}

	/**
	 * <b>设置过渡页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param interimPage 过渡页面
	 */
	public void setInterimPage(String interimPage) {
		this.interimPage = interimPage;
	}

	/** 
	 * execute。
	 * @see org.quincy.rock.sso.web.AbstractHttpServlet#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (StringUtil.isBlank(req.getParameter(tonkenName))) {
			//正常跳转
			super.execute(req, resp);
		} else {
			//从sso带着凭证跳转过来的
			boolean reqJson = Boolean.parseBoolean(req.getParameter(jsonName)); //请求返回json结果
			if (reqJson) {
				//返回一个json
				SSOUtils.writeJson(resp, Result.TRUE);
			} else if (StringUtils.isBlank(interimPage)) {
				//显示默认过渡页面
				PrintWriter out = resp.getWriter();
				try {
					out.println("<!DOCTYPE html>\n<html>\n<head>");
					out.println("<meta charset='UTF-8'>");
					if (isNoCache()) {
						out.println("<meta http-equiv='Pragma' content='no-cache'>");
						out.println("<meta http-equiv='Cache-Control' content='no-cache'>");
						out.println("<meta http-equiv='Expires' content='0'>");
					}
					out.println("<title>index</title>");
					out.println("</head>\n<body>");
					//body
					String body = SSOUtils.getMessage("web.IndexHttpServlet.interimPage.body",
							SSOUtils.getRequestPathWithContext(req));
					out.println(body);
					out.println("</body>\n<script type='text/javascript'>");
					//script
					String script = SSOUtils.getMessage("web.IndexHttpServlet.interimPage.script");
					out.println(script);
					out.println("</script>\n</html>");
				} finally {
					IOUtil.closeQuietly(out);
				}
			} else {
				//显示自定义过渡页面				
				req.getRequestDispatcher(interimPage).forward(req, resp);
			}
		}
	}
}
