package org.quincy.rock.comm;

import java.util.Map;

import org.quincy.rock.core.function.Consumer;

/**
 * <b>RpcMessageSender。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月28日 上午11:43:00</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface RpcMessageSender<K, UChannel> extends MessageSender<K> {
	/**
	 * <b>发送Rpc消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一id
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @param succeed 成功回调Consumer
	 * @param failed 失败回调Consumer
	 * @exception CommunicateException
	 */
	public void sendRpcMessage(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, Consumer<?> succeed, Consumer<Exception> failed)
			throws CommunicateException;

	/**
	 * <b>发送Rpc消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一id
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @return 返回值
	 * @exception CommunicateException
	 */
	public <T> T sendRpcMessage(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment) throws CommunicateException;

	/**
	 * <b>发送Rpc消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道，必须是有效的唯一终端通道
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @param succeed 成功回调Consumer
	 * @param failed 失败回调Consumer
	 * @exception CommunicateException
	 */
	public void sendRpcMessageByChannel(UChannel channel, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, Consumer<?> succeed, Consumer<Exception> failed)
			throws CommunicateException;

	/**
	 * <b>发送Rpc消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道，必须是有效的唯一终端通道
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @return 返回值
	 * @exception CommunicateException
	 */
	public <T> T sendRpcMessageByChannel(UChannel channel, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment) throws CommunicateException;
}
