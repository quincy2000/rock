package org.quincy.rock.comm.entrepot;

import java.util.Collection;
import java.util.List;

import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>二进制报文拆分器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 下午12:53:40</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class BinaryMessageSplitter extends LengthMessageSplitter<byte[]> {
	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public BinaryMessageSplitter(String contentType) {
		super(contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public BinaryMessageSplitter(Collection<String> contentType) {
		super(contentType);
	}

	/** 
	 * split。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#split(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected byte[][] split(Object cmdCode, byte[] message) {
		return CommUtils.splitMessage(message, getSize());
	}

	/** 
	 * join。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#join(java.lang.Object, java.util.List)
	 */
	@Override
	protected byte[] join(Object cmdCode, List<byte[]> messages) {
		return CommUtils.joinMessage(messages);
	}

	/** 
	 * length。
	 * @see org.quincy.rock.comm.entrepot.LengthMessageSplitter#length(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected int length(Object cmdCode, byte[] message) {
		return message.length;
	}
}
