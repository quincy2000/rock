package org.quincy.rock.comm.netty.parser;

import java.nio.ByteOrder;
import java.util.Map;

import org.quincy.rock.comm.parser.CasingListMessage;
import org.quincy.rock.comm.parser.CasingListResultMessage;
import org.quincy.rock.comm.parser.CasingResultMessage;
import org.quincy.rock.comm.parser.Message;
import org.quincy.rock.comm.parser.UpDownBinaryMessageParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>NettyUpDownBinaryMessageParser。</b>
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
public class NettyUpDownBinaryMessageParser<K> extends UpDownBinaryMessageParser<K, ByteBuf> {

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
	public NettyUpDownBinaryMessageParser(K functionCode, String messageClassName4Up, int casing4Up,
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
	public NettyUpDownBinaryMessageParser(K functionCode, String contentType, String messageClassName4Up, int casing4Up,
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
	public NettyUpDownBinaryMessageParser(K functionCode, String contentType) {
		super(functionCode, contentType);
	}

	/** 
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public NettyUpDownBinaryMessageParser(K functionCode) {
		super(functionCode);
	}

	/** 
	 * createCasingListMessage。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createCasingListMessage()
	 */
	@Override
	protected CasingListMessage<ByteBuf> createCasingListMessage() {
		return new NettyCasingListMessage();
	}

	/** 
	 * createCasingResultMessage。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createCasingResultMessage()
	 */
	@Override
	protected CasingResultMessage<ByteBuf, ?> createCasingResultMessage() {
		return new NettyCasingResultMessage();
	}

	/** 
	 * createCasingListResultMessage。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createCasingListResultMessage()
	 */
	@Override
	protected CasingListResultMessage<ByteBuf, ?> createCasingListResultMessage() {
		return new NettyCasingListResultMessage();
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#createBuffer(int, boolean)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected ByteBuf createBuffer(int initialCapacity, boolean bigEndian) {
		return Unpooled.buffer(initialCapacity).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
	}
	
	/** 
	 * hasRemaining。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#hasRemaining(java.lang.Object)
	 */
	@Override
	protected boolean hasRemaining(ByteBuf buf) {
		return buf.readableBytes() > 0;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.parser.UpDownBinaryMessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<ByteBuf> unpack(ByteBuf message, Map<String, Object> ctx) {
		message = message.retain();
		try {
			return super.unpack(message, ctx);
		} finally {
			message.release();
		}
	}
}
