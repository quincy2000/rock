package org.quincy.rock.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * <b>CoreUtilTest。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月7日 上午10:42:39</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CoreUtilTest {

	@Test
	public void testHexString2ByteArray() {
		String hex = "0102", rtn;
		byte[] arr = CoreUtil.hexString2ByteArray(hex);
		rtn = CoreUtil.byteArray2HexString(arr);
		assertEquals(hex, rtn);
	}

	@Test
	public void testBinaryToHex() {
		String v = "1F02440E", bin, hex;
		bin = CoreUtil.hexToBinary(v);
		hex = CoreUtil.binaryToHex(bin);
		assertEquals(hex.toLowerCase(), v.toLowerCase());
	}

}
