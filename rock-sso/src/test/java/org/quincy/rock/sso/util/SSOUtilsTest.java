package org.quincy.rock.sso.util;

import java.text.MessageFormat;

import org.junit.Test;

/**
 * <b>SSOUitlsTest。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年9月12日 上午11:40:59</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOUtilsTest {

	@Test
	public void test() throws Exception {
		Object a=MessageFormat.format("aaaa{1}",1,2);
		System.out.println(a);
	}

}
