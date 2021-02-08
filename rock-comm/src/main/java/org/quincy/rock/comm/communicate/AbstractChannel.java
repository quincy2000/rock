package org.quincy.rock.comm.communicate;

import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>AbstractChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年2月26日 上午9:32:47</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class AbstractChannel extends Vo<Object> implements IChannel {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3817874966391215744L;

	/**
	 * 时间戳。
	 */
	private long timestamp = System.currentTimeMillis();
	/**
	 * 最后一次访问时间。
	 */
	private long lastAccessTime = timestamp;

	/**
	 * 是否是发送通道。
	 */
	protected boolean sendChannel;
	/**
	 * 是请求还是响应。
	 */
	private boolean request = true;
	/**
	 * 是否是服务器端。
	 */
	private boolean serverSide;
	/**
	 * 内容类型。
	 */
	private String contentType;
	/**
	 * 报文协议版本。
	 */
	private String protocolVer;

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Object id() {
		return channelId();
	}

	/** 
	 * isValidChannel。
	 * @see org.quincy.rock.comm.communicate.IChannel#isValidChannel()
	 */
	@Override
	public boolean isValidChannel() {
		return channelId() != null;
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.concurrent.HasTimestamp#timestamp()
	 */
	@Override
	public final long timestamp() {
		return timestamp;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	/** 
	 * lastAccessTime。
	 * @see org.quincy.rock.core.cache.HasAccessTime#lastAccessTime()
	 */
	@Override
	public final long lastAccessTime() {
		return lastAccessTime > timestamp ? lastAccessTime : timestamp;
	}

	/** 
	 * updateAccessTime。
	 * @see org.quincy.rock.core.cache.HasAccessTime#updateAccessTime()
	 */
	@Override
	public final void updateAccessTime() {
		lastAccessTime = System.currentTimeMillis();
	}

	/** 
	 * isPattern。
	 * @see org.quincy.rock.core.util.HasPattern#isPattern()
	 */
	@Override
	public boolean isPattern() {
		return false;
	}

	/** 
	 * isMatched。
	 * @see org.quincy.rock.core.util.HasPattern#isMatched(java.lang.Object)
	 */
	@Override
	public boolean isMatched(Object obj) {
		return false;
	}

	/**
	 * <b>设置内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	protected final void contentType(String contentType) {
		this.contentType = contentType;
	}

	/** 
	 * contentType。
	 * @see org.quincy.rock.comm.communicate.IChannel#contentType()
	 */
	@Override
	public final String contentType() {
		return contentType;
	}

	/**
	 * <b>设置报文协议版本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param protocolVer 报文协议版本
	 */
	protected final void protocolVer(String protocolVer) {
		this.protocolVer = protocolVer;
	}

	/** 
	 * protocolVer。
	 * @see org.quincy.rock.comm.communicate.IChannel#protocolVer()
	 */
	@Override
	public String protocolVer() {
		return protocolVer;
	}

	/** 
	 * isSendChannel。
	 * @see org.quincy.rock.comm.communicate.IChannel#isSendChannel()
	 */
	@Override
	public final boolean isSendChannel() {
		return sendChannel;
	}

	/** 
	 * isRequest。
	 * @see org.quincy.rock.comm.communicate.IChannel#isRequest()
	 */
	public final boolean isRequest() {
		return request;
	}

	/** 
	 * setRequest。
	 * @see org.quincy.rock.comm.communicate.IChannel#setRequest(boolean)
	 */
	public final void setRequest(boolean request) {
		this.request = request;
	}

	/**
	 * <b>是否是服务器端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是服务器端
	 */
	public final boolean isServerSide() {
		return serverSide;
	}

	/**
	 * <b>是否是服务器端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param serverSide 是否是服务器端
	 */
	public final void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}

	/** 
	 * newSendChannel。
	 * @see org.quincy.rock.comm.communicate.IChannel#newSendChannel()
	 */
	@Override
	public <T extends IChannel> T newSendChannel() {
		return newSendChannel(null);
	}

	/** 
	 * newSendChannel。
	 * @see org.quincy.rock.comm.communicate.IChannel#newSendChannel(org.quincy.rock.comm.communicate.Adviser)
	 */
	@Override
	public <T extends IChannel> T newSendChannel(Adviser adviser) {
		AbstractChannel ch = this.cloneMe();
		ch.sendChannel = true;
		return (T) ch;
	}

	/** 
	 * clone。
	 * @see org.quincy.rock.core.vo.Vo#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		AbstractChannel ch = (AbstractChannel) super.clone();
		ch.timestamp = System.currentTimeMillis();
		return ch;
	}

	/** 
	 * addressInfo。
	 * @see org.quincy.rock.comm.communicate.IChannel#addressInfo()
	 */
	@Override
	public String addressInfo() {
		return CoreUtil.toString(id());
	}

}
