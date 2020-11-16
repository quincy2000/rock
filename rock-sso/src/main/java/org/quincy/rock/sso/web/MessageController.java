package org.quincy.rock.sso.web;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.quincy.rock.core.exception.ResultException;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.JsonUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSOTicket;
import org.quincy.rock.sso.util.SSOUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>MessageController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 在spring环境下使用,提供对报文服务器的rest访问接口。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年10月7日 下午5:33:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Controller
@RequestMapping("message")
public class MessageController {
	/**
	 * logger。
	 */
	private static Logger logger = RockUtil.getLogger(MessageController.class);

	/**
	 * 连接池。
	 */
	private final PoolingHttpClientConnectionManager pool;

	/**
	 * 凭证变量名。
	 */
	private String tonkenName = SSOUtils.SSO_TONKEN_VAR_NAME;
	/**
	 * 报文服务器Url。
	 */
	private String messageServerUrl;
	/**
	 * 报文服务路径名。
	 */
	private String serverPath = "/server";
	/**
	 * 字符集编码。
	 */
	private String encoding;
	/**
	 * 使用SSO Tonken访问报文服务器。
	 */
	private boolean useSsOTonken = true;

	/**
	 * SSO服务器url。
	 */
	private String ssoServerUrl;
	/**
	 * 组织机构标识。
	 */
	private String organization;
	/**
	 * 业务系统id。
	 */
	private String misId;
	/**
	 * 用户id。
	 */
	private String userId;
	/**
	 * 密码。
	 */
	private String password;
	/**
	 * 固定验证码。
	 */
	private String fixCaptcha;

	/**
	 * tonken。
	 */
	private String tonken;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MessageController() {
		this(2, 2);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param poolSize http连接池大小
	 * @param timeToLive http连接存活秒数
	 */
	public MessageController(int poolSize, int timeToLive) {
		this.pool = new PoolingHttpClientConnectionManager(timeToLive, TimeUnit.SECONDS);
		this.pool.setDefaultMaxPerRoute(poolSize);
		this.pool.setMaxTotal(poolSize);
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
	 * <b>设置报文服务器Url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageServerUrl 报文服务器Url
	 */
	public void setMessageServerUrl(String messageServerUrl) {
		this.messageServerUrl = messageServerUrl.endsWith("/")
				? messageServerUrl.substring(0, ssoServerUrl.length() - 1)
				: messageServerUrl;
	}

	/**
	 * <b>设置报文服务路径名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverPath 报文服务路径名
	 */
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
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
	 * <b>使用SSO Tonken访问报文服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param useSsOTonken 使用SSO Tonken访问报文服务器
	 */
	public void setUseSsOTonken(boolean useSsOTonken) {
		this.useSsOTonken = useSsOTonken;
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
	 * <b>设置组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 */
	public void setOrganization(String org) {
		this.organization = org;
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
	 * <b>设置用户id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userId 用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * <b>设置密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <b>设置固定验证码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fixCaptcha 固定验证码
	 */
	public void setFixCaptcha(String fixCaptcha) {
		this.fixCaptcha = fixCaptcha;
	}

	//修正请求和响应
	private void reviseRequestAndResponse(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (StringUtils.isNoneBlank(encoding)) {
			req.setCharacterEncoding(encoding);
			resp.setCharacterEncoding(encoding);
		}
	}

	//
	private String getTonken(HttpSession session) {
		if (useSsOTonken) {
			SSOAdapter ssoAdapter = SSOUtils.getSSOAdapter(session);
			return ssoAdapter.tonken();
		} else {
			if (tonken == null) {
				String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, null, "/loginByUser",
						SSOUtils.parameter("misid", misId), SSOUtils.parameter("org", organization),
						SSOUtils.parameter("userid", userId), SSOUtils.parameter("password", password),
						SSOUtils.parameter("captcha", fixCaptcha), SSOUtils.parameter("age", -1));
				Result<SSOTicket> result = Result.toResult(json, SSOTicket.class);
				tonken = result.getResult().getTonken();
			}
			return tonken;
		}
	}

	/**
	 * <b>获得在线终端信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param type 终端类型，如果为null则返回全部
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 在线终端信息列表(Result<PageSet<Map<String, Object>>>)
	 */
	@RequestMapping(value = "/findAllOnlineTerminal", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody String findAllOnlineTerminal(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			String json = SSOUtils.executePostUrl(httpClient(), messageServerUrl, serverPath, "/findAllOnlineTerminal",
					SSOUtils.parameter(tonkenName, getTonken(session)),
					SSOUtils.parameter("type", type == null ? "" : type), SSOUtils.parameter("page", page),
					SSOUtils.parameter("pageSize", pageSize));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("findAllOnlineTerminal", e.getMessage(), e));
		}
	}

	/**
	 * <b>返回在线终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型，如果为null则返回全部数量
	 * @return 在线终端数量(Result<Integer>)
	 */
	@RequestMapping(value = "/onlineTerminalCount", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody String onlineTerminalCount(@RequestParam(required = false, name = "type") String type,
			HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			String json = SSOUtils.executePostUrl(httpClient(), messageServerUrl, serverPath, "/onlineTerminalCount",
					SSOUtils.parameter(tonkenName, getTonken(session)),
					SSOUtils.parameter("type", type == null ? "" : type));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("onlineTerminalCount", e.getMessage(), e));
		}
	}

	/**
	 * <b>终端下线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 是否成功(Result<Boolean>)
	 */
	@RequestMapping(value = "/offlineTerminal", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody String offlineTerminal(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code, HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			String json = SSOUtils.executePostUrl(httpClient(), messageServerUrl, serverPath, "/offlineTerminal",
					SSOUtils.parameter(tonkenName, getTonken(session)),
					SSOUtils.parameter("type", type == null ? "" : type), SSOUtils.parameter("code", code));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("offlineTerminal", e.getMessage(), e));
		}
	}

