package org.quincy.rock.comm.netty.parser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.quincy.rock.comm.parser.MessageParser4Suffix;
import org.quincy.rock.comm.util.CommUtils;
import org.quincy.rock.core.lang.Recorder;

import io.netty.buffer.ByteBuf;

/**
 * <b>报文解析器通用基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月22日 上午11:58:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class MessageParserBase<V extends Message> extends MessageParser4Suffix<Integer, ByteBuf, V> {
	/**
	 * recorder。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public MessageParserBase(Collection<String> contentType) {
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
	public MessageParserBase(Collection<Integer> functionCode, Collection<String> contentType) {
		super(functionCode, contentType);
	}
	
	/**
	 * <b>getRecorder。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public Recorder getRecorder() {
		return recorder;
	}

	/**
	 * <b>setRecorder。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder
	 */
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
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

	/**
	 * <b>获得报文实体类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文实体类型
	 */
	protected Class<? extends Message> getMessageClass() {
		if (_voClass == null) {
			ParameterizedType type = getParameterizedType(this.getClass());
			if (type != null) {
				_voClass = (Class<? extends Message>) type.getActualTypeArguments()[0];
			}
		}
		return _voClass;
	}

	//
	private ParameterizedType getParameterizedType(Class<?> clazz) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType)
			return (ParameterizedType) type;
		else if (type instanceof Class)
			return getParameterizedType(clazz.getSuperclass());
		else
			return null;
	}

	private transient Class<? extends Message> _voClass;
}
