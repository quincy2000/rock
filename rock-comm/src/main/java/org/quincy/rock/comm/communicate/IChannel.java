package org.quincy.rock.comm.communicate;

import org.quincy.rock.core.cache.HasAccessTime;
import org.quincy.rock.core.cache.HasTimestamp;
import org.quincy.rock.core.util.HasPattern;
import org.quincy.rock.core.vo.CloneMe;

/**
 * <b>自定义通道接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月1日 下午4:43:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface IChannel extends Adviser, HasTimestamp, HasAccessTime, HasPattern, CloneMe {

	/**
	 * <b>是否是有效的可用通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public boolean isValidChannel();

	/**
	 * <b>是否是服务器端通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是服务器端通道
	 */
	public boolean isServerSide();

	/**
	 * <b>设置是否是服务器端通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverSide 是否是服务器端通道
	 */
	public void setServerSide(boolean serverSide);

	/**
	 * <b>是请求还是响应。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指示当前通道上正在传送的报文是请求报文还是响应报文。
	 * @return 是请求还是响应
	 */
	public boolean isRequest();

	/**
	 * <b>设置是请求还是响应。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指示当前通道上正在传送的报文是请求报文还是响应报文。
	 * @param request 是请求还是响应
	 */
	public void setRequest(boolean request);

	/**
	 * <b>内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 内容类型
	 */
	public String contentType();

	/**
	 * <b>报文协议版本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文协议版本
	 */
	public String protocolVer();

	/**
	 * <b>通道唯一id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道唯一id
	 */
	public Object channelId();

	/**
	 * <b>是否是发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是发送通道
	 */
	public boolean isSendChannel();

	/**
	 * <b>创建新的发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 新的发送通道
	 */
	public <T extends IChannel> T newSendChannel();

	/**
	 * <b>创建新的发送通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 创建新通道时可以参考顾问建议。
	 * @param adviser 顾问建议,如果没有建议可以传null
	 * @return 新的发送通道
	 */
	public <T extends IChannel> T newSendChannel(Adviser adviser);

	/**
	 * <b>返回通道地址信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道地址信息
	 */
	public String addressInfo();
}
