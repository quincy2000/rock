package org.quincy.rock.comm.entrepot;

import java.util.Map;

/**
 * <b>报文集散中心接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 报文组装车间和中转仓库。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2011-10-19 下午08:57:21</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageEntrepot {
	/**
	 * <b>添加报文集散中心监听器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 报文集散中心监听器
	 */
	public void addMessageEntrepotListener(MessageEntrepotListener listener);

	/**
	 * <b>移除报文集散中心监听器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 报文集散中心监听器
	 */
	public void removeMessageEntrepotListener(MessageEntrepotListener listener);

	/**
	 * <b>移除所有的报文集散中心监听器。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void removeAllMessageEntrepotListener();

	/**
	 * <b>添加将要被发送的本地报文。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 本地报文将要被发送到远程终端或主机,如果报文过大，将被仓库拆分后发送。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 报文id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	public void addToSentMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx);

	/**
	 * <b>添加接收到的远程到达报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 添加接收到的远程到达报文,有可能是拆分过的报文(分组报文)。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 报文id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	public void addArrivedMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx);

	/**
	 * <b>移走终端数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 远程终端或主机唯一标识
	 */
	public void removeTerminal(Object terminalId);
}
