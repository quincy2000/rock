package org.quincy.rock.sso.server.controller;

import java.util.Collection;
import java.util.List;

import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.DataRoleProvider;
import org.quincy.rock.sso.SSOAccesser;
import org.quincy.rock.sso.SSODataRole;
import org.quincy.rock.sso.util.SSOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>数据权限访问控制器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月19日 上午10:05:36</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Controller
@RequestMapping("dataRole")
public class DataRoleController extends SSOServerController {

	/**
	 * <b>返回数据权限是否支持叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @return 是否支持叶子节点
	 */
	@RequestMapping(value = "/supportLeaf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> supportLeaf(@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			boolean ok = provider.supportLeaf();
			return Result.of(ok);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.supportLeaf.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("supportLeaf", errorText, e);
		}
	}

	/**
	 * <b>返回所有的数据权限枝干节点列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @param tree 是否返回Tree形式的枝干节点列表
	 * @return 枝干节点列表
	 */
	@RequestMapping(value = "/findAllLimbs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODataRole>> findAllLimbs(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid,
			@RequestParam(required = true, name = "tree") boolean tree) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			List<SSODataRole> list = provider.findAllLimbs(tree);
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.findAllLimbs.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllLimbs", errorText, e);
		}
	}

	/**
	 * <b>返回所有的数据权限直接子枝干列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @param limbCode 枝干代码，如果为null则获得顶层枝干列表
	 * @return 枝干节点列表
	 */
	@RequestMapping(value = "/findAllChildren", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<List<SSODataRole>> findAllChildren(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid,
			@RequestParam(required = false, name = "limbCode") String limbCode) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			List<SSODataRole> list = provider.findAllChildren(limbCode);
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.findAllChildren.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllChildren", errorText, e);
		}
	}

	/**
	 * <b>通过代码获得指定的数据权限枝干节点信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @param code 枝干代码
	 * @return 枝干节点信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findLimb", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSODataRole> findLimb(@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid,
			@RequestParam(required = true, name = "code") String code) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			SSODataRole dept = provider.findLimb(code);
			return Result.toResult(dept);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.findLimb.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findLimb", errorText, e);
		}
	}

	/**
	 * <b>返回数据权限枝干中的所有叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @param limbCode  枝干代码，如果为null则返回所有叶子节点
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 枝干节点中的所有叶子节点列表
	 */
	@RequestMapping(value = "/findAllLeafs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<SSODataRole>> findAllLeafs(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid,
			@RequestParam(required = false, name = "limbCode") String limbCode,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			PageSet<SSODataRole> ps = provider.findAllLeafs(limbCode, page, pageSize);
			return Result.toResult(ps);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.findAllLeafs.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllLeafs", errorText, e);
		}
	}

	/**
	 * <b>通过代码获得数据权限叶子节点信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @param code 叶子节点代码
	 * @return 叶子节点信息,如果没有则返回null
	 */
	@RequestMapping(value = "/findLeaf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<SSODataRole> findLeaf(@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid,
			@RequestParam(required = true, name = "code") String code) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			SSODataRole dept = provider.findLeaf(code);
			return Result.toResult(dept);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.findLeaf.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findLeaf", errorText, e);
		}
	}

	/**
	 * <b>获得符合条件的所有节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 需要验证的凭证号
	 * @param misid 业务系统id
	 * @param limbCodes 枝干代码列表，可以为null
	 * @param leafCodes 叶子代码列表，可以为null
	 * @return 叶子节点列表
	 */
	@RequestMapping(value = "/findAllNodes", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<SSODataRole>> findAllNodes(
			@RequestParam(required = true, name = "ticket") String ticketNo,
			@RequestParam(required = true, name = "misid") String misid,
			@RequestParam(required = false, name = "limbCodes") String limbCodes,
			@RequestParam(required = false, name = "leafCodes") String leafCodes) {
		try {
			SSOAccesser ssoAccesser = this.getSsoAccesser();
			DataRoleProvider provider = ssoAccesser.getDataRoleProvider(misid, ticketNo);
			List<String> limbs = CoreUtil.toList(limbCodes, (ele) -> ele.toString().trim());
			List<String> leafs = CoreUtil.toList(leafCodes, (ele) -> ele.toString().trim());
			Collection<SSODataRole> list = provider.findAllNodes(limbs, leafs);
			return Result.toResult(list);
		} catch (Exception e) {
			String errorText = SSOUtils.getMessage("server.dataRole.findAllNodes.fail", e.getMessage());
			logger.warn(errorText, e);
			return Result.toResult("findAllNodes", errorText, e);
		}
	}
}
