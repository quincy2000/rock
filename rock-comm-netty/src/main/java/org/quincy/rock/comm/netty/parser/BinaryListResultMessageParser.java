package org.quincy.rock.comm.netty.parser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>二进制响应报文(嵌套数组报文)解析器基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 提供CasingListResultMessage报文解析支持。
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
public abstract class BinaryListResultMessageParser<V extends Message>
		extends BinaryMessageParser<CasingListResultMessage<V>> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 默认类型为CommUtils.MESSAGE_TYPE_BINARY。
	 */
	public BinaryListResultMessageParser() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public BinaryListResultMessageParser(Collection<String> contentType) {
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
	public BinaryListResultMessageParser(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/** 
	 * pack。
	 * @see com.tonsel.togt.comm.codec.BinaryMessageParser#pack(com.tonsel.togt.comm.vo.Message, java.util.Map)
	 */
	@Override
	public ByteBuf pack(CasingListResultMessage<V> value, Map<String, Object> ctx) {
		ByteBuf buf = Unpooled.buffer();
		value.toBinary(buf, ctx);
		List<V> list = value.getData();
		if (list != null && !list.isEmpty()) {
			for (V data : list) {
				data.toBinary(buf, ctx);
			}
		}
		//
		return buf;
	}

	/** 
	 * unpack。
	 * @see com.tonsel.togt.comm.codec.BinaryMessageParser#unpack(io.netty.buffer.ByteBuf, java.util.Map)
	 */
	@Override
	public CasingListResultMessage<V> unpack(ByteBuf message, Map<String, Object> ctx) {
		CasingListResultMessage<V> crm = new CasingListResultMessage<>();
		message = message.retainedSlice();
		try {
			crm.fromBinary(message, ctx);
			while (message.readableBytes() > 0) {
				V data = this.newMessage();
				data.fromBinary(message, ctx);
				crm.addData(data);
			}
		} finally {
			message.release();
		}
		return crm;
	}
}
