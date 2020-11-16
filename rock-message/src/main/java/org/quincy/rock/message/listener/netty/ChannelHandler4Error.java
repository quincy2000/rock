package org.quincy.rock.message.listener.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * <b>用来记录报文日志的ChannelHandler拦截器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年11月30日 下午2:13:08</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ChannelHandler4Error extends ChannelDuplexHandler {

	/**
	 * logger。
	 */
	public static final Logger logger = LoggerFactory.getLogger("rock.message.netty.error.log");

	/** 
	 * exceptionCaught。
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		try {
			logger.error(cause.getMessage(), cause);
		} catch (Exception e) {
		}
		ctx.fireExceptionCaught(cause);
	}

	/** 
	 * isSharable。
	 * @see io.netty.channel.ChannelHandlerAdapter#isSharable()
	 */
	@Override
	public boolean isSharable() {
		return true;
	}
}
