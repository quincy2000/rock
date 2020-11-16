package org.quincy.rock.comm.communicate;

/**
 * <b>通讯服务器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月30日 上午9:26:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface CommunicateServer<UChannel> extends Communicator<UChannel> {

	/**
	 * <b>添加通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 监听器
	 */
	public void addCommunicateServerListener(CommunicateServerListener<UChannel> listener);
	
	/**
	 * <b>移走通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 监听器
	 */
	public void removeCommunicateServerListener(CommunicateServerListener<UChannel> listener);
	
	/**
	 * <b>移除所有的通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void removeAllCommunicateServerListener();
	
	/**
	 * <b>获得服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器主机
	 */
	public String getHost();

	/**
	 * <b>获得端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 端口号
	 */
	public int getPort();

	/**
	 * <b>启动通讯服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void start();

	/**
	 * <b>停止通讯服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void stop();

	/**
	 * <b>重新启动通讯服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void reset();

	/**
	 * <b>返回通讯服务是否正在运行。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯服务是否正在运行
	 */
	public boolean isRunning();
}
