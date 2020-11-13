package org.quincy.rock.core.lang;

import java.text.MessageFormat;

/**
 * <b>TraceRecorder。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月25日 上午12:30:10</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class TraceRecorder implements Recorder {
	/**
	 * 是否记录实体。
	 */
	private boolean record;

	/**
	 * <b>是否记录实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否记录实体
	 */
	public final boolean isRecord() {
		return record;
	}

	/**
	 * <b>设置是否记录实体。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param record 是否记录实体
	 */
	public final void setRecord(boolean record) {
		this.record = record;
	}

	/** 
	 * canWrite。
	 * @see org.quincy.rock.core.lang.Recorder#canWrite()
	 */
	@Override
	public boolean canWrite() {
		return record;
	}

	/** 
	 * write。
	 * @see org.quincy.rock.core.lang.Recorder#write(java.lang.Throwable, java.lang.String, java.lang.Object[])
	 */
	@Override
	public final void write(Throwable t, CharSequence message, Object... args) {
		if (canWrite()) {
			try {
				this.trace(message == null ? null : MessageFormat.format(message.toString(), args), t);
			} catch (Exception e) {
				this.trace(message == null ? null : message.toString(), t);
			}
		}
	}

	/** 
	 * write。
	 * @see org.quincy.rock.core.lang.Recorder#write(java.lang.String, java.lang.Object[])
	 */
	@Override
	public final void write(CharSequence message, Object... args) {
		this.write(null, message, args);
	}

	/**
	 * <b>跟踪记录消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 消息文本
	 * @param t 异常对象，允许为null
	 */
	protected abstract void trace(String message, Throwable t);
}
