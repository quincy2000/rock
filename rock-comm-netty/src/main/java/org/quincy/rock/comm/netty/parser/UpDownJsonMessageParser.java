package org.quincy.rock.comm.netty.parser;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;

import org.quincy.rock.comm.AbstractMessageParser;
import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.CharsetUtil;

/**
 * <b>同时支持上下行报文的Json报文解析器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2019年2月14日 上午9:56:05</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UpDownJsonMessageParser extends AbstractMessageParser<Integer, ByteBuf, Message> {
	/**
	 * JSON转换器。
	 */
	protected JsonConverter jsonConverter;
	/**
	 * 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	private int casing4Up = 0;
	/**
	 * 上行Message报文实现类。
	 */
	private Class<? extends Message> messageClass4Up;
	/**
	 * 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	private int casing4Down = 0;
	/**
	 * 下行Message报文实现类。
	 */
	private Class<? extends Message> messageClass4Down;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public UpDownJsonMessageParser(Integer functionCode) {
		this.addFunctionCode(functionCode);
		this.addContentType(CommUtils.MESSAGE_TYPE_JSON);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public UpDownJsonMessageParser(Integer functionCode, String contentType) {
		this.addFunctionCode(functionCode);
		this.addContentType(contentType);
	}

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
	public UpDownJsonMessageParser(Integer functionCode, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down) {
		this(functionCode);
		this.setMessageClassName4Up(messageClassName4Up);
		this.setCasing4Up(casing4Up);
		this.setMessageClassName4Down(messageClassName4Down);
		this.setCasing4Down(casing4Down);
	}

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
	 * @param jsonConverter JSON转换器
	 */
	public UpDownJsonMessageParser(Integer functionCode, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down, JsonConverter jsonConverter) {
		this(functionCode);
		this.setMessageClassName4Up(messageClassName4Up);
		this.setCasing4Up(casing4Up);
		this.setMessageClassName4Down(messageClassName4Down);
		this.setCasing4Down(casing4Down);
		this.setJsonConverter(jsonConverter);
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
	public UpDownJsonMessageParser(Integer functionCode, String contentType, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down) {
		this(functionCode, contentType);
		this.setMessageClassName4Up(messageClassName4Up);
		this.setCasing4Up(casing4Up);
		this.setMessageClassName4Down(messageClassName4Down);
		this.setCasing4Down(casing4Down);
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
	 * @param jsonConverter JSON转换器
	 */
	public UpDownJsonMessageParser(Integer functionCode, String contentType, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down, JsonConverter jsonConverter) {
		this(functionCode, contentType);
		this.setMessageClassName4Up(messageClassName4Up);
		this.setCasing4Up(casing4Up);
		this.setMessageClassName4Down(messageClassName4Down);
		this.setCasing4Down(casing4Down);
		this.setJsonConverter(jsonConverter);
	}

	/**
	 * <b>获得JSON转换器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return JSON转换器
	 */
	public JsonConverter getJsonConverter() {
		return jsonConverter;
	}

	/**
	 * <b>设置JSON转换器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param jsonConverter JSON转换器
	 */
	public void setJsonConverter(JsonConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	/**
	 * <b>获得上行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)
	 */
	public int getCasing4Up() {
		return casing4Up;
	}

	/**
	 * <b>设置上行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param casing4Up 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)
	 */
	public void setCasing4Up(int casing4Up) {
		this.casing4Up = casing4Up;
	}

	/**
	 * <b>获得上行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 上行Message报文实现类
	 */
	public Class<? extends Message> getMessageClass4Up() {
		return messageClass4Up;
	}

	/**
	 * <b>设置上行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageClass4Up 上行Message报文实现类
	 */
	public void setMessageClass4Up(Class<? extends Message> messageClass4Up) {
		this.messageClass4Up = messageClass4Up;
	}

	/**
	 * <b>获得上行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 上行Message报文实现类
	 */
	public String getMessageClassName4Up() {
		Class<? extends Message> clazz = getMessageClass4Up();
		return clazz == null ? null : clazz.getName();
	}

	/**
	 * <b>设置上行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className 上行Message报文实现类
	 */
	public void setMessageClassName4Up(String className4Up) {
		this.setMessageClass4Up((Class) CoreUtil.findClass(className4Up));
	}

	/**
	 * <b>获得下行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)
	 */
	public int getCasing4Down() {
		return casing4Down;
	}

	/**
	 * <b>设置下行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param casing4Down 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)
	 */
	public void setCasing4Down(int casing4Down) {
		this.casing4Down = casing4Down;
	}

	/**
	 * <b>获得下行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 下行Message报文实现类
	 */
	public Class<? extends Message> getMessageClass4Down() {
		return messageClass4Down;
	}

	/**
	 * <b>设置下行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageClass4Down 下行Message报文实现类
	 */
	public void setMessageClass4Down(Class<? extends Message> messageClass4Down) {
		this.messageClass4Down = messageClass4Down;
	}

	/**
	 * <b>获得下行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 下行Message报文实现类
	 */
	public String getMessageClassName4Down() {
		Class<? extends Message> clazz = getMessageClass4Down();
		return clazz == null ? null : clazz.getName();
	}

	/**
	 * <b>设置下行Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className 下行Message报文实现类
	 */
	public void setMessageClassName4Down(String className4Down) {
		this.setMessageClass4Down((Class) CoreUtil.findClass(className4Down));
	}

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public ByteBuf pack(Message value, Map<String, Object> ctx) {
		String json = toJson(value, ctx);
		return ByteBufUtil.encodeString(UnpooledByteBufAllocator.DEFAULT, CharBuffer.wrap(json), CharsetUtil.UTF_8);
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message unpack(ByteBuf message, Map<String, Object> ctx) {
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
	public String toJson(Message value, Map<String, Object> ctx) {
		boolean up = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_RECEIVE_FALG));
		int casing = up ? casing4Up : casing4Down;
		if (casing == 1) {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			List<Message> list = ((CasingListMessage) value).getData();
			for (int i = 0, l = list.size(); i < l; i++) {
				if (i != 0)
					sb.append(StringUtil.CHAR_COMMA);
				sb.append(jsonConverter.toJson(list.get(i)));
			}
			sb.append(']');
			return sb.toString();
		} else {
			return jsonConverter.toJson(value);
		}
	}

	/**
	 * <b>将json字符串解析成报文值对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json message字符串
	 * @param ctx 上下文
	 * @return 报文值对象
	 */
	public Message fromJson(String message, Map<String, Object> ctx) {
		Message vo = null;
		boolean up = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_RECEIVE_FALG));
		int casing = up ? casing4Up : casing4Down;
		Class<? extends Message> clazz = up ? messageClass4Up : messageClass4Down;
		if (casing == 0)
			vo = jsonConverter.fromJson(message, clazz);
		else if (casing == 1) {
			CasingListMessage clm = new CasingListMessage();
			clm.setData(jsonConverter.fromJsonArray(message, clazz));
			vo = clm;
		} else {
			int leftPos = message.indexOf("{", 4);
			int rigthPos = message.lastIndexOf('}', message.length() - 2);
			String jsonInner = message.substring(leftPos, rigthPos + 1);
			String jsonOuter = message.substring(0, leftPos) + "null"
					+ message.substring(rigthPos + 1, message.length());
			if (casing == 2) {
				vo = jsonConverter.fromJson(jsonInner, clazz);
				CasingResultMessage crm = jsonConverter.fromJson(jsonOuter, CasingResultMessage.class);
				crm.setData(vo);
				vo = crm;
			} else if (casing == 3) {
				List<? extends Message> list = jsonConverter.fromJsonArray(jsonInner, clazz);
				CasingListResultMessage clrm = jsonConverter.fromJson(jsonOuter, CasingListResultMessage.class);
				clrm.setData(list);
				vo = clrm;
			} else
				throw new UnsupportException("casing:" + casing);
		}
		//
		return vo;
	}
}
