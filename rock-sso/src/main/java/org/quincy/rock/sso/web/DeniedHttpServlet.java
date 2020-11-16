package org.quincy.rock.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>权限禁止Servlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 如果指定了跳转页面则跳转到指定页面，否则返回权限禁止Json字符串。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月15日 下午2:21:56</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DeniedHttpServlet extends ForwardHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -2388690758958306922L;

	/**
	 * 权限禁止代码。
	 */
	private String code = "nogrant";
	/**
	 * 权限禁止消息文本。
	 */
	private String message = "This function is not authorized!";

	/**
	 * <b>获得权限禁止代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 权限禁止代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * <b>设置权限禁止代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 权限禁止代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * <b>获得权限禁止消息文本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 权限禁止消息文本
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <b>设置权限禁止消息文本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 权限禁止消息文本
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/** 
	 * execute。
	 * @see org.quincy.rock.sso.web.AbstractHttpServlet#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (this.hasForward()) {
			super.execute(req, resp);
		} else {
			SSOUtils.writeJson(resp, Result.toResult(code, message));
		}
	}
}
