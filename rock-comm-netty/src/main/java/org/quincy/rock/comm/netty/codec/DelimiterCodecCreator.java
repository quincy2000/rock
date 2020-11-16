package org.quincy.rock.comm.netty.codec;

import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

/**
 * <b>基于单个分隔符的编解码器创建者。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月24日 下午9:57:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DelimiterCodecCreator extends AbstractDelimiterCodecCreator {
	/**
	 * 分隔符。
	 */
	private ByteBuf[] delimiters;
	/**
	 * 编码器使用的分隔符回调器。
	 */
	private final Function<Channel, ByteBuf> delimiter4Encoder;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DelimiterCodecCreator() {
		this.delimiter4Encoder = new Function<Channel, ByteBuf>() {

			@Override
			public ByteBuf call(Channel t) {
				return delimiter();
			}
		};
	}

	/**
	 * <b>设置分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter 分隔符
	 */
	public void setDelimiter(String delimiter) {
		ByteBuf buf = NettyUtil.wrapDelimiter(delimiter, CharsetUtil.UTF_8);
		delimiter(buf);
	}

	private void delimiter(ByteBuf buf) {
		if (delimiters == null)
			delimiters = new ByteBuf[1];
		delimiters[0] = buf;
	}

	/**
	 * <b>返回分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分隔符 
	 */
	protected ByteBuf delimiter() {
		return delimiters == null ? null : delimiters[0];
	}

	/** 
	 * delimiter4Encoder。
	 * @see org.quincy.rock.comm.netty.codec.AbstractDelimiterCodecCreator#delimiter4Encoder()
	 */
	@Override
	protected Function<Channel, ByteBuf> delimiter4Encoder() {
		return delimiter4Encoder;
	}

	/** 
	 * delimiter4Decoder。
	 * @see org.quincy.rock.comm.netty.codec.AbstractDelimiterCodecCreator#delimiter4Decoder()
	 */
	@Override
	protected ByteBuf[] delimiter4Decoder() {
		return delimiters;
	}
}
