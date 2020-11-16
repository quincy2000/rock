package org.quincy.rock.sso;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;

import org.quincy.rock.core.cache.HasTimestamp;
import org.quincy.rock.core.vo.PageSet;

/**
 * <b>SSO授权访问适配器。</b>
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
public interface SSOAdapter extends HasTimestamp {

	/**
	 * <b>使用凭证进行登录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticket 凭证
	 * @param hostAddr 客户端主机地址
	 * @return 登录是否成功。
	 */
	public boolean login(SSOTicket ticket, String hostAddr);

	/**
	 * <b>注销登录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不能抛出异常。
	 * @param discard 是否废弃凭证
	 * @param descr 注销描述
	 * @return 注销登录是否成功。
	 */
	public boolean logout(boolean discard, String descr);

	/**
	 * <b>是否支持数据权限角色。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 是否支持数据权限角色
	 * @throws SSOException
	 */
	public boolean supportDataRole(String misId) throws SSOException;

	/**
	 * <b>返回指定系统的数据权限角色提供者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @return 数据权限角色提供者
	 * @throws SSOException
	 */
	public DataRoleProvider dataRoleProvider(String misId) throws SSOException;

	/**
	 * <b>返回组织机构适配器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param org 组织机构代码
	 * @return 组织机构适配器
	 * @throws SSOException
	 */
	public OrgAdapter orgAdapter(String org) throws SSOException;

	/**
	 * <b>获得一页当前在线用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页在线用户集合
	 * @throws SSOException
	 */
	public PageSet<SSOUser> onlineUsers(int page, int pageSize) throws SSOException;

	/**
	 * <b>获得当前在线用户人数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前在线用户人数
	 * @throws SSOException
	 */
	public long onlineUserCount() throws SSOException;

	/**
	 * <b>主页网址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 主页网址
	 * @throws SSOException
	 */
	public String homepage() throws SSOException;

	/**
	 * <b>获得业务系统id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不能抛出异常。
	 * @return 业务系统id
	 */
	public String misId();

	/**
	 * <b>获得业务系统名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务系统名称
	 * @throws SSOException
	 */
	public String misName() throws SSOException;

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
	 * @return 门户入口系统信息，如果无法获得则返回null
	 */
	public Map<String, Object> portalMis();

	/**
	 * <b>获得当前用户能访问的所有业务系统。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * Map内容：<br>
	 * code-业务系统代码。<br>
	 * name-业务系统名称。<br>
	 * homepage-业务系统主页。<br>
	 * orgCode-组织机构代码。<br>
	 * orgName-组织机构名称。<br>
	 * remarks-备注<br>
	 * @return 业务系统列表
	 * @throws SSOException
	 */
	public Collection<Map<String, Object>> accessibleMis() throws SSOException;

	/**
	 * <b>根据通道标识获得通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 通道标识
	 * @return 通道
	 * @throws SSOException
	 */
	public Collection<SSORouteway> routeway(String key) throws SSOException;

	/**
	 * <b>获得系统参数集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * name=value。
	 * @return 系统参数集合
	 * @throws SSOException
	 */
	public Map<String, String> allParams() throws SSOException;

	/**
	 * <b>获得指定参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 参数key
	 * @return 参数值,如果没有则返回null
	 * @throws SSOException
	 */
	public String getParam(String key) throws SSOException;

	/**
	 * <b>设置参数值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只能设置系统维护参数。
	 * @param key 参数key
	 * @param value 参数值
	 * @throws SSOException
	 */
	public void setParam(String key, String value) throws SSOException;

	/**
	 * <b>获得一个或多个自动增长序列值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 序列名称
	 * @param count 返回的序列值个数
	 * @return 一个或多个自动增长序列值
	 * @throws SSOException
	 */
	public Collection<String> sequences(String name, int count) throws SSOException;

	/**
	 * <b>写入操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不能抛出异常。
	 * @param opInfo 操作信息
	 */
	public void writeOplog(OpInfo opInfo);

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
	 * @throws SSOException
	 */
	public PageSet<OpLog> oplogByCode(String opCode, Date beginDate, Date endDate, int page, int pageSize)
			throws SSOException;

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
	 * @throws SSOException
	 */
	public PageSet<OpLog> oplogByOwner(String owner, Date beginDate, Date endDate, int page, int pageSize)
			throws SSOException;

	/**
	 * <b>获得凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不能抛出异常。
	 * @return SSOTicket,如果没有登录则返回null
	 */
	public SSOTicket getTicket();

	/**
	 * <b>返回凭证tonken。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证tonken
	 */
	public default String tonken() {
		SSOTicket ticket = getTicket();
		return ticket == null ? null : ticket.getTonken();
	}

	/**
	 * <b>返回组织机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 组织机构标识
	 */
	public default String organization() {
		SSOTicket ticket = getTicket();
		return ticket == null ? null : ticket.getOrganization();
	}

	/**
	 * <b>获得授权用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 授权用户
	 * @throws SSOException
	 */
	public GrantUser getGrantUser() throws SSOException;

	/**
	 * <b>获得用户权限列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户权限列表
	 * @throws SSOException
	 */
	default Collection<SSOAction> getActions() throws SSOException {
		return getGrantUser().flatActions();
	}

	/**
	 * <b>获得用户服务范围列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户服务范围列表
	 * @throws SSOException
	 */
	default Collection<SSOScope> getScopes() throws SSOException {
		return getGrantUser().flatScopes();
	}

	/**
	 * <b>返回数据权限角色集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 数据权限角色集合
	 * @throws SSOException
	 */
	default Collection<SSODataRole> getDataRoles() throws SSOException {
		return getGrantUser().getDataRoles();
	}

	/**
	 * <b>适配器是否是有效的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不能抛出异常。
	 * @return 适配器是否是有效的
	 */
	default boolean isValid() {
		return getTicket() != null;
	}

	/**
	 * <b>关闭适配器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不能抛出异常。
	 */
	default void close() {
		logout(false, "SSOAdapter close");
	}
}
