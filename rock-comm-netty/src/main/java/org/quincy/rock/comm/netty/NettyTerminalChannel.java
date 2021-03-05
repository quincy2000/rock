package org.quincy.rock.comm.netty;

import org.quincy.rock.comm.communicate.AbstractTerminalChannel;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.lang.Getter;

import io.netty.channel.Channel;

/**
 * <b>NettyTerminalChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月1日 下午10:04:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NettyTerminalChannel<TYPE, CODE> extends AbstractTerminalChannel<TYPE, CODE>
		implements INettyTerminalChannel<TYPE, CODE> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1449218941077213843L;

	/**
	 * channel。
	 */
	private Getter<Channel> getter;

	/** 
	 * channel。
	 * @see org.quincy.rock.comm.netty.INettyChannel#channel()
	 */
	public final Channel channel() {
		return getter == null ? null : getter.get();
	}

	/** 
	 * setChannelGetter。
	 * @see org.quincy.rock.comm.netty.INettyChannel#setChannelGetter(org.quincy.rock.core.lang.Getter)
	 */
	@Override
	public <T extends INettyChannel> T setChannelGetter(Getter<Channel> getter) {
		this.getter = getter;
		return (T) this;
	}

	/** 
	 * createLocal。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#createLocal()
	 */
	@Override
	protected TerminalId<TYPE, CODE> createLocal() {
		return new TerminalId() {
			/**
			 * serialVersionUID。
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isServer() {
				return isServerSide();
			}
		};
	}

	/** 
	 * createRemote。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#createRemote()
	 */
	@Override
	protected TerminalId<TYPE, CODE> createRemote() {
		return new TerminalId() {
			/**
			 * serialVersionUID。
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isServer() {
				return !isServerSide();
			}
		};
	}

	/** 
	 * local。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#local()
	 */
	@Override
	public TerminalId<TYPE, CODE> local() {
		TerminalId<TYPE, CODE> term = super.local();
		Channel channel = channel();
		if (channel != null && term.getAddress() == null) {
			term.setAddress(channel.localAddress().toString());
		}
		return term;
	}

	/** 
	 * remote。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#remote()
	 */
	@Override
	public TerminalId<TYPE, CODE> remote() {
		TerminalId<TYPE, CODE> term = super.remote();
		Channel channel = channel();
		if (channel != null && term.getAddress() == null) {
			term.setAddress(channel.remoteAddress().toString());
		}
		return term;
	}

	/** 
	 * isValidChannel。
	 * @see org.quincy.rock.comm.communicate.AbstractChannel#isValidChannel()
	 */
	@Override
	public boolean isValidChannel() {
		return this.channel() != null;
	}

	/** 
	 * channelId。
	 * @see org.quincy.rock.comm.communicate.AbstractTerminalChannel#channelId()
	 */
	@Override
	public Object channelId() {
		Channel ch = this.channel();
		return ch == null ? remote().id() : (remote().id() + ch.id().asLongText());
	}
}
