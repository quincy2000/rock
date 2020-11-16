package org.quincy.rock.comm.netty.mqtt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.netty.ChannelHandlerCreator;
import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.comm.netty.codec.AbstractCRCCodecCreatorHandler;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.CloneMe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttMessageBuilders.ConnectBuilder;
import io.netty.handler.codec.mqtt.MqttMessageBuilders.UnsubscribeBuilder;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

/**
 * <b>MqttChannelHandler。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月30日 下午3:09:49</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked" })
public class MqttChannelHandler extends AbstractCRCCodecCreatorHandler
		implements ChannelHandlerCreator, Cloneable, CloneMe {
	/**
	 * 错误或非法的topic。
	 */
	private static final String ERROR_TOPIC = "Topic is illegal:{0}!";

	/**
	 * MAX_MESSAGE_ID。
	 */
	private static final int MAX_MESSAGE_ID = 65530;

	/**
	 * MQTT_HEADER_PINGREQ。
	 */
	private static final MqttFixedHeader MQTT_HEADER_PINGREQ = new MqttFixedHeader(MqttMessageType.PINGREQ, false,
			MqttQoS.AT_MOST_ONCE, false, 0);
	/**
	 * MQTT_HEADER_PUBACK。
	 */
	private static final MqttFixedHeader MQTT_HEADER_PUBACK = new MqttFixedHeader(MqttMessageType.PUBACK, false,
			MqttQoS.AT_MOST_ONCE, false, 2);
	/**
	 * mqtt缺省ping间隔。
	 */
	private static final int DEFAULT_PINGREQ_INTERVAL = 60;

	/**
	 * 缺省的MqttQoS。
	 */
	public static final MqttQoS DEFAULT_QOS = MqttQoS.AT_LEAST_ONCE;

	/**
	 * 发送的消息MqttQoS。
	 */
	public static final AttributeKey<MqttQoS> SENDED_MESSAGE_MQTT_QOS_KEY = AttributeKey
			.valueOf("sendedMessageMqttQoS");
	/**
	 * 发送的消息是否阅后即焚。
	 */
	public static final AttributeKey<Boolean> SENDED_MESSAGE_BURN_KEY = AttributeKey.valueOf("sendedMessageBurn");

	/**
	 * 接收到的消息TopicKey。
	 */
	public static final AttributeKey<String> RECEIVED_MESSAGE_TOPIC_KEY = AttributeKey.valueOf("receivedMessageTopic");
	/**
	 * 发送的消息TopicKey。
	 */
	public static final AttributeKey<String> SENDED_MESSAGE_TOPIC_KEY = AttributeKey.valueOf("sendedMessageTopic");

	/**
	 * mqtt客户端唯一标识。
	 */
	private String clientid;
	/**
	 * mqtt用户名。
	 */
	private String userName;
	/**
	 * 密码。
	 */
	private String password;
	/**
	 * 心跳间隔时间。
	 */
	private int heartbeat;

	/**
	 * 遗嘱字节码。
	 */
	private byte[] willBytes;

	/**
	 * 遗嘱Topic。
	 */
	private String willTopic;
	/**
	 * 遗嘱MqttQoS。
	 */
	private MqttQoS willQos;

	/**
	 *  初始订阅的Topic列表。
	 */
	private List<SubscribeTopic> InitialSubscribeTopics;

	/**
	 * 默认的发送报文时的MqttQoS。
	 */
	private MqttQoS defaultQosForSend;

	/**
	 * 报文消息最大字节数。
	 */
	private int maxBytesInMessage = 8092;

	/**
	 * <b>获得mqtt客户端唯一标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt客户端唯一标识
	 */
	public String getClientid() {
		return clientid;
	}

	/**
	 * <b>设置mqtt客户端唯一标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clientid mqtt客户端唯一标识
	 */
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	/**
	 * <b>获得mqtt用户名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * <b>设置userName。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userName userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * <b>获得密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <b>设置密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <b>获得心跳间隔时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 心跳间隔时间
	 */
	public int getHeartbeat() {
		return heartbeat;
	}

	/**
	 * <b>设置心跳间隔时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param heartbeat 心跳间隔时间
	 */
	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat < 0 ? 0 : heartbeat;
	}

	/**
	 * <b>获得遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 遗嘱
	 */
	public byte[] getWillBytes() {
		return willBytes;
	}

	/**
	 * <b>设置遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param willBytes 遗嘱
	 */
	public void setWillBytes(byte[] willBytes) {
		this.willBytes = willBytes;
	}

	/**
	 * <b>设置遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param will 遗嘱
	 */
	public void setWillString(String will) {
		if (will == null)
			this.willBytes = null;
		else if (CoreUtil.isHex(will))
			this.willBytes = CoreUtil.hexString2ByteArray(will.substring(2));
		else
			this.willBytes = will.getBytes(CharsetUtil.UTF_8);
	}

	/**
	 * <b>获得遗嘱Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 遗嘱Topic
	 */
	public String getWillTopic() {
		return willTopic;
	}

	/**
	 * <b>设置遗嘱Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param willTopic 遗嘱Topic
	 */
	public void setWillTopic(String willTopic) {
		this.willTopic = willTopic;
	}

	/**
	 * <b>获得遗嘱MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 遗嘱MqttQoS
	 */
	public MqttQoS getWillQos() {
		return willQos == null ? DEFAULT_QOS : willQos;
	}

	/**
	 * <b>设置遗嘱MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param willQos 遗嘱MqttQoS
	 */
	public void setWillQos(MqttQoS willQos) {
		this.willQos = willQos;
	}

	/**
	 * <b>获得初始订阅的Topic列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 初始订阅的Topic列表
	 */
	public List<SubscribeTopic> getInitialSubscribeTopics() {
		return InitialSubscribeTopics == null ? (InitialSubscribeTopics = new ArrayList<>()) : InitialSubscribeTopics;
	}

	/**
	 * <b>设置初始订阅的Topic列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param subscribeTopics 初始订阅的Topic列表
	 */
	public void setInitialSubscribeTopics(List<SubscribeTopic> subscribeTopics) {
		this.InitialSubscribeTopics = subscribeTopics;
	}

	/**
	 * <b>添加初始订阅的Topic 。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param subscribeTopic 初始订阅的Topic
	 */
	public void addInitialSubscribeTopic(SubscribeTopic subscribeTopic) {
		this.getInitialSubscribeTopics().add(subscribeTopic);
	}

	/**
	 * <b>默认的发送报文时的MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 默认的发送报文时的MqttQoS
	 */
	public MqttQoS getDefaultQosForSend() {
		return defaultQosForSend == null ? DEFAULT_QOS : defaultQosForSend;
	}

	/**
	 * <b>默认的发送报文时的MqttQoS。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultQosForSend 默认的发送报文时的MqttQoS
	 */
	public void setDefaultQosForSend(MqttQoS defaultQosForSend) {
		this.defaultQosForSend = defaultQosForSend;
	}

	/**
	 * <b>获得报文消息最大字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文消息最大字节数
	 */
	public int getMaxBytesInMessage() {
		return maxBytesInMessage;
	}

	/**
	 * <b>设置报文消息最大字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxBytesInMessage 报文消息最大字节数
	 */
	public void setMaxBytesInMessage(int maxBytesInMessage) {
		this.maxBytesInMessage = maxBytesInMessage;
	}

	/**
	 * <b>是否设置了遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否设置了遗嘱
	 */
	public boolean hasWill() {
		return willBytes != null && willBytes.length > 0 && !StringUtil.isBlank(willTopic);
	}

	/**
	 * ctx4ChannelMap。
	 */
	private final Map<ChannelId, ChannelHandlerContext> ctx4ChannelMap = new ConcurrentHashMap<>();

	/** 
	 * channelActive。
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		try {
			ConnectBuilder builder = MqttMessageBuilders.connect().clientId(clientid).cleanSession(true)
					.username(userName).password(password.getBytes(CharsetUtil.UTF_8)).keepAlive(heartbeat);
			if (hasWill()) {
				builder = builder.willQoS(getWillQos()).willMessage(willBytes).willTopic(willTopic).willRetain(true)
						.willFlag(true);
			}
			ctx.writeAndFlush(builder.build());
			ctx.fireChannelActive();
		} catch (Exception e) {
			ctx4ChannelMap.remove(ctx.channel().id());
		}
	}

	/** 
	 * channelInactive。
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx4ChannelMap.remove(ctx.channel().id());
		ctx.fireChannelInactive();
	}

	/** 
	 * channelRead。
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MqttMessage mqttMessage = (MqttMessage) msg;
		DecoderResult result = mqttMessage.decoderResult();
		if (result.isFailure()) {
			String errMsg = "Receive an error message:" + mqttMessage.toString();
			recorder.write(result.cause(), errMsg);
			NettyUtil.releaseRC(mqttMessage);
			//解析报文错误
			throw new CommunicateException(errMsg, result.cause());
		}
		//MQTT消息解析成功
		MqttMessageType type = mqttMessage.fixedHeader().messageType();
		switch (type) {
		case CONNACK:
			//第一次连接应答
			ctx4ChannelMap.put(ctx.channel().id(), ctx);
			//订阅topic
			for (SubscribeTopic topic : this.getInitialSubscribeTopics())
				this.subscribeTopic(ctx.channel().id(), topic);
			NettyUtil.releaseRC(mqttMessage);
			break;
		case PUBLISH:
			MqttPublishVariableHeader pubHeader = (MqttPublishVariableHeader) mqttMessage.variableHeader();
			String topic = pubHeader.topicName();
			int messageId = pubHeader.packetId();
			Attribute<String> topicKey = ctx.channel().attr(RECEIVED_MESSAGE_TOPIC_KEY);
			topicKey.set(topic);
			//消息收到
			ctx.writeAndFlush(new MqttPubAckMessage(MQTT_HEADER_PUBACK, MqttMessageIdVariableHeader.from(messageId)));
			recorder.write("Messages are received({0}):{1}.", String.valueOf(messageId), topic);
			//继续传递
			ctx.fireChannelRead(mqttMessage.payload());
			break;
		case SUBACK:
			//订阅成功	
			recorder.write("Successfully subscribed topics.");
			NettyUtil.releaseRC(mqttMessage);
			break;
		case UNSUBACK:
			//反订阅成功	
			recorder.write("Successfully unsubscribed topics.");
			NettyUtil.releaseRC(mqttMessage);
			break;
		case PUBACK:
			recorder.write("Send data successfully");
			NettyUtil.releaseRC(mqttMessage);
			break;
		case PINGRESP:
			recorder.write("Received the heartbeat return value!");
			NettyUtil.releaseRC(mqttMessage);
			break;
		default:
			Exception ex = new UnsupportedMessageTypeException(type);
			recorder.write(ex, "Unsupported message type:{0}.", type.toString());
			NettyUtil.releaseRC(mqttMessage);
			throw ex;
		}
	}

	/** 
	 * write。
	 * @see io.netty.channel.ChannelDuplexHandler#write(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		String topic = ctx.channel().attr(SENDED_MESSAGE_TOPIC_KEY).getAndSet(null);
		MqttQoS qos = ctx.channel().attr(SENDED_MESSAGE_MQTT_QOS_KEY).getAndSet(null);
		Boolean burn = ctx.channel().attr(SENDED_MESSAGE_BURN_KEY).getAndSet(null);
		if (StringUtils.isBlank(topic) || StringUtils.containsAny(topic, '+', '#')) {
			NettyUtil.releaseRC(msg);
			String err = MessageFormat.format(ERROR_TOPIC, topic);
			recorder.write(err);
			throw new CommunicateException(err);
		}
		//
		MqttPublishMessage message = MqttMessageBuilders.publish().qos(qos == null ? getDefaultQosForSend() : qos)
				.topicName(topic).retained(!Boolean.TRUE.equals(burn))
				.messageId(CommUtils.uniqueMessageIdAsInt() % MAX_MESSAGE_ID).payload((ByteBuf) msg).build();
		if (!NettyUtil.releaseRC(msg))
			recorder.write(NettyUtil.REF_CNT_0);
		//发送消息
		recorder.write("Send message:{0}.", topic);
		//
		ctx.write(message, promise);
	}

	/** 
	 * userEventTriggered。
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch (event.state()) {
			case READER_IDLE:
				//长时间未收到心跳返回值	
				ctx.channel().close().sync();
				recorder.write("Lost connection to the MQTT service,Heart stopped.");
				break;
			case WRITER_IDLE:
				ctx.writeAndFlush(new MqttMessage(MQTT_HEADER_PINGREQ));
				recorder.write("Send heartbeat successfully");
				break;
			case ALL_IDLE:
			default:
				Exception ex = new UnsupportException("IdleState:" + event.state().name());
				recorder.write(ex, ex.getMessage());
				throw ex;
			}
		} else {
			ctx.fireUserEventTriggered(evt);
		}
	}

	/** 
	 * clone。
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		MqttChannelHandler ch = (MqttChannelHandler) super.clone();
		return ch;
	}

	/** 
	 * cloneMe。
	 * @see org.quincy.rock.core.vo.CloneMe#cloneMe()
	 */
	@Override
	public final <M> M cloneMe() {
		try {
			return (M) clone();
		} catch (Exception e) {
			return null;
		}
	}

	//
	private ChannelHandlerContext getChannelHandlerContext(ChannelId id) {
		ChannelHandlerContext ctx = ctx4ChannelMap.get(id);
		int i = 10 * 2; //最多延迟10秒
		while (ctx == null && i-- > 0) {
			//睡眠0.5秒
			DateUtil.sleep(500);
			ctx = ctx4ChannelMap.get(id);
		}
		if (ctx == null)
			throw new NotFoundException("No valid channel handler context was found!");
		return ctx;
	}

	/**
	 * <b>实时订阅。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 通道id
	 * @param topics 一个或多个topic
	 */
	public void subscribeTopic(ChannelId id, SubscribeTopic... topics) {
		ChannelHandlerContext ctx = getChannelHandlerContext(id);
		MqttMessageBuilders.SubscribeBuilder builder = MqttMessageBuilders.subscribe()
				.messageId(CommUtils.uniqueMessageIdAsInt() % MAX_MESSAGE_ID);
		for (SubscribeTopic topic : topics) {
			builder.addSubscription(topic.getMqttQoS(), topic.getTopic());
		}
		MqttSubscribeMessage subMsg = builder.build();
		ctx.writeAndFlush(subMsg);
		if (recorder.canWrite()) {
			for (SubscribeTopic topic : topics) {
				recorder.write("Subscribe to the topic({0}):{1}.", topic.getMqttQoS(), topic.getTopic());
			}
		}
	}

	/**
	 * <b>实时取消订阅。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 通道id
	 * @param topics 一个或多个topic
	 */
	public void unsubscribeTopic(ChannelId id, String... topics) {
		ChannelHandlerContext ctx = getChannelHandlerContext(id);
		UnsubscribeBuilder builder = MqttMessageBuilders.unsubscribe()
				.messageId(CommUtils.uniqueMessageIdAsInt() % MAX_MESSAGE_ID);
		for (String topic : topics) {
			builder.addTopicFilter(topic);
		}
		MqttUnsubscribeMessage subMsg = builder.build();
		ctx.writeAndFlush(subMsg);
		if (recorder.canWrite()) {
			for (String topic : topics) {
				recorder.write("unsubscribe from the topic:{0}.", topic);
			}
		}
	}

	/** 
	 * isSharable。
	 * @see io.netty.channel.ChannelHandlerAdapter#isSharable()
	 */
	@Override
	public boolean isSharable() {
		return true;
	}

	/** 
	 * createChannelHandlers。
	 * @see org.quincy.rock.comm.netty.ChannelHandlerCreator#createChannelHandlers()
	 */
	@Override
	public Iterable<ChannelHandler> createChannelHandlers() {
		List<ChannelHandler> list = new ArrayList<ChannelHandler>();
		ChannelHandler first = this.getFirstChannelHandler();
		ChannelHandler send = this.getSendInterceptor();
		ChannelHandler receive = this.getReceiveInterceptor();
		//首个ChannelHandler拦截器
		if (first != null)
			list.add(first);
		list.add(createMqttDecoder());
		list.add(createMqttEncoder());
		list.add(createHeartbeatHandler());
		list.add(this);
		//拦截
		if (receive != null)
			list.add(receive);
		if (send != null)
			list.add(send);
		if (checkCRC()) {
			list.add(createCrcEncoder());
			list.add(createCrcDecoder());
		}
		return list;
	}

	/**
	 * <b>获得mqttDecoder。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqttDecoder
	 */
	protected ChannelHandler createMqttDecoder() {
		return new MqttDecoder(getMaxBytesInMessage());
	}

	/**
	 * <b>获得mqttEncoder。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqttEncoder
	 */
	protected ChannelHandler createMqttEncoder() {
		return MqttEncoder.INSTANCE;
	}

	/**
	 * <b>创建心跳句柄。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 心跳句柄
	 */
	protected ChannelHandler createHeartbeatHandler() {
		return idleStateHandler(heartbeat > 0 ? heartbeat : DEFAULT_PINGREQ_INTERVAL);
	}

	//空闲心跳句柄
	private IdleStateHandler idleStateHandler(int heartbeat) {
		return new IdleStateHandler(heartbeat * 2 + 1, heartbeat, 0);
	}
}
