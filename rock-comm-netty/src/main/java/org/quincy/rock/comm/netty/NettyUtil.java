package org.quincy.rock.comm.netty;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.netty.codec.DefaultDelimiterGetter;
import org.quincy.rock.core.concurrent.Waiter;
import org.quincy.rock.core.lang.Getter;
import org.quincy.rock.core.lang.TwoString;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;

/**
 * <b>NettyUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月17日 上午11:20:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class NettyUtil {
	/**
	 * 此Key存放编码时使用的分隔符,编码器使用该分隔符进行编码。
	 */
	public static final AttributeKey<ByteBuf> DELIMITER_FOR_ENCODER_KEY = AttributeKey.valueOf("ak_delimiter4Encoder");
	/**
	 * 此Key存放解码器解码出来的分隔符。
	 */
	public static final AttributeKey<ByteBuf> DELIMITER_FOR_DECODER_KEY = AttributeKey.valueOf("ak_delimiter4Decoder");
	/**
	 * 使用此Key存放通道时间戳。
	 */
	public static final AttributeKey<Long> AK_CHANNEL_TIMESTAMP = AttributeKey.valueOf("ak_channel_timestamp");
	/**
	 * 使用此Key存放通道最后访问时间戳。
	 */
	public static final AttributeKey<Long> AK_CHANNEL_LAST_ACCESS_TIME = AttributeKey
			.valueOf("ak_channel_lastAccessTime");
	/**
	 * 使用此Key存放通道默认内容类型。
	 */
	public static final AttributeKey<String> AK_CHANNEL_CONTENT_TYPE = AttributeKey.valueOf("ak_channel_contentType");

	/**
	 * 释放引用计数失败消息。
	 */
	public static final String REF_CNT_0 = "Release reference count error:refCnt=0.";
	/**
	 * 通道锁名称。
	 */
	public static final String CHANNEL_SEND_LOCK_NAME = "channel_send_lock";

	/**
	 * <b>获得Waiter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param name 锁名
	 * @param sync 是否是同步的(false为假锁)
	 * @return Waiter
	 */
	public static synchronized Waiter<Object, RuntimeException> retrieveWaiter(Channel channel, String name,
			boolean sync) {
		Attribute attr = channel.attr(AttributeKey.valueOf(name));
		Waiter<Object, RuntimeException> waiter = (Waiter) attr.get();
		if (waiter == null) {
			waiter = new Waiter<>(sync);
			attr.set(waiter);
		}
		if (waiter.isSync() != sync)
			throw new IllegalArgumentException("sync");
		return waiter;
	}

	/**
	 * <b>将原始报文数据转换成十六进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 原始报文数据
	 * @return 十六进制字符串
	 */
	public static String toHexString(Object data) {
		String hex = null;
		if (data instanceof ByteBuf) {
			ByteBuf buf = ((ByteBuf) data).slice();
			hex = NettyUtil.readHex(buf, buf.readableBytes());
		} else if (data instanceof ByteBuffer) {
			ByteBuf buf = Unpooled.wrappedBuffer((ByteBuffer) data).slice();
			hex = NettyUtil.readHex(buf, buf.readableBytes());
		} else if (data instanceof byte[]) {
			hex = CoreUtil.byteArray2HexString((byte[]) data);
		} else if (data instanceof String) {
			hex = data.toString();
		} else if (data != null) {
			hex = data.getClass().getName() + ":";
			try {
				hex += data.toString();
			} catch (Exception e) {
				hex += e.getMessage();
			}
		}
		//
		return hex;
	}

	/**
	 * <b>写时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 先写入4字节的无符号秒数，然后写入4字节的毫秒数。
	 * @param buf 字节缓冲区
	 * @param timestamp 时间戳毫秒数
	 * @return ByteBuf
	 */
	public static ByteBuf writeTimestamp(ByteBuf buf, long timestamp) {
		long left = timestamp / 1000;
		long right = timestamp % 1000;
		buf.writeInt((int) left);
		buf.writeInt((int) right);
		return buf;
	}

	/**
	 * <b>读时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 先读取4字节的无符号秒数，然后读取4字节的毫秒数。
	 * @param buf 字节缓冲区
	 * @return 时间戳毫秒数
	 */
	public static long readTimestamp(ByteBuf buf) {
		return buf.readUnsignedInt() * 1000 + buf.readInt();
	}

	/**
	 * <b>写入定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果长度不够则前面填充空格。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param len 写入长度
	 * @return ByteBuf
	 */
	public static ByteBuf writeChars(ByteBuf buf, CharSequence value, int len) {
		return writeChars(buf, value, len, StringUtil.CHAR_SPACE, CharsetUtil.UTF_8);
	}

	/**
	 * <b>写入定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果长度不够则前面填充空格。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param len 写入长度
	 * @param charset 字符集
	 * @return ByteBuf
	 */
	public static ByteBuf writeChars(ByteBuf buf, CharSequence value, int len, Charset charset) {
		return writeChars(buf, value, len, StringUtil.CHAR_SPACE, charset);
	}

	/**
	 * <b>写入定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 前面填充字符到指定长度。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param len 写入长度
	 * @param padding 前面填充字符
	 * @return ByteBuf
	 */
	public static ByteBuf writeChars(ByteBuf buf, CharSequence value, int len, char padding) {
		return writeChars(buf, value, len, padding, CharsetUtil.UTF_8);
	}

	/**
	 * <b>写入定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 前面填充字符到指定长度。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param len 写入长度
	 * @param padding 前面填充字符
	 * @param charset 字符集
	 * @return ByteBuf
	 */
	public static ByteBuf writeChars(ByteBuf buf, CharSequence value, int len, char padding, Charset charset) {
		int valSize, padSize;
		if (value == null || value.length() == 0) {
			valSize = 0;
			padSize = len;
		} else if (value.length() < len) {
			valSize = value.length();
			padSize = len - value.length();
		} else {
			valSize = len;
			padSize = 0;
		}
		if (padSize > 0)
			writeByte(buf, padding, padSize);
		if (valSize > 0)
			buf.writeCharSequence(value.subSequence(0, valSize > len ? len : valSize), charset);
		return buf;
	}

	/**
	 * <b>读取定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param len 读取的长度
	 * @return 字符串
	 */
	public static String readChars(ByteBuf buf, int len) {
		return readChars(buf, len, false, CharsetUtil.UTF_8);
	}

	/**
	 * <b>读取定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param len 读取的长度
	 * @param trim 是否要去掉前导空格
	 * @return 字符串
	 */
	public static String readChars(ByteBuf buf, int len, boolean trim) {
		return readChars(buf, len, trim, CharsetUtil.UTF_8);
	}

	/**
	 * <b>读取定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param len 读取的长度
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String readChars(ByteBuf buf, int len, Charset charset) {
		return readChars(buf, len, false, charset);
	}

	/**
	 * <b>读取定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param len 读取的长度
	 * @param trim 是否要去掉前导空格
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String readChars(ByteBuf buf, int len, boolean trim, Charset charset) {
		CharSequence cs = buf.readCharSequence(len, charset);
		len = cs.length();
		if (trim) {
			int i = 0;
			while (i < len && cs.charAt(i) == StringUtil.CHAR_SPACE)
				i++;
			if (i == len)
				cs = StringUtil.EMPTY;
			else if (i != 0)
				cs = cs.subSequence(i, len);
		}
		return cs.toString();
	}

	/**
	 * <b>写变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @return ByteBuf
	 */
	public static ByteBuf writeVarchar(ByteBuf buf, CharSequence value) {
		return writeVarchar(buf, value, '\0', CharsetUtil.UTF_8);
	}

	/**
	 * <b>写变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param ec 结束符
	 * @return ByteBuf
	 */
	public static ByteBuf writeVarchar(ByteBuf buf, CharSequence value, char ec) {
		return writeVarchar(buf, value, ec, CharsetUtil.UTF_8);
	}

	/**
	 * <b>写变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param charset 字符集
	 * @return ByteBuf
	 */
	public static ByteBuf writeVarchar(ByteBuf buf, CharSequence value, Charset charset) {
		return writeVarchar(buf, value, '\0', charset);
	}

	/**
	 * <b>写变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param ec 结束符
	 * @param charset 字符集
	 * @return ByteBuf
	 */
	public static ByteBuf writeVarchar(ByteBuf buf, CharSequence value, char ec, Charset charset) {
		if (value != null && value.length() != 0)
			buf.writeCharSequence(value, charset);
		buf.writeByte(ec);
		return buf;
	}

	/**
	 * <b>读取变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @return 字符串
	 */
	public static String readVarchar(ByteBuf buf) {
		return readVarchar(buf, '\0', CharsetUtil.UTF_8);
	}

	/**
	 * <b>读取变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param ec 结束符
	 * @return 字符串
	 */
	public static String readVarchar(ByteBuf buf, char ec) {
		return readVarchar(buf, ec, CharsetUtil.UTF_8);
	}

	/**
	 * <b>读取变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String readVarchar(ByteBuf buf, Charset charset) {
		return readVarchar(buf, '\0', charset);
	}

	/**
	 * <b>读取变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param ec 结束符
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String readVarchar(ByteBuf buf, char ec, Charset charset) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte c;
		while (buf.readableBytes() > 0 && (c = buf.readByte()) != ec) {
			baos.write(c);
		}
		try {
			return baos.toString(charset.name());
		} catch (UnsupportedEncodingException e) {
			throw new CommunicateException(e.getMessage(), e);
		}
	}

	/**
	 * <b>写入重复字节数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param c 字符
	 * @param repeat 重复次数
	 * @return ByteBuf
	 */
	public static ByteBuf writeByte(ByteBuf buf, int c, int repeat) {
		for (int i = 0; i < repeat; i++)
			buf.writeByte(c);
		return buf;
	}

	/**
	 * <b>读剩余的全部字节数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @return 字节数组
	 */
	public static byte[] readBytes(ByteBuf buf) {
		return readBytes(buf, buf.readableBytes());
	}

	/**
	 * <b>读字节数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param len 读取长度
	 * @return 字节数组
	 */
	public static byte[] readBytes(ByteBuf buf, int len) {
		byte[] dst = new byte[len];
		buf.readBytes(dst, 0, len);
		return dst;
	}

	/**
	 * <b>读取ByteBuf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param len 读取字节数
	 * @return 新的ByteBuf
	 */
	public static ByteBuf readByteBuf(ByteBuf buf, int len) {
		if (len > 0) {
			ByteBuf dest = Unpooled.buffer(len);
			buf.readBytes(dest, len);
			return dest;
		} else
			return Unpooled.EMPTY_BUFFER;
	}

	/**
	 * <b>读取剩余的ByteBuf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @return 新的ByteBuf
	 */
	public static ByteBuf readByteBuf(ByteBuf buf) {
		return readByteBuf(buf, buf.readableBytes());
	}

	/**
	 * <b>大海捞针。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param haystack 大海
	 * @param pos 开始位置(索引从0开始)
	 * @param needle 针
	 * @return 字节数
	 */
	public static int indexOf(ByteBuf haystack, int pos, byte needle) {
		for (int i = haystack.readerIndex() + pos; i < haystack.writerIndex(); i++) {
			if (haystack.getByte(i) == needle) {
				return i - haystack.readerIndex();
			}
		}
		return -1;
	}

	/**
	 * <b>大海捞针。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param haystack 大海
	 * @param pos 开始位置(索引从0开始)
	 * @param needle 针
	 * @return 字节数
	 */
	public static int indexOf(ByteBuf haystack, int pos, ByteBuf needle) {
		for (int i = haystack.readerIndex() + pos; i < haystack.writerIndex(); i++) {
			int haystackIndex = i;
			int needleIndex;
			for (needleIndex = 0; needleIndex < needle.capacity(); needleIndex++) {
				if (haystack.getByte(haystackIndex) != needle.getByte(needleIndex)) {
					break;
				} else {
					haystackIndex++;
					if (haystackIndex == haystack.writerIndex() && needleIndex != needle.capacity() - 1) {
						return -1;
					}
				}
			}
			//
			if (needleIndex == needle.capacity()) {
				return i - haystack.readerIndex();
			}
		}
		return -1;
	}

	/**
	 * <b>是否是头部字节数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param head 头部字节数组
	 * @return 是否是头部字节数组
	 */
	public static boolean isStartWith(ByteBuf buf, byte[] head) {
		if (head == null || head.length > buf.readableBytes()) {
			return false;
		} else {
			for (int i = 0; i < head.length; i++) {
				if (buf.getByte(buf.readerIndex() + i) != head[i])
					return false;
			}
		}
		return true;
	}

	/**
	 * <b>将分隔符封装成ByteBuf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter 分隔符
	 * @return 分隔符ByteBuf
	 */
	public static ByteBuf wrapDelimiter(String delimiter) {
		return wrapDelimiter(delimiter, CharsetUtil.UTF_8);
	}

	/**
	 * <b>将分隔符封装成ByteBuf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param delimiter 分隔符
	 * @param charset 字符集
	 * @return 分隔符ByteBuf
	 */
	public static ByteBuf wrapDelimiter(String delimiter, Charset charset) {
		ByteBuf buf;
		if (CoreUtil.isHex(delimiter)) {
			byte[] arr = CoreUtil.hexString2ByteArray(delimiter.substring(2));
			buf = Unpooled.wrappedBuffer(arr);
		} else {
			int len = delimiter.length();
			buf = NettyUtil.writeChars(Unpooled.buffer(len, len), delimiter, len, charset);
		}
		return buf;
	}

	/**
	 * <b>writeBCD。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 也支持16进制字符串。
	 * @param buf ByteBuf
	 * @param decimal 十进制数字字符串(len*2)
	 * @param len 写入字节数
	 * @return ByteBuf
	 */
	public static ByteBuf writeBCD(ByteBuf buf, CharSequence decimal, int len) {
		return writeHex(buf, decimal, len);
	}

	/**
	 * <b>readBCD。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 也支持16进制字符串。
	 * @param buf ByteBuf
	 * @param len 读取字节数
	 * @return 十进制数字字符串(len*2)
	 */
	public static String readBCD(ByteBuf buf, int len) {
		return readHex(buf, len);
	}

	/**
	 * <b>写入16进制。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param hex 16进制数字字符串(len*2)
	 * @param len 写入字节数
	 * @return ByteBuf
	 */
	public static ByteBuf writeHex(ByteBuf buf, CharSequence hex, int len) {
		if (hex != null && hex.length() > 0 && len > 0) {
			int valLen = hex.length() >> 1;
			int mod = hex.length() % 2;
			if (mod != 0) {
				hex = new TwoString("0", hex);
				valLen++;
			}
			//
			int l;
			if (len > valLen) {
				NettyUtil.writeByte(buf, 0, len - valLen);
				l = valLen << 1;
			} else {
				l = len << 1;
			}
			int hi, lo;
			for (int i = 0; i < l;) {
				hi = Character.digit(hex.charAt(i++), 16);
				lo = Character.digit(hex.charAt(i++), 16);
				if (hi != 0)
					hi = hi << 4;
				buf.writeByte(hi | lo);
			}
		}
		return buf;
	}

	/**
	 * <b>读取16进制。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param len 读取字节数
	 * @return 16进制数字字符串(len*2)
	 */
	public static String readHex(ByteBuf buf, int len) {
		StringBuilder sb = new StringBuilder(len << 1);
		int b;
		char c;
		for (int i = 0; i < len; i++) {
			b = buf.readUnsignedByte();
			c = Character.forDigit(b >>> 4, 16);
			sb.append(Character.toUpperCase(c));
			c = Character.forDigit(b & 0x0F, 16);
			sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}

	/**
	 * <b>增加引用计数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msg 报文对象
	 * @return 报文对象
	 */
	public static Object retainRC(Object msg) {
		if (msg instanceof ReferenceCounted) {
			ReferenceCounted rc = ((ReferenceCounted) msg);
			if (rc.refCnt() > 0)
				rc.retain();
		}
		return msg;
	}

	/**
	 * <b>释放引用计数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msg 报文对象
	 * @return 是否释放成功，如果没有引用计数需要释放则返回false
	 */
	public static boolean releaseRC(Object msg) {
		if (msg instanceof ReferenceCounted) {
			ReferenceCounted rc = ((ReferenceCounted) msg);
			boolean ok = rc.refCnt() > 0;
			if (ok)
				rc.release();
			return ok;
		}
		return true;
	}

	/**
	 * <b>切片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msg 报文对象
	 * @return 切片报文对象
	 */
	public static Object slice(Object msg) {
		if (msg instanceof ByteBuf)
			return ((ByteBuf) msg).slice();
		else if (msg instanceof ByteBuffer) {
			return ((ByteBuffer) msg).slice();
		} else
			return msg;
	}

	/**
	 * <b>绑定报文编码时使用的分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 当每次编码时使用不同的分隔符时使用。
	 * @param ch Netty通道
	 * @param delimiter 分隔符
	 * @see DefaultDelimiterGetter
	 */
	public static void bindDelimiterForEncode(Channel ch, ByteBuf delimiter) {
		Attribute<ByteBuf> arr = ch.attr(DELIMITER_FOR_ENCODER_KEY);
		arr.set(delimiter);
	}

	/**
	 * <b>绑定报文编码时使用的分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 当每次编码时使用不同的分隔符时使用。
	 * @param ch 封装了Netty通道的伪通道
	 * @param delimiter 分隔符
	 * @see DefaultDelimiterGetter
	 */
	public static void bindDelimiterForEncode(INettyChannel ch, ByteBuf delimiter) {
		bindDelimiterForEncode(ch.channel(), delimiter);
	}

	/**
	 * <b>取回解码器放入的报文分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch Netty通道
	 * @return 分隔符
	 */
	public static ByteBuf retrieveDelimiterForDecode(Channel ch) {
		Attribute<ByteBuf> arr = ch.attr(DELIMITER_FOR_DECODER_KEY);
		return arr.getAndSet(null);
	}

	/**
	 * <b>取回解码器放入的报文分隔符。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch 封装了Netty通道的伪通道
	 * @return 分隔符
	 */
	public static ByteBuf retrieveDelimiterForDecode(INettyChannel ch) {
		return retrieveDelimiterForDecode(ch.channel());
	}

	/**
	 * <b>创建Netty通道Getter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel Netty通道对象
	 * @return Netty通道Getter
	 */
	public static Getter<Channel> createChannelGetter(final Channel channel) {
		return new Getter<Channel>() {
			private final Channel ch = channel;

			@Override
			public Channel get() {
				return ch;
			}
		};
	}

}
