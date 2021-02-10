package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.quincy.rock.comm.communicate.AbstractChannel;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>MqttChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月1日 下午4:26:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MqttChannel extends AbstractChannel implements IMqttChannel {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3197277635634898980L;

	/**
	 * mqttClient。
	 */
	private IMqttAsyncClient mqttClient;
	/**
	 * 通道连接的服务器URI。
	 */
	private String serverURI;
	/**
	 * QTT消息服务质量。
	 */
	private int mqttQos = 1;
	/**
	 * 是否在服务器上保留消息。
	 */
	private boolean retained;
	/**
	 * 主题Topic。
	 */
	private String topic;
	/**
	 * 远程id。
	 */
	private String remoteId;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MqttChannel() {
	}

	/** 
	 * mqttClient。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#mqttClient()
	 */
	@Override
	public final IMqttAsyncClient mqttClient() {
		return this.mqttClient;
	}

	/** 
	 * mqttClient。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#mqttClient(org.eclipse.paho.client.mqttv3.IMqttAsyncClient)
	 */
	@Override
	public final void mqttClient(IMqttAsyncClient mqttClient) {
		this.mqttClient = mqttClient;
	}

	/** 
	 * serverURI。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#serverURI()
	 */
	@Override
	public final String serverURI() {
		return this.serverURI;
	}

	/** 
	 * serverURI。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#serverURI(java.lang.String)
	 */
	@Override
	public final void serverURI(String serverURI) {
		this.serverURI = serverURI;
	}

	/** 
	 * isRetained。
	 * @see org.quincy.rock.comm.mqtt.MqttSendConfig#isRetained()
	 */
	@Override
	public final boolean isRetained() {
		return this.retained;
	}

	/** 
	 * setRetained。
	 * @see org.quincy.rock.comm.mqtt.MqttSendConfig#setRetained(boolean)
	 */
	@Override
	public final void setRetained(boolean retained) {
		this.retained = retained;
	}

	/** 
	 * getMqttQos。
	 * @see org.quincy.rock.comm.mqtt.MqttSendConfig#getMqttQos()
	 */
	@Override
	public final int getMqttQos() {
		return this.mqttQos;
	}

	/** 
	 * setMqttQos。
	 * @see org.quincy.rock.comm.mqtt.MqttSendConfig#setMqttQos(int)
	 */
	@Override
	public final void setMqttQos(int qos) {
		this.mqttQos = qos;
	}

	/**
	 * <b>获得主题Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 主题Topic
	 */
	public final String topic() {
		return toTopic();
	}

	/**
	 * <b>设置主题Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic 主题Topic
	 */
	public final void topic(String topic) {
		this.fromTopic(topic);
	}

	/**
	 * <b>获得远程唯一id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 远程唯一id
	 */
	public final String remoteId() {
		return StringUtil.isBlank(remoteId) ? topic : remoteId;
	}

	/**
	 * <b>设置远程唯一id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param remoteId 远程唯一id
	 */
	public final void remoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	/** 
	 * channelId。
	 * @see org.quincy.rock.comm.communicate.IChannel#channelId()
	 */
	@Override
	public Object channelId() {
		return remoteId();
	}

	/** 
	 * fromTopic。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#fromTopic(java.lang.String)
	 */
	@Override
	public IMqttChannel fromTopic(String topic) {
		this.topic = topic;
		return this;
	}

	/** 
	 * toTopic。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#toTopic()
	 */
	@Override
	public String toTopic() {
		return topic;
	}

	/** 
	 * isPattern。
	 * @see org.quincy.rock.comm.communicate.AbstractChannel#isPattern()
	 */
	@Override
	public boolean isPattern() {
		return !nonPattern() && StringUtil.isBlank(remoteId());
	}

	/** 
	 * isMatched。
	 * @see org.quincy.rock.core.util.HasPattern#isMatched(java.lang.Object)
	 */
	@Override
	public boolean isMatched(Object obj) {
		String remoteId = this.remoteId();
		if (StringUtil.isBlank(remoteId))
			return true;
		else if (obj instanceof MqttChannel) {
			return remoteId.equals(((MqttChannel) obj).remoteId());
		} else if (obj instanceof String) {
			return remoteId.equals(obj);
		} else
			return false;
	}
}
