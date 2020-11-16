package org.quincy.rock.comm.communicate;

import java.util.EventListener;

/**
 * <b>通讯监听器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
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
public interface CommunicateListener<UChannel> extends EventListener {
	/**
	 * <b>建立连接时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 连接通道
	 */
	public void connection(UChannel channel);

	/**
	 * <b>断开连接时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 连接通道
	 */
	public void disconnection(UChannel channel);

	/**
	 * <b>接受到报文数据时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 连接通道
	 * @param data 接收到的数据
	 */
	public void receiveData(UChannel channel, Object data);

	/** 
	 * <b>发送报文数据完成时触发。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 连接通道
	 * @param data 已经发送出去的数据
	 * @param success 发送是否成功
	 */
	public void sendData(UChannel channel, Object data, boolean success);

	/**
	 * <b>捕获异常错误。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 连接通道
	 * @param e 异常信息
	 */
	public void exceptionCaught(UChannel channel, Throwable e);
}