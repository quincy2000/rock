package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

/**
 * <b>操作日志类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月19日 下午3:50:37</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class OpLog extends OpInfo {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4376291945687694132L;

	/**
	 * 业务系统id。
	 */
	private String misId;
	/**
	 * 凭证号。
	 */
	private String ticketNo;
	/**
	 * 机构标识。
	 */
	private String organization;
	/**
	 * 操作者登录名。
	 */
	private String logonName;
	/**
	 * 操作者姓名。
	 */
	private String name;

	/**
	 * <b>获得业务系统id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 业务系统id
	 */
	public String getMisId() {
		return misId;
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
	 * <b>获得凭证号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 凭证号
	 */
	public String getTicketNo() {
		return ticketNo;
	}

	/**
	 * <b>设置凭证号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ticketNo 凭证号
	 */
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	/**
	 * <b>获得机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 机构标识
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * <b>设置机构标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param organization 机构标识
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * <b>获得操作者登录名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作者登录名
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * <b>设置操作者登录名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param logonName 操作者登录名
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	/**
	 * <b>获得操作者姓名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作者姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>设置操作者姓名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 操作者姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.sso.OpInfo#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("misId", "ticketNo", "organization", "logonName", "name"));
		return cs;
	}

	/**
	 * <b>创建OpLog。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param misId 业务系统id
	 * @param ticketNo 凭证号
	 * @param organization 机构标识
	 * @param logonName 登录名
	 * @param name 姓名
	 * @param opInfo 操作信息
	 * @return 操作日志
	 */
	public static OpLog of(String misId, String ticketNo, String organization, String logonName, String name,
			OpInfo opInfo) {
		OpLog opLog = new OpLog();
		opLog.setMisId(misId);
		opLog.setTicketNo(ticketNo);
		opLog.setOrganization(organization);
		opLog.setLogonName(logonName);
		opLog.setName(name);
		opLog.setHostAddr(opInfo.getHostAddr());
		opLog.setOpCode(opInfo.getOpCode());
		opLog.setOpName(opInfo.getOpName());
		opLog.setOpTime(opInfo.getOpTime());
		opLog.setOpDescr(opInfo.getOpDescr());
		return opLog;
	}
}
