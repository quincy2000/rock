package org.quincy.rock.sso.adapter;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.OrgAdapter;
import org.quincy.rock.sso.SSODept;
import org.quincy.rock.sso.SSOUser;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>RestOrgAdapter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月3日 下午1:46:16</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
class RestOrgAdapter implements OrgAdapter {
	/**
	 * SSO_ORG_REST_PATH。
	 */
	private static final String SSO_ORG_REST_PATH = "/org";
	/**
	 * 连接池。
	 */
	private final HttpClientConnectionManager pool;

	/**
	 * SSO服务器url。
	 */
	private String ssoServerUrl;
	/**
	 * 组织机构代码。
	 */
	private String org;
	/**
	 * 凭证号。
	 */
	private String ticketNo;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pool 连接池
	 * @param ssoServerUrl SSO服务器url
	 * @param org 组织机构代码
	 * @param ticketNo 凭证号
	 */
	public RestOrgAdapter(HttpClientConnectionManager pool, String ssoServerUrl, String org, String ticketNo) {
		this.pool = pool;
		this.ssoServerUrl = ssoServerUrl;
		this.org = org;
		this.ticketNo = ticketNo;
	}

	/** 
	 * getOrganization。
	 * @see org.quincy.rock.sso.OrgAdapter#getOrganization()
	 */
	@Override
	public String getOrganization() {
		return org;
	}

	/** 
	 * findAllDepts。
	 * @see org.quincy.rock.sso.OrgAdapter#findAllDepts(boolean)
	 */
	@Override
	public List<SSODept> findAllDepts(boolean tree) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/findAllDepts",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
				SSOUtils.parameter("tree", tree));
		Result<List> result = Result.toResult(json, List.class, SSODept.class);
		return result.getResult();
	}

	/** 
	 * findAllChildren。
	 * @see org.quincy.rock.sso.OrgAdapter#findAllChildren(java.lang.String)
	 */
	@Override
	public List<SSODept> findAllChildren(String deptCode) {
		NameValuePair[] pairs;
		if (StringUtil.isBlank(deptCode))
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org) };
		else
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
					SSOUtils.parameter("deptCode", deptCode) };
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/findAllChildren", pairs);
		Result<List> result = Result.toResult(json, List.class, SSODept.class);
		return result.getResult();
	}

	/** 
	 * findDept。
	 * @see org.quincy.rock.sso.OrgAdapter#findDept(java.lang.String)
	 */
	@Override
	public SSODept findDept(String code) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/findDept",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
				SSOUtils.parameter("code", code));
		Result<SSODept> result = Result.toResult(json, SSODept.class);
		return result.getResult();
	}

	/** 
	 * findAllUsers。
	 * @see org.quincy.rock.sso.OrgAdapter#findAllUsers(java.lang.String[])
	 */
	@Override
	public List<SSOUser> findAllUsers(String... deptCode) {
		NameValuePair[] pairs;
		if (ArrayUtils.isEmpty(deptCode))
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org) };
		else
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
					SSOUtils.parameter("deptCode", StringUtils.join(deptCode, StringUtil.CHAR_COMMA)) };
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/findAllUsers", pairs);
		Result<List> result = Result.toResult(json, List.class, SSOUser.class);
		return result.getResult();
	}

	/** 
	 * findAllUsers。
	 * @see org.quincy.rock.sso.OrgAdapter#findAllUsers(java.lang.Iterable)
	 */
	@Override
	public List<SSOUser> findAllUsers(Iterable<String> codes) {
		String strCodes = StringUtils.join(codes, StringUtil.CHAR_COMMA);
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/findAllUsersIn",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
				SSOUtils.parameter("codes", strCodes));
		Result<List> result = Result.toResult(json, List.class, SSOUser.class);
		return result.getResult();
	}

	/** 
	 * findUser。
	 * @see org.quincy.rock.sso.OrgAdapter#findUser(java.lang.String)
	 */
	@Override
	public SSOUser findUser(String code) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/findUser",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
				SSOUtils.parameter("code", code));
		Result<SSOUser> result = Result.toResult(json, SSOUser.class);
		return result.getResult();
	}

	/** 
	 * changePassword。
	 * @see org.quincy.rock.sso.OrgAdapter#changePassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean changePassword(String code, String oldPwd, String newPwd) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_ORG_REST_PATH, "/changePassword",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("org", org),
				SSOUtils.parameter("code", code), SSOUtils.parameter("oldPwd", oldPwd),
				SSOUtils.parameter("newPwd", newPwd));
		Result<Boolean> result = Result.toResult(json, Boolean.class);
		return result.getResult();
	}

	/**
	 * <b>获得HttpClient。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return HttpClient
	 */
	private HttpClient httpClient() {
		return HttpClients.custom().setConnectionManager(this.pool).build();
	}
}
