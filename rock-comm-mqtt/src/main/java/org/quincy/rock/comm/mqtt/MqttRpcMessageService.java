package org.quincy.rock.comm.mqtt;

import org.quincy.rock.comm.RpcMessageService;
import org.quincy.rock.comm.communicate.Adviser;

/**
 * <b>MqttRpcMessageService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月8日 下午1:38:45</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MqttRpcMessageService<K, UChannel extends IMqttChannel> extends RpcMessageService<K, UChannel> {
	/** 
	 * findSendChannel。
	 * @see org.quincy.rock.comm.AbstractMessageService#findSendChannel(java.lang.Object)
	 */
	@Override
	protected UChannel findSendChannel(Object terminalId) {
		UChannel channel = getTerminalChannelMapping().findChannel(terminalId);
		if (channel == null)
			channel = getMqttCommunicator().getRootChannel();
		return channel.newSendChannel(terminalId instanceof Adviser ? (Adviser) terminalId : null);
	}

	/** 
	 * getSendChannel。
	 * @see org.quincy.rock.comm.AbstractMessageService#getSendChannel(java.lang.Object)
	 */
	@Override
	protected UChannel getSendChannel(UChannel ch) {
		Object terminalId = getTerminalChannelMapping().findTerminal(ch);
		UChannel channel = terminalId == null ? null : getTerminalChannelMapping().findChannel(terminalId);
		if (channel == null)
			channel = getMqttCommunicator().getRootChannel();
		return channel.newSendChannel(ch instanceof Adviser ? (Adviser) ch : null);
	}

	/**
	 * <b>获得Mqtt通讯器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Mqtt通讯器
	 */
	public IMqttCommunicator<UChannel> getMqttCommunicator() {
		return (IMqttCommunicator) getCommunicator();
	}
}
