package org.quincy.rock.comm.entrepot;

import java.util.Collection;

/**
 * <b>报文拆分器抽象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 下午12:48:48</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractMessageSplitter implements MessageSplitter {
	/**
	 * 内容类型。
	 */
	private Collection<String> contentType;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public AbstractMessageSplitter(Collection<String> contentType) {
		this.contentType = contentType;
	}

	/** 
	 * getContentType。
	 * @see org.quincy.rock.comm.entrepot.MessageSplitter#getContentType()
	 */
	@Override
	public final Collection<String> getContentType() {
		return contentType;
	}

	/**
	 * <b>设置内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public final void setContentType(Collection<String> contentType) {
		this.contentType = contentType;
	}
}
