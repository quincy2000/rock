package org.quincy.rock.comm.mqtt;

import org.quincy.rock.comm.communicate.TerminalId;

/**
 * <b>MqttTerminal。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月9日 下午2:53:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MqttTerminal<TYPE, CODE> extends TerminalId<TYPE, CODE> implements MqttSendConfig {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * QTT消息服务质量。
	 */
	private int mqttQos = 1;
	/**
	 * 是否在服务器上保留消息。
	 */
	private boolean retained;

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
	 * advise。
	 * @see org.quincy.rock.comm.communicate.TerminalId#advise(org.quincy.rock.comm.communicate.TerminalId)
	 */
	@Override
	public void advise(TerminalId<TYPE, CODE> adviser) {
		super.advise(adviser);
		if (adviser instanceof MqttSendConfig) {
			MqttSendConfig cfg = (MqttSendConfig) adviser;
			this.setMqttQos(cfg.getMqttQos());
			this.setRetained(cfg.isRetained());
		}
	}
}
