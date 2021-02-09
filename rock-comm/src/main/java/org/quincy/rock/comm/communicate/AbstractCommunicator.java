package org.quincy.rock.comm.communicate;

import java.util.List;
import java.util.Vector;

import org.quincy.rock.core.function.Function;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>AbstractCommunicator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月3日 下午5:24:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractCommunicator<UChannel> implements Communicator<UChannel> {
	/**
	 * 通讯器名称。
	 */
	private final String id = StringUtil.getUniqueIdentifierName("c");

	/**
	 * listeners。
	 */
	private List<CommunicateListener<UChannel>> listeners = new Vector<>();

	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * <b>返回通讯器id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯器id
	 */
	public String id() {
		return id;
	}

	/**
	 * <b>日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 日志记录器
	 */
	public final Recorder getRecorder() {
		return recorder;
	}

	/**
	 * <b>日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder 日志记录器
	 */
	public final void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	/** 
	 * addCommunicateListener。
	 * @see org.quincy.rock.comm.communicate.Communicator#addCommunicateListener(int, org.quincy.rock.comm.communicate.CommunicateListener)
	 */
	@Override
	public void addCommunicateListener(int index, CommunicateListener<UChannel> listener) {
		listeners.add(index, listener);
	}

	/** 
	 * addCommunicateListener。
	 * @see org.quincy.rock.comm.communicate.Communicator#addCommunicateListener(org.quincy.rock.comm.communicate.CommunicateListener)
	 */
	@Override
	public void addCommunicateListener(CommunicateListener<UChannel> listener) {
		listeners.add(listener);
	}

	/**
	 * removeCommunicateListener。
	 * @see org.quincy.rock.comm.communicate.Communicator#removeCommunicateListener(org.quincy.rock.comm.communicate.CommunicateListener)
	 */
	@Override
	public void removeCommunicateListener(CommunicateListener<UChannel> listener) {
		listeners.remove(listener);
	}

	/** 
	 * removeAllCommunicateListener。
	 * @see org.quincy.rock.comm.communicate.Communicator#removeAllCommunicateListener()
	 */
	@Override
	public void removeAllCommunicateListener() {
		listeners.clear();
	}

	/**
	 * <b>触发连接事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 */
	protected void fireConnectionEvent(UChannel channel) {
		for (CommunicateListener<UChannel> listener : listeners) {
			listener.connection(channel);
		}
	}

	/**
	 * <b>触发断开连接事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 */
	protected void fireDisconnectionEvent(UChannel channel) {
		for (CommunicateListener<UChannel> listener : listeners) {
			listener.disconnection(channel);
		}
	}

	/**
	 * <b>触发错误捕获事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param e 异常信息
	 */
	protected void fireExceptionCaughtEvent(UChannel channel, Throwable e) {
		for (CommunicateListener<UChannel> listener : listeners) {
			listener.exceptionCaught(channel, e);
		}
	}

	/**
	 * <b>触发报文接收事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param channel 通道
	 * @param data 接收到的数据
	 * @param apply Function,可以为null
	 */
	protected <T> void fireReceiveDataEvent(UChannel channel, T data, Function<T, Object> apply) {
		for (CommunicateListener<UChannel> listener : listeners) {
			listener.receiveData(channel, apply == null ? data : apply.call(data));
		}
	}

	/**
	 * <b>触发报文发送事件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 报文发送完成后触发。
	 * @param channel 通道
	 * @param data 已经发送出去的数据
	 * @param success 发送是否成功
	 * @param apply Function,可以为null
	 */
	protected <T> void fireSendDataEvent(UChannel channel, T data, boolean success, Function<T, Object> apply) {
		for (CommunicateListener<UChannel> listener : listeners) {
			listener.sendData(channel, apply == null ? data : apply.call(data), success);
		}
	}

	/** 
	 * destroy。
	 * @see org.quincy.rock.comm.communicate.Communicator#destroy()
	 */
	@Override
	public void destroy() {
		this.removeAllCommunicateListener();
	}

}
