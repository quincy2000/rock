package org.quincy.rock.comm.netty.parser;

import java.util.Collection;

import org.quincy.rock.comm.parser.BinaryMessageParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * <b>NettyBinaryMessageParser。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年12月3日 下午4:51:00</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class NettyBinaryMessageParser<K> extends BinaryMessageParser<K, ByteBuf> {
	/**
	 * ByteBuf的初始容量。
	 */
	private int initialCapacity = 256;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 */
	public NettyBinaryMessageParser(Collection<String> contentType) {
		this(contentType, 256);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 */
	public NettyBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType) {
		this(functionCode, contentType, 256);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param contentType 内容类型
	 * @param initialCapacity ByteBuf的初始容量
	 */
	public NettyBinaryMessageParser(Collection<String> contentType, int initialCapacity) {
		super(contentType);
		this.setInitialCapacity(initialCapacity);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param functionCode 功能码
	 * @param contentType 内容类型
	 * @param initialCapacity ByteBuf的初始容量
	 */
	public NettyBinaryMessageParser(Collection<K> functionCode, Collection<String> contentType,
			int initialCapacity) {
		super(functionCode, contentType);
		this.setInitialCapacity(initialCapacity);
	}

	/**
	 * <b>获得ByteBuf的初始容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建ByteBuf报文缓冲区。
	 * @return ByteBuf的初始容量
	 */
	public int getInitialCapacity() {
		return initialCapacity;
	}

	/**
	 * <b>设置ByteBuf的初始容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * pack报文时使用该参数值创建ByteBuf报文缓冲区。
	 * @param initialCapacity ByteBuf的初始容量
	 */
	public void setInitialCapacity(int initialCapacity) {
		this.initialCapacity = initialCapacity;
	}

	/** 
	 * createBuffer。
	 * @see org.quincy.rock.comm.parser.BinaryMessageParser#createBuffer()
	 */
	@Override
	protected ByteBuf createBuffer() {
		return Unpooled.buffer(initialCapacity);
	}
}
