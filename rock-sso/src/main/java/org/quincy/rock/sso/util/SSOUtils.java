package org.quincy.rock.sso.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.quincy.rock.core.function.Consumer;
import org.quincy.rock.core.function.EachFunction;
import org.quincy.rock.core.util.ClosureUtil;
import org.quincy.rock.core.util.ClosureUtil.MultiObjectClosure;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.JsonUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.GrantUser;
import org.quincy.rock.sso.OpInfo;
import org.quincy.rock.sso.OpLog;
import org.quincy.rock.sso.SSOAction;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSOException;
import org.quincy.rock.sso.SSORouteway;
import org.quincy.rock.sso.SSOTicket;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * <b>SSOUitls。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月5日 下午1:51:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SSOUtils {
	/**
	 * 消息资源访问器。
	 */
	public static final MessageSourceAccessor MSA = RockUtil.getMessageSourceAccessor("org.quincy.rock.sso.resources");

	/**
	 * SSO_TICKET_COOKIE_PREFIX。
	 */
	public static final String SSO_TICKET_COOKIE_PREFIX = "st_";

	/**
	 * 默认的SSO适配器变量名称。
	 */
	public final static String SSO_ADAPTER_VAR_NAME = "_ssoAdapter";

	/**
	 * 默认的SSO凭证变量名。
	 */
	public final static String SSO_TONKEN_VAR_NAME = "_tonken";

	/**
	 * 要求返回json的变量名。
	 */
	public final static String SSO_JSON_VAR_NAME = "_json";

	/**
	 * 默认的SSO登录URL。
	 */
	public final static String SSO_LOGON_URL = "/logon.do";

	/**
	 * 默认的权限禁止页面。
	 */
	public final static String SSO_DENIED_PAGE = "/denied.html";

	/**
	 * WEB_EXCEPTION_KEY。
	 */
	public static final String WEB_EXCEPTION_KEY = "webException";
	/**
	 * WEB_ERROR_KEY。
	 */
	public static final String WEB_ERROR_KEY = "webError";
	/**
	 * WEB_SSO_CACHE_PREFIX。
	 */
	public static final String WEB_SSO_CACHE_PREFIX = "webSSO_";

	/**
	 * currentRequest。
	 */
	private static final ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();

	static void setCurrentRequest(HttpServletRequest request) {
		if (request == null)
			currentRequest.remove();
		else
			currentRequest.set(request);
	}

	/**
	 * <b>获得消息资源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 消息资源key
	 * @param args 零个或多个参数
	 * @return 消息资源字符串
	 */
	public static String getMessage(String key, Object... args) {
		return MSA.getMessage(key, args);
	}

	/**
	 * <b>获得当前请求。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前请求
	 */
	public static HttpServletRequest getRequest() {
		return currentRequest.get();
	}

	/**
	 * <b>获得当前会话。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前会话
	 */
	public static HttpSession getSession() {
		HttpServletRequest req = getRequest();
		return req == null ? null : req.getSession();
	}

	/**
	 * <b>返回当前Application。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前Application
	 */
	public static ServletContext getApplication() {
		HttpSession session = getSession();
		return session == null ? null : session.getServletContext();
	}

	/**
	 * <b>获得上下文路径。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 确保上下文路径以斜杠结尾。
	 * @param application ServletContext
	 * @return 上下文路径
	 */
	public static String getContextPath(ServletContext application) {
		String ctx = application.getContextPath();
		if (ctx.length() > 1)
			ctx += StringUtil.CHAR_SLASH;
		return ctx;
	}

	/**
	 * <b>获得请求路径。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @return 请求路径
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		return (pathInfo == null) ? request.getServletPath() : (request.getServletPath() + pathInfo);
	}

	/**
	 * <b>获得带上下文的请求路径。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @return 带上下文的请求路径
	 */
	public static String getRequestPathWithContext(HttpServletRequest request) {
		String path = request.getContextPath() + request.getServletPath();
		String pathInfo = request.getPathInfo();
		return (pathInfo == null) ? path : (path + pathInfo);
	}

	/**
	 * <b>获得完整的请求URL。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 包含查询串。
	 * @param request HttpServletRequest
	 * @return 完整的请求URL
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURL());
		String q = request.getQueryString();
		if (StringUtils.isNoneBlank(q)) {
			sb.append('?');
			sb.append(q);
		}
		return sb.toString();
	}

	/**
	 * <b>获得应用程序磁盘路径。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 应用程序磁盘路径
	 */
	public static File getWebAppPath(ServletContext ctx) {
		return new File(ctx.getRealPath("/"));
	}

	/**
	 * <b>根据路径信息解析方法名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @return 方法名
	 */
	public static String getPathMethodName(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (path == null || path.length() < 2)
			return null;
		else {
			int beginIndex = 1;
			int endIndex = path.lastIndexOf('.');
			if (endIndex == -1) {
				endIndex = path.length();
				if (path.charAt(endIndex - 1) == StringUtil.CHAR_SLASH)
					endIndex--;
			}
			path = path.substring(beginIndex, endIndex);
			return path;
		}
	}

	/**
	 * <b>分析请求参数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法不能处理上传文件。
	 * @param request 请求对象
	 * @return 参数集合
	 */
	public static Map<String, Object> parseRequest(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String, String[]>> set = request.getParameterMap().entrySet();
		for (Entry<String, String[]> entry : set) {
			String name = entry.getKey();
			String[] value = entry.getValue();
			if (value.length > 1)
				map.put(name, value);
			else
				map.put(name, value[0]);
		}
		return map;
	}

	/**
	 * <b>解析请求查询字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param param 要解析的请求查询字符串
	 * @param encoding 字符集编码
	 * @return 解析后的map
	 */
	public static Map<String, Object> parseQueryString(String param, String encoding) {
		Map<String, List<String>> listMap = new HashMap<>();
		if (!StringUtil.isBlank(param)) {
			StringUtil.split(param, 0, param.length(), new char[] { '&', '=' },
					new EachFunction<CharSequence, Boolean>() {
						private String name;

						@Override
						public Boolean each(int index, CharSequence ele) {
							if (index % 2 == 0) {
								name = ele.toString();
							} else {
								String value;
								try {
									value = URLDecoder.decode(ele.toString(), encoding);
								} catch (Exception e) {
									value = ele.toString();
								}
								List<String> list = listMap.get(name);
								if (list == null) {
									list = new ArrayList<>();
									listMap.put(name, list);
								}
								list.add(value);
								name = null;
							}
							return false;
						}
					});
		}
		Map<String, Object> map = new HashMap<>();
		for (String key : listMap.keySet()) {
			List<String> list = listMap.get(key);
			int size = list.size();
			if (size == 1)
				map.put(key, list.get(0));
			else
				map.put(key, list.toArray(new String[size]));
		}
		return map;
	}

	/**
	 * <b>根据map拼QueryString。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param map 存放参数的map
	 * @param encoding 字符集编码
	 * @return 请求查询字符串
	 */
	public static String joinQueryString(Map<String, Object> map, String encoding) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			String[] ss;
			if (CoreUtil.isArray(obj)) //数组
			{
				int size = Array.getLength(obj);
				ss = new String[size];
				for (int i = 0; i < size; i++) {
					ss[i] = String.valueOf(Array.get(obj, i));
				}
			} else if (obj instanceof Collection) {
				Collection<?> objs = (Collection<?>) obj;
				ss = new String[objs.size()];
				int i = 0;
				for (Object o : objs) {
					ss[i] = String.valueOf(o);
					i++;
				}
			} else {
				ss = new String[] { String.valueOf(obj) };
			}
			//将参数拼串			
			for (int i = 0, l = ss.length; i < l; i++) {
				if (sb.length() > 0)
					sb.append("&");
				sb.append(key);
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(ss[i], encoding));
				} catch (UnsupportedEncodingException e) {
					sb.append(ss[i]);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * <b>处理请求参数Map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 将带点的name的处理层嵌套map(如：peson.name)。
	 * @param map 请求参数Map
	 * @return 处理后的请求参数Map
	 */
	public static Map<String, Object> processParameterMap(Map<String, Object> map) {
		Map<String, Object> myMap = new HashMap<>();
		for (String name : map.keySet()) {
			int pos = name.indexOf(StringUtil.CHAR_DOT);
			if (pos == -1) {
				myMap.put(name, map.get(name));
			} else {
				String key1 = name.substring(0, pos);
				String key2 = name.substring(pos + 1);
				Map<String, Object> subMap = (Map) myMap.get(key1);
				if (subMap == null) {
					subMap = new HashMap<>();
					myMap.put(key1, subMap);
				}
				subMap.put(key2, map.get(name));
			}
		}
		for (String name : myMap.keySet()) {
			Object value = myMap.get(name);
			if (value instanceof Map) {
				myMap.put(name, processParameterMap((Map) value));
			}
		}
		return myMap;
	}

	/**
	 * <b>获得SSOAdapter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 单点登录适配器
	 */
	public static SSOAdapter getSSOAdapter() {
		return getSSOAdapter(getSession());
	}

	/**
	 * <b>写入json响应结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param resp ServletResponse
	 * @param result 响应结果对象
	 * @throws IOException
	 */
	public static void writeJson(ServletResponse resp, Result<?> result) throws IOException {
		write(resp, JsonUtil.toJson(result));
	}

	/**
	 * <b>写入响应字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param resp ServletResponse
	 * @param result 响应结果字符串
	 * @throws IOException
	 */
	public static void write(ServletResponse resp, String result) throws IOException {
		Writer out = resp.getWriter();
		try {
			out.write(result);
		} finally {
			IOUtil.closeQuietly(out);
		}
	}

	/**
	 * <b>获得SSOAdapter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只有合法登录用户才能获得SSOAdapter。
	 * @param session HttpSession
	 * @return 单点登录适配器
	 */
	public static SSOAdapter getSSOAdapter(HttpSession session) {
		String ssoAdapterName = session.getServletContext().getInitParameter("ssoAdapterName");
		if (ssoAdapterName == null)
			ssoAdapterName = SSOUtils.SSO_ADAPTER_VAR_NAME;
		SSOAdapter adapter = (SSOAdapter) session.getAttribute(ssoAdapterName);
		if (adapter == null)
			throw new SSOException("Not found SSOAdapter,please login first.");
		return adapter;
	}

	/**
	 * <b>过滤返回授权的通道列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param routeways 完整的通道列表
	 * @param grantActions 授权的action
	 * @return 授权的通道列表
	 */
	public static Collection<SSORouteway> filterRouteway(Collection<SSORouteway> routeways,
			Collection<SSOAction> grantActions) {
		//过滤未授权节点
		SSORouteway root = new SSORouteway();
		root.setLevel(0);
		ClosureUtil.processMultiObject(routeways, root, new MultiObjectClosure<SSORouteway, SSORouteway>() {

			@Override
			public Collection<SSORouteway> getSub(SSORouteway srcParent) {
				return srcParent.getChildren();
			}

			@Override
			public SSORouteway process(SSORouteway srcChild, SSORouteway destParent) {
				SSOAction action = srcChild.getAction();
				if (srcChild.isRequired() || (action == null && srcChild.hasChildren())
						|| (action != null && grantActions.contains(action))) {
					//枝干节点或有权限的叶子节点
					SSORouteway child = new SSORouteway();
					child.setCode(srcChild.getCode());
					child.setNodeName(srcChild.getNodeName());
					child.setName(srcChild.getName());
					child.setLevel(srcChild.getLevel());
					child.setRequired(srcChild.isRequired());
					child.setDescr(srcChild.getDescr());
					child.setTag(srcChild.getTag());
					child.setAction(srcChild.getAction());
					destParent.addChild(child, false);
					return child;
				} else {
					//没有权限，丢弃该节点及子节点
					return null;
				}
			}

		});
		//去掉空节点
		List<SSORouteway> discard = new ArrayList<>();
		ClosureUtil.forEach(root, false, new Consumer<SSORouteway>() {

			@Override
			public void call(SSORouteway routeway) {
				if (routeway.hasChildren()) {
					Collection<SSORouteway> children = routeway.getChildren();
					for (SSORouteway child : children) {
						if (!child.isRequired() && child.getAction() == null && !child.hasChildren()) {
							discard.add(child);
						}
					}
					for (SSORouteway child : discard) {
						children.remove(child);
					}
					discard.clear();
				}
			}
		});
		return root.getChildren();
	}

	/**
	 * <b>获得远程地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @return 远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * <b>保存登录凭证到Cookie。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param ticket 登录凭证
	 * @param response HttpServletResponse
	 */
	public static void saveTicketToCookie(String org, SSOTicket ticket, HttpServletResponse response) {
		String name = SSO_TICKET_COOKIE_PREFIX + org;
		Cookie cookie = new Cookie(name, ticket.getTonken());
		if (ticket.onlySessionValid())
			cookie.setMaxAge(-1);
		else
			cookie.setMaxAge(ticket.getMaxAge());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * <b>从Cookie查找登录凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param request HttpServletRequest
	 * @return 返回登录凭证，如果没有则返回null
	 */
	public static SSOTicket findTicketFromCookie(String org, HttpServletRequest request) {
		SSOTicket ticket = null;
		//获得登录凭证
		String name = SSO_TICKET_COOKIE_PREFIX + org;
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			try {
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						ticket = new SSOTicket(cookie.getValue());
						ticket.setOrganization(org);
						break;
					}
				}
			} catch (Exception e) {
			}
		}
		return SSOTicket.isValidTicket(ticket) ? ticket : null;
	}

	/**
	 * <b>从Cookie清除登录凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param response HttpServletResponse
	 */
	public static void clearTicketFromCookie(String org, HttpServletResponse response) {
		String name = SSO_TICKET_COOKIE_PREFIX + org;
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * <b>创建参数NameValuePair。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 参数名称
	 * @param value 参数值
	 * @return 参数NameValuePair
	 */
	public static NameValuePair parameter(String name, Object value) {
		return new BasicNameValuePair(name, CoreUtil.toString(value));
	}

	/**
	 * <b>创建参数列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param args 参数数组
	 * @return 参数列表
	 */
	public static List<NameValuePair> asList(NameValuePair... args) {
		int len = args.length;
		List<NameValuePair> list = new ArrayList<>(len);
		for (int i = 0; i < len; i++) {
			NameValuePair arg = args[i];
			if (arg.getValue() != null)
				list.add(arg);
		}
		return list;
	}

	/**
	 * <b>executePostUrl。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param httpClient
	 * @param root
	 * @param path
	 * @param page
	 * @param nvps
	 * @return
	 */
	public static String executePostUrl(HttpClient httpClient, String root, String path, String page,
			NameValuePair... nvps) {
		HttpResponse resp = null;
		try {
			URIBuilder builder = new URIBuilder(root);
			builder.setCharset(StringUtil.DEFAULT_ENCODING);
			if (!StringUtil.isBlank(path))
				builder.setPath(builder.getPath() + path);
			if (!StringUtil.isBlank(page))
				builder.setPath(builder.getPath() + page);
			HttpPost req = new HttpPost(builder.build());
			if (!ArrayUtils.isEmpty(nvps)) {
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(asList(nvps), StringUtil.DEFAULT_ENCODING);
				req.setEntity(formEntity);
			}
			resp = httpClient.execute(req);
			InputStream is = resp.getEntity().getContent();
			StringWriter writer = new StringWriter(is.available());
			IOUtils.copy(is, writer, StringUtil.DEFAULT_ENCODING);
			return writer.toString();
		} catch (Exception e) {
			throw new SSOException(e.getMessage(), e);
		} finally {
			if (resp instanceof Closeable)
				IOUtil.closeQuietly((Closeable) resp);
		}
	}

	/**
	 * <b>executeGetUrl。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param httpClient
	 * @param root
	 * @param path
	 * @param page
	 * @param nvps
	 * @return
	 */
	public static String executeGetUrl(HttpClient httpClient, String root, String path, String page,
			NameValuePair... nvps) {
		HttpResponse resp = null;
		try {
			URIBuilder builder = new URIBuilder(root);
			builder.setCharset(StringUtil.DEFAULT_ENCODING);
			if (!StringUtil.isBlank(path))
				builder.setPath(builder.getPath() + path);
			if (!StringUtil.isBlank(page))
				builder.setPath(builder.getPath() + page);
			if (!ArrayUtils.isEmpty(nvps))
				builder.addParameters(asList(nvps));
			HttpGet req = new HttpGet(builder.build());
			resp = httpClient.execute(req);
			InputStream is = resp.getEntity().getContent();
			StringWriter writer = new StringWriter(is.available());
			IOUtils.copy(is, writer, StringUtil.DEFAULT_ENCODING);
			return writer.toString();
		} catch (Exception e) {
			throw new SSOException(e.getMessage(), e);
		} finally {
			if (resp instanceof Closeable)
				IOUtil.closeQuietly((Closeable) resp);
		}
	}

	/**
	 * <b>executeUrl。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 请求内容是json串。
	 * @param httpClient
	 * @param root
	 * @param path
	 * @param page
	 * @param body
	 * @param nvps
	 * @return
	 */
	public static String executeUrl(HttpClient httpClient, String root, String path, String page, String body,
			NameValuePair... nvps) {
		HttpResponse resp = null;
		try {
			URIBuilder builder = new URIBuilder(root);
			builder.setCharset(StringUtil.DEFAULT_ENCODING);
			if (!StringUtil.isBlank(path))
				builder.setPath(builder.getPath() + path);
			if (!StringUtil.isBlank(page))
				builder.setPath(builder.getPath() + page);
			if (!ArrayUtils.isEmpty(nvps))
				builder.addParameters(asList(nvps));
			HttpPost req = new HttpPost(builder.build());
			StringEntity bodyEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
			req.setEntity(bodyEntity);
			resp = httpClient.execute(req);
			InputStream is = resp.getEntity().getContent();
			StringWriter writer = new StringWriter(is.available());
			IOUtils.copy(is, writer, StringUtil.DEFAULT_ENCODING);
			return writer.toString();
		} catch (Exception e) {
			throw new SSOException(e.getMessage(), e);
		} finally {
			if (resp instanceof Closeable)
				IOUtil.closeQuietly((Closeable) resp);
		}
	}

	/**
	 * <b>添加url请求参数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param url StringBuilder
	 * @param paramName 请求参数name
	 * @param value 请求参数value 
	 * @return StringBuilder
	 */
	public static StringBuilder appendParameter(StringBuilder url, String paramName, String value) {
		return appendParameter(url, paramName, value, false);
	}

	/**
	 * <b>添加url请求参数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param url StringBuilder
	 * @param paramName 请求参数name
	 * @param value 请求参数value
	 * @param strict 严格检查参数是否已经存在 
	 * @return StringBuilder
	 */
	public static StringBuilder appendParameter(StringBuilder url, String paramName, String value, boolean strict) {
		String paramValue;
		try {
			paramValue = URLEncoder.encode(value, StringUtil.DEFAULT_ENCODING.name());
		} catch (UnsupportedEncodingException e) {
			paramValue = value;
		}
		if (paramName.charAt(paramName.length() - 1) != '=')
			paramName += '=';
		int pos = url.indexOf("?");
		int mark = pos + 1;
		if (pos == -1) { //没找到问号
			url.append('?');
			url.append(paramName);
			url.append(paramValue);
		} else if (mark == url.length()) { //最后一个字符是问号
			url.append(paramName);
			url.append(paramValue);
		} else if (!strict || (url.indexOf('?' + paramName, pos) == -1 && url.indexOf('&' + paramName, mark) == -1)) {
			url.append('&');
			url.append(paramName);
			url.append(paramValue);
		}
		return url;
	}

	/**
	 * <b>创建新的不重复的凭证号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构代码
	 * @return 凭证号
	 */
	public static synchronized String createUniqueTicketNo(String org) {
		_ticketNo = TicketArithmetic.INSTANCE.getNextId(_ticketNo);
		return org + '_' + _ticketNo;
	}

	/**
	 * <b>创建操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param session HttpSession
	 * @param opInfo 操作信息
	 * @return 操作日志
	 */
	public static OpLog createOpLog(HttpSession session, OpInfo opInfo) {
		SSOAdapter ssoAdapter = SSOUtils.getSSOAdapter(session);
		SSOTicket ticket = ssoAdapter.getTicket();
		GrantUser gu = ssoAdapter.getGrantUser();
		OpLog opLog = OpLog.of(ssoAdapter.misId(), ticket.getTicketNo(), ticket.getOrganization(), gu.getCode(),
				gu.getName(), opInfo);
		return opLog;
	}

	private static String _ticketNo = null;
}
