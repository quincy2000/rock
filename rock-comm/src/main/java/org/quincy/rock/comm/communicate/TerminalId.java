package org.quincy.rock.comm.communicate;

import org.quincy.rock.core.exception.UnsupportException;
import org.quincy.rock.core.util.HasPattern;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.Vo;

/**
 * <b>业务终端唯一id。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 终端Id类。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月2日 下午10:18:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TerminalId<TYPE, CODE> extends Vo<String> implements Adviser, HasPattern {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -4918664966490567358L;
	/**
	 * 终端地址。
	 */
	private String address;
	/**
	 * 类型。
	 */
	private TYPE type;
	/**
	 * 唯一编码。
	 */
	private CODE code;
	/**
	 * 描述。
	 */
	private String descr;
	/**
	 * tag。
	 */
	private Object tag;
	/**
	 * 是否是服务器。
	 */
	private boolean server;

	/**
	 * 唯一id。
	 */
	private transient String id;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public TerminalId() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param server 是否是服务器
	 */
	public TerminalId(boolean server) {
		this.server = server;
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
	 * <b>获得类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 类型
	 */
	public TYPE getType() {
		return type;
	}

	/**
	 * <b>设置类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 类型
	 */
	public void setType(TYPE type) {
		this.type = type;
		this.clearId();
	}

	/**
	 * <b>获得唯一编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 唯一编码
	 */
	public CODE getCode() {
		return code;
	}

	/**
	 * <b>设置唯一编码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 唯一编码
	 */
	public void setCode(CODE code) {
		this.code = code;
		this.clearId();
	}

	/**
	 * <b>获得描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 描述
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * <b>设置描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param descr 描述
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * <b>getTag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return tag
	 */
	public Object getTag() {
		return tag;
	}

	/**
	 * <b>setTag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tag tag
	 */
	public void setTag(Object tag) {
		this.tag = tag;
	}

	/**
	 * <b>是否是服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是服务器
	 */
	public boolean isServer() {
		return server;
	}

	/**
	 * <b>建议。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param adviser 建议示例终端,不能为null
	 * @param force 是否强制接受全部建议 
	 */
	public void advise(TerminalId<TYPE, CODE> adviser) {
		if (isServer() != adviser.isServer())
			throw new UnsupportException();
		if (adviser.type != null)
			this.type = adviser.type;
		if (adviser.code != null)
			this.code = adviser.code;
		if (adviser.address != null)
			this.address = adviser.address;
		if (adviser.tag != null)
			this.tag = adviser.tag;
		if (adviser.descr != null)
			this.descr = adviser.descr;
		this.clearId();
	}

	/**
	 * <b>清除id缓存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	protected void clearId() {
		this.id = null;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		if (id == null) {
			StringBuilder sb = new StringBuilder(server ? "sid" : "cid");
			sb.append(StringUtil.CHAR_UNDERLINE);
			if (type != null)
				sb.append(type);
			sb.append(StringUtil.CHAR_UNDERLINE);
			if (code != null)
				sb.append(code);
			sb.append(StringUtil.CHAR_UNDERLINE);
			id = sb.toString();
		}
		return id;
	}

	/** 
	 * loose。
	 * @see org.quincy.rock.core.vo.Vo#loose()
	 */
	@Override
	public boolean loose() {
		return true;
	}

	/** 
	 * isPattern。
	 * @see org.quincy.rock.core.util.HasPattern#isPattern()
	 */
	@Override
	public boolean isPattern() {
		return code == null;
	}

	/** 
	 * isMatched。
	 * @see org.quincy.rock.core.util.HasPattern#isMatched(java.lang.Object)
	 */
	@Override
	public boolean isMatched(Object obj) {
		if (obj instanceof TerminalId) {
			TerminalId<TYPE, CODE> term = (TerminalId) obj;
			TYPE type = getType();
			if (isPattern()) {
				return type == null || type.equals(term.getType());
			} else if (type == null) {
				return term.getType() == null && getCode().equals(term.getCode());
			} else {
				return type.equals(term.getType()) && getCode().equals(term.getCode());
			}
		} else if (obj instanceof TerminalChannel) {
			return isMatched(((TerminalChannel) obj).remote());
		} else
			return false;
	}

	/** 
	 * clone。
	 * @see org.quincy.rock.core.vo.Vo#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		TerminalId term = (TerminalId) super.clone();
		term.clearId();
		return term;
	}

	/** 
	 * toString。
	 * @see org.quincy.rock.core.vo.Vo#toString()
	 */
	@Override
	public String toString() {
		String id = id();
		return id == null ? "all" : id;
	}

	/**
	 * <b>终端是否是服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param term 终端
	 * @return 终端是否是服务器
	 */
	public static boolean isServer(TerminalId<?, ?> term) {
		return term != null && term.isServer();
	}
}
