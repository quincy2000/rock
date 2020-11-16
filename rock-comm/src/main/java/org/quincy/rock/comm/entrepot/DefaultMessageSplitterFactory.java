package org.quincy.rock.comm.entrepot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.NotFoundException;

/**
 * <b>DefaultMessageSplitterFactory。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月4日 下午2:18:38</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DefaultMessageSplitterFactory implements MessageSplitterFactory {
	/**
	 * splitterMap。
	 */
	private Map<String, MessageSplitter> splitterMap = new HashMap<>();

	/** 
	 * getMessageSplitter。
	 * @see org.quincy.rock.comm.entrepot.MessageSplitterFactory#getMessageSplitter(java.lang.String)
	 */
	@Override
	public MessageSplitter getMessageSplitter(String contentType) {
		MessageSplitter splitter = splitterMap.get(contentType);
		if (splitter == null)
			splitter = splitterMap.get(CommUtils.MESSAGE_TYPE_ALL);		
		if (splitter == null)
			throw new NotFoundException("Could not find MessageSplitter for " + contentType);
		return splitter;
	}

	/**
	 * <b>添加报文拆分器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param splitter 报文拆分器
	 */
	public void addMessageSplitter(MessageSplitter splitter) {
		for (String type : splitter.getContentType()) {
			splitterMap.put(type, splitter);
		}
	}

	/**
	 * <b>设置报文拆分器列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param list 报文拆分器列表
	 */
	public void setMessageSplitters(List<MessageSplitter> list) {
		for (MessageSplitter splitter : list) {
			addMessageSplitter(splitter);
		}
	}
}
