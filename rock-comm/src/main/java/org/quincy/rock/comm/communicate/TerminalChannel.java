package org.quincy.rock.comm.communicate;

/**
 * <b>关联业务终端的通道接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月1日 上午11:15:09</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface TerminalChannel<TYPE, CODE> extends IChannel {
	/**
	 * <b>远程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 远程id
	 */
	public TerminalId<TYPE, CODE> remote();

	/**
	 * <b>本地id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 本地id
	 */
	public TerminalId<TYPE, CODE> local();

	/**
	 * <b>设置本地终端类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 本地终端类型
	 */
	public void setLocalType(TYPE type);

	/**
	 * <b>获得本地终端类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 本地终端类型
	 */
	public TYPE getLocalType();

	/**
	 * <b>设置本地终端代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 本地终端代码
	 */
	public void setLocalCode(CODE code);

	/**
	 * <b>获得本地终端代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 本地终端代码
	 */
	public CODE getLocalCode();

	/**
	 * <b>设置远程终端类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 远程终端类型
	 */
	public void setRemoteType(TYPE type);

	/**
	 * <b>获得远程终端类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 远程终端类型
	 */
	public TYPE getRemoteType();

	/**
	 * <b>设置远程终端代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 远程终端代码
	 */
	public void setRemoteCode(CODE code);

	/**
	 * <b>获得远程终端代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 远程终端代码
	 */
	public CODE getRemoteCode();

	/**
	 * <b>报文目的地是服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文目的地是服务器
	 */
	public boolean toServer();

	/**
	 * <b>报文发出端是服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文发出端是服务器
	 */
	public boolean fromServer();
}
