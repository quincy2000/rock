package org.quincy.rock.comm.cmd;

import org.quincy.rock.comm.communicate.TerminalId;

/**
 * <b>封装终端指令。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月23日 下午2:38:21</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TerminalCommand<TERM extends TerminalId<TYPE, CODE>, TYPE, CODE> extends Command<TERM> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * clone。
	 * @see org.quincy.rock.comm.cmd.Command#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		TerminalCommand<TERM, TYPE, CODE> command = (TerminalCommand) super.clone();
		if (command.terminal != null)
			command.terminal = command.terminal.cloneMe();
		return command;
	}
}
