package org.quincy.rock.core.id;

/**
 * <b>IncGenerator。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2016年5月31日 上午12:00:41</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class IncGenerator implements IdentifierGenerator<Object, Long> {
	/**
	 * id。
	 */
	private long id = 1;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public IncGenerator() {
		super();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 初始id值
	 */
	public IncGenerator(long id) {
		super();
		this.id = id;
	}

	/** 
	 * generate。
	 * @see org.quincy.rock.core.id.IdentifierGenerator#generate(java.lang.Object)
	 */
	@Override
	public synchronized Long generate(Object key) {
		return id++;
	}

}
