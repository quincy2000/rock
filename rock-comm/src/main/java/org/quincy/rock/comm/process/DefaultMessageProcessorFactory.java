package org.quincy.rock.comm.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quincy.rock.core.util.HasOwner;

/**
 * <b>DefaultMessageProcessorFactory。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月7日 下午3:12:21</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DefaultMessageProcessorFactory<K> implements MessageProcessorFactory<K>, HasOwner<Object> {
	/**
	 * processorMap。
	 */
	private Map<K, MessageProcessor<K, ?>> processorMap = new HashMap();

	/**
	 * owner。
	 */
	protected Object owner;

	/**
	 * 默认的报文处理器。
	 */
	private MessageProcessor<K, ?> defaultMessageProcessor;

	/** 
	 * getOwner。
	 * @see org.quincy.rock.core.util.HasOwner#getOwner()
	 */
	public Object getOwner() {
		return owner;
	}

	/** 
	 * setOwner。
	 * @see org.quincy.rock.core.util.HasOwner#setOwner(java.lang.Object)
	 */
	public void setOwner(Object owner) {
		this.owner = owner;
	}

	/** 
	 * hasOwner。
	 * @see org.quincy.rock.core.util.HasOwner#hasOwner()
	 */
	@Override
	public boolean hasOwner() {
		return this.owner != null;
	}

	/**
	 * <b>获得默认的报文处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 默认的报文处理器
	 */
	public MessageProcessor<K, ?> getDefaultMessageProcessor() {
		return defaultMessageProcessor;
	}

	/**
	 * <b>设置默认的报文处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultMessageProcessor 默认的报文处理器
	 */
	public void setDefaultMessageProcessor(MessageProcessor<K, ?> defaultMessageProcessor) {
		this.defaultMessageProcessor = defaultMessageProcessor;
	}

	/** 
	 * getMessageProcessor。
	 * @see org.quincy.rock.comm.process.MessageProcessorFactory#getMessageProcessor(java.lang.Object)
	 */
	@Override
	public <M> MessageProcessor<K, M> getMessageProcessor(K functionCode) {
		MessageProcessor<K, M> processor = (MessageProcessor<K, M>) processorMap.get(functionCode);
		if (processor == null)
			processor = (MessageProcessor) this.getDefaultMessageProcessor();
		if (owner != null && processor instanceof HasOwner) {
			HasOwner<Object> me = (HasOwner) processor;
			if (!me.hasOwner())
				me.setOwner(owner);
		}
		return processor;
	}

	/**
	 * <b>添加报文内容处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processor 报文内容处理器
	 */
	public void addMessageProcessor(MessageProcessor<K, ?> processor) {
		processorMap.put(processor.getFunctionCode(), processor);
	}

	/**
	 * <b>设置报文内容处理器列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param list 报文内容处理器列表
	 */
	public void setMessageProcessors(List<MessageProcessor<K, ?>> list) {
		for (MessageProcessor<K, ?> processor : list) {
			addMessageProcessor(processor);
		}
	}
}
