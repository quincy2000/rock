package org.quincy.rock.comm.mqtt;

/**
 * <b>MQTT消息发送时配置参数。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月10日 上午10:59:22</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MqttSendConfig {
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
}
