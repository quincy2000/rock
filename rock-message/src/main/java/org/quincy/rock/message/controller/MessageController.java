package org.quincy.rock.message.controller;

import org.quincy.rock.comm.cmd.TerminalCommand;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.message.server.MessageServer;

/**
 * <b>MessageController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>mex20</td><td>2020年10月9日 下午5:27:01</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MessageController<CMD extends TerminalCommand<TERM, TYPE, CODE>, TERM extends TerminalId<TYPE, CODE>, TYPE, CODE> {
	/**
	 * 报文服务器。
	 */
	private MessageServer<CMD, TERM, TYPE, CODE> messageServer;

	/**
	 * <b>获得报文服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文服务器
	 */
	public MessageServer<CMD, TERM, TYPE, CODE> getMessageServer() {
		return messageServer;
	}

	/**
	 * <b>设置报文服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageServer 报文服务器
	 */
	public void setMessageServer(MessageServer<CMD, TERM, TYPE, CODE> messageServer) {
		this.messageServer = messageServer;
	}
}
