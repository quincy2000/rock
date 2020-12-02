package org.quincy.rock.comm.process;

import org.quincy.rock.comm.MessageSender;
import org.quincy.rock.core.cache.HasTimestamp;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>排队消息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月9日 下午4:02:22</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueueMessage extends Vo<String> implements HasTimestamp {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 4475451004311260030L;

	/**
	 * 时间戳。
	 */
	private long timestamp = System.currentTimeMillis();

	/**
	 * 终端标识。
	 */
	protected Object terminalId;
	/**
	 * 消息id。
	 */
	protected Object msgId;
	/**
	 * 功能码。
	 */
	protected Object cmdCode;
	/**
	 * 报文消息传输时间戳。
	 */
	protected Long messageTime;

	/**
	 * 报文正文。
	 */
	Object content;
	/**
	 * 该报文在进入处理队列之前是否已经处理过。
	 */
	boolean processed;

	/**
	 * 报文处理器。
	 */	
	transient MessageProcessor processor;
	/**
	 * 报文发送者。
	 */
	transient MessageSender sender;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public QueueMessage() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param content 内容
	 */
	QueueMessage(Object content) {
		this.content = content;
	}

	/** 
	 * 消息流水号。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public final String id() {
		return msgId == null ? null : msgId.toString();
	}

	/**
	 * <b>该报文在进入处理队列之前是否已经处理过。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 该报文在进入处理队列之前是否已经处理过
	 */
	public final boolean processed() {
		return processed;
	}

	/** 
	 * 消息对象被创建的时间。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	public final long timestamp() {
		return timestamp;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public final void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * <b>返回功能码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 功能码
	 */
	public Object cmdCode() {
		return cmdCode;
	}

	/**
	 * <b>返回报文消息传输时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文消息传输时间戳
	 */
	public Long messageTime() {
		return messageTime;
	}

	/**
	 * <b>返回终端标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端标识
	 */
	public Object terminalId() {
		return terminalId;
	}

	/**
	 * <b>处理报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sender 报文发送者
	 */
	final void process(MessageSender<?> sender) {
		processor.process(this.sender == null ? sender : this.sender, terminalId, msgId,
				content == null ? this : content);
	}
}
