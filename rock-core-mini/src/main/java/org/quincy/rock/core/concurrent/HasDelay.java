package org.quincy.rock.core.concurrent;

/**
 * <b>HasDelay。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年3月12日 下午4:05:59</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface HasDelay {
	/**
	 * 无效延迟。
	 */
	public static final int invalidDelay = -1;
	/**
	 * 没有延迟。
	 */
	public static final int noDelay = 0;

	/**
	 * <b>获得延迟毫秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 延迟毫秒数
	 */
	public int getDelayMillis();
}
