package org.quincy.rock.comm.mqtt;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.ScheduledExecutorPingSender;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.communicate.AbstractCommunicator;
import org.quincy.rock.comm.communicate.Adviser;
import org.quincy.rock.comm.communicate.CommunicateServerListener;
import org.quincy.rock.comm.util.CommunicateServerListenerSupport;
import org.quincy.rock.comm.util.NioUtils;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.function.Consumer;
import org.quincy.rock.core.function.Function;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>封装了paho框架的MQTT通讯器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年1月27日 下午5:54:47</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractMqttCommunicator<UChannel extends IMqttChannel> extends AbstractCommunicator<UChannel>
		implements IMqttCommunicator<UChannel> {
	/**
	 * mqttClient。
	 */
	private final IMqttAsyncClient mqttClient;
	/**
	 * 任务线程执行器。
	 */
	private final ScheduledExecutorService executorService;
	/**
	 * svrListenerSupport。
	 */
	private final CommunicateServerListenerSupport<UChannel> svrListenerSupport = new CommunicateServerListenerSupport<>();
	/**
	 * MQTT连接选项。
	 */
	private MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
	/**
	 * mqtt根通道。
	 */
	private UChannel rootChannel;
	/**
	 * 初始订阅的Topic列表。
	 */
	private List<String> initialSubscribeTopics;
	/**
	 * 支持的最大服务质量。
	 */
	private int maxQos = 2;

	/**
	 * 数据切片器。
	 */
	private Function<Object, Object> dataSlicer = new Function<Object, Object>() {

		@Override
		public Object call(Object t) {
			return NioUtils.slice(t);
		}
	};

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverURI 要连接的服务器地址，用URI指定。可以覆盖通过使用MqttConnectOptions.setServerURIs(String[] serverURIs)。
	 * @param clientId 唯一的客户端标识符
	 */
	public AbstractMqttCommunicator(String serverURI, String clientId) {
		this(serverURI, clientId, 5, new MemoryPersistence());
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverURI 要连接的服务器地址，用URI指定。可以覆盖通过使用MqttConnectOptions.setServerURIs(String[] serverURIs)。
	 * @param clientId 唯一的客户端标识符
	 * @param corePoolSize 核心线程池大小
	 */
	public AbstractMqttCommunicator(String serverURI, String clientId, int corePoolSize) {
		this(serverURI, clientId, corePoolSize, new MemoryPersistence());
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverURI 要连接的服务器地址，用URI指定。可以覆盖通过使用MqttConnectOptions.setServerURIs(String[] serverURIs)。
	 * @param clientId 唯一的客户端标识符
	 * @param corePoolSize 核心线程池大小
	 * @param persistence 用于存储动态消息的持久化类
	 */
	public AbstractMqttCommunicator(String serverURI, String clientId, int corePoolSize,
			MqttClientPersistence persistence) {
		this.executorService = new ScheduledThreadPoolExecutor(corePoolSize);
		try {
			this.mqttClient = new MqttAsyncClient(serverURI, clientId, persistence,
					new ScheduledExecutorPingSender(executorService), executorService);
			this.mqttClient.setCallback(new MqttCallbackExtended() {

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					if (isDuplicateMessage(topic, message)) {
						recorder.write("Duplicate data received:{0},discard!", topic);
					} else {
						recorder.write("Received data:{0}.", topic);
						UChannel ch = cloneMqttChannel();
						ch.fromTopic(topic);
						ch.setMqttQos(message.getQos());
						ch.setRetained(message.isRetained());
						Object data = payloadToData(ch, message.getPayload());
						fireReceiveDataEvent(ch, data, dataSlicer);
					}
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					//发送完成
					Object ctx = token.getUserContext(); //发送时传递过来的通道和发送数据		
					Pair<UChannel, Object> pair = (ctx instanceof Pair) ? (Pair) ctx : null;
					UChannel ch = pair == null ? (UChannel) cloneMqttChannel().fromTopic(token.getTopics()[0])
							: pair.getLeft();
					Object data = pair == null ? token.getUserContext() : pair.getRight();
					fireSendDataEvent(ch, data, token.getException() == null, dataSlicer);
				}

				@Override
				public void connectComplete(boolean reconnect, String serverURI) {
					if (recorder.canWrite()) {
						recorder.write(reconnect ? "Reconnect " : "Connecting " + serverURI + " successfully.");
					}
					//订阅初始topic
					executorService.submit(new Runnable() {

						@Override
						public void run() {
							try {
								initialSubscribeTopics();
							} catch (Exception e) {
								recorder.write(e, "initialSubscribeTopics:" + e.getMessage());
							}
						}
					});
					useServerURI(serverURI);
					fireConnectionEvent(cloneMqttChannel());
					svrListenerSupport.fireServerStartedEvent(AbstractMqttCommunicator.this);
				}

				@Override
				public void connectionLost(Throwable cause) {
					if (recorder.canWrite()) {
						recorder.write(cause, "The connection has been broken.");
					}
					fireDisconnectionEvent(cloneMqttChannel());
					svrListenerSupport.fireServerStoppedEvent(AbstractMqttCommunicator.this);
				}

			});
		} catch (MqttException e) {
			throw new CommunicateException(e.getMessage(), e);
		}
	}

	/**
	 * <b>获得MQTT连接选项。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return MQTT连接选项
	 */
	public final MqttConnectOptions getMqttConnectOptions() {
		return mqttConnectOptions;
	}

	/**
	 * <b>设置MQTT连接选项。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttConnectOptions MQTT连接选项
	 */
	public final void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
		this.mqttConnectOptions = mqttConnectOptions;
	}

	/** 
	 * getRootChannel。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#getRootChannel()
	 */
	@Override
	public final UChannel getRootChannel() {
		if (rootChannel == null)
			throw new CommunicateException("MqttChannel not set!");
		if (rootChannel.mqttClient() == null)
			rootChannel.mqttClient(mqttClient);
		return rootChannel;
	}

	/**
	 * <b>设置mqtt根通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param rootChannel mqtt根通道
	 */
	public final void setRootChannel(UChannel rootChannel) {
		this.rootChannel = rootChannel;
	}

	/**
	 * <b>获得初始订阅的Topic列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 初始订阅的Topic列表
	 */
	public final List<String> getInitialSubscribeTopics() {
		if (this.initialSubscribeTopics == null) {
			this.initialSubscribeTopics = new ArrayList<>();
		}
		return this.initialSubscribeTopics;
	}

	/**
	 * <b>设置初始订阅的Topic列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param subscribeTopics 初始订阅的Topic列表
	 */
	public final void setInitialSubscribeTopics(List<String> topics) {
		this.initialSubscribeTopics = topics;
	}

	/**
	 * <b>获得支持的最大服务质量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 支持的最大服务质量
	 */
	public final int getMaxQos() {
		return maxQos;
	}

	/**
	 * <b>设置支持的最大服务质量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxQos 支持的最大服务质量
	 */
	public final void setMaxQos(int maxQos) {
		this.maxQos = maxQos;
	}

	/**
	 * <b>获得数据切片器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 数据切片器
	 */
	public final Function<Object, Object> getDataSlicer() {
		return dataSlicer;
	}

	/**
	 * <b>设置数据切片器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dataSlicer 数据切片器
	 */
	public final void setDataSlicer(Function<Object, Object> dataSlicer) {
		this.dataSlicer = dataSlicer;
	}

	/** 
	 * getMqttClientId。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#getMqttClientId()
	 */
	@Override
	public final String getMqttClientId() {
		return mqttClient.getClientId();
	}

	/** 
	 * newSendChannel。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#newSendChannel()
	 */
	@Override
	public final UChannel newSendChannel() {
		return getRootChannel().newSendChannel();
	}

	/** 
	 * newSendChannel。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#newSendChannel(org.quincy.rock.comm.communicate.Adviser)
	 */
	@Override
	public UChannel newSendChannel(Adviser adviser) {
		return getRootChannel().newSendChannel(adviser);
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicate.CommunicateClient#connect()
	 */
	@Override
	public UChannel connect() {
		return this.connect(this.mqttConnectOptions);
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicate.CommunicateClient#connect(java.lang.String)
	 */
	@Override
	public UChannel connect(String host) {
		return this.connect(host, 1883);
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicate.CommunicateClient#connect(java.lang.String, int)
	 */
	@Override
	public UChannel connect(String host, int port) {
		this.mqttConnectOptions.setServerURIs(new String[] { host + StringUtil.CHAR_COLON + port });
		return this.connect(this.mqttConnectOptions);
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#connect(org.eclipse.paho.client.mqttv3.MqttConnectOptions)
	 */
	@Override
	public UChannel connect(MqttConnectOptions options) {
		if (options == null)
			options = this.mqttConnectOptions;
		else
			this.mqttConnectOptions = options;
		//
		try {
			IMqttToken token = mqttClient.connect(options);
			token.waitForCompletion(options.getConnectionTimeout());
			return this.cloneMqttChannel();
		} catch (MqttException e) {
			throw new CommunicateException(e.getMessage(), e);
		}
	}

	/** 
	 * subscribe。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#subscribe(java.lang.String[])
	 */
	@Override
	public IMqttToken subscribe(String... topics) throws MqttException {
		int l = topics.length;
		int[] qos = new int[l];
		for (int i = 0; i < l; i++) {
			qos[i] = maxQos;
		}
		IMqttToken token = mqttClient.subscribe(topics, qos, qos, new IMqttActionListener() {

			@Override
			public void onSuccess(IMqttToken token) {
				if (recorder.canWrite()) {
					int[] gQos = token.getGrantedQos();
					int[] qos = (int[]) token.getUserContext();
					String[] topics = token.getTopics();
					int l = gQos.length;
					String[] msgs = new String[l];
					for (int i = 0; i < l; i++) {
						msgs[i] = topics[i] + ":Qos=" + gQos[i] + "(" + qos[i] + ")";
					}
					recorder.write("Subscribe topic successfully:" + Arrays.toString(msgs));
				}
			}

			@Override
			public void onFailure(IMqttToken token, Throwable e) {
				if (recorder.canWrite()) {
					recorder.write(e, "Subscribe topic failed:" + Arrays.toString(token.getTopics()));
				}
			}
		});
		return token;
	}

	/** 
	 * unsubscribe。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#unsubscribe(java.lang.String[])
	 */
	@Override
	public IMqttToken unsubscribe(String... topics) throws MqttException {
		IMqttToken token = mqttClient.unsubscribe(topics, null, new IMqttActionListener() {

			@Override
			public void onSuccess(IMqttToken token) {
				if (recorder.canWrite()) {
					recorder.write("Unsubscribe topic successfully:" + Arrays.toString(token.getTopics()));
				}
			}

			@Override
			public void onFailure(IMqttToken token, Throwable e) {
				if (recorder.canWrite()) {
					recorder.write(e, "Unsubscribe topic failed:" + Arrays.toString(token.getTopics()));
				}
			}
		});
		return token;
	}

	/** 
	 * sendData。
	 * @see org.quincy.rock.comm.communicate.Communicator#sendData(java.lang.Object, java.lang.Object, boolean, org.quincy.rock.core.function.Consumer)
	 */
	@Override
	public void sendData(UChannel channel, Object data, boolean async, final Consumer<Boolean> consumer) {
		try {
			IMqttToken token = this.sendData(channel, data, new IMqttActionListener() {
				private Consumer<Boolean> callback = consumer;

				@Override
				public void onSuccess(IMqttToken token) {
					if (recorder.canWrite()) {
						recorder.write("Data sent successfully:" + token.getTopics()[0]);
					}
					if (callback != null) {
						callback.call(true);
					}
				}

				@Override
				public void onFailure(IMqttToken token, Throwable ex) {
					if (recorder.canWrite()) {
						recorder.write(ex, "Failed to send data:" + token.getTopics()[0]);
					}
					if (callback != null) {
						callback.call(false);
					}
				}
			});
			if (!async) {
				//同步
				token.waitForCompletion(mqttConnectOptions.getConnectionTimeout());
			}
		} catch (Exception e) {
			throw new CommunicateException("Failed to send data:" + e.getMessage(), e);
		}
	}

	/** 
	 * sendData。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#sendData(org.quincy.rock.comm.mqtt.IMqttChannel, java.lang.Object, org.eclipse.paho.client.mqttv3.IMqttActionListener)
	 */
	@Override
	public IMqttToken sendData(UChannel channel, Object data, IMqttActionListener callback) throws MqttException {
		checkSendChannel(channel);
		byte[] payload = this.dataToPayload(channel, dataSlicer.call(data));
		IMqttDeliveryToken token = mqttClient.publish(channel.toTopic(), payload, channel.getMqttQos(),
				channel.isRetained(), ImmutablePair.of(channel, data), callback);
		return token;
	}

	/**
	 * <b>将用户发送的数据类型转换成字节数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用户可以覆盖此方法定制自己的发送数据类型。
	 * @param channel 通道引用
	 * @param data 用户发送的数据
	 * @return 字节数组
	 */
	protected byte[] dataToPayload(UChannel channel, Object data) {
		if (data instanceof MqttMessage) {
			MqttMessage msg = (MqttMessage) data;
			data = msg.getPayload();
			channel.setMqttQos(msg.getQos());
			channel.setRetained(msg.isRetained());
		}
		try {
			return (byte[]) data;
		} catch (Exception e) {
			throw new UnsupportException("Unsupported packet type!", e);
		}
	}

	/**
	 * <b>将字节数组转换成用户接收的数据类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用户可以覆盖此方法定制自己的接收数据类型。
	 * @param channel 通道引用
	 * @param payload 字节数组
	 * @return 用户接收的数据类型
	 */
	protected Object payloadToData(UChannel channel, byte[] payload) {
		return payload;
	}

	/**
	 * <b>是否是重复消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * Qos=1时会产生重复消息。
	 * 默认情况下不对重复消息进行检查，子类可覆盖该方法去重。
	 * @param topic 主题
	 * @param message MqttMessage
	 * @return 是否是重复消息
	 */
	protected boolean isDuplicateMessage(String topic, MqttMessage message) {
		return false;
	}

	/** 
	 * close。
	 * @see org.quincy.rock.comm.communicate.Communicator#close(java.lang.Object)
	 */
	@Override
	public void close(UChannel channel) {
		checkMqttChannel(channel);
		this.close();
	}

	/** 
	 * isActive。
	 * @see org.quincy.rock.comm.communicate.Communicator#isActive(java.lang.Object)
	 */
	@Override
	public boolean isActive(UChannel channel) {
		checkMqttChannel(channel);
		return this.isActive();
	}

	/** 
	 * getMaxActive。
	 * @see org.quincy.rock.comm.communicate.Communicator#getMaxActive()
	 */
	@Override
	public int getMaxActive() {
		return 1;
	}

	/** 
	 * getActiveCount。
	 * @see org.quincy.rock.comm.communicate.Communicator#getActiveCount()
	 */
	@Override
	public int getActiveCount() {
		return mqttClient.isConnected() ? 1 : 0;
	}

	/** 
	 * addCommunicateServerListener。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#addCommunicateServerListener(org.quincy.rock.comm.communicate.CommunicateServerListener)
	 */
	@Override
	public void addCommunicateServerListener(CommunicateServerListener<UChannel> listener) {
		svrListenerSupport.addCommunicateServerListener(listener);
	}

	/** 
	 * removeCommunicateServerListener。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#removeCommunicateServerListener(org.quincy.rock.comm.communicate.CommunicateServerListener)
	 */
	@Override
	public void removeCommunicateServerListener(CommunicateServerListener<UChannel> listener) {
		svrListenerSupport.removeCommunicateServerListener(listener);
	}

	/** 
	 * removeAllCommunicateServerListener。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#removeAllCommunicateServerListener()
	 */
	@Override
	public void removeAllCommunicateServerListener() {
		svrListenerSupport.removeAllCommunicateServerListener();
	}

	/** 
	 * getHost。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#getHost()
	 */
	@Override
	public String getHost() {
		return mqttClient.getServerURI();
	}

	/** 
	 * getPort。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#getPort()
	 */
	@Override
	public int getPort() {
		try {
			URI uri = new URI(mqttClient.getServerURI());
			return uri.getPort();
		} catch (URISyntaxException e) {
			throw new CommunicateException(e.getMessage(), e);
		}
	}

	/** 
	 * start。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#start()
	 */
	@Override
	public void start() {
		this.connect();
	}

	/** 
	 * stop。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#stop()
	 */
	@Override
	public void stop() {
		this.close();
	}

	/** 
	 * reset。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#reset()
	 */
	@Override
	public void reset() {
		this.stop();
		this.start();
	}

	/** 
	 * isRunning。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return mqttClient.isConnected();
	}

	/** 
	 * isActive。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#isActive()
	 */
	@Override
	public boolean isActive() {
		return mqttClient.isConnected();
	}

	/** 
	 * close。
	 * @see org.quincy.rock.comm.mqtt.IMqttCommunicator#close()
	 */
	@Override
	public void close() {
		try {
			IMqttToken token = mqttClient.disconnect();
			token.waitForCompletion(mqttConnectOptions.getConnectionTimeout());
			recorder.write("The connection has been broken.");
		} catch (Exception e) {
			recorder.write(e, e.getMessage());
		}
		fireDisconnectionEvent(cloneMqttChannel());
		svrListenerSupport.fireServerStoppedEvent(this);
	}

	/** 
	 * destroy。
	 * @see org.quincy.rock.comm.communicate.AbstractCommunicator#destroy()
	 */
	@Override
	public void destroy() {
		this.close();
		this.shutdownExecutorService();
		try {
			mqttClient.close();
		} catch (Exception e) {
			recorder.write(e, e.getMessage());
		}
		svrListenerSupport.removeAllCommunicateServerListener();
		super.destroy();
	}

	//关闭线程执行服务器
	private void shutdownExecutorService() {
		try {
			executorService.shutdown();
			if (!executorService.awaitTermination(mqttConnectOptions.getExecutorServiceTimeout(), TimeUnit.SECONDS)) {
				executorService.shutdownNow();
				if (!executorService.awaitTermination(mqttConnectOptions.getExecutorServiceTimeout(),
						TimeUnit.SECONDS)) {
					recorder.write("ExecutorService did not terminate!");
				}
			}
		} catch (Exception e) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	//初始化订阅主题
	private void initialSubscribeTopics() throws MqttException {
		String[] topics = this.getInitialSubscribeTopics().toArray(ArrayUtils.EMPTY_STRING_ARRAY);
		if (ArrayUtils.isNotEmpty(topics)) {
			this.subscribe(topics);
		}
	}

	//使用的serverURI
	private void useServerURI(String serverURI) {
		getRootChannel().serverURI(serverURI);
	}

	//克隆mqtt通道
	private UChannel cloneMqttChannel() {
		return this.getRootChannel().cloneMe();
	}

	//检查发送通道合法性
	private void checkSendChannel(UChannel ch) {
		if (ch.mqttClient() == null)
			ch.mqttClient(mqttClient);
		this.checkMqttChannel(ch);
		if (!ch.isValidChannel() || !ch.isSendChannel())
			throw new CommunicateException("This is not a valid send MqttChannel!");
	}

	//检查通道合法性
	private void checkMqttChannel(UChannel ch) {
		if (ch.mqttClient() != mqttClient)
			throw new CommunicateException("This is not a valid approved MqttChannel!");
	}
}
