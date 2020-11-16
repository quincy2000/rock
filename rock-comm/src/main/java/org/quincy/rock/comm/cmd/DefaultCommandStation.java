package org.quincy.rock.comm.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.quincy.rock.core.cache.LRUOwnerCachePool;
import org.quincy.rock.core.cache.OwnerCachePool;
import org.quincy.rock.core.util.CoreUtil;

/**
 * <b>DefaultCommandStation。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 指令站台的默认实现。
 * 每个终端拥有一个自己独立的站台。
 * 最大的站台数决定了支持的最大终端数，如果达到最大数量则会挤掉一个旧站台。
 * 每个站台拥有一个独立的指令缓冲区，指令被发送之前会暂存在指令缓冲区内，发送完成后需要移除该指令。
 * 每个站台至少能缓存100个指令，当缓存满时新指令会废弃旧指令，即使旧指令没有被发送。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月28日 上午9:38:48</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DefaultCommandStation<TERM> implements CommandStation<TERM> {
	/**
	 * 终端指令站台缓存Map。
	 */
	private final OwnerCachePool<Command> stationPool = new LRUOwnerCachePool<>();

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public DefaultCommandStation() {
		this.setTimeout(36000); //10小时
		this.setCacheSize(10000); //每个站台最多缓存1万条指令
		this.setMaxStationCount(10000); //1万个终端
	}

	/**
	 * <b>获得最大站台数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大站台数
	 */
	public int getMaxStationCount() {
		return stationPool.getCacheSize();
	}

	/**
	 * <b>设置最大站台数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxStationCount 最大站台数
	 */
	public void setMaxStationCount(int maxStationCount) {
		stationPool.setCacheSize(maxStationCount);
	}

	/**
	 * <b>获得每个站台缓存大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 每个站台缓存大小
	 */
	public int getCacheSize() {
		return stationPool.getOwnerCacheSize();
	}

	/**
	 * <b>设置每个站台缓存大小。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cacheSize 每个站台缓存大小
	 */
	public void setCacheSize(int cacheSize) {
		//每个站台至少能缓存100个指令
		//当缓存满时新指令会废弃旧指令，即使旧指令没有被发送。
		cacheSize = Math.max(cacheSize, 100);
		stationPool.setOwnerCacheSize(cacheSize);
	}

	/**
	 * <b>获得指令超时秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指令超时秒数
	 */
	public int getTimeout() {
		return stationPool.getTimeout() / 1000;
	}

	/**
	 * <b>设置指令超时秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param timeout 指令超时秒数
	 */
	public void setTimeout(int timeout) {
		stationPool.setTimeout(timeout * 1000);
	}

	/**
	 * <b>移走终端指令站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminal 终端指令
	 * @param force 是否强制删除终端站台(无论站台是否为空)
	 */
	protected void removeStation(TERM terminal, boolean force) {
		if (force || count(terminal) == 0) {
			stationPool.removeOwner(terminal);
		}
	}

	/** 
	 * terminals。
	 * @see org.quincy.rock.comm.cmd.CommandStation#terminals()
	 */
	@Override
	public Iterable terminals() {
		return stationPool.owners();
	}

	/** 
	 * count。
	 * @see org.quincy.rock.comm.cmd.CommandStation#count()
	 */
	@Override
	public int count() {
		return stationPool.count();
	}

	/** 
	 * count。
	 * @see org.quincy.rock.comm.cmd.CommandStation#count(java.lang.Object)
	 */
	@Override
	public int count(TERM terminal) {
		return stationPool.count(terminal);
	}

	/** 
	 * putCommand。
	 * @see org.quincy.rock.comm.cmd.CommandStation#putCommand(java.lang.Object, org.quincy.rock.comm.cmd.Command)
	 */
	@Override
	public void putCommand(TERM terminal, Command<? extends TERM> cmd) {
		stationPool.putBufferValue(terminal, cmd.id(), cmd);
	}

	/** 
	 * removeCommand。
	 * @see org.quincy.rock.comm.cmd.CommandStation#removeCommand(java.lang.Object, org.quincy.rock.comm.cmd.Command)
	 */
	@Override
	public void removeCommand(TERM terminal, Command<? extends TERM> cmd) {
		stationPool.removeBufferValue(terminal, cmd.id());
	}

	/** 
	 * getNextCommand。
	 * @see org.quincy.rock.comm.cmd.CommandStation#getNextCommand(java.lang.Object)
	 */
	@Override
	public <T extends TERM> Command<T> getNextCommand(T terminal) {
		Iterator it = stationPool.keys(terminal, 0, 10).iterator();
		Command<T> cmd = null;
		while (cmd == null && it.hasNext()) {
			cmd = stationPool.getBufferValue(terminal, it.next());
		}
		return cmd;
	}

	/** 
	 * getCommands。
	 * @see org.quincy.rock.comm.cmd.CommandStation#getCommands(java.lang.Object, int)
	 */
	@Override
	public <T extends TERM> Iterable<Command<T>> getCommands(T terminal, int limit) {
		Iterable keys = stationPool.keys(terminal, 0, limit);
		List<Command<T>> list = new ArrayList<>(CoreUtil.getSize(keys));
		for (Object key : keys) {
			Command<T> cmd = stationPool.getBufferValue(terminal, key);
			if (cmd != null)
				list.add(cmd);
		}
		return list;
	}

}
