package org.quincy.rock.comm;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.quincy.rock.comm.MessageParser.MsgType;
import org.quincy.rock.comm.communicate.CommunicateAdapter;
import org.quincy.rock.comm.communicate.CommunicateListener;
import org.quincy.rock.comm.communicate.Communicator;
import org.quincy.rock.comm.communicate.PatternChannelMapping;
import org.quincy.rock.comm.communicate.TerminalChannelMapping;
import org.quincy.rock.comm.entrepot.MessageEntrepot;
import org.quincy.rock.comm.entrepot.MessageEntrepotListener;
import org.quincy.rock.comm.process.MessageProcessService;
import org.quincy.rock.comm.process.MessageProcessorFactory;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.function.Consumer;
import org.quincy.rock.core.util.HasPattern;

/**
 * <b>报文服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 下午11:25:03</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DefaultMessageService<K, UChannel> extends AbstractMessageService<K, UChannel> {
	/**
	 * 通讯器。
	 */
	private Communicator<UChannel> communicator;
	/**
	 * 报文解析器工厂。
	 */
	private MessageParserFactory<K> messageParserFactory;
	/**
	 * 报文中转站。
	 */
	private MessageEntrepot messageEntrepot;

	/**
	 * 报文消息处理服务。
	 */
	private MessageProcessService<K> messageProcessService;

	/**
	 * 内部通讯事件监听器。
	 */
	private CommunicateListener<UChannel> _communicateListener = null;
	/**
	 * 内部报文中转站事件监听器。
	 */
	private MessageEntrepotListener _messageEntrepotListener = null;
	/**
	 * 捕获通讯异常并关闭物理通道。
	 */
	private boolean closeChannelWhileError = true;
	/**
	 * 当终端离线时关闭物理通道。
	 */
	private boolean closeChannelWhileOffline = true;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DefaultMessageService() {
		super();
		this.init();
	}

	private void init() {
		//建立通讯事件监听器
		this._communicateListener = new CommunicateAdapter<UChannel>() {

			/** 
			 * receiveData。
			 * @see org.quincy.rock.comm.communicate.CommunicateListener#receiveData(java.lang.Object, java.lang.Object)
			 */
			@Override
			public void receiveData(UChannel channel, Object data) {
				Map<String, Object> ctx = createContext(channel, data);
				//收到报文数据先解析报文头
				MessageHeadParser header = getMessageParserFactory().getMessageHeadParser();
				Object content = null;
				try {
					content = header.unpack(data, ctx);
				} catch (Exception e) {
					fireMessageHeadParserException(data, ctx, e);
					return; //报文头解析失败
				}
				//合法性检查
				Object msgId = ctx.get(CommUtils.COMM_MSG_ID_KEY);
				K code = (K) ctx.get(CommUtils.COMM_FUNCTION_CODE_KEY);
				Object terminalId = getTerminalChannelMapping().checkLegality(msgId, code, channel, ctx, content);
				if (terminalId == null) { //不合法
					//返回值数据编码
					content = ctx.get(CommUtils.COMM_MSG_DIRECT_RESPONE_KEY);
					if (content != null) {
						code = (K) ctx.get(CommUtils.COMM_FUNCTION_CODE_KEY);
						String type = (String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY);
						MessageParser parser = getMessageParserFactory().getMessageParser(code, MsgType.SEND, type);
						content = parser.pack(content, ctx);
						content = header.pack(content, ctx);
						getCommunicator().sendData(channel, content, true, null);
					}
					fireCheckLegalityException(msgId, code, content, ctx);
					return;
				}
				//通过合法性检查
				ctx.put(CommUtils.COMM_TERMINAL_ID_KEY, terminalId);
				//将报文数据添加到仓库
				getMessageEntrepot().addArrivedMessage(terminalId, msgId, code, content, ctx);
			}

			/** 
			 * disconnection。
			 * @see org.quincy.rock.comm.communicate.CommunicateListener#disconnection(java.lang.Object)
			 */
			@Override
			public void disconnection(UChannel channel) {
				channelClosed(channel, null);
			}

			/** 
			 * exceptionCaught。
			 * @see org.quincy.rock.comm.communicate.CommunicateListener#exceptionCaught(java.lang.Object, java.lang.Throwable)
			 */
			@Override
			public void exceptionCaught(UChannel channel, Throwable e) {
				channelClosed(channel, e); //仅仅关闭逻辑通道，不关闭物理通道
				if (closeChannelWhileError) {
					getCommunicator().close(channel); //关闭物理通道（所有逻辑通道也会级联关闭）
				}
			}

		};

		//初始化中转站事件监听器
		this._messageEntrepotListener = new MessageEntrepotListener() {

			@Override
			public void arrivedMessageAdded(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
					Object content, Map<String, Object> ctx) {

			}

			@Override
			public void arrivedMessageAddDone(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
					Object content, Map<String, Object> ctx) {
				//解码完整报文
				K code = (K) cmdCode;
				String type = (String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY);
				ctx.put(CommUtils.COMM_MSG_ORIGINAL_CONTENT, content);
				try {
					MessageParser<K, Object, Object> parser = getMessageParserFactory().getMessageParser(code,
							MsgType.RECEIVE, type);
					content = parser.unpack(content, ctx);
				} catch (Exception e) {
					fireMessageParserException(terminalId, msgId, code, content, ctx, e);
					return; //报文正文解析失败
				}
				UChannel channel = (UChannel) ctx.get(CommUtils.COMM_CHANNEL_KEY);
				Object termId = getTerminalChannelMapping().findMapping(channel);
				if (termId == null) {
					//第一次发报文，建立映射
					termId = getTerminalChannelMapping().createMapping(channel, ctx, content);
					if (termId == null) {
						//无法建立映射
						content = ctx.get(CommUtils.COMM_MSG_DIRECT_RESPONE_KEY);
						if (content != null) {
							//返回值数据编码
							code = (K) ctx.get(CommUtils.COMM_FUNCTION_CODE_KEY);
							type = (String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY);
							MessageHeadParser header = getMessageParserFactory().getMessageHeadParser();
							MessageParser parser = getMessageParserFactory().getMessageParser(code, MsgType.SEND, type);
							content = parser.pack(content, ctx);
							content = header.pack(content, ctx);
							getCommunicator().sendData(channel, content, true, null);
						}
						return; //握手失败
					}
					//终端上线
					fireTerminalOnlineEvent(termId);
				}
				if (!termId.equals(terminalId)) {
					String msg = MessageFormat.format("The terminals are inconsistent({1} but {})!", terminalId,
							termId);
					throw new CommunicateException(msg);
				}
				//触发指令到达事件
				ctx.remove(CommUtils.COMM_CHANNEL_KEY);
				messageArrived(channel, terminalId, msgId, code, content, ctx);
			}

			@Override
			public void toSentMessageAdded(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
					Object content, Map<String, Object> ctx) {
				//编码报文头
				MessageHeadParser header = getMessageParserFactory().getMessageHeadParser();
				Object data = null;
				try {
					data = header.pack(content, ctx);
				} catch (Exception e) {
					fireMessageHeadParserException(content, ctx, e);
					return;
				}
				//编码完毕，开始发送
				ctx.put(CommUtils.COMM_MSG_ORIGINAL_MESSAGE, data);
				Boolean async = (Boolean) ctx.get(CommUtils.COMM_MSG_ASYNC_SEND_KEY);
				Consumer<Boolean> consumer = (Consumer<Boolean>) ctx.get(CommUtils.COMM_ASYNC_CALLBACK_KEY);
				UChannel channel = (UChannel) ctx.get(CommUtils.COMM_CHANNEL_KEY);
				if (channel == null && terminalId != null)
					channel = getTerminalChannelMapping().getChannel(terminalId);
				getCommunicator().sendData(channel, data, async == null ? true : async.booleanValue(), consumer);
			}

			@Override
			public void toSentMessageAddDone(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
					Object content, Map<String, Object> ctx, boolean success) {
				content = ctx.get(CommUtils.COMM_MSG_ORIGINAL_CONTENT);
				fireMessageSendedEvent(terminalId, msgId, (K) cmdCode, content, ctx, success);
			}

		};

	}

	/**
	 * <b>连接通道关闭时调用。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 关闭的连接通道
	 * @param e 导致通道关闭的异常，可以为null
	 */
	protected void channelClosed(UChannel channel, Throwable e) {
		TerminalChannelMapping mapping = getTerminalChannelMapping();
		if (channel instanceof HasPattern && mapping instanceof PatternChannelMapping) {
			//支持模式
			Collection<Object> terminalIds = ((PatternChannelMapping) mapping).destroyMappings((HasPattern) channel);
			if (terminalIds != null && !terminalIds.isEmpty()) {
				for (Object terminalId : terminalIds) {
					getMessageEntrepot().removeTerminal(terminalId);
					fireTerminalOfflineEvent(terminalId);
				}
			}
		} else {
			//不支持模式
			Object terminalId = mapping.destroyMapping(channel);
			if (terminalId != null) {
				getMessageEntrepot().removeTerminal(terminalId);
				fireTerminalOfflineEvent(terminalId);
			}
		}
	}

	/** 
	 * offlineTerminal。
	 * @see org.quincy.rock.comm.MessageSender#offlineTerminal(java.lang.Object)
	 */
	@Override
	public void offlineTerminal(Object terminalId) {
		TerminalChannelMapping<UChannel> mapping = getTerminalChannelMapping();
		UChannel channel = mapping.getChannel(terminalId);
		if (isCloseChannelWhileOffline())
			this.getCommunicator().close(channel);
		else {
			this.channelClosed(channel, null);
		}
	}

	/**
	 * <b>消息到达时调用。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道,不能为空
	 * @param terminalId 终端唯一标识，没有时允许为null
	 * @param msgId 报文id
	 * @param functionCode 功能码
	 * @param content 报文正文数据
	 * @param ctx 报文上下文数据
	 */
	protected void messageArrived(UChannel channel, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx) {
		if (terminalId != null)
			fireMessageArrivedEvent(terminalId, msgId, functionCode, content, ctx);
	}

	/**
	 * <b>创建报文上下文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param data 原始报文数据
	 * @return 报文上下文
	 */
	protected Map<String, Object> createContext(UChannel channel, Object data) {
		Map<String, Object> ctx = new HashMap<>();
		ctx.put(CommUtils.COMM_CHANNEL_KEY, channel);
		ctx.put(CommUtils.COMM_MSG_ORIGINAL_MESSAGE, data);
		ctx.put(CommUtils.COMM_MSG_RECEIVE_FALG, Boolean.TRUE);
		return ctx;
	}

	/**
	 * <b>捕获通讯异常并关闭物理通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 捕获通讯异常并关闭物理通道
	 */
	public boolean isCloseChannelWhileError() {
		return closeChannelWhileError;
	}

	/**
	 * <b>捕获通讯异常并关闭物理通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param closeChannelWhileError 捕获通讯异常并关闭物理通道
	 */
	public void setCloseChannelWhileError(boolean closeChannelWhileError) {
		this.closeChannelWhileError = closeChannelWhileError;
	}

	/**
	 * <b>当终端离线时关闭物理通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当终端离线时关闭物理通道
	 */
	public boolean isCloseChannelWhileOffline() {
		return closeChannelWhileOffline;
	}

	/**
	 * <b>当终端离线时关闭物理通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param closeChannelWhileOffline 当终端离线时关闭物理通道
	 */
	public void setCloseChannelWhileOffline(boolean closeChannelWhileOffline) {
		this.closeChannelWhileOffline = closeChannelWhileOffline;
	}

	/**
	 * <b>设置通讯器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param communicator 通讯器
	 */
	public void setCommunicator(Communicator<UChannel> communicator) {
		if (this.communicator != null) {
			this.communicator.removeCommunicateListener(this._communicateListener);
		}
		this.communicator = communicator;
		if (this.communicator != null) {
			this.communicator.addCommunicateListener(this._communicateListener);
		}
	}

	/**
	 * <b>获得通讯器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯器
	 */
	public Communicator<UChannel> getCommunicator() {
		return communicator;
	}

	/**
	 * <b>设置报文解析器工厂。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageParserFactory 报文解析器工厂
	 */
	public void setMessageParserFactory(MessageParserFactory<K> messageParserFactory) {
		this.messageParserFactory = messageParserFactory;
	}

	/**
	 * <b>获得报文解析器工厂。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文解析器工厂
	 */
	public MessageParserFactory<K> getMessageParserFactory() {
		return messageParserFactory;
	}

	/**
	 * <b>获得报文仓库。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文仓库
	 */
	public MessageEntrepot getMessageEntrepot() {
		return messageEntrepot;
	}

	/**
	 * <b>设置报文仓库。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageEntrepot 报文仓库
	 */
	public void setMessageEntrepot(MessageEntrepot messageEntrepot) {
		if (this.messageEntrepot != null) {
			this.messageEntrepot.removeMessageEntrepotListener(this._messageEntrepotListener);
		}
		this.messageEntrepot = messageEntrepot;
		if (this.messageEntrepot != null) {
			this.messageEntrepot.addMessageEntrepotListener(this._messageEntrepotListener);
		}
	}

	/**
	 * 返回报文消息处理服务
	 * @return 报文消息处理服务
	 */
	public MessageProcessService<K> getMessageProcessService() {
		return messageProcessService;
	}

	/**
	 * 设置报文消息处理服务
	 * @param messageProcessService 报文消息处理服务
	 */
	public void setMessageProcessService(MessageProcessService<K> messageProcessService) {
		if (this.messageProcessService != null)
			this.messageProcessService.unbind(this);
		this.messageProcessService = messageProcessService;
		if (this.messageProcessService != null)
			this.messageProcessService.bind(this);
	}

	/**
	 * <b>设置报文处理器工厂。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param processorFactory 报文处理器工厂
	 */
	public void setMessageProcessorFactory(MessageProcessorFactory<K> processorFactory) {
		this.messageProcessService.processorFactory(processorFactory);
	}

	/**
	 * <b>获得设置报文处理器工厂。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 设置报文处理器工厂
	 */
	public MessageProcessorFactory<K> getMessageProcessorFactory() {
		return this.messageProcessService.processorFactory();
	}

	/** 
	 * sendMessage0。
	 * @see org.quincy.rock.comm.AbstractMessageService#sendMessage0(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, boolean, java.util.function.Consumer)
	 */
	protected void sendMessage0(UChannel channel, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> attachment, boolean async, Consumer<Boolean> consumer) {
		Map<String, Object> ctx = attachment == null ? new HashMap() : new HashMap(attachment);
		ctx.put(CommUtils.COMM_CHANNEL_KEY, channel);
		ctx.put(CommUtils.COMM_TERMINAL_ID_KEY, terminalId);
		ctx.put(CommUtils.COMM_MSG_ID_KEY, msgId);
		ctx.put(CommUtils.COMM_FUNCTION_CODE_KEY, functionCode);
		ctx.put(CommUtils.COMM_MSG_ASYNC_SEND_KEY, async);
		ctx.put(CommUtils.COMM_MSG_ORIGINAL_CONTENT, content);
		if (consumer != null)
			ctx.put(CommUtils.COMM_ASYNC_CALLBACK_KEY, consumer);
		String type = (String) ctx.get(CommUtils.COMM_MSG_TYPE_KEY);
		if (type == null) {
			type = getMessageParserFactory().getMessageHeadParser().getDefaultContentType();
			ctx.put(CommUtils.COMM_MSG_TYPE_KEY, type);
		}
		if (!ctx.containsKey(CommUtils.COMM_MSG_TIMESTAMP_KEY))
			ctx.put(CommUtils.COMM_MSG_TIMESTAMP_KEY, System.currentTimeMillis());

		//开始对正文进行编码
		Object data = null;
		try {
			MessageParser<K, Object, Object> parser = getMessageParserFactory().getMessageParser(functionCode,
					MsgType.SEND, type);
			data = parser.pack(content, ctx);
		} catch (Exception e) {
			fireMessageParserException(terminalId, msgId, functionCode, content, ctx, e);
			return;
		}
		//正文编码完毕，加到发送中转站
		this.getMessageEntrepot().addToSentMessage(terminalId, msgId, functionCode, data, ctx);
	}
}
