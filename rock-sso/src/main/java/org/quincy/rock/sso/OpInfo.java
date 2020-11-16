package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

import org.quincy.rock.core.vo.BaseEntity;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>操作信息类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2008-10-18 下午07:25:39</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class OpInfo extends BaseEntity<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 2412711330260257955L;

	/**
	 * 操作标识。
	 */
	private String opCode;
	/**
	 * 操作名称
	 */
	private String opName;
	/**
	 * 操作时间。
	 */
	private long opTime = System.currentTimeMillis();
	/**
	 * 操作说明。
	 */
	private String opDescr;
	/**
	 * 客户机地址。
	 */
	private String hostAddr;

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return opCode;
	}

	/**
	 * <b>id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id
	 * @return
	 */
	@Override
	public Vo<String> id(String id) {
		this.opCode = id;
		return this;
	}

	/**
	 * <b>获得操作标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作标识
	 */
	public String getOpCode() {
		return opCode;
	}

	/**
	 * <b>设置操作标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opCode 操作标识
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	/**
	 * <b>获得操作名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作名称
	 */
	public String getOpName() {
		return opName;
	}

	/**
	 * <b>设置操作名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opName 操作名称
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}

	/**
	 * <b>获得操作时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作时间
	 */
	public long getOpTime() {
		return opTime;
	}

	/**
	 * <b>设置操作时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opTime 操作时间
	 */
	public void setOpTime(long opTime) {
		this.opTime = opTime;
	}

	/**
	 * <b>获得操作说明。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作说明
	 */
	public String getOpDescr() {
		return opDescr;
	}

	/**
	 * <b>设置操作说明。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opDescr 操作说明
	 */
	public void setOpDescr(String opDescr) {
		this.opDescr = opDescr;
	}

	/**
	 * <b>获得客户机地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 客户机地址
	 */
	public String getHostAddr() {
		return hostAddr;
	}

	/**
	 * <b>设置客户机地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param hostAddr 客户机地址
	 */
	public void setHostAddr(String hostAddr) {
		this.hostAddr = hostAddr;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.core.sso.ClientInfo#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("opCode", "opName"));
		return cs;
	}

	/**
	 * <b>创建操作信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opCode 操作代码
	 * @param opName 操作名称
	 * @param opTime 操作时间
	 * @param opDescr 操作说明
	 * @param hostAddr 客户机地址
	 * @return OpInfo
	 */
	public static OpInfo of(String opCode, String opName, long opTime, String opDescr, String hostAddr) {
		OpInfo opInfo = new OpInfo();
		opInfo.setOpCode(opCode);
		opInfo.setOpName(opName);
		opInfo.setOpTime(opTime);
		opInfo.setOpDescr(opDescr);
		opInfo.setHostAddr(hostAddr);
		return opInfo;
	}
}