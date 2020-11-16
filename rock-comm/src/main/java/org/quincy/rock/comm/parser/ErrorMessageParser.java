package org.quincy.rock.comm.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.quincy.rock.comm.AbstractMessageParser;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.lang.Recorder;

/**
 * <b>ErrorMessageParser。</b>
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErrorMessageParser<K> extends AbstractMessageParser<K, Object, Object> {
	/**
	 * INSTANCE。
	 */
	public static final ErrorMessageParser INSTANCE = new ErrorMessageParser<>();

	/**
	 * 日志记录器。
	 */
	private Recorder recorder = Recorder.EMPTY;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public ErrorMessageParser() {
		this(Collections.EMPTY_LIST);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 */
	public ErrorMessageParser(Collection<K> functionCode) {
		super(functionCode, Arrays.asList(CommUtils.MESSAGE_TYPE_ALL));
	}

	/**
	 * <b>获得日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 日志记录器
	 */
	public Recorder getRecorder() {
		return recorder;
	}

	/**
	 * <b>设置日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder 日志记录器
	 */
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	/** 
	 * pack。
	 * @see org.quincy.rock.comm.MessageParser#pack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Object pack(Object value, Map<String, Object> ctx) {
		throwException(ctx);
		return value;
	}

	/** 
	 * unpack。
	 * @see org.quincy.rock.comm.MessageParser#unpack(java.lang.Object, java.util.Map)
	 */
	@Override
	public Object unpack(Object message, Map<String, Object> ctx) {
		throwException(ctx);
		return message;
	}

	private void throwException(Map<String, Object> ctx) {
		StringBuilder sb = new StringBuilder("Could not find MessageParser");
		sb.append(ctx.get(CommUtils.COMM_FUNCTION_CODE_KEY));
		sb.append(" for [");
		sb.append(ctx.get(CommUtils.COMM_MSG_TYPE_KEY));
		sb.append("].");
		recorder.write(sb);
		throw new NotFoundException(sb.toString());
	}

	/**
	 * <b>创建一个ErrorMessageParser。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder 记录器
	 * @param codes 功能代码
	 * @return ErrorMessageParser
	 */
	public static <K> ErrorMessageParser<K> of(Recorder recorder, K... codes) {
		ErrorMessageParser parser = new ErrorMessageParser<>(Arrays.asList(codes));
		parser.setRecorder(recorder);
		return parser;
	}
}
