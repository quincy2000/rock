package org.quincy.rock.comm.netty.codec;

import java.nio.ByteOrder;

import org.quincy.rock.comm.netty.ChannelHandlerCreator;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.security.CrcType;
import org.quincy.rock.core.util.CoreUtil;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * <b>AbstractCRCCodecCreator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月26日 下午4:22:31</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractCRCCodecCreator extends ChannelHandlerAdapter
		implements ChannelHandlerCreator, HasRandomKey {
	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * 首个ChannelHandler拦截器。
	 */
	private ChannelHandler firstChannelHandler;

	/**
	 * 报文发送拦截器。
	 */
	private ChannelHandler sendInterceptor;

	/**
	 * 报文接收拦截器。
	 */
	private ChannelHandler receiveInterceptor;

	/**
	 * 是否是大端字节序。
	 */
	private boolean bigEndian = true;
	/**
	 * crcType。
	 */
	private CrcType crcType;
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
	 * CRC是否是BCD码。
	 */
	private boolean bcdCrc;

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
	 * <b>获得首个ChannelHandler拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 首个ChannelHandler拦截器
	 */
	public ChannelHandler getFirstChannelHandler() {
		if (firstChannelHandler == null) {
			firstChannelHandler = new ChannelDuplexHandler() {

				@Override
				public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
					try {
						recorder.write(cause, "firstChannelHandler.exceptionCaught");
					} catch (Exception e) {
					}
				}

				@Override
				public boolean isSharable() {
					return true;
				}
			};
		}
		return firstChannelHandler;
	}

	/**
	 * <b>设置首个ChannelHandler拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param firstChannelHandler 首个ChannelHandler拦截器
	 */
	public void setFirstChannelHandler(ChannelHandler firstChannelHandler) {
		this.firstChannelHandler = firstChannelHandler;
	}

	/**
	 * <b>获得报文发送拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文发送拦截器
	 */
	public ChannelHandler getSendInterceptor() {
		return sendInterceptor;
	}

	/**
	 * <b>设置报文发送拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sendInterceptor 报文发送拦截器
	 */
	public void setSendInterceptor(ChannelHandler sendInterceptor) {
		this.sendInterceptor = sendInterceptor;
	}

	/**
	 * <b>获得报文接收拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文接收拦截器
	 */
	public ChannelHandler getReceiveInterceptor() {
		return receiveInterceptor;
	}

	/**
	 * <b>设置报文接收拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param receiveInterceptor 报文接收拦截器
	 */
	public void setReceiveInterceptor(ChannelHandler receiveInterceptor) {
		this.receiveInterceptor = receiveInterceptor;
	}

	/**
	 * <b>是否是大端字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是大端字节序
	 */
	public final boolean isBigEndian() {
		return bigEndian;
	}

	/**
	 * <b>是否是大端字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bigEndian 是否是大端字节序
	 */
	public final void setBigEndian(boolean bigEndian) {
		this.bigEndian = bigEndian;
	}

	/**
	 * <b>getCrcType。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CrcType
	 */
	public final CrcType getCrcType() {
		return crcType == null ? CrcType.NONE : crcType;
	}

	/**
	 * <b>setCrcType。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param crcType CrcType
	 */
	public final void setCrcType(CrcType crcType) {
		this.crcType = crcType;
	}

	/**
	 * <b>CRC是否是BCD码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CRC是否是BCD码
	 */
	public final boolean isBcdCrc() {
		return bcdCrc;
	}

	/**
	 * <b>CRC是否是BCD码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bcdCrc CRC是否是BCD码
	 */
	public final void setBcdCrc(boolean bcdCrc) {
		this.bcdCrc = bcdCrc;
	}

	/**
	 * <b>获得忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 忽略的头部(该头部报文没有crc校验)
	 */
	public final byte[] getIgnoreHead() {
		return ignoreHead;
	}

	/**
	 * <b>设置忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreHead 忽略的头部(该头部报文没有crc校验)
	 */
	public final void setIgnoreHead(byte[] ignoreHead) {
		this.ignoreHead = ignoreHead;
	}

	/**
	 * <b>设置忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreHead 忽略的头部(该头部报文没有crc校验)
	 */
	public final void setIgnoreHeadString(String ignoreHead) {
		if (ignoreHead == null)
			this.ignoreHead = null;
		else if (CoreUtil.isHex(ignoreHead))
			this.ignoreHead = CoreUtil.hexString2ByteArray(ignoreHead.substring(2));
		else
			this.ignoreHead = ignoreHead.getBytes(CharsetUtil.UTF_8);
	}

	/**
	 * <b>获得一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 一般防篡改秘钥
	 */
	public final byte[] getGeneralKey() {
		return generalKey;
	}

	/**
	 * <b>设置一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param generalKey 一般防篡改秘钥
	 */
	public final void setGeneralKey(byte[] generalKey) {
		this.generalKey = generalKey;
	}

	/**
	 * <b>是否忽略CRC错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否忽略CRC错误
	 */
	public final boolean isIgnoreCrcError() {
		return ignoreCrcError;
	}

	/**
	 * <b>设置是否忽略CRC错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreCrcError 是否忽略CRC错误
	 */
	public final void setIgnoreCrcError(boolean ignoreCrcError) {
		this.ignoreCrcError = ignoreCrcError;
	}

	/**
	 * <b>设置一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param generalKey 一般防篡改秘钥
	 */
	public final void setGeneralKeyString(String generalKey) {
		if (generalKey == null)
			this.generalKey = null;
		else if (CoreUtil.isHex(generalKey))
			this.generalKey = CoreUtil.hexString2ByteArray(generalKey.substring(2));
		else
			this.generalKey = generalKey.getBytes(CharsetUtil.UTF_8);
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
	 * <b>是否需要添加CRC检查。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否需要添加CRC检查
	 */
	public boolean checkCRC() {
		return getCrcType() != CrcType.NONE;
	}

	/**
	 * <b>创建CRC解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CRC解码器
	 */
	protected ChannelHandler createCrcDecoder() {
		CrcDecoder decoder = null;
		CrcType type = getCrcType();
		if (type != CrcType.NONE) {
			decoder = createCrcDecoder(type);
			decoder.setRecorder(recorder);
			decoder.setBigEndian(isBigEndian());
			decoder.setIgnoreHead(ignoreHead);
			decoder.setGeneralKey(generalKey);
			decoder.setRandomKey(randomKey);
			decoder.setIgnoreCrcError(ignoreCrcError);
			decoder.setBcd(bcdCrc);
		}
		return decoder;
	}

	/**
	 * <b>创建CRC解码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param crcType CrcType
	 * @return CRC解码器
	 */
	protected CrcDecoder createCrcDecoder(CrcType crcType) {
		return new CrcDecoder(crcType.createChecker(), crcType);
	}

	/**
	 * <b>创建CRC编码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CRC编码器
	 */
	protected ChannelHandler createCrcEncoder() {
		CrcEncoder encoder = null;
		CrcType type = getCrcType();
		if (type != CrcType.NONE) {
			encoder = createCrcEncoder(type);
			encoder.setRecorder(recorder);
			encoder.setBigEndian(isBigEndian());
			encoder.setIgnoreHead(ignoreHead);
			encoder.setGeneralKey(generalKey);
			encoder.setRandomKey(randomKey);
			encoder.setBcd(bcdCrc);
		}
		return encoder;
	}

	/**
	 * <b>创建CRC编码器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param crcType CrcType
	 * @return CRC编码器
	 */
	protected CrcEncoder createCrcEncoder(CrcType crcType) {
		return new CrcEncoder(crcType.createChecker(), crcType);
	}

	/**
	 * <b>返回字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 字节序
	 */
	protected final ByteOrder byteOrder() {
		return bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
	}
}
