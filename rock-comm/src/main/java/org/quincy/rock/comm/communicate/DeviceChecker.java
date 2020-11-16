package org.quincy.rock.comm.communicate;

/**
 * <b>使用终端设备提供者实现的终端检查器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月29日 下午1:03:44</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DeviceChecker<T extends TerminalId<?, ?>> implements TerminalChecker<T> {

	/**
	 * 终端提供者。
	 */
	private DeviceProvider<?> deviceProvider;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DeviceChecker() {
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalProvider 终端提供者
	 */
	public DeviceChecker(DeviceProvider<?> terminalProvider) {
		this.deviceProvider = terminalProvider;
	}

	/**
	 * <b>获得终端提供者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端提供者
	 */
	public DeviceProvider<?> getTerminalProvider() {
		return deviceProvider;
	}

	/**
	 * <b>设置终端提供者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalProvider 终端提供者
	 */
	public void setTerminalProvider(DeviceProvider<?> terminalProvider) {
		this.deviceProvider = terminalProvider;
	}

	/** 
	 * checkTerminal。
	 * @see org.quincy.rock.comm.communicate.TerminalChecker#checkTerminal(org.quincy.rock.comm.communicate.TerminalId)
	 */
	@Override
	public boolean checkTerminal(T termId) {
		DeviceProvider<?> provider = this.getTerminalProvider();
		return provider.findDevice(termId.getCode()) != null;
	}

}
