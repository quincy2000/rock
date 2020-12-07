package org.quincy.rock.comm.parser;

import java.nio.ByteBuffer;

/**
 * <b>NioUpDownBinaryMessageParser。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月3日 下午3:48:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NioUpDownBinaryMessageParser<K> extends UpDownBinaryMessageParser<K, ByteBuffer> {
	/**
	 * ByteBuffer的容量。
	 */
	private int byteBufferCapacity = 4096;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param messageClassName4Up 上行Message实现类类名
	 * @param casing4Up 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @param messageClassName4Down 下行Message实现类类名
	 * @param casing4Down 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public NioUpDownBinaryMessageParser(K functionCode, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down) {
		super(functionCode, messageClassName4Up, casing4Up, messageClassName4Down, casing4Down);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param messageClassName4Up 上行Message实现类类名
	 * @param casing4Up 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @param messageClassName4Down 下行Message实现类类名
	 * @param casing4Down 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public NioUpDownBinaryMessageParser(K functionCode, String contentType, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down) {
		super(functionCode, contentType, messageClassName4Up, casing4Up, messageClassName4Down, casing4Down);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public NioUpDownBinaryMessageParser(K functionCode, String contentType) {
		super(functionCode, contentType);
	}

	/** 
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public NioUpDownBinaryMessageParser(K functionCode) {
		super(functionCode);
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
	 * createCasingListMessage。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createCasingListMessage()
	 */
	@Override
	protected CasingListMessage<ByteBuffer> createCasingListMessage() {
		return new NioCasingListMessage();
	}

	/** 
	 * createCasingResultMessage。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createCasingResultMessage()
	 */
	@Override
	protected CasingResultMessage<ByteBuffer, ?> createCasingResultMessage() {
		return new NioCasingResultMessage();
	}

	/** 
	 * createCasingListResultMessage。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createCasingListResultMessage()
	 */
	@Override
	protected CasingListResultMessage<ByteBuffer, ?> createCasingListResultMessage() {
		return new NioCasingListResultMessage();
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createBuffer()
	 */
	@Override
	protected ByteBuffer createBuffer() {
		return ByteBuffer.allocate(this.getByteBufferCapacity());
	}

	/** 
	 * hasRemaining。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#hasRemaining(java.lang.Object)
	 */
	@Override
	protected boolean hasRemaining(ByteBuffer buf) {
		return buf.hasRemaining();
	}

}
