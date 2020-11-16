package org.quincy.rock.sso.server.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.OrgAdapter;
import org.quincy.rock.sso.SSOAccesser;
import org.quincy.rock.sso.SSODept;
import org.quincy.rock.sso.SSOUser;
import org.quincy.rock.sso.util.SSOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>组织机构功能访问接口控制器。</b>
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
@RequestMapping("org")
public class OrgController extends SSOServerController {

	/**
	 * <b>获得指定组织机构的所有部门列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param tree 是否返回Tree形式的部门列表
	 * @return 部门列表
	 */
	@RequestMapping(value = "/findAllDepts", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODept>> findAllDepts(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = true, name = "tree") boolean tree) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			List<SSODept> list = oa.findAllDepts(tree);
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.findAllDepts.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllDepts", errorText, e);
		}
	}

	/**
	 * <b>获得指定组织机构的所有的直接下属部门。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param deptCode 部门代码,如果为null则获得顶层部门列表
	 * @return 直接下属部门列表
	 */
	@RequestMapping(value = "/findAllChildren", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODept>> findAllChildren(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = false, name = "deptCode") String deptCode) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			List<SSODept> list = oa.findAllChildren(deptCode);
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.findAllChildren.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllChildren", errorText, e);
		}
	}

	/**
	 * <b>通过代码获得指定组织机构的部门信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param code 部门代码
	 * @return 部门信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findDept", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSODept> findDept(@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = true, name = "code") String code) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			SSODept dept = oa.findDept(code);
			return Result.toResult(dept);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.findDept.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findDept", errorText, e);
		}
	}

	/**
	 * <b>获得指定组织机构的部门中的所有用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param deptCode 部门代码(多个代码使用逗号分隔)，如果为空则返回所有用户
	 * @return 部门中的所有用户列表
	 */
	@RequestMapping(value = "/findAllUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSOUser>> findAllUsers(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = false, name = "deptCode") String deptCode) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			List<SSOUser> list = oa.findAllUsers(StringUtils.split(deptCode, StringUtil.CHAR_COMMA));
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.findAllUsers.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllUsers", errorText, e);
		}
	}

	/**
	 * <b>获得指定组织机构的符合条件的所有用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param codes 用户代码列表
	 * @return 用户列表
	 */
	@RequestMapping(value = "/findAllUsersIn", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSOUser>> findAllUsersIn(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = true, name = "codes") String codes) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			List<SSOUser> list = oa.findAllUsers(CoreUtil.toList(codes, (ele) -> ele.toString().trim()));
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.findAllUsersIn.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllUsersIn", errorText, e);
		}
	}

	/**
	 * <b>通过代码获得指定组织机构的用户信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param code 用户代码
	 * @return 用户信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findUser", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSOUser> findUser(@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = true, name = "code") String code) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			SSOUser user = oa.findUser(code);
			return Result.toResult(user);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.findUser.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findUser", errorText, e);
		}
	}

	/**
	 * <b>修改用户密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param org 组织机构标识
	 * @param code 用户代码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 是否修改成功
	 */
	@RequestMapping(value = "/changePassword", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> changePassword(@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "org") String org,
			@RequestParam(required = true, name = "code") String code,
			@RequestParam(required = true, name = "oldPwd") String oldPwd,
			@RequestParam(required = true, name = "newPwd") String newPwd) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			OrgAdapter oa = ssoAccesser.getOrgAdapter(org, ticketNo);
			Boolean ok = oa.changePassword(code, oldPwd, newPwd);
			return Result.toResult(ok);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.org.changePassword.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("changePassword", errorText, e);
		}
	}
}
