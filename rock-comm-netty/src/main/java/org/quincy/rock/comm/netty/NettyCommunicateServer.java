package org.quincy.rock.comm.netty;

import java.net.InetSocketAddress;
import java.util.Map;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.communicate.CommunicateServer;
import org.quincy.rock.comm.communicate.CommunicateServerListener;
import org.quincy.rock.comm.util.CommunicateServerListenerSupport;
import org.quincy.rock.core.util.StringUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

/**
 * <b>NettyCommunicatorServer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月11日 下午3:24:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class NettyCommunicateServer<UChannel> extends NettyCommunicator<UChannel>
		implements CommunicateServer<UChannel> {

	/**
	 * 服务器主机。
	 */
	protected String host;

	/**
	 * 端口号。
	 */
	protected int port;

	/**
	 * 服务器通道线程组。
	 */
	private EventLoopGroup bossGroup;
	/**
	 * 客户端通道线程组。
	 */
	private EventLoopGroup workerGroup;
	/**
	 * 服务器通道Future(检查器)。
	 */
	private ChannelFuture channelFuture_boss = null;

	/**
	 * svrListenerSupport。
	 */
	private final CommunicateServerListenerSupport<UChannel> svrListenerSupport = new CommunicateServerListenerSupport();

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param port 端口号
	 * @param maxActive 最大活动连接数
	 * @param channelTransformer 通道转换器
	 */
	public NettyCommunicateServer(int port, int maxActive, ChannelTransformer<UChannel> channelTransformer) {
		super(maxActive, channelTransformer);
		this.port = port;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param host 服务器主机
	 * @param port 端口号
	 * @param maxActive 最大活动连接数
	 * @param channelTransformer 通道转换器
	 */
	public NettyCommunicateServer(String host, int port, int maxActive,
			ChannelTransformer<UChannel> channelTransformer) {
		super(maxActive, channelTransformer);
		this.host = host;
		this.port = port;
	}

	/**
	 * <b>获得服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器主机
	 */
	public String getHost() {
		return host;
	}

	/**
	 * <b>获得端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 端口号
	 */
	public int getPort() {
		return port;
	}

	/**
	 * <b>配置线程组、设置通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bootstrap ServerBootstrap
	 * @return ServerBootstrap
	 */
	protected abstract ServerBootstrap config(ServerBootstrap bootstrap);

	/** 
	 * start。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#start()
	 */
	public final void start() {
		if (isRunning())
			return;
		//获得绑定地址
		String host = getHost();
		InetSocketAddress addr = StringUtil.isBlank(host) ? new InetSocketAddress(getPort())
				: new InetSocketAddress(host, getPort());
		//创建线程组
		try {
			//创建ServerBootstrap
			ServerBootstrap b = serverBootstrap();
			this.bossGroup = b.config().group();
			this.workerGroup = b.config().childGroup();
			//启动服务
			this.channelFuture_boss = b.bind(addr).sync();
			recorder.write("Start the netty communicate service.");
			//触发启动事件
			svrListenerSupport.fireServerStartedEvent(this);
		} catch (Exception e) {
			throw new CommunicateException(e.getMessage(), e);
		}
	}

	/** 
	 * stop。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#stop()
	 */
	public final void stop() {
		if (isRunning()) {
			try {
				channelFuture_boss.channel().close().sync();
			} catch (Exception e) {
			}
			try {
				workerGroup.shutdownGracefully().sync();
			} catch (Exception e) {
			}
			if (bossGroup != workerGroup) {
				try {
					bossGroup.shutdownGracefully().sync();
				} catch (Exception e) {
				}
			}
			workerGroup = null;
			bossGroup = null;
			channelFuture_boss = null;
			recorder.write("Stop the netty communicate service.");
			svrListenerSupport.fireServerStoppedEvent(this);
		}
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
	 * isRunning。
	 * @see org.quincy.rock.comm.communicator.CommunicatorServer#isRunning()
	 */
	public final boolean isRunning() {
		return channelFuture_boss != null;
	}

	/**
	 * <b>返回ServerBootstrap。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return ServerBootstrap
	 */
	private ServerBootstrap serverBootstrap() {
		ServerBootstrap b = this.config(new ServerBootstrap());
		Map<ChannelOption<?>, Object> optionMap = channelOptions();
		for (ChannelOption option : optionMap.keySet()) {
			b.childOption(option, optionMap.get(option));
		}
		b.childHandler(createChannelInitializer());
		return b;
	}

	/** 
	 * destroy。
	 * @see org.quincy.rock.comm.communicator.AbstractCommunicator#destroy()
	 */
	@Override
	public void destroy() {
		this.stop();
		super.destroy();
		recorder.write("Exit the netty communicate service.");
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
}