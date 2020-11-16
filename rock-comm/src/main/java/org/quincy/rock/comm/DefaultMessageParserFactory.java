package org.quincy.rock.comm;

import java.util.List;

import org.quincy.rock.comm.MessageParser.MsgType;
import org.quincy.rock.comm.parser.ErrorMessageParser;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.bean.CascadeMap;
import org.quincy.rock.core.exception.UnsupportException;

/**
 * <b>DefaultMessageParserFactory。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 下午1:08:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class DefaultMessageParserFactory<K> implements MessageParserFactory<K> {
	/**
	 * messageHeadParser。
	 */
	private MessageHeadParser<?, ?> messageHeadParser;
	/**
	 * messageParserMap。
	 */
	private final CascadeMap<K, String, MessageParser<K, ?, ?>> messageParserMap4Receive = new CascadeMap<>();
	/**
	 * messageParserMap。
	 */
	private final CascadeMap<K, String, MessageParser<K, ?, ?>> messageParserMap4Send = new CascadeMap<>();
	/**
	 * defaultMessageParser。
	 */
	private MessageParser<K, ?, ?> defaultMessageParser = ErrorMessageParser.INSTANCE;

	/**
	 * <b>获得存放解析器的容器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param msgType 消息类型
	 * @return 存放解析器的容器
	 */
	protected CascadeMap<K, String, MessageParser<K, ?, ?>> getMessageParserMap(MsgType msgType) {
		CascadeMap<K, String, MessageParser<K, ?, ?>> map;
		switch (msgType) {
		case RECEIVE:
			map = messageParserMap4Receive;
			break;
		case SEND:
			map = messageParserMap4Send;
			break;
		default:
			throw new UnsupportException(msgType.name());
		}
		return map;
	}

	/** 
	 * getMessageHeadParser。
	 * @see org.quincy.rock.comm.MessageParserFactory#getMessageHeadParser()
	 */
	@Override
	public <M, V> MessageHeadParser<M, V> getMessageHeadParser() {
		return (MessageHeadParser<M, V>) messageHeadParser;
	}

	/**
	 * <b>设置报文头解析器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageHeadParser 报文头解析器
	 */
	public void setMessageHeadParser(MessageHeadParser<?, ?> messageHeadParser) {
		this.messageHeadParser = messageHeadParser;
	}

	/** 
	 * getMessageParser。
	 * @see org.quincy.rock.comm.MessageParserFactory#getMessageParser(java.lang.Object, org.quincy.rock.comm.MessageParser.MsgType, java.lang.String)
	 */
	@Override
	public <M, V> MessageParser<K, M, V> getMessageParser(K functionCode, MsgType msgType, String contentType) {
		CascadeMap<K, String, MessageParser<K, ?, ?>> messageParserMap = getMessageParserMap(msgType);
		MessageParser<K, ?, ?> parser = messageParserMap.get(functionCode, contentType);
		if (parser == null)
			parser = messageParserMap.get(functionCode, CommUtils.MESSAGE_TYPE_ALL);
		if (parser == null)
			parser = getSpecificMessageParser(functionCode, msgType, contentType);
		return (MessageParser<K, M, V>) (parser == null ? defaultMessageParser : parser);
	}

	/**
	 * <b>获得特殊或特定的报文正文解析器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param msgType 报文类型
	 * @param contentType 报文内容类型 
	 * @return 报文正文解析器
	 */
	protected <M, V> MessageParser<K, M, V> getSpecificMessageParser(K functionCode, MsgType msgType,
			String contentType) {
		return null;
	}

	/**
	 * <b>添加报文正文解析器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param parser 报文正文解析器
	 */
	public void addMessageParser(MessageParser<K, ?, ?> parser) {
		for (K code : parser.getFunctionCode()) {
			for (String type : parser.getContentType()) {
				MsgType msgType = parser.getMessageType();
				if (msgType == null || msgType == MsgType.ALL) {
					messageParserMap4Receive.put(code, type, parser);
					messageParserMap4Send.put(code, type, parser);
				} else {
					getMessageParserMap(msgType).put(code, type, parser);
				}
			}
		}
	}

	/**
	 * <b>设置报文正文解析器列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param list 报文正文解析器列表
	 */
	public void setMessageParsers(List<MessageParser<K, ?, ?>> list) {
		for (MessageParser<K, ?, ?> parser : list) {
			addMessageParser(parser);
		}
	}

	/**
	 * <b>设置默认的报文正文解析器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultMessageParser 默认的报文正文解析器
	 */
	public void setDefaultMessageParser(MessageParser<K, ?, ?> defaultMessageParser) {
		this.defaultMessageParser = defaultMessageParser;
	}
}
