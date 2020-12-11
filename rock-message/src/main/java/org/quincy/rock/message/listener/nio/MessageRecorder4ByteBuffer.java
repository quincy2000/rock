package org.quincy.rock.message.listener.nio;

import org.quincy.rock.comm.util.NioUtils;
import org.quincy.rock.message.listener.MessageRecorder;

/**
 * <b>MessageRecorder4ByteBuffer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年4月30日 下午12:56:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MessageRecorder4ByteBuffer extends MessageRecorder<Object> {

	/** 
	 * toHexString。
	 * @see com.tonsel.message.listener.MessageRecorder#toHexString(java.lang.Object)
	 */
	@Override
	protected String toHexString(Object data) {
		return NioUtils.toHexString(data);
	}

}
