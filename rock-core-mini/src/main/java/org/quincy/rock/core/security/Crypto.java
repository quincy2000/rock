package org.quincy.rock.core.security;

import org.quincy.rock.core.exception.CalculatorException;

/**
 * <b>加解密接口类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年8月13日 上午10:00:56</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface Crypto {
	/**
	 * <b>加密。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 需要加密的数据
	 * @return 加密数据
	 * @throws CalculatorException
	 */
	public byte[] encrypt(byte[] data) throws CalculatorException;

	/**
	 * <b>解密。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param data 需要解密的数据
	 * @return 解密数据
	 * @throws CalculatorException
	 */
	public byte[] decrypt(byte[] data) throws CalculatorException;
}
