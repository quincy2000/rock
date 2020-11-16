package org.quincy.rock.comm.netty.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * <b>MQTT报文发送配置接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 用来设置的发送报文mqtt属性。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月2日 下午6:36:37</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MqttSendConfig {

	/**
	 * <b>设置MQTT报文是否阅后即焚。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param burnAfterRead 是否阅后即焚
	 */
	public void setBurnAfterRead(boolean burnAfterRead);

	/**
	 * <b>MQTT报文是否阅后即焚。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否阅后即焚
	 */
	public boolean isBurnAfterRead();

	/**
	 * <b>设置MQTT报文发送质量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param qos MQTT报文发送质量
	 */
	public void setMqttQos(MqttQoS qos);

	/**
	 * <b>获得MQTT报文发送质量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return MQTT报文发送质量
	 */
	public MqttQoS getMqttQos();
}
