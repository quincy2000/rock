package org.quincy.rock.comm.communicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.quincy.rock.core.util.HasPattern;

/**
 * <b>AbstractPatternTerminalChannelMapping。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 为终端通道映射增加群发支持。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月1日 下午5:02:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked" })
public abstract class AbstractPatternTerminalChannelMapping<UChannel extends IChannel>
		extends AbstractTerminalChannelMapping<UChannel> implements PatternChannelMapping<UChannel> {

	/** 
	 * findChannelByExample。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannelMapping#findChannelByExample(org.quincy.rock.comm.communicate.IChannel)
	 */
	@Override
	public UChannel findChannelByExample(UChannel example) {
		UChannel ch = null;
		if (example instanceof IChannel || example.isPattern()) {
			for (UChannel channel : channels()) {
				if (isMatched(channel, example)) {
					ch = channel;
					break;
				}
			}
		} else {
			//精确终端
			ch = findChannel(example);
		}
		return ch;
	}

	/** 
	 * findChannels。
	 * @see org.quincy.rock.comm.communicate.PatternChannelMapping#findChannels(org.quincy.rock.core.util.HasPattern)
	 */
	@Override
	public Collection<UChannel> findChannels(HasPattern pattern) {
		if (pattern instanceof IChannel || pattern.isPattern()) {
			Collection<UChannel> list = new ArrayList<>();
			for (UChannel channel : channels()) {
				if (isMatched(channel, pattern)) {
					list.add(channel);
				}
			}
			return list;
		} else {
			//精确终端
			UChannel ch = findChannel(pattern);
			return ch == null ? Collections.EMPTY_LIST : Arrays.asList(ch);
		}
	}

	/** 
	 * destroyMappings。
	 * @see org.quincy.rock.comm.communicate.PatternChannelMapping#destroyMappings(org.quincy.rock.core.util.HasPattern)
	 */
	@Override
	public Collection<Object> destroyMappings(HasPattern pattern) {
		if (pattern.isPattern()) {
			//模式
			List<UChannel> channels = new ArrayList<>();
			for (UChannel channel : channels()) {
				if (isMatched(channel, pattern))
					channels.add(channel);
			}
			List<Object> list = new ArrayList<>(channels.size());
			for (UChannel ch : channels) {
				Object terminalId = destroyMapping(ch);
				if (terminalId != null)
					list.add(terminalId);
			}
			return list;
		} else if (pattern instanceof IChannel) {
			//精确通道
			Object terminalId = destroyMapping((UChannel) pattern);
			return terminalId == null ? Collections.EMPTY_LIST : Arrays.asList(terminalId);
		} else {
			//精确终端
			UChannel ch = findChannel(pattern);
			Object terminalId = ch == null ? null : destroyMapping(ch);
			return terminalId == null ? Collections.EMPTY_LIST : Arrays.asList(terminalId);
		}
	}

	/**
	 * <b>检查是否匹配模式。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 要检查的通道,不能为null
	 * @param pattern 通道匹配模式,不能为null
	 * @return 是否匹配模式
	 */
	protected boolean isMatched(UChannel channel, HasPattern pattern) {
		return pattern.isMatched(channel);
	}
}
