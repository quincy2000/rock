package org.quincy.rock.comm.entrepot;

import java.util.EventListener;
import java.util.Map;

/**
 * <b>报文集散中心监听器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 监听报文的进出库。
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
public interface MessageEntrepotListener extends EventListener {
	/**
	 * <b>待发送报文添加到仓库时触发该事件。</b>
	 * <p><b>详细说明：</b></p>
	 * 对于context参数的说明：这是本地要发送出去的报文上下文数据，如果报文是分组发送的，则这是一个分组报文；
	 * 如果报文不是分组发送的，则该报文只有一个分组。
	 * @param me 报文仓库
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 报文id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	public void toSentMessageAdded(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx);

	/**
	 * <b>一组完整的待发送报文全部添加到仓库时触发该事件。</b>
	 * <p><b>详细说明：</b></p>
	 * 对于context参数的说明：这是本地要发送出去的报文上下文数据。
	 * @param me 报文仓库
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 报文id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 * @param success 是否发送成功
	 */
	public void toSentMessageAddDone(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx, boolean success);

	/**
	 * <b>远程到达报文添加到仓库时触发该事件。</b>
	 * <p><b>详细说明：</b></p>
	 * 对于context参数的说明：这是远程到达的报文上下文数据，如果报文是分组发送的，则这是一个分组报文；
	 * 如果报文不是分组发送的，则该报文只有一个分组。
	 * @param me 报文仓库
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 报文id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 远程到达的报文上下文数据
	 */
	public void arrivedMessageAdded(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx);

	/**
	 * <b>一组完整的远程到达报文全部添加到仓库时触发该事件。</b>  
	 * <p><b>详细说明：</b></p>
	 * 对于context参数的说明：这是远程到达的报文上下文数据。
	 * @param me 报文仓库
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 报文id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 远程到达的报文上下文数据
	 */
	public void arrivedMessageAddDone(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx);
}
