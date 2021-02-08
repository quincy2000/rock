package org.quincy.rock.comm.netty;

import java.net.InetSocketAddress;
import java.util.Map;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.communicate.CommunicateClient;
import org.quincy.rock.comm.netty.ChannelTransformer.STransformPoint;
import org.quincy.rock.core.util.StringUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;

/**
 * <b>NettyCommunicatorClient。</b>
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
public abstract class NettyCommunicateClient<UChannel> extends NettyCommunicator<UChannel>
		implements CommunicateClient<UChannel> {
	/**
	 * 缺省的服务器主机。
	 */
	private String defaultHost;
	/**
	 * 缺省的端口号。
	 */
	private int defaultPort;

	/**
	 * bootstrap。
	 */
	private Bootstrap bootstrap;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultPort 缺省的端口号
	 */
	public NettyCommunicateClient(int defaultPort) {
		this(defaultPort, 1);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultPort 缺省的端口号
	 * @param maxActive 最大活动连接数
	 */
	public NettyCommunicateClient(int defaultPort, int maxActive) {
		super(maxActive);
		this.defaultPort = defaultPort;
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultHost 缺省的服务器主机
	 * @param defaultPort 缺省的端口号
	 * @param maxActive 最大活动连接数
	 */
	public NettyCommunicateClient(String defaultHost, int defaultPort, int maxActive) {
		this(defaultPort, maxActive);
		this.defaultHost = defaultHost;
	}

	/**
	 * <b>获得缺省的服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缺省的服务器主机
	 */
	public String getDefaultHost() {
		return defaultHost;
	}

	/**
	 * <b>获得缺省的端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缺省的端口号
	 */
	public int getDefaultPort() {
		return defaultPort;
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicator.CommunicatorClient#connect()
	 */
	public UChannel connect() {
		return this.connect(getDefaultHost(), getDefaultPort());
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicator.CommunicatorClient#connect(java.lang.String)
	 */
	public UChannel connect(String host) {
		return connect(host, getDefaultPort());
	}

	/** 
	 * connect。
	 * @see org.quincy.rock.comm.communicate.CommunicateClient#connect(java.lang.String, int)
	 */
	public UChannel connect(String host, int port) {
		InetSocketAddress addr = StringUtil.isBlank(host) ? new InetSocketAddress(port)
				: new InetSocketAddress(host, port);
		Bootstrap b = this.bootstrap();
		ChannelFuture f = null;
		try {
			f = b.connect(addr).sync();
		} catch (Exception e) {
			throw new CommunicateException("Failed to connect to server!\n" + e.getMessage(), e);
		}
		if (f.isSuccess())
			return getChannelTransformer().transform(f.channel(), STransformPoint.ONLY_RETURN);
		else
			throw new CommunicateException(f.cause());
	}

	/**
	 * <b>配置线程组、设置通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bootstrap Bootstrap
	 * @return Bootstrap
	 */
	protected abstract Bootstrap config(Bootstrap bootstrap);

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
	 * destroy。
	 * @see org.quincy.rock.comm.communicator.AbstractCommunicator#destroy()
	 */
	@Override
	public void destroy() {
		if (bootstrap != null) {
			try {
				bootstrap.config().group().shutdownGracefully().sync();
			} catch (Exception e) {
			}
			bootstrap = null;
		}
		super.destroy();
	}
}
