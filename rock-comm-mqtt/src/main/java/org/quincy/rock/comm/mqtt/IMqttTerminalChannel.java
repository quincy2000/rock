package org.quincy.rock.comm.mqtt;

import org.quincy.rock.comm.communicate.TerminalChannel;

/**
 * <b>IMqttTerminalChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2021年2月9日 下午2:49:33</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface IMqttTerminalChannel<TYPE, CODE> extends IMqttChannel, TerminalChannel<TYPE, CODE> {

	/** 
	 * remote。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#remote()
	 */
	public MqttTerminal<TYPE, CODE> remote();

	/** 
	 * local。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#local()
	 */
	public MqttTerminal<TYPE, CODE> local();
	
	/** 
	 * fromTopic。
	 * @see org.quincy.rock.comm.mqtt.IMqttChannel#fromTopic(java.lang.String)
	 */
	public IMqttTerminalChannel<TYPE, CODE> fromTopic(String topic);
	
	/** 
	 * sendFlag。
	 * @see org.quincy.rock.comm.communicate.TerminalChannel#sendFlag(java.lang.Object)
	 */
	public IMqttTerminalChannel<TYPE, CODE> sendFlag(Object sendFlag);
}
