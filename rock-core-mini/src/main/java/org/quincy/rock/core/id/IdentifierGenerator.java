package org.quincy.rock.core.id;

/**
 * <b>ID生成器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * ID生成器接口。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2008-9-23 上午11:26:39</td><td>建立类型</td></tr>
 *
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface IdentifierGenerator<K, V> {
	/**
	 * <b>根据名称生成唯一id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key id key
	 * @return 唯一id
	 */
	public V generate(K key);
}
