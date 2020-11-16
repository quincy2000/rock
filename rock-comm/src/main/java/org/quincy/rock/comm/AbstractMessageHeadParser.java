package org.quincy.rock.comm;

import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>报文头解析器抽象基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 上午10:29:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class AbstractMessageHeadParser<M, V> implements MessageHeadParser<M, V> {
	/**
	 * 缺省的报文振文格式类型。
	 */
	private String defaultContentType;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultContentType 缺省的报文振文格式类型
	 */
	public AbstractMessageHeadParser(String defaultContentType) {
		this.defaultContentType = defaultContentType;
	}

	/** 
	 * getDefaultContentType。
	 * @see org.quincy.rock.comm.MessageHeadParser#getDefaultContentType()
	 */
	@Override
	public final String getDefaultContentType() {
		return defaultContentType;
	}

	/**
	 * <b>设置缺省的报文振文格式类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param defaultContentType 缺省的报文振文格式类型
	 */
	public void setDefaultContentType(String defaultContentType) {
		this.defaultContentType = defaultContentType;
	}

	/**
	 * <b>确保返回可用的内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 * @return 确保返回可用的内容类型
	 */
	protected String ensureContentType(Object contentType) {
		if (contentType == null) {
			return StringUtil.isBlank(defaultContentType) ? CommUtils.MESSAGE_TYPE_BINARY : defaultContentType;
		} else
			return contentType.toString();
	}
}
