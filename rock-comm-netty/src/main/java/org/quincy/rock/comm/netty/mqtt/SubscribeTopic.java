package org.quincy.rock.comm.netty.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * <b>SubscribeTopic。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月31日 上午12:21:01</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SubscribeTopic {
	/**
	 * topic。
	 */
	private String topic;
	/**
	 * mqttQoS。
	 */
	private MqttQoS mqttQoS = MqttQoS.AT_LEAST_ONCE;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public SubscribeTopic() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic 订阅Topic
	 */
	public SubscribeTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * <b>获得订阅Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 订阅Topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * <b>设置订阅Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic 订阅Topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * <b>获得MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return MqttQoS
	 */
	public MqttQoS getMqttQoS() {
		return mqttQoS;
	}

	/**
	 * <b>设置MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttQoS MqttQoS
	 */
	public void setMqttQoS(MqttQoS mqttQoS) {
		this.mqttQoS = mqttQoS;
	}

	/**
	 * <b>获得MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return MqttQoS
	 */
	public String getMqttQosString() {
		return mqttQoS.name();
	}

	/**
	 * <b>设置MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttQoS MqttQoS
	 */
	public void setMqttQosString(String mqttQoS) {
		this.mqttQoS = MqttQoS.valueOf(mqttQoS.toUpperCase());
	}

	/**
	 * <b>of。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic
	 * @return
	 */
	public static SubscribeTopic of(String topic) {
		return new SubscribeTopic(topic);
	}

	/**
	 * <b>of。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic
	 * @param qos
	 * @return
	 */
	public static SubscribeTopic of(String topic, MqttQoS qos) {
		SubscribeTopic st = new SubscribeTopic(topic);
		st.setMqttQoS(qos);
		return st;
	}
}
