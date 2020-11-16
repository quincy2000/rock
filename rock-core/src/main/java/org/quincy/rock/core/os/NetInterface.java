package org.quincy.rock.core.os;

import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.Vo;

import oshi.hardware.NetworkIF;

/**
 * <b>网卡信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月14日 下午4:56:11</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NetInterface extends Vo<String> {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6335235753459024937L;

	private final NetworkIF net;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param net NetworkIF
	 */
	public NetInterface(NetworkIF net) {
		this.net = net;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return getName();
	}

	/**
	 * <b>获得网卡名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 网卡名称
	 */
	public String getName() {
		return net.getName();
	}

	/**
	 * <b>获得IP地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return IP地址
	 */
	public String getAddress() {
		return net.getIPv4addr()[0];
	}

	/**
	 * <b>获得描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 描述
	 */
	public String getDescr() {
		return net.getDisplayName();
	}

	/**
	 * <b>获得mac地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mac地址
	 */
	public String getMacAddress() {
		return net.getMacaddr();
	}

	/**
	 * <b>获得网卡速度(M)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 网卡速度(M)
	 */
	public int getSeed() {
		return (int) (net.getSpeed() / IOUtil.IO_BINARY_UNIT_M);
	}

	/**
	 * <b>获得接收数据字节数(K)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 接收数据字节数(K)
	 */
	public long getRxBytes() {
		return net.getBytesRecv() / IOUtil.IO_BINARY_UNIT_K;
	}

	/**
	 * <b>获得发送数据字节数(K)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 发送数据字节数(K)
	 */
	public long getTxBytes() {
		return net.getBytesSent() / IOUtil.IO_BINARY_UNIT_K;
	}
}
