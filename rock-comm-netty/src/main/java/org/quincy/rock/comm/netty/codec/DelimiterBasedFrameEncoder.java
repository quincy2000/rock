package org.quincy.rock.comm.netty.codec;

import java.util.List;

import org.quincy.rock.core.function.Function;
import org.quincy.rock.core.lang.Recorder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * <b>DelimiterBasedFrameEncoder。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月25日 上午9:32:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Sharable
public class DelimiterBasedFrameEncoder extends MessageToMessageEncoder<ByteBuf> {
	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * 分隔符。
	 */
	private final Function<Channel, ByteBuf> delimiter;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter 分隔符
	 */
	public DelimiterBasedFrameEncoder(final ByteBuf delimiter) {
		this.delimiter = new Function<Channel, ByteBuf>() {
			private ByteBuf delimi = delimiter;

			@Override
			public ByteBuf call(Channel channel) {
				return delimi;
			}

		};
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter 分隔符
	 */
	public DelimiterBasedFrameEncoder(Function<Channel, ByteBuf> delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * <b>获得日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 日志记录器
	 */
	public Recorder getRecorder() {
		return recorder;
	}

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
	 * <b>返回分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ctx ChannelHandlerContext
	 * @return 分隔符
	 */
	protected ByteBuf delimiter(ChannelHandlerContext ctx) {
		return delimiter == null ? null : delimiter.call(ctx.channel());
	}

	/** 
	 * encode。
	 * @see io.netty.handler.codec.MessageToMessageEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, java.util.List)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		ByteBuf delimiter = delimiter(ctx);
		if (delimiter == null)
			out.add(msg.retain());
		else {
			CompositeByteBuf buf = ctx.alloc().compositeBuffer(2);
			try {
				buf.addComponent(true, msg.retain());
				buf.addComponent(true, delimiter.retainedSlice());
				out.add(buf.retain());
			} finally {
				buf.release();
			}
		}
	}
}
