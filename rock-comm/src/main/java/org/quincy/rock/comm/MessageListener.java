package org.quincy.rock.comm;

import java.util.EventListener;
import java.util.Map;

/**
 * <b>报文消息监听器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2011-6-21 下午11:49:37</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageListener<K> extends EventListener {
	/**
	 * <b>终端上线时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sender 发送者
	 * @param terminalId 终端标识
	 */
	public void terminalOnline(MessageSender<K> sender, Object terminalId);

	/**
	 * <b>终端离线时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sender 发送者
	 * @param terminalId 终端标识
	 */
	public void terminalOffline(MessageSender<K> sender, Object terminalId);

	/**
	 * <b>报文消息到达了。</b>  
	 * <p><b>详细说明：</b></p>
	 * 无。
	 * @param sender 发送者
	 * @param terminalId 终端标识
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	public void messageArrived(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx);

	/**
	 * <b>报文消息出发了。</b>  
	 * <p><b>详细说明：</b></p>
	 * 无。
	 * @param sender 发送者
	 * @param terminalId 终端标识
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据 
	 * @param ctx 报文上下文数据
	 * @param success 是否发送成功
	 */
	public void messageSended(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx, boolean success);

	/**
	 * <b>报文头解析异常。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sender 发送者
	 * @param data 报文数据
	 * @param ctx 报文上下文数据
	 * @param e 异常
	 */
	public void messageHeadParserException(MessageSender<K> sender, Object data, Map<String, Object> ctx, Throwable e);

	/**
	 * <b>报文正文解析异常。</b>  
	 * <p><b>详细说明：</b></p>
	 * 无。
	 * @param sender 发送者
	 * @param terminalId 终端标识
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 * @param e 异常
	 */
	public void messageParserException(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode,
			Object content, Map<String, Object> ctx, Throwable e);

	/**
	 * <b>合法性检查异常。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sender 发送者
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文原始数据
	 * @param ctx 报文上下文数据
	 */
	public void checkLegalityException(MessageSender<K> sender, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx);
}