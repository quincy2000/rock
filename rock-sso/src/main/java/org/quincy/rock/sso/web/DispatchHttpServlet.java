package org.quincy.rock.sso.web;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.util.StringUtil;
import org.springframework.util.ReflectionUtils;

/**
 * <b>分派HttpServlet。</b>
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
public abstract class DispatchHttpServlet extends AbstractHttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <b>缺省执行方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param methodName 未发现的方法名
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws Exception
	 */
	protected void doDefault(String methodName, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.sendError(404);
	}

	/** 
	 * execute。
	 * @see org.quincy.rock.sso.web.AbstractHttpServlet#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String methodName = this.getMethodName(req);
		Method method = getMethod(methodName);
		if (method != null) {
			method.invoke(this, req, resp);
		} else {
			this.doDefault(methodName, req, resp);
		}
	}

	/**
	 * <b>获得要执行的方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param methodName 方法名
	 * @return Method，如果没有发现则返回null
	 */
	private Method getMethod(String methodName) {
		if (StringUtil.isBlank(methodName))
			return null;
		else
			return ReflectionUtils.findMethod(this.getClass(), methodName, HttpServletRequest.class,
					HttpServletResponse.class);
	}

	/**
	 * <b>获得要执行的方法名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param req HttpServletRequest
	 * @return 方法名
	 */
	protected abstract String getMethodName(HttpServletRequest req);
}
