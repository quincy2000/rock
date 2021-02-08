package org.quincy.rock.comm.communicate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.cache.AccessTime;
import org.quincy.rock.core.cache.LiveTime;
import org.quincy.rock.core.exception.NotFoundException;

/**
 * <b>终端通道映射容器抽象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月11日 上午1:08:57</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractTerminalChannelMapping<UChannel extends IChannel>
		implements TerminalChannelMapping<UChannel>, LiveTime<Object>, AccessTime<Object> {
	/**
	 * 通道id-终端。
	 */
	private final Map<Object, TermPair> channelTerminalMapping = new ConcurrentHashMap<>();
	/**
	 * 终端id-通道。
	 */
	private final Map<Object, UChannel> terminalChannelMapping = new ConcurrentHashMap<>();
	/**
	 * 最大终端数。
	 */
	private int maxCount = Integer.MAX_VALUE;

	/**
	 * 报文过期秒数。
	 */
	private long expire = 10 * 1000;

	/**
	 * <b>获得报文过期秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文过期秒数
	 */
	public int getExpire() {
		return (int) (expire / 1000);
	}

	/**
	 * <b>设置报文过期秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param expire 报文过期秒数
	 */
	public void setExpire(int expire) {
		this.expire = expire * 1000L;
	}

	/**
	 * <b>获得最大终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大终端数
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * <b>设置最大终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxCount 最大终端数
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	/** 
	 * findMapping。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#findMapping(java.lang.Object)
	 */
	@Override
	public Object findMapping(UChannel channel) {
		TermPair pair = channelTerminalMapping.get(channel.channelId());
		if (pair == null) {
			return null;
		} else {
			pair.channel.updateAccessTime();
			return pair.terminalId;
		}
	}

	/** 
	 * createMapping。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#createMapping(java.lang.Object, java.util.Map, java.lang.Object)
	 */
	@Override
	public final Object createMapping(UChannel channel, Map<String, Object> ctx, Object content) {
		//处理报文是否过期
		Object code = ctx.get(CommUtils.COMM_FUNCTION_CODE_KEY);
		Long timestamp = (Long) ctx.get(CommUtils.COMM_MSG_TIMESTAMP_KEY);
		if (timestamp != null) {
			boolean expired = System.currentTimeMillis() > (timestamp + expire);
			if (expired && processExpired(code, timestamp, channel, ctx, content)) {
				return null;
			}
		}
		//处理超载
		if (count() >= getMaxCount() && processOverload(code, channel, ctx, content))
			return null;
		//创建终端id
		Object terminalId = createTerminalId(code, channel, ctx, content);
		if (terminalId != null) {
			if (channel instanceof AbstractChannel) {
				AbstractChannel ac = (AbstractChannel) channel;
				String type = (String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY);
				if (type != null)
					ac.contentType(type);
				String ver = (String) ctx.get(CommUtils.COMM_MSG_PROTOCOL_VERSION_KEY);
				if (ver != null)
					ac.protocolVer(ver);
			}
			//原始通道可能是一个共享通道，所以要克隆一份
			channel = channel.cloneMe();
			channelTerminalMapping.put(channel.channelId(), pair(terminalId, channel));
			terminalChannelMapping.put(terminalId, channel);
		}
		return terminalId;
	}

	/**
	 * <b>创建终端id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 可在该方法中校验通道合法性，如果不合法可以返回null,也可以将不合法的原因生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * 返回响应涉及到的上下文key:
	 * CommUtils.COMM_MSG_DIRECT_RESPONE_KEY(必须),
	 * CommUtils.COMM_FUNCTION_CODE_KEY(必须),
	 * CommUtils.COMM_MSG_TYPE_KEY(可选)
	 * @param funcCode 功能码
	 * @param channel 通道
	 * @param ctx 报文上下文对象
	 * @param content 报文正文
	 * @return 终端id,必须是一个新的终端id(非共享实例)，返回null代表创建失败
	 * @see CommUtils
	 */
	protected abstract Object createTerminalId(Object funcCode, UChannel channel, Map<String, Object> ctx,
			Object content);

	/**
	 * <b>处理过期的报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果需要返回过期响应报文，则可以在此生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * 返回响应涉及到的上下文key:
	 * CommUtils.COMM_MSG_DIRECT_RESPONE_KEY(必须),
	 * CommUtils.COMM_FUNCTION_CODE_KEY(必须),
	 * CommUtils.COMM_MSG_TYPE_KEY(可选)
	 * @param funcCode 功能码
	 * @param timestamp 时间戳
	 * @param channel 通道
	 * @param ctx 报文上下文对象
	 * @param content 报文正文
	 * @return 是否中断创建终端id
	 * @see CommUtils
	 */
	protected abstract boolean processExpired(Object funcCode, long timestamp, UChannel channel,
			Map<String, Object> ctx, Object content);

	/**
	 * <b>处理超载。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果需要返回超载响应报文，则可以在此生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * 返回响应涉及到的上下文key:
	 * CommUtils.COMM_MSG_DIRECT_RESPONE_KEY(必须),
	 * CommUtils.COMM_FUNCTION_CODE_KEY(必须),
	 * CommUtils.COMM_MSG_TYPE_KEY(可选)
	 * @param funcCode 功能码
	 * @param channel 通道
	 * @param ctx 报文上下文对象
	 * @param content 报文正文
	 * @return 是否中断创建终端id
	 * @see CommUtils
	 */
	protected boolean processOverload(Object funcCode, UChannel channel, Map<String, Object> ctx, Object content) {
		throw new CommunicateException("Reach the maximum number of terminal.");
	}

	/** 
	 * destroyMapping。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#destroyMapping(java.lang.Object)
	 */
	@Override
	public Object destroyMapping(UChannel channel) {
		TermPair pair = channelTerminalMapping.remove(channel.channelId());
		if (pair == null)
			return null;
		else {
			terminalChannelMapping.remove(pair.terminalId);
			return pair.terminalId;
		}
	}

	/**
	 * <b>返回连接终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 连接终端数量
	 */
	public final int count() {
		return terminalChannelMapping.size();
	}

	/** 
	 * findChannel。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#findChannel(java.lang.Object)
	 */
	@Override
	public UChannel findChannel(Object terminalId) {
		return terminalChannelMapping.get(terminalId);
	}

	/** 
	 * findTerminal。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#findTerminal(java.lang.Object)
	 */
	@Override
	public Object findTerminal(UChannel channel) {
		TermPair pair = channelTerminalMapping.get(channel.channelId());
		return pair == null ? null : pair.terminalId;
	}

	/**
	 * <b>返回所有终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端标识列表
	 */
	public final Iterable<Object> terminals() {
		return terminalChannelMapping.keySet();
	}

	/**
	 * <b>返回所有通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道列表
	 */
	public final Iterable<UChannel> channels() {
		return terminalChannelMapping.values();
	}

	/**
	 * <b>是否有指定的通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @return 是否有指定的通道
	 */
	public boolean hasChannel(UChannel channel) {
		return channelTerminalMapping.containsKey(channel.channelId());
	}

	/**
	 * <b>是否有指定的终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端标识
	 * @return 是否有指定的终端
	 */
	public boolean hasTerminal(Object terminalId) {
		return terminalChannelMapping.containsKey(terminalId);
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.cache.LiveTime#timestamp(java.lang.Object)
	 */
	@Override
	public long timestamp(Object terminalId) {
		UChannel ch = this.getChannel(terminalId);
		return ch.timestamp();
	}

	/** 
	 * lastAccessTime。
	 * @see org.quincy.rock.core.cache.AccessTime#lastAccessTime(java.lang.Object)
	 */
	@Override
	public long lastAccessTime(Object terminalId) {
		UChannel ch = this.getChannel(terminalId);
		return ch.lastAccessTime();
	}

	/** 
	 * getChannel。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#getChannel(java.lang.Object)
	 */
	@Override
	public UChannel getChannel(Object terminalId) throws NotFoundException {
		UChannel channel = findChannel(terminalId);
		if (channel == null)
			throw new NotFoundException(terminalId.toString());
		return channel;
	}

	/** 
	 * getTerminal。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#getTerminal(java.lang.Object)
	 */
	@Override
	public Object getTerminal(UChannel channel) throws NotFoundException {
		Object terminalId = findTerminal(channel);
		if (terminalId == null)
			throw new NotFoundException(channel.toString());
		return terminalId;
	}

	/** 
	 * isEmpty。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return count() == 0;
	}

	/**
	 * <b>返回终端缺省的报文正文内容格式类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一id
	 * @return 报文正文内容格式类型
	 */
	public String contentType(Object terminalId) {
		return this.getChannel(terminalId).contentType();
	}

	/**
	 * <b>返回终端的报文协议版本号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 终端唯一id
	 * @return 报文协议版本号
	 */
	public String protocolVer(Object terminalId) {
		return this.getChannel(terminalId).protocolVer();
	}

	/**
	 * <b>产生一个用于发送报文的上下文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 上下文中初始化了默认的报文正文格式类型。
	 * @param terminalId 终端唯一id
	 * @return 用于发送报文的上下文
	 */
	public Map<String, Object> sendedContext(Object terminalId) {
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put(CommUtils.COMM_MSG_TYPE_KEY, contentType(terminalId));
		ctx.put(CommUtils.COMM_MSG_RECEIVE_FALG, Boolean.FALSE);
		ctx.put(CommUtils.COMM_MSG_PROTOCOL_VERSION_KEY, protocolVer(terminalId));
		return ctx;
	}

	//
	private TermPair pair(Object terminalId, UChannel channel) {
		return new TermPair(terminalId, channel);
	}

	//
	private class TermPair {
		Object terminalId;
		UChannel channel;

		TermPair(Object terminalId, UChannel channel) {
			this.terminalId = terminalId;
			this.channel = channel;
		}
	}
}
