package org.quincy.rock.comm.cmd;

import org.quincy.rock.comm.MessageSender;
import org.quincy.rock.core.concurrent.DelayQueueProcessService;
import org.quincy.rock.core.concurrent.Processor;
import org.quincy.rock.core.exception.NotFoundException;

/**
 * <b>延迟指令发送服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年3月12日 下午1:38:43</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DelayCommandSendService extends DelayQueueProcessService<Object, Command<?>>
		implements Processor<Command<?>> {

	/**
	 * 报文发送者。
	 */
	private MessageSender<?> messageSender;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DelayCommandSendService() {
		this(2);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 */
	public DelayCommandSendService(int maxThreadCount) {
		super(maxThreadCount);
	}

	/**
	 * <b>获得报文发送者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文发送者
	 */
	public MessageSender<?> getMessageSender() {
		return messageSender;
	}

	/**
	 * <b>设置报文发送者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageSender 报文发送者
	 */
	public void setMessageSender(MessageSender<?> messageSender) {
		this.messageSender = messageSender;
	}

	/** 
	 * process。
	 * @see org.quincy.rock.core.concurrent.Processor#process(java.lang.Object)
	 */
	@Override
	public void process(Command<?> cmd) {
		Object term = cmd.getTerminal();
		MessageSender sender = this.getMessageSender();
		sender.sendMessage(term, cmd.getMsgId(), cmd.getCmdCode(), cmd.getMessage(), cmd.getAttachment(), cmd.isAsync(),
				cmd.consumer());
	}

	/** 
	 * getProcessor。
	 * @see org.quincy.rock.core.concurrent.ProcessService#getProcessor(java.lang.Object)
	 */
	@Override
	protected Processor<Command<?>> getProcessor(Object key) throws NotFoundException {
		return this;
	}

}
