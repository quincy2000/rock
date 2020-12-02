package org.quincy.rock.comm.parser;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>嵌套数组数据的报文。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月21日 下午1:55:58</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public final class CasingListMessage<T extends Message> extends Message {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3149374594542222402L;

	/**
	 * 返回的结果数据。
	 */
	private List<T> data;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public CasingListMessage() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 返回的结果数据
	 */
	public CasingListMessage(List<T> data) {
		super();
		this.data = data;
	}

	/**
	 * <b>返回的结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 返回的结果数据
	 */
	public List<T> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	/**
	 * <b>设置返回的结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 返回的结果数据
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * <b>添加结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 结果数据
	 */
	public void addData(T data) {
		this.getData().add(data);
	}

	/** 
	 * toBinary。
	 * @see org.quincy.rock.comm.parser.Message#toBinary(java.nio.ByteBuffer, java.util.Map)
	 */
	@Override
	public ByteBuffer toBinary(ByteBuffer buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		return buf;
	}

	/** 
	 * fromBinary。
	 * @see org.quincy.rock.comm.parser.Message#fromBinary(java.nio.ByteBuffer, java.util.Map)
	 */
	@Override
	public Message fromBinary(ByteBuffer buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		return this;
	}
}
