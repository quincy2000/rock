package org.quincy.rock.core.os;

/**
 * <b>物主缓冲池信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月9日 下午4:22:38</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class OwnerCachePoolInfo extends CachePoolInfo {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 4798194020760381855L;

	/**
	 * 物主的缓存池大小。
	 */
	private int ownerCacheSize;	
	/**
	 * 空池数量。
	 */
	private int emptyCount;
	/**
	 * 满池数量。
	 */
	private int fullCount;
	/**
	 * 超过临界值的预警数量。
	 */
	private int criticalCount;
	/**
	 * 平均数量。
	 */
	private int avgCount;
	
	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 缓冲池id
	 * @param name 缓冲池名称
	 */
	public OwnerCachePoolInfo(String id, String name) {
		super(id, name);
	}

	/**
	 * <b>获得物主的缓存池大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物主的缓存池大小
	 */
	public int getOwnerCacheSize() {
		return ownerCacheSize;
	}

	/**
	 * <b>设置物主的缓存池大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ownerCacheSize 物主的缓存池大小
	 */
	public void setOwnerCacheSize(int ownerCacheSize) {
		this.ownerCacheSize = ownerCacheSize;
	}

	/**
	 * <b>获得空池数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 空池数量
	 */
	public int getEmptyCount() {
		return emptyCount;
	}

	/**
	 * <b>设置空池数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param emptyCount 空池数量
	 */
	public void setEmptyCount(int emptyCount) {
		this.emptyCount = emptyCount;
	}

	/**
	 * <b>获得满池数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 满池数量
	 */
	public int getFullCount() {
		return fullCount;
	}

	/**
	 * <b>设置满池数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fullCount 满池数量
	 */
	public void setFullCount(int fullCount) {
		this.fullCount = fullCount;
	}

	/**
	 * <b>获得超过临界值的预警数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 超过临界值的预警数量
	 */
	public int getCriticalCount() {
		return criticalCount;
	}

	/**
	 * <b>设置超过临界值的预警数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param criticalCount 超过临界值的预警数量
	 */
	public void setCriticalCount(int criticalCount) {
		this.criticalCount = criticalCount;
	}

	/**
	 * <b>获得平均数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 平均数量
	 */
	public int getAvgCount() {
		return avgCount;
	}

	/**
	 * <b>设置平均数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param avgCount 平均数量
	 */
	public void setAvgCount(int avgCount) {
		this.avgCount = avgCount;
	}
}
