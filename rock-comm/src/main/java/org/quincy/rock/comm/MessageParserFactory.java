package org.quincy.rock.comm;

import org.quincy.rock.comm.MessageParser.MsgType;

/**
 * <b>报文解析器工厂。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2011-10-22 下午11:55:45</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface MessageParserFactory<K> {
	/**
	 * <b>获得报文头解析器对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文头解析器对象
	 */
	public <M, V> MessageHeadParser<M, V> getMessageHeadParser();

	/**
	 * <b>获得报文正文解析器对象。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param msgType 报文类型
	 * @param contentType 报文内容类型 
	 * @return 报文正文解析器对象
	 */
	public <M, V> MessageParser<K, M, V> getMessageParser(K functionCode, MsgType msgType, String contentType);
}
