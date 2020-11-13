package org.quincy.rock.core.exception;

/**
 * <b>不支持异常。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月18日 下午3:50:03</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class UnsupportException extends RockException {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportException() {
		super();
	}

	public UnsupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportException(String message) {
		super(message);
	}

	public UnsupportException(Throwable cause) {
		super(cause);
	}
}
