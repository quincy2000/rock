package org.quincy.rock.comm.netty;

import java.util.Collection;
import java.util.List;

import org.quincy.rock.comm.entrepot.LengthMessageSplitter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>ByteBufMessageSplitter。</b>
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
public class ByteBufMessageSplitter extends LengthMessageSplitter<ByteBuf> {

	/**
	 * 使用数据复制策略（用于释放直接内存）。
	 */
	private boolean copy;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public ByteBufMessageSplitter(String contentType) {
		super(contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public ByteBufMessageSplitter(Collection<String> contentType) {
		super(contentType);
	}

	/**
	 * <b>使用数据复制策略（用于释放直接内存）。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 使用数据复制策略（用于释放直接内存）
	 */
	public boolean isCopy() {
		return copy;
	}

	/**
	 * <b>使用数据复制策略（用于释放直接内存）。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param copy 使用数据复制策略（用于释放直接内存）
	 */
	public void setCopy(boolean copy) {
		this.copy = copy;
	}

	/** 
	 * split。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#split(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected ByteBuf[] split(Object cmdCode, ByteBuf buf) {
		int size = getSize();
		int len = buf.readableBytes();
		int count = len / size;
		int mod = len % size;
		len = count + (mod != 0 ? 1 : 0);
		ByteBuf[] rtn = new ByteBuf[len];
		for (int i = 0; i < count; i++) {
			rtn[i] = buf.retainedSlice(i * size, size);
		}
		if (mod != 0)
			rtn[count] = buf.retainedSlice(count * size, mod);
		return rtn;
	}

	/** 
	 * join。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#join(java.lang.Object, java.util.List)
	 */
	@Override
	protected ByteBuf join(Object cmdCode, List<ByteBuf> messages) {
		CompositeByteBuf buf = Unpooled.compositeBuffer(messages.size());
		for (ByteBuf message : messages) {
			buf.addComponents(true, message);
		}
		if (isCopy()) {
			try {
				return buf.copy();
			} finally {
				buf.release();
			}
		} else {
			return buf;
		}
	}

	/** 
	 * length。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#length(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected int length(Object cmdCode, ByteBuf message) {
		return message.readableBytes();
	}

}
