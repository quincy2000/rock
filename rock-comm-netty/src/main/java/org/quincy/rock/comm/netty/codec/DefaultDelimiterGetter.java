package org.quincy.rock.comm.netty.codec;

import org.quincy.rock.comm.netty.NettyUtil;
import org.quincy.rock.core.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * <b>缺省的报文分隔符拾取器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 使用NettyUtil.bindDelimiterForEncode方法绑定发送时的分隔符。
 * 该方法适用于分隔符有多个的情况。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月27日 上午12:57:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DefaultDelimiterGetter implements Function<Channel, ByteBuf> {
	/**
	 * INSTANCE。
	 */
	public final static DefaultDelimiterGetter INSTANCE = new DefaultDelimiterGetter();

	/**
	 * 取得后销毁。
	 */
	private boolean burn;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DefaultDelimiterGetter() {
		this(true);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param burn 取得后销毁
	 */
	public DefaultDelimiterGetter(boolean burn) {
		this.burn = burn;
	}

	/** 
	 * call。
	 * @see org.quincy.rock.core.function.Function#call(java.lang.Object)
	 */
	@Override
	public ByteBuf call(Channel channel) {
		Attribute<ByteBuf> attr = channel.attr(NettyUtil.DELIMITER_FOR_ENCODER_KEY);
		return burn ? attr.getAndSet(null) : attr.get();
	}
}
