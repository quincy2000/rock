package org.quincy.rock.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>ForwardHttpServlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
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
public class ForwardHttpServlet extends AbstractHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 7673973632828079555L;

	/**
	 * 跳转页面。
	 */
	private String forward;

	/**
	 * <b>获得跳转页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 跳转页面
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * <b>设置跳转页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param forward 跳转页面
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * <b>是否有跳转页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有跳转页面
	 */
	protected boolean hasForward() {
		return getForward() != null;
	}

	/** 
	 * execute。
	 * @see org.quincy.rock.sso.web.AbstractHttpServlet#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		this.forward(req, resp, "forward");
	}
}
