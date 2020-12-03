package org.quincy.rock.comm.parser;

import java.nio.ByteBuffer;
import java.util.Collection;

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
	 * ByteBuffer的容量。
	 */
	private int byteBufferCapacity = 4096;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public NioBinaryMessageParser(Collection<String> contentType) {
		this(contentType, 4096);
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
		this(functionCode, contentType, 4096);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 * @param byteBufferCapacity ByteBuffer的容量
	 */
	public NioBinaryMessageParser(Collection<String> contentType, int byteBufferCapacity) {
		super(contentType);
		this.setByteBufferCapacity(byteBufferCapacity);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param byteBufferCapacity ByteBuffer的容量
	 */
	public NioBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType, int byteBufferCapacity) {
		super(functionCode, contentType);
		this.setByteBufferCapacity(byteBufferCapacity);
	}

	/**
	 * <b>获得ByteBuffer的容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建ByteBuffer报文缓冲区。
	 * @return ByteBuffer的容量
	 */
	public int getByteBufferCapacity() {
		return byteBufferCapacity;
	}

	/**
	 * <b>设置ByteBuffer的容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建ByteBuffer报文缓冲区。
	 * @param byteBufferCapacity ByteBuffer的容量
	 */
	public void setByteBufferCapacity(int byteBufferCapacity) {
		this.byteBufferCapacity = byteBufferCapacity;
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.BinaryMessageParser#createBuffer()
	 */
	@Override
	protected ByteBuffer createBuffer() {
		return ByteBuffer.allocate(this.getByteBufferCapacity());
	}
}
