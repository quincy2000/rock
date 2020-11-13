package org.quincy.rock.core.lang;

/**
 * <b>Recorder。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月7日 下午7:33:01</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface Recorder {
	/**
	 * EMPTY。
	 */
	public static final Recorder EMPTY = new Recorder() {

		@Override
		public void write(Throwable t, CharSequence message, Object... args) {

		}

		@Override
		public void write(CharSequence message, Object... args) {

		}

		@Override
		public boolean canWrite() {
			return false;
		}

	};

	/**
	 * <b>记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param t 异常，可以为null
	 * @param message 消息
	 * @param args 参数
	 */
	public void write(Throwable t, CharSequence message, Object... args);

	/**
	 * <b>记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 消息
	 * @param args 参数
	 */
	public void write(CharSequence message, Object... args);

	/**
	 * <b>是否允许记录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否允许记录
	 */
	public boolean canWrite();
}
