package org.quincy.rock.comm.netty.mqtt;

import org.quincy.rock.comm.DefaultMessageService;
import org.quincy.rock.comm.communicate.Adviser;
import org.quincy.rock.comm.communicate.Communicator;
import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalId;

/**
 * <b>MqttMessageService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月10日 上午11:36:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes"})
public class MqttMessageService<K, UChannel extends TerminalChannel<?, ?>> extends DefaultMessageService<K, UChannel> {
	/**
	 * 确保有可用的通道。
	 */
	private boolean ensureChannel;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MqttMessageService() {
		this(true);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ensureChannel 确保有可用的通道
	 */
	public MqttMessageService(boolean ensureChannel) {
		this.ensureChannel = ensureChannel;
	}

	/**
	 * <b>确保有可用的通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 确保有可用的通道
	 */
	public boolean isEnsureChannel() {
		return ensureChannel;
	}

	/** 
	 * getSendChannel。
	 * @see org.quincy.rock.comm.AbstractMessageService#getSendChannel(java.lang.Object)
	 */
	@Override
	protected UChannel getSendChannel(UChannel ch) {
		Object terminalId = getTerminalChannelMapping().findTerminal(ch);
		UChannel channel = terminalId == null ? null : getTerminalChannelMapping().findChannel(terminalId);
		if (channel == null && isEnsureChannel())
			channel = getMqttCommunicator().rootChannel();
		return channel == null ? null : newSendChannel(channel, ch);
	}

	/** 
	 * findSendChannel。
	 * @see org.quincy.rock.comm.AbstractMessageService#findSendChannel(java.lang.Object)
	 */
	@Override
	protected UChannel findSendChannel(Object terminalId) {
		TerminalId term = (TerminalId) terminalId;
		UChannel channel = getTerminalChannelMapping().findChannel(term);
		if (channel == null && isEnsureChannel())
			channel = getMqttCommunicator().rootChannel();
		return channel == null ? null : newSendChannel(channel, term);
	}

	/**
	 * <b>创建新的发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 原来的通道
	 * @param adviser 建议
	 * @return 新的发送通道
	 */
	protected UChannel newSendChannel(UChannel channel, Adviser adviser) {
		UChannel ch = channel.newSendChannel(adviser);
		if (ch instanceof MqttSendConfig && adviser instanceof MqttSendConfig) {
			MqttSendConfig src = (MqttSendConfig) adviser;
			MqttSendConfig dest = (MqttSendConfig) ch;
			dest.setBurnAfterRead(src.isBurnAfterRead());
			dest.setMqttQos(src.getMqttQos());
		}
		return ch;
	}

	/**
	 * <b>获得Mqtt通讯器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Mqtt通讯器
	 */
	public MqttCommunicator getMqttCommunicator() {
		Communicator communicator = getCommunicator();
		return (MqttCommunicator) communicator;
	}
}