	/**
	 * <b>查询终端是否在线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null,如果查询多个终端则使用逗号分割
	 * @return 终端是否在线，如果查询一个终端则返回Boolean,查询多个则返回List<Boolean>(Result<?>)
	 */
	@RequestMapping(value = "/terminalActived", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody String terminalActived(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code, HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			String json = SSOUtils.executePostUrl(httpClient(), messageServerUrl, serverPath, "/terminalActived",
					SSOUtils.parameter(tonkenName, getTonken(session)),
					SSOUtils.parameter("type", type == null ? "" : type), SSOUtils.parameter("code", code));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("terminalActived", e.getMessage(), e));
		}
	}

	/**
	 * <b>返回站台上指定终端的待发送指令数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 终端的待发送指令数(Result<Integer>)
	 */
	@RequestMapping(value = "/commandCount", method = { RequestMethod.GET, RequestMethod.POST })
	public final @ResponseBody String commandCount(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code, HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			String json = SSOUtils.executePostUrl(httpClient(), messageServerUrl, serverPath, "/commandCount",
					SSOUtils.parameter(tonkenName, getTonken(session)),
					SSOUtils.parameter("type", type == null ? "" : type), SSOUtils.parameter("code", code));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("commandCount", e.getMessage(), e));
		}
	}

	/**
	 * <b>给终端发送Rpc指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法是阻塞方法，会等待终端返回的报文对象。
	 * @param cmd 指令对象
	 * @return 指令返回的报文对象(Result<Object>)
	 */
	@RequestMapping(value = "/sendRpcMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public final @ResponseBody String sendRpcMessage(@RequestParam Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			cmd = SSOUtils.processParameterMap(cmd);
			String json = SSOUtils.executeUrl(httpClient(), messageServerUrl, serverPath, "/sendRpcMessage_json",
					JsonUtil.toJson(cmd), SSOUtils.parameter(tonkenName, getTonken(session)));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("sendRpcMessage", e.getMessage(), e));
		}
	}

	/**
	 * <b>给终端发送Rpc指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法是阻塞方法，会等待终端返回的报文对象。
	 * @param cmd 指令对象
	 * @return 指令返回的报文对象(Result<Object>)
	 */
	@RequestMapping(value = "/sendRpcMessage_json", method = { RequestMethod.POST })
	public final @ResponseBody String sendRpcMessage_json(@RequestBody Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		return sendRpcMessage(cmd, req, resp, session);
	}

	/**
	 * <b>给终端发送指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 同步指令不会提交到指令站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功,3-设备不在线,已经提交到指令站台)(Result<Integer>)
	 */
	@RequestMapping(value = "/sendMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public final @ResponseBody String sendMessage(@RequestParam Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			cmd = SSOUtils.processParameterMap(cmd);
			String json = SSOUtils.executeUrl(httpClient(), messageServerUrl, serverPath, "/sendMessage_json",
					JsonUtil.toJson(cmd), SSOUtils.parameter(tonkenName, getTonken(session)));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("sendMessage", e.getMessage(), e));
		}
	}

	/**
	 * <b>给终端发送指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 同步指令不会提交到指令站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功,3-设备不在线,已经提交到指令站台)(Result<Integer>)
	 */
	@RequestMapping(value = "/sendMessage_json", method = { RequestMethod.POST })
	public final @ResponseBody String sendMessage_json(@RequestBody Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		return sendMessage(cmd, req, resp, session);
	}

	/**
	 * <b>给终端直接发送消息指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不检查是否在线，也不会提交到站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功)(Result<Integer>)
	 */
	@RequestMapping(value = "/directMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public final @ResponseBody String directMessage(@RequestParam Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			cmd = SSOUtils.processParameterMap(cmd);
			String json = SSOUtils.executeUrl(httpClient(), messageServerUrl, serverPath, "/directMessage_json",
					JsonUtil.toJson(cmd), SSOUtils.parameter(tonkenName, getTonken(session)));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("directMessage", e.getMessage(), e));
		}
	}

	/**
	 * <b>给终端直接发送消息指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不检查是否在线，也不会提交到站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功)(Result<Integer>)
	 */
	@RequestMapping(value = "/directMessage_json", method = { RequestMethod.POST })
	public final @ResponseBody String directMessage_json(@RequestBody Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		return directMessage(cmd, req, resp, session);
	}

	/**
	 * <b>提交消息指令到站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 暂时不发送，仅仅提交消息到站台。
	 * @param cmd 指令对象
	 * @return 提交是否成功(Result<Boolean>)
	 */
	@RequestMapping(value = "/submitMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public final @ResponseBody String submitMessage(@RequestParam Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		try {
			this.reviseRequestAndResponse(req, resp);
			cmd = SSOUtils.processParameterMap(cmd);
			String json = SSOUtils.executeUrl(httpClient(), messageServerUrl, serverPath, "/submitMessage_json",
					JsonUtil.toJson(cmd), SSOUtils.parameter(tonkenName, getTonken(session)));
			JsonUtil.checkJson(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonUtil.toJson(Result.toResult("submitMessage", e.getMessage(), e));
		}
	}

	/**
	 * <b>提交消息指令到站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 暂时不发送，仅仅提交消息到站台。
	 * @param cmd 指令对象
	 * @return 提交是否成功(Result<Boolean>)
	 */
	@RequestMapping(value = "/submitMessage_json", method = { RequestMethod.POST })
	public final @ResponseBody String submitMessage_json(@RequestBody Map<String, Object> cmd, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) {
		return submitMessage(cmd, req, resp, session);
	}

	/**
	 * <b>关闭控制器，释放资源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void close() {
		if (!useSsOTonken && StringUtils.isNoneBlank(tonken)) {
			try {
				String json = SSOUtils.executePostUrl(httpClient(), ssoServerUrl, null, "/discardTonken",
						SSOUtils.parameter("misid", misId), SSOUtils.parameter("tonken", tonken));
				Result<Boolean> result = Result.toResult(json, Boolean.class);
				if (!result.getResult()) {
					throw new ResultException("discardTonken=false");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		IOUtil.closeQuietly(this.pool);
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
