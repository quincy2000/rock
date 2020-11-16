package org.quincy.rock.comm.communicate;

import org.quincy.rock.core.function.Consumer;

/**
 * <b>通讯器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 下午11:09:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface Communicator<UChannel> {
	/**
	 * <b>添加通讯监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 索引位置
	 * @param listener 监听器
	 */
	public void addCommunicateListener(int index,CommunicateListener<UChannel> listener);
	
	/**
	 * <b>添加通讯监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 监听器
	 */
	public void addCommunicateListener(CommunicateListener<UChannel> listener);

	/**
	 * <b>移除通讯监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param listener 监听器
	 */
	public void removeCommunicateListener(CommunicateListener<UChannel> listener);

	/**
	 * <b>移除所有的通讯监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void removeAllCommunicateListener();

	/**
	 * <b>发送数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param data 数据
	 * @param async 是否是异步发送
	 * @param consumer 回调Consumer,可以为null
	 */
	public void sendData(UChannel channel, Object data, boolean async, Consumer<Boolean> consumer);

	/**
	 * <b>关闭通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 */
	public void close(UChannel channel);

	/**
	 * <b>通道是否是活动的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @return 通道是否是活动的
	 */
	public boolean isActive(UChannel channel);

	/**
	 * <b>销毁通讯器并释放资源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void destroy();

	/**
	 * <b>获得最大活动连接数阈值。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大活动连接数阈值
	 */
	public int getMaxActive();

	/**
	 * <b>返回活动连接数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 活动连接数
	 */
	public int getActiveCount();
}
