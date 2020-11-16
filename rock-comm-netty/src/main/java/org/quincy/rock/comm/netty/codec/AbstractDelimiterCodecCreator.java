package org.quincy.rock.comm.netty.codec;

import java.util.ArrayList;
import java.util.Collection;

import org.quincy.rock.core.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

/**
 * <b>基于分隔符的编解码器创建者。</b>
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
public abstract class AbstractDelimiterCodecCreator extends AbstractCRCCodecCreator {
	/**
	 * 最大长度(分隔符解码器)。
	 */
	private int maxFrameLength = 8192;

	/**
	 * 最小帧长度(分隔符解码器)。
	 */
	private int minFrameLength;

	/**
	 * 尾部修正字节数(分隔符解码器)。
	 */
	private int tailAdjust;

	/**
	 * 剥掉尾部字节数(分隔符解码器)。
	 */
	private int stripTail;

	/**
	 * 剥掉分隔符(CRC解码器)。
	 */
	private boolean stripDelimiter;

	/**
	 * 尾部是CRC校验码而不是分隔符(CRC编码器)。
	 */
	private boolean crcIsTail;

	/**
	 * <b>获得最小帧长度(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最小帧长度(分隔符解码器)
	 */
	public final int getMinFrameLength() {
		return minFrameLength;
	}

	/**
	 * <b>设置最小帧长度(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param minFrameLength 最小帧长度(分隔符解码器)
	 */
	public final void setMinFrameLength(int minFrameLength) {
		this.minFrameLength = minFrameLength;
	}

	/**
	 * <b>获得最大长度(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大长度(分隔符解码器)
	 */
	public final int getMaxFrameLength() {
		return maxFrameLength;
	}

	/**
	 * <b>设置最大长度(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxFrameLength 最大长度(分隔符解码器)
	 */
	public final void setMaxFrameLength(int maxFrameLength) {
		this.maxFrameLength = maxFrameLength;
	}

	/**
	 * <b>获得尾部修正字节数(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 尾部修正字节数(分隔符解码器)
	 */
	public final int getTailAdjust() {
		return tailAdjust;
	}

	/**
	 * <b>设置尾部修正字节数(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tailAdjust 尾部修正字节数(分隔符解码器)
	 */
	public final void setTailAdjust(int tailAdjust) {
		this.tailAdjust = tailAdjust;
	}

	/**
	 * <b>获得剥掉尾部字节数(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一般用于剥掉分隔符。
	 * @return 剥掉尾部字节数(分隔符解码器)
	 */
	public final int getStripTail() {
		return stripTail;
	}

	/**
	 * <b>设置剥掉尾部字节数(分隔符解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 一般用于剥掉分隔符。
	 * @param stripTail 剥掉尾部字节数(分隔符解码器)
	 */
	public final void setStripTail(int stripTail) {
		this.stripTail = stripTail;
	}

	/**
	 * <b>剥掉分隔符(CRC解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 在CRC解码器中剥掉分隔符，适用于CRC在分隔符后部的情况。
	 * 如果CRC在分隔符的前面，则分隔符会在分隔符解码器中剥除。
	 * @return 剥掉分隔符(CRC解码器)
	 */
	public final boolean isStripDelimiter() {
		return stripDelimiter;
	}

	/**
	 * <b>剥掉分隔符(CRC解码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 在CRC解码器中剥掉分隔符，适用于CRC在分隔符后部的情况。
	 * 如果CRC在分隔符的前面，则分隔符会在分隔符解码器中剥除。
	 * @param stripDelimiter 剥掉分隔符(CRC解码器)
	 */
	public final void setStripDelimiter(boolean stripDelimiter) {
		this.stripDelimiter = stripDelimiter;
	}

	/**
	 * <b>尾部是CRC校验码而不是分隔符(CRC编码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 尾部是CRC校验码而不是分隔符(CRC编码器)
	 */
	public boolean isCrcIsTail() {
		return crcIsTail;
	}

	/**
	 * <b>尾部是CRC校验码而不是分隔符(CRC编码器)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param crcIsTail 尾部是CRC校验码而不是分隔符(CRC编码器)
	 */
	public void setCrcIsTail(boolean crcIsTail) {
		this.crcIsTail = crcIsTail;
	}

	/**
	 * <b>编码器使用的分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 使用回调器获得分隔符。
	 * @return 编码器使用的分隔符
	 */
	protected abstract Function<Channel, ByteBuf> delimiter4Encoder();

	/**
	 * <b>解码器使用的分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 支持一个或多个分隔符。
	 * @return 分隔符
	 */
	protected abstract ByteBuf[] delimiter4Decoder();

	/** 
	 * createCrcDecoder。
	 * @see org.quincy.rock.comm.netty.codec.AbstractCRCCodecCreator#createCrcDecoder()
	 */
	@Override
	protected ChannelHandler createCrcDecoder() {
		CrcDecoder decoder = (CrcDecoder) super.createCrcDecoder();
		if (decoder != null && stripDelimiter) {
			ByteBuf[] delimiter4Decoder = delimiter4Decoder();
			ByteBuf delimiter = delimiter4Decoder == null ? null : delimiter4Decoder[0];
			decoder.setStripTail(delimiter == null ? 0 : delimiter.readableBytes());
		}
		return decoder;
	}

	/**
	 * <b>创建分隔符解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分隔符解码器
	 */
	protected ChannelHandler createDelimiterEncoder() {
		DelimiterBasedFrameEncoder encoder = createDelimiterEncoder(delimiter4Encoder());
		encoder.setRecorder(recorder);
		return encoder;
	}

	/**
	 * <b>创建分隔符解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter4Encoder 分隔符
	 * @return 分隔符解码器
	 */
	protected DelimiterBasedFrameEncoder createDelimiterEncoder(Function<Channel, ByteBuf> delimiter4Encoder) {
		return new DelimiterBasedFrameEncoder(delimiter4Encoder);
	}

	/**
	 * <b>创建分隔符解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分隔符解码器
	 */
	protected ChannelHandler createDelimiterDecoder() {
		DelimiterBasedFrameDecoder decoder = createDelimiterDecoder(delimiter4Decoder());
		decoder.setFailFast(true);
		decoder.setMinFrameLength(minFrameLength);
		decoder.setMaxFrameLength(maxFrameLength);
		decoder.setStripTail(stripTail);
		decoder.setTailAdjust(tailAdjust);
		decoder.setRecorder(recorder);
		return decoder;
	}

	/**
	 * <b>创建分隔符解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter4Decoder 分隔符数组
	 * @return 分隔符解码器
	 */
	protected DelimiterBasedFrameDecoder createDelimiterDecoder(ByteBuf[] delimiter4Decoder) {
		return new DelimiterBasedFrameDecoder(delimiter4Decoder);
	}

	/** 
	 * createChannelHandlers。
	 * @see org.quincy.rock.comm.netty.ChannelHandlerCreator#createChannelHandlers()
	 */
	@Override
	public Iterable<ChannelHandler> createChannelHandlers() {
		Collection<ChannelHandler> list = new ArrayList<>();
		ChannelHandler first = this.getFirstChannelHandler();
		ChannelHandler send = this.getSendInterceptor();
		ChannelHandler receive = this.getReceiveInterceptor();
		//首个ChannelHandler拦截器
		if (first != null)
			list.add(first);
		//Decoder
		list.add(createDelimiterDecoder()); //先切割报文
		if (receive != null)
			list.add(receive); //拦截接收的报文
		if (checkCRC())
			list.add(createCrcDecoder()); //再验证crc
		//Encoder
		if (send != null)
			list.add(send); //拦截发送的报文
		if (checkCRC() && isCrcIsTail())
			list.add(createCrcEncoder()); //crc校验码在最后
		list.add(createDelimiterEncoder());
		if (checkCRC() && !isCrcIsTail())
			list.add(createCrcEncoder()); //crc校验码在分隔符前面
		return list;
	}
}
