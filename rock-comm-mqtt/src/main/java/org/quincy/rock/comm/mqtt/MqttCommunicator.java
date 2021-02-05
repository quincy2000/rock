package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClientPersistence;

/**
 * <b>MqttCommunicator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月4日 下午4:42:01</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MqttCommunicator<UChannel extends MqttChannel> extends AbstractMqttCommunicator<UChannel> {

	public MqttCommunicator(String serverURI, String clientId, int corePoolSize, MqttClientPersistence persistence) {
		super(serverURI, clientId, corePoolSize, persistence);
	}

	public MqttCommunicator(String serverURI, String clientId, int corePoolSize) {
		super(serverURI, clientId, corePoolSize);
	}

	public MqttCommunicator(String serverURI, String clientId) {
		super(serverURI, clientId);
	}

}
