package org.quincy.rock.message.vo;

import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>终端信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月14日 下午12:41:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class TerminalInfo extends Vo<String> {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1231712540650425681L;

	/**
	 * 终端id。
	 */
	private String id;
	/**
	 * 终端类型。
	 */
	private String type;
	/**
	 * 终端编号。
	 */
	private String code;
	/**
	 * 终端地址。
	 */
	private String address;
	/**
	 * 备注说明。
	 */
	private String descr;
	/**
	 * 内容类型。
	 */
	private String contentType;
	/**
	 * 上线时间。
	 */
	private long createTime;
	/**
	 * 最后一次通讯时间。
	 */
	private long lastAccessTime;
	/**
	 * 终端通讯协议版本。
	 */
	private String protocolVer;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public TerminalInfo() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param term 终端对象
	 */
	public TerminalInfo(TerminalId<?, ?> term) {
		if (term != null) {
			this.id(term.id());
			this.setType(CoreUtil.toString(term.getType()));
			this.setCode(CoreUtil.toString(term.getCode()));
			this.setAddress(term.getAddress());
			this.setDescr(term.getDescr());
		}
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch 终端通道
	 */
	public TerminalInfo(TerminalChannel<?, ?> ch) {
		if (ch != null) {
			TerminalId<?, ?> term = ch.remote();
			this.id(term.id());
			this.setType(CoreUtil.toString(term.getType()));
			this.setCode(CoreUtil.toString(term.getCode()));
			this.setAddress(term.getAddress());
			this.setDescr(term.getDescr());
			this.setContentType(ch.contentType());
			this.setCreateTime(ch.timestamp());
			this.setLastAccessTime(ch.lastAccessTime());
			this.setProtocolVer(ch.protocolVer());
		}
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return id;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id(java.lang.Object)
	 */
	@Override
	public Vo<String> id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * <b>获得终端类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * <b>设置终端类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * <b>获得终端编号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * <b>设置终端编号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 终端编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * <b>获得终端地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <b>设置终端地址。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param address 终端地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * <b>获得备注说明。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 备注说明
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * <b>设置备注说明。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param descr 备注说明
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * <b>获得内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 内容类型
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * <b>设置内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * <b>获得上线时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 上线时间
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * <b>设置上线时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param createTime 上线时间
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * <b>获得最后一次通讯时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最后一次通讯时间
	 */
	public long getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * <b>设置最后一次通讯时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param lastAccessTime 最后一次通讯时间
	 */
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * <b>获得终端通讯协议版本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端通讯协议版本
	 */
	public String getProtocolVer() {
		return protocolVer;
	}

	/**
	 * <b>设置终端通讯协议版本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param protocolVer 终端通讯协议版本
	 */
	public void setProtocolVer(String protocolVer) {
		this.protocolVer = protocolVer;
	}
}
