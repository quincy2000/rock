package org.quincy.rock.comm.communicate;

import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>DefaultTerminalChannelMapping。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月14日 下午1:25:43</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public class DefaultTerminalChannelMapping<UChannel extends TerminalChannel<?, ?>>
		extends AbstractTerminalChannelMapping<UChannel> {
	/**
	 * 终端合法性检查器。
	 */
	private TerminalChecker terminalChecker = TerminalChecker.ALLOW_ALL;

	/**
	 * <b>获得终端合法性检查器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端合法性检查器
	 */
	public TerminalChecker getTerminalChecker() {
		return terminalChecker;
	}

	/**
	 * <b>设置终端合法性检查器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalChecker 终端合法性检查器
	 */

	public void setTerminalChecker(TerminalChecker terminalChecker) {
		this.terminalChecker = terminalChecker;
	}

	/** 
	 * checkLegality。
	 * @see org.quincy.rock.comm.communicate.TerminalChannelMapping#checkLegality(java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Object)
	 */
	@Override
	public Object checkLegality(Object msgId, Object funcCode, UChannel channel, Map<String, Object> ctx,
			Object content) {
		Object termId = this.findMapping(channel);
		if (termId == null && checkTerminal(funcCode, channel.remote(), channel, ctx, content)) {
			termId = channel.remote() == null ? null : channel.remote().cloneMe();
		}
		return termId;
	}

	/** 
	 * createTerminalId。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannelMapping#createTerminalId(java.lang.Object, org.quincy.rock.comm.communicate.IChannel, java.util.Map, java.lang.Object)
	 */
	@Override
	protected TerminalId<?, ?> createTerminalId(Object funcCode, UChannel channel, Map<String, Object> ctx,
			Object content) {
		TerminalId<?, ?> term = channel.remote();
		if (term == null)
			throw new NullPointerException("Terminal is null.");
		else if (checkTerminal(funcCode, term, channel, ctx, content))
			return term.cloneMe();
		else
			return null;
	}

	/**
	 * <b>处理过期的报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果需要返回过期响应报文，则可以在此生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * @param funcCode 功能码
	 * @param timestamp 时间戳
	 * @param channel 通道
	 * @param ctx 报文上下文对象
	 * @param content 报文正文
	 * @return 是否中断创建终端id
	 * @see CommUtils
	 */
	@Override
	protected boolean processExpired(Object funcCode, long timestamp, UChannel channel, Map<String, Object> ctx,
			Object content) {
		//默认是过期即中断创建
		return true;
	}

	/**
	 * <b>检查终端合法性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 可以将不合法的原因生成响应返回给通道用户，返回响应的正文数据放在上下文对象中（CommUtils.COMM_MSG_DIRECT_RESPONE_KEY）。
	 * 当然，也可以抛出异常中断该通道。
	 * @param funcCode 功能码
	 * @param termId 终端id
	 * @param channel 通道
	 * @param ctx 报文上下文对象
	 * @param content 报文正文
	 * @return 是否合法
	 * @see CommUtils
	 */
	@SuppressWarnings("unchecked")
	protected boolean checkTerminal(Object funcCode, TerminalId<?, ?> termId, UChannel channel, Map<String, Object> ctx,
			Object content) {
		return terminalChecker.checkTerminal(termId);
	}
}
