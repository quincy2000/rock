package org.quincy.rock.core.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.quincy.rock.core.exception.BlcokingException;
import org.quincy.rock.core.vo.PageSet;

/**
 * <b>MemoryAlarmManager。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月16日 下午3:05:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MemoryAlarmManager implements AlarmManager {
	/**
	 * 告警Map。
	 */
	private final Map<Integer, Alarm> remindMap = new ConcurrentHashMap();
	/**
	 * 不需要提醒的告警map。
	 */
	private final Map<Integer, Alarm> noRemindMap = new ConcurrentHashMap();

	/**
	 * 最大存放个数。
	 */
	private int maxCount;
	/**
	 * 存放最长时间(秒)。
	 */
	private long maxAge;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxCount 最大存放个数
	 * @param maxAge 存放最长时间(秒)
	 */
	public MemoryAlarmManager(int maxCount, int maxAge) {
		this.setMaxCount(maxCount);
		this.setMaxAge(maxAge);
	}

	/**
	 * <b>获得最大存放个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 最大存放个数
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * <b>设置最大存放个数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxCount 最大存放个数
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * <b>获得存放最长时间(秒)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 存放最长时间(秒)
	 */
	public int getMaxAge() {
		return (int) maxAge / 1000;
	}

	/**
	 * <b>设置存放最长时间(秒)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param maxAge 存放最长时间(秒)
	 */
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge * 1000;
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.cache.AbstractAlarmManager#count()
	 */
	@Override
	public int count() {
		return remindMap.size() + noRemindMap.size();
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.cache.AlarmManager#count(int)
	 */
	@Override
	public int count(int type) {
		int count = 0;
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type) {
				count++;
			}
		}
		for (Alarm alarm : noRemindMap.values()) {
			if (alarm.getType() == type) {
				count++;
			}
		}
		return count;
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.cache.AlarmManager#count(java.lang.Object)
	 */
	@Override
	public int count(Object source) {
		int count = 0;
		for (Alarm alarm : remindMap.values()) {
			if (Objects.equals(alarm.getSource(), source)) {
				count++;
			}
		}
		for (Alarm alarm : noRemindMap.values()) {
			if (Objects.equals(alarm.getSource(), source)) {
				count++;
			}
		}
		return count;
	}

	/** 
	 * count。
	 * @see org.quincy.rock.core.cache.AlarmManager#count(java.lang.Object, int)
	 */
	@Override
	public int count(Object source, int type) {
		int count = 0;
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type && Objects.equals(alarm.getSource(), source)) {
				count++;
			}
		}
		for (Alarm alarm : noRemindMap.values()) {
			if (alarm.getType() == type && Objects.equals(alarm.getSource(), source)) {
				count++;
			}
		}
		return count;
	}

	/** 
	 * remindCount。
	 * @see org.quincy.rock.core.cache.AbstractAlarmManager#remindCount()
	 */
	@Override
	public int remindCount() {
		return remindMap.size();
	}

	/** 
	 * remindCount。
	 * @see org.quincy.rock.core.cache.AlarmManager#remindCount(int)
	 */
	@Override
	public int remindCount(int type) {
		int count = 0;
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type) {
				count++;
			}
		}
		return count;
	}

	/** 
	 * remindCount。
	 * @see org.quincy.rock.core.cache.AlarmManager#remindCount(java.lang.Object)
	 */
	@Override
	public int remindCount(Object source) {
		int count = 0;
		for (Alarm alarm : remindMap.values()) {
			if (Objects.equals(alarm.getSource(), source)) {
				count++;
			}
		}
		return count;
	}

	/** 
	 * remindCount。
	 * @see org.quincy.rock.core.cache.AlarmManager#remindCount(java.lang.Object, int)
	 */
	@Override
	public int remindCount(Object source, int type) {
		int count = 0;
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type && Objects.equals(alarm.getSource(), source)) {
				count++;
			}
		}
		return count;
	}

	/** 
	 * addAlarm。
	 * @see org.quincy.rock.core.cache.AlarmManager#addAlarm(org.quincy.rock.core.cache.Alarm)
	 */
	@Override
	public synchronized void addAlarm(Alarm alarm) {
		if (checkCapacity()) {
			alarm.setId(getNextId());
			if (alarm.isRemind())
				remindMap.put(alarm.getId(), alarm);
			else
				noRemindMap.put(alarm.getId(), alarm);
		} else
			throw new BlcokingException("Reach the maximum number of MemoryAlarmManager:" + getMaxCount());
	}

	/** 
	 * addAlarm。
	 * @see org.quincy.rock.core.cache.AlarmManager#addAlarm(org.quincy.rock.core.cache.Alarm[])
	 */
	@Override
	public void addAlarm(Alarm... alarms) {
		for (Alarm a : alarms)
			this.addAlarm(a);
	}

	/** 
	 * cancelRemind。
	 * @see org.quincy.rock.core.cache.AlarmManager#cancelRemind(int)
	 */
	@Override
	public void cancelRemind(int id) {
		Alarm alarm = remindMap.remove(id);
		if (alarm != null) {
			alarm.setRemind(false);
			noRemindMap.put(id, alarm);
		}
	}

	/** 
	 * cancelRemind。
	 * @see org.quincy.rock.core.cache.AlarmManager#cancelRemind(int[])
	 */
	@Override
	public void cancelRemind(int... ids) {
		for (int id : ids) {
			this.cancelRemind(id);
		}
	}

	/** 
	 * cancelAllRemind。
	 * @see org.quincy.rock.core.cache.AlarmManager#cancelAllRemind(java.lang.Object)
	 */
	@Override
	public void cancelAllRemind(Object source) {
		List<Alarm> list = new ArrayList(remindCount());
		for (Alarm alarm : remindMap.values()) {
			if (Objects.equals(alarm.getSource(), source))
				list.add(alarm);
		}
		for (Alarm alarm : list) {
			cancelRemind(alarm.getId());
		}
	}

	/** 
	 * deleteAlarm。
	 * @see org.quincy.rock.core.cache.AlarmManager#deleteAlarm(int)
	 */
	@Override
	public void deleteAlarm(int id) {
		if (remindMap.containsKey(id))
			remindMap.remove(id);
		else if (noRemindMap.containsKey(id))
			noRemindMap.remove(id);
	}

	/** 
	 * deleteAlarm。
	 * @see org.quincy.rock.core.cache.AlarmManager#deleteAlarm(int[])
	 */
	@Override
	public void deleteAlarm(int... ids) {
		for (int id : ids) {
			this.deleteAlarm(id);
		}
	}

	/** 
	 * deleteAllAlarm。
	 * @see org.quincy.rock.core.cache.AlarmManager#deleteAllAlarm(java.lang.Object)
	 */
	@Override
	public void deleteAllAlarm(Object source) {
		List<Alarm> list = new ArrayList(Math.max(count(), remindCount()));
		for (Alarm alarm : remindMap.values()) {
			if (Objects.equals(alarm.getSource(), source))
				list.add(alarm);
		}
		for (Alarm alarm : list) {
			remindMap.remove(alarm.getId());
		}
		list.clear();
		//
		for (Alarm alarm : noRemindMap.values()) {
			if (Objects.equals(alarm.getSource(), source))
				list.add(alarm);
		}
		for (Alarm alarm : list) {
			noRemindMap.remove(alarm.getId());
		}
	}

	/** 
	 * getAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getAlarms(int, int)
	 */
	@Override
	public PageSet<Alarm> getAlarms(int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		list.addAll(remindMap.values());
		list.addAll(noRemindMap.values());
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getAlarms(int, int, int)
	 */
	@Override
	public PageSet<Alarm> getAlarms(int type, int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type) {
				list.add(alarm);
			}
		}
		for (Alarm alarm : noRemindMap.values()) {
			if (alarm.getType() == type) {
				list.add(alarm);
			}
		}
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getAlarms(java.lang.Object, int, int)
	 */
	@Override
	public PageSet<Alarm> getAlarms(Object source, int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		for (Alarm alarm : remindMap.values()) {
			if (Objects.equals(alarm.getSource(), source)) {
				list.add(alarm);
			}
		}
		for (Alarm alarm : noRemindMap.values()) {
			if (Objects.equals(alarm.getSource(), source)) {
				list.add(alarm);
			}
		}
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getAlarms(java.lang.Object, int, int, int)
	 */
	@Override
	public PageSet<Alarm> getAlarms(Object source, int type, int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type && Objects.equals(alarm.getSource(), source)) {
				list.add(alarm);
			}
		}
		for (Alarm alarm : noRemindMap.values()) {
			if (alarm.getType() == type && Objects.equals(alarm.getSource(), source)) {
				list.add(alarm);
			}
		}
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getRemindAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getRemindAlarms(int, int)
	 */
	@Override
	public PageSet<Alarm> getRemindAlarms(int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		list.addAll(remindMap.values());
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getRemindAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getRemindAlarms(int, int, int)
	 */
	@Override
	public PageSet<Alarm> getRemindAlarms(int type, int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type) {
				list.add(alarm);
			}
		}
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getRemindAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getRemindAlarms(java.lang.Object, int, int)
	 */
	@Override
	public PageSet<Alarm> getRemindAlarms(Object source, int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		for (Alarm alarm : remindMap.values()) {
			if (Objects.equals(alarm.getSource(), source)) {
				list.add(alarm);
			}
		}
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/** 
	 * getRemindAlarms。
	 * @see org.quincy.rock.core.cache.AlarmManager#getRemindAlarms(java.lang.Object, int, int, int)
	 */
	@Override
	public PageSet<Alarm> getRemindAlarms(Object source, int type, int page, int pageSize) {
		List<Alarm> list = new ArrayList(count());
		for (Alarm alarm : remindMap.values()) {
			if (alarm.getType() == type && Objects.equals(alarm.getSource(), source)) {
				list.add(alarm);
			}
		}
		Collections.sort(list, Alarm.alarmComparator);
		return PageSet.toPageSet(list, page, pageSize);
	}

	/**
	 * <b>检查容量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否检查通过
	 */
	protected boolean checkCapacity() {
		if (count() < getMaxCount())
			return true;
		{
			//先释放noRemindMap空间
			boolean ok = false;
			Alarm alarm = null;
			List<Integer> list = new ArrayList();
			for (Alarm a : noRemindMap.values()) {
				if (a.timeout(maxAge))
					list.add(a.getId());
				else if (a.older(alarm))
					alarm = a;
			}
			ok = list.size() > 0;
			for (Integer id : list)
				noRemindMap.remove(id);
			//再释放remindMap空间
			list.clear();
			for (Alarm a : remindMap.values()) {
				if (a.timeout(maxAge))
					list.add(a.getId());
			}
			for (Integer id : list)
				remindMap.remove(id);
			ok = ok || list.size() > 0;
			if (!ok && alarm != null) {
				noRemindMap.remove(alarm.getId());
				ok = true;
			}
			return ok;
		}
	}

	/**
	 * <b>获得下一个id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 下一个id
	 */
	private int getNextId() {
		if (_id == Integer.MAX_VALUE)
			_id = 0;
		return ++_id;
	}

	private static int _id;
}
