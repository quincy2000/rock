package org.quincy.rock.sso.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.sso.SSOAction;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSOScope;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>SSO授权检查过滤器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月12日 下午4:02:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SSOCheckGrantFilter extends AbstractCheckGrantFilter {

	/**
	 * 存放功能权限列表的key。
	 */
	private static final String SESSION_KEY_UGAC = "sso_ugActions";
	/**
	 * 存放服务范围列表的key。
	 */
	private static final String SESSION_KEY_UGSC = "sso_ugScopes";

	/**
	 * SSO适配器变量名称。
	 */
	private String ssoAdapterName = SSOUtils.SSO_ADAPTER_VAR_NAME;

	/**
	 * <b>获得SSO适配器变量名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return SSO适配器变量名称
	 */
	public String getSsoAdapterName() {
		return ssoAdapterName;
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
	 * checkGrant。
	 * @see org.quincy.rock.sso.web.AbstractCheckGrantFilter#checkGrant(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected final boolean checkGrant(String requestPath, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		SSOAdapter ssoAdapter = (SSOAdapter) session.getAttribute(ssoAdapterName);
		if (ssoAdapter == null)
			throw new NotFoundException(SSOAdapter.class.getSimpleName());
		//获得功能权限Map
		Map<String, Collection<SSOAction>> acMap = this.getActionMap(session, ssoAdapter);
		//先检查url权限
		boolean ok = acMap.containsKey(requestPath);
		if (ok) {
			//有权限访问url，继续检查其他权限
			ok = this.checkGrant(request, response, ssoAdapter, acMap.get(requestPath),
					getScopeMap(session, ssoAdapter));
		}
		return ok;
	}

	/**
	 * <b>检查权限。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param request 请求HttpServlet
	 * @param response 响应HttpServlet
	 * @param ssoAdapter SSO适配器
	 * @param actions 当前url对应的功能权限列表
	 * @param scMap 服务范围权限列表
	 * @return 是否是授权的合法操作
	 */
	protected boolean checkGrant(HttpServletRequest request, HttpServletResponse response, SSOAdapter ssoAdapter,
			Collection<SSOAction> actions, Map<String, SSOScope> scMap) {
		return true;
	}

	//获得服务范围权限
	private Map<String, SSOScope> getScopeMap(HttpSession session, SSOAdapter ssoAdapter) {
		Map<String, SSOScope> scMap = (Map) session.getAttribute(SESSION_KEY_UGSC);
		if (scMap == null) {
			synchronized (SESSION_KEY_UGSC) {
				scMap = (Map) session.getAttribute(SESSION_KEY_UGSC);
				if (scMap == null) {
					//将服务范围列表放进Map(key=code)	
					scMap = new HashMap<>();
					for (SSOScope scope : ssoAdapter.getScopes()) {
						scMap.put(scope.getCode(), scope);
					}
					session.setAttribute(SESSION_KEY_UGSC, scMap);
				}
			}
		}
		return scMap;
	}

	//获得功能权限Map
	private Map<String, Collection<SSOAction>> getActionMap(HttpSession session, SSOAdapter ssoAdapter) {
		Map<String, Collection<SSOAction>> acMap = (Map) session.getAttribute(SESSION_KEY_UGAC);
		if (acMap == null) {
			synchronized (SESSION_KEY_UGAC) {
				acMap = (Map) session.getAttribute(SESSION_KEY_UGAC);
				if (acMap == null) {
					//将功能权限列表放进Map(key=url)			
					acMap = new HashMap<>();
					for (SSOAction action : ssoAdapter.getActions()) {
						String path = action.getPathName();
						if (StringUtil.isBlank(path))
							continue;
						Collection<SSOAction> set = acMap.get(path);
						if (set == null) {
							set = new TreeSet<SSOAction>();
							acMap.put(path, set);
						}
						set.add(action);
					}
					session.setAttribute(SESSION_KEY_UGAC, acMap);
				}
			}
		}
		return acMap;
	}
}
