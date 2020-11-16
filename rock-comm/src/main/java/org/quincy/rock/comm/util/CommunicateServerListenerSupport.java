package org.quincy.rock.comm.util;

import java.util.List;
import java.util.Vector;

import org.quincy.rock.comm.communicate.CommunicateServer;
import org.quincy.rock.comm.communicate.CommunicateServerListener;

/**
 * <b>CommunicateServerListenerSupport。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月25日 下午3:05:56</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CommunicateServerListenerSupport<UChannel> {
	/**
	 * listeners。
	 */
	private List<CommunicateServerListener<UChannel>> listeners = new Vector<>();

	/**
	 * <b>添加通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 监听器
	 */
	public void addCommunicateServerListener(CommunicateServerListener<UChannel> listener) {
		listeners.add(listener);
	}

	/**
	 * <b>移走通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 监听器
	 */
	public void removeCommunicateServerListener(CommunicateServerListener<UChannel> listener) {
		listeners.remove(listener);
	}

	/**
	 * <b>移除所有的通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void removeAllCommunicateServerListener() {
		listeners.clear();
	}

	/**
	 * <b>触发服务器启动事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param server 服务器实例
	 */
	public void fireServerStartedEvent(CommunicateServer<UChannel> server) {
		for (CommunicateServerListener<UChannel> listener : listeners) {
			listener.serverStarted(server);
		}
	}

	/**
	 * <b>触发服务器停止事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param server 服务器实例
	 */
	public void fireServerStoppedEvent(CommunicateServer<UChannel> server) {
		for (CommunicateServerListener<UChannel> listener : listeners) {
			listener.serverStopped(server);
		}
	}
}
