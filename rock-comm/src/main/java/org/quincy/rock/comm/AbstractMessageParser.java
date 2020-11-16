package org.quincy.rock.comm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <b>报文正文解析器抽象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 上午10:06:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractMessageParser<K, M, V> implements MessageParser<K, M, V> {
	/**
	 * 功能码。
	 */
	private Collection<K> functionCode;
	/**
	 * 报文类型。
	 */
	private MsgType messageType;
	/**
	 * 内容类型。
	 */
	private Collection<String> contentType;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public AbstractMessageParser() {

	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public AbstractMessageParser(Collection<String> contentType) {
		this.contentType = contentType;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public AbstractMessageParser(Collection<K> functionCode, Collection<String> contentType) {
		this.functionCode = functionCode;
		this.contentType = contentType;
	}

	/** 
	 * getFunctionCode。
	 * @see org.quincy.rock.comm.MessageParser#getFunctionCode()
	 */
	@Override
	public final Collection<K> getFunctionCode() {
		if (functionCode == null) {
			functionCode = new ArrayList<>();
		}
		return functionCode;
	}

	/**
	 * <b>设置功能码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public final void setFunctionCode(Collection<K> functionCode) {
		this.functionCode = functionCode;
	}

	/** 
	 * getMessageType。
	 * @see org.quincy.rock.comm.MessageParser#getMessageType()
	 */
	@Override
	public MsgType getMessageType() {
		return messageType;
	}

	/**
	 * <b>设置报文类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageType 报文类型
	 */
	public void setMessageType(MsgType messageType) {
		this.messageType = messageType;
	}

	/** 
	 * getContentType。
	 * @see org.quincy.rock.comm.MessageParser#getContentType()
	 */
	@Override
	public final Collection<String> getContentType() {
		if (contentType == null) {
			contentType = new ArrayList<>();
		}
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

	/**
	 * <b>添加内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public final void addContentType(String contentType) {
		this.getContentType().add(contentType);
	}

	/**
	 * <b>添加功能码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public final void addFunctionCode(K functionCode) {
		this.getFunctionCode().add(functionCode);
	}
}
