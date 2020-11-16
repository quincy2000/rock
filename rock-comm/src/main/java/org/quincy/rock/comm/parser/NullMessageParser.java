package org.quincy.rock.comm.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.quincy.rock.comm.AbstractMessageParser;
import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>NullMessageParser。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2011-11-12 下午10:28:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class NullMessageParser<K> extends AbstractMessageParser<K, Object, Object> {

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public NullMessageParser() {
		this(Collections.EMPTY_LIST);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public NullMessageParser(Collection<K> functionCode) {
		super(functionCode, Arrays.asList(CommUtils.MESSAGE_TYPE_ALL));
	}

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Object pack(Object value, Map<String, Object> ctx) {
		ctx.put(CommUtils.COMM_MSG_TYPE_KEY, CommUtils.MESSAGE_TYPE_NONE);
		return null;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Object unpack(Object message, Map<String, Object> ctx) {
		ctx.put(CommUtils.COMM_MSG_TYPE_KEY, CommUtils.MESSAGE_TYPE_NONE);
		return null;
	}
}
