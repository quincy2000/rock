package org.quincy.rock.message.vo;

import org.quincy.rock.core.os.ProcessThreadInfo;

/**
 * <b>报文处理服务线程信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年8月23日 下午3:55:46</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MessageProcessThreadInfo extends ProcessThreadInfo {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 4915264746460619651L;
	
	/**
	 * 报文解析毫秒数。
	 */
	private long resolveMillis;
	/**
	 * 报文传输毫秒数。
	 */
	private long transferMillis;
	
	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 线程id
	 */
	public MessageProcessThreadInfo(int id) {
		super(id);
	}

	/**
	 * <b>获得报文解析毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文解析毫秒数
	 */
	public long getResolveMillis() {
		return resolveMillis;
	}

	/**
	 * <b>设置报文解析毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param resolveMillis 报文解析毫秒数
	 */
	public void setResolveMillis(long resolveMillis) {
		this.resolveMillis = resolveMillis;
	}

	/**
	 * <b>获得报文传输毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文传输毫秒数
	 */
	public long getTransferMillis() {
		return transferMillis;
	}

	/**
	 * <b>设置报文传输毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param transferMillis 报文传输毫秒数
	 */
	public void setTransferMillis(long transferMillis) {
		this.transferMillis = transferMillis;
	}
}
