package org.quincy.rock.core.os;

import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.Vo;

import oshi.hardware.HWDiskStore;

/**
 * <b>磁盘。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月8日 上午9:40:40</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class Disk extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 2004784806075065385L;

	private final HWDiskStore disk;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param disk HWDiskStore
	 */
	public Disk(HWDiskStore disk) {
		this.disk = disk;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public String id() {
		return getName();
	}

	/**
	 * <b>获得磁盘名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘名称
	 */
	public String getName() {
		return disk.getName();
	}

	/**
	 * <b>返回磁盘型号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘型号
	 */
	public String getModel() {
		return disk.getModel();
	}

	/**
	 * <b>返回磁盘序列号。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘序列号
	 */
	public String getSerial() {
		return disk.getSerial();
	}

	/**
	 * <b>返回磁盘大小(M)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 磁盘大小(M)
	 */
	public long getSize() {
		return disk.getSize() / IOUtil.IO_BINARY_UNIT_M;
	}

}
