package org.quincy.rock.message.listener;

import org.quincy.rock.comm.communicate.CommunicateListener;
import org.quincy.rock.comm.communicate.IChannel;
import org.quincy.rock.message._Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>数据原始报文记录器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 使用该记录器监听通讯器接收报文信息。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月21日 下午4:05:50</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class OriginalMesssageRecorder implements CommunicateListener<IChannel> {
	/**
	 * logger。
	 */
	protected static final Logger logger = LoggerFactory.getLogger("rock.message.connection.log");
	/**
	 * logger4Receive。
	 */
	protected static final Logger logger4Receive = LoggerFactory.getLogger("rock.message.original.receive.log");
	/**
	 * logger4Send。
	 */
	protected static final Logger logger4Send = LoggerFactory.getLogger("rock.message.original.send.log");

	/**
	 * <b>将原始报文数据转换成十六进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 原始报文数据
	 * @return 十六进制字符串
	 */
	protected abstract String toHexString(Object data);

	/** 
	 * receiveData。
	 * @see org.quincy.rock.comm.communicator.CommunicateListener#receiveData(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void receiveData(IChannel channel, Object data) {
		if (logger4Receive.isInfoEnabled()) {
			try {
				logger4Receive.info("[{}]:{}", channel.addressInfo(), toHexString(data));
			} catch (Exception e) {
				logger4Receive.error("receiveData error!", e);
			}
		}
	}

	/** 
	 * sendData。
	 * @see org.quincy.rock.comm.communicator.CommunicateListener#sendData(java.lang.Object, java.lang.Object, boolean)
	 */
	@Override
	public void sendData(IChannel channel, Object data, boolean success) {
		if (logger4Receive.isInfoEnabled()) {
			try {
				logger4Send.info("[{}]:{}:{}", channel.addressInfo(), toHexString(data),
						success ? "success" : "failure");
			} catch (Exception e) {
				logger4Send.error("sendData error!", e);
			}
		}
	}

	/** 
	 * connection。
	 * @see org.quincy.rock.comm.communicator.CommunicateListener#connection(java.lang.Object)
	 */
	@Override
	public void connection(IChannel channel) {
		if (logger.isDebugEnabled()) {
			try {
				String msg = _Utils.MSA.getMessage("log.connection", new Object[] { channel.addressInfo() });
				logger.debug(msg);
			} catch (Exception ex) {
				logger.error("connection error!", ex);
			}
		}
	}

	/** 
	 * disconnection。
	 * @see org.quincy.rock.comm.communicator.CommunicateListener#disconnection(java.lang.Object)
	 */
	@Override
	public void disconnection(IChannel channel) {
		if (logger.isDebugEnabled()) {
			try {
				String msg = _Utils.MSA.getMessage("log.disconnection", new Object[] { channel.addressInfo() });
				logger.debug(msg);
			} catch (Exception ex) {
				logger.error("disconnection error!", ex);
			}
		}
	}

	/** 
	 * exceptionCaught。
	 * @see org.quincy.rock.comm.communicator.CommunicateListener#exceptionCaught(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IChannel channel, Throwable e) {
		try {
			String msg = _Utils.MSA.getMessage("log.exceptionCaught", new Object[] { channel.addressInfo() });
			logger.error(msg, e);
		} catch (Exception ex) {
			logger.error("exceptionCaught error!", ex);
		}
	}
}
