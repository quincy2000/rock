package org.quincy.rock.comm.netty.parser;

import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Map;

import org.quincy.rock.comm.parser.BinaryMessageParser;
import org.quincy.rock.comm.parser.Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>NettyBinaryMessageParser。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月3日 下午4:51:00</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public abstract class NettyBinaryMessageParser<K> extends BinaryMessageParser<K, ByteBuf> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param initialCapacity Buffer的初始容量
	 */
	public NettyBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType, int initialCapacity) {
		super(functionCode, contentType, initialCapacity);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public NettyBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 * @param initialCapacity Buffer的初始容量
	 */
	public NettyBinaryMessageParser(Collection<String> contentType, int initialCapacity) {
		super(contentType, initialCapacity);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public NettyBinaryMessageParser(Collection<String> contentType) {
		super(contentType);
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.BinaryMessageParser#createBuffer(int, boolean)
	 */
	@Override
	protected ByteBuf createBuffer(int initialCapacity, boolean bigEndian) {
		return Unpooled.buffer(initialCapacity).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.parser.BinaryMessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<ByteBuf> unpack(ByteBuf buf, Map<String, Object> ctx) {
		buf = buf.retain();
		try {
			return super.unpack(buf.order(isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN), ctx);
		} finally {
			buf.release();
		}
	}
}
