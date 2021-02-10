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
public interface IMqttChannel extends IChannel {

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
	 * <b>是否在服务器上保留消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否在服务器上保留消息
	 */
	public boolean isRetained();

	/**
	 * <b>是否在服务器上保留消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param retained 是否在服务器上保留消息
	 */
	public void setRetained(boolean retained);

	/**
	 * <b>获得MQTT消息服务质量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 消息服务质量(发送消息时需要):
	 * 0:最多一次，不需要确认(质量最差)。
	 * 1:最少一次,需要确认，收不到确认就会重复发送。
	 * 2:只发送一次，确保每个消息只收到一次，是最慢也是最安全的等级(质量最好)。
	 * @return MQTT消息服务质量
	 */
	public int getMqttQos();

	/**
	 * <b>设置MQTT消息服务质量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 消息服务质量(发送消息时需要):
	 * 0:最多一次，不需要确认(质量最差)。
	 * 1:最少一次,需要确认，收不到确认就会重复发送。
	 * 2:只发送一次，确保每个消息只收到一次，是最慢也是最安全的等级(质量最好)。
	 * @param qos MQTT消息服务质量
	 */
	public void setMqttQos(int qos);

	/**
	 * <b>解析topic字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic topic字符串
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
