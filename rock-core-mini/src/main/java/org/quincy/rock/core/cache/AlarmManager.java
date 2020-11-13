package org.quincy.rock.core.cache;

import org.quincy.rock.core.vo.PageSet;

/**
 * <b>告警信息管理器接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月16日 上午10:43:43</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface AlarmManager {
	/**
	 * <b>添加告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param alarm 告警信息
	 */
	public void addAlarm(Alarm alarm);

	/**
	 * <b>添加告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param alarms 告警信息数组
	 */
	public void addAlarm(Alarm... alarms);

	/**
	 * <b>不再提醒。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 告警id
	 */
	public void cancelRemind(int id);

	/**
	 * <b>不再提醒。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 告警id数组
	 */
	public void cancelRemind(int... ids);

	/**
	 * <b>告警源的所有已有告警不再提醒。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 以后新的产生的告警不会取消。
	 * @param source 告警源
	 */
	public void cancelAllRemind(Object source);

	/**
	 * <b>删除告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 告警id
	 */
	public void deleteAlarm(int id);

	/**
	 * <b>删除告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ids 告警数组
	 */
	public void deleteAlarm(int... ids);

	/**
	 * <b>删除所有告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 */
	public void deleteAllAlarm(Object source);

	/**
	 * <b>全部告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 全部告警条数
	 */
	public int count();

	/**
	 * <b>全部告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 告警类型
	 * @return 全部告警条数
	 */
	public int count(int type);

	/**
	 * <b>全部告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @return 全部告警条数
	 */
	public int count(Object source);

	/**
	 * <b>全部告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param type 告警类型
	 * @return 全部告警条数
	 */
	public int count(Object source, int type);

	/**
	 * <b>需要提醒的告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 需要提醒的告警条数
	 */
	public int remindCount();

	/**
	 * <b>需要提醒的告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 告警类型
	 * @return 需要提醒的告警条数
	 */
	public int remindCount(int type);

	/**
	 * <b>需要提醒的告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @return 需要提醒的告警条数
	 */
	public int remindCount(Object source);

	/**
	 * <b>需要提醒的告警条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param type 告警类型
	 * @return 需要提醒的告警条数
	 */
	public int remindCount(Object source, int type);

	/**
	 * <b>获得待提醒告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getRemindAlarms(int page, int pageSize);

	/**
	 * <b>获得待提醒告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 告警类型
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getRemindAlarms(int type, int page, int pageSize);

	/**
	 * <b>获得待提醒告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getRemindAlarms(Object source, int page, int pageSize);

	/**
	 * <b>获得待提醒告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param type 告警类型
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getRemindAlarms(Object source, int type, int page, int pageSize);

	/**
	 * <b>获得所有告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 包括需要提醒和不需要提醒的所有告警消息。
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getAlarms(int page, int pageSize);

	/**
	 * <b>获得所有告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 包括需要提醒和不需要提醒的所有告警消息。
	 * @param type 告警类型
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getAlarms(int type, int page, int pageSize);

	/**
	 * <b>获得所有告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getAlarms(Object source, int page, int pageSize);

	/**
	 * <b>获得所有告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param type 告警类型
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	public PageSet<Alarm> getAlarms(Object source, int type, int page, int pageSize);
}
