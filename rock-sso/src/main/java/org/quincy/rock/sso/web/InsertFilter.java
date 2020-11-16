package org.quincy.rock.sso.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>根据插入点进行插入或填充的过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 可以利用该过滤器向网页中插入内容。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月31日 下午6:04:39</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class InsertFilter extends AbstractFilter {

	/**
	 * 插入位置枚举
	 */
	public enum Localtion {
	BEFORE, AFTER, REPLACE;
	}

	/**
	 * 切入点。
	 */
	private String pointcut;

	/**
	 * 要插入的内容。
	 */
	private String content;

	/**
	 * 插入位置。
	 */
	private Localtion location = Localtion.REPLACE;

	/** 
	 * doFilter。
	 * @see org.quincy.rock.sso.web.AbstractFilter#doFilter(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(String requestPath, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception {
		//将response掉包，呵呵，狸猫换太子
		HttpServletResponseWrapper myResponse = new HttpServletResponseWrapper(response);
		chain.doFilter(request, myResponse);
		//现在所有的响应内容都放在myResponse中
		StringBuffer sb = myResponse.getResponseText();
		if (sb.length() > 0) {
			String pc = this.getPointcut();
			String c = this.getContent();
			int pos = sb.indexOf(pc);
			if (pos > -1) {
				switch (this.location) {
				case AFTER:
					sb.insert(pos + pc.length(), c);
					break;
				case BEFORE:
					sb.insert(pos, c);
					break;
				default:
					sb.replace(pos, pos + pc.length(), c);
				}
			}

			//将太子换回来,呵呵
			response.getWriter().write(sb.toString());
		}
	}

	/**
	 * <b>获得切入点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 切入点
	 */
	public String getPointcut() {
		return pointcut;
	}

	/**
	 * <b>设置切入点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pointcut 切入点
	 */
	public void setPointcut(String pointcut) {
		this.pointcut = pointcut;
	}

	/**
	 * <b>获得要插入的内容。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 要插入的内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <b>设置要插入的内容。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param content 要插入的内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * <b>获得插入位置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 插入位置
	 */
	public String getLocation() {
		return location.name();
	}

	/**
	 * <b>设置插入位置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param location 插入位置
	 */
	public void setLocation(String location) {
		this.location = Localtion.valueOf(location.toUpperCase());
	}

	//用于掉包的HttpServletResponse包装类
	private class HttpServletResponseWrapper implements HttpServletResponse {
		/**
		 * response。
		 */
		private HttpServletResponse response;

		/**
		 * write。
		 */
		private StringWriter write = new StringWriter();

		/**
		 * <b>构造方法。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param response
		 */
		protected HttpServletResponseWrapper(HttpServletResponse response) {
			this.response = response;
		}

		/**
		 * <b>获得响应文本。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @return 响应文本
		 */
		public StringBuffer getResponseText() {
			return write.getBuffer();
		}

		/**
		 * <b>getOutputStream。</b>
		 * @return
		 * @throws IOException
		 * @see javax.servlet.ServletResponse#getOutputStream()
		 */
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return response.getOutputStream();
		}

		/**
		 * <b>getWriter。</b>
		 * @return
		 * @throws IOException
		 * @see javax.servlet.ServletResponse#getWriter()
		 */
		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(write);
		}

		/**
		 * <b>flushBuffer。</b>
		 * @throws IOException
		 * @see javax.servlet.ServletResponse#flushBuffer()
		 */
		@Override
		public void flushBuffer() throws IOException {
			response.flushBuffer();
		}

		/**
		 * <b>addCookie。</b>
		 * @param cookie
		 * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
		 */
		@Override
		public void addCookie(Cookie cookie) {
			response.addCookie(cookie);
		}

		/**
		 * <b>addDateHeader。</b>
		 * @param name
		 * @param date
		 * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
		 */
		@Override
		public void addDateHeader(String name, long date) {
			response.addDateHeader(name, date);
		}

		/**
		 * <b>addHeader。</b>
		 * @param name
		 * @param value
		 * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
		 */
		@Override
		public void addHeader(String name, String value) {
			response.addHeader(name, value);
		}

		/**
		 * <b>addIntHeader。</b>
		 * @param name
		 * @param value
		 * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
		 */
		@Override
		public void addIntHeader(String name, int value) {
			response.addIntHeader(name, value);
		}

		/**
		 * <b>containsHeader。</b>
		 * @param name
		 * @return
		 * @see javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
		 */
		@Override
		public boolean containsHeader(String name) {
			return response.containsHeader(name);
		}

		/**
		 * <b>encodeRedirectUrl。</b>
		 * @param url
		 * @return
		 * @deprecated
		 * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String)
		 */
		@Override
		public String encodeRedirectUrl(String url) {
			return response.encodeRedirectUrl(url);
		}

		/**
		 * <b>encodeRedirectURL。</b>
		 * @param url
		 * @return
		 * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String)
		 */
		@Override
		public String encodeRedirectURL(String url) {
			return response.encodeRedirectURL(url);
		}

		/**
		 * <b>encodeUrl。</b>
		 * @param url
		 * @return
		 * @deprecated
		 * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
		 */
		@Override
		public String encodeUrl(String url) {
			return response.encodeUrl(url);
		}

		/**
		 * <b>encodeURL。</b>
		 * @param url
		 * @return
		 * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
		 */
		@Override
		public String encodeURL(String url) {
			return response.encodeURL(url);
		}

		/**
		 * <b>getBufferSize。</b>
		 * @return
		 * @see javax.servlet.ServletResponse#getBufferSize()
		 */
		@Override
		public int getBufferSize() {
			return response.getBufferSize();
		}

		/**
		 * <b>getCharacterEncoding。</b>
		 * @return
		 * @see javax.servlet.ServletResponse#getCharacterEncoding()
		 */
		@Override
		public String getCharacterEncoding() {
			return response.getCharacterEncoding();
		}

		/**
		 * <b>getContentType。</b>
		 * @return
		 * @see javax.servlet.ServletResponse#getContentType()
		 */
		@Override
		public String getContentType() {
			return response.getContentType();
		}

		/**
		 * <b>getLocale。</b>
		 * @return
		 * @see javax.servlet.ServletResponse#getLocale()
		 */
		@Override
		public Locale getLocale() {
			return response.getLocale();
		}

		/**
		 * <b>isCommitted。</b>
		 * @return
		 * @see javax.servlet.ServletResponse#isCommitted()
		 */
		@Override
		public boolean isCommitted() {
			return response.isCommitted();
		}

		/**
		 * <b>reset。</b>
		 * @see javax.servlet.ServletResponse#reset()
		 */
		@Override
		public void reset() {
			response.reset();
		}

		/**
		 * <b>resetBuffer。</b>
		 * @see javax.servlet.ServletResponse#resetBuffer()
		 */
		@Override
		public void resetBuffer() {
			response.resetBuffer();
		}

		/**
		 * <b>sendError。</b>
		 * @param sc
		 * @param msg
		 * @throws IOException
		 * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
		 */
		@Override
		public void sendError(int sc, String msg) throws IOException {
			response.sendError(sc, msg);
		}

		/**
		 * <b>sendError。</b>
		 * @param sc
		 * @throws IOException
		 * @see javax.servlet.http.HttpServletResponse#sendError(int)
		 */
		@Override
		public void sendError(int sc) throws IOException {
			response.sendError(sc);
		}

		/**
		 * <b>sendRedirect。</b>
		 * @param location
		 * @throws IOException
		 * @see javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
		 */
		@Override
		public void sendRedirect(String location) throws IOException {
			response.sendRedirect(location);
		}

		/**
		 * <b>setBufferSize。</b>
		 * @param size
		 * @see javax.servlet.ServletResponse#setBufferSize(int)
		 */
		@Override
		public void setBufferSize(int size) {
			response.setBufferSize(size);
		}

		/**
		 * <b>setCharacterEncoding。</b>
		 * @param charset
		 * @see javax.servlet.ServletResponse#setCharacterEncoding(java.lang.String)
		 */
		@Override
		public void setCharacterEncoding(String charset) {
			response.setCharacterEncoding(charset);
		}

		/**
		 * <b>setContentLength。</b>
		 * @param len
		 * @see javax.servlet.ServletResponse#setContentLength(int)
		 */
		@Override
		public void setContentLength(int len) {
			response.setContentLength(len);
		}

		/**
		 * <b>setContentType。</b>
		 * @param type
		 * @see javax.servlet.ServletResponse#setContentType(java.lang.String)
		 */
		@Override
		public void setContentType(String type) {
			response.setContentType(type);
		}

		/**
		 * <b>setDateHeader。</b>
		 * @param name
		 * @param date
		 * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
		 */
		@Override
		public void setDateHeader(String name, long date) {
			response.setDateHeader(name, date);
		}

		/**
		 * <b>setHeader。</b>
		 * @param name
		 * @param value
		 * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
		 */
		@Override
		public void setHeader(String name, String value) {
			response.setHeader(name, value);
		}

		/**
		 * <b>setIntHeader。</b>
		 * @param name
		 * @param value
		 * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
		 */
		@Override
		public void setIntHeader(String name, int value) {
			response.setIntHeader(name, value);
		}

		/**
		 * <b>setLocale。</b>
		 * @param loc
		 * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
		 */
		@Override
		public void setLocale(Locale loc) {
			response.setLocale(loc);
		}

		/**
		 * <b>setStatus。</b>
		 * @param sc
		 * @param sm
		 * @deprecated
		 * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
		 */
		@Override
		public void setStatus(int sc, String sm) {
			response.setStatus(sc, sm);
		}

		/**
		 * <b>setStatus。</b>
		 * @param sc
		 * @see javax.servlet.http.HttpServletResponse#setStatus(int)
		 */
		@Override
		public void setStatus(int sc) {
			response.setStatus(sc);
		}

		/** 
		 * setContentLengthLong。
		 * @see javax.servlet.ServletResponse#setContentLengthLong(long)
		 */
		@Override
		public void setContentLengthLong(long arg0) {
			response.setContentLengthLong(arg0);
		}

		/** 
		 * getHeader。
		 * @see javax.servlet.http.HttpServletResponse#getHeader(java.lang.String)
		 */
		@Override
		public String getHeader(String arg0) {
			return response.getHeader(arg0);
		}

		/** 
		 * getHeaderNames。
		 * @see javax.servlet.http.HttpServletResponse#getHeaderNames()
		 */
		@Override
		public Collection<String> getHeaderNames() {
			return response.getHeaderNames();
		}

		/** 
		 * getHeaders。
		 * @see javax.servlet.http.HttpServletResponse#getHeaders(java.lang.String)
		 */
		@Override
		public Collection<String> getHeaders(String arg0) {
			return response.getHeaders(arg0);
		}

		/** 
		 * getStatus。
		 * @see javax.servlet.http.HttpServletResponse#getStatus()
		 */
		@Override
		public int getStatus() {
			return response.getStatus();
		}
	}
}
