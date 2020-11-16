package org.quincy.rock.comm.parser;

import java.util.Collection;

import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>通过数字后缀来标识功能码的报文解析类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月21日 下午11:47:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MessageParser4Integer<M, V> extends MessageParser4Suffix<Integer, M, V> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public MessageParser4Integer(Collection<String> contentType) {
		super(contentType);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public MessageParser4Integer(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/** 
	 * parseFunctionCodeFromSuffix。
	 * @see org.quincy.rock.comm.parser.MessageParser4Suffix#parseFunctionCodeFromSuffix()
	 */
	@Override
	protected Integer parseFunctionCodeFromSuffix() {
		String key = CommUtils.parseSuffix(this.getClass(), true);
		return key == null ? null : Integer.valueOf(key);
	}
}
