package org.quincy.rock.core.os;

import org.apache.commons.lang3.SystemUtils;
import org.quincy.rock.core.vo.Vo;

import oshi.hardware.GlobalMemory;

/**
 * <b>MemoryInfo。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月6日 下午2:04:16</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MemoryInfo extends Vo<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6422999902378152591L;

	private final GlobalMemory memory;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param memory GlobalMemory
	 */
	public MemoryInfo(GlobalMemory memory) {
		this.memory = memory;
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
	 * <b>获得id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return id
	 */
	public String getId() {
		return SystemUtils.getHostName();
	}

	/**
	 * <b>返回虚拟机总共内存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 虚拟机总共内存
	 */
	public long getJvmTotal() {
		Runtime r = Runtime.getRuntime();
		return r.maxMemory();
	}

	/**
	 * <b>返回虚拟机剩余内存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 虚拟机剩余内存
	 */
	public long getJvmFree() {
		Runtime r = Runtime.getRuntime();
		return r.maxMemory() - (r.totalMemory() - r.freeMemory());
	}

	/**
	 * <b>返回物理总共内存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物理总共内存
	 */
	public long getTotal() {
		return memory.getTotal();
	}

	/**
	 * <b>返回物理剩余内存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 物理剩余内存
	 */
	public long getFree() {
		return memory.getAvailable();
	}
}
