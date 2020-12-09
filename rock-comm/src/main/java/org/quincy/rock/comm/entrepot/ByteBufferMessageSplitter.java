package org.quincy.rock.comm.entrepot;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

/**
 * <b>ByteBufferMessageSplitter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月10日 下午3:38:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ByteBufferMessageSplitter extends LengthMessageSplitter<ByteBuffer> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public ByteBufferMessageSplitter(String contentType) {
		super(contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public ByteBufferMessageSplitter(Collection<String> contentType) {
		super(contentType);
	}

	/** 
	 * split。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#split(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected ByteBuffer[] split(Object cmdCode, ByteBuffer buf) {
		int size = getSize();
		int len = buf.remaining();
		int count = len / size;
		int mod = len % size;
		len = count + (mod != 0 ? 1 : 0);
		ByteBuffer[] rtn = new ByteBuffer[len];
		for (int i = 0; i < count; i++) {
			byte[] arr = new byte[size];
			buf.get(arr);
			rtn[i] = ByteBuffer.wrap(arr);
		}
		if (mod != 0) {
			byte[] arr = new byte[mod];
			buf.get(arr);
			rtn[count] = ByteBuffer.wrap(arr);
		}
		return rtn;
	}

	/** 
	 * join。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#join(java.lang.Object, java.util.List)
	 */
	@Override
	protected ByteBuffer join(Object cmdCode, List<ByteBuffer> messages) {
		int cap = 0;
		for (ByteBuffer bb : messages) {
			cap += bb.remaining();
		}
		ByteBuffer buf = ByteBuffer.allocate(cap);
		for (ByteBuffer bb : messages) {
			buf.put(bb);
		}
		buf.flip();
		return buf;
	}

	/** 
	 * length。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#length(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected int length(Object cmdCode, ByteBuffer message) {
		return message.remaining();
	}
}
