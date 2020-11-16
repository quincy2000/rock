package org.quincy.rock.sso.server.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.GrantUser;
import org.quincy.rock.sso.OpLog;
import org.quincy.rock.sso.SSOAccesser;
import org.quincy.rock.sso.SSORouteway;
import org.quincy.rock.sso.SSOTicket;
import org.quincy.rock.sso.SSOUser;
import org.quincy.rock.sso.util.SSOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>业务系统功能访问接口控制器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月11日 下午3:04:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Controller
@RequestMapping("mis")
public class MisController extends SSOServerController {

	/**
	 * <b>使用凭证登录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param hostAddr 主机地址
	 * @param mark 私有标记
	 * @return 用户授权信息
	 */
	@RequestMapping(value = { "/login", "/logon" }, method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<GrantUser> login(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "hostAddr") String hostAddr,
			@RequestParam(required = false, name = "mark") String mark, HttpServletRequest request) {
		Result<GrantUser> result;
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			GrantUser gu = ssoAccesser.loginMis(misId, ticketNo, hostAddr, SSOUtils.getRemoteAddr(request), mark);
			result = Result.toResult(gu);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.login.fail", e.getMessage());
			logger.warn(errorText, e);
			result = Result.toResult("login", errorText, e);
		}
		return result;
	}

	/**
	 * <b>注销凭证登录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号 
	 * @param discard 是否废弃凭证
	 * @param mark 私有标记
	 * @param descr 注销描述
	 * @return 是否注销成功
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> logout(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "discard", defaultValue = "false") boolean discard,
			@RequestParam(required = false, name = "mark") String mark,
			@RequestParam(required = false, name = "descr") String descr, HttpServletRequest request) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.logoutMis(misId, ticketNo, discard, mark, descr);
			return Result.TRUE;
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.logout.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("logout", errorText, e);
		}
	}

	/**
	 * <b>写操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opLog 操作日志
	 * @return 是否成功
	 */
	@RequestMapping(value = "/writeOplog", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> writeOplog(OpLog opLog, HttpServletRequest request) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.writeOplog(opLog, SSOUtils.getRemoteAddr(request));
			return Result.TRUE;
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.writeOplog.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("writeOplog", errorText, e);
		}
	}

	/**
	 * <b>写操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opLog 操作日志
	 * @return 是否成功
	 */
	@RequestMapping(value = "/writeOplog_json", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> writeOplog_json(@RequestBody OpLog opLog, HttpServletRequest request) {
		return writeOplog(opLog, request);
	}

	/**
	 * <b>返回某个操作的操作日志列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param opCode 操作代码
	 * @param beginDate 时间段开始日期
	 * @param endDate 时间段结束日期
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页操作日志列表
	 */
	@RequestMapping(value = "/oplogByCode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<OpLog>> oplogByCode(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "opCode") String opCode,
			@RequestParam(required = true, name = "beginDate") String beginDate,
			@RequestParam(required = true, name = "endDate") String endDate,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			PageSet<OpLog> ps = ssoAccesser.oplogByCode(misId, opCode, DateUtil.toDate(beginDate),
					DateUtil.toDate(endDate), page, pageSize);
			return Result.toResult(ps);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.oplogByCode.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("oplogByCode", errorText, e);
		}
	}

	/**
	 * <b>返回某个操作用户的操作日志列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param owner 用户代码
	 * @param beginDate 时间段开始日期
	 * @param endDate 时间段结束日期
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页操作日志列表
	 */
	@RequestMapping(value = "/oplogByOwner", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<OpLog>> oplogByOwner(
			@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "owner") String owner,
			@RequestParam(required = true, name = "beginDate") String beginDate,
			@RequestParam(required = true, name = "endDate") String endDate,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			PageSet<OpLog> ps = ssoAccesser.oplogByOwner(misId, owner, DateUtil.toDate(beginDate),
					DateUtil.toDate(endDate), page, pageSize);
			return Result.toResult(ps);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.oplogByOwner.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("oplogByOwner", errorText, e);
		}
	}

	/**
	 * <b>设置参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只能设置系统维护参数。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param key 参数key
	 * @param value 参数值
	 * @return 是否成功
	 */
	@RequestMapping(value = "/setParam", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> setParam(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "key") String key,
			@RequestParam(required = false, name = "value") String value) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			ssoAccesser.setParam(misId, key, value);
			return Result.TRUE;
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.setParam.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("setParam", errorText, e);
		}
	}

	/**
	 * <b>获得参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param key 参数key
	 * @return 参数值
	 */
	@RequestMapping(value = "/getParam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> getParam(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "key") String key) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			String param = ssoAccesser.getParam(misId, key);
			return Result.toResult(param);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getParam.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getParam", errorText, e);
		}
	}

	/**
	 * <b>获得所有参数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @return 参数列表
	 */
	@RequestMapping(value = "/getAllParam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Map<String, String>> getAllParam(
			@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			Map<String, String> params = ssoAccesser.allParams(misId);
			return Result.toResult(params);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getAllParam.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getAllParam", errorText, e);
		}
	}

	/**
	 * <b>获得一个或多个自动增长序列值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param name 序列名称
	 * @param count 返回的序列值个数
	 * @return 一个或多个自动增长序列值
	 */
	@RequestMapping(value = "/getSequences", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<String>> getSequences(
			@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "count") int count) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			Collection<String> seqs = ssoAccesser.sequences(misId, name, count);
			return Result.toResult(seqs);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getSequences.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getSequences", errorText, e);
		}
	}

	/**
	 * <b>根据通道标识获得通道列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param key 通道标识
	 * @return 通道列表
	 */
	@RequestMapping(value = "/getRouteways", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<SSORouteway>> getRouteways(
			@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "key") String key) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo, misId);
			Collection<SSORouteway> list = ssoAccesser.routeway(misId, key);
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getRouteways.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getRouteways", errorText, e);
		}
	}

	/**
	 * <b>获得业务系统名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @return 业务系统名称
	 */
	@RequestMapping(value = "/getMisName", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> getMisName(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo);
			String homepage = ssoAccesser.misName(misId);
			return Result.toResult(homepage);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getMisName.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getMisName", errorText, e);
		}
	}

	/**
	 * <b>获得业务系统主页地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @return 业务系统主页地址
	 */
	@RequestMapping(value = "/getHomepage", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> getHomepage(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo);
			String homepage = ssoAccesser.homepage(misId);
			return Result.toResult(homepage);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getHomepage.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getHomepage", errorText, e);
		}
	}

	/**
	 * <b>获得在线用户人数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @return 在线用户人数
	 */
	@RequestMapping(value = "/onlineUserCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Long> onlineUserCount(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo);
			Long count = ssoAccesser.onlineUserCount(misId);
			return Result.toResult(count);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.onlineUserCount.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("onlineUserCount", errorText, e);
		}
	}

	/**
	 * <b>获得在线用户里列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页在线用户数据
	 */
	@RequestMapping(value = "/onlineUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<SSOUser>> onlineUsers(
			@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo);
			PageSet<SSOUser> ps = ssoAccesser.onlineUsers(misId, page, pageSize);
			return Result.toResult(ps);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.onlineUsers.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("onlineUsers", errorText, e);
		}
	}

	/**
	 * <b>获得门户入口系统信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * Map内容：<br>
	 * code-业务系统代码。<br>
	 * name-业务系统名称。<br>
	 * homepage-业务系统主页。<br>
	 * orgCode-组织机构代码。<br>
	 * orgName-组织机构名称。<br>
	 * remarks-备注<br>
	 * @param ticketNo 凭证号
	 * @return 门户入口系统信息
	 */
	@RequestMapping(value = "/getPortalMis", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Map<String, Object>> getPortalMis(
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo);
			Map<String, Object> map = ssoAccesser.getPortalMis();
			return Result.toResult(map);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getPortalMis.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getPortalMis", errorText, e);
		}
	}

	/**
	 * <b>获得凭证能访问的所有业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * Map内容：<br>
	 * code-业务系统代码。<br>
	 * name-业务系统名称。<br>
	 * homepage-业务系统主页。<br>
	 * orgCode-组织机构代码。<br>
	 * orgName-组织机构名称。<br>
	 * remarks-备注<br>
	 * @param ticketNo 凭证号
	 * @return 业务系统列表
	 */
	@RequestMapping(value = "/getAccessibleMis", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<Map<String, Object>>> getAccessibleMis(
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			SSOTicket ticket = ssoAccesser.getValidTicket(ticketNo);
			List<Map<String, Object>> list = ssoAccesser.getAccessibleMis(ticket.getOrganization(), ticket.getOwner());
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.getAccessibleMis.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("getAccessibleMis", errorText, e);
		}
	}

	/**
	 * <b>返回业务系统是否支持数据权限接口。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @param ticketNo 需要验证的凭证号
	 * @return 业务系统是否支持数据权限接口
	 */
	@RequestMapping(value = "/supportDataRole", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> supportDataRole(@RequestParam(required = true, name = "misid") String misId,
			@RequestParam(required = true, name = "ticket") String ticketNo) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			ssoAccesser.checkTicket(ticketNo);
			boolean ok = ssoAccesser.supportDataRole(misId);
			return Result.of(ok);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.mis.supportDataRole.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("supportDataRole", errorText, e);
		}
	}
}
