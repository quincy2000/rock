package org.quincy.rock.comm.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;

/**
 * <b>EpollNettyCommunicatorClient。</b>
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
public class EpollNettyCommunicateClient<UChannel> extends NettyCommunicateClient<UChannel> {

	public EpollNettyCommunicateClient(String defaultHost, int defaultPort,
			ChannelTransformer<UChannel> channelTransformer) {
		super(defaultHost, defaultPort, channelTransformer);
	}

	public EpollNettyCommunicateClient(String defaultHost, int defaultPort, int maxActive,
			ChannelTransformer<UChannel> channelTransformer) {
		super(defaultHost, defaultPort, maxActive, channelTransformer);
	}

	/** 
	 * config。
	 * @see org.quincy.rock.comm.netty.NettyCommunicateClient#config(io.netty.bootstrap.Bootstrap)
	 */
	@Override
	protected Bootstrap config(Bootstrap bootstrap) {
		return bootstrap.group(new EpollEventLoopGroup()).channel(EpollSocketChannel.class);
	}
}
