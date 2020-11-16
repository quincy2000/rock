package org.quincy.rock.comm.netty.mqtt;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import org.quincy.rock.comm.RpcMessageService;
import org.quincy.rock.comm.communicate.Adviser;
import org.quincy.rock.comm.communicate.Communicator;
import org.quincy.rock.comm.communicate.PatternChannelMapping;
import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.util.HasPattern;

/**
 * <b>MqttRpcMessageService。</b>
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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MqttRpcMessageService<K, UChannel extends TerminalChannel<?, ?>> extends RpcMessageService<K, UChannel> {
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
	public MqttRpcMessageService() {
		this(true);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param ensureChannel 确保有可用的通道。
	 */
	public MqttRpcMessageService(boolean ensureChannel) {
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
	 * findSendChannelByExample。
	 * @see org.quincy.rock.comm.AbstractMessageService#findSendChannelByExample(java.lang.Object)
	 */
	@Override
	protected UChannel findSendChannelByExample(UChannel ch) {
		UChannel channel = getTerminalChannelMapping().findChannelByExample(ch);
		if (channel == null && isEnsureChannel())
			channel = getMqttCommunicator().rootChannel();
		return channel == null ? null : newSendChannel(channel, ch, ch.isPattern());
	}

	/** 
	 * findSendChannel。
	 * @see org.quincy.rock.comm.AbstractMessageService#findSendChannel(java.lang.Object)
	 */
	@Override
	protected UChannel findSendChannel(Object terminalId) {
		TerminalId<Short, String> term = (TerminalId) terminalId;
		UChannel channel = getTerminalChannelMapping().findChannel(term);
		if (channel == null && isEnsureChannel())
			channel = getMqttCommunicator().rootChannel();
		return channel == null ? null : newSendChannel(channel, term, term.isPattern());
	}

	/** 
	 * findSendChannels。
	 * @see org.quincy.rock.comm.AbstractMessageService#findSendChannels(org.quincy.rock.core.util.HasPattern)
	 */
	@Override
	protected Collection<UChannel> findSendChannels(HasPattern pattern) {
		Adviser adviser = (pattern instanceof Adviser) ? (Adviser) pattern : null;
		Collection<UChannel> channels = ((PatternChannelMapping) this.getTerminalChannelMapping())
				.findChannels(pattern);
		Collection<UChannel> list = new TreeSet<>();
		for (UChannel channel : channels) {
			UChannel ch = newSendChannel(channel, adviser, pattern.isPattern());
			list.add(ch);
		}
		if (list.isEmpty() && isEnsureChannel()) {
			//如果没有通道则自己创建一个MQTT发送通道
			UChannel ch = getMqttCommunicator().rootChannel();
			list = Arrays.asList(newSendChannel(ch, adviser, pattern.isPattern()));
		}
		return list;
	}

	/**
	 * <b>创新新的发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 原来的通道
	 * @param adviser 建议
	 * @param pattern 是否时模式匹配
	 * @return 新的发送通道
	 */
	protected UChannel newSendChannel(UChannel channel, Adviser adviser, boolean pattern) {
		return channel.newSendChannel(adviser, true);
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
