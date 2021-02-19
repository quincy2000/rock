package org.quincy.rock.comm.netty.mqtt;

import org.quincy.rock.comm.netty.NettyChannelTransformer;

import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * <b>MqttChannelTransformer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月31日 上午12:07:46</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MqttChannelTransformer<UChannel extends IMqttChannel> extends NettyChannelTransformer<UChannel> {
	/** 
	 * transform。
	 * @see org.quincy.rock.comm.communicate.ChannelTransformer#transform(java.lang.Object, org.quincy.rock.comm.communicate.ChannelTransformer.STransformPoint)
	 */
	@Override
	public UChannel transform(Channel ch, STransformPoint point) {
		UChannel channel;
		String topic;
		switch (point) {
		case CHANNEL_READ:
		case CHANNEL_ERROR:
			channel = getNettyChannel(ch, NETTY_RECEIVE_CHANNEL_KEY, false);
			topic = ch.attr(MqttChannelHandler.RECEIVED_MESSAGE_TOPIC_KEY).get();
			channel.fromTopic(topic);
			break;
		case CHANNEL_WRITE:
			channel = getNettyChannel(ch, NETTY_SEND_CHANNEL_KEY, false);
			topic = channel.toTopic();
			ch.attr(MqttChannelHandler.SENDED_MESSAGE_TOPIC_KEY).set(topic);
			if (channel instanceof MqttSendConfig) {
				MqttSendConfig msc = (MqttSendConfig) channel;
				MqttQoS qos = msc.getMqttQos();
				if (qos != null)
					ch.attr(MqttChannelHandler.SENDED_MESSAGE_MQTT_QOS_KEY).set(qos);
				ch.attr(MqttChannelHandler.SENDED_MESSAGE_BURN_KEY)
						.set(msc.isBurnAfterRead() ? Boolean.TRUE : Boolean.FALSE);
			}
			break;
		default:
			channel = getNettyChannel(ch, NETTY_CHANNEL_KEY, false);
			break;
		}
		return channel;
	}
}
