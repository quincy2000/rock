package org.quincy.rock.comm.communicate;

import java.util.Collection;

import org.quincy.rock.core.util.HasPattern;

/**
 * <b>模式匹配映射 for 群发报文。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 增加群发能力。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月31日 上午10:38:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface PatternChannelMapping<UChannel> extends TerminalChannelMapping<UChannel> {

	/**
	 * <b>获得所有符合匹配模式的通道列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pattern 匹配通道模式
	 * @return 通道列表
	 */
	public Collection<UChannel> findChannels(HasPattern pattern);

	/**
	 * <b>销毁符合模式条件的所有通道终端映射。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pattern 匹配通道模式
	 * @return 销毁的终端唯一标识列表
	 */
	public Collection<Object> destroyMappings(HasPattern pattern);
}
