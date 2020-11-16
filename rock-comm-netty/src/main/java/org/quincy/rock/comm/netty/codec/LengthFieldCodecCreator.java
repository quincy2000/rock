package org.quincy.rock.comm.netty.codec;

import java.util.ArrayList;
import java.util.Collection;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * <b>基于长度字段的编解码器创建者。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月12日 下午2:46:40</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class LengthFieldCodecCreator extends AbstractCRCCodecCreator {
	/**
	 * 报文的最大字节数。
	 */
	private int maxBytesInMessage = 8192;
	/**
	 * 报文长度字节的长度。
	 */
	private int lengthFieldLength = 2;
	/**
	 * 报文长度是否包含长度字段的长度。
	 */
	private boolean lengthIncludesLengthFieldLength;

	/**
	 * <b>获得报文的最大字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文的最大字节数
	 */
	public int getMaxBytesInMessage() {
		return maxBytesInMessage;
	}

	/**
	 * <b>设置报文的最大字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxBytesInMessage 报文的最大字节数
	 */
	public void setMaxBytesInMessage(int maxBytesInMessage) {
		this.maxBytesInMessage = maxBytesInMessage;
	}

	/**
	 * <b>获得报文长度字节的长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文长度字节的长度
	 */
	public int getLengthFieldLength() {
		return lengthFieldLength;
	}

	/**
	 * <b>设置报文长度字节的长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param lengthFieldLength 报文长度字节的长度
	 */
	public void setLengthFieldLength(int lengthFieldLength) {
		this.lengthFieldLength = lengthFieldLength;
	}

	/**
	 * <b>报文长度是否包含长度字段的长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文长度是否包含长度字段的长度
	 */
	public boolean isLengthIncludesLengthFieldLength() {
		return lengthIncludesLengthFieldLength;
	}

	/**
	 * <b>设置报文长度是否包含长度字段的长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param lengthIncludesLengthFieldLength 报文长度是否包含长度字段的长度
	 */
	public void setLengthIncludesLengthFieldLength(boolean lengthIncludesLengthFieldLength) {
		this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength;
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
		//截取报文
		list.add(createLengthFieldDecoder());
		list.add(createLengthFieldEncoder());
		//拦截
		if (receive != null)
			list.add(receive);
		if (send != null)
			list.add(send);
		//CRC校验
		if (checkCRC())
			list.add(createCrcDecoder());
		if (checkCRC())
			list.add(createCrcEncoder());
		//
		return list;
	}

	/**
	 * <b>创建基于长度字段的解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 基于长度字段的解码器
	 */
	protected ChannelHandler createLengthFieldDecoder() {
		//创建新的非共享实例
		LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(byteOrder(), maxBytesInMessage, 0,
				lengthFieldLength, 0, lengthFieldLength, true);
		//
		return decoder;
	}

	/**
	 * <b>创建基于长度字段的编码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 基于长度字段的编码器
	 */
	protected ChannelHandler createLengthFieldEncoder() {
		if (lengthFieldEncoder == null) {
			lengthFieldEncoder = new LengthFieldPrepender(byteOrder(), lengthFieldLength, 0,
					lengthIncludesLengthFieldLength);
		}
		//
		return lengthFieldEncoder;
	}

	//Sharable
	private ChannelHandler lengthFieldEncoder;
}
