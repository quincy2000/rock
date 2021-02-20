package org.quincy.rock.comm.netty;

import io.netty.channel.Channel;

/**
 * <b>NettyChannelTransformer。</b>
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
public class NettyChannelTransformer extends AbstractChannelTransformer<NettyChannel> {

	/** 
	 * createChannel。
	 * @see org.quincy.rock.comm.netty.AbstractChannelTransformer#createChannel(io.netty.channel.Channel)
	 */
	@Override
	protected NettyChannel createChannel(Channel ch) {		
		return new NettyChannel();
	}
}
