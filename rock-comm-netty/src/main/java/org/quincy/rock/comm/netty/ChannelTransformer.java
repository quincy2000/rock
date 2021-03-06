package org.quincy.rock.comm.netty;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * <b>通道转换器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月30日 下午11:01:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public interface ChannelTransformer<UChannel> {
	/**
	 * 存放纯净通道的Key。
	 */
	public static final AttributeKey<INettyChannel> NETTY_CHANNEL_KEY = AttributeKey.valueOf("nettyChannel");

	/**
	 * 存放接收通道的Key。
	 */
	public static final AttributeKey<INettyChannel> NETTY_RECEIVE_CHANNEL_KEY = AttributeKey
			.valueOf("nettyReceiveChannel");

	/**
	 * 存放发送通道的Key。
	 */
	public static final AttributeKey<INettyChannel> NETTY_SEND_CHANNEL_KEY = AttributeKey.valueOf("nettySendChannel");

	/**
	 * NONE。
	 */
	public static final ChannelTransformer NONE = new NoneChannelTransformer();

	/**
	 * 原始通道转换点
	 */
	public enum STransformPoint {
		CHANNEL_ACTIVE, CHANNEL_INACTIVE, CHANNEL_READ, CHANNEL_WRITE, CHANNEL_ERROR, ONLY_RETURN
	}

	/**
	 * 定制通道转换点（伪通道）
	 */
	public enum UTransformPoint {
		SEND_DATA, CLOSE_CHANNEL, ONLY_RETURN
	}

	/**
	 * <b>原始通道转换到定制通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sch 原始通道
	 * @param point 转换点
	 * @return 定制通道
	 */
	public UChannel transform(Channel sch, STransformPoint point);

	/**
	 * <b>定制通道转换到原始通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param uch 定制通道
	 * @param point 转换点
	 * @return 原始通道
	 */
	public Channel transform(UChannel uch, UTransformPoint point);

	/**
	 * <b>取得通道发送消息锁。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param uch 用户定义通道
	 * @return 通道发送消息锁
	 */
	public Object retrieveSendLock(UChannel uch);
}
