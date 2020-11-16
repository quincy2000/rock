package org.quincy.rock.sso.web;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.util.StringUtil;

/**
 * <b>字符编码处理过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 该过滤器可以有效地处理中文乱码。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月31日 下午5:31:12</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CharacterEncodingFilter extends AbstractFilter {
	//设置编码Scope
	public enum Scope {
		/**
		 * 只设置请求
		 */
		request,
		/**
		 * 只设置响应
		 */
		response,
		/**
		 * 设置请求和响应
		 */
		both
	}

	/**
	 * 字符集编码。
	 */
	private String encoding;

	/**
	 * 忽略客户端设置。
	 */
	private boolean ignore;

	/**
	 * 编码范围(request,response,both)。
	 */
	private Scope scope = Scope.request;

	/** 
	 * doFilter。
	 * @see org.quincy.rock.sso.web.AbstractFilter#doFilter(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(String requestPath, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception {
		String encoding = isIgnore() ? null : request.getCharacterEncoding();
		if (StringUtil.isBlank(encoding))
			encoding = this.getEncoding();
		//
		switch (scope) {
		case request:
			request.setCharacterEncoding(encoding);
			break;
		case response:
			response.setCharacterEncoding(encoding);
			break;
		default:
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		//
		chain.doFilter(request, response);
	}

	/**
	 * <b>获得字符集编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 字符集编码
	 */
	public String getEncoding() {
		return StringUtil.isBlank(encoding) ? StringUtil.DEFAULT_ENCODING.name() : encoding;
	}

	/**
	 * <b>设置字符集编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param encoding 字符集编码
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * <b>是否忽略客户端设置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否忽略客户端设置
	 */
	public boolean isIgnore() {
		return ignore;
	}

	/**
	 * <b>忽略客户端设置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignore 忽略客户端设置
	 */
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	/**
	 * <b>获得编码范围(request,response,both)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 编码范围(request,response,both)
	 */
	public String getScope() {
		return scope.name();
	}

	/**
	 * <b>设置编码范围(request,response,both)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param scope 编码范围(request,response,both)
	 */
	public void setScope(String scope) {
		this.scope = Scope.valueOf(scope.toLowerCase());
	}
}
