package org.quincy.rock.comm.communicate;

/**
 * <b>AbstractTerminalChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月3日 下午2:51:52</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractTerminalChannel<TYPE, CODE> extends AbstractChannel
		implements TerminalChannel<TYPE, CODE> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 2101214543930994745L;

	/**
	 * 本地终端。
	 */
	private TerminalId<TYPE, CODE> localTerm;
	/**
	 * 远程终端。
	 */
	private TerminalId<TYPE, CODE> remoteTerm;

	/**
	 * <b>创建本地终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 本地终端
	 */
	protected abstract TerminalId<TYPE, CODE> createLocal();

	/**
	 * <b>创建远程终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 远程终端
	 */
	protected abstract TerminalId<TYPE, CODE> createRemote();

	/** 
	 * local。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#local()
	 */
	@Override
	public TerminalId<TYPE, CODE> local() {
		if (localTerm == null)
			localTerm = createLocal();
		return localTerm;
	}

	/** 
	 * remote。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#remote()
	 */
	@Override
	public TerminalId<TYPE, CODE> remote() {
		if (remoteTerm == null)
			remoteTerm = createRemote();
		return remoteTerm;
	}

	/** 
	 * setLocalType。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#setLocalType(java.lang.Object)
	 */
	@Override
	public void setLocalType(TYPE type) {
		local().setType(type);
	}

	/** 
	 * getLocalType。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#getLocalType()
	 */
	@Override
	public TYPE getLocalType() {
		return local().getType();
	}

	/** 
	 * setLocalCode。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#setLocalCode(java.lang.Object)
	 */
	@Override
	public void setLocalCode(CODE code) {
		local().setCode(code);
	}

	/** 
	 * getLocalCode。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#getLocalCode()
	 */
	@Override
	public CODE getLocalCode() {
		return local().getCode();
	}

	/** 
	 * setRemoteType。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#setRemoteType(java.lang.Object)
	 */
	@Override
	public void setRemoteType(TYPE type) {
		remote().setType(type);
	}

	/**
	 * getRemoteType。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#getRemoteType()
	 */
	@Override
	public TYPE getRemoteType() {
		return remote().getType();
	}

	/** 
	 * setRemoteCode。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#setRemoteCode(java.lang.Object)
	 */
	@Override
	public void setRemoteCode(CODE code) {
		remote().setCode(code);
	}

	/** 
	 * getRemoteCode。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#getRemoteCode()
	 */
	@Override
	public CODE getRemoteCode() {
		return remote().getCode();
	}

	/** 
	 * toServer。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#toServer()
	 */
	@Override
	public boolean toServer() {
		TerminalId<TYPE, CODE> term = isSendChannel() ? this.remote() : this.local();
		return TerminalId.isServer(term);
	}

	/** 
	 * fromServer。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#fromServer()
	 */
	@Override
	public boolean fromServer() {
		TerminalId<TYPE, CODE> term = isSendChannel() ? this.local() : this.remote();
		return TerminalId.isServer(term);
	}

	/** 
	 * clone。
	 * @see org.quincy.rock.comm.communicate.AbstractChannel#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		AbstractTerminalChannel vo = (AbstractTerminalChannel) super.clone();
		if (remoteTerm != null)
			vo.remoteTerm = remoteTerm.cloneMe();
		if (localTerm != null)
			vo.localTerm = localTerm.cloneMe();
		return vo;
	}

	/** 
	 * newSendChannel。
	 * @see org.quincy.rock.comm.communicate.AbstractChannel#newSendChannel(org.quincy.rock.comm.communicate.Adviser)
	 */
	@Override
	public <T extends IChannel> T newSendChannel(Adviser adviser) {
		TerminalChannel ch = super.newSendChannel(adviser);
		if (adviser instanceof TerminalId) {
			ch.remote().advise((TerminalId) adviser);
		} else if (adviser instanceof TerminalChannel) {
			ch.remote().advise(((TerminalChannel) adviser).remote());
		}
		return (T) ch;
	}

	/** 
	 * channelId。
	 * @see org.quincy.rock.comm.communicate.IChannel#channelId()
	 */
	@Override
	public Object channelId() {
		//使用远程终端作为通道id
		return remote();
	}
}
