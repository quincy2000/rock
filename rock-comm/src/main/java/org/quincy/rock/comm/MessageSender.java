package org.quincy.rock.comm;

import java.util.Map;

import org.quincy.rock.comm.cmd.CommandStation;
import org.quincy.rock.core.function.Consumer;

/**
 * <b>消息发送者接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月7日 下午2:55:40</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageSender<K> {
	/**
	 * <b>发送消息报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 终端唯一标识参数也支持终端模式。
	 * @param terminalId 终端唯一标识
	 * @param msgId 消息唯一id
	 * @param functionCode 功能码
	 * @param content 报文内容 
	 * @param attachment 附加信息,可以为null
	 * @param async 是否是异步发送，指示是立即返回还是发送完成后再返回
	 * @param consumer 回调Consumer,指示发送是否成功,可以为null
	 */
	public void sendMessage(Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, boolean async, Consumer<Boolean> consumer);

	/**
	 * <b>终端是否在线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一标识
	 * @return 终端是否在线
	 */
	public boolean isOnline(Object terminalId);

	/**
	 * <b>切断终端连接，使其离线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一标识
	 */
	public void offlineTerminal(Object terminalId);

	/**
	 * <b>返回指令站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指令站台
	 */
	public <T> CommandStation<T> commandStation();
}
