package org.quincy.rock.comm.netty;

import io.netty.channel.Channel;

/**
 * <b>DefaultNettyChannelTransformer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月4日 下午7:25:42</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DefaultNettyChannelTransformer extends NettyChannelTransformer<INettyChannel> {
	/**
	 * 用户自定义通道bean实例。
	 */
	private INettyChannel channelBean;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DefaultNettyChannelTransformer() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelBean 用户自定义通道bean实例。
	 */
	public DefaultNettyChannelTransformer(INettyChannel channelBean) {
		this.channelBean = channelBean;
	}

	/**
	 * <b>获得用户自定义通道bean实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户自定义通道bean实例
	 */
	public INettyChannel getChannelBean() {
		if (channelBean == null) {
			channelBean = new NettyChannel();
		}
		return channelBean;
	}

	/**
	 * <b>设置用户自定义通道bean实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelBean 用户自定义通道bean实例
	 */
	public void setChannelBean(INettyChannel channelBean) {
		this.channelBean = channelBean;
	}

	/** 
	 * createChannel。
	 * @see org.quincy.rock.comm.netty.AbstractNettyChannelTransformer#createChannel(io.netty.channel.Channel)
	 */
	@Override
	protected INettyChannel createChannel(final Channel source) {
		INettyChannel ch = getChannelBean().cloneMe();
		ch.setChannelGetter(NettyUtil.createChannelGetter(source));
		return ch;
	}
}
