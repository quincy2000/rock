package org.quincy.rock.comm.parser;

import java.util.Collection;
import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>BinaryMessageParser。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月3日 下午4:35:39</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class BinaryMessageParser<K, BUF> extends MessageParser4Suffix<K, BUF, Message<BUF>> {
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
	 * @param contentType 内容类型
	 */
	public BinaryMessageParser(Collection<String> contentType) {
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
	public BinaryMessageParser(Collection<K> functionCode, Collection<String> contentType) {
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
	public BinaryMessageParser(Collection<String> contentType, int initialCapacity) {
		super(contentType);
		this.setInitialCapacity(initialCapacity);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param initialCapacity Buffer的初始容量
	 */
	public BinaryMessageParser(Collection<K> functionCode, Collection<String> contentType, int initialCapacity) {
		super(functionCode, contentType);
		this.setInitialCapacity(initialCapacity);
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
	 * <b>创建Message的新实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Message的新实例
	 */
	protected abstract Message<BUF> newMessage();

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public BUF pack(Message<BUF> value, Map<String, Object> ctx) {
		Integer initSize = (Integer) ctx.get(CommUtils.COMM_BUFFER_INIT_SIZE);
		BUF buf = this.createBuffer(initSize == null ? initialCapacity : initSize, bigEndian);
		buf = value.toBinary(buf, ctx);
		return buf;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<BUF> unpack(BUF buf, Map<String, Object> ctx) {
		Message<BUF> value = this.newMessage();
		value.fromBinary(buf, ctx);
		return value;
	}
}
