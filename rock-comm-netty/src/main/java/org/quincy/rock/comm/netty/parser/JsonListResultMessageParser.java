package org.quincy.rock.comm.netty.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <b>json响应报文(嵌套数组报文)解析器基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 提供CasingListResultMessage报文解析支持。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月14日 下午4:44:16</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class JsonListResultMessageParser<V extends Message>
		extends JsonMessageParser<CasingListResultMessage<V>> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 默认内容类型为CommUtils.MESSAGE_TYPE_JSON。
	 */
	public JsonListResultMessageParser() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public JsonListResultMessageParser(Collection<String> contentType) {
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
	public JsonListResultMessageParser(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/** 
	 * fromJson。
	 * @see com.tonsel.togt.comm.codec.JsonMessageParser#fromJson(java.lang.String, java.util.Map)
	 */
	@Override
	public CasingListResultMessage<V> fromJson(String message, Map<String, Object> ctx) {
		int leftPos = message.indexOf("[", 4);
		int rigthPos = message.lastIndexOf(']', message.length() - 2);
		String json = message.substring(leftPos, rigthPos + 1);
		Class<? extends Message> clazz = getMessageClass();
		List<?> list = clazz == null ? Collections.EMPTY_LIST : jsonConverter.fromJsonArray(json, clazz);
		//
		json = message.substring(0, leftPos) + "null" + message.substring(rigthPos + 1, message.length());
		CasingListResultMessage resultMessage = jsonConverter.fromJson(json, CasingListResultMessage.class);
		resultMessage.setData(list);
		return resultMessage;
	}
}
