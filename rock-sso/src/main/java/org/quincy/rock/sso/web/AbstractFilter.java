package org.quincy.rock.sso.web;

import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.quincy.rock.core.bean.BeanUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.sso.util.SSOUtils;
import org.slf4j.Logger;

/**
 * <b>抽象过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月31日 下午4:37:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractFilter implements Filter {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(AbstractFilter.class);

	/**
	 * 过滤器配置。
	 */
	private FilterConfig config;

	/**
	 * application。
	 */
	private ServletContext application;

	/**
	 * 过滤器名称。
	 */
	private String filterName;

	/**
	 * 包含的文件列表。
	 */
	private FilenameFilter includeFileFilter;

	/**
	 * 排除的文件列表。
	 */
	private FilenameFilter excludeFileFilter;

	/**
	 * <b>destroy。</b>
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		this.config = null;
		this.application = null;
	}

	/** 
	 * init。
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public final void init(FilterConfig config) throws ServletException {
		this.config = config;
		application = config.getServletContext();
		filterName = config.getFilterName();

		//存放已经设置过的属性
		Set<String> seted = new HashSet<String>();
		Map<String, Object> map = BeanUtil.toMap(this);

		//处理过滤器初始化参数
		for (Enumeration<String> list = config.getInitParameterNames(); list.hasMoreElements();) {
			String name = list.nextElement();
			String value = config.getInitParameter(name);
			try {
				if (map.containsKey(name))
					map.put(name, value);
				seted.add(name);
			} catch (Exception e) {
				this.log(e);
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
				this.log(e);
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

	/**
	 * <b>getApplication。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return ServletContext
	 */
	public final ServletContext getApplication() {
		return application;
	}

	/**
	 * <b>写错误日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param e 错误日志异常对象
	 */
	protected void log(Throwable e) {
		logger.error(e.getMessage(), e);
		this.application.log(e.getMessage(), e);
	}

	/**
	 * <b>写日志消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * info级别信息。
	 * @param s 日志消息
	 */
	protected void log(String s) {
		logger.info(s);
		this.application.log(s);
	}

	/**
	 * <b>获得过滤器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 过滤器名称
	 */
	public final String getFilterName() {
		return filterName;
	}

	/**
	 * <b>doFilter。</b>
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			//当前的uri路径
			String uri = SSOUtils.getRequestPath(req);
			if (isInclude(uri) && !isExclude(uri)) {
				try {
					this.doFilter(uri, req, resp, chain);
				} catch (Exception e) {
					if (e instanceof ServletException)
						throw (ServletException) e;
					else
						throw new ServletException(e.getMessage(), e);
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * <b>拦截过滤。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param requestPath 请求路径
	 * @param request 请求HttpServlet
	 * @param response 响应HttpServlet
	 * @param chain 拦截过滤链
	 * @throws Exception
	 */
	public abstract void doFilter(String requestPath, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;

	/**
	 * <b>设置包含的文件列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只有符合该列表条件的文件过滤器才起作用。可以使用通配符。
	 * @param includeFileNames 包含的文件列表
	 */
	public void setIncludeFileNames(String includeFileNames) {
		if (StringUtil.isBlank(includeFileNames))
			this.includeFileFilter = null;
		else {
			List<String> list = new ArrayList<>();
			StringUtil.split(includeFileNames, StringUtil.CHAR_COMMA, (i, cs) -> list.add(cs.toString().trim()));
			this.includeFileFilter = new WildcardFileFilter(list, IOCase.INSENSITIVE);
		}
	}

	/**
	 * <b>设置排除的文件列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 在该列表中的文件会绕过该过滤器的检查。可以使用通配符。
	 * @param excludeFileNames 排除的文件列表
	 */
	public void setExcludeFileNames(String excludeFileNames) {
		if (StringUtil.isBlank(excludeFileNames))
			this.excludeFileFilter = null;
		else {
			List<String> list = new ArrayList<>();
			StringUtil.split(excludeFileNames, StringUtil.CHAR_COMMA, (i, cs) -> list.add(cs.toString().trim()));
			this.excludeFileFilter = new WildcardFileFilter(list, IOCase.INSENSITIVE);
		}
	}

	/**
	 * <b>判断uri是否是要排除的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 符合条件的uri将跳过本次过滤。
	 * @param uri 要判断的uri
	 * @return uri是否是要排除的
	 */
	protected boolean isExclude(String uri) {
		return excludeFileFilter == null ? false : excludeFileFilter.accept(null, uri);
	}

	/**
	 * <b>判断是否要过滤该uri。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param uri 要判断的uri
	 * @return 是否要过滤该uri
	 */
	protected boolean isInclude(String uri) {
		return includeFileFilter == null ? true : includeFileFilter.accept(null, uri);
	}

	/**
	 * <b>获得过滤器配置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 过滤器配置
	 */
	public final FilterConfig getConfig() {
		return config;
	}

	/**
	 * <b>获得属性值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 属性名称
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public String getProperty(String name, String defaultValue) {
		String value = this.getConfig().getInitParameter(name);
		if (value == null)
			value = this.getApplication().getInitParameter(name);
		if (value == null)
			value = defaultValue;
		return value;
	}
}
