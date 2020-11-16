package org.quincy.rock.core;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * <b>HackLogger。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>王昆山</td><td>2014-5-5 下午5:15:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author 王昆山
 * @since 1.0
 */
public class HackLogger implements Logger {

	/**
	 * logger。
	 */
	private Logger logger;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param logger Logger
	 */
	public HackLogger(Logger logger) {
		this.logger = logger;
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(java.lang.String)
	 */
	@Override
	public void debug(String arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug(hack(arg0));
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String arg0, Object arg1) {
		if (logger.isDebugEnabled()) {
			logger.debug(hack(arg0), arg1);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void debug(String arg0, Object... arg1) {
		if (logger.isDebugEnabled()) {
			logger.debug(hack(arg0), arg1);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String arg0, Throwable arg1) {
		if (logger.isDebugEnabled()) {
			logger.debug(hack(arg0), arg1);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String)
	 */
	@Override
	public void debug(Marker arg0, String arg1) {
		if (logger.isDebugEnabled(arg0)) {
			logger.debug(arg0, hack(arg1));
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
		if (logger.isDebugEnabled()) {
			logger.debug(hack(arg0), arg1, arg2);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(Marker arg0, String arg1, Object arg2) {
		if (logger.isDebugEnabled(arg0)) {
			logger.debug(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void debug(Marker arg0, String arg1, Object... arg2) {
		if (logger.isDebugEnabled(arg0)) {
			logger.debug(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(Marker arg0, String arg1, Throwable arg2) {
		if (logger.isDebugEnabled(arg0)) {
			logger.debug(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * debug。
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		if (logger.isDebugEnabled(arg0)) {
			logger.debug(arg0, hack(arg1), arg2, arg3);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(java.lang.String)
	 */
	@Override
	public void error(String arg0) {
		if (logger.isErrorEnabled()) {
			logger.error(hack(arg0));
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String arg0, Object arg1) {
		if (logger.isErrorEnabled()) {
			logger.error(hack(arg0), arg1);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void error(String arg0, Object... arg1) {
		if (logger.isErrorEnabled()) {
			logger.error(hack(arg0), arg1);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String arg0, Throwable arg1) {
		if (logger.isErrorEnabled()) {
			logger.error(hack(arg0), arg1);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String)
	 */
	@Override
	public void error(Marker arg0, String arg1) {
		if (logger.isErrorEnabled()) {
			logger.error(arg0, hack(arg1));
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void error(String arg0, Object arg1, Object arg2) {
		if (logger.isErrorEnabled()) {
			logger.error(hack(arg0), arg1, arg2);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(Marker arg0, String arg1, Object arg2) {
		if (logger.isErrorEnabled()) {
			logger.error(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void error(Marker arg0, String arg1, Object... arg2) {
		if (logger.isErrorEnabled()) {
			logger.error(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(Marker arg0, String arg1, Throwable arg2) {
		if (logger.isErrorEnabled()) {
			logger.error(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * error。
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		if (logger.isErrorEnabled()) {
			logger.error(arg0, hack(arg1), arg2, arg3);
		}
	}

	/** 
	 * getName。
	 * @see org.slf4j.Logger#getName()
	 */
	@Override
	public String getName() {
		return logger.getName();
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(java.lang.String)
	 */
	@Override
	public void info(String arg0) {
		if (logger.isInfoEnabled()) {
			logger.info(hack(arg0));
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(String arg0, Object arg1) {
		if (logger.isInfoEnabled()) {
			logger.info(hack(arg0), arg1);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void info(String arg0, Object... arg1) {
		if (logger.isInfoEnabled()) {
			logger.info(hack(arg0), arg1);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void info(String arg0, Throwable arg1) {
		if (logger.isInfoEnabled()) {
			logger.info(hack(arg0), arg1);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String)
	 */
	@Override
	public void info(Marker arg0, String arg1) {
		if (logger.isInfoEnabled()) {
			logger.info(arg0, hack(arg1));
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void info(String arg0, Object arg1, Object arg2) {
		if (logger.isInfoEnabled()) {
			logger.info(hack(arg0), arg1, arg2);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(Marker arg0, String arg1, Object arg2) {
		if (logger.isInfoEnabled()) {
			logger.info(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void info(Marker arg0, String arg1, Object... arg2) {
		if (logger.isInfoEnabled()) {
			logger.info(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void info(Marker arg0, String arg1, Throwable arg2) {
		if (logger.isInfoEnabled()) {
			logger.info(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * info。
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		if (logger.isInfoEnabled()) {
			logger.info(arg0, hack(arg1), arg2, arg3);
		}
	}

	/** 
	 * isDebugEnabled。
	 * @see org.slf4j.Logger#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/** 
	 * isDebugEnabled。
	 * @see org.slf4j.Logger#isDebugEnabled(org.slf4j.Marker)
	 */
	@Override
	public boolean isDebugEnabled(Marker arg0) {
		return logger.isDebugEnabled(arg0);
	}

	/** 
	 * isErrorEnabled。
	 * @see org.slf4j.Logger#isErrorEnabled()
	 */
	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/** 
	 * isErrorEnabled。
	 * @see org.slf4j.Logger#isErrorEnabled(org.slf4j.Marker)
	 */
	@Override
	public boolean isErrorEnabled(Marker arg0) {
		return logger.isErrorEnabled(arg0);
	}

	/** 
	 * isInfoEnabled。
	 * @see org.slf4j.Logger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/** 
	 * isInfoEnabled。
	 * @see org.slf4j.Logger#isInfoEnabled(org.slf4j.Marker)
	 */
	@Override
	public boolean isInfoEnabled(Marker arg0) {
		return logger.isInfoEnabled(arg0);
	}

	/** 
	 * isTraceEnabled。
	 * @see org.slf4j.Logger#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/** 
	 * isTraceEnabled。
	 * @see org.slf4j.Logger#isTraceEnabled(org.slf4j.Marker)
	 */
	@Override
	public boolean isTraceEnabled(Marker arg0) {
		return logger.isTraceEnabled(arg0);
	}

	/** 
	 * isWarnEnabled。
	 * @see org.slf4j.Logger#isWarnEnabled()
	 */
	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	/** 
	 * isWarnEnabled。
	 * @see org.slf4j.Logger#isWarnEnabled(org.slf4j.Marker)
	 */
	@Override
	public boolean isWarnEnabled(Marker arg0) {
		return logger.isWarnEnabled(arg0);
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(java.lang.String)
	 */
	@Override
	public void trace(String arg0) {
		if (logger.isTraceEnabled()) {
			logger.trace(hack(arg0));
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(String arg0, Object arg1) {
		if (logger.isTraceEnabled()) {
			logger.trace(hack(arg0), arg1);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void trace(String arg0, Object... arg1) {
		if (logger.isTraceEnabled()) {
			logger.trace(hack(arg0), arg1);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String arg0, Throwable arg1) {
		if (logger.isTraceEnabled()) {
			logger.trace(hack(arg0), arg1);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String)
	 */
	@Override
	public void trace(Marker arg0, String arg1) {
		if (logger.isTraceEnabled()) {
			logger.trace(arg0, hack(arg1));
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void trace(String arg0, Object arg1, Object arg2) {
		if (logger.isTraceEnabled()) {
			logger.trace(hack(arg0), arg1, arg2);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(Marker arg0, String arg1, Object arg2) {
		if (logger.isTraceEnabled()) {
			logger.trace(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void trace(Marker arg0, String arg1, Object... arg2) {
		if (logger.isTraceEnabled()) {
			logger.trace(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(Marker arg0, String arg1, Throwable arg2) {
		if (logger.isTraceEnabled()) {
			logger.trace(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * trace。
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
		if (logger.isTraceEnabled()) {
			logger.trace(arg0, hack(arg1), arg2, arg3);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(java.lang.String)
	 */
	@Override
	public void warn(String arg0) {
		if (logger.isWarnEnabled()) {
			logger.warn(hack(arg0));
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(String arg0, Object arg1) {
		if (logger.isWarnEnabled()) {
			logger.warn(hack(arg0), arg1);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void warn(String arg0, Object... arg1) {
		if (logger.isWarnEnabled()) {
			logger.warn(hack(arg0), arg1);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String arg0, Throwable arg1) {
		if (logger.isWarnEnabled()) {
			logger.warn(hack(arg0), arg1);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String)
	 */
	@Override
	public void warn(Marker arg0, String arg1) {
		if (logger.isWarnEnabled()) {
			logger.warn(arg0, hack(arg1));
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void warn(String arg0, Object arg1, Object arg2) {
		if (logger.isWarnEnabled()) {
			logger.warn(hack(arg0), arg1, arg2);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(Marker arg0, String arg1, Object arg2) {
		if (logger.isWarnEnabled()) {
			logger.warn(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void warn(Marker arg0, String arg1, Object... arg2) {
		if (logger.isWarnEnabled()) {
			logger.warn(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(Marker arg0, String arg1, Throwable arg2) {
		if (logger.isWarnEnabled()) {
			logger.warn(arg0, hack(arg1), arg2);
		}
	}

	/** 
	 * warn。
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
		if (logger.isWarnEnabled()) {
			logger.warn(arg0, hack(arg1), arg2, arg3);
		}
	}

	//
	private String hack(String msg) {
		try {
			StackTraceElement[] element = Thread.currentThread().getStackTrace();
			StackTraceElement invokeElement = element[3];
			StringBuilder sb = new StringBuilder();
			sb.append(invokeElement.getClassName());
			sb.append('.');
			sb.append(invokeElement.getMethodName());
			sb.append(" - ");
			sb.append(msg);
			return sb.toString();
		} catch (Exception ex) {
			return msg;
		}
	}
}
