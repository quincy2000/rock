package org.quincy.rock.comm.netty.parser;

import java.util.Map;

import org.quincy.rock.comm.parser.CasingListMessage;
import org.quincy.rock.comm.parser.CasingListResultMessage;
import org.quincy.rock.comm.parser.CasingResultMessage;
import org.quincy.rock.comm.parser.Message;
import org.quincy.rock.comm.parser.SimpleBinaryMessageParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>NettySimpleBinaryMessageParser。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月3日 下午3:20:02</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public class NettySimpleBinaryMessageParser<K> extends SimpleBinaryMessageParser<K, ByteBuf> {
	/**
	 * ByteBuf的初始容量。
	 */
	private int initialCapacity = 256;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param messageClassName Message实现类类名
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public NettySimpleBinaryMessageParser(K functionCode, String messageClassName, int casing) {
		super(functionCode, messageClassName, casing);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param messageClassName Message实现类类名
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public NettySimpleBinaryMessageParser(K functionCode, String contentType, String messageClassName, int casing) {
		super(functionCode, contentType, messageClassName, casing);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public NettySimpleBinaryMessageParser(K functionCode, String contentType) {
		super(functionCode, contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public NettySimpleBinaryMessageParser(K functionCode) {
		super(functionCode);
	}

	/**
	 * <b>获得ByteBuf的初始容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建ByteBuf报文缓冲区。
	 * @return ByteBuf的初始容量
	 */
	public int getInitialCapacity() {
		return initialCapacity;
	}

	/**
	 * <b>设置ByteBuf的初始容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建ByteBuf报文缓冲区。
	 * @param initialCapacity ByteBuf的初始容量
	 */
	public void setInitialCapacity(int initialCapacity) {
		this.initialCapacity = initialCapacity;
	}

	/** 
	 * createCasingListMessage。
	 * @see org.quincy.rock.comm.parser.SimpleBinaryMessageParser#createCasingListMessage()
	 */
	@Override
	protected CasingListMessage<ByteBuf> createCasingListMessage() {
		return new NettyCasingListMessage();
	}

	/** 
	 * createCasingResultMessage。
	 * @see org.quincy.rock.comm.parser.SimpleBinaryMessageParser#createCasingResultMessage()
	 */
	@Override
	protected CasingResultMessage<ByteBuf, ?> createCasingResultMessage() {
		return new NettyCasingResultMessage();
	}

	/** 
	 * createCasingListResultMessage。
	 * @see org.quincy.rock.comm.parser.SimpleBinaryMessageParser#createCasingListResultMessage()
	 */
	@Override
	protected CasingListResultMessage<ByteBuf, ?> createCasingListResultMessage() {
		return new NettyCasingListResultMessage();
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.SimpleBinaryMessageParser#createBuffer()
	 */
	@Override
	protected ByteBuf createBuffer() {
		return Unpooled.buffer(initialCapacity);
	}

	/** 
	 * hasRemaining。
	 * @see org.quincy.rock.comm.parser.SimpleBinaryMessageParser#hasRemaining(java.lang.Object)
	 */
	@Override
	protected boolean hasRemaining(ByteBuf buf) {
		return buf.readableBytes() > 0;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.parser.SimpleBinaryMessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<ByteBuf> unpack(ByteBuf message, Map<String, Object> ctx) {
		message = message.retainedSlice();
		try {
			return super.unpack(message, ctx);
		} finally {
			message.release();
		}
	}

	/**
	 * <b>创建SimpleBinaryMessageParser的实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param messageClass Message报文实现类
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @return SimpleBinaryMessageParser
	 */
	public static <K> SimpleBinaryMessageParser<K, ByteBuf> of(K functionCode, Class<? extends Message> messageClass,
			int casing) {
		NettySimpleBinaryMessageParser<K> parser = new NettySimpleBinaryMessageParser<>(functionCode);
		parser.setMessageClass(messageClass);
		parser.setCasing(casing);
		return parser;
	}

	/**
	 * <b>创建SimpleBinaryMessageParser的实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param messageClass Message报文实现类
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @return SimpleBinaryMessageParser
	 */
	public static <K> SimpleBinaryMessageParser<K, ByteBuf> of(K functionCode, String contentType,
			Class<? extends Message> messageClass, int casing) {
		NettySimpleBinaryMessageParser<K> parser = new NettySimpleBinaryMessageParser<>(functionCode, contentType);
		parser.setMessageClass(messageClass);
		parser.setCasing(casing);
		return parser;
	}

	/**
	 * <b>创建SimpleBinaryMessageParser的实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param messageClass Message报文实现类
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @param initialCapacity ByteBuf的初始容量
	 * @return SimpleBinaryMessageParser
	 */
	public static <K> SimpleBinaryMessageParser<K, ByteBuf> of(K functionCode, Class<? extends Message> messageClass,
			int casing, int initialCapacity) {
		NettySimpleBinaryMessageParser<K> parser = new NettySimpleBinaryMessageParser<>(functionCode);
		parser.setMessageClass(messageClass);
		parser.setCasing(casing);
		parser.setInitialCapacity(initialCapacity);
		return parser;
	}

	/**
	 * <b>创建SimpleBinaryMessageParser的实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param messageClass Message报文实现类
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @param initialCapacity ByteBuf的初始容量
	 * @return SimpleBinaryMessageParser
	 */
	public static <K> SimpleBinaryMessageParser<K, ByteBuf> of(K functionCode, String contentType,
			Class<? extends Message> messageClass, int casing, int initialCapacity) {
		NettySimpleBinaryMessageParser<K> parser = new NettySimpleBinaryMessageParser<>(functionCode, contentType);
		parser.setMessageClass(messageClass);
		parser.setCasing(casing);
		parser.setInitialCapacity(initialCapacity);
		return parser;
	}
}
