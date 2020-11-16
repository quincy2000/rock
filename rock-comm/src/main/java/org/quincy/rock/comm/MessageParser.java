package org.quincy.rock.comm;

import java.util.Collection;
import java.util.Map;

/**
 * <b>报文正文解析器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 完成报文正文的解包和组包工作。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2011-6-22 下午11:14:44</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageParser<K, M, V> {
	/**
	 * 报文类型。
	 */
	public enum MsgType {
	/**
	 * 到达的报文。
	 */
	RECEIVE,
	/**
	 * 发出的报文 
	 */
	SEND,
	/**
	 * 到达和发出的报文
	 */
	ALL
	}

	/**
	 * <b>获得能处理的功能码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 能处理的功能码
	 */
	public Collection<K> getFunctionCode();

	/**
	 * <b>获得能处理的内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 能处理的内容类型
	 */
	public Collection<String> getContentType();

	/**
	 * <b>获得报文类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文类型
	 */
	public MsgType getMessageType();

	/**
	 * <b>组包。</b> 
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 报文对象
	 * @param ctx 上下文对象
	 * @return 组包后的报文消息正文
	 */
	public M pack(V value, Map<String, Object> ctx);

	/**
	 * <b>解包。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 报文消息正文
	 * @param ctx 上下文对象
	 * @return 解包后的报文对象
	 */
	public V unpack(M message, Map<String, Object> ctx);
}
