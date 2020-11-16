package org.quincy.rock.core.os;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>进程信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年5月8日 下午1:29:00</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ProcessInfo extends Vo<Integer> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6530336308703841536L;

	/**
	 * 进程id。
	 */
	private int id;
	/**
	 * 进程名称。
	 */
	private String name;
	/**
	 * 用户。
	 */
	private String user;
	/**
	 * 程序路径。
	 */
	private String path;
	/**
	 * 打开文件数。
	 */
	private long openFiles;
	/**
	 * 线程数。
	 */
	private int threadCount;
	/**
	 * 启动时间。
	 */
	private long startTime;
	/**
	 * 运行时间。
	 */
	private long runTime;
	/**
	 * 父进程id。
	 */
	private int parentId;
	/**
	 * 占用内存。
	 */
	private long memory;
	/**
	 * CPU占用率(0-100)。
	 */
	private double cpuPercent;
	/**
	 * 读取磁盘字节数。
	 */
	private long diskReads;
	/**
	 * 写入磁盘字节数。
	 */
	private long diskWrites;
	/**
	 * 获得位数(32位或64位)。
	 */
	private int bitness;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 进程id
	 * @param name 进程名称
	 */
	public ProcessInfo(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Integer id() {
		return getId();
	}

	/**
	 * <b>获得进程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 进程id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * <b>获得进程名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 进程名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <b>获得用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 用户
	 */
	public String getUser() {
		return user;
	}

	/**
	 * <b>设置用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param user 用户
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * <b>获得程序路径。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 程序路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <b>设置程序路径。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param path 程序路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * <b>获得打开文件数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 打开文件数
	 */
	public long getOpenFiles() {
		return openFiles;
	}

	/**
	 * <b>设置打开文件数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param openFiles 打开文件数
	 */
	public void setOpenFiles(long openFiles) {
		this.openFiles = openFiles;
	}

	/**
	 * <b>获得线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 线程数
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * <b>设置线程数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param threadCount 线程数
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	/**
	 * <b>获得启动时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 启动时间
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * <b>设置启动时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param startTime 启动时间
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * <b>获得运行时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 运行时间
	 */
	public long getRunTime() {
		return runTime;
	}

	/**
	 * <b>设置运行时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param runTime 运行时间
	 */
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}

	/**
	 * <b>获得父进程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 父进程id
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * <b>设置父进程id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param parentId 父进程id
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * <b>获得占用内存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 占用内存
	 */
	public long getMemory() {
		return memory;
	}

	/**
	 * <b>设置占用内存。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param memory 占用内存
	 */
	public void setMemory(long memory) {
		this.memory = memory;
	}

	/**
	 * <b>获得CPU占用率(0-100)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return CPU占用率(0-100)
	 */
	public double getCpuPercent() {
		return cpuPercent;
	}

	/**
	 * <b>设置CPU占用率(0-100)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cpuPercent CPU占用率(0-100)
	 */
	public void setCpuPercent(double cpuPercent) {
		this.cpuPercent = cpuPercent;
	}

	/**
	 * <b>获得读取磁盘字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 读取磁盘字节数
	 */
	public long getDiskReads() {
		return diskReads;
	}

	/**
	 * <b>设置读取磁盘字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param diskReads 读取磁盘字节数
	 */
	public void setDiskReads(long diskReads) {
		this.diskReads = diskReads;
	}

	/**
	 * <b>获得写入磁盘字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 写入磁盘字节数
	 */
	public long getDiskWrites() {
		return diskWrites;
	}

	/**
	 * <b>设置写入磁盘字节数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param diskWrites 写入磁盘字节数
	 */
	public void setDiskWrites(long diskWrites) {
		this.diskWrites = diskWrites;
	}

	/**
	 * <b>获得获得位数(32位或64位)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 获得位数(32位或64位)
	 */
	public int getBitness() {
		return bitness;
	}

	/**
	 * <b>设置获得位数(32位或64位)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param bitness 获得位数(32位或64位)
	 */
	public void setBitness(int bitness) {
		this.bitness = bitness;
	}

}
