package org.quincy.rock.comm.netty.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.quincy.rock.core.util.StringUtil;

/**
 * <b>json嵌套数组报文解析器基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 提供CasingListMessage报文解析支持。
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
public abstract class JsonListMessageParser<V extends Message> extends JsonMessageParser<CasingListMessage<V>> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 默认内容类型为CommUtils.MESSAGE_TYPE_JSON。
	 */
	public JsonListMessageParser() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public JsonListMessageParser(Collection<String> contentType) {
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
	public JsonListMessageParser(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}

	/** 
	 * fromJson。
	 * @see org.quincy.rock.comm.netty.parser.JsonMessageParser#fromJson(java.lang.String, java.util.Map)
	 */
	@Override
	public CasingListMessage<V> fromJson(String message, Map<String, Object> ctx) {
		Class<? extends Message> clazz = getMessageClass();
		List<?> list = clazz == null ? Collections.EMPTY_LIST : jsonConverter.fromJsonArray(message, clazz);
		//		
		CasingListMessage clm = new CasingListMessage();
		clm.setData(list);
		return clm;
	}

	/** 
	 * toJson。
	 * @see org.quincy.rock.comm.netty.parser.JsonMessageParser#toJson(org.quincy.rock.comm.netty.parser.Message, java.util.Map)
	 */
	@Override
	public String toJson(CasingListMessage<V> value, Map<String, Object> ctx) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		List<V> list = value.getData();
		for (int i = 0, l = list.size(); i < l; i++) {
			if (i != 0)
				sb.append(StringUtil.CHAR_COMMA);
			sb.append(jsonConverter.toJson(list.get(i)));
		}
		sb.append(']');
		return sb.toString();
	}
}
