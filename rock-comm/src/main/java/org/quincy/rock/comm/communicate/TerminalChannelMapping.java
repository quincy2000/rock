package org.quincy.rock.comm.communicate;

import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.cache.AccessTime;
import org.quincy.rock.core.cache.LiveTime;
import org.quincy.rock.core.exception.NotFoundException;

/**
 * <b>通道终端映射器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月7日 下午1:03:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface TerminalChannelMapping<UChannel> extends LiveTime<Object>, AccessTime<Object> {
	/**
	 * <b>获得报文过期秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文过期秒数
	 */
	public int getExpire();

	/**
	 * <b>设置报文过期秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param expire 报文过期秒数
	 */
	public void setExpire(int expire);

	/**
	 * <b>获得最大终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大终端数
	 */
	public int getMaxCount();

	/**
	 * <b>设置最大终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxCount 最大终端数
	 */
	public void setMaxCount(int maxCount);

	/**
	 * <b>指令合法性检查。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 在该方法中校验通道指令合法性，如果不合法可以返回null,也可以将不合法的原因生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * 返回响应涉及到的上下文key:
	 * CommUtils.COMM_MSG_DIRECT_RESPONE_KEY(必须),
	 * CommUtils.COMM_FUNCTION_CODE_KEY(必须),
	 * CommUtils.COMM_MSG_TYPE_KEY(可选)
	 * @param msgId 报文id
	 * @param funcCode 功能码
	 * @param channel 通道
	 * @param ctx 报文上下文数据
	 * @param data 报文正文原始数据
	 * @return 如果指令合法返回终端唯一标识(非共享实例)，否则返回null
	 * @see CommUtils
	 */
	public Object checkLegality(Object msgId, Object funcCode, UChannel channel, Map<String, Object> ctx,
			Object content);

	/**
	 * <b>发现通道终端映射，如果没有发现则返回null。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法会更新最后访问时间。
	 * @param channel 通道对象
	 * @return 终端唯一标识，如果没有发现可以返回null
	 */
	public Object findMapping(UChannel channel);

	/**
	 * <b>创建通道终端映射。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 可在该方法中校验通道合法性，如果不合法可以返回null,也可以将不合法的原因生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * 返回响应涉及到的上下文key:
	 * CommUtils.COMM_MSG_DIRECT_RESPONE_KEY(必须),
	 * CommUtils.COMM_FUNCTION_CODE_KEY(必须),
	 * CommUtils.COMM_MSG_TYPE_KEY(可选)
	 * @param channel 通道对象
	 * @param ctx 报文上下文对象
	 * @param content 报文正文内容
	 * @return 终端唯一标识((非共享实例)),返回null代表创建失败
	 * @see CommUtils
	 */
	public Object createMapping(UChannel channel, Map<String, Object> ctx, Object content);

	/**
	 * <b>销毁通道终端映射。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道对象
	 * @return 终端唯一标识,如果销毁的通道不存在则返回null
	 */
	public Object destroyMapping(UChannel channel);

	/**
	 * <b>返回连接终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 连接终端数量
	 */
	public int count();

	/**
	 * <b>返回所有终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端标识列表
	 */
	public Iterable<Object> terminals();

	/**
	 * <b>返回所有通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道列表
	 */
	public Iterable<UChannel> channels();

	/**
	 * <b>获得通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一标识
	 * @return 通道对象
	 */
	public UChannel findChannel(Object terminalId);
	
	/**
	 * <b>获得终端标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @return 终端标识
	 */
	public Object findTerminal(UChannel channel);

	/**
	 * <b>获得通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一标识
	 * @return 通道对象
	 * @exception NotFoundException
	 */
	public UChannel getChannel(Object terminalId) throws NotFoundException;

	/**
	 * <b>获得终端标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @return 终端标识
	 * @exception NotFoundException
	 */
	public Object getTerminal(UChannel channel) throws NotFoundException;

	/**
	 * <b>是否有指定的通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @return 是否有指定的通道
	 */
	public boolean hasChannel(UChannel channel);

	/**
	 * <b>是否有指定的终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @return 是否有指定的终端
	 */
	public boolean hasTerminal(Object terminalId);

	/**
	 * <b>是否为空。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否为空
	 */
	public boolean isEmpty();

	/**
	 * <b>返回终端缺省的报文正文内容格式类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一id
	 * @return 报文正文内容格式类型
	 */
	public String contentType(Object terminalId);

	/**
	 * <b>产生一个用于发送报文的上下文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 上下文中初始化了默认的报文正文格式类型。
	 * @param terminalId 终端唯一id
	 * @return 用于发送报文的上下文
	 */
	public Map<String, Object> sendedContext(Object terminalId);
}
