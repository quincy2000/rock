package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * <b>MqttTerminalCommunicator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月4日 下午5:02:17</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MqttTerminalCommunicator<CODE, TYPE, UChannel extends MqttTerminalChannel<CODE, TYPE>>
		extends AbstractMqttCommunicator<UChannel> {

	public MqttTerminalCommunicator(String serverURI, String clientId, int corePoolSize,
			MqttClientPersistence persistence) {
		super(serverURI, clientId, corePoolSize, persistence);
	}

	public MqttTerminalCommunicator(String serverURI, String clientId, int corePoolSize) {
		super(serverURI, clientId, corePoolSize);
	}

	public MqttTerminalCommunicator(String serverURI, String clientId) {
		super(serverURI, clientId);
	}

	/**
	 * <b>订阅终端对应的Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法使用通道的toTopic方法获取终端Topic。
	 * @param terms 要订阅的终端Topic
	 * @return IMqttToken
	 * @throws MqttException
	 */
	public IMqttToken subscribe(MqttTerminal<?, ?>... terms) throws MqttException {
		int len = terms.length;
		String[] topics = new String[len];
		UChannel rootCh = this.getRootChannel();
		for (int i = 0; i < len; i++) {
			UChannel sendCh = rootCh.newSendChannel(terms[i]);
			topics[i] = sendCh.toTopic();
		}
		return this.subscribe(topics);
	}

	/**
	 * <b>取消订阅终端对应的Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法使用通道的toTopic方法获取终端Topic。
	 * @param terms 要取消订阅的终端Topic
	 * @return IMqttToken
	 * @throws MqttException
	 */
	public IMqttToken unsubscribe(MqttTerminal<?, ?>... terms) throws MqttException {
		int len = terms.length;
		String[] topics = new String[len];
		UChannel rootCh = this.getRootChannel();
		for (int i = 0; i < len; i++) {
			UChannel sendCh = rootCh.newSendChannel(terms[i]);
			topics[i] = sendCh.toTopic();
		}
		return this.unsubscribe(topics);
	}

	/**
	 * <b>发送数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param term 发送的终端
	 * @param data 发送的数据
	 * @param callback 回调监听
	 * @return IMqttToken
	 * @throws MqttException
	 */
	public IMqttToken sendData(MqttTerminal<?, ?> term, Object data, IMqttActionListener callback)
			throws MqttException {
		UChannel ch = this.getRootChannel();
		ch.newSendChannel(term);
		return this.sendData(ch, data, callback);
	}
}
