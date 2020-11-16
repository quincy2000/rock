package org.quincy.rock.comm.netty.parser;

import java.util.List;

/**
 * <b>JsonConverter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月22日 上午12:22:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface JsonConverter {
	/**
	 * <b>将报文对象转成json字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param message 报文对象
	 * @return json字符串
	 */
	public String toJson(Message message);

	/**
	 * <b>将json字符串转换成报文对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @param type 报文对象类型
	 * @return 报文对象
	 */
	public <T extends Message> T fromJson(String json, Class<T> type);
	
	/**
	 * <b>将json字符串转换成报文对象（数组）。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param json json字符串
	 * @param type 数组元素类型
	 * @return 报文对象列表
	 */
	public <T extends Message> List<T> fromJsonArray(String json, Class<T> type);
}
