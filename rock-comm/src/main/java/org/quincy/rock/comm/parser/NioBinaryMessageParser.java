package org.quincy.rock.comm.parser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Map;

/**
 * <b>NioBinaryMessageParser。</b>
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
public abstract class NioBinaryMessageParser<K> extends BinaryMessageParser<K, ByteBuffer> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param initialCapacity Buffer的初始容量
	 */
	public NioBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType, int initialCapacity) {
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
	public NioBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType) {
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
	public NioBinaryMessageParser(Collection<String> contentType, int initialCapacity) {
		super(contentType, initialCapacity);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public NioBinaryMessageParser(Collection<String> contentType) {
		super(contentType);
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.BinaryMessageParser#createBuffer(int, boolean)
	 */
	@Override
	protected ByteBuffer createBuffer(int initialCapacity, boolean bigEndian) {
		return ByteBuffer.allocate(initialCapacity).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
	}

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.parser.BinaryMessageParser#pack(org.quincy.rock.comm.parser.Message, java.util.Map)
	 */
	@Override
	public ByteBuffer pack(Message<ByteBuffer> value, Map<String, Object> ctx) {
		ByteBuffer buf = super.pack(value, ctx);
		buf.flip();
		return buf;
	}
}
