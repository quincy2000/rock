package org.quincy.rock.comm.netty.codec;

import java.net.SocketAddress;

import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.lang.Recorder;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;

/**
 * <b>日志记录通道句柄。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年9月21日 上午9:28:44</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Sharable
public class LoggingChannelHandler extends ChannelHandlerAdapter
		implements ChannelInboundHandler, ChannelOutboundHandler {
	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * <b>设置日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder 日志记录器
	 */
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	/** 
	 * handlerAdded。
	 * @see io.netty.channel.ChannelHandlerAdapter#handlerAdded(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-handlerAdded:{0}", ctx.channel());
	}

	/** 
	 * handlerRemoved。
	 * @see io.netty.channel.ChannelHandlerAdapter#handlerRemoved(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-handlerRemoved:{0}", ctx.channel());
	}

	/** 
	 * channelRegistered。
	 * @see io.netty.channel.ChannelInboundHandler#channelRegistered(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-channelRegistered:{0}", ctx.channel());
		ctx.fireChannelRegistered();
	}

	/** 
	 * channelUnregistered。
	 * @see io.netty.channel.ChannelInboundHandler#channelUnregistered(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-channelUnregistered:{0}", ctx.channel());
		ctx.fireChannelUnregistered();
	}

	/** 
	 * channelActive。
	 * @see io.netty.channel.ChannelInboundHandler#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-channelActive:{0}", ctx.channel());
		ctx.fireChannelActive();
	}

	/** 
	 * channelInactive。
	 * @see io.netty.channel.ChannelInboundHandler#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-channelInactive:{0}", ctx.channel());
		ctx.fireChannelInactive();
	}

	/** 
	 * userEventTriggered。
	 * @see io.netty.channel.ChannelInboundHandler#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		recorder.write("LoggingChannelHandler-userEventTriggered:{0},{1}", ctx.channel(), evt);
		ctx.fireUserEventTriggered(evt);
	}

	/** 
	 * channelWritabilityChanged。
	 * @see io.netty.channel.ChannelInboundHandler#channelWritabilityChanged(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-channelWritabilityChanged:{0}", ctx.channel());
		ctx.fireChannelWritabilityChanged();
	}

	/** 
	 * channelRead。
	 * @see io.netty.channel.ChannelInboundHandler#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (recorder.canWrite()) {
			recorder.write("LoggingChannelHandler-channelRead:{0},{1}", ctx.channel(), NettyUtil.toHexString(msg));
		}
		ctx.fireChannelRead(msg);
	}

	/** 
	 * channelReadComplete。
	 * @see io.netty.channel.ChannelInboundHandler#channelReadComplete(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-channelReadComplete:{0}", ctx.channel());
		ctx.fireChannelReadComplete();
	}

	/** 
	 * exceptionCaught。
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		recorder.write(cause, "LoggingChannelHandler-exceptionCaught:{0},{1}", ctx.channel(), cause.getMessage());
		ctx.fireExceptionCaught(cause);
	}

	/** 
	 * write。
	 * @see io.netty.channel.ChannelOutboundHandler#write(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (recorder.canWrite()) {
			recorder.write("LoggingChannelHandler-write:{0},{1}", ctx.channel(), NettyUtil.toHexString(msg));
		}
		ctx.write(msg, promise);
	}

	/** 
	 * read。
	 * @see io.netty.channel.ChannelOutboundHandler#read(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-read:{0}", ctx.channel());
		ctx.read();
	}

	/** 
	 * flush。
	 * @see io.netty.channel.ChannelOutboundHandler#flush(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		recorder.write("LoggingChannelHandler-flush:{0}", ctx.channel());
		ctx.flush();
	}

	/** 
	 * connect。
	 * @see io.netty.channel.ChannelOutboundHandler#connect(io.netty.channel.ChannelHandlerContext, java.net.SocketAddress, java.net.SocketAddress, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		recorder.write("LoggingChannelHandler-connect:{0}", ctx.channel());
		ctx.connect(remoteAddress, localAddress, promise);
	}

	/** 
	 * disconnect。
	 * @see io.netty.channel.ChannelOutboundHandler#disconnect(io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		recorder.write("LoggingChannelHandler-disconnect:{0}", ctx.channel());
		ctx.disconnect(promise);
	}

	/** 
	 * bind。
	 * @see io.netty.channel.ChannelOutboundHandler#bind(io.netty.channel.ChannelHandlerContext, java.net.SocketAddress, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		recorder.write("LoggingChannelHandler-bind:{0}", ctx.channel());
		ctx.bind(localAddress, promise);
	}

	/** 
	 * deregister。
	 * @see io.netty.channel.ChannelOutboundHandler#deregister(io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		recorder.write("LoggingChannelHandler-deregister:{0}", ctx.channel());
		ctx.deregister(promise);
	}

	/** 
	 * close。
	 * @see io.netty.channel.ChannelOutboundHandler#close(io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		recorder.write("LoggingChannelHandler-close:{0}", ctx.channel());
		ctx.close(promise);
	}

}
