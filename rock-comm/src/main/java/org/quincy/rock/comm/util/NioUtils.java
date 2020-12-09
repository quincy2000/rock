package org.quincy.rock.comm.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.quincy.rock.core.lang.TwoString;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>NioUtils。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月9日 下午3:59:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class NioUtils {
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
		if (data instanceof ByteBuffer) {
			ByteBuffer buf = ((ByteBuffer) data).slice();
			hex = readHex(buf, buf.remaining());
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
	 * <b>确保缓冲区容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 实现缓冲区的自动增长逻辑。
	 * @param buf 缓冲区
	 * @param dataSize 待放入的数据字节数
	 * @return 足够容量的缓冲区
	 */
	public static ByteBuffer ensureCapacity(ByteBuffer buf, int dataSize) {
		if (buf.remaining() < dataSize) {
			ByteBuffer newBuf = ByteBuffer.allocate((buf.capacity() + dataSize) * 3 / 2);
			buf.flip();
			newBuf.put(buf);
			return newBuf;
		} else {
			return buf;
		}
	}

	/**
	 * <b>写时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 先写入4字节的无符号秒数，然后写入4字节的毫秒数。
	 * @param buf 字节缓冲区
	 * @param timestamp 时间戳毫秒数
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeTimestamp(ByteBuffer buf, long timestamp) {
		buf = ensureCapacity(buf, 8);
		long left = timestamp / 1000;
		long right = timestamp % 1000;
		buf.putInt((int) left);
		buf.putInt((int) right);
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
	public static long readTimestamp(ByteBuffer buf) {
		return (buf.getInt() & 0xFFFFFFFFL) * 1000 + buf.getInt();
	}

	/**
	 * <b>写入一个字节。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuffer
	 * @param b 字节数据
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeByte(ByteBuffer buf, int b) {
		buf.put((byte) b);
		return buf;
	}

	/**
	 * <b>写入重复字节数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuffer
	 * @param c 字节数据
	 * @param repeat 重复次数
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeByte(ByteBuffer buf, int c, int repeat) {
		byte b = (byte) c;
		for (int i = 0; i < repeat; i++)
			buf.put(b);
		return buf;
	}

	/**
	 * <b>读字节数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuffer
	 * @param len 读取长度
	 * @return 字节数组
	 */
	public static byte[] readBytes(ByteBuffer buf, int len) {
		byte[] dst = new byte[len];
		buf.get(dst, 0, len);
		return dst;
	}

	/**
	 * <b>读剩余的全部字节数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuffer
	 * @return 字节数组
	 */
	public static byte[] readBytes(ByteBuffer buf) {
		return readBytes(buf, buf.remaining());
	}

	/**
	 * <b>writeBCD。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 也支持16进制字符串。
	 * @param buf ByteBuffer
	 * @param decimal 十进制数字字符串(len*2)
	 * @param len 写入字节数
	 * @return ByteBuf
	 */
	public static ByteBuffer writeBCD(ByteBuffer buf, CharSequence decimal, int len) {
		return writeHex(buf, decimal, len);
	}

	/**
	 * <b>readBCD。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 也支持16进制字符串。
	 * @param buf ByteBuffer
	 * @param len 读取字节数
	 * @return 十进制数字字符串(len*2)
	 */
	public static String readBCD(ByteBuffer buf, int len) {
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
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeHex(ByteBuffer buf, CharSequence hex, int len) {
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
				writeByte(buf, 0, len - valLen);
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
				buf.put((byte) (hi | lo));
			}
		}
		return buf;
	}

	/**
	 * <b>读取16进制。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuffer
	 * @param len 读取字节数
	 * @return 16进制数字字符串(len*2)
	 */
	public static String readHex(ByteBuffer buf, int len) {
		StringBuilder sb = new StringBuilder(len << 1);
		int b;
		char c;
		for (int i = 0; i < len; i++) {
			b = buf.get() & 0xFFFF;
			c = Character.forDigit(b >>> 4, 16);
			sb.append(Character.toUpperCase(c));
			c = Character.forDigit(b & 0x0F, 16);
			sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}

	/**
	 * <b>写入定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果长度不够则前面填充空格。
	 * 仅支持ISO-8859-1字符集。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param len 写入长度
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeChars(ByteBuffer buf, String value, int len) {
		return writeChars(buf, value, len, StringUtil.CHAR_SPACE);
	}

	/**
	 * <b>写入定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 前面填充字符到指定长度。
	 * 仅支持ISO-8859-1字符集。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @param len 写入长度
	 * @param padding 前面填充字符
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeChars(ByteBuffer buf, String value, int len, char padding) {
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
		if (valSize > 0) {
			byte[] bs = value.getBytes(StringUtil.ISO_8859_1);
			buf.put(bs, 0, valSize);
		}
		return buf;
	}

	/**
	 * <b>读取定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 仅支持ISO-8859-1字符集。
	 * @param buf 字节缓冲区
	 * @param len 读取的长度
	 * @return 字符串
	 */
	public static String readChars(ByteBuffer buf, int len) {
		return readChars(buf, len, false);
	}

	/**
	 * <b>读取定长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 仅支持ISO-8859-1字符集。
	 * @param buf 字节缓冲区
	 * @param len 读取的长度
	 * @param trim 是否要去掉前导空格
	 * @return 字符串
	 */
	public static String readChars(ByteBuffer buf, int len, boolean trim) {
		String cs = new String(readBytes(buf, len), StringUtil.ISO_8859_1);
		return trim ? cs.trim() : cs;
	}

	/**
	 * <b>写变长字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 字节缓冲区
	 * @param value 字符串
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeVarchar(ByteBuffer buf, String value) {
		return writeVarchar(buf, value, '\0', StringUtil.UTF_8);
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
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeVarchar(ByteBuffer buf, String value, char ec, Charset charset) {
		if (value != null && value.length() != 0) {
			buf.put(value.getBytes(charset));
		}
		writeByte(buf, ec);
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
	public static String readVarchar(ByteBuffer buf) {
		return readVarchar(buf, '\0', StringUtil.UTF_8);
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
	public static String readVarchar(ByteBuffer buf, char ec, Charset charset) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte c;
		while (buf.hasRemaining() && (c = buf.get()) != ec) {
			baos.write(c);
		}
		return new String(baos.toByteArray(), charset);
	}
}
