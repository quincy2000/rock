package org.quincy.rock.comm.entrepot;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.quincy.rock.core.function.Consumer;

/**
 * <b>报文仓库抽象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月12日 下午4:12:52</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractMessageEntrepot implements MessageEntrepot {

	/**
	 * 监听器列表。
	 */
	private List<MessageEntrepotListener> listeners = new Vector<MessageEntrepotListener>();

	/** 
	 * addMessageEntrepotListener。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#addMessageEntrepotListener(org.quincy.rock.comm.entrepot.MessageEntrepotListener)
	 */
	@Override
	public void addMessageEntrepotListener(MessageEntrepotListener listener) {
		this.listeners.add(listener);
	}

	/** 
	 * removeMessageEntrepotListener。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#removeMessageEntrepotListener(org.quincy.rock.comm.entrepot.MessageEntrepotListener)
	 */
	@Override
	public void removeMessageEntrepotListener(MessageEntrepotListener listener) {
		this.listeners.remove(listener);
	}

	/** 
	 * removeAllMessageEntrepotListener。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepot#removeAllMessageEntrepotListener()
	 */
	@Override
	public void removeAllMessageEntrepotListener() {
		this.listeners.clear();
	}

	/**
	 * <b>fireToSentMessageAddedEvent。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	protected void fireToSentMessageAddedEvent(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		for (MessageEntrepotListener listener : listeners) {
			listener.toSentMessageAdded(this, terminalId, msgId, cmdCode, content, ctx);
		}
	}

	/**
	 * <b>fireToSentMessageAddDoneEvent。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 * @param success 是否发送成功
	 */
	protected void fireToSentMessageAddDoneEvent(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx, boolean success) {
		for (MessageEntrepotListener listener : listeners) {
			listener.toSentMessageAddDone(this, terminalId, msgId, cmdCode, content, ctx, success);
		}
	}

	/**
	 * <b>fireArrivedMessageAddedEvent。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	protected void fireArrivedMessageAddedEvent(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		for (MessageEntrepotListener listener : listeners) {
			listener.arrivedMessageAdded(this, terminalId, msgId, cmdCode, content, ctx);
		}
	}

	/**
	 * <b>fireArrivedMessageAddDoneEvent。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	protected void fireArrivedMessageAddDoneEvent(Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {
		for (MessageEntrepotListener listener : listeners) {
			listener.arrivedMessageAddDone(this, terminalId, msgId, cmdCode, content, ctx);
		}
	}

	/**
	 * <b>创建并初始化本地报文发送完成回调Consumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalId 远程终端或主机唯一标识
	 * @param msgId 消息id
	 * @param cmdCode 指令代码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 * @param consumer 异步回调函数
	 * @return 本地报文发送完成时的回调Consumer
	 */
	protected Consumer<Boolean> createMsgSentDoneConsumer(Object terminalId, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx, Consumer<Boolean> consumer) {
		return new MsgSentDoneConsumer(terminalId, msgId, cmdCode, content, ctx, consumer);
	}

	/** 
	 * 本地报文发送完成时的回调方法类
	 */
	private class MsgSentDoneConsumer implements Consumer<Boolean> {
		private Consumer<Boolean> consumer;
		private Object terminalId;
		private Object msgId;
		private Object cmdCode;
		private Object content;
		private Map<String, Object> ctx;

		public MsgSentDoneConsumer(Object terminalId, Object msgId, Object cmdCode, Object content,
				Map<String, Object> ctx, Consumer<Boolean> consumer) {
			this.consumer = consumer;
			this.terminalId = terminalId;
			this.msgId = msgId;
			this.cmdCode = cmdCode;
			this.content = content;
			this.ctx = ctx;
		}

		public void call(Boolean success) {
			//发送完成了
			fireToSentMessageAddDoneEvent(terminalId, msgId, cmdCode, content, ctx, success);
			if (consumer != null) {
				consumer.call(success);
			}
		}
	}
}
