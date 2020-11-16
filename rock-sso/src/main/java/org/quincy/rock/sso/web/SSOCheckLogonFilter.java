package org.quincy.rock.sso.web;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.quincy.rock.core.exception.LoginException;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSOTicket;
import org.quincy.rock.sso.adapter.RestSSOAdapter;
import org.quincy.rock.sso.util.SSOUtils;
import org.slf4j.Logger;

/**
 * <b>SSO登录检查过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月1日 上午12:03:42</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOCheckLogonFilter extends AbstractFilter {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(SSOCheckLogonFilter.class);

	/**
	 * LOGIN_ERROR_MESSAGE。
	 */
	public static final String LOGIN_ERROR_MESSAGE = "Failed to login using ticket:";

	/**
	 * SSO适配器变量名称。
	 */
	private String ssoAdapterName = SSOUtils.SSO_ADAPTER_VAR_NAME;
	/**
	 * 组织机构标识。
	 */
	private String organization;
	/**
	 * SSO服务器url。
	 */
	private String ssoServerUrl;
	/**
	 * 业务系统id。
	 */
	private String misId;
	/**
	 * 私有标记。
	 */
	private String mark;
	/**
	 * 登录Url。
	 */
	private String logonUrl = SSOUtils.SSO_LOGON_URL;
	/**
	 * 凭证变量名。
	 */
	private String tonkenName = SSOUtils.SSO_TONKEN_VAR_NAME;
	/**
	 * 要求返回json错误码的变量名。
	 */
	private String jsonName = SSOUtils.SSO_JSON_VAR_NAME;
	/**
	 * 未登录json错误码。
	 */
	private String nologinCode = "nologin";
	/**
	 * 是否缓存凭证。
	 */
	private boolean cacheTicket;
	/**
	 * 登录成功后回到当前页。
	 */
	private boolean backCurrent;
	/**
	 * http连接池大小。
	 */
	private int httpPoolSize = 4;
	/**
	 * http连接存活秒数。
	 */
	private int httpTimeToLive = 2;

	/** 
	 * doFilter。
	 * @see org.quincy.rock.sso.web.AbstractFilter#doFilter(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(String requestPath, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception {
		logger.trace(SSOUtils.getRequestUrl(request));
		//先检查会话是否已经存在登录用户
		HttpSession session = request.getSession();
		SSOAdapter ssoAdapter = (SSOAdapter) session.getAttribute(ssoAdapterName);
		if (ssoAdapter == null) //新开始的会话
		{
			//如果没有有效登录则必须跳转到登录页
			synchronized (this) {
				ssoAdapter = (SSOAdapter) session.getAttribute(ssoAdapterName);
				if (ssoAdapter == null) //新开始的会话
				{
					String goPage = null;
					//尝试获得凭证
					String tonken = request.getParameter(tonkenName); //凭证通过请求参数获得
					boolean reqJson = Boolean.parseBoolean(request.getParameter(jsonName)); //登陆失败返回json格式信息
					String org = loadOrganization(request);
					SSOTicket ssoTicket = tonken == null ? SSOUtils.findTicketFromCookie(org, request)
							: new SSOTicket(tonken);
					if (ssoTicket == null) {
						//无凭证，转到登录页进行登录						
						goPage = createLogonUrl(request, org, false);
					} else {
						ssoTicket.setOrganization(org);
						//有凭证,使用凭证进行登录操作,创建SSO适配器并登录
						try {
							ssoAdapter = this.login(ssoTicket, SSOUtils.getRemoteAddr(request));
							session.setAttribute(ssoAdapterName, ssoAdapter);
							if (cacheTicket) {
								SSOUtils.saveTicketToCookie(org, ssoTicket, response);
							}
						} catch (Exception e) {
							//凭证号无效,转到登录页进行登录
							logger.debug(e.getMessage(), e);
							goPage = createLogonUrl(request, org, true);
						}
					}
					if (goPage != null) {
						if (reqJson) {
							SSOUtils.writeJson(response, Result.toResult(nologinCode, goPage));
						} else {
							response.sendRedirect(goPage);
						}
						logger.debug("No login or ticket expired,sendRedirect:{}", goPage);
						return;
					}
				}
			}
		}
		//已登录,往下走
		chain.doFilter(request, response);
	}

	/**
	 * <b>设置SSO适配器变量名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ssoAdapterName SSO适配器变量名称
	 */
	public void setSsoAdapterName(String ssoAdapterName) {
		this.ssoAdapterName = ssoAdapterName;
	}

	/**
	 * <b>设置组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param organization 组织机构标识
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * <b>设置SSO服务器url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ssoServerUrl SSO服务器url
	 */
	public void setSsoServerUrl(String ssoServerUrl) {
		this.ssoServerUrl = ssoServerUrl.endsWith("/") ? ssoServerUrl.substring(0, ssoServerUrl.length() - 1)
				: ssoServerUrl;
	}

	/**
	 * <b>设置业务系统id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 */
	public void setMisId(String misId) {
		this.misId = misId;
	}

	/**
	 * <b>设置私有标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mark 私有标记
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * <b>设置登录Url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param logonUrl 登录Url
	 */
	public void setLogonUrl(String logonUrl) {
		this.logonUrl = logonUrl;
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
	 * <b>设置未登录json错误码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param nologinCode 未登录json错误码
	 */
	public void setNologinCode(String nologinCode) {
		this.nologinCode = nologinCode;
	}

	/**
	 * <b>设置是否缓存凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cacheTicket 是否缓存凭证
	 */
	public void setCacheTicket(boolean cacheTicket) {
		this.cacheTicket = cacheTicket;
	}

	/**
	 * <b>登录成功后回到当前页。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param backCurrent 登录成功后回到当前页
	 */
	public void setBackCurrent(boolean backCurrent) {
		this.backCurrent = backCurrent;
	}

	/**
	 * <b>设置http连接池大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param httpPoolSize http连接池大小
	 */
	public void setHttpPoolSize(int httpPoolSize) {
		this.httpPoolSize = httpPoolSize;
	}

	/**
	 * <b>设置http连接存活秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param httpTimeToLive http连接存活秒数
	 */
	public void setHttpTimeToLive(int httpTimeToLive) {
		this.httpTimeToLive = httpTimeToLive;
	}

	/**
	 * <b>获得组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request HttpServletRequest
	 * @return 组织机构标识
	 */
	private String loadOrganization(HttpServletRequest request) {
		String org = request.getParameter("org");
		return StringUtil.isBlank(org) ? organization : org;
	}

	/**
	 * <b>构造完整的登录Url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param req HttpServletRequest
	 * @param org 组织机构标识
	 * @param crear 是否清除旧的凭证
	 * @return 登录Url
	 */
	private String createLogonUrl(HttpServletRequest req, String org, boolean crear) {
		StringBuilder sb = new StringBuilder();
		if (logonUrl.startsWith("/"))
			sb.append(ssoServerUrl);
		sb.append(logonUrl);
		if (org != null)
			SSOUtils.appendParameter(sb, "org=", org);
		SSOUtils.appendParameter(sb, "misid=", misId);
		SSOUtils.appendParameter(sb, "clear=", Boolean.toString(crear));
		if (backCurrent) {
			String query = req.getQueryString();
			query = query == null ? "?" : ("?" + query);
			//查找凭证
			int index = query.indexOf("?" + tonkenName + "=");
			if (index == -1)
				index = query.indexOf('&' + tonkenName + "=");
			if (index != -1) {
				//找到了凭证，去掉
				int next = query.indexOf('&', index + tonkenName.length() + 2);
				if (next == -1) {
					query = query.substring(0, index);
				} else {
					query = query.substring(0, index) + query.substring(next);
				}
			}
			//myUrl
			StringBuffer myUrl = req.getRequestURL();
			if (query.length() > 1) {
				myUrl.append(query);
			}
			SSOUtils.appendParameter(sb, "orig=", StringUtil.str2hex(myUrl.toString()));
		}
		return sb.toString();
	}

	/**
	 * <b>使用凭证登录系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticket 凭证
	 * @param hostAddr 客户端主机地址
	 * @return 如果登录成功返回SSO适配器，否则抛出异常
	 * @throws LoginException
	 */
	private SSOAdapter login(SSOTicket ticket, String hostAddr) throws LoginException {
		SSOAdapter adapter = createSSOAdapter(misId, mark, ssoServerUrl);
		if (adapter.login(ticket, hostAddr))
			return adapter;
		else
			throw new LoginException(LOGIN_ERROR_MESSAGE + ticket);
	}

	/**
	 * <b>创建SSO适配器器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param mark 私有标记
	 * @param ssoServerUrl SSO服务器url
	 * @return SSO适配器器
	 */
	private SSOAdapter createSSOAdapter(String misId, String mark, String ssoServerUrl) {
		RestSSOAdapter adapter = new RestSSOAdapter(misId, mark, ssoServerUrl, this.httpPoolSize, this.httpTimeToLive);
		return adapter;
	}
}
