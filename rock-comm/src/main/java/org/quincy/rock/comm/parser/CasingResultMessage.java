package org.quincy.rock.comm.parser;

/**
 * <b>嵌套响应数据的结果报文。</b>
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
public final class CasingResultMessage<T extends Message> extends ResultMessage {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 8717399528657625234L;

	/**
	 * 返回的结果数据。
	 */
	private T data;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public CasingResultMessage() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 返回的结果代码
	 * @param data 返回的结果数据
	 */
	public CasingResultMessage(byte result, T data) {
		super(result);
		this.data = data;
	}

	/**
	 * <b>返回的结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 返回的结果数据
	 */
	public T getData() {
		return data;
	}

	/**
	 * <b>设置返回的结果数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 返回的结果数据
	 */
	public void setData(T data) {
		this.data = data;
	}
}
