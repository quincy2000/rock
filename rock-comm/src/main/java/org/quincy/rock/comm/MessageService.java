package org.quincy.rock.comm;

import java.util.Map;

import org.quincy.rock.core.function.Consumer;

/**
 * <b>报文服务接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月9日 下午12:21:48</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageService<K, UChannel> extends MessageSender<K> {
	/**
	 * <b>添加报文消息监听器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 报文消息监听器
	 */
	public void addMessageListener(MessageListener<K> listener);

	/**
	 * <b>移走报文消息监听器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 报文消息监听器
	 */
	public void removeMessageListener(MessageListener<K> listener);

	/**
	 * <b>移走所有的报文消息监听器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void removeAllMessageListener();

	/**
	 * <b>是否有指定的事件监听了。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener  报文消息监听器
	 * @return 是否有指定的事件监听了
	 */
	public boolean hasMessageListener(MessageListener<K> listener);

	/**
	 * <b>发送消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @param async 是否是异步发送，指示是立即返回还是发送完成后再返回
	 * @param consumer 回调Consumer,指示发送是否成功,可以为null
	 */
	public void sendMessageByChannel(UChannel channel, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, boolean async, Consumer<Boolean> consumer);
}
