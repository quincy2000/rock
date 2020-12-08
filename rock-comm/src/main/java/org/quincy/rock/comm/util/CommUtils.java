package org.quincy.rock.comm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.List;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.core.lang.TwoString;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>报文处理工具类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2012-2-13 上午10:24:37</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CommUtils {
	/**
	 * 默认的RPC调用超时秒数。
	 */
	public static final int DEFAULT_RPC_INVOKE_TIMEOUT = 10;

	/**
	 * 代表该消息已经处理完成了（后边的处理器会忽略掉已经处理完成的消息）。
	 */
	public static final String COMM_MSG_PROCESS_DONE_KEY = "_processDone";
	/**
	 * 存放忽略响应标识位的key,如果请求方置位忽略响应，则响应方只要处理接受数据即可，不需要给对方应答。
	 */
	public static final String COMM_MSG_IGNORE_RESPONE_KEY = "_ignoreRespone";
	/**
	 * 存放响应返回值的Key,用于不经过报文正文解码器和报文处理器（提前返回）直接返回响应值的情况。
	 */
	public static final String COMM_MSG_DIRECT_RESPONE_KEY = "_directRespone";
	/**
	 * 存放报文ID(也称作流水号)的key。
	 */
	public static final String COMM_MSG_ID_KEY = "_id";
	/**
	 * 存放功能代码的key。
	 */
	public static final String COMM_FUNCTION_CODE_KEY = "_code";
	/**
	 * 存放消息发送者id的key。
	 */
	public static final String COMM_MSG_SENDER_ID_KEY = "_senderId";
	/**
	 * 存放消息接收者id的key。
	 */
	public static final String COMM_MSG_RECEIVER_ID_KEY = "_receiverId";
	/**
	 * 存放消息发布者id的key。
	 */
	public static final String COMM_MSG_PULISHER_ID_KEY = "_pulisherId";
	/**
	 * 存放消息订阅者id的key。
	 */
	public static final String COMM_MSG_SUBSCRIBER_ID_KEY = "_subscriberId";
	/**
	 * 存放消息原始报文的key。
	 */
	public static final String COMM_MSG_ORIGINAL_MESSAGE = "_original_message";
	/**
	 * 存放消息原始报文正文的key。
	 */
	public static final String COMM_MSG_ORIGINAL_CONTENT = "_original_content";
	/**
	 * 存放消息源id的key。
	 */
	public static final String COMM_MSG_SRC_ID_KEY = "_srcId";
	/**
	 * 存放消息目标id的key。
	 */
	public static final String COMM_MSG_DEST_ID_KEY = "_destId";
	/**
	 * 存放消息源地址的key。
	 */
	public static final String COMM_MSG_SRC_ADDRESS_KEY = "_srcAddr";
	/**
	 * 存放消息目标地址的key。
	 */
	public static final String COMM_MSG_DEST_ADDRESS_KEY = "_destAddr";
	/**
	 * 存放通道的key。
	 */
	public static final String COMM_CHANNEL_KEY = "_channel";
	/**
	 * 存放远程终端唯一标识id的key。
	 */
	public static final String COMM_TERMINAL_ID_KEY = "_terminalId";
	/**
	 * 存放报文协议版本号的key。
	 */
	public static final String COMM_MSG_PROTOCOL_VERSION_KEY = "_ver";
	/**
	 * 存放报文时间戳的key(Long类型)。
	 */
	public static final String COMM_MSG_TIMESTAMP_KEY = "_timestamp";
	/**
	 * 存放报文总包数的key。
	 */
	public static final String COMM_MSG_PKG_TOTAL_KEY = "_total";
	/**
	 * 存放报文当前包索引的key(从0开始)。
	 */
	public static final String COMM_MSG_PKG_INDEX_KEY = "_index";
	/**
	 * 存放报文格式类型的key。
	 */
	public static final String COMM_MSG_TYPE_KEY = "_type";
	/**
	 * 存放报文是否需要异步发送的key。
	 */
	public static final String COMM_MSG_ASYNC_SEND_KEY = "_async";
	/**
	 * 存放异步回调function的key。
	 */
	public static final String COMM_ASYNC_CALLBACK_KEY = "_asyncCallback";
	/**
	 * 存放接收标志的key(用来区分发送报文还是接受报文)。
	 */
	public static final String COMM_MSG_RECEIVE_FALG = "_receive_flag_true";

	/**
	 * json格式报文类型。
	 */
	public static final String MESSAGE_TYPE_JSON = "json";
	/**
	 * 二进制格式报文类型。
	 */
	public static final String MESSAGE_TYPE_BINARY = "binary";
	/**
	 * XML格式报文类型。
	 */
	public static final String MESSAGE_TYPE_XML = "xml";
	/**
	 * 普通文本格式报文类型。
	 */
	public static final String MESSAGE_TYPE_TEXT = "text";

	/**
	 * 处理所有格式的报文。
	 */
	public static final String MESSAGE_TYPE_ALL = "all";
	/**
	 * 没有报文正文(不需要报文正文解析器)。
	 */
	public static final String MESSAGE_TYPE_NONE = "none";

	/**
	 * <b>将一个长报文分解成多个短报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 --> 
	 * 仅仅适用于二进制报文。 
	 * @param data 长报文字节数组
	 * @param size 短报文大小
	 * @return 多个短报文,每个短报文是一个字节数组
	 */
	public static byte[][] splitMessage(byte[] data, int size) {
		if (data == null || data.length == 0) {
			return new byte[1][0];
		} else if (data.length <= size) {
			return new byte[][] { data };
		} else {
			int total = data.length;
			int num = (int) Math.ceil((total * 1.0) / size);
			byte[][] rtn = new byte[num][];
			int pos = 0;
			for (int i = 0; i < num; i++) {
				int remain = total - pos;
				int len = remain > size ? size : remain;
				byte[] tmp = new byte[len];
				System.arraycopy(data, pos, tmp, 0, len);
				rtn[i] = tmp;
				pos += len;
			}
			return rtn;
		}
	}

	/**
	 * <b>合并报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 --> 
	 * 仅仅适用于二进制报文。 
	 * @param datas 多个报文,每个报文是一个字节数组
	 * @return 合并后的报文
	 */
	public static byte[] joinMessage(List<byte[]> datas) {
		if (datas == null || datas.size() == 0)
			return new byte[0];
		else if (datas.size() == 1)
			return (byte[]) datas.get(0);
		int len = datas.size();
		int maxSize = len * Array.getLength(datas.get(0));
		ByteArrayOutputStream baos = new ByteArrayOutputStream(maxSize);
		for (int i = 0; i < len; i++) {
			try {
				baos.write(datas.get(i));
			} catch (IOException e) {
				throw new CommunicateException(e);
			}
		}
		return baos.toByteArray();
	}

	private static int _UniqueMessageId4int = StringUtil.random().nextInt(100);

	/**
	 * <b>产生唯一的消息id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 产生唯一的消息id。
	 * @return 唯一的消息id
	 */
	public static synchronized int uniqueMessageIdAsInt() {
		if (_UniqueMessageId4int == Integer.MAX_VALUE)
			_UniqueMessageId4int = StringUtil.random().nextInt(100);
		return ++_UniqueMessageId4int;
	}

	private static short _UniqueMessageId4Short = (short) StringUtil.random().nextInt(100);

	/**
	 * <b>产生唯一的消息id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 产生唯一的消息id。
	 * @return 唯一的消息id
	 */
	public static synchronized int uniqueMessageIdAsShort() {
		if (_UniqueMessageId4Short == Short.MAX_VALUE)
			_UniqueMessageId4Short = (short) StringUtil.random().nextInt(100);
		return ++_UniqueMessageId4Short;
	}

	/**
	 * <b>解析类名后缀。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果有后缀是字符串，则后缀前面必须使用下划线字符分割。
	 * @param claszz 类型
	 * @param isDigit 后缀是数字，否则是字符串
	 * @return 解析出来的后缀
	 */
	public static String parseSuffix(Class<?> claszz, boolean isDigit) {
		String clsName = claszz.getSimpleName();
		StringBuilder sb = new StringBuilder();
		if (isDigit) {
			for (int i = clsName.length() - 1; i > 0; i--) {
				char ch = clsName.charAt(i);
				if (Character.isDigit(ch)) {
					sb.insert(0, ch);
				} else
					break;
			}
		} else {
			for (int i = clsName.length() - 1; i > 0; i--) {
				char ch = clsName.charAt(i);
				if (ch == '_') {
					break;
				} else
					sb.insert(0, ch);
			}
		}
		return (sb.length() == 0 || sb.length() == clsName.length()) ? null : sb.toString();
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
	 * <b>写时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 先写入4字节的无符号秒数，然后写入4字节的毫秒数。
	 * @param buf 字节缓冲区
	 * @param timestamp 时间戳毫秒数
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeTimestamp(ByteBuffer buf, long timestamp) {
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
	 * <b>写入重复字节数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuffer
	 * @param b 字节数据
	 * @param repeat 重复次数
	 * @return ByteBuffer
	 */
	public static ByteBuffer writeByte(ByteBuffer buf, byte b, int repeat) {
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
				writeByte(buf, (byte) 0, len - valLen);
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
}
