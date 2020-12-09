package org.quincy.rock.comm.parser;

import java.util.List;
import java.util.Map;

import org.quincy.rock.comm.AbstractMessageParser;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.CoreUtil;

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
public abstract class SimpleBinaryMessageParser<K, BUF> extends AbstractMessageParser<K, BUF, Message<BUF>> {
	/**
	 * 结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	private int casing = 0;
	/**
	 * Message报文实现类。
	 */
	private Class<? extends Message> messageClass;
	/**
	 * Buffer的初始容量。
	 */
	private int initialCapacity = 256;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public SimpleBinaryMessageParser(K functionCode) {
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
	public SimpleBinaryMessageParser(K functionCode, String messageClassName, int casing) {
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
	public SimpleBinaryMessageParser(K functionCode, String contentType) {
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
	public SimpleBinaryMessageParser(K functionCode, String contentType, String messageClassName, int casing) {
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
	 * <b>获得Buffer的初始容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建报文缓冲区。
	 * @return Buffer的初始容量
	 */
	public int getInitialCapacity() {
		return initialCapacity;
	}

	/**
	 * <b>设置Buffer的初始容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建报文缓冲区。。
	 * @param initialCapacity Buffer的初始容量
	 */
	public void setInitialCapacity(int initialCapacity) {
		this.initialCapacity = initialCapacity;
	}

	/**
	 * <b>创建CasingListMessage。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CasingListMessage
	 */
	protected abstract CasingListMessage<BUF> createCasingListMessage();

	/**
	 * <b>创建CasingResultMessage。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CasingResultMessage
	 */
	protected abstract CasingResultMessage<BUF, ?> createCasingResultMessage();

	/**
	 * <b>创建CasingListResultMessage。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CasingListResultMessage
	 */
	protected abstract CasingListResultMessage<BUF, ?> createCasingListResultMessage();

	/**
	 * <b>创建缓冲区。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 发送和pack报文时使用该缓冲区存放报文数据。
	 * @param initialCapacity Buffer的初始容量
	 * @return 缓冲区
	 */
	protected abstract BUF createBuffer(int initialCapacity);

	/**
	 * <b>缓冲区中是否还有未读完的字节。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf 缓冲区
	 * @return 缓冲区中是否还有未读完的字节
	 */
	protected abstract boolean hasRemaining(BUF buf);

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public BUF pack(Message<BUF> value, Map<String, Object> ctx) {
		Integer initSize = (Integer) ctx.get(CommUtils.COMM_BUFFER_INIT_SIZE);
		BUF buf = this.createBuffer(initSize == null ? initialCapacity : initSize);
		buf = value.toBinary(buf, ctx);
		if (casing == 1) {
			CasingListMessage clm = (CasingListMessage) value;
			List<Message> list = clm.getData();
			if (list != null && !list.isEmpty()) {
				for (Message<BUF> data : list) {
					buf = data.toBinary(buf, ctx);
				}
			}
		} else if (casing == 2) {
			CasingResultMessage crm = (CasingResultMessage) value;
			Message<BUF> data = crm.getData();
			if (data != null)
				buf = data.toBinary(buf, ctx);
		} else if (casing == 3) {
			CasingListResultMessage clrm = (CasingListResultMessage) value;
			List<Message> list = clrm.getData();
			if (list != null && !list.isEmpty()) {
				for (Message<BUF> data : list) {
					buf = data.toBinary(buf, ctx);
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
	public Message<BUF> unpack(BUF message, Map<String, Object> ctx) {
		Message vo = null;
		switch (casing) {
		case 0:
			vo = CoreUtil.constructor(messageClass).fromBinary(message, ctx);
			break;
		case 1:
			CasingListMessage<BUF> clm = this.createCasingListMessage();
			clm.fromBinary(message, ctx);
			while (hasRemaining(message)) {
				clm.addData(CoreUtil.constructor(messageClass).fromBinary(message, ctx));
			}
			vo = clm;
			break;
		case 2:
			CasingResultMessage<BUF, ?> crm = this.createCasingResultMessage();
			crm.fromBinary(message, ctx);
			if (hasRemaining(message)) {
				crm.setData(CoreUtil.constructor(messageClass).fromBinary(message, ctx));
			}
			vo = crm;
			break;
		case 3:
			CasingListResultMessage<BUF, ?> clrm = this.createCasingListResultMessage();
			clrm.fromBinary(message, ctx);
			while (hasRemaining(message)) {
				clrm.addData(CoreUtil.constructor(messageClass).fromBinary(message, ctx));
			}
			vo = clrm;
			break;
		default:
			throw new UnsupportException("casing:" + casing);
		}
		return vo;
	}
}
