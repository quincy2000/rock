package org.quincy.rock.comm.netty.codec;

import java.nio.ByteOrder;
import java.util.List;
import java.util.zip.Checksum;

import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.security.CrcType;
import org.quincy.rock.core.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * <b>CrcEncoder。</b>
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
public class CrcEncoder extends MessageToMessageEncoder<ByteBuf> implements HasRandomKey {
	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * crc计算器。
	 */
	private Checksum checker;
	/**
	 * crcType。
	 */
	private CrcType crcType;
	/**
	 * 是否是大端字节序。
	 */
	private boolean bigEndian = true;
	/**
	 * 忽略的头部(该头部报文没有crc校验)。
	 */
	private byte[] ignoreHead;
	/**
	 * 一般防篡改秘钥。
	 */
	private byte[] generalKey;
	/**
	 * 随机防篡改秘钥。
	 */
	private KeyGetter randomKey;
	/**
	 * 是否是BCD码。
	 */
	private boolean bcd;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param checker crc计算器
	 * @param crcType CrcType
	 */
	public CrcEncoder(Checksum checker, CrcType crcType) {
		if (crcType == CrcType.NONE)
			throw new IllegalArgumentException("CrcType.NONE");
		this.checker = checker;
		this.crcType = crcType;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param checker crc计算器
	 * @param crcType CrcType
	 * @param generalKey 一般防篡改秘钥
	 */
	public CrcEncoder(Checksum checker, CrcType crcType, byte[] generalKey) {
		this(checker, crcType);
		this.generalKey = generalKey;
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
	 * <b>是否是大端字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是大端字节序
	 */
	public boolean isBigEndian() {
		return bigEndian;
	}

	/**
	 * <b>是否是大端字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bigEndian 是否是大端字节序
	 */
	public void setBigEndian(boolean bigEndian) {
		this.bigEndian = bigEndian;
	}

	/**
	 * <b>是否是BCD码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是BCD码
	 */
	public boolean isBcd() {
		return bcd;
	}

	/**
	 * <b>设置是否是BCD码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bcd 是否是BCD码
	 */
	public void setBcd(boolean bcd) {
		this.bcd = bcd;
	}

	/**
	 * <b>获得忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 忽略的头部(该头部报文没有crc校验)
	 */
	public byte[] getIgnoreHead() {
		return ignoreHead;
	}

	/**
	 * <b>设置忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreHead 忽略的头部(该头部报文没有crc校验)
	 */
	public void setIgnoreHead(byte[] ignoreHead) {
		this.ignoreHead = ignoreHead;
	}

	/**
	 * <b>获得一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 一般防篡改秘钥
	 */
	public byte[] getGeneralKey() {
		return generalKey;
	}

	/**
	 * <b>设置一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param generalKey 一般防篡改秘钥
	 */
	public void setGeneralKey(byte[] generalKey) {
		this.generalKey = generalKey;
	}

	/** 
	 * setRandomKey。
	 * @see org.quincy.rock.comm.netty.codec.HasRandomKey#setRandomKey(org.quincy.rock.comm.netty.codec.KeyGetter)
	 */
	@Override
	public void setRandomKey(KeyGetter randomKey) {
		this.randomKey = randomKey;
	}

	/**
	 * <b>返回随机防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel netty通道
	 * @param msg 报文
	 * @return 随机防篡改秘钥
	 */
	protected byte[] randomKey(Channel channel, ByteBuf msg) {
		return randomKey == null ? null : randomKey.getKey(channel, msg);
	}

	/**
	 * <b>返回CrcType。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CrcType
	 */
	public CrcType crcType() {
		return crcType;
	}

	/**
	 * <b>返回字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 字节序
	 */
	protected ByteOrder byteOrder() {
		return bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
	}

	/**
	 * <b>是否忽略处理该报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msg ByteBuf
	 * @return 是否忽略处理该报文
	 */
	protected boolean ignored(ByteBuf msg) {
		return NettyUtil.isStartWith(msg, ignoreHead);
	}

	/**
	 * <b>检查是否是bcd码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ctx ChannelHandlerContext
	 * @param msg 报文数据
	 * @return 是否是bcd码
	 */
	protected boolean checkBCD(ChannelHandlerContext ctx, ByteBuf msg) {
		return bcd;
	}

	/**
	 * <b>计算校验码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不会影响读写指针。
	 * @param ctx ChannelHandlerContext
	 * @param msg ByteBuf
	 * @return 校验码ByteBuf
	 */
	protected ByteBuf calculateCRC(ChannelHandlerContext ctx, ByteBuf msg) {
		long crcValue = crc(ctx, msg);
		//
		boolean isBCD = checkBCD(ctx, msg);
		int byteCount = isBCD ? crcType.byteCount4BCD() : crcType.byteCount();
		ByteBuf crcBuf = ctx.alloc().buffer(byteCount, byteCount + 30);
		try {
			if (crcType.isHex()) {
				String crc = StringUtil.leftPad(Long.toHexString(crcValue), crcType.byteCount(), '0');
				if (isBCD)
					NettyUtil.writeBCD(crcBuf, crc, byteCount);
				else
					NettyUtil.writeChars(crcBuf, crc, byteCount);
			} else if (isBigEndian()) {
				for (int i = byteCount - 1; i > 0; i--)
					crcBuf.writeByte((byte) (crcValue >>> (i * 8)));
				crcBuf.writeByte((byte) crcValue);
			} else {
				crcBuf.writeByte((byte) crcValue);
				for (int i = 1; i < byteCount; i++)
					crcBuf.writeByte((byte) (crcValue >>> (i * 8)));
			}
			return crcBuf.retain();
		} finally {
			crcBuf.release();
		}
	}

	/** 
	 * encode。
	 * @see io.netty.handler.codec.MessageToMessageEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, java.util.List)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		if (ignored(msg)) {
			out.add(msg.retain());
		} else {
			CompositeByteBuf buf = ctx.alloc().compositeBuffer(2);
			try {
				buf.addComponent(true, msg.retain());
				buf.addComponent(true, calculateCRC(ctx, msg));
				out.add(buf.retain());
			} finally {
				buf.release();
			}
		}
	}

	/**
	 * <b>计算校验码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不会影响读写指针。
	 * @param ctx ChannelHandlerContext
	 * @param msg ByteBuf
	 * @return 校验码
	 */
	private long crc(ChannelHandlerContext ctx, ByteBuf msg) {
		checker.reset();
		byte[] randomKey = randomKey(ctx.channel(), msg);
		if (randomKey != null) {
			checker.update(randomKey, 0, randomKey.length);
		} else if (generalKey != null)
			checker.update(generalKey, 0, generalKey.length);
		for (int i = msg.readerIndex(); i < msg.writerIndex(); i++) {
			checker.update(msg.getByte(i));
		}
		return checker.getValue();
	}
}
