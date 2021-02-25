package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>连接选项。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>mex20</td><td>2021年2月23日 下午5:36:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ConnectOptions extends MqttConnectOptions {
	/**
	 * 发送消息超时。
	 */
	private int timeout = CONNECTION_TIMEOUT_DEFAULT;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public ConnectOptions() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userName 用户名
	 * @param password 密码
	 */
	public ConnectOptions(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password.toCharArray());
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param willTopic 遗嘱topic
	 * @param willMessage 遗嘱消息串
	 * @param willQos 消息服务质量(0, 1 or 2)
	 * @param willRetained 是否在服务器上保留消息
	 */
	public ConnectOptions(String willTopic, String willMessage, int willQos, boolean willRetained) {
		this.setWill(willTopic, willMessage.getBytes(StringUtil.UTF_8), willQos, willRetained);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userName 用户名
	 * @param password 密码
	 * @param willTopic 遗嘱topic
	 * @param willMessage 遗嘱消息串
	 * @param willQos 消息服务质量(0, 1 or 2)
	 * @param willRetained 是否在服务器上保留消息
	 */
	public ConnectOptions(String userName, String password, String willTopic, String willMessage, int willQos,
			boolean willRetained) {
		this.setUserName(userName);
		this.setPassword(password.toCharArray());
		this.setWill(willTopic, willMessage.getBytes(StringUtil.UTF_8), willQos, willRetained);
	}

	/**
	 * <b>获得发送消息超时。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 发送消息超时
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <b>设置发送消息超时。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timeout 发送消息超时
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
