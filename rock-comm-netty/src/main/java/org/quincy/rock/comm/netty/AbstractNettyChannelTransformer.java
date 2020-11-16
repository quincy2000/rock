package org.quincy.rock.comm.netty;

import io.netty.channel.Channel;

/**
 * <b>AbstractNettyChannelTransformer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 适用于一个物理通道和逻辑通道一一对应的情况。
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
public abstract class AbstractNettyChannelTransformer<UChannel extends INettyChannel>
		extends AbstractChannelTransformer<UChannel> {

	/** 
	 * transform。
	 * @see org.quincy.rock.comm.communicator.ChannelTransformer#transform(java.lang.Object, org.quincy.rock.comm.communicator.ChannelTransformer.STransformPoint)
	 */
	@Override
	public UChannel transform(Channel ch, STransformPoint point) {
		UChannel channel;
		switch (point) {
		case CHANNEL_INACTIVE:
		case CHANNEL_READ:
		case CHANNEL_ERROR:
			channel = getNettyChannel(ch, NETTY_RECEIVE_CHANNEL_KEY, true);
			break;
		case CHANNEL_WRITE:
			channel = getNettyChannel(ch, NETTY_SEND_CHANNEL_KEY, false);
			break;
		default:
			channel = getNettyChannel(ch, NETTY_CHANNEL_KEY, false);
			break;
		}
		return channel;
	}
}
