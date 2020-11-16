package org.quincy.rock.comm;

import java.util.Map;

/**
 * <b>报文头解析器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月28日 上午10:55:26</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageHeadParser<M,V> {
	/**
	 * <b>获得缺省的报文正文格式类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 缺省的报文正文格式类型
	 */
	public String getDefaultContentType();
	
	/**
	 * <b>组装报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param value 报文正文内容
	 * @param ctx 上下文对象(inout类型参数)
	 * @return 待传输的报文
	 */
	public M pack(V value, Map<String, Object> ctx);
	
	/**
	 * <b>解析报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 带解析的报文
	 * @param ctx 上下文对象(inout类型参数)
	 * @return 解析出来的报文正文内容
	 */
	public V unpack(M message, Map<String, Object> ctx);
}
