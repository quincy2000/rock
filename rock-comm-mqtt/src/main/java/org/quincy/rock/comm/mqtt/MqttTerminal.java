package org.quincy.rock.comm.mqtt;

import org.quincy.rock.comm.communicate.TerminalId;

/**
 * <b>MqttTerminal。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月9日 下午2:53:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MqttTerminal<TYPE, CODE> extends TerminalId<TYPE, CODE> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MqttTerminal() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param server 是否是服务器
	 */
	public MqttTerminal(boolean server) {
		super(server);
	}

	/**
	 * <b>解析topic字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param topic topic字符串
	 * @return MqttTerminal
	 */
	public abstract MqttTerminal<TYPE, CODE> fromTopic(String topic);

	/**
	 * <b>生成topic字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return topic字符串
	 */
	public abstract String toTopic();
}
