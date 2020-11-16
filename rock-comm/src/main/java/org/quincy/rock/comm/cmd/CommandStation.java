package org.quincy.rock.comm.cmd;

/**
 * <b>发送报文指令站台。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 指令站台存放将要发送的报文指令，一旦终端上线则就将站台中的该终端的报文指令发送出去。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月27日 下午5:02:11</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface CommandStation<TERM> {
	/**
	 * <b>将终端指令放置到站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端对象
	 * @param cmd 指令对象
	 */
	public void putCommand(TERM terminal, Command<? extends TERM> cmd);

	/**
	 * <b>移走终端指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端对象
	 * @param cmd 指令对象
	 */
	public void removeCommand(TERM terminal, Command<? extends TERM> cmd);
	
	/**
	 * <b>获得指定条数的终端指令对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端对象
	 * @param limit 返回的最多条数
	 * @return 指令对象列表
	 */
	public <T extends TERM> Iterable<Command<T>> getCommands(T terminal, int limit);

	/**
	 * <b>获得要发送的下一条指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端对象
	 * @return 要发送的下一条指令，如果没有则返回null
	 */
	public <T extends TERM> Command<T> getNextCommand(T terminal);

	/**
	 * <b>返回终端列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端列表
	 */
	public <T extends TERM> Iterable<T> terminals();

	/**
	 * <b>返回终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端数量
	 */
	public int count();

	/**
	 * <b>返回终端指令数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端对象
	 * @return 终端指令数量
	 */
	public int count(TERM terminal);
}
