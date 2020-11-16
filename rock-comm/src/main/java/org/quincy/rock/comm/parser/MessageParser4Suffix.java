package org.quincy.rock.comm.parser;

import java.util.Collection;

import org.quincy.rock.comm.AbstractMessageParser;

/**
 * <b>通过类名后缀来标识功能码的报文解析类。</b>
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
public abstract class MessageParser4Suffix<K, M, V> extends AbstractMessageParser<K, M, V> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public MessageParser4Suffix(Collection<String> contentType) {
		super(contentType);
		K key = this.parseFunctionCodeFromSuffix();
		if (key != null)
			this.addFunctionCode(key);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public MessageParser4Suffix(Collection<K> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/**
	 * <b>从类名后缀解析功能码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 功能码
	 */
	protected abstract K parseFunctionCodeFromSuffix();
}
