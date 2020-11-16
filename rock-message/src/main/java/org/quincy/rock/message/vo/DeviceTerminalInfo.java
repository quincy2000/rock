package org.quincy.rock.message.vo;

import org.quincy.rock.comm.communicate.DeviceProvider.DeviceInfo;
import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.util.CoreUtil;

/**
 * <b>设备终端信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年6月1日 下午2:14:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DeviceTerminalInfo extends TerminalInfo {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 534868288325730006L;

	/**
	 * 设备类型代码。
	 */
	private String typeCode;

	/**
	 * 设备类型名称。
	 */
	private String typeName;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DeviceTerminalInfo() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param term 终端id
	 */
	public DeviceTerminalInfo(TerminalId<?, ?> term) {
		super(term);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch 终端通道
	 */
	public DeviceTerminalInfo(TerminalChannel<?, ?> ch) {
		super(ch);
	}

	/**
	 * <b>获得设备类型代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 设备类型代码
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * <b>设置设备类型代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param typeCode 设备类型代码
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * <b>获得设备类型名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 设备类型名称
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * <b>设置设备类型名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param typeName 设备类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * <b>从设备信息复制属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param di 设备信息
	 * @return 设备终端信息
	 */
	public DeviceTerminalInfo fromDeviceInfo(DeviceInfo<?> di) {
		this.id(CoreUtil.toString(di.getId()));
		this.setCode(di.getCode());
		this.setTypeCode(di.getTypeCode());
		this.setTypeName(di.getTypeName());
		return this;
	}
}
