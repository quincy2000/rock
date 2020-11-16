package org.quincy.rock.sso.adapter;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.JsonUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.DataRoleProvider;
import org.quincy.rock.sso.GrantUser;
import org.quincy.rock.sso.OpInfo;
import org.quincy.rock.sso.OpLog;
import org.quincy.rock.sso.OrgAdapter;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSOException;
import org.quincy.rock.sso.SSORouteway;
import org.quincy.rock.sso.SSOTicket;
import org.quincy.rock.sso.SSOUser;
import org.quincy.rock.sso.util.SSOUtils;
import org.slf4j.Logger;

/**
 * <b>RestSSOAdapter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月3日 下午1:41:09</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RestSSOAdapter implements SSOAdapter {
	/**
	 * logger。
	 */
	private static Logger logger = RockUtil.getLogger(RestSSOAdapter.class);
	/**
	 * SSO_REST_PATH。
	 */
	private static final String SSO_REST_PATH = "/mis";

	/**
	 * 连接池。
	 */
	private final PoolingHttpClientConnectionManager pool;

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
	 * 时间戳。
	 */
	private long timestamp = System.currentTimeMillis();
	/**
	 * 凭证。
	 */
	private SSOTicket ticket;
	/**
	 * 授权用户。
	 */
	private GrantUser grantUser;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param mark 私有标记
	 * @param ssoServerUrl SSO服务器url
	 * @param poolSize http连接池大小
	 * @param timeToLive http连接存活秒数
	 */
	public RestSSOAdapter(String misId, String mark, String ssoServerUrl, int poolSize, int timeToLive) {
		this.misId = misId;
		this.mark = mark;
		this.setSsoServerUrl(ssoServerUrl);
		this.pool = new PoolingHttpClientConnectionManager(timeToLive, TimeUnit.SECONDS);
		this.pool.setDefaultMaxPerRoute(poolSize);
		this.pool.setMaxTotal(poolSize * 4);
	}

	/**
	 * <b>获得SSO服务器url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return SSO服务器url
	 */
	public String getSsoServerUrl() {
		return ssoServerUrl;
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
	 * timestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	@Override
	public long timestamp() {
		return timestamp;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	/** 
	 * login。
	 * @see org.quincy.rock.sso.SSOAdapter#login(org.quincy.rock.sso.SSOTicket, java.lang.String)
	 */
	@Override
	public boolean login(SSOTicket ticket, String hostAddr) {
		try {
			String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/login",
					SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
					SSOUtils.parameter("hostAddr", hostAddr), SSOUtils.parameter("mark", mark));
			Result<GrantUser> result = Result.toResult(json, GrantUser.class);
			grantUser = result.getResult();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
		ticket.setOrganization(grantUser.getOrg());
		ticket.setOwner(grantUser.getCode());
		this.ticket = ticket;
		this.timestamp = System.currentTimeMillis();
		return true;
	}

	/** 
	 * logout。
	 * @see org.quincy.rock.sso.SSOAdapter#logout(boolean, java.lang.String)
	 */
	@Override
	public boolean logout(boolean discard, String descr) {
		if (!isValid())
			return true;
		try {
			String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/logout",
					SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
					SSOUtils.parameter("discard", discard), SSOUtils.parameter("mark", mark),
					SSOUtils.parameter("descr", descr));
			Result<Boolean> result = Result.toResult(json, Boolean.class);
			return result.getResult();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		} finally {
			this.ticket = null;
			this.grantUser = null;
		}
	}

	/** 
	 * dataRoleProvider。
	 * @see org.quincy.rock.sso.SSOAdapter#dataRoleProvider(java.lang.String)
	 */
	@Override
	public DataRoleProvider dataRoleProvider(String misId) throws SSOException {
		checkValid();
		return new RestDataRoleProvider(this.pool, ssoServerUrl, misId, ticket.getTicketNo());
	}

	/** 
	 * supportDataRole。
	 * @see org.quincy.rock.sso.SSOAdapter#supportDataRole(java.lang.String)
	 */
	@Override
	public boolean supportDataRole(String misId) throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/supportDataRole",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()));
		Result<Boolean> result = Result.toResult(json, Boolean.class);
		return result.getResult();
	}

	/** 
	 * orgAdapter。
	 * @see org.quincy.rock.sso.SSOAdapter#orgAdapter(java.lang.String)
	 */
	@Override
	public OrgAdapter orgAdapter(String org) throws SSOException {
		checkValid();
		return new RestOrgAdapter(this.pool, ssoServerUrl, org, ticket.getTicketNo());
	}

	/** 
	 * onlineUsers。
	 * @see org.quincy.rock.sso.SSOAdapter#onlineUsers(int, int)
	 */
	@Override
	public PageSet<SSOUser> onlineUsers(int page, int pageSize) throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/onlineUsers",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("page", page), SSOUtils.parameter("pageSize", pageSize));
		Result<PageSet> result = Result.toResult(json, PageSet.class, SSOUser.class);
		return result.getResult();
	}

	/** 
	 * onlineUserCount。
	 * @see org.quincy.rock.sso.SSOAdapter#onlineUserCount()
	 */
	@Override
	public long onlineUserCount() throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/onlineUserCount",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()));
		Result<Long> result = Result.toResult(json, Long.class);
		return result.getResult();
	}

	/** 
	 * misId。
	 * @see org.quincy.rock.sso.SSOAdapter#misId()
	 */
	@Override
	public String misId() {
		return misId;
	}

	/** 
	 * misName。
	 * @see org.quincy.rock.sso.SSOAdapter#misName()
	 */
	@Override
	public String misName() throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getMisName",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()));
		Result<String> result = Result.toResult(json, String.class);
		return result.getResult();
	}

	/** 
	 * homepage。
	 * @see org.quincy.rock.sso.SSOAdapter#homepage()
	 */
	@Override
	public String homepage() throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getHomepage",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()));
		Result<String> result = Result.toResult(json, String.class);
		return result.getResult();
	}

	private Map<String, Object> portalMis;

	/** 
	 * portalMis。
	 * @see org.quincy.rock.sso.SSOAdapter#portalMis()
	 */
	@Override
	public Map<String, Object> portalMis() {
		try {
			checkValid();
			String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getPortalMis",
					SSOUtils.parameter("ticket", ticket.getTicketNo()));
			Result<Map> result = Result.toResult(json, Map.class);
			portalMis = result.getResult();
			return portalMis;
		} catch (Exception e) {
			return portalMis;
		}
	}

	/** 
	 * accessibleMis。
	 * @see org.quincy.rock.sso.SSOAdapter#accessibleMis()
	 */
	@Override
	public Collection<Map<String, Object>> accessibleMis() throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getAccessibleMis",
				SSOUtils.parameter("ticket", ticket.getTicketNo()));
		Result<Collection> result = Result.toResult(json, Collection.class);
		return result.getResult();
	}

	/** 
	 * routeway。
	 * @see org.quincy.rock.sso.SSOAdapter#routeway(java.lang.String)
	 */
	@Override
	public Collection<SSORouteway> routeway(String key) throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getRouteways",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("key", key));
		Result<Collection> result = Result.toResult(json, Collection.class, SSORouteway.class);
		return result.getResult();
	}

	/** 
	 * allParams。
	 * @see org.quincy.rock.sso.SSOAdapter#allParams()
	 */
	@Override
	public Map<String, String> allParams() throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getAllParam",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()));
		Result<Map> result = Result.toResult(json, Map.class);
		return result.getResult();
	}

	/** 
	 * getParam。
	 * @see org.quincy.rock.sso.SSOAdapter#getParam(java.lang.String)
	 */
	@Override
	public String getParam(String key) throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getParam",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("key", key));
		Result<String> result = Result.toResult(json, String.class);
		return result.getResult();
	}

	/** 
	 * setParam。
	 * @see org.quincy.rock.sso.SSOAdapter#setParam(java.lang.String, java.lang.String)
	 */
	@Override
	public void setParam(String key, String value) throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/setParam",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("key", key), SSOUtils.parameter("value", value));
		Result<Boolean> result = Result.toResult(json, Boolean.class);
		if (!Boolean.TRUE.equals(result.getResult()))
			throw new SSOException("Setting parameter failed!");
	}

	/** 
	 * sequences。
	 * @see org.quincy.rock.sso.SSOAdapter#sequences(java.lang.String, int)
	 */
	@Override
	public Collection<String> sequences(String name, int count) throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/getSequences",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("name", name), SSOUtils.parameter("count", count));
		Result<Collection> result = Result.toResult(json, Collection.class);
		return result.getResult();
	}

	/** 
	 * oplogByCode。
	 * @see org.quincy.rock.sso.SSOAdapter#oplogByCode(java.lang.String, java.sql.Date, java.sql.Date, int, int)
	 */
	@Override
	public PageSet<OpLog> oplogByCode(String opCode, Date beginDate, Date endDate, int page, int pageSize)
			throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/oplogByCode",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("opCode", opCode), SSOUtils.parameter("beginDate", beginDate.getTime()),
				SSOUtils.parameter("endDate", endDate.getTime()), SSOUtils.parameter("page", page),
				SSOUtils.parameter("pageSize", pageSize));
		Result<PageSet> result = Result.toResult(json, PageSet.class, OpLog.class);
		return result.getResult();
	}

	/** 
	 * oplogByOwner。
	 * @see org.quincy.rock.sso.SSOAdapter#oplogByOwner(java.lang.String, java.sql.Date, java.sql.Date, int, int)
	 */
	@Override
	public PageSet<OpLog> oplogByOwner(String owner, Date beginDate, Date endDate, int page, int pageSize)
			throws SSOException {
		checkValid();
		String json = SSOUtils.executePostUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/oplogByOwner",
				SSOUtils.parameter("misid", misId), SSOUtils.parameter("ticket", ticket.getTicketNo()),
				SSOUtils.parameter("owner", owner), SSOUtils.parameter("beginDate", beginDate.getTime()),
				SSOUtils.parameter("endDate", endDate.getTime()), SSOUtils.parameter("page", page),
				SSOUtils.parameter("pageSize", pageSize));
		Result<PageSet> result = Result.toResult(json, PageSet.class, OpLog.class);
		return result.getResult();
	}

	/** 
	 * writeOplog。
	 * @see org.quincy.rock.sso.SSOAdapter#writeOplog(org.quincy.rock.sso.OpInfo)
	 */
	@Override
	public void writeOplog(OpInfo opInfo) {
		try {
			checkValid();
			OpLog opLog = OpLog.of(misId, ticket.getTicketNo(), ticket.getOrganization(), grantUser.getCode(),
					grantUser.getName(), opInfo);
			String json = SSOUtils.executeUrl(httpClient(), getSsoServerUrl(), SSO_REST_PATH, "/writeOplog_json",
					JsonUtil.toJson(opLog));
			Result<Boolean> result = Result.toResult(json, Boolean.class);
			if (!Boolean.TRUE.equals(result.getResult()))
				throw new SSOException("Write operation log failed!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/** 
	 * getTicket。
	 * @see org.quincy.rock.sso.SSOAdapter#getTicket()
	 */
	@Override
	public SSOTicket getTicket() {
		return ticket;
	}

	/** 
	 * getGrantUser。
	 * @see org.quincy.rock.sso.SSOAdapter#getGrantUser()
	 */
	@Override
	public GrantUser getGrantUser() throws SSOException {
		checkValid();
		return grantUser;
	}

	/** 
	 * close。
	 * @see org.quincy.rock.sso.SSOAdapter#close()
	 */
	@Override
	public void close() {
		SSOAdapter.super.close();
		IOUtil.closeQuietly(this.pool);
	}

	/**
	 * <b>适配器有效性检查。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @throws SSOException
	 */
	protected void checkValid() throws SSOException {
		if (!isValid())
			throw new SSOException("Not login!");
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
