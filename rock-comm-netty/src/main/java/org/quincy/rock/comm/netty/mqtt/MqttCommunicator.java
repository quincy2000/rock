package org.quincy.rock.comm.netty.mqtt;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.communicate.ChannelTransformer;
import org.quincy.rock.comm.communicate.ChannelTransformer.STransformPoint;
import org.quincy.rock.comm.communicate.CommunicateClient;
import org.quincy.rock.comm.communicate.CommunicateServer;
import org.quincy.rock.comm.communicate.CommunicateServerListener;
import org.quincy.rock.comm.communicate.IChannel;
import org.quincy.rock.comm.netty.ChannelHandlerCreator;
import org.quincy.rock.comm.netty.INettyChannel;
import org.quincy.rock.comm.netty.NettyCommunicator;
import org.quincy.rock.comm.netty.codec.KeyGetter;
import org.quincy.rock.comm.util.CommunicateServerListenerSupport;
import org.quincy.rock.core.exception.AlreadyExistException;
import org.quincy.rock.core.exception.ConfigException;
import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.lang.Getter;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.security.CrcType;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.util.StringUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <b>MqttCommunicator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月14日 上午10:03:48</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MqttCommunicator extends NettyCommunicator<IMqttChannel>
		implements CommunicateServer<IMqttChannel>, CommunicateClient<IMqttChannel> {
	/**
	 * mqtt服务器主机。
	 */
	private String mqttHost;
	/**
	 * mqtt服务器端口号。
	 */
	private int mqttPort;
	/**
	 * netty通道。
	 */
	private Channel nettyChannel;
	/**
	 * mqtt通道。
	 */
	private IMqttChannel mqttChannel;

	/**
	 * mqtt内部通道句柄。
	 */
	private MqttChannelHandler mqttChannelHandler;

	/**
	 * bootstrap。
	 */
	private Bootstrap bootstrap;

	/**
	 * svrListenerSupport。
	 */
	private final CommunicateServerListenerSupport<IMqttChannel> svrListenerSupport = new CommunicateServerListenerSupport();

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttPort mqtt服务器端口号
	 */
	public MqttCommunicator(int mqttPort) {
		this(null, mqttPort);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttHost mqtt服务器主机
	 * @param mqttPort mqtt服务器端口号
	 */
	public MqttCommunicator(String mqttHost, int mqttPort) {
		super(1);
		this.mqttHost = mqttHost;
		this.mqttPort = mqttPort;
		this.mqttChannelHandler = createMqttChannelHandler();
		if (mqttChannelHandler.getClientid() == null)
			mqttChannelHandler.setClientid(StringUtil.getUniqueIdentifierName("mqtt"));
		//
		super.setChannelTransformer(new MqttChannelTransformer<IMqttChannel>() {
			private Getter<Channel> getter = new Getter<Channel>() {
				@Override
				public Channel get() {
					return nettyChannel();
				}
			};

			@Override
			protected IMqttChannel createChannel(Channel source) {
				IMqttChannel ch = rootChannel().cloneMe();
				ch.setChannelGetter(getter);
				return ch;
			}

		});
	}

	/**
	 * <b>创建MqttChannelHandler。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return MqttChannelHandler
	 */
	protected MqttChannelHandler createMqttChannelHandler() {
		return new MqttChannelHandler();
	}

	/**
	 * <b>获得mqtt服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt服务器主机
	 */
	public String getMqttHost() {
		return mqttHost;
	}

	/**
	 * <b>设置mqtt服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttHost mqtt服务器主机
	 */
	public void setMqttHost(String mqttHost) {
		this.mqttHost = mqttHost;
	}

	/**
	 * <b>获得mqtt服务器端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt服务器端口号
	 */
	public int getMqttPort() {
		return mqttPort;
	}

	/**
	 * <b>设置mqtt服务器端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttPort mqtt服务器端口号
	 */
	public void setMqttPort(int mqttPort) {
		this.mqttPort = mqttPort;
	}

	/**
	 * <b>获得本地主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 本地主机
	 */
	public String getHost() {
		if (isActive()) {
			InetSocketAddress addr = (InetSocketAddress) mqttChannel.channel().localAddress();
			return addr.getHostName();
		} else
			return null;
	}

	/**
	 * <b>获得本地主机端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 本地主机端口号
	 */
	public int getPort() {
		if (isActive()) {
			InetSocketAddress addr = (InetSocketAddress) mqttChannel.channel().localAddress();
			return addr.getPort();
		} else
			return -1;
	}

	//获得netty通道
	private Channel nettyChannel() {
		return nettyChannel;
	}

	/** 
	 * setNettyChannel。
	 * @see org.quincy.rock.comm.netty.NettyCommunicator#setNettyChannel(org.quincy.rock.comm.netty.INettyChannel)
	 */
	@Override
	public void setNettyChannel(INettyChannel channel) {
		if (channel instanceof IMqttChannel)
			this.setMqttChannel((IMqttChannel) channel);
		else
			throw new ConfigException("A channel must be an instance of IMqttChannel");
	}

	/**
	 * <b>设置mqtt通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param mqttChannel mqtt通道
	 */
	public void setMqttChannel(IMqttChannel mqttChannel) {
		this.mqttChannel = mqttChannel;
	}

	/**
	 * <b>根通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 根通道
	 */
	public <T extends IChannel> T rootChannel() {
		return (T) this.mqttChannel;
	}
	
	/** 
	 * start。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#start()
	 */
	@Override
	public void start() {
		connect();
	}

	/** 
	 * stop。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#stop()
	 */
	@Override
	public void stop() {
		close();
	}

	/** 
	 * isRunning。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return isActive();
	}

	/** 
	 * reset。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#reset()
	 */
	@Override
	public void reset() {
		this.stop();
		this.start();
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicator.CommunicatorClient#connect()
	 */
	@Override
	public IMqttChannel connect() {
		if (isActive())
			throw new AlreadyExistException("The connection already exists!");
		nettyChannel = null;
		InetSocketAddress addr = StringUtil.isBlank(mqttHost) ? new InetSocketAddress(mqttPort)
				: new InetSocketAddress(mqttHost, mqttPort);
		Bootstrap b = this.bootstrap();
		ChannelFuture f = null;
		try {
			f = b.connect(addr).sync();
		} catch (Exception e) {
			throw new CommunicateException("Failed to connect to server!\n" + e.getMessage(), e);
		}
		if (f.isSuccess()) {
			nettyChannel = f.channel();
			mqttChannel = getChannelTransformer().transform(nettyChannel, STransformPoint.ONLY_RETURN);
			DateUtil.sleep(500);
			svrListenerSupport.fireServerStartedEvent(this);
			return mqttChannel;
		} else
			throw new CommunicateException(f.cause());
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicate.CommunicateClient#connect(java.lang.String)
	 */
	@Override
	public final IMqttChannel connect(String mqttHost) {
		if (isActive())
			throw new AlreadyExistException("The connection already exists!");
		this.mqttHost = mqttHost;
		return connect();
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicate.CommunicateClient#connect(java.lang.String, int)
	 */
	@Override
	public IMqttChannel connect(String mqttHost, int mqttPort) {
		this.mqttPort = mqttPort;
		return connect(mqttHost);
	}

	/** 
	 * isActive。
	 * @see org.quincy.rock.comm.netty.NettyCommunicator#isActive(java.lang.Object)
	 */
	@Override
	@Deprecated
	public final boolean isActive(IMqttChannel channel) {
		if (this.mqttChannel != channel)
			throw new CommunicateException("It's not an internal mqttChannel!");
		return super.isActive(channel);
	}

	/**
	 * <b>是否是活动的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是活动的
	 */
	public boolean isActive() {
		return super.isActive(mqttChannel);
	}

	/** 
	 * close。
	 * @see org.quincy.rock.comm.netty.NettyCommunicator#close(java.lang.Object)
	 */
	@Override
	@Deprecated
	public final void close(IMqttChannel channel) {
		if (mqttChannel.channel() != channel.channel())
			throw new CommunicateException("It's not an internal mqttChannel!");
		super.close(channel);
		Channel ch = mqttChannel.channel();
		if (ch != null && ch.isActive()) {
			try {
				ChannelFuture future = ch.close();
				future.awaitUninterruptibly(getTimeout(), TimeUnit.SECONDS);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * <b>关闭连接。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void close() {
		super.close(mqttChannel);
		svrListenerSupport.fireServerStoppedEvent(this);
	}

	/** 
	 * initializeChannel。
	 * @see org.quincy.rock.comm.netty.NettyCommunicator#initializeChannel(io.netty.channel.Channel)
	 */
	@Override
	protected void initializeChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//mqtt
		for (ChannelHandler handler : mqttChannelHandler.createChannelHandlers())
			pipeline.addLast(handler);
		//user
		ChannelHandlerCreator creator = getChannelHandlerCreator();
		if (creator != null) {
			for (ChannelHandler handler : creator.createChannelHandlers())
				pipeline.addLast(handler);
		}
	}

	/** 
	 * setChannelTransformer。
	 * @see org.quincy.rock.comm.netty.NettyCommunicator#setChannelTransformer(org.quincy.rock.comm.communicate.ChannelTransformer)
	 */
	@Override
	@Deprecated
	public final void setChannelTransformer(ChannelTransformer<IMqttChannel, Channel> channelTransformer) {
		if (channelTransformer != this.getChannelTransformer())
			throw new UnsupportException("Custom channelTransformer are not supported!");
	}

	/**
	 * <b>返回Bootstrap。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Bootstrap
	 */
	private synchronized Bootstrap bootstrap() {
		if (bootstrap == null) {
			bootstrap = this.config(new Bootstrap());
			Map<ChannelOption<?>, Object> optionMap = channelOptions();
			for (ChannelOption option : optionMap.keySet()) {
				bootstrap.option(option, optionMap.get(option));
			}
			bootstrap.handler(this.createChannelInitializer());
		}
		//
		return bootstrap;
	}

	/**
	 * <b>配置线程组、设置通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bootstrap Bootstrap
	 * @return Bootstrap
	 */
	protected Bootstrap config(Bootstrap bootstrap) {
		return bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class);
	}

	/** 
	 * destroy。
	 * @see org.quincy.rock.comm.communicator.AbstractCommunicator#destroy()
	 */
	@Override
	public void destroy() {
		this.close();
		if (bootstrap != null) {
			try {
				bootstrap.config().group().shutdownGracefully().sync();
			} catch (Exception e) {
			}
			bootstrap = null;
			mqttChannel = null;
		}
		super.destroy();
	}

	/** 
	 * setRecorder。
	 * @see org.quincy.rock.comm.communicator.AbstractCommunicator#setRecorder(org.quincy.rock.core.util.Recorder)
	 */
	@Override
	public void setRecorder(Recorder recorder) {
		mqttChannelHandler.setRecorder(recorder);
		super.setRecorder(recorder);
	}

	/**
	 * <b>获得mqtt客户端唯一标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt客户端唯一标识
	 */
	public final String getClientid() {
		return mqttChannelHandler.getClientid();
	}

	/**
	 * <b>设置mqtt客户端唯一标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clientid mqtt客户端唯一标识
	 */
	public final void setClientid(String clientid) {
		mqttChannelHandler.setClientid(clientid);
	}

	/**
	 * <b>获得mqtt用户名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return mqtt用户名
	 */
	public final String getUserName() {
		return mqttChannelHandler.getUserName();
	}

	/**
	 * <b>设置userName。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param userName userName
	 */
	public final void setUserName(String userName) {
		mqttChannelHandler.setUserName(userName);
	}

	/**
	 * <b>获得密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @return 密码
	 */
	public final String getPassword() {
		return mqttChannelHandler.getPassword();
	}

	/**
	 * <b>设置密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param password 密码
	 */
	public final void setPassword(String password) {
		mqttChannelHandler.setPassword(password);
	}

	/**
	 * <b>获得心跳间隔时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 心跳间隔时间
	 */
	public final int getHeartbeat() {
		return mqttChannelHandler.getHeartbeat();
	}

	/**
	 * <b>设置心跳间隔时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param heartbeat 心跳间隔时间
	 */
	public final void setHeartbeat(int heartbeat) {
		mqttChannelHandler.setHeartbeat(heartbeat);
	}

	/**
	 * <b>设置遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param will 遗嘱
	 */
	public final void setWillString(String will) {
		mqttChannelHandler.setWillString(will);
	}

	/**
	 * <b>获得遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 遗嘱
	 */
	public final byte[] getWillBytes() {
		return mqttChannelHandler.getWillBytes();
	}

	/**
	 * <b>设置遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param will 遗嘱
	 */
	public final void setWillBytes(byte[] willBytes) {
		mqttChannelHandler.setWillBytes(willBytes);
	}

	/**
	 * <b>getCrcType。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CrcType
	 */
	public final CrcType getCrcType() {
		return mqttChannelHandler.getCrcType();
	}

	/**
	 * <b>setCrcType。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param crcType CrcType
	 */
	public final void setCrcType(CrcType crcType) {
		mqttChannelHandler.setCrcType(crcType);
	}

	/**
	 * <b>CRC是否是BCD码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CRC是否是BCD码
	 */
	public final boolean isBcdCrc() {
		return mqttChannelHandler.isBcdCrc();
	}

	/**
	 * <b>CRC是否是BCD码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bcdCrc CRC是否是BCD码
	 */
	public final void setBcdCrc(boolean bcdCrc) {
		mqttChannelHandler.setBcdCrc(bcdCrc);
	}

	/**
	 * <b>是否忽略CRC错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否忽略CRC错误
	 */
	public final boolean isIgnoreCrcError() {
		return mqttChannelHandler.isIgnoreCrcError();
	}

	/**
	 * <b>设置是否忽略CRC错误。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreCrcError 是否忽略CRC错误
	 */
	public final void setIgnoreCrcError(boolean ignoreCrcError) {
		mqttChannelHandler.setIgnoreCrcError(ignoreCrcError);
	}

	/**
	 * <b>获得忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 忽略的头部(该头部报文没有crc校验)
	 */
	public final byte[] getIgnoreHead() {
		return mqttChannelHandler.getIgnoreHead();
	}

	/**
	 * <b>设置忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreHead 忽略的头部(该头部报文没有crc校验)
	 */
	public final void setIgnoreHead(byte[] ignoreHead) {
		mqttChannelHandler.setIgnoreHead(ignoreHead);
	}

	/**
	 * <b>设置忽略的头部(该头部报文没有crc校验)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ignoreHead 忽略的头部(该头部报文没有crc校验)
	 */
	public final void setIgnoreHeadString(String ignoreHead) {
		mqttChannelHandler.setIgnoreHeadString(ignoreHead);
	}

	/**
	 * <b>获得一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 一般防篡改秘钥
	 */
	public final byte[] getGeneralKey() {
		return mqttChannelHandler.getGeneralKey();
	}

	/**
	 * <b>设置一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param generalKey 一般防篡改秘钥
	 */
	public final void setGeneralKey(byte[] generalKey) {
		mqttChannelHandler.setGeneralKey(generalKey);
	}

	/**
	 * <b>设置一般防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param generalKey 一般防篡改秘钥
	 */
	public final void setGeneralKeyString(String generalKey) {
		mqttChannelHandler.setGeneralKeyString(generalKey);
	}

	/**
	 * <b>设置随机防篡改秘钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param randomKey 随机防篡改秘钥回调器
	 */
	public final void setRandomKey(KeyGetter randomKey) {
		mqttChannelHandler.setRandomKey(randomKey);
	}

	/**
	 * <b>获得遗嘱Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 遗嘱Topic
	 */
	public final String getWillTopic() {
		return mqttChannelHandler.getWillTopic();
	}

	/**
	 * <b>设置遗嘱Topic。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param willTopic 遗嘱Topic
	 */
	public final void setWillTopic(String willTopic) {
		mqttChannelHandler.setWillTopic(willTopic);
	}

	/**
	 * <b>是否设置了遗嘱。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否设置了遗嘱
	 */
	public final boolean hasWill() {
		return mqttChannelHandler.hasWill();
	}

	/**
	 * <b>获得报文消息最大字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文消息最大字节数
	 */
	public final int getMaxBytesInMessage() {
		return mqttChannelHandler.getMaxBytesInMessage();
	}

	/**
	 * <b>设置报文消息最大字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxBytesInMessage 报文消息最大字节数
	 */
	public final void setMaxBytesInMessage(int maxBytesInMessage) {
		mqttChannelHandler.setMaxBytesInMessage(maxBytesInMessage);
	}

	/**
	 * <b>是否是大端字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是大端字节序
	 */
	public final boolean isBigEndian() {
		return mqttChannelHandler.isBigEndian();
	}

	/**
	 * <b>是否是大端字节序。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bigEndian 是否是大端字节序
	 */
	public final void setBigEndian(boolean bigEndian) {
		mqttChannelHandler.setBigEndian(bigEndian);
	}

	/**
	 * <b>获得首个ChannelHandler拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 首个ChannelHandler拦截器
	 */
	public final ChannelHandler getFirstChannelHandler() {
		return mqttChannelHandler.getFirstChannelHandler();
	}

	/**
	 * <b>设置首个ChannelHandler拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param firstChannelHandler 首个ChannelHandler拦截器
	 */
	public final void setFirstChannelHandler(ChannelHandler firstChannelHandler) {
		mqttChannelHandler.setFirstChannelHandler(firstChannelHandler);
	}

	/**
	 * <b>获得报文发送拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文发送拦截器
	 */
	public final ChannelHandler getSendInterceptor() {
		return mqttChannelHandler.getSendInterceptor();
	}

	/**
	 * <b>设置报文发送拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param sendInterceptor 报文发送拦截器
	 */
	public final void setSendInterceptor(ChannelHandler sendInterceptor) {
		mqttChannelHandler.setSendInterceptor(sendInterceptor);
	}

	/**
	 * <b>获得报文接收拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文接收拦截器
	 */
	public final ChannelHandler getReceiveInterceptor() {
		return mqttChannelHandler.getReceiveInterceptor();
	}

	/**
	 * <b>设置报文接收拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param receiveInterceptor 报文接收拦截器
	 */
	public final void setReceiveInterceptor(ChannelHandler receiveInterceptor) {
		mqttChannelHandler.setReceiveInterceptor(receiveInterceptor);
	}

	/**
	 * <b>获得初始订阅的Topic列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 初始订阅的Topic列表
	 */
	public final List<SubscribeTopic> getInitialSubscribeTopics() {
		return mqttChannelHandler.getInitialSubscribeTopics();
	}

	/**
	 * <b>设置初始订阅的Topic列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param subscribeTopics 初始订阅的Topic列表
	 */
	public final void setInitialSubscribeTopics(List<SubscribeTopic> subscribeTopics) {
		mqttChannelHandler.setInitialSubscribeTopics(subscribeTopics);
	}

	/**
	 * <b>添加初始订阅的Topic 。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param subscribeTopic 初始订阅的Topic
	 */
	public final void addInitialSubscribeTopic(SubscribeTopic subscribeTopic) {
		mqttChannelHandler.addInitialSubscribeTopic(subscribeTopic);
	}

	/**
	 * <b>实时订阅。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topics 一个或多个topic
	 */
	public final void subscribeTopic(SubscribeTopic... topics) {
		mqttChannelHandler.subscribeTopic(mqttChannel.channel().id(), topics);
	}

	/**
	 * <b>实时取消订阅。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topics 一个或多个topic
	 */
	public final void unsubscribeTopic(String... topics) {
		mqttChannelHandler.unsubscribeTopic(mqttChannel.channel().id(), topics);
	}

	/** 
	 * addCommunicateServerListener。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#addCommunicateServerListener(org.quincy.rock.comm.communicate.CommunicateServerListener)
	 */
	@Override
	public void addCommunicateServerListener(CommunicateServerListener<IMqttChannel> listener) {
		svrListenerSupport.addCommunicateServerListener(listener);
	}

	/** 
	 * removeCommunicateServerListener。
	 * @see org.quincy.rock.comm.communicate.CommunicateServer#removeCommunicateServerListener(org.quincy.rock.comm.communicate.CommunicateServerListener)
	 */
	@Override
	public void removeCommunicateServerListener(CommunicateServerListener<IMqttChannel> listener) {
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
}
