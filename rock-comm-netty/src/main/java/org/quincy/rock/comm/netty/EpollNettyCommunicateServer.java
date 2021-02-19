package org.quincy.rock.comm.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

/**
 * <b>EpollNettyCommunicatorServer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月14日 上午10:03:48</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class EpollNettyCommunicateServer<UChannel> extends NettyCommunicateServer<UChannel> {

	public EpollNettyCommunicateServer(int port, int maxActive, ChannelTransformer<UChannel> channelTransformer) {
		super(port, maxActive, channelTransformer);
	}

	public EpollNettyCommunicateServer(String host, int port, int maxActive,
			ChannelTransformer<UChannel> channelTransformer) {
		super(host, port, maxActive, channelTransformer);
	}

	/** 
	 * config。
	 * @see org.quincy.rock.comm.netty.NettyCommunicateServer#config(io.netty.bootstrap.ServerBootstrap)
	 */
	@Override
	protected ServerBootstrap config(ServerBootstrap bootstrap) {
		bootstrap.group(new EpollEventLoopGroup(), new EpollEventLoopGroup()).channel(EpollServerSocketChannel.class);
		return bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
	}
}
