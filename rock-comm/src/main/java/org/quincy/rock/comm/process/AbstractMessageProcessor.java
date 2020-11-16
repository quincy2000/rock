package org.quincy.rock.comm.process;

import org.quincy.rock.core.util.HasOwner;

/**
 * <b>AbstractMessageProcessor。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月14日 下午9:36:06</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractMessageProcessor<K, M> implements MessageProcessor<K, M>, HasOwner<Object> {
	/**
	 * owner。
	 */
	private Object owner;

	/**
	 * 功能码。
	 */
	private K functionCode;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public AbstractMessageProcessor() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public AbstractMessageProcessor(K functionCode) {
		super();
		this.functionCode = functionCode;
	}

	/** 
	 * setOwner。
	 * @see org.quincy.rock.core.util.HasOwner#setOwner(java.lang.Object)
	 */
	@Override
	public void setOwner(Object owner) {
		this.owner = owner;
	}

	/** 
	 * getOwner。
	 * @see org.quincy.rock.core.util.HasOwner#getOwner()
	 */
	@Override
	public Object getOwner() {
		return this.owner;
	}

	/** 
	 * hasOwner。
	 * @see org.quincy.rock.core.util.HasOwner#hasOwner()
	 */
	@Override
	public boolean hasOwner() {
		return this.owner != null;
	}

	/** 
	 * getFunctionCode。
	 * @see org.quincy.rock.comm.process.MessageProcessor#getFunctionCode()
	 */
	public K getFunctionCode() {
		return functionCode;
	}

}
