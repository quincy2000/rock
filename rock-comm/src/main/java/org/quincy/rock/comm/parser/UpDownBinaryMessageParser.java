package org.quincy.rock.comm.parser;

import java.util.List;
import java.util.Map;

import org.quincy.rock.comm.AbstractMessageParser;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.CoreUtil;

/**
 * <b>同时支持上下行报文的二进制报文解析器。</b>
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
public abstract class UpDownBinaryMessageParser<K, BUF> extends AbstractMessageParser<K, BUF, Message<BUF>> {
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
	 * 是否是客户端。
	 */
	private boolean client;
	/**
	 * Buffer的初始容量。
	 */
	private int initialCapacity = 256;
	/**
	 * 是否是大端协议。
	 */
	private boolean bigEndian = true;

	/** 
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public UpDownBinaryMessageParser(K functionCode) {
		this.addFunctionCode(functionCode);
		this.addContentType(CommUtils.MESSAGE_TYPE_BINARY);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public UpDownBinaryMessageParser(K functionCode, String contentType) {
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
	public UpDownBinaryMessageParser(K functionCode, String messageClassName4Up, int casing4Up,
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
	 * @param contentType 内容类型
	 * @param messageClassName4Up 上行Message实现类类名
	 * @param casing4Up 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 * @param messageClassName4Down 下行Message实现类类名
	 * @param casing4Down 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public UpDownBinaryMessageParser(K functionCode, String contentType, String messageClassName4Up, int casing4Up,
			String messageClassName4Down, int casing4Down) {
		this(functionCode, contentType);
		this.setMessageClassName4Up(messageClassName4Up);
		this.setCasing4Up(casing4Up);
		this.setMessageClassName4Down(messageClassName4Down);
		this.setCasing4Down(casing4Down);
	}

	/**
	 * <b>获得上行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public int getCasing4Up() {
		return casing4Up;
	}

	/**
	 * <b>设置上行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param casing4Up 上行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
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
	 * @param className4Up 上行Message报文实现类
	 */
	public void setMessageClassName4Up(String className4Up) {
		this.setMessageClass4Up((Class) CoreUtil.findClass(className4Up));
	}

	/**
	 * <b>获得下行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
	 */
	public int getCasing4Down() {
		return casing4Down;
	}

	/**
	 * <b>设置下行结果嵌套标记。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param casing4Down 下行结果嵌套标记(0-对象,1-数组,2-嵌套结果对象,3-嵌套结果数组)。
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
	 * @param className4Down 下行Message报文实现类
	 */
	public void setMessageClassName4Down(String className4Down) {
		this.setMessageClass4Down((Class) CoreUtil.findClass(className4Down));
	}

	/**
	 * <b>是否是客户端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是客户端
	 */
	public boolean isClient() {
		return client;
	}

	/**
	 * <b>是否是客户端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param client 是否是客户端
	 */
	public void setClient(boolean client) {
		this.client = client;
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
	 * <b>是否是大端协议。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是大端协议
	 */
	public boolean isBigEndian() {
		return bigEndian;
	}

	/**
	 * <b>是否是大端协议。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bigEndian 是否是大端协议
	 */
	public void setBigEndian(boolean bigEndian) {
		this.bigEndian = bigEndian;
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
	 * @param bigEndian 是否是大端协议
	 * @return 缓冲区
	 */
	protected abstract BUF createBuffer(int initialCapacity, boolean bigEndian);

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
		boolean received = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_RECEIVE_FALG));
		int casing = client ? (received ? casing4Down : casing4Up) : (received ? casing4Up : casing4Down);
		Integer initSize = (Integer) ctx.get(CommUtils.COMM_BUFFER_INIT_SIZE);
		BUF buf = this.createBuffer(initSize == null ? initialCapacity : initSize, bigEndian);
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
		boolean received = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_RECEIVE_FALG));
		int casing = client ? (received ? casing4Down : casing4Up) : (received ? casing4Up : casing4Down);
		Class<? extends Message> messageClass = client ? (received ? messageClass4Down : messageClass4Up)
				: (received ? messageClass4Up : messageClass4Down);
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
