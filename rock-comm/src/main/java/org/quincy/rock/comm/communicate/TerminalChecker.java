package org.quincy.rock.comm.communicate;

/**
 * <b>终端合法性检查器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月27日 下午2:55:26</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface TerminalChecker<T extends TerminalId<?, ?>> {
	/**
	 * 允许所有终端检查通过。
	 */
	public static final TerminalChecker<TerminalId> ALLOW_ALL = new TerminalChecker() {

		@Override
		public boolean checkTerminal(TerminalId termId) {
			return true;
		}
	};

	/**
	 * 拒绝所有终端。
	 */
	public static final TerminalChecker REJECT_ALL = new TerminalChecker() {

		@Override
		public boolean checkTerminal(TerminalId termId) {
			return false;
		}
	};

	/**
	 * <b>检查终端合法性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termId 终端id
	 * @param 是否合法
	 */
	public boolean checkTerminal(T termId);
}
