package org.quincy.rock.comm.process;

/**
 * <b>DirectMessageProcessService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月9日 下午3:17:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DirectMessageProcessService<K> extends MessageProcessService<K> {

	/** 
	 * handleArrivedMessage。
	 * @see org.quincy.rock.comm.process.MessageProcessService#handleArrivedMessage(org.quincy.rock.comm.process.QueueMessage)
	 */
	@Override
	protected void handleArrivedMessage(QueueMessage queueMessage) {
		this.processQueueMessage(queueMessage);
	}

	/** 
	 * awaitTermination。
	 * @see org.quincy.rock.comm.process.MessageProcessService#awaitTermination()
	 */
	@Override
	protected void awaitTermination() throws Exception {
		
	}
}
