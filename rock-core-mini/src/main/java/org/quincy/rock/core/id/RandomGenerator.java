package org.quincy.rock.core.id;

import java.util.Random;

/**
 * <b>随机数生成器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2009-4-22 下午10:08:32</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class RandomGenerator implements IdentifierGenerator<Object, Integer> {
	/**
	 * instance。
	 */
	public static final RandomGenerator instance = new RandomGenerator();

	/**
	 * RANDOM。
	 */
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	/** 
	 * generate。
	 * @see org.quincy.rock.core.id.IdentifierGenerator#generate(java.lang.Object)
	 */
	public Integer generate(Object key) {
		int i = RANDOM.nextInt(Integer.MAX_VALUE - 1) + 1;
		return i;
	}
}
