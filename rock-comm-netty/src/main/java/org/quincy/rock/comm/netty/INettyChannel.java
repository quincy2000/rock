package org.quincy.rock.comm.netty;

import org.quincy.rock.comm.communicate.IChannel;
import org.quincy.rock.core.lang.Getter;

import io.netty.channel.Channel;

/**
 * <b>INettyChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月2日 下午5:37:32</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface INettyChannel extends IChannel {
	/**
	 * <b>获得netty原始通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return netty原始通道
	 */
	public Channel channel();

	/**
	 * <b>设置netty原始通道Getter。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param getter netty原始通道Getter
	 * @return 通道
	 */
	public <T extends INettyChannel> T setChannelGetter(Getter<Channel> getter);
}
