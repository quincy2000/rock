package org.quincy.rock.comm.parser;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * <b>NioCasingResultMessage。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月3日 下午2:58:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NioCasingResultMessage extends CasingResultMessage<ByteBuffer, Byte> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * toBinary。
	 * @see org.quincy.rock.comm.parser.Message#toBinary(java.lang.Object, java.util.Map)
	 */
	@Override
	public ByteBuffer toBinary(ByteBuffer buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		buf.put(this.getResult());
		return buf;
	}

	/** 
	 * fromBinary。
	 * @see org.quincy.rock.comm.parser.Message#fromBinary(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<ByteBuffer> fromBinary(ByteBuffer buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		this.setResult(buf.get());
		return this;
	}
}
