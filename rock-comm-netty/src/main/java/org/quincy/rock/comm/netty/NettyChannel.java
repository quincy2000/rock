package org.quincy.rock.comm.netty;

import org.quincy.rock.comm.communicate.AbstractChannel;
import org.quincy.rock.core.lang.Getter;

import io.netty.channel.Channel;

/**
 * <b>NettyChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月1日 下午10:04:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class NettyChannel extends AbstractChannel implements INettyChannel {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1449218941077213843L;

	/**
	 * channel。
	 */
	private Getter<Channel> getter;

	/** 
	 * channel。
	 * @see org.quincy.rock.comm.netty.INettyChannel#channel()
	 */
	public final Channel channel() {
		return getter == null ? null : getter.get();
	}

	/** 
	 * setChannelGetter。
	 * @see org.quincy.rock.comm.netty.INettyChannel#setChannelGetter(org.quincy.rock.core.lang.Getter)
	 */
	@Override
	public void setChannelGetter(Getter<Channel> getter) {
		this.getter = getter;
	}

	/** 
	 * channelId。
	 * @see org.quincy.rock.comm.communicate.IChannel#channelId()
	 */
	@Override
	public Object channelId() {
		Channel ch = channel();
		return ch == null ? null : ch.id();
	}

	/** 
	 * addressInfo。
	 * @see org.quincy.rock.comm.communicate.AbstractChannel#addressInfo()
	 */
	@Override
	public String addressInfo() {
		Channel ch = channel();
		if (ch == null)
			return null;
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("channel_id:");
			sb.append(ch.id());
			sb.append(",remoteAddress:");
			sb.append(ch.remoteAddress());
			sb.append(",localAddress:");
			sb.append(ch.localAddress());
			return sb.toString();
		}
	}

}
