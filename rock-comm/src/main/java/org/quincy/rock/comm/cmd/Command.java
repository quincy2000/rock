package org.quincy.rock.comm.cmd;

import java.util.HashMap;
import java.util.Map;

import org.quincy.rock.core.cache.HasTimestamp;
import org.quincy.rock.core.function.ValueConsumer;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>发送报文指令封装对象。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 指令对象用于封装要发送的报文信息。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月23日 下午3:03:58</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Command<TERM> extends Vo<Object> implements HasTimestamp {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -6238074695409135166L;

	/**
	 * 终端标识。
	 */
	protected TERM terminal;
	/**
	 * 报文正文。
	 */
	protected Object message;

	/**
	 * 指令代码。
	 */
	private Object cmdCode;
	/**
	 * 报文流水号。
	 */
	private Object msgId;
	/**
	 * 是否是异步发送。
	 */
	private boolean async;
	/**
	 * 附加属性。
	 */
	private Map<String, Object> attachment;
	/**
	 * 时间戳。
	 */
	private long timestamp = System.currentTimeMillis();
	/**
	 * 回调Consumer,指示发送是否成功。
	 */
	private ValueConsumer<Boolean> consumer;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public Command() {
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Object id() {
		return msgId;
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	@Override
	public long timestamp() {
		return timestamp;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	/** 
	 * clone。
	 * @see org.quincy.rock.core.vo.Vo#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Command cmd = (Command) super.clone();
		cmd.timestamp = System.currentTimeMillis();
		if (cmd.attachment != null)
			cmd.attachment = new HashMap(cmd.attachment);
		return cmd;
	}

	/**
	 * <b>获得时间戳。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 时间戳
	 */
	public long getTimestamp() {
		return timestamp();
	}

	/**
	 * <b>获得指令代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指令代码
	 */
	public Object getCmdCode() {
		return cmdCode;
	}

	/**
	 * <b>设置指令代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmdCode 指令代码
	 */
	public void setCmdCode(Object cmdCode) {
		this.cmdCode = cmdCode;
	}

	/**
	 * <b>获得报文流水号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文流水号
	 */
	public Object getMsgId() {
		return msgId;
	}

	/**
	 * <b>设置报文流水号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msgId 报文流水号
	 */
	public void setMsgId(Object msgId) {
		this.msgId = msgId;
	}

	/**
	 * <b>获得终端标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端标识
	 */
	public TERM getTerminal() {
		return terminal;
	}

	/**
	 * <b>设置终端标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端标识
	 */
	public void setTerminal(TERM terminal) {
		this.terminal = terminal;
	}

	/**
	 * <b>获得报文正文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文正文
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * <b>设置报文正文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 报文正文
	 */
	public void setMessage(Object message) {
		this.message = message;
	}

	/**
	 * <b>是否是异步发送。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是异步发送
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * <b>是否是异步发送。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param async 是否是异步发送
	 */
	public void setAsync(boolean async) {
		this.async = async;
	}

	/**
	 * <b>是否有附加属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否有附加属性
	 */
	public boolean hasAttachment() {
		return !CoreUtil.isEmpty(attachment);
	}

	/**
	 * <b>是否有附加属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 键
	 * @return 是否有附加属性
	 */
	public boolean hasAttachment(String key) {
		return !CoreUtil.isEmpty(attachment) && attachment.containsKey(key);
	}

	/**
	 * <b>获得附加属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 附加属性
	 */
	public synchronized Map<String, Object> getAttachment() {
		if (attachment == null) {
			attachment = new HashMap<>();
		}
		return attachment;
	}

	/**
	 * <b>设置附加属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param attachment 附加属性
	 */
	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}

	/**
	 * <b>添加附加属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 附加属性key
	 * @param value 附加属性value
	 */
	public void putAttachment(String key, Object value) {
		this.getAttachment().put(key, value);
	}

	/**
	 * <b>返回回调Consumer,指示发送是否成功。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 回调Consumer,指示发送是否成功
	 */
	public ValueConsumer<Boolean> consumer() {
		return consumer;
	}

	/**
	 * <b>设置回调Consumer,指示发送是否成功。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param consumer 回调Consumer,指示发送是否成功
	 */
	public void consumer(ValueConsumer<Boolean> consumer) {
		this.consumer = consumer;
	}

	/**
	 * <b>修正指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param attachment 用于修正的附加属性,可以为null
	 * @return 修正的指令对象
	 */
	public <T extends Command<TERM>> T correction(Map<String, Object> attachment) {
		if (!CoreUtil.isEmpty(attachment)) {
			Map<String, Object> map = this.getAttachment();
			for (String key : attachment.keySet()) {
				if (!map.containsKey(key)) {
					map.put(key, attachment.get(key));
				}
			}
		}
		return (T) this;
	}
}
