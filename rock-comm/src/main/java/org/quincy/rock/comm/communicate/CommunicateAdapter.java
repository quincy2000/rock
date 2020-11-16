package org.quincy.rock.comm.communicate;

/**
 * <b>CommunicateAdapter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月10日 上午12:12:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CommunicateAdapter<UChannel> implements CommunicateListener<UChannel> {

	/** 
	 * connection。
	 * @see org.quincy.rock.comm.communicate.CommunicateListener#connection(java.lang.Object)
	 */
	@Override
	public void connection(UChannel channel) {

	}

	/** 
	 * disconnection。
	 * @see org.quincy.rock.comm.communicate.CommunicateListener#disconnection(java.lang.Object)
	 */
	@Override
	public void disconnection(UChannel channel) {

	}

	/** 
	 * receiveData。
	 * @see org.quincy.rock.comm.communicate.CommunicateListener#receiveData(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void receiveData(UChannel channel, Object data) {

	}

	/** 
	 * sendData。
	 * @see org.quincy.rock.comm.communicate.CommunicateListener#sendData(java.lang.Object, java.lang.Object, boolean)
	 */
	@Override
	public void sendData(UChannel channel, Object data, boolean success) {

	}

	/** 
	 * exceptionCaught。
	 * @see org.quincy.rock.comm.communicate.CommunicateListener#exceptionCaught(java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(UChannel channel, Throwable e) {

	}

}
