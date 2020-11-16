package org.quincy.rock.comm.communicate;

/**
 * <b>CommunicatorClient。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月30日 上午9:38:01</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface CommunicateClient<UChannel> extends Communicator<UChannel> {

	/**
	 * <b>连接默认主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道
	 */
	public UChannel connect();

	/**
	 * <b>连接指定主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param host 主机
	 * @return 通道
	 */
	public UChannel connect(String host);

	/**
	 * <b>连接指定主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param host 主机
	 * @param port 端口号
	 * @return 通道
	 */
	public UChannel connect(String host, int port);
}
