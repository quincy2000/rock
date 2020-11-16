package org.quincy.rock.comm.netty.codec;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.lang.Recorder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.Attribute;

/**
 * <b>DelimiterBasedFrameDecoder。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 先修正再废弃。
 * 尾部修正值tailAdjust允许为负数，
 * 剥掉尾部字节数stripTail不允许为负数。
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
public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {
	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * 分隔符列表。
	 */
	private final ByteBuf[] delimiters;
	/**
	 * 最大帧长度。
	 */
	private int maxFrameLength = 8192;
	/**
	 * 最小帧长度。
	 */
	private int minFrameLength;
	/**
	 * 是否快速失败。
	 */
	private boolean failFast = true;
	/**
	 * 尾部修正字节数。
	 */
	private int tailAdjust = 0;
	/**
	 * 剥掉尾部字节数。
	 */
	private int stripTail = 0;

	/**
	 * 当前帧下次开始读位置。
	 */
	private int nextReadIndex;
	/**
	 * 是否要废弃当前帧。
	 */
	private boolean discardingTooLongFrame;
	/**
	 * 最大分隔符长度。
	 */
	private int maxDelimiterLength;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiters 分隔符列表
	 */
	public DelimiterBasedFrameDecoder(ByteBuf... delimiters) {
		if (delimiters == null) {
			throw new NullPointerException("delimiters");
		}
		int len = delimiters.length;
		if (len == 0) {
			throw new IllegalArgumentException("empty delimiters");
		}
		this.delimiters = new ByteBuf[len];
		for (int i = 0; i < len; i++) {
			ByteBuf delimiter = delimiters[i];
			validateDelimiter(delimiter);
			if (delimiter.readableBytes() > maxDelimiterLength)
				maxDelimiterLength = delimiter.readableBytes();
			this.delimiters[i] = delimiter.slice();
		}
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
	 * <b>获得最小帧长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最小帧长度
	 */
	public int getMinFrameLength() {
		return minFrameLength;
	}

	/**
	 * <b>设置最小帧长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param minFrameLength 最小帧长度
	 */
	public void setMinFrameLength(int minFrameLength) {
		this.minFrameLength = minFrameLength;
	}

	/**
	 * <b>获得最大帧长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大帧长度
	 */
	public int getMaxFrameLength() {
		return maxFrameLength;
	}

	/**
	 * <b>设置最大帧长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxFrameLength 最大帧长度
	 */
	public void setMaxFrameLength(int maxFrameLength) {
		this.maxFrameLength = maxFrameLength;
	}

	/**
	 * <b>是否快速失败。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否快速失败
	 */
	public boolean isFailFast() {
		return failFast;
	}

	/**
	 * <b>设置是否快速失败。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param failFast 是否快速失败
	 */
	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	/**
	 * <b>获得尾部修正字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 尾部修正字节数
	 */
	public int getTailAdjust() {
		return tailAdjust;
	}

	/**
	 * <b>设置尾部修正字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tailAdjust 尾部修正字节数
	 */
	public void setTailAdjust(int tailAdjust) {
		this.tailAdjust = tailAdjust;
	}

	/**
	 * <b>获得剥掉尾部字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 剥掉尾部字节数
	 */
	public int getStripTail() {
		return stripTail;
	}

	/**
	 * <b>设置剥掉尾部字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param stripTail 剥掉尾部字节数
	 */
	public void setStripTail(int stripTail) {
		this.stripTail = stripTail;
	}

	/**
	 * <b>计算尾部修正值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ctx ChannelHandlerContext
	 * @param in 报文数据
	 * @return 尾部修正值
	 */
	protected int calcTailAdjust(ChannelHandlerContext ctx, ByteBuf in) {
		return tailAdjust;
	}

	/**
	 * <b>查找分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 索引是相对于读索引的位置。
	 * @param ctx ChannelHandlerContext
	 * @param buffer 报文消息
	 * @param beginIndex 开始查找位置
	 * @param delim 分隔符
	 * @return 分隔符位置索引，如果未找到则返回-1
	 */
	protected int findDelimiter(ChannelHandlerContext ctx, ByteBuf buffer, int beginIndex, ByteBuf delim) {
		int frameLength = delim.capacity() == 1
				? NettyUtil.indexOf(buffer, beginIndex, delim.getByte(delim.readerIndex()))
				: NettyUtil.indexOf(buffer, beginIndex, delim);
		return frameLength;
	}

	/**
	 * <b>查找分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 索引是相对于读索引的位置。
	 * @param ctx ChannelHandlerContext
	 * @param buffer 报文消息
	 * @param beginIndex 开始查找位置
	 * @param delims 分隔符数组
	 * @return 找到的分隔符及位置索引，如果未找到则返回null
	 */
	protected Pair<Integer, ByteBuf> findDelimiter(ChannelHandlerContext ctx, ByteBuf buffer, int beginIndex,
			ByteBuf[] delims) {
		Pair<Integer, ByteBuf> pair = null;
		for (ByteBuf delim : delims) {
			int index = findDelimiter(ctx, buffer, beginIndex, delim);
			if (index != -1) {
				pair = Pair.of(index, delim);
				break;
			}
		}
		return pair;
	}

	/** 
	 * decode。
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() > minFrameLength) {
			ByteBuf decoded = decode(ctx, in);
			if (decoded != null) {
				out.add(decoded);
			}
		}
	}

	/**
	* <b>解码。</b>
	* <p><b>详细说明：</b></p>
	* <!-- 在此添加详细说明 -->
	* 无。
	* @param ctx ChannelHandlerContext
	* @param buffer 输入ByteBuf
	* @return 输出解码对象,如果没有成功解码则返回null
	* @throws Exception
	*/
	protected ByteBuf decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		//解析一个完整的帧
		recorder.write("DelimiterBasedFrameDecoder:Decoding......");
		Pair<Integer, ByteBuf> finalDelim = findDelimiter(ctx, buffer, Math.max(minFrameLength, nextReadIndex),
				delimiters);
		//
		ByteBuf logSlice = buffer.slice();
		ByteBuf oneFrame;
		if (finalDelim == null) {
			nextReadIndex = Math.max(0, buffer.readableBytes() - maxDelimiterLength);
			//没找到分隔符
			if (!discardingTooLongFrame && buffer.readableBytes() > maxFrameLength) {
				//帧不合法，超长了，丢掉
				discardingTooLongFrame = true;
				if (failFast) {
					fail(buffer.readableBytes());
				}
			}
			oneFrame = null;
		} else {
			//解析到一个数据帧
			int _tailAdjust = calcTailAdjust(ctx, buffer); //尾部修正值
			int realAllLength = finalDelim.getLeft() + finalDelim.getRight().capacity() + _tailAdjust; //本次读取的实际全部长度
			if (_tailAdjust > 0 && buffer.readableBytes() < realAllLength) {
				oneFrame = null;
				if (recorder.canWrite()) {
					recorder.write("DelimiterBasedFrameDecoder:readableBytes<realAllLength({0}<{1}) for {2}.",
							logSlice.readableBytes(), realAllLength, NettyUtil.toHexString(logSlice));
				}
			} else {
				int realFrameLength = realAllLength - stripTail;//实际返回的帧长度
				oneFrame = buffer.readSlice(realFrameLength);
				if (stripTail > 0)
					buffer.skipBytes(stripTail);
				nextReadIndex = 0; //复位
				//判断该完整帧的合法性
				if (discardingTooLongFrame) {
					//该数据帧太长了，不合法，需要丢弃
					discardingTooLongFrame = false; //复位
					if (!failFast) {
						fail(realAllLength);
					}
					oneFrame = null;
				} else if (realAllLength > maxFrameLength) {
					//最后一刻才发现该数据帧太长了，不合法，需要丢弃			
					fail(realAllLength);
					oneFrame = null;
				}
			}
		}
		if (oneFrame == null) {
			if (recorder.canWrite()) {
				recorder.write("DelimiterBasedFrameDecoder:Skip this decoding for [{0}]({1}).",
						NettyUtil.toHexString(logSlice), logSlice.readableBytes());
			}
		} else {
			oneFrame.retain();
			Attribute<ByteBuf> arr = ctx.channel().attr(NettyUtil.DELIMITER_FOR_DECODER_KEY);
			arr.set(finalDelim.getRight());
			if (recorder.canWrite()) {
				recorder.write("DelimiterBasedFrameDecoder:Decoding success for [{0}]({1}).",
						NettyUtil.toHexString(oneFrame), oneFrame.readableBytes());
			}
		}
		return oneFrame;
	}

	private void fail(long frameLength) throws Exception {
		Exception ex = new TooLongFrameException(
				"frame length exceeds " + maxFrameLength + ":" + frameLength + " - discarded.");
		recorder.write(ex, ex.getMessage());
		throw ex;
	}

	private static void validateDelimiter(ByteBuf delimiter) {
		if (delimiter == null) {
			throw new NullPointerException("delimiter");
		}
		if (!delimiter.isReadable()) {
			throw new IllegalArgumentException("empty delimiter");
		}
	}
}
