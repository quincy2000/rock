package org.quincy.rock.comm.netty.parser;

import java.util.Map;

import org.quincy.rock.comm.parser.Message;
import org.quincy.rock.comm.parser.ResultMessage;

import io.netty.buffer.ByteBuf;

/**
 * <b>NettyResultMessage。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年3月1日 下午5:50:43</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NettyResultMessage extends ResultMessage<ByteBuf, Integer> {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4127249324260076697L;

	/**
	 * 结果字段使用无符号字节。
	 */
	private boolean unsignedByte;
	
	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param unsignedByte 结果字段使用无符号字节
	 */
	public NettyResultMessage(boolean unsignedByte) {
		this.unsignedByte=unsignedByte;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 返回的结果代码
	 * @param unsignedByte 结果字段使用无符号字节
	 */
	public NettyResultMessage(Integer result,boolean unsignedByte) {
		super(result);
		this.unsignedByte=unsignedByte;
	}

	/** 
	 * toBinary。
	 * @see org.quincy.rock.comm.parser.Message#toBinary(java.lang.Object, java.util.Map)
	 */
	@Override
	public ByteBuf toBinary(ByteBuf buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		buf.writeByte(this.getResult());
		return buf;
	}

	/** 
	 * fromBinary。
	 * @see org.quincy.rock.comm.parser.Message#fromBinary(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<ByteBuf> fromBinary(ByteBuf buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		this.setResult((int) (unsignedByte ? buf.readUnsignedByte() : buf.readByte()));
		return this;
	}

}
