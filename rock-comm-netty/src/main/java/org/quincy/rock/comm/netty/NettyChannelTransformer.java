package org.quincy.rock.comm.netty;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * <b>AbstractChannelTransformer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月30日 下午11:12:12</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class NettyChannelTransformer<UChannel extends INettyChannel>
		implements ChannelTransformer<UChannel> {
	/**
	 * 存放纯净通道的Key。
	 */
	public static final AttributeKey<INettyChannel> NETTY_CHANNEL_KEY = AttributeKey.valueOf("nettyChannel");

	/**
	 * 存放接收通道的Key。
	 */
	public static final AttributeKey<INettyChannel> NETTY_RECEIVE_CHANNEL_KEY = AttributeKey
			.valueOf("nettyReceiveChannel");

	/**
	 * 存放发送通道的Key。
	 */
	public static final AttributeKey<INettyChannel> NETTY_SEND_CHANNEL_KEY = AttributeKey.valueOf("nettySendChannel");

	/** 
	 * transform。
	 * @see org.quincy.rock.comm.netty.ChannelTransformer#transform(java.lang.Object, org.quincy.rock.comm.netty.ChannelTransformer.UTransformPoint)
	 */
	@Override
	public Channel transform(UChannel uch, UTransformPoint point) {
		Channel channel = uch.channel();
		switch (point) {
		case SEND_DATA:
			if (!uch.isSendChannel())
				uch = uch.newSendChannel();
			channel.attr(NETTY_SEND_CHANNEL_KEY).set(uch);
			break;
		case CLOSE_CHANNEL:
		case ONLY_RETURN:
		default:
			break;
		}
		return channel;
	}

	/** 
	 * retrieveSendLock。
	 * @see org.quincy.rock.comm.netty.ChannelTransformer#retrieveSendLock(java.lang.Object)
	 */
	@Override
	public Object retrieveSendLock(UChannel uch) {
		Channel channel = uch.channel();
		Object lock = NettyUtil.retrieveWaiter(channel, NettyUtil.CHANNEL_SEND_LOCK_NAME, false);
		return lock;
	}

	/**
	 * <b>获得INettyChannel，如果没有就创建一个。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch Channel
	 * @param key AttributeKey
	 * @param nonPattern 一定是非模式通道
	 * @return INettyChannel
	 */
	@SuppressWarnings("unchecked")
	protected UChannel getNettyChannel(Channel ch, AttributeKey<INettyChannel> key, boolean nonPattern) {
		Attribute<INettyChannel> attr = ch.attr(key);
		UChannel channel = (UChannel) attr.get();
		if (channel == null) {
			channel = createChannel(ch);
			channel.nonPattern(nonPattern);
			attr.set(channel);
		}
		return channel;
	}

	/**
	 * <b>创建自定义通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 原始netty通道
	 * @return 自定义通道
	 */
	protected abstract UChannel createChannel(Channel source);
}
