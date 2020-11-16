package org.quincy.rock.message.listener;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Map;

import org.quincy.rock.comm.MessageListener;
import org.quincy.rock.comm.MessageSender;
import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.ParseException;
import org.quincy.rock.core.util.MapUtil;
import org.quincy.rock.message._Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>终端收发报文记录器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月22日 下午2:28:04</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class MessageRecorder implements MessageListener<Object> {
	/**
	 * logger,存放终端上下线信息。
	 */
	protected static final Logger logger = LoggerFactory.getLogger("rock.message.term.log");
	/**
	 * logger4Receive,存放接收报文及错误日志。
	 */
	protected static final Logger logger4Receive = LoggerFactory.getLogger("rock.message.term.receive.log");
	/**
	 * logger4Send,存放发送报文及错误日志。
	 */
	protected static final Logger logger4Send = LoggerFactory.getLogger("rock.message.term.send.log");

	/**
	 * 当解析错误的时候是否继续处理下一个报文。
	 */
	private boolean continueWhileParseError;

	/**
	 * <b>当解析错误的时候是否继续处理下一个报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param continueWhileParseError 当解析错误的时候是否继续处理下一个报文
	 */
	public void setContinueWhileParseError(boolean continueWhileParseError) {
		this.continueWhileParseError = continueWhileParseError;
	}

	/**
	 * <b>当解析错误的时候是否继续处理下一个报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当解析错误的时候是否继续处理下一个报文
	 */
	public boolean isContinueWhileParseError() {
		return continueWhileParseError;
	}

	/** 
	 * terminalOnline。
	 * @see org.quincy.rock.comm.MessageListener#terminalOnline(org.quincy.rock.comm.MessageSender, java.lang.Object)
	 */
	@Override
	public void terminalOnline(MessageSender<Object> sender, Object terminalId) {
		if (logger.isDebugEnabled()) {
			try {
				String msg = _Utils.MSA.getMessage("log.terminalOnline", new Object[] { terminalId });
				logger.debug(msg);
			} catch (Exception e) {
				logger.error("terminalOnline error!", e);
			}
		}
	}

	/** 
	 * terminalOffline。
	 * @see org.quincy.rock.comm.MessageListener#terminalOffline(org.quincy.rock.comm.MessageSender, java.lang.Object)
	 */
	@Override
	public void terminalOffline(MessageSender<Object> sender, Object terminalId) {
		if (logger.isDebugEnabled()) {
			try {
				String msg = _Utils.MSA.getMessage("log.terminalOffline", new Object[] { terminalId });
				logger.debug(msg);
			} catch (Exception e) {
				logger.error("terminalOffline error!", e);
			}
		}
	}

	/** 
	 * messageArrived。
	 * @see org.quincy.rock.comm.MessageListener#messageArrived(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void messageArrived(MessageSender<Object> sender, Object terminalId, Object msgId, Object functionCode,
			Object content, Map<String, Object> ctx) {
		if (logger4Receive.isInfoEnabled()) {
			try {
				TerminalId id = (TerminalId) terminalId;
				Timestamp timestamp = MapUtil.getTimestamp(ctx, CommUtils.COMM_MSG_TIMESTAMP_KEY);
				logger4Receive.info("receive:{type:{},code:{},address:{},msgId:{},cmdCode:{},timestamp:{},content:{}}",
						id.getType(), id.getCode(), id.getAddress(), msgId, functionCode, timestamp, content);
			} catch (Exception e) {
				logger4Receive.error("messageArrived error!", e);
			}
		}
	}

	/** 
	 * messageSended。
	 * @see org.quincy.rock.comm.MessageListener#messageSended(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, boolean)
	 */
	@Override
	public void messageSended(MessageSender<Object> sender, Object terminalId, Object msgId, Object functionCode,
			Object content, Map<String, Object> ctx, boolean success) {
		if (logger4Send.isInfoEnabled()) {
			try {
				TerminalId id = (TerminalId) terminalId;
				Timestamp timestamp = MapUtil.getTimestamp(ctx, CommUtils.COMM_MSG_TIMESTAMP_KEY);
				logger4Send.info(
						"send:{type:{},code:{},address:{},msgId:{},cmdCode:{},timestamp:{},content:{},success:{}}",
						id.getType(), id.getCode(), id.getAddress(), msgId, functionCode, timestamp, content, success);
			} catch (Exception e) {
				logger4Send.error("messageSended error!", e);
			}
		}
	}

	/** 
	 * messageHeadParserException。
	 * @see org.quincy.rock.comm.MessageListener#messageHeadParserException(org.quincy.rock.comm.MessageSender, java.lang.Object, java.util.Map, java.lang.Throwable)
	 */
	@Override
	public void messageHeadParserException(MessageSender<Object> sender, Object data, Map<String, Object> ctx,
			Throwable e) {
		boolean isReceive = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_RECEIVE_FALG));
		if (isReceive) {
			//解码报文头失败
			try {
				String msg = MessageFormat.format("Decoding message header failed:{0}，cause:{1}",
						this.toHexString(data), e.getMessage());
				logger4Receive.error(msg, e);
			} catch (Exception ex) {
				logger4Receive.error("messageHeadParserException receive error!", ex);
			}
		} else {
			//编码报文头失败
			try {
				String msg = MessageFormat.format("Encoding message header failed:{0}，cause:{1}",
						this.toHexString(data), e.getMessage());
				logger4Send.error(msg, e);
			} catch (Exception ex) {
				logger4Send.error("messageHeadParserException send error!", ex);
			}
		}
		if (!isContinueWhileParseError()) {
			throw new ParseException(e.getMessage(), e);
		}
	}

	/** 
	 * messageParserException。
	 * @see org.quincy.rock.comm.MessageListener#messageParserException(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Throwable)
	 */
	@Override
	public void messageParserException(MessageSender<Object> sender, Object terminalId, Object msgId,
			Object functionCode, Object content, Map<String, Object> ctx, Throwable e) {
		boolean isReceive = Boolean.TRUE.equals(ctx.get(CommUtils.COMM_MSG_RECEIVE_FALG));
		if (isReceive) {
			//解码报文正文失败
			try {
				String msg = MessageFormat.format(
						"Decoding message body failed:{0}，terminalId:{1},msgId:{2},cmdCode:{3},cause:{4}",
						this.toHexString(content), terminalId, msgId, functionCode, e.getMessage());
				logger4Receive.error(msg, e);
			} catch (Exception ex) {
				logger4Receive.error("messageParserException receive error!", ex);
			}
		} else {
			//编码报文正文失败
			try {
				String msg = MessageFormat.format(
						"Encoding message body failed:{0}，terminalId:{1},msgId:{2},cmdCode:{3},cause:{4}",
						this.toHexString(content), terminalId, msgId, functionCode, e.getMessage());
				logger4Send.error(msg, e);
			} catch (Exception ex) {
				logger4Send.error("messageParserException send error!", ex);
			}
		}
		if (!isContinueWhileParseError()) {
			throw new ParseException(e.getMessage(), e);
		}
	}

	/** 
	 * checkLegalityException。
	 * @see org.quincy.rock.comm.MessageListener#checkLegalityException(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void checkLegalityException(MessageSender<Object> sender, Object msgId, Object functionCode, Object content,
			Map<String, Object> ctx) {
		if (logger4Receive.isWarnEnabled()) {
			try {
				TerminalChannel ch = (TerminalChannel) MapUtil.getObject(ctx, CommUtils.COMM_CHANNEL_KEY);
				logger4Receive.warn("Illegal terminal:{}!",ch.remote());
			} catch (Exception ex) {
				logger4Receive.error("checkLegalityException error!", ex);
			}
		}
	}

	/**
	 * <b>将原始报文数据转换成十六进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 原始报文数据
	 * @return 十六进制字符串
	 */
	protected abstract String toHexString(Object data);
}
