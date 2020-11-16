package org.quincy.rock.comm.entrepot;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
@SuppressWarnings("unchecked")
public abstract class LengthMessageSplitter<T> extends AbstractMessageSplitter {
	/**
	 * 拆分的最大长度。
	 */
	private int size = 1024;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public LengthMessageSplitter(String contentType) {
		super(Arrays.asList(contentType.split(",")));
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public LengthMessageSplitter(Collection<String> contentType) {
		super(contentType);
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/** 
	 * canSplit。
	 * @see org.quincy.rock.comm.entrepot.MessageSplitter#canSplit(java.lang.Object, java.lang.Object)
	 */
	@Override
	public final boolean canSplit(Object cmdCode, Object message) {
		return length(cmdCode, (T) message) > getSize();
	}

	/** 
	 * splitMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageSplitter#splitMessage(java.lang.Object, java.lang.Object)
	 */
	@Override
	public final Object[] splitMessage(Object cmdCode, Object message) {
		return split(cmdCode, (T) message);
	}

	/** 
	 * joinMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageSplitter#joinMessage(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public final Object joinMessage(Object cmdCode, Object[] messages) {
		return join(cmdCode, (List<T>) Arrays.asList(messages));
	}

	/**
	 * <b>分拆报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 * @param message 报文
	 * @return 报文数组
	 */
	protected abstract T[] split(Object cmdCode, T message);

	/**
	 * <b>合并报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 * @param messages 报文数组
	 * @return 报文
	 */
	protected abstract T join(Object cmdCode, List<T> messages);

	/**
	 * <b>返回报文长度。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 * @param message 报文
	 * @return 报文长度
	 */
	protected abstract int length(Object cmdCode, T message);
}
