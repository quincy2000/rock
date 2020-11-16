package org.quincy.rock.comm.netty.parser;

import java.util.List;
import java.util.Map;

import org.quincy.rock.comm.AbstractMessageParser;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.CoreUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>简单的二进制报文解析器。</b>
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
public class SimpleBinaryMessageParser extends AbstractMessageParser<Integer, ByteBuf, Message> {
	/**
	 * 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	private int casing = 0;
	/**
	 * Message报文实现类。
	 */
	private Class<? extends Message> messageClass;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public SimpleBinaryMessageParser(Integer functionCode) {
		this.addFunctionCode(functionCode);
		this.addContentType(CommUtils.MESSAGE_TYPE_BINARY);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param messageClassName Message实现类类名
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public SimpleBinaryMessageParser(Integer functionCode, String messageClassName, int casing) {
		this(functionCode);
		this.setMessageClassName(messageClassName);
		this.setCasing(casing);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public SimpleBinaryMessageParser(Integer functionCode, String contentType) {
		this.addFunctionCode(functionCode);
		this.addContentType(contentType);
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
	public SimpleBinaryMessageParser(Integer functionCode, String contentType, String messageClassName, int casing) {
		this(functionCode, contentType);
		this.setMessageClassName(messageClassName);
		this.setCasing(casing);
	}

	/**
	 * <b>获得结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public int getCasing() {
		return casing;
	}

	/**
	 * <b>设置结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param casing 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public void setCasing(int casing) {
		this.casing = casing;
	}

	/**
	 * <b>获得Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Message报文实现类
	 */
	public Class<? extends Message> getMessageClass() {
		return messageClass;
	}

	/**
	 * <b>设置Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageClass Message报文实现类
	 */
	public void setMessageClass(Class<? extends Message> messageClass) {
		this.messageClass = messageClass;
	}

	/**
	 * <b>获得Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Message报文实现类
	 */
	public String getMessageClassName() {
		Class<? extends Message> clazz = getMessageClass();
		return clazz == null ? null : clazz.getName();
	}

	/**
	 * <b>设置Message报文实现类。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param className Message报文实现类
	 */
	public void setMessageClassName(String className) {
		this.setMessageClass((Class) CoreUtil.findClass(className));
	}

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public ByteBuf pack(Message value, Map<String, Object> ctx) {
		ByteBuf buf = Unpooled.buffer();
		value.toBinary(buf, ctx);
		if (casing == 1) {
			List<? extends Message> list = ((CasingListMessage<?>) value).getData();
			if (list != null && !list.isEmpty()) {
				for (Message data : list) {
					data.toBinary(buf, ctx);
				}
			}
		} else if (casing == 2) {
			Message data = ((CasingResultMessage<?>) value).getData();
			if (data != null)
				data.toBinary(buf, ctx);
		} else if (casing == 3) {
			List<? extends Message> list = ((CasingListResultMessage<?>) value).getData();
			if (list != null && !list.isEmpty()) {
				for (Message data : list) {
					data.toBinary(buf, ctx);
				}
			}
		}
		return buf;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message unpack(ByteBuf message, Map<String, Object> ctx) {
		Message vo = null;
		message = message.retainedSlice();
		try {
			switch (casing) {
			case 0:
				vo = CoreUtil.constructor(messageClass).fromBinary(message, ctx);
				break;
			case 1:
				CasingListMessage<Message> clm = new CasingListMessage<>();
				clm.fromBinary(message, ctx);
				while (message.readableBytes() > 0) {
					clm.addData(CoreUtil.constructor(messageClass).fromBinary(message, ctx));
				}
				vo = clm;
				break;
			case 2:
				CasingResultMessage<Message> crm = new CasingResultMessage<>();
				crm.fromBinary(message, ctx);
				if (message.readableBytes() > 0) {
					crm.setData(CoreUtil.constructor(messageClass).fromBinary(message, ctx));
				}
				vo = crm;
				break;
			case 3:
				CasingListResultMessage<Message> clrm = new CasingListResultMessage<>();
				clrm.fromBinary(message, ctx);
				while (message.readableBytes() > 0) {
					clrm.addData(CoreUtil.constructor(messageClass).fromBinary(message, ctx));
				}
				vo = clrm;
				break;
			default:
				throw new UnsupportException("casing:" + casing);
			}
		} finally {
			message.release();
		}
		return vo;
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
	public static SimpleBinaryMessageParser of(Integer functionCode, Class<? extends Message> messageClass,
			int casing) {
		SimpleBinaryMessageParser parser = new SimpleBinaryMessageParser(functionCode);
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
	public static SimpleBinaryMessageParser of(Integer functionCode, String contentType,
			Class<? extends Message> messageClass, int casing) {
		SimpleBinaryMessageParser parser = new SimpleBinaryMessageParser(functionCode, contentType);
		parser.setMessageClass(messageClass);
		parser.setCasing(casing);
		return parser;
	}
}
