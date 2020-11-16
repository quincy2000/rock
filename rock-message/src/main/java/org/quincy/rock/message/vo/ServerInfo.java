package org.quincy.rock.message.vo;

import java.util.HashMap;
import java.util.Map;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>报文服务器逻辑信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月13日 下午5:38:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ServerInfo extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -2805655406805431786L;

	/**
	 * 服务器名称。
	 */
	private String name;
	/**
	 * 服务器正在运行。
	 */
	private boolean running;
	/**
	 * 是否服务器数据接收者处于暂停状态。
	 */
	private boolean receiverPausing;
	/**
	 * 操作正忙，不可中断。
	 */
	private boolean busy;
	/**
	 * 服务器创建时间。
	 */
	private long createTime;
	/**
	 * 服务器启动或停止时间。
	 */
	private long timestamp;
	/**
	 * 服务器主机。
	 */
	private String host;
	/**
	 * 服务器端口号。
	 */
	private int port;
	/**
	 * 通讯器类型。
	 */
	private String commType;

	/**
	 * 连接器属性集合。
	 */
	private final Map<String, Object> commAttrs = new HashMap<>();
	/**
	 * 最大终端数。
	 */
	private int maxTermCount;
	/**
	 * 当前终端数。
	 */
	private int termCount;
	/**
	 * 默认报文内容类型。
	 */
	private String contentType;
	/**
	 * 报文过期秒数。
	 */
	private int expire;

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return name;
	}

	/**
	 * <b>获得服务器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>设置服务器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 服务器名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <b>是否服务器正在运行。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否服务器正在运行
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * <b>设置是否服务器正在运行。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param running 是否服务器正在运行
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * <b>是否服务器数据接收者处于暂停状态。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否服务器数据接收者处于暂停状态
	 */
	public boolean isReceiverPausing() {
		return receiverPausing;
	}

	/**
	 * <b>设置是否服务器数据接收者处于暂停状态。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param receiverPausing 是否服务器数据接收者处于暂停状态
	 */
	public void setReceiverPausing(boolean receiverPausing) {
		this.receiverPausing = receiverPausing;
	}

	/**
	 * <b>操作正忙，不可中断。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作正忙，不可中断
	 */
	public boolean isBusy() {
		return busy;
	}

	/**
	 * <b>操作正忙，不可中断。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param busy 操作正忙，不可中断
	 */
	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	/**
	 * <b>获得服务器创建时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器创建时间
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * <b>设置服务器创建时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param createTime 服务器创建时间
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * <b>获得服务器启动或停止时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器启动或停止时间
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * <b>设置服务器启动或停止时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timestamp 服务器启动或停止时间
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * <b>获得服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器主机
	 */
	public String getHost() {
		return host;
	}

	/**
	 * <b>设置服务器主机。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param host 服务器主机
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * <b>获得服务器端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器端口号
	 */
	public int getPort() {
		return port;
	}

	/**
	 * <b>设置服务器端口号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param port 服务器端口号
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * <b>获得通讯器类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯器类型
	 */
	public String getCommType() {
		return commType;
	}

	/**
	 * <b>设置通讯器类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param commType 通讯器类型
	 */
	public void setCommType(String commType) {
		this.commType = commType;
	}

	/**
	 * <b>获得连接器属性集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 连接器属性集合
	 */
	public Map<String, Object> getCommAttrs() {
		return commAttrs;
	}

	/**
	 * <b>设置连接器属性集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param commAttrs 连接器属性集合
	 */
	public void setCommAttrs(Map<String, Object> commAttrs) {
		this.commAttrs.clear();
		for (String key : commAttrs.keySet())
			putCommAttr(key, commAttrs.get(key));
	}

	/**
	 * <b>置入连接器属性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param attrName 属性名
	 * @param attrValue 属性值
	 */
	public void putCommAttr(String attrName, Object attrValue) {
		commAttrs.put(attrName, attrValue == null ? null : attrValue.toString());
	}

	/**
	 * <b>获得最大终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大终端数
	 */
	public int getMaxTermCount() {
		return maxTermCount;
	}

	/**
	 * <b>设置最大终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxTermCount 最大终端数
	 */
	public void setMaxTermCount(int maxTermCount) {
		this.maxTermCount = maxTermCount;
	}

	/**
	 * <b>获得当前终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前终端数
	 */
	public int getTermCount() {
		return termCount;
	}

	/**
	 * <b>设置当前终端数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termCount 当前终端数
	 */
	public void setTermCount(int termCount) {
		this.termCount = termCount;
	}

	/**
	 * <b>获得默认报文内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 默认报文内容类型
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * <b>设置默认报文内容类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 默认报文内容类型
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * <b>获得报文过期秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文过期秒数
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * <b>设置报文过期秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param expire 报文过期秒数
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}
}
