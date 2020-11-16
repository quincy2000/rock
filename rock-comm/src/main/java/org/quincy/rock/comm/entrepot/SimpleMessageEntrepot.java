package org.quincy.rock.comm.entrepot;

import java.util.Map;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.function.Consumer;

/**
 * <b>简单报文仓库。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 不能处理分组报文,不对报文进行缓存控制。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2016年1月7日 下午4:54:34</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class SimpleMessageEntrepot extends AbstractMessageEntrepot {

	/** 
	 * addToSentMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#addToSentMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void addToSentMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		Consumer<Boolean> consumer = (Consumer<Boolean>) ctx.get(CommUtils.COMM_ASYNC_CALLBACK_KEY);
		consumer = createMsgSentDoneConsumer(terminalId, msgId, cmdCode, content, ctx, consumer);
		ctx.put(CommUtils.COMM_ASYNC_CALLBACK_KEY, consumer);
		//触发事件,发送指令
		this.fireToSentMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
	}

	/** 
	 * addArrivedMessage。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#addArrivedMessage(java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void addArrivedMessage(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		fireArrivedMessageAddedEvent(terminalId, msgId, cmdCode, content, ctx);
		fireArrivedMessageAddDoneEvent(terminalId, msgId, cmdCode, content, ctx);
	}

	/** 
	 * removeTerminal。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#removeTerminal(java.lang.Object)
	 */
	@Override
	public void removeTerminal(Object terminalId) {

	}

}
