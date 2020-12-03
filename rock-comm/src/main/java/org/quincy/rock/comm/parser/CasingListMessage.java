package org.quincy.rock.comm.parser;

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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CasingListMessage<BUF> extends Message<BUF> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3149374594542222402L;

	/**
	 * 返回的结果数据。
	 */
	private List<Message<BUF>> data;

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
	public CasingListMessage(List<? extends Message<BUF>> data) {
		super();
		this.data = (List) data;
	}

	/**
	 * <b>返回的结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 返回的结果数据
	 */
	public List<Message<BUF>> getData() {
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
	public void setData(List<? extends Message<BUF>> data) {
		this.data = (List) data;
	}

	/**
	 * <b>添加结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 结果数据
	 */
	public void addData(Message<BUF> data) {
		this.getData().add(data);
	}

	/** 
	 * toBinary。
	 * @see org.quincy.rock.comm.parser.Message#toBinary(java.lang.Object, java.util.Map)
	 */
	@Override
	public BUF toBinary(BUF buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		return buf;
	}

	/** 
	 * fromBinary。
	 * @see org.quincy.rock.comm.parser.Message#fromBinary(java.lang.Object, java.util.Map)
	 */
	@Override
	public Message<BUF> fromBinary(BUF buf, Map<String, Object> ctx) {
		this.initializeOnce(ctx);
		return this;
	}
}
