package org.quincy.rock.comm.process;

import java.util.ArrayList;
import java.util.List;

import org.quincy.rock.comm.MessageSender;
import org.quincy.rock.core.util.HasOwner;

/**
 * <b>MultiMessageProcessorFactory。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 每个功能码可以有多个处理器。
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
public class MultiMessageProcessorFactory<K> extends DefaultMessageProcessorFactory<K> {

	/** 
	 * addMessageProcessor。
	 * @see org.quincy.rock.comm.process.DefaultMessageProcessorFactory#addMessageProcessor(org.quincy.rock.comm.process.MessageProcessor)
	 */
	@Override
	public void addMessageProcessor(MessageProcessor<K, ?> processor) {
		K code = processor.getFunctionCode();
		MultiMessageProcessor mmp = (MultiMessageProcessor) this.getMessageProcessor(code);
		if (mmp == null) {
			mmp = new MultiMessageProcessor(code);
			super.addMessageProcessor(mmp);
		}
		mmp.addMessageProcessor((MessageProcessor) processor);
	}

	//
	private class MultiMessageProcessor extends AbstractMessageProcessor<K, Object> {
		private final List<MessageProcessor<K, Object>> list = new ArrayList();

		public MultiMessageProcessor(K functionCode) {
			super(functionCode);
		}

		public void addMessageProcessor(MessageProcessor<K, Object> processor) {
			list.add(processor);
		}

		@Override
		public void process(MessageSender<K> sender, Object terminalId, Object msgId, Object content) {
			for (MessageProcessor<K, Object> processor : list) {
				try {
					if (this.getOwner() != null && processor instanceof HasOwner) {
						HasOwner<Object> hasOwner = (HasOwner) processor;
						if (hasOwner.getOwner() == null)
							hasOwner.setOwner(this.getOwner());
					}
					processor.process(sender, terminalId, msgId, content);
				} catch (Exception e) {
				}
			}
		}
	}
}
