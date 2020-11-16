package org.quincy.rock.sso.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.bean.BeanUtil;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.function.EachFunction;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.sso.util.SSOUtils;
import org.quincy.rock.sso.web.CharacterEncodingFilter.Scope;
import org.slf4j.Logger;

/**
 * <b>AbstractHttpServlet。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 跳转页面支持参数化表达。
 * 例如:http://{serverName}:{serverPort}{contextPath}
 * <br>
 * 其中{contextPath}是自带前后斜杠的
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月7日 下午1:17:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractHttpServlet extends HttpServlet {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7743593525158855769L;

	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(AbstractHttpServlet.class);

	//转向forward
	private class Forward implements Serializable {
		/**
		 * serialVersionUID。
		 */
		private static final long serialVersionUID = -3309860489505368122L;

		private String name;

		private String path;

		private boolean redirect = false;

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public boolean isRedirect() {
			return redirect;
		}

		public void setRedirect(boolean redirect) {
			this.redirect = redirect;
		}

		public boolean isValid() {
			return !StringUtil.isBlank(path);
		}
	}

	/**
	 * 不要缓存页面。
	 */
	private boolean noCache;

	/**
	 * 缓存的forward列表。
	 */
	private Map<String, Forward> forwards = new Hashtable();

	/**
	 * 字符集编码。
	 */
	private String encoding;
	/**
	 * 编码范围(request,response,both)。
	 */
	private Scope scope = Scope.response;

	/**
	 * <b>是否不要缓存页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否不要缓存页面
	 */
	public final boolean isNoCache() {
		return noCache;
	}

	/**
	 * <b>设置不要缓存页面。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param noCache 不要缓存页面
	 */
	public final void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	/**
	 * <b>获得字符集编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 字符集编码
	 */
	public final String getEncoding() {
		return encoding;
	}

	/**
	 * <b>设置字符集编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param encoding 字符集编码
	 */
	public final void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * <b>获得编码范围(request,response,both)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 编码范围(request,response,both)
	 */
	public final String getScope() {
		return scope.name();
	}

	/**
	 * <b>设置编码范围(request,response,both)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param scope 编码范围(request,response,both)
	 */
	public final void setScope(String scope) {
		this.scope = Scope.valueOf(scope.toLowerCase());
	}

	/**
	 * <b>返回初始化参数名称对应的参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 先读取Servlet的初始化参数，如果为空则继续读取ServletContext初始化参数。
	 * @param name 初始化参数名称
	 * @param defaultValue 默认值
	 * @return 参数值
	 */
	protected final String getProperty(String name, String defaultValue) {
		String value = getProperty(name);
		if (value == null)
			value = defaultValue;
		return value;
	}

	/**
	 * <b>返回初始化参数名称对应的参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 先读取Servlet的初始化参数，如果为空则继续读取ServletContext初始化参数。
	 * @param name 初始化参数名称
	 * @return 参数值,如果没有则返回null
	 */
	protected final String getProperty(String name) {
		String value = this.getInitParameter(name);
		if (value == null)
			value = this.getServletContext().getInitParameter(name);
		return value;
	}

	/**
	 * <b>根据forward配置名进行转向操作。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name forward配置名称
	 * @param paramMap 请求参数
	 */
	protected final void forward(HttpServletRequest request, HttpServletResponse response, String name,
			Map<String, String> paramMap) throws IOException, ServletException {
		Forward forward = this.findForward(name);
		if (forward != null && forward.isValid()) {
			StringBuilder url = new StringBuilder();
			url.append(normalizePath(forward, request));
			for (String key : paramMap.keySet()) {
				SSOUtils.appendParameter(url, key, paramMap.get(key), true);
			}
			if (forward.redirect)
				response.sendRedirect(url.toString());
			else
				request.getRequestDispatcher(url.toString()).forward(request, response);
		} else {
			throw new NotFoundException("Forward:" + name);
		}
	}

	/**
	 * <b>根据forward配置名进行转向操作。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name forward配置名称
	 */
	protected final void forward(HttpServletRequest request, HttpServletResponse response, String name)
			throws IOException, ServletException {
		Forward forward = this.findForward(name);
		if (forward != null && forward.isValid()) {
			if (forward.redirect)
				response.sendRedirect(normalizePath(forward, request));
			else
				request.getRequestDispatcher(normalizePath(forward, request)).forward(request, response);
		} else {
			throw new NotFoundException("Forward:" + name);
		}
	}

	private String normalizePath(Forward forward, HttpServletRequest req) {
		String path = forward.getPath();
		if (path.indexOf('{') != -1) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("serverName", req.getServerName());
			paramMap.put("serverPort", req.getServerPort());
			paramMap.put("contextPath", SSOUtils.getContextPath(req.getServletContext()));
			//
			StringBuilder sb = new StringBuilder();
			StringUtil.split(path, 0, path.length(), new char[] { '{', '}' },
					new EachFunction<CharSequence, Boolean>() {
						@Override
						public Boolean each(int index, CharSequence ele) {
							if (index % 2 == 0) {
								//是
								sb.append(ele);
							} else {
								sb.append(paramMap.get(ele.toString()));
							}
							return false;
						}
					});
			path = sb.toString();
		}
		return path;
	}

	private Forward findForward(String name) {
		Forward forward = forwards.get(name);
		if (forward == null) {
			String path = this.getProperty(name);
			if (!StringUtil.isBlank(path)) {
				forward = new Forward();
				forward.setName(name);
				int i = path.indexOf(",");
				if (i >= 0) {
					String head = path.substring(0, i).trim();
					path = path.substring(i + 1).trim();
					if (head.equalsIgnoreCase("redirect")) {
						forward.setRedirect(true);
					}
				}
				forward.setPath(path);
				forwards.put(name, forward);
			}
		}
		return forward;
	}

	private Forward findForward(Throwable t) {
		Class cls = t.getClass();
		String name = cls.getSimpleName();
		while (getProperty(name) == null) {
			t = t.getCause();
			if (t == null) {
				name = null;
				break;
			} else {
				name = t.getClass().getSimpleName();
			}
		}
		if (name == null) {
			do {
				cls = cls.getSuperclass();
				if (cls == null) {
					name = null;
					break;
				} else {
					name = cls.getSimpleName();
				}
			} while (getProperty(name) == null);
		}
		return name == null ? null : findForward(name);
	}

	/**
	 * <b>doPost。</b>
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	/**
	 * <b>doGet。</b>
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if (isNoCache()) {
				resp.setHeader("Cache-Control", "no-cache");
				resp.setHeader("Pragma", "no-cache");
				resp.setDateHeader("Expires", 0);
			}
			if (!StringUtil.isBlank(encoding)) {
				switch (scope) {
				case request:
					req.setCharacterEncoding(encoding);
					break;
				case response:
					resp.setCharacterEncoding(encoding);
					break;
				default:
					req.setCharacterEncoding(encoding);
					resp.setCharacterEncoding(encoding);
				}
			}
			this.execute(req, resp);
		} catch (Throwable e) {
			Forward forward = this.findForward(e);
			if (forward != null) {
				processException(req, resp, e, forward.getPath(), forward.isRedirect());
			} else {
				this.log(e.getMessage(), e);
				if (e instanceof IOException)
					throw (IOException) e;
				else if (e instanceof ServletException)
					throw (ServletException) e;
				else
					throw new ServletException(e.getMessage(), e);
			}
		}
	}

	/**
	 * <b>处理异常。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 子类可重写该方法实现自己的错误处理机制。
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param e Throwable
	 * @param forward 跳转页面
	 * @param redirect 是否是重定向
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void processException(HttpServletRequest request, HttpServletResponse response, Throwable e,
			String forward, boolean redirect) throws IOException, ServletException {
		if (redirect) {
			if (forward.startsWith("/"))
				forward = request.getContextPath() + forward;
			request.getSession().setAttribute(SSOUtils.WEB_EXCEPTION_KEY, e);
			response.sendRedirect(forward);
		} else {
			request.setAttribute(SSOUtils.WEB_EXCEPTION_KEY, e);
			request.getRequestDispatcher(forward).forward(request, response);
		}
	}

	/**
	 * <b>execute。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	protected abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;

	/**
	 * <b>getApplication。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return ServletContext
	 */
	public final ServletContext getApplication() {
		return this.getServletContext();
	}

	/** 
	 * log。
	 * @see javax.servlet.GenericServlet#log(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(String message, Throwable t) {
		logger.error(message, t);
		super.log(message, t);
	}

	/** 
	 * log。
	 * @see javax.servlet.GenericServlet#log(java.lang.String)
	 */
	@Override
	public void log(String msg) {
		logger.info(msg);
		super.log(msg);
	}

	/**
	 * <b>destroy。</b>
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public final void destroy() {
		this.forwards.clear();
		this.doDestroy();
	}

	/**
	 * <b>doDestroy。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	protected void doDestroy() {
	}

	/**
	 * <b>init。</b>
	 * @see javax.servlet.GenericServlet#init()
	 */
	public final void init() throws ServletException {
		ServletContext application = this.getServletContext();

		//存放已经设置过的属性
		Set<String> seted = new HashSet<String>();
		Map map = BeanUtil.toMap(this);

		//处理初始化参数
		for (Enumeration<String> list = this.getInitParameterNames(); list.hasMoreElements();) {
			String name = list.nextElement();
			String value = this.getInitParameter(name);
			try {
				if (map.containsKey(name))
					map.put(name, value);
				seted.add(name);
			} catch (Exception e) {
				this.log(e.getMessage(), e);
			}
		}

		//处理应用程序上下文参数
		for (Enumeration<String> list = application.getInitParameterNames(); list.hasMoreElements();) {
			String name = list.nextElement();
			if (seted.contains(name) || !map.containsKey(name))
				continue;
			String value = application.getInitParameter(name);
			try {
				map.put(name, value);
			} catch (Exception e) {
				this.log(e.getMessage(), e);
			}
		}
		try {
			this.doInit();
		} catch (Exception e) {
			if (e instanceof ServletException)
				throw (ServletException) e;
			else
				throw new ServletException(e.getMessage(), e);
		}
	}

	/**
	 * <b>doInit。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @throws Exception
	 */
	protected void doInit() throws Exception {
	}
}