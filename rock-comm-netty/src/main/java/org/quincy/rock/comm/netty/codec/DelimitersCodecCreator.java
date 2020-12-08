package org.quincy.rock.comm.netty.codec;

import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * <b>基于多个分隔符的编解码器创建者。</b>
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
public class DelimitersCodecCreator extends AbstractDelimiterCodecCreator {
	/**
	 * 分隔符列表(解码器使用)。
	 */
	private ByteBuf[] delimiter4Decoder;
	/**
	 * 分隔符回调(编码器使用)。
	 */
	private Function<Channel, ByteBuf> delimiter4Encoder = DefaultDelimiterGetter.INSTANCE;

	/**
	 * <b>设置分隔符(解码器使用)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果是多个分隔符则使用逗号分隔。
	 * @param delimiter 分隔符(解码器使用)
	 */
	public final void setDelimiter4Decoder(String delimiter) {
		if (delimiter.length() < 3) {
			this.delimiter4Decoder = new ByteBuf[] { NettyUtil.wrapDelimiter(delimiter) };
		} else {
			String[] ds = delimiter.split(",");
			int len = ds.length;
			this.delimiter4Decoder = new ByteBuf[len];
			for (int i = 0; i < len; i++) {
				this.delimiter4Decoder[i] = NettyUtil.wrapDelimiter(ds[i]);
			}
		}
	}

	/**
	 * <b>设置分隔符回调(编码器使用)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter 分隔符回调(编码器使用)
	 */
	public final void setDelimiter4Encoder(Function<Channel, ByteBuf> delimiter) {
		this.delimiter4Encoder = delimiter;
	}

	/** 
	 * delimiter4Encoder。
	 * @see org.quincy.rock.comm.netty.codec.AbstractDelimiterCodecCreator#delimiter4Encoder()
	 */
	@Override
	protected final Function<Channel, ByteBuf> delimiter4Encoder() {
		return delimiter4Encoder;
	}

	/** 
	 * delimiter4Decoder。
	 * @see org.quincy.rock.comm.netty.codec.AbstractDelimiterCodecCreator#delimiter4Decoder()
	 */
	@Override
	protected final ByteBuf[] delimiter4Decoder() {
		return delimiter4Decoder;
	}
}
