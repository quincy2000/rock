package org.quincy.rock.comm.parser;

/**
 * <b>响应报文。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 仅仅用于处理响应代码为字节的情况。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月21日 下午1:44:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class ResultMessage<BUF, R> extends Message<BUF> {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 2773075669198507543L;

	/**
	 * 返回的结果代码。
	 */
	private R result;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public ResultMessage() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 返回的结果代码
	 */
	public ResultMessage(R result) {
		this.result = result;
	}

	/**
	 * <b>获得结果代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 结果代码
	 */
	public R getResult() {
		return result;
	}

	/**
	 * <b>设置结果代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param result 结果代码
	 */
	public void setResult(R result) {
		this.result = result;
	}

	/** 
	 * toString。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append("{");
		sb.append("result:");
		sb.append(getResult());
		sb.append("}");
		return sb.toString();
	}
}
