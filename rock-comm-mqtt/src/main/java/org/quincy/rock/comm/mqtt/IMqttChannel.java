package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.quincy.rock.comm.communicate.IChannel;

/**
 * <b>IMqttChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年1月29日 下午1:50:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface IMqttChannel extends IChannel, MqttSendConfig {

	/**
	 * <b>返回Mqtt客户端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Mqtt客户端
	 */
	public IMqttAsyncClient mqttClient();

	/**
	 * <b>设置Mqtt客户端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttClient Mqtt客户端
	 */
	public void mqttClient(IMqttAsyncClient mqttClient);

	/**
	 * <b>返回通道连接的服务器URI。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道连接的服务器URI
	 */
	public String serverURI();

	/**
	 * <b>设置通道连接的服务器URI。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverURI 通道连接的服务器URI
	 */
	public void serverURI(String serverURI);

	/**
	 * <b>解析topic字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic topic字符串
	 * @return IMqttChannel
	 */
	public IMqttChannel fromTopic(String topic);

	/**
	 * <b>生成topic字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return topic字符串
	 */
	public String toTopic();
}
