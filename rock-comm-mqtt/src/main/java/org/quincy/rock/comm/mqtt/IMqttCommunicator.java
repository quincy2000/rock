package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.quincy.rock.comm.communicate.CommunicateClient;
import org.quincy.rock.comm.communicate.CommunicateServer;

/**
 * <b>MQTT通讯器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年1月22日 下午4:44:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface IMqttCommunicator<UChannel extends IMqttChannel>
		extends CommunicateClient<UChannel>, CommunicateServer<UChannel> {
	/**
	 * <b>获得Mqtt客户端id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Mqtt客户端id
	 */
	public String getMqttClientId();

	/**
	 * <b>获得mqtt通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt通道
	 */
	public UChannel getMqttChannel();

	/**
	 * <b>连接指定主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param host 主机
	 * @return 通道
	 */
	public UChannel connect(MqttConnectOptions options);

	/**
	 * <b>订阅主题。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topics 要订阅的主题
	 * @return IMqttToken
	 * @throws MqttException
	 */
	public IMqttToken subscribe(String... topics) throws MqttException;

	/**
	 * <b>取消订阅主题。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topics 要取消订阅的主题
	 * @return IMqttToken
	 * @throws MqttException
	 */
	public IMqttToken unsubscribe(String... topics) throws MqttException;

	/**
	 * <b>发送数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 发送通道
	 * @param data 发送的数据
	 * @param callback 回调监听
	 * @return IMqttToken
	 * @throws MqttException
	 */
	public IMqttToken sendData(UChannel channel, Object data, IMqttActionListener callback) throws MqttException;

	/**
	 * <b>通讯连接是否是活动的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯连接是否是活动的
	 */
	public boolean isActive();

	/**
	 * <b>关闭连接通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void close();
}
