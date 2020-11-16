package org.quincy.rock.comm.netty.codec;

import java.nio.ByteOrder;
import java.util.List;
import java.util.zip.Checksum;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.security.CrcType;
import org.quincy.rock.core.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * <b>CrcDecoder。</b>
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
public class CrcDecoder extends MessageToMessageDecoder<ByteBuf> implements HasRandomKey {
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
	 * 剥掉尾部字节数。
	 */
	private int stripTail = 0;
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
	 * 是否忽略CRC错误。
	 */
	private boolean ignoreCrcError;
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
	public CrcDecoder(Checksum checker, CrcType crcType) {
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
	public CrcDecoder(Checksum checker, CrcType crcType, byte[] generalKey) {
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
	 * <b>是否忽略CRC错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否忽略CRC错误
	 */
	public boolean isIgnoreCrcError() {
		return ignoreCrcError;
	}

	/**
	 * <b>设置是否忽略CRC错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreCrcError 是否忽略CRC错误
	 */
	public void setIgnoreCrcError(boolean ignoreCrcError) {
		this.ignoreCrcError = ignoreCrcError;
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
	 * <b>计算校验码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不会影响读写指针。
	 * @param msg ByteBuf
	 * @return 校验码
	 */
	protected long crc(ByteBuf msg, byte[] key) {
		checker.reset();
		if (key != null)
			checker.update(key, 0, key.length);
		for (int i = msg.readerIndex(); i < msg.writerIndex(); i++) {
			checker.update(msg.getByte(i));
		}
		return checker.getValue();
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
	 * @param in 报文数据
	 * @return 是否是bcd码
	 */
	protected boolean checkBCD(ChannelHandlerContext ctx, ByteBuf in) {
		return bcd;
	}

	/** 
	 * decode。
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		boolean ok = false, isBCD = false;
		long crcCode = 0, crcCode_calc = 0;
		ByteBuf data = null, crcData = null;
		if (ignored(in)) {
			ok = true;
			data = in;
		} else {
			String logData = null; //日志数据
			if (recorder.canWrite()) {
				logData = NettyUtil.readHex(in.slice(), in.readableBytes());
				recorder.write("CrcDecoder:check start:[{0}]", logData);
			}
			isBCD = checkBCD(ctx, in);
			int byteCount = isBCD ? crcType.byteCount4BCD() : crcType.byteCount();
			int len = in.readableBytes() - byteCount; //计算校验码的数据长度
			data = in.slice(in.readerIndex(), len - stripTail); //需要继续传递的报文数据
			//验证CRC校验码
			crcData = in.readSlice(len); //用来计算校验码的数据			
			ByteBuf bufCode = in.readSlice(byteCount); //传过来的校验码
			crcCode = (crcType.isHex() ? readCrcCode(bufCode, isBCD) : readCrcCode(bufCode));
			byte[] key = randomKey(ctx.channel(), crcData);
			if (key != null) {
				crcCode_calc = crc(crcData, key);
				ok = crcCode_calc == crcCode;
			}
			if (!ok) {
				crcCode_calc = crc(crcData, generalKey);
				ok = crcCode_calc == crcCode;
			}
			if (ok && recorder.canWrite()) {
				recorder.write("CrcDecoder:check success:[{0}]", logData);
			}
		}
		//校验完成
		if (!ok) {
			StringBuilder sb = new StringBuilder();
			sb.append("CrcDecoder:check error!");
			sb.append("data:[");
			if (crcData != null) {
				sb.append(NettyUtil.readHex(crcData.slice(), crcData.readableBytes()));
			} else
				sb.append("null");
			sb.append("],content:[");
			if (data != null) {
				sb.append(NettyUtil.readHex(data.slice(), data.readableBytes()));
			} else
				sb.append("null");
			sb.append("],CRC:[");
			sb.append(StringUtil.leftPad(Long.toHexString(crcCode_calc), crcType.bit() / 4, '0'));
			sb.append("],but:[");
			sb.append(StringUtil.leftPad(Long.toHexString(crcCode), crcType.bit() / 4, '0'));
			sb.append("].ignoreCrcError=");
			sb.append(ignoreCrcError);
			CommunicateException ex = new CommunicateException(sb.toString());
			recorder.write(ex, ex.getMessage());
			if (!ignoreCrcError)
				throw ex;
		}
		out.add(data.retain());
	}

	//获得crc校验码
	private long readCrcCode(ByteBuf bufCode) {
		long crc;
		int byteCount = bufCode.readableBytes();
		switch (byteCount) {
		case 1:
			crc = bufCode.readUnsignedByte();
			break;
		case 2:
			crc = bigEndian ? bufCode.readUnsignedShort() : bufCode.readUnsignedShortLE();
			break;
		case 4:
			crc = bigEndian ? bufCode.readUnsignedInt() : bufCode.readUnsignedIntLE();
			break;
		default:
			throw new UnsupportException("CRC size:" + byteCount);
		}
		return crc;
	}

	//从16进制字符串获得crc校验码
	private long readCrcCode(ByteBuf bufCode, boolean isBCD) {
		String hex = isBCD ? NettyUtil.readBCD(bufCode, bufCode.readableBytes())
				: NettyUtil.readChars(bufCode, bufCode.readableBytes());
		return Long.parseLong(hex, 16);
	}
}
