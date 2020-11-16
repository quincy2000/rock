package org.quincy.rock.core.os;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>CPU信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月6日 下午4:34:35</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CpuInfo extends Cpu<String> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 8703024940921498046L;

	/**
	 * cpu名称。
	 */
	private String name;
	/**
	 * cpu内核信息列表。
	 */
	private List<CpuCoreInfo> cpuCoreInfos;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name cpu名称
	 */
	public CpuInfo(String name) {
		this.name = name;
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
	 * <b>获得cpu名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return cpu名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>获得cpu内核信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return cpu内核信息列表
	 */
	public List<CpuCoreInfo> getCpuCoreInfos() {
		if (cpuCoreInfos == null) {
			cpuCoreInfos = new ArrayList<>();
		}
		return cpuCoreInfos;
	}

	/**
	 * <b>添加cpu内核信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cci cpu内核信息
	 */
	public void addCpuCoreInfo(CpuCoreInfo cci) {
		this.getCpuCoreInfos().add(cci);
	}
}
