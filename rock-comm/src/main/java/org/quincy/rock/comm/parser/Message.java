package org.quincy.rock.comm.parser;

import java.util.HashMap;
import java.util.Map;

import org.quincy.rock.comm.process.QueueMessage;
import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>报文消息基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月4日 上午12:11:45</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class Message<BUF> extends QueueMessage {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 801625974609501815L;

	/**
	 * 用户自定义标记Map。
	 */
	private Map<String, Object> tagMap;

	/**
	 * <b>用户自定义标记Map。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return tagMap 用户自定义标记Map
	 */
	public synchronized Map<String, Object> tagMap() {
		if (tagMap == null) {
			tagMap = new HashMap<>();
		}
		return tagMap;
	}

	/**
	 * <b>初始化。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ctx 上下文对象
	 */
	protected void initialize(Map<String, Object> ctx) {
		if (this.cmdCode == null)
			this.cmdCode = ctx.get(CommUtils.COMM_FUNCTION_CODE_KEY);
		if (this.messageTime == null)
			this.messageTime = (Long) ctx.get(CommUtils.COMM_MSG_TIMESTAMP_KEY);
		if (this.msgId == null)
			this.msgId = ctx.get(CommUtils.COMM_MSG_ID_KEY);
		if (this.terminalId == null)
			this.terminalId = ctx.get(CommUtils.COMM_TERMINAL_ID_KEY);
	}

	/**
	 * <b>初始化一次。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 仅初始化一次，第二次调用将忽略。
	 * @param ctx 上下文对象
	 */
	protected final void initializeOnce(Map<String, Object> ctx) {
		if (!initialized) {
			this.initialize(ctx);
			initialized = true;
		}
	}

	private boolean initialized = false;

	/**
	 * <b>将报文对象pack成二进制报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param ctx 上下文对象
	 * @return ByteBuf
	 */
	public abstract BUF toBinary(BUF buf, Map<String, Object> ctx);

	/**
	 * <b>将二进制报文转换成报文对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param buf ByteBuf
	 * @param ctx 上下文对象
	 * @return this
	 */
	public abstract Message<BUF> fromBinary(BUF buf, Map<String, Object> ctx);
}
