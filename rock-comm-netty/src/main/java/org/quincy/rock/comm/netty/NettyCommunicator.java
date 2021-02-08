package org.quincy.rock.comm.netty;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.communicate.AbstractCommunicator;
import org.quincy.rock.comm.netty.ChannelTransformer.STransformPoint;
import org.quincy.rock.comm.netty.ChannelTransformer.UTransformPoint;
import org.quincy.rock.core.function.Consumer;
import org.quincy.rock.core.function.Function;
import org.quincy.rock.core.vo.Option;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * <b>NettyCommunicator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月1日 下午5:03:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class NettyCommunicator<UChannel> extends AbstractCommunicator<UChannel> {
	/**
	 * 存放通讯器id的Key,标记Netty通道所属通讯器。
	 */
	private static final AttributeKey<String> NETTY_CHANNEL_OWNER_KEY = AttributeKey.valueOf("netty_channel_owner");

	/**
	 * lastChannelHandler。
	 */
	private final LastChannelHandler lastChannelHandler = new LastChannelHandler();
	/**
	 * lastErrorChannelHandler。
	 */
	private final LastErrorChannelHandler lastErrorChannelHandler = new LastErrorChannelHandler();

	/**
	 * 切片程序。
	 */
	private final static Function<Object, Object> FOR_SLICE = new Function<Object, Object>() {

		@Override
		public Object call(Object t) {
			return NettyUtil.slice(t);
		}
	};

	/**
	 * 超时秒数。
	 */
	private int timeout = Integer.MAX_VALUE;

	/**
	 * 最大活动连接数。
	 */
	private int maxActive;

	/**
	 * 连接数。
	 */
	private final AtomicInteger atomicCount = new AtomicInteger(0);
	/**
	 * 通道选项列表。
	 */
	private final Map<ChannelOption<?>, Object> channelOptions = new HashMap<>();
	/**
	 * 通道转换器。
	 */
	private ChannelTransformer<UChannel> channelTransformer = ChannelTransformer.NONE;
	/**
	 * 通道句柄创建器。
	 */
	private ChannelHandlerCreator channelHandlerCreator;
	/**
	 * 使用ByteBuffer。
	 */
	private boolean useByteBuffer;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxActive 最大活动连接数
	 */
	public NettyCommunicator(int maxActive) {
		this.maxActive = maxActive;
	}

	/**
	 * <b>获得最大活动连接数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大活动连接数
	 */
	public int getMaxActive() {
		return maxActive;
	}

	/**
	 * <b>返回活动连接数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 活动连接数
	 */
	public int getActiveCount() {
		return atomicCount.get();
	}

	/**
	 * <b>设置超时秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timeout 超时秒数
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout < 0 ? Integer.MAX_VALUE : timeout;
	}

	/**
	 * <b>获得超时秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 超时秒数
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <b>是否使用ByteBuffer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否使用ByteBuffer
	 */
	public boolean isUseByteBuffer() {
		return useByteBuffer;
	}

	/**
	 * <b>是否使用ByteBuffer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param useByteBuffer 是否使用ByteBuffer
	 */
	public void setUseByteBuffer(boolean useByteBuffer) {
		this.useByteBuffer = useByteBuffer;
	}

	/**
	 * <b>设置ChannelOption列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelOptions ChannelOption列表
	 */
	public void channelOptions(Map<ChannelOption<?>, Object> channelOptions) {
		this.channelOptions.putAll(channelOptions);
	}

	/**
	 * <b>返回ChannelOption列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return ChannelOption列表
	 */
	public Map<ChannelOption<?>, Object> channelOptions() {
		return this.channelOptions;
	}

	/**
	 * <b>设置ChannelOption。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelOption ChannelOption
	 * @param value 值
	 */
	public <T> void addChannelOption(ChannelOption<T> channelOption, T value) {
		this.channelOptions.put(channelOption, value);
	}

	/**
	 * <b>设置ChannelOption。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelOptions
	 */
	public void setChannelOptions(List<Option<?>> options) {
		for (Option<?> option : options) {
			this.addChannelOption(ChannelOption.valueOf(option.getName()), option.getValue());
		}
	}

	/**
	 * <b>获得通道转换器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @return 通道转换器
	 */
	public ChannelTransformer<UChannel> getChannelTransformer() {
		return channelTransformer;
	}

	/**
	 * <b>设置通道转换器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelTransformer 通道转换器
	 */
	public void setChannelTransformer(ChannelTransformer<UChannel> channelTransformer) {
		this.channelTransformer = channelTransformer;
	}

	/**
	 * <b>设置NettyChannel。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel NettyChannel的实例
	 */
	public void setNettyChannel(INettyChannel channel) {
		ChannelTransformer ct = new DefaultNettyChannelTransformer(channel);
		this.setChannelTransformer(ct);
	}

	/**
	 * <b>获得通道句柄创建器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通道句柄创建器
	 */
	public ChannelHandlerCreator getChannelHandlerCreator() {
		return channelHandlerCreator;
	}

	/**
	 * <b>设置通道句柄创建器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channelHandlerCreator 通道句柄创建器
	 */
	public void setChannelHandlerCreator(ChannelHandlerCreator channelHandlerCreator) {
		this.channelHandlerCreator = channelHandlerCreator;
	}

	/**
	 * <b>创建通道初始化器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param lastChannelHandler 最后添加的通道句柄
	 * @return 通道初始化器
	 */
	protected final ChannelHandler createChannelInitializer() {
		ChannelHandler ch = new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				initializeChannel(ch);
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(lastChannelHandler);
				pipeline.addLast(lastErrorChannelHandler);
			}

		};
		return ch;
	}

	/**
	 * <b>初始化通道。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 子类实现该方法设置编码解码器或进行其他通道设置
	 * @param ch 通道
	 * @throws Exception
	 */
	protected void initializeChannel(Channel ch) throws Exception {
		ChannelHandlerCreator creator = getChannelHandlerCreator();
		if (creator != null) {
			ChannelPipeline pipeline = ch.pipeline();
			for (ChannelHandler handler : creator.createChannelHandlers())
				pipeline.addLast(handler);
		}
	}

	/** 
	 * sendData。
	 * @see org.quincy.rock.comm.communicate.Communicator#sendData(java.lang.Object, java.lang.Object, boolean, org.quincy.rock.core.function.Consumer)
	 */
	@Override
	public void sendData(UChannel channel, final Object data, boolean async, final Consumer<Boolean> consumer) {
		GenericFutureListener<Future<? super Void>> gfListener;
		ChannelFuture future;
		//获得通道转换器
		ChannelTransformer<UChannel> ct = this.getChannelTransformer();
		Object lock = ct.retrieveSendLock(channel);
		synchronized (lock) {
			Channel ch = ct.transform(channel, UTransformPoint.SEND_DATA);
			//创建事件监听
			gfListener = consumer == null ? null : new GenericFutureListener<Future<? super Void>>() {
				private Consumer<Boolean> c = consumer;
				private Object m = NettyUtil.retainRC(data);

				@Override
				public void operationComplete(Future<? super Void> f) throws Exception {
					try {
						c.call(f.isSuccess());
					} finally {
						if (!NettyUtil.releaseRC(m))
							recorder.write(NettyUtil.REF_CNT_0);
					}
				}
			};
			future = ch.writeAndFlush(data);
		}
		if (async && gfListener != null) {
			//异步
			future.addListener(gfListener);
		} else if (!async) {
			//同步
			try {
				boolean ok = future.awaitUninterruptibly(getTimeout(), TimeUnit.SECONDS);
				if (consumer != null)
					consumer.call(ok && future.isSuccess());
			} finally {
				if (gfListener != null && !NettyUtil.releaseRC(data)) {
					recorder.write(NettyUtil.REF_CNT_0);
				}
			}
		}
	}

	/** 
	 * close。
	 * @see org.quincy.rock.comm.communicate.Communicator#close(java.lang.Object)
	 */
	@Override
	public void close(UChannel channel) {
		if (channel != null) {
			try {
				Channel ch = getChannelTransformer().transform(channel, UTransformPoint.CLOSE_CHANNEL);
				if (isMyChannel(ch) && ch.isActive()) {
					ChannelFuture future = ch.close();
					future.awaitUninterruptibly(getTimeout(), TimeUnit.SECONDS);
				}
			} catch (Exception e) {
				recorder.write(e, "NettyCommunicator close:{0}", e.getMessage());
			}
		}
	}

	/** 
	 * isActive。
	 * @see org.quincy.rock.comm.communicate.Communicator#isActive(java.lang.Object)
	 */
	@Override
	public boolean isActive(UChannel channel) {
		Channel ch = channel == null ? null : getChannelTransformer().transform(channel, UTransformPoint.ONLY_RETURN);
		return isMyChannel(ch) && ch.isActive();
	}

	/**
	 * <b>通道增加时调用该方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 增加的通道
	 */
	protected void addChannel(Channel channel) {
		if (isMyChannel(channel))
			return;
		int count = atomicCount.getAndIncrement();
		if (count == maxActive) {
			atomicCount.getAndDecrement();
			throw new CommunicateException("Reach the maximum number of connections.");
		}
		channel.attr(NETTY_CHANNEL_OWNER_KEY).set(id());
	}

	/**
	 * <b>通道移走时调用该方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 移走的通道
	 */
	protected void removeChannel(Channel channel) {
		if (!isMyChannel(channel))
			return;
		channel.attr(NETTY_CHANNEL_OWNER_KEY).set(null);
		int count = atomicCount.getAndDecrement();
		if (count == 0) {
			atomicCount.getAndIncrement();
			throw new CommunicateException("Reach the minimum number of connections.");
		}
	}

	private boolean isMyChannel(Channel channel) {
		return channel != null && channel.attr(NETTY_CHANNEL_OWNER_KEY).get() == id();
	}

	@Sharable
	private class LastChannelHandler extends ChannelDuplexHandler {

		/** 
		 * channelActive。
		 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			try {
				addChannel(ctx.channel());
				fireConnectionEvent(getChannelTransformer().transform(ctx.channel(), STransformPoint.CHANNEL_ACTIVE));
			} catch (Exception e) {
				recorder.write(e, "NettyCommunicator channelActive[{0}]:{1}", ctx.channel().id(), e.getMessage());
				throw e;
			}
			recorder.write("channelActive:{0}", ctx.channel().id());
		}

		/** 
		 * channelInactive。
		 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
		 */
		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			try {
				fireDisconnectionEvent(
						getChannelTransformer().transform(ctx.channel(), STransformPoint.CHANNEL_INACTIVE));
			} catch (Exception e) {
				recorder.write(e, "NettyCommunicator channelInactive[{0}]:{1}", ctx.channel().id(), e.getMessage());
			}
			try {
				removeChannel(ctx.channel());
			} catch (Exception e) {
				recorder.write(e, "NettyCommunicator channelInactive[{0}]:{1}", ctx.channel().id(), e.getMessage());
			}
			recorder.write("channelInactive:{0}", ctx.channel().id());
		}

		/** 
		 * channelRead。
		 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
			final Object msg = (useByteBuffer && message instanceof ByteBuf) ? ((ByteBuf) message).nioBuffer()
					: message;
			try {
				fireReceiveDataEvent(getChannelTransformer().transform(ctx.channel(), STransformPoint.CHANNEL_READ),
						msg, FOR_SLICE);
			} finally {
				if (!NettyUtil.releaseRC(message))
					recorder.write(NettyUtil.REF_CNT_0);
			}
		}

		/** 
		 * write。
		 * @see io.netty.channel.ChannelDuplexHandler#write(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise)
		 */
		@Override
		public void write(ChannelHandlerContext ctx, Object message, ChannelPromise promise) throws Exception {
			final Object msg = (useByteBuffer && message instanceof ByteBuffer)
					? Unpooled.wrappedBuffer((ByteBuffer) message)
					: message;
			final Channel channel = ctx.channel();
			try {
				promise.addListener(new GenericFutureListener<Future<? super Void>>() {
					private UChannel c = getChannelTransformer().transform(channel, STransformPoint.CHANNEL_WRITE);
					private Object m = NettyUtil.retainRC(msg);

					@Override
					public void operationComplete(Future<? super Void> f) throws Exception {
						try {
							fireSendDataEvent(c, m, f.isSuccess(), FOR_SLICE);
						} finally {
							if (!NettyUtil.releaseRC(m))
								recorder.write(NettyUtil.REF_CNT_0);
						}
					}
				});
			} catch (Exception e) {
				recorder.write(e, "NettyCommunicator write[{0}]:{1}", ctx.channel().id(), e.getMessage());
				promise.setFailure(e);
			}
			ctx.write(NettyUtil.slice(msg), promise);
		}
	}

	@Sharable
	private class LastErrorChannelHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			try {
				fireExceptionCaughtEvent(
						getChannelTransformer().transform(ctx.channel(), STransformPoint.CHANNEL_ERROR), cause);
			} catch (Exception e) {
				recorder.write(e, "NettyCommunicator fireExceptionCaughtEvent[{0}]:{1}", ctx.channel().id(),
						e.getMessage());
			} finally {
				recorder.write(cause, "NettyCommunicator exceptionCaught[{0}]:{1}", ctx.channel().id(),
						cause.getMessage());
			}
		}

	}

}
