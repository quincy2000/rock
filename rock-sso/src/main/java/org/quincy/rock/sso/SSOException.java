package org.quincy.rock.sso;

import org.quincy.rock.core.exception.RockException;

/**
 * <b>SSOException。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月7日 下午12:20:38</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOException extends RockException {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	public SSOException() {
		super();		
	}

	public SSOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public SSOException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSOException(String message) {
		super(message);
	}

	public SSOException(Throwable cause) {
		super(cause);
	}
}
