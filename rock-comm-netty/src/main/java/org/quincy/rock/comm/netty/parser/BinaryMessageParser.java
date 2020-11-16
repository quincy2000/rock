package org.quincy.rock.comm.netty.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>二进制报文解析器基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
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
public abstract class BinaryMessageParser<V extends Message> extends MessageParserBase<V> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 默认类型为CommUtils.MESSAGE_TYPE_BINARY。
	 */
	public BinaryMessageParser() {
		super(Arrays.asList(CommUtils.MESSAGE_TYPE_BINARY));
	}

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
	public BinaryMessageParser(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}
	
	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public ByteBuf pack(V value, Map<String, Object> ctx) {
		ByteBuf buf = value.toBinary(Unpooled.buffer(),ctx);
		return buf;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public V unpack(ByteBuf message, Map<String, Object> ctx) {
		V vo = this.newMessage();
		if (vo != null) {
			message = message.retainedSlice();
			try {
				vo.fromBinary(message,ctx);
			} finally {
				message.release();
			}
		}
		return vo;
	}

	/**
	 * <b>创建新的报文实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 新的报文实例
	 */
	protected <T extends Message> T newMessage() {
		try {
			Class<?> clazz = this.getMessageClass();
			T vo = (T) clazz.newInstance();
			return vo;
		} catch (Exception e) {
			recorder.write(e, e.getMessage());
			return null;
		}
	}
}
