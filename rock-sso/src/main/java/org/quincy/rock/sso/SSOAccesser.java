package org.quincy.rock.sso;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.quincy.rock.core.vo.PageSet;

/**
 * <b>SSO数据访问器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月20日 下午10:43:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface SSOAccesser {

	/**
	 * <b>是否是有效的业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 是否是有效的业务系统
	 * @throws SSOException
	 */
	public boolean isValidMis(String misId) throws SSOException;

	/**
	 * <b>检查业务系统有效性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id，如果不合法则抛出异常
	 * @throws SSOException
	 */
	default void checkMis(String misId) throws SSOException {
		if (!isValidMis(misId))
			throw new SSOException("Mis invalid:" + misId);
	}

	/**
	 * <b>获得业务系统使用的组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 组织机构标识，如果没有则抛出异常
	 * @throws SSOException
	 */
	public String getOrganization(String misId) throws SSOException;

	/**
	 * <b>获得业务系统使用的组织机构适配器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 组织机构适配器，如果没有则抛出异常
	 * @throws SSOException
	 */
	public OrgAdapter getOrgAdapter(String misId) throws SSOException;

	/**
	 * <b>获得组织机构适配器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只有合法凭证才可以获得组织机构适配器。
	 * @param organization 组织机构标识
	 * @param ticketNo 需要检查的凭证号
	 * @return 组织机构适配器，如果没有则抛出异常
	 * @throws SSOException
	 */
	public OrgAdapter getOrgAdapter(String organization, String ticketNo) throws SSOException;

	/**
	 * <b>返回指定系统的数据权限角色提供者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 需要检查的凭证号
	 * @return 数据权限角色提供者，如果没有则抛出异常
	 * @throws SSOException
	 */
	public DataRoleProvider getDataRoleProvider(String misId, String ticketNo) throws SSOException;

	/**
	 * <b>是否支持数据权限。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 是否支持数据权限
	 * @throws SSOException
	 */
	public boolean supportDataRole(String misId) throws SSOException;

	/**
	 * <b>使用用户名密码进行登录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param organization 组织机构标识
	 * @param logonName 登录名
	 * @param password 密码
	 * @param hostAddr 主机地址
	 * @param maxAge 登录凭证有效秒数
	 * @return 凭证SSOTicket
	 * @throws SSOException
	 */
	public SSOTicket login(String organization, String logonName, String password, String hostAddr, int maxAge)
			throws SSOException;

	/**
	 * <b>获得有效凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号
	 * @return 凭证SSOTicket，如果没有则返回null
	 * @throws SSOException
	 */
	public SSOTicket findValidTicket(String ticketNo) throws SSOException;

	/**
	 * <b>获得有效凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号
	 * @return 凭证SSOTicket，如果没有则抛出异常
	 * @throws SSOException
	 */
	default SSOTicket getValidTicket(String ticketNo) throws SSOException {
		SSOTicket ticket = findValidTicket(ticketNo);
		if (ticket == null)
			throw new SSOException("No valid ticket were found:" + ticketNo);
		return ticket;
	}

	/**
	 * <b>是否是合法凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号
	 * @return 凭证是否合法
	 * @throws SSOException
	 */
	default boolean isValidTicket(String ticketNo) throws SSOException {
		return findValidTicket(ticketNo) != null;
	}

	/**
	 * <b>凭证合法性检查。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号，如果不合法则抛出异常
	 * @throws SSOException
	 */
	default void checkTicket(String ticketNo) throws SSOException {
		if (!isValidTicket(ticketNo))
			throw new SSOException("Ticket invalid:" + ticketNo);
	}

	/**
	 * <b>凭证合法性检查。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 检查凭证是否能够合法访问指定的业务系统。
	 * @param ticketNo 凭证号，如果不合法则抛出异常
	 * @param misId 业务系统id，如果凭证不能访问该系统则抛出异常
	 * @throws SSOException
	 */
	default void checkTicket(String ticketNo, String misId) throws SSOException {
		SSOTicket ticket = findValidTicket(ticketNo);
		if (ticket == null)
			throw new SSOException("Ticket invalid:" + ticketNo);
		boolean ok = canAccessMis(ticket.getOrganization(), ticket.getOwner(), misId);
		if (!ok)
			throw new SSOException("Ticket invalid:" + ticket + " for " + misId);
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
	 * @return 门户入口系统信息
	 * @throws SSOException
	 */
	public Map<String, Object> getPortalMis() throws SSOException;

	/**
	 * <b>获得用户能访问的所有业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * Map内容：<br>
	 * code-业务系统代码。<br>
	 * name-业务系统名称。<br>
	 * homepage-业务系统主页。<br>
	 * orgCode-组织机构代码。<br>
	 * orgName-组织机构名称。<br>
	 * remarks-备注<br>
	 * @param organization 组织机构标识
	 * @param userId 用户id
	 * @return 业务系统列表
	 * @throws SSOException
	 */
	public List<Map<String, Object>> getAccessibleMis(String organization, String userId) throws SSOException;

	/**
	 * <b>用户是否可以访问该业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param organization 组织机构标识
	 * @param userId 用户id
	 * @param misId 业务系统id
	 * @return 用户是否可以访问该业务系统
	 * @throws SSOException
	 */
	public boolean canAccessMis(String organization, String userId, String misId) throws SSOException;

	/**
	 * <b>使用凭证登录业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param hostAddr 客户端主机地址
	 * @param extraAddr 额外主机地址
	 * @param mark 私有标记
	 * @return 用户授权信息
	 * @throws SSOException
	 */
	public GrantUser loginMis(String misId, String ticketNo, String hostAddr, String extraAddr, String mark)
			throws SSOException;

	/**
	 * <b>注销登录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法不需要抛出异常。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param discard 是否废弃凭证
	 * @param mark 私有标记
	 * @param descr 注销描述
	 */
	public void logoutMis(String misId, String ticketNo, boolean discard, String mark, String descr);

	/**
	 * <b>获得当前在线凭证人数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前在线凭证人数
	 * @throws SSOException
	 */
	public long onlineTicketCount() throws SSOException;

	/**
	 * <b>获得一页当前在线用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 在线用户集合
	 * @throws SSOException
	 */
	public PageSet<SSOUser> onlineUsers(String misId, int page, int pageSize) throws SSOException;

	/**
	 * <b>获得当前在线用户人数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 当前在线用户人数
	 * @throws SSOException
	 */
	public long onlineUserCount(String misId) throws SSOException;

	/**
	 * <b>主页网址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 主页网址
	 * @throws SSOException
	 */
	public String homepage(String misId) throws SSOException;

	/**
	 * <b>返回业务系统名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 主页网址
	 * @throws SSOException
	 */
	public String misName(String misId) throws SSOException;

	/**
	 * <b>根据通道标识获得通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param key 通道标识
	 * @return 通道
	 * @throws SSOException
	 */
	public Collection<SSORouteway> routeway(String misId, String key) throws SSOException;

	/**
	 * <b>获得系统参数集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 系统参数集合
	 * @throws SSOException
	 */
	public Map<String, String> allParams(String misId) throws SSOException;

	/**
	 * <b>获得指定参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param code 参数key
	 * @return 参数值，如果没有则返回null
	 * @throws SSOException
	 */
	public String getParam(String misId, String key) throws SSOException;

	/**
	 * <b>设置参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只能设置系统维护参数。
	 * @param misId 业务系统id
	 * @param key 参数key
	 * @param value 参数值
	 * @throws SSOException
	 */
	public void setParam(String misId, String key, String value) throws SSOException;

	/**
	 * <b>获得一个或多个自动增长序列值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param name 序列名称
	 * @param count 返回的序列值个数
	 * @return 一个或多个自动增长序列值
	 * @throws SSOException
	 */
	public Collection<String> sequences(String misId, String name, int count) throws SSOException;

	/**
	 * <b>返回某个操作的操作日志列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param opCode 操作代码
	 * @param beginDate 时间段开始日期
	 * @param endDate 时间段结束日期
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页操作日志列表
	 * @throws SSOException
	 */
	public PageSet<OpLog> oplogByCode(String misId, String opCode, Date beginDate, Date endDate, int page, int pageSize)
			throws SSOException;

	/**
	 * <b>返回某个操作用户的操作日志列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param owner 用户代码
	 * @param beginDate 时间段开始日期
	 * @param endDate 时间段结束日期
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页操作日志列表
	 * @throws SSOException
	 */
	public PageSet<OpLog> oplogByOwner(String misId, String owner, Date beginDate, Date endDate, int page, int pageSize)
			throws SSOException;

	/**
	 * <b>写入操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opLog 操作日志
	 * @param extraAddr 额外地址
	 * @throws SSOException
	 */
	public void writeOplog(OpLog opLog, String extraAddr) throws SSOException;
}
