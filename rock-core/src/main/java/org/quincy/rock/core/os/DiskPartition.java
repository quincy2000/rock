package org.quincy.rock.core.os;

import org.quincy.rock.core.vo.Vo;

import oshi.software.os.OSFileStore;

/**
 * <b>磁盘分区。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月15日 下午2:39:23</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DiskPartition extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7351672952017347641L;

	private final OSFileStore fs;

	/**
	 * 磁盘名称。
	 */
	private String diskName;
	/**
	 * 磁盘分区信息。
	 */
	private String diskInfo;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param fs OSFileStore
	 */
	public DiskPartition(OSFileStore fs) {
		this.fs = fs;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return getId();
	}

	/**
	 * <b>获得分区唯一标识。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分区唯一标识
	 */
	public String getId() {
		return fs.getUUID();
	}

	/**
	 * <b>获得盘符名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 盘符名称
	 */
	public String getName() {
		return fs.getName();
	}

	/**
	 * <b>获得盘符根目录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 盘符根目录
	 */
	public String getDirName() {
		return fs.getMount();
	}

	/**
	 * <b>获得分区类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分区类型
	 */
	public String getType() {
		return fs.getType();
	}

	/**
	 * <b>获得卷标。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 卷标
	 */
	public String getLable() {
		return fs.getLabel();
	}

	/**
	 * <b>获得分区容量(K)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分区容量 (K)
	 */
	public long getDiskTotal() {
		return fs.getTotalSpace();
	}

	/**
	 * <b>获得分区空闲容量(K)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分区空闲容量 (K)
	 */
	public long getDiskFree() {
		return fs.getFreeSpace();
	}

	/**
	 * <b>获得分区描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分区描述
	 */
	public String getDescr() {
		return fs.getDescription();
	}

	/**
	 * <b>获得磁盘名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘名称
	 */
	public String getDiskName() {
		return diskName;
	}

	/**
	 * <b>设置磁盘名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param diskName 磁盘名称
	 */
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	/**
	 * <b>获得磁盘分区信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘分区信息
	 */
	public String getDiskInfo() {
		return diskInfo;
	}

	/**
	 * <b>设置磁盘分区信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param diskInfo 磁盘分区信息
	 */
	public void setDiskInfo(String diskInfo) {
		this.diskInfo = diskInfo;
	}
}
