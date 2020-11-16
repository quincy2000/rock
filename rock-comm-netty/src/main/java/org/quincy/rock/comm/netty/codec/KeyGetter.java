package org.quincy.rock.comm.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * <b>随机密钥Key拾取器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 使用该接口用来实现获得随机密钥key的机制。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2019年10月25日 下午2:43:35</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface KeyGetter {
	/**
	 * <b>getKey。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ch netty通道
	 * @param msg 报文
	 * @return key
	 */
	public byte[] getKey(Channel ch, ByteBuf msg);
}
