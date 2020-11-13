package org.quincy.rock.core.security;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.quincy.rock.core.exception.UnsupportException;

/**
 * <b>CrcType。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月26日 上午10:08:08</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public enum CrcType {
	NONE(0, false), CRC16(16, false), CRC32(32, false), CRC16HEX(16, true), CRC32HEX(32, true);
	/**
	 * 位数。
	 */
	private int bit;
	/**
	 * 是否是16进制字符串。
	 */
	private boolean hex;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bit 位数
	 * @param hex 是否是16进制字符串
	 */
	private CrcType(int bit, boolean hex) {
		this.bit = bit;
		this.hex = hex;
	}

	/**
	 * <b>位数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 位数
	 */
	public int bit() {
		return bit;
	}

	/**
	 * <b>是否是16进制字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是16进制字符串
	 */
	public boolean isHex() {
		return hex;
	}

	/**
	 * <b>字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 字节数
	 */
	public int byteCount() {
		return bit >> (hex ? 2 : 3);
	}

	/**
	 * <b>字节数 for BCD。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 字节数 for BCD
	 */
	public int byteCount4BCD() {
		return bit >> 3;
	}

	/**
	 * <b>createChecker。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public Checksum createChecker() {
		Checksum checker;
		switch (this.bit) {
		case 16:
			checker = new CRC16();
			break;
		case 32:
			checker = new CRC32();
			break;
		default:
			throw new UnsupportException("Failed to create CRC checker for type:" + this.name());
		}
		return checker;
	}
}
