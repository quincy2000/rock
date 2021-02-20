package org.quincy.rock.comm.netty;

import io.netty.channel.Channel;

/**
 * <b>NettyTerminalChannelTransformer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月20日 上午10:16:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NettyTerminalChannelTransformer<TYPE, CODE>
		extends AbstractChannelTransformer<NettyTerminalChannel<TYPE, CODE>> {
	/**
	 * 是服务器。
	 */
	private boolean isServer;
	/**
	 * 本地终端类型。
	 */
	private TYPE localType;
	/**
	 * 本地终端代码。
	 */
	private CODE localCode;
	
	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param isServer 是服务器
	 * @param localType 本地终端类型
	 * @param localCode 本地终端代码
	 */
	public NettyTerminalChannelTransformer(boolean isServer, TYPE localType, CODE localCode) {
		this.isServer = isServer;
		this.localType = localType;
		this.localCode = localCode;
	}

	/** 
	 * createChannel。
	 * @see org.quincy.rock.comm.netty.AbstractChannelTransformer#createChannel(io.netty.channel.Channel)
	 */
	@Override
	protected NettyTerminalChannel<TYPE, CODE> createChannel(Channel ch) {
		NettyTerminalChannel<TYPE, CODE> channel=new NettyTerminalChannel<>().setChannelGetter(NettyUtil.createChannelGetter(ch));
		channel.setServerSide(isServer);
		channel.setLocalType(localType);
		channel.setLocalCode(localCode);
		return channel;
	}
}
