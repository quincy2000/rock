package org.quincy.rock.comm.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <b>NioNettyCommunicatorClient。</b>
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
public class NioNettyCommunicateClient<UChannel> extends NettyCommunicateClient<UChannel> {

	public NioNettyCommunicateClient(String defaultHost, int defaultPort,
			ChannelTransformer<UChannel> channelTransformer) {
		super(defaultHost, defaultPort, channelTransformer);
	}

	public NioNettyCommunicateClient(String defaultHost, int defaultPort, int maxActive,
			ChannelTransformer<UChannel> channelTransformer) {
		super(defaultHost, defaultPort, maxActive, channelTransformer);
	}

	/** 
	 * config。
	 * @see org.quincy.rock.comm.netty.NettyCommunicateClient#config(io.netty.bootstrap.Bootstrap)
	 */
	@Override
	protected Bootstrap config(Bootstrap bootstrap) {
		return bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class);
	}
}
