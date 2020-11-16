package org.quincy.rock.sso;

import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>登录凭证类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月20日 下午10:56:57</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOTicket extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6876112382420499659L;

	/**
	 * 凭证号(必需)。
	 */
	private String ticketNo;
	/**
	 * 凭证颁发时间(必需)。
	 */
	private long awardTime;
	/**
	 * 凭证存活秒数(必需)。
	 */
	private int maxAge;
	/**
	 * 机构标识。
	 */
	private String organization;
	/**
	 * 凭证拥有者。
	 */
	private String owner;
	/**
	 * 客户端IP地址。
	 */
	private String hostAddr;
	/**
	 * 自定义tag。
	 */
	private String tag;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tonken 凭证tonken
	 */
	public SSOTicket(String tonken) {
		int pos1 = tonken.indexOf(StringUtil.CHAR_z);
		this.setAwardTime(Long.parseLong(tonken.substring(0, pos1), 35));
		int pos2 = tonken.indexOf(StringUtil.CHAR_z, pos1 + 1);
		this.setMaxAge(Integer.parseInt(tonken.substring(pos1 + 1, pos2), 35));
		this.setTicketNo(tonken.substring(pos2 + 1));
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号
	 * @param awardTime 凭证颁发时间
	 * @param maxAge 凭证存活秒数
	 */
	public SSOTicket(String ticketNo, long awardTime, int maxAge) {
		this.setTicketNo(ticketNo);
		this.setAwardTime(awardTime);
		this.setMaxAge(maxAge);
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.BaseEntity#id()
	 */
	@Override
	public String id() {
		return getTicketNo();
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.BaseEntity#id(java.lang.Object)
	 */
	@Override
	public SSOTicket id(String id) {
		setTicketNo(id);
		return this;
	}

	/**
	 * <b>获得凭证号(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证号(必需)
	 */
	public String getTicketNo() {
		return ticketNo;
	}

	/**
	 * <b>设置凭证号(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号(必需)
	 */
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	/**
	 * <b>获得凭证颁发时间(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证颁发时间(必需)
	 */
	public long getAwardTime() {
		return awardTime;
	}

	/**
	 * <b>设置凭证颁发时间(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param awardTime 凭证颁发时间(必需)
	 */
	public void setAwardTime(long awardTime) {
		this.awardTime = awardTime;
	}

	/**
	 * <b>获得凭证存活秒数(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证存活秒数(必需)
	 */
	public int getMaxAge() {
		return maxAge;
	}

	/**
	 * <b>设置凭证存活秒数(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxAge 凭证存活秒数(必需)
	 */
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge < 0 ? Integer.MAX_VALUE : maxAge;
	}

	/**
	 * <b>获得凭证的机构标识(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证的机构标识(必需)
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * <b>设置凭证的机构标识(必需)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param organization 凭证的机构标识(必需)
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * <b>获得凭证拥有者(用户)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证拥有者(用户)
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * <b>设置凭证拥有者(用户)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param owner 凭证拥有者(用户)
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * <b>获得客户端IP地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 客户端IP地址
	 */
	public String getHostAddr() {
		return hostAddr;
	}

	/**
	 * <b>设置客户端IP地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param hostAddr 客户端IP地址
	 */
	public void setHostAddr(String hostAddr) {
		this.hostAddr = hostAddr;
	}

	/**
	 * <b>获得自定义tag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 自定义tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * <b>设置自定义tag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tag 自定义tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * <b>生成凭证tonken。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 凭证tonken。
	 * @return 凭证tonken
	 */
	public String getTonken() {
		StringBuilder sb = new StringBuilder();
		sb.append(Long.toString(getAwardTime(), 35));
		sb.append(StringUtil.CHAR_z);
		sb.append(Integer.toString(getMaxAge(), 35));
		sb.append(StringUtil.CHAR_z);
		sb.append(getTicketNo());
		return sb.toString();
	}

	/**
	 * <b>仅仅当前会话有效。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 仅仅当前会话有效
	 */
	public boolean onlySessionValid() {
		return this.getMaxAge() == Integer.MAX_VALUE;
	}

	/**
	 * <b>是否是有效凭证。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ssoTicket 凭证
	 * @return 是否是有效凭证
	 */
	public static boolean isValidTicket(SSOTicket ssoTicket) {
		if (!hasId(ssoTicket))
			return false;
		else {
			int maxAge = ssoTicket.getMaxAge();
			if (maxAge == Integer.MAX_VALUE)
				return true;
			else if (maxAge == 0)
				return false;
			else {
				long age = (System.currentTimeMillis() - ssoTicket.getAwardTime()) / 1000;
				return age <= maxAge;
			}
		}
	}
}
