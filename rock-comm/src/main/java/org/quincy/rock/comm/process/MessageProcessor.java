package org.quincy.rock.comm.process;

import org.quincy.rock.comm.MessageSender;

/**
 * <b>报文内容处理器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 报文全部解析完成后交由处理器处理。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年7月17日 下午3:40:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageProcessor<K, M> {
	/**
	 * <b>获得功能码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 功能码
	 */
	public K getFunctionCode();

	/**
	 * <b>处理报文。</b>
	 * <p><b>详细说明：</b></p>
	 * 无。
	 * @param sender 消息发送者
	 * @param terminalId 终端标识
	 * @param msgId 消息id
	 * @param content 消息正文数据
	 */
	public void process(MessageSender<K> sender, Object terminalId, Object msgId, M content);
}
