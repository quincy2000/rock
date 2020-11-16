package org.quincy.rock.comm.communicate;

import java.util.EventListener;

/**
 * <b>通讯服务器监听接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月25日 下午2:27:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface CommunicateServerListener<UChannel> extends EventListener {
	/**
	 * <b>服务器开始时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param server 服务器实例
	 */
	public void serverStarted(CommunicateServer<UChannel> server);

	/**
	 * <b>服务器停止时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param server 服务器实例
	 */
	public void serverStopped(CommunicateServer<UChannel> server);
}
