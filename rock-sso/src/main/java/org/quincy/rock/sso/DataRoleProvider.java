package org.quincy.rock.sso;

import java.util.Collection;
import java.util.List;

import org.quincy.rock.core.vo.PageSet;

/**
 * <b>数据权限角色提供者接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月18日 下午3:29:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface DataRoleProvider {
	/**
	 * <b>返回业务系统id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 返回业务系统id
	 */
	public String getMisId();

	/**
	 * <b>是否支持叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否支持叶子节点
	 */
	public boolean supportLeaf();

	/**
	 * <b>返回所有的枝干列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tree 是否返回Tree形式的枝干列表
	 * @return 枝干列表，如果没有枝干则返回空列表
	 */
	public List<SSODataRole> findAllLimbs(boolean tree);

	/**
	 * <b>返回所有的直接子枝干列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param limbCode 枝干代码,如果为null则获得顶层枝干列表
	 * @return 子枝干列表，如果没有枝干则返回空列表
	 */
	public List<SSODataRole> findAllChildren(String limbCode);

	/**
	 * <b>通过代码获得枝干信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 枝干代码
	 * @return 枝干信息,如果没有则返回null
	 */
	public SSODataRole findLimb(String code);

	/**
	 * <b>获得枝干中的所有叶子节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param limbCode 枝干代码，如果为null则返回所有叶子节点
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 枝干节点中的所有叶子节点列表，如果没有叶子则返回空列表
	 */
	public PageSet<SSODataRole> findAllLeafs(String limbCode, int page, int pageSize);

	/**
	 * <b>通过代码获得叶子节点信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 叶子节点代码
	 * @return 叶子节点信息,如果没有则返回null
	 */
	public SSODataRole findLeaf(String code);

	/**
	 * <b>获得符合条件的所有节点。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用户登录时登录服务器会根据用户数据权限调用该方法获取用户明细数据权限列表返回给用户客户端。
	 * 返回的结果可是是树型结构列表，也可以是一般列表。
	 * 开发者可以根据需求决定返回节点列表形式和数据描述形式。<br>
	 * 枝干代码说明：<br>
	 * 枝干代码可以添加匹配符后缀，代码和后缀用下划线分隔，例如:010203_1*。<br>
	 * 1*：匹配枝干下所有叶子节点<br>
	 * 2*：匹配枝干下所有后代枝干节点<br>
	 * 3*：匹配枝干下所有枝干和叶子节点<br>
	 * @param limbCodes 枝干代码列表,如果为null代表拥有所有数据权限并忽略leafCodes参数。
	 * @param leafCodes 叶子代码列表,如果为空代表没有明确的叶子权限。
	 * @return 节点列表,如果没有则返回空列表
	 */
	public Collection<SSODataRole> findAllNodes(Iterable<String> limbCodes, Iterable<String> leafCodes);
}
