package org.quincy.rock.comm.entrepot;

import java.util.Collection;

/**
 * <b>报文拆分器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 下午12:16:09</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0	
 * @author wks
 * @since 1.0
 */
public interface MessageSplitter {
	/**
	 * <b>获得能处理的内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 能处理的内容类型
	 */
	public Collection<String> getContentType();

	/**
	 * <b>判断是否需要拆分。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 * @param message 消息报文
	 * @return 消息报文是否需要拆分
	 */
	public boolean canSplit(Object cmdCode, Object message);

	/**
	 * <b>拆分报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 * @param message 长报文
	 * @return 短报文数组
	 */
	public Object[] splitMessage(Object cmdCode, Object message);

	/**
	 * <b>合并报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 * @param messages 短报文数组
	 * @return 长报文
	 */
	public Object joinMessage(Object cmdCode, Object[] messages);
}
