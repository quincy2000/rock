package org.quincy.rock.comm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.quincy.rock.comm.communicate.AbstractTerminalChannel;

/**
 * <b>MqttTerminalChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月1日 下午4:28:10</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class MqttTerminalChannel<TYPE, CODE> extends AbstractTerminalChannel<TYPE, CODE>
		implements IMqttTerminalChannel<TYPE, CODE> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6849975758706373983L;

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
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MqttTerminalChannel() {
	}

	/** 
	 * local。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#local()
	 */
	@Override
	public MqttTerminal<TYPE, CODE> local() {
		return (MqttTerminal) super.local();
	}

	/** 
	 * remote。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#remote()
	 */
	@Override
	public MqttTerminal<TYPE, CODE> remote() {
		return (MqttTerminal) super.remote();
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
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#isRetained()
	 */
	@Override
	public final boolean isRetained() {
		return this.retained;
	}

	/** 
	 * setRetained。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#setRetained(boolean)
	 */
	@Override
	public final void setRetained(boolean retained) {
		this.setRetained(retained);
	}

	/** 
	 * getMqttQos。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#getMqttQos()
	 */
	@Override
	public final int getMqttQos() {
		return this.mqttQos;
	}

	/** 
	 * setMqttQos。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#setMqttQos(int)
	 */
	@Override
	public final void setMqttQos(int qos) {
		this.mqttQos = qos;
	}
}
