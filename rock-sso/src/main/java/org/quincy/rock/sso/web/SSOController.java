package org.quincy.rock.sso.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.DataRoleProvider;
import org.quincy.rock.sso.OpInfo;
import org.quincy.rock.sso.OpLog;
import org.quincy.rock.sso.OrgAdapter;
import org.quincy.rock.sso.SSOAdapter;
import org.quincy.rock.sso.SSODataRole;
import org.quincy.rock.sso.SSODept;
import org.quincy.rock.sso.SSORouteway;
import org.quincy.rock.sso.SSOTicket;
import org.quincy.rock.sso.SSOUser;
import org.quincy.rock.sso.util.SSOUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>SSOController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 实现和SSOHttpServlet一样的功能，在spring环境下使用。
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
@RequestMapping("sso")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SSOController {
	/**
	 * logger。
	 */
	private static Logger logger = RockUtil.getLogger(SSOController.class);

	/**
	 * 是否缓存查询结果。
	 */
	private boolean cache;

	/**
	 * <b>是否缓存查询结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否缓存查询结果
	 */
	public boolean isCache() {
		return cache;
	}

	/**
	 * <b>设置是否缓存查询结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cache 是否缓存查询结果
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}

	/**
	 * <b>当前用户是否是系统管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前用户是否是系统管理员
	 */
	@RequestMapping(value = "/isAdmin", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> isAdmin(HttpSession session) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.of(adapter.getGrantUser().isAdmin());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("isAdmin", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>当前用户是否是超级管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前用户是否是超级管理员
	 */
	@RequestMapping(value = "/isSuperAdmin", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> isSuperAdmin(HttpSession session) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.of(adapter.getGrantUser().isSuperAdmin());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("isSuperAdmin", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>当前用户是否是SSO系统管理员。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前用户是否是SSO系统管理员
	 */
	@RequestMapping(value = "/isSsoAdmin", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> isSsoAdmin(HttpSession session) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.of(adapter.getGrantUser().isSsoAdmin());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("isSsoAdmin", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得当前用户类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户类型
	 */
	@RequestMapping(value = "/userType", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> userType(HttpSession session) {
		Result<Integer> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.getGrantUser().getUserType());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("userType", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得当前用户信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前用户信息
	 */
	@RequestMapping(value = "/user", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSOUser> user(HttpSession session) {
		Result<SSOUser> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			SSOUser user = new SSOUser(adapter.getGrantUser());
			user.setTag(adapter.organization());
			result = Result.toResult(user);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("user", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得当前组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前组织机构标识
	 */
	@RequestMapping(value = "/organization", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> organization(HttpSession session) {
		Result<String> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.organization());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("organization", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得业务系统id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务系统id
	 */
	@RequestMapping(value = "/misid", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> misid(HttpSession session) {
		Result<String> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.misId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("misid", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得业务系统名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务系统名称
	 */
	@RequestMapping(value = "/misName", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> misName(HttpSession session) {
		Result<String> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.misName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("misName", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得业务系统主页地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务系统主页地址
	 */
	@RequestMapping(value = "/homepage", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> homepage(HttpSession session) {
		Result<String> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.homepage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("homepage", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得当前使用的tonken。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前使用的tonken
	 */
	@RequestMapping(value = "/tonken", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> tonken(HttpSession session) {
		Result<String> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.tonken());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("tonken", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得门户入口系统信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 门户入口系统信息
	 */
	@RequestMapping(value = "/portalMis", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Map<String, Object>> portalMis(HttpSession session) {
		Result<Map<String, Object>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.portalMis());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("portalMis", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得当前用户能访问的所有业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务系统列表
	 */
	@RequestMapping(value = "/accessibleMis", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<Map<String, Object>>> accessibleMis(HttpSession session) {
		Result<Collection<Map<String, Object>>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.accessibleMis());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("accessibleMis", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得在线用户人数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 在线用户人数
	 */
	@RequestMapping(value = "/onlineUserCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Long> onlineUserCount(HttpSession session) {
		Result<Long> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			return Result.toResult(adapter.onlineUserCount());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("onlineUserCount", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得在线用户里列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页在线用户数据
	 */
	@RequestMapping(value = "/onlineUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<SSOUser>> onlineUsers(@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize, HttpSession session) {
		Result<PageSet<SSOUser>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			return Result.toResult(adapter.onlineUsers(page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("onlineUsers", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>写操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opCode 操作代码
	 * @param opName 操作名称
	 * @param opDescr 操作说明
	 * @return 是否成功
	 */
	@RequestMapping(value = "/writeOplog", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> writeOplog(@RequestParam(required = true, name = "opCode") String opCode,
			@RequestParam(required = true, name = "opName") String opName,
			@RequestParam(required = true, name = "opDescr") String opDescr, HttpServletRequest request) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(request.getSession());
			adapter.writeOplog(
					OpInfo.of(opCode, opName, System.currentTimeMillis(), opDescr, SSOUtils.getRemoteAddr(request)));
			result = Result.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("writeOplog", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>返回某个操作的操作日志列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opCode 操作代码
	 * @param beginDate 时间段开始日期
	 * @param endDate 时间段结束日期
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页操作日志列表
	 */
	@RequestMapping(value = "/oplogByCode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<OpLog>> oplogByCode(
			@RequestParam(required = true, name = "opCode") String opCode,
			@RequestParam(required = true, name = "beginDate") String beginDate,
			@RequestParam(required = true, name = "endDate") String endDate,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize, HttpSession session) {
		Result<PageSet<OpLog>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			PageSet<OpLog> ps = adapter.oplogByCode(opCode, DateUtil.toDate(beginDate), DateUtil.toDate(endDate), page,
					pageSize);
			result = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("oplogByCode", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>返回某个操作用户的操作日志列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 用户代码
	 * @param beginDate 时间段开始日期
	 * @param endDate 时间段结束日期
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页操作日志列表
	 */
	@RequestMapping(value = "/oplogByOwner", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<OpLog>> oplogByOwner(
			@RequestParam(required = true, name = "owner") String owner,
			@RequestParam(required = true, name = "beginDate") String beginDate,
			@RequestParam(required = true, name = "endDate") String endDate,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize, HttpSession session) {
		Result<PageSet<OpLog>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			PageSet<OpLog> ps = adapter.oplogByOwner(owner, DateUtil.toDate(beginDate), DateUtil.toDate(endDate), page,
					pageSize);
			result = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("oplogByOwner", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得一个或多个自动增长序列值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 序列名称
	 * @param count 返回的序列值个数
	 * @return 一个或多个自动增长序列值
	 */
	@RequestMapping(value = "/sequences", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<String>> sequences(@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "count") int count, HttpSession session) {
		Result<Collection<String>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.sequences(name, count));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("sequences", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>设置参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只能设置系统维护参数。
	 * @param key 参数key
	 * @param value 参数值
	 * @return 是否成功
	 */
	@RequestMapping(value = "/setParam", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> setParam(@RequestParam(required = true, name = "key") String key,
			@RequestParam(required = false, name = "value") String value, HttpSession session) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			adapter.setParam(key, value);
			result = Result.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("setParam", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 参数key
	 * @return 参数值
	 */
	@RequestMapping(value = "/getParam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<String> getParam(@RequestParam(required = true, name = "key") String key,
			HttpSession session) {
		Result<String> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.getParam(key));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("getParam", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得所有参数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 参数列表
	 */
	@RequestMapping(value = "/allParam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Map<String, String>> allParam(HttpSession session) {
		Result<Map<String, String>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.allParams());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("allParam", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>根据通道标识获得通道列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 通道标识
	 * @param isAll 是否获得完整通道（包括未授权部分）
	 * @return 通道列表
	 */
	@RequestMapping(value = "/routeways", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<SSORouteway>> getRouteways(
			@RequestParam(required = true, name = "key") String key,
			@RequestParam(required = true, name = "isAll", defaultValue = "true") boolean isAll, HttpSession session) {
		//缓存键
		String cacheKey = StringUtil
				.concat(SSOUtils.WEB_SSO_CACHE_PREFIX, "routeways_", key, "_", String.valueOf(isAll)).toString();
		Result<Collection<SSORouteway>> result = (Result) session.getAttribute(cacheKey);
		if (result == null) {
			try {
				SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
				Collection<SSORouteway> list = isAll ? adapter.routeway(key)
						: SSOUtils.filterRouteway(adapter.routeway(key), adapter.getActions());
				result = Result.toResult(list);
				if (isCache())
					session.setAttribute(cacheKey, result);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = Result.toResult("routeways", e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * <b>获得所有部门列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param tree 是否返回Tree形式的部门列表
	 * @return 部门列表
	 */
	@RequestMapping(value = "/findAllDepts", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODept>> findAllDepts(@RequestParam(required = false, name = "org") String org,
			@RequestParam(required = true, name = "tree") boolean tree, HttpSession session) {
		SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
		OrgAdapter oa = adapter.orgAdapter(org == null ? adapter.organization() : org);
		Result<List<SSODept>> result;
		try {
			result = Result.toResult(oa.findAllDepts(tree));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findAllDepts", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得所有的直接下属部门。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param code 部门代码,如果为null则获得顶层部门列表
	 * @return 直接下属部门列表
	 */
	@RequestMapping(value = "/findSubDepts", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODept>> findSubDepts(@RequestParam(required = false, name = "org") String org,
			@RequestParam(required = false, name = "code") String code, HttpSession session) {
		SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
		OrgAdapter oa = adapter.orgAdapter(org == null ? adapter.organization() : org);
		Result<List<SSODept>> result;
		try {
			result = Result.toResult(oa.findAllChildren(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findSubDepts", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>通过代码获得部门信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param code 部门代码
	 * @return 部门信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findDept", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSODept> findDept(@RequestParam(required = false, name = "org") String org,
			@RequestParam(required = true, name = "code") String code, HttpSession session) {
		SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
		OrgAdapter oa = adapter.orgAdapter(org == null ? adapter.organization() : org);
		Result<SSODept> result;
		try {
			result = Result.toResult(oa.findDept(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findDept", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>获得部门中的所有用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param code 部门代码(多个代码使用逗号分隔)，如果为空则返回所有用户
	 * @return 用户列表
	 */
	@RequestMapping(value = "/findAllUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSOUser>> findAllUsers(@RequestParam(required = false, name = "org") String org,
			@RequestParam(required = false, name = "code") String code, HttpSession session) {
		SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
		OrgAdapter oa = adapter.orgAdapter(org == null ? adapter.organization() : org);
		Result<List<SSOUser>> result;
		try {
			result = Result.toResult(oa.findAllUsers(StringUtils.split(code, StringUtil.CHAR_COMMA)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findAllUsers", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>通过代码获得用户信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param code 用户代码
	 * @return 用户信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findUser", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSOUser> findUser(@RequestParam(required = false, name = "org") String org,
			@RequestParam(required = true, name = "code") String code, HttpSession session) {
		SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
		OrgAdapter oa = adapter.orgAdapter(org == null ? adapter.organization() : org);
		Result<SSOUser> result;
		try {
			result = Result.toResult(oa.findUser(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findUser", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>修改用户密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构标识
	 * @param code 用户代码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 是否修改成功
	 */
	@RequestMapping(value = "/changePassword", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> changePassword(@RequestParam(required = false, name = "org") String org,
			@RequestParam(required = false, name = "code") String code,
			@RequestParam(required = true, name = "oldPwd") String oldPwd,
			@RequestParam(required = true, name = "newPwd") String newPwd, HttpSession session) {
		SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
		SSOTicket ticket = adapter.getTicket();
		OrgAdapter oa = adapter.orgAdapter(org == null ? ticket.getOrganization() : org);
		Result<Boolean> result;
		try {
			result = Result.toResult(oa.changePassword(code == null ? ticket.getOwner() : code, oldPwd, newPwd));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("changePassword", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>当前会话时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 会话开始时间。
	 * @return 当前会话时间戳
	 */
	@RequestMapping(value = "/timestamp", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Long> timestamp(HttpSession session) {
		Result<Long> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.toResult(adapter.timestamp());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("timestamp", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>注销当前会话。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param discard 是否废弃凭证
	 * @return 是否注销成功
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> logout(
			@RequestParam(required = true, name = "discard", defaultValue = "false") boolean discard,
			HttpServletRequest req, HttpServletResponse resp) {
		Result<Boolean> result;
		HttpSession session = req.getSession();
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			SSOUtils.clearTicketFromCookie(adapter.organization(), resp);
			result = Result.of(adapter.logout(discard, "User logout"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("logout", e.getMessage(), e);
		} finally {
			session.invalidate();
		}
		return result;
	}

	/**
	 * <b>业务系统是否支持数据权限。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @return 是否支持数据权限
	 */
	@RequestMapping(value = "/supportDataRole", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> supportDataRole(@RequestParam(required = false, name = "misid") String misid,
			HttpSession session) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			result = Result.of(adapter.supportDataRole(misid == null ? adapter.misId() : misid));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("supportDataRole", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>业务系统数据权限是否支持叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @return 数据权限是否支持叶子节点
	 */
	@RequestMapping(value = "/supportLeaf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> supportLeaf(@RequestParam(required = false, name = "misid") String misid,
			HttpSession session) {
		Result<Boolean> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			DataRoleProvider provider = adapter.dataRoleProvider(misid == null ? adapter.misId() : misid);
			result = Result.of(provider.supportLeaf());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("supportLeaf", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>返回所有的数据权限枝干列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @param tree 是否返回Tree形式的枝干列表
	 * @return 数据权限枝干列表
	 */
	@RequestMapping(value = "/findAllLimbs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODataRole>> findAllLimbs(
			@RequestParam(required = false, name = "misid") String misid,
			@RequestParam(required = true, name = "tree") boolean tree, HttpSession session) {
		Result<List<SSODataRole>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			DataRoleProvider provider = adapter.dataRoleProvider(misid == null ? adapter.misId() : misid);
			result = Result.toResult(provider.findAllLimbs(tree));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findAllLimbs", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>返回所有的数据权限直接子枝干列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @param code 枝干代码,如果为null则获得顶层枝干列表
	 * @return 数据权限子枝干列表
	 */
	@RequestMapping(value = "/findSubLimbs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODataRole>> findSubLimbs(
			@RequestParam(required = false, name = "misid") String misid,
			@RequestParam(required = false, name = "code") String code, HttpSession session) {
		Result<List<SSODataRole>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			DataRoleProvider provider = adapter.dataRoleProvider(misid == null ? adapter.misId() : misid);
			result = Result.toResult(provider.findAllChildren(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findSubLimbs", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>通过代码获得数据权限枝干节点信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @param code 枝干代码
	 * @return 数据权限枝干节点信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findLimb", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSODataRole> findLimb(@RequestParam(required = false, name = "misid") String misid,
			@RequestParam(required = true, name = "code") String code, HttpSession session) {
		Result<SSODataRole> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			DataRoleProvider provider = adapter.dataRoleProvider(misid == null ? adapter.misId() : misid);
			result = Result.toResult(provider.findLimb(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findLimb", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>返回数据权限枝干中的所有叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @param code 枝干代码，如果为null则返回所有叶子节点
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 数据权限叶子节点列表
	 */
	@RequestMapping(value = "/findAllLeafs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<SSODataRole>> findAllLeafs(
			@RequestParam(required = false, name = "misid") String misid,
			@RequestParam(required = false, name = "code") String code,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize, HttpSession session) {
		Result<PageSet<SSODataRole>> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			DataRoleProvider provider = adapter.dataRoleProvider(misid == null ? adapter.misId() : misid);
			result = Result.toResult(provider.findAllLeafs(code, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findAllLeafs", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <b>通过代码获得数据权限叶子节点信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misid 业务系统id
	 * @param code 叶子代码
	 * @return 数据权限叶子节点信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findLeaf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSODataRole> findLeaf(@RequestParam(required = false, name = "misid") String misid,
			@RequestParam(required = true, name = "code") String code, HttpSession session) {
		Result<SSODataRole> result;
		try {
			SSOAdapter adapter = SSOUtils.getSSOAdapter(session);
			DataRoleProvider provider = adapter.dataRoleProvider(misid == null ? adapter.misId() : misid);
			result = Result.toResult(provider.findLeaf(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = Result.toResult("findLeaf", e.getMessage(), e);
		}
		return result;
	}
}
