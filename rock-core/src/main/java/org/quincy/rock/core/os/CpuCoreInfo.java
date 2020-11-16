package org.quincy.rock.core.os;

/**
 * <b>Cpu内核信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月15日 上午10:07:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CpuCoreInfo extends Cpu<Integer> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -3122449018250104374L;

	/**
	 * cpu id。
	 */
	private final int id;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id id
	 */
	public CpuCoreInfo(int id) {
		this.id = id;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Integer id() {
		return id;
	}

	/**
	 * <b>获得cpu id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return cpu id
	 */
	public int getId() {
		return id;
	}
}
