package org.quincy.rock.comm.netty.parser;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.comm.util.CommUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.CharsetUtil;

/**
 * <b>json报文解析器基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 提供非嵌套报文支持。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月14日 下午4:44:16</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class JsonMessageParser<V extends Message> extends MessageParserBase<V> {
	/**
	 * jsonConverter。
	 */
	protected JsonConverter jsonConverter;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 默认内容类型为CommUtils.MESSAGE_TYPE_JSON。
	 */
	public JsonMessageParser() {
		super(Arrays.asList(CommUtils.MESSAGE_TYPE_JSON));
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public JsonMessageParser(Collection<String> contentType) {
		super(contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public JsonMessageParser(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/**
	 * <b>getJsonConverter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public JsonConverter getJsonConverter() {
		return jsonConverter;
	}

	/**
	 * <b>setJsonConverter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jsonConverter
	 */
	public void setJsonConverter(JsonConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public final ByteBuf pack(V value, Map<String, Object> ctx) {
		String json = toJson(value, ctx);
		return ByteBufUtil.encodeString(UnpooledByteBufAllocator.DEFAULT, CharBuffer.wrap(json), CharsetUtil.UTF_8);
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public final V unpack(ByteBuf message, Map<String, Object> ctx) {
		String json;
		message = message.retainedSlice();
		try {
			json = NettyUtil.readChars(message, message.readableBytes(), false);
		} finally {
			message.release();
		}
		return fromJson(json, ctx);
	}

	/**
	 * <b>将报文值对象转乘json字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 报文值对象
	 * @param ctx 上下文
	 * @return json字符串
	 */
	public String toJson(V value, Map<String, Object> ctx) {
		String json = jsonConverter.toJson(value);
		return json;
	}

	/**
	 * <b>将json字符串解析成报文值对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message json字符串
	 * @param ctx 上下文
	 * @return 报文值对象
	 */
	public V fromJson(String message, Map<String, Object> ctx) {
		Class<? extends Message> clazz = getMessageClass();
		Message vo = clazz == null ? null : jsonConverter.fromJson(message, clazz);
		return (V) vo;
	}
}
