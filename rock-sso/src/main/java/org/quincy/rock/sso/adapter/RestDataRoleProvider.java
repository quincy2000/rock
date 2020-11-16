package org.quincy.rock.sso.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.DataRoleProvider;
import org.quincy.rock.sso.SSODataRole;
import org.quincy.rock.sso.util.SSOUtils;

/**
 * <b>RestDataRoleProvider。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月19日 下午12:25:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
class RestDataRoleProvider implements DataRoleProvider {
	/**
	 * SSO_DATAROLE_REST_PATH。
	 */
	private static final String SSO_DATAROLE_REST_PATH = "/dataRole";
	/**
	 * 连接池。
	 */
	private final HttpClientConnectionManager pool;

	/**
	 * SSO服务器url。
	 */
	private String ssoServerUrl;
	/**
	 * 业务系统id。
	 */
	private String misid;
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
	 * @param misid 业务系统id
	 * @param ticketNo 凭证号
	 */
	public RestDataRoleProvider(HttpClientConnectionManager pool, String ssoServerUrl, String misid, String ticketNo) {
		this.pool = pool;
		this.ssoServerUrl = ssoServerUrl;
		this.misid = misid;
		this.ticketNo = ticketNo;
	}

	/** 
	 * getMisId。
	 * @see org.quincy.rock.sso.DataRoleProvider#getMisId()
	 */
	@Override
	public String getMisId() {
		return this.misid;
	}

	/** 
	 * supportLeaf。
	 * @see org.quincy.rock.sso.DataRoleProvider#supportLeaf()
	 */
	@Override
	public boolean supportLeaf() {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/supportLeaf",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid));
		Result<Boolean> result = Result.toResult(json, Boolean.class);
		return result.getResult();
	}

	/** 
	 * findAllLimbs。
	 * @see org.quincy.rock.sso.DataRoleProvider#findAllLimbs(boolean)
	 */
	@Override
	public List<SSODataRole> findAllLimbs(boolean tree) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/findAllLimbs",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid),
				SSOUtils.parameter("tree", tree));
		Result<List> result = Result.toResult(json, List.class, SSODataRole.class);
		return result.getResult();
	}

	/** 
	 * findAllChildren。
	 * @see org.quincy.rock.sso.DataRoleProvider#findAllChildren(java.lang.String)
	 */
	@Override
	public List<SSODataRole> findAllChildren(String limbCode) {
		NameValuePair[] pairs;
		if (StringUtil.isBlank(limbCode))
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid) };
		else
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid),
					SSOUtils.parameter("limbCode", limbCode) };
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/findAllChildren",
				pairs);
		Result<List> result = Result.toResult(json, List.class, SSODataRole.class);
		return result.getResult();
	}

	/** 
	 * findLimb。
	 * @see org.quincy.rock.sso.DataRoleProvider#findLimb(java.lang.String)
	 */
	@Override
	public SSODataRole findLimb(String code) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/findLimb",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid),
				SSOUtils.parameter("code", code));
		Result<SSODataRole> result = Result.toResult(json, SSODataRole.class);
		return result.getResult();
	}

	/** 
	 * findAllLeafs。
	 * @see org.quincy.rock.sso.DataRoleProvider#findAllLeafs(java.lang.String, int, int)
	 */
	@Override
	public PageSet<SSODataRole> findAllLeafs(String limbCode, int page, int pageSize) {
		NameValuePair[] pairs;
		if (StringUtil.isBlank(limbCode))
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid),
					SSOUtils.parameter("page", page), SSOUtils.parameter("pageSize", pageSize) };
		else
			pairs = new NameValuePair[] { SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid),
					SSOUtils.parameter("limbCode", limbCode), SSOUtils.parameter("page", page),
					SSOUtils.parameter("pageSize", pageSize) };
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/findAllLeafs",
				pairs);
		Result<PageSet> result = Result.toResult(json, PageSet.class, SSODataRole.class);
		return result.getResult();
	}

	/** 
	 * findLeaf。
	 * @see org.quincy.rock.sso.DataRoleProvider#findLeaf(java.lang.String)
	 */
	@Override
	public SSODataRole findLeaf(String code) {
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/findLeaf",
				SSOUtils.parameter("ticket", ticketNo), SSOUtils.parameter("misid", misid),
				SSOUtils.parameter("code", code));
		Result<SSODataRole> result = Result.toResult(json, SSODataRole.class);
		return result.getResult();
	}

	/** 
	 * findAllNodes。
	 * @see org.quincy.rock.sso.DataRoleProvider#findAllNodes(java.lang.Iterable, java.lang.Iterable)
	 */
	@Override
	public Collection<SSODataRole> findAllNodes(Iterable<String> limbCodes, Iterable<String> leafCodes) {
		String strCodes;
		List<NameValuePair> params = new ArrayList<>();
		params.add(SSOUtils.parameter("ticket", ticketNo));
		params.add(SSOUtils.parameter("misid", misid));
		strCodes = StringUtils.join(limbCodes, StringUtil.CHAR_COMMA);
		if (!StringUtil.isBlank(strCodes)) {
			params.add(SSOUtils.parameter("limbCodes", strCodes));
		}
		strCodes = StringUtils.join(leafCodes, StringUtil.CHAR_COMMA);
		if (!StringUtil.isBlank(strCodes)) {
			params.add(SSOUtils.parameter("leafCodes", strCodes));
		}
		String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, SSO_DATAROLE_REST_PATH, "/findAllNodes",
				params.toArray(new NameValuePair[params.size()]));
		Result<List> result = Result.toResult(json, List.class, SSODataRole.class);
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
