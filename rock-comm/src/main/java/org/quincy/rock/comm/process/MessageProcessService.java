package org.quincy.rock.comm.process;

import java.util.Map;

import org.quincy.rock.comm.MessageAdapter;
import org.quincy.rock.comm.MessageListener;
import org.quincy.rock.comm.MessageSender;
import org.quincy.rock.comm.MessageService;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.BlcokingException;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.lang.Recorder;

/**
 * <b>报文处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月9日 下午1:13:37</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MessageProcessService<K> {
	/**
	 * _messageListener。
	 */
	private final MessageListener<K> _messageListener;
	/**
	 * messageProcessorFactory。
	 */
	private MessageProcessorFactory<K> processorFactory;
	/**
	 * 报文服务。
	 */
	private MessageService<K, ?> messageService;
	/**
	 * 停止的。
	 */
	private boolean stopped = true;
	/**
	 * 仅仅允许绑定一个MessageService。
	 */
	private boolean bindOne;
	/**
	 * 忽略[处理完成标志]并继续调用处理器(CommUtils.COMM_MSG_PROCESS_DONE_KEY)。
	 */
	private boolean ignoreDoneFlag;

	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MessageProcessService() {
		this._messageListener = new MessageAdapter<K>() {

			@Override
			public void messageArrived(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode,
					Object content, Map<String, Object> ctx) {
				boolean done = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_PROCESS_DONE_KEY));
				if (done && !isIgnoreDoneFlag())
					return;
				if (isStopped()) {
					BlcokingException ex = new BlcokingException("MessageProcessService is stopped!");
					recorder.write(ex, ex.getMessage());
					throw ex;
				}
				MessageProcessor<K, Object> processor = processorFactory().getMessageProcessor(functionCode);
				if (processor == null) {
					NotFoundException ex = new NotFoundException("Could not find MessageProcessor" + functionCode);
					recorder.write(ex, ex.getMessage());
					throw ex;
				}
				QueueMessage queueMessage = (content instanceof QueueMessage) ? (QueueMessage) content
						: new QueueMessage(content);
				queueMessage.terminalId = terminalId;
				queueMessage.msgId = msgId;
				queueMessage.processor = processor;
				queueMessage.sender = bindOne ? null : sender;
				queueMessage.cmdCode = functionCode;
				queueMessage.messageTime = (Long) ctx.get(CommUtils.COMM_MSG_TIMESTAMP_KEY);
				queueMessage.processed = done;
				handleArrivedMessage(queueMessage);
			}

		};
	}

	/**
	 * <b>仅仅允许绑定一个MessageService 。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 仅仅允许绑定一个MessageService
	 */
	public boolean isBindOne() {
		return bindOne;
	}

	/**
	 * <b>仅仅允许绑定一个MessageService 。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bindOne 仅仅允许绑定一个MessageService
	 */
	public void setBindOne(boolean bindOne) {
		this.bindOne = bindOne;
	}

	/**
	 * <b>日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 日志记录器
	 */
	public Recorder getRecorder() {
		return recorder;
	}

	/**
	 * <b>日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder 日志记录器
	 */
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	/**
	 * <b>忽略[处理完成标志]并继续调用处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 参考:CommUtils.COMM_MSG_PROCESS_DONE_KEY。
	 * @return 忽略[处理完成标志]并继续调用处理器
	 */
	public boolean isIgnoreDoneFlag() {
		return ignoreDoneFlag;
	}

	/**
	 * <b>忽略[处理完成标志]并继续调用处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 参考:CommUtils.COMM_MSG_PROCESS_DONE_KEY。
	 * @param ignoreDoneFlag 忽略[处理完成标志]并继续调用处理器
	 */
	public void setIgnoreDoneFlag(boolean ignoreDoneFlag) {
		this.ignoreDoneFlag = ignoreDoneFlag;
	}

	/**
	 * <b>绑定报文服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageService 报文服务
	 */
	public final void bind(MessageService<K, ?> messageService) {
		if (this.messageService != null)
			this.messageService.removeMessageListener(_messageListener);
		if (messageService != null)
			messageService.addMessageListener(_messageListener);
		this.messageService = bindOne ? messageService : null;
	}

	/**
	 * <b>解绑报文服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageService 报文服务
	 */
	public final void unbind(MessageService<K, ?> messageService) {
		if (messageService != null) {
			messageService.removeMessageListener(_messageListener);
			if (this.messageService == messageService)
				this.messageService = null;
		}
		if (bindOne && this.messageService != null) {
			this.messageService.removeMessageListener(_messageListener);
			this.messageService = null;
		}
	}

	/**
	 * <b>设置报文处理器工厂。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processorFactory 报文处理器工厂
	 */
	public final void processorFactory(MessageProcessorFactory<K> processorFactory) {
		this.processorFactory = processorFactory;
	}

	/**
	 * <b>返回报文处理器工厂。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文处理器工厂
	 */
	public final MessageProcessorFactory<K> processorFactory() {
		return this.processorFactory;
	}

	/**
	 * <b>处理队里消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param queueMessage 队列消息
	 */
	protected final void processQueueMessage(QueueMessage queueMessage) {
		try {
			queueMessage.process(messageService);
		} catch (Exception e) {
			recorder.write(e, "processQueueMessage:{0}", e.getMessage());
		}
	}

	/**
	 * <b>收到报文时调用该方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 子类继承该方法处理收到的报文消息。
	 * @param queueMessage 排队报文
	 * @exception BlcokingException
	 */
	protected abstract void handleArrivedMessage(QueueMessage queueMessage) throws BlcokingException;

	/**
	 * <b>开始处理报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void start() {
		if (isStopped()) {
			this.stopped = false;
			recorder.write("Start the message processing service.");
		}
	}

	/**
	 * <b>停止处理报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void stop() {
		if (!isStopped()) {
			this.stopped = true;
			recorder.write("Stop the message processing service.");
		}
	}

	/**
	 * <b>isStopped。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public final boolean isStopped() {
		return this.stopped;
	}

	/**
	 * <b>重新启动处理服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void reset() {
		this.destroy();
		this.start();
	}

	/**
	 * <b>销毁处理服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public final void destroy() {
		this.stop();
		//等待完成
		try {
			this.awaitTermination();
		} catch (Exception e) {
			recorder.write(e, e.getMessage());
		}
		recorder.write("Exit the message processing service.");
	}

	/**
	 * <b>等待缓存数据处理完成。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	protected abstract void awaitTermination() throws Exception;
}
