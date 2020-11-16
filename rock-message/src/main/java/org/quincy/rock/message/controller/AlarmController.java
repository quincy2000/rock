package org.quincy.rock.message.controller;

import java.util.ArrayList;
import java.util.List;

import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.cache.Alarm;
import org.quincy.rock.core.cache.AlarmManager;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.message.server.MessageServer;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>告警服务提供给客户端的接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月11日 下午5:30:53</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@RequestMapping("alarm")
@SuppressWarnings({ "rawtypes" })
public class AlarmController extends MessageController {
	/**
	 * logger。
	 */
	protected static final Logger logger = RockUtil.getLogger(AlarmController.class);

	/**
	 * <b>返回待提醒告警信息数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param alarmType 警告类型(-1-全部(默认),1-一般通知消息,2-警告消息,3-错误消息)
	 * @return 待提醒告警信息数量
	 */
	@RequestMapping(value = "/remindCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> remindCount(
			@RequestParam(required = false, name = "alarmType", defaultValue = "-1") int alarmType) {
		Result<Integer> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			AlarmManager am = messageServer.getAlarmManager();
			int count = alarmType > 0 ? am.count(alarmType) : am.count();
			json = Result.toResult(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("remindCount", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>返回终端待提醒告警信息数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @param alarmType 警告类型(-1-全部(默认),1-一般通知消息,2-警告消息,3-错误消息)
	 * @return 待提醒告警信息数量
	 */
	@RequestMapping(value = "/terminalRemindCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> terminalRemindCount(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code,
			@RequestParam(required = false, name = "alarmType", defaultValue = "-1") int alarmType) {
		Result<Integer> json;
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			AlarmManager am = messageServer.getAlarmManager();
			int count = alarmType > 0 ? am.count(term.id(), alarmType) : am.count(term.id());
			json = Result.toResult(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("terminalRemindCount", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得待提醒告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param alarmType 警告类型(-1-全部(默认),1-一般通知消息,2-警告消息,3-错误消息)
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页待提醒告警信息列表
	 */
	@RequestMapping(value = "/getReminds", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<Alarm>> getReminds(
			@RequestParam(required = false, name = "alarmType", defaultValue = "-1") int alarmType,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<Alarm>> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			AlarmManager am = messageServer.getAlarmManager();
			PageSet<Alarm> ps = alarmType > 0 ? am.getRemindAlarms(alarmType, page, pageSize)
					: am.getRemindAlarms(page, pageSize);
			json = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getReminds", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得终端待提醒告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @param alarmType 警告类型(-1-全部(默认),1-一般通知消息,2-警告消息,3-错误消息)
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页待提醒告警信息列表
	 */
	@RequestMapping(value = "/getTerminalReminds", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<Alarm>> getTerminalReminds(
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code,
			@RequestParam(required = false, name = "alarmType", defaultValue = "-1") int alarmType,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<Alarm>> json;
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			AlarmManager am = messageServer.getAlarmManager();
			PageSet<Alarm> ps = alarmType > 0 ? am.getRemindAlarms(term.id(), alarmType, page, pageSize)
					: am.getRemindAlarms(term.id(), page, pageSize);
			json = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getTerminalReminds", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得所有告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 包括需要提醒和不需要提醒的所有告警消息。
	 * @param alarmType 警告类型(-1-全部(默认),1-一般通知消息,2-警告消息,3-错误消息)
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	@RequestMapping(value = "/getAlarms", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<Alarm>> getAlarms(
			@RequestParam(required = false, name = "alarmType", defaultValue = "-1") int alarmType,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<Alarm>> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			AlarmManager am = messageServer.getAlarmManager();
			PageSet<Alarm> ps = alarmType > 0 ? am.getAlarms(alarmType, page, pageSize) : am.getAlarms(page, pageSize);
			json = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getAlarms", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得终端的所有告警信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 包括需要提醒和不需要提醒的所有告警消息。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @param alarmType 警告类型(-1-全部(默认),1-一般通知消息,2-警告消息,3-错误消息)
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页告警信息列表
	 */
	@RequestMapping(value = "/getTerminalAlarms", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<Alarm>> getTerminalAlarms(
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code,
			@RequestParam(required = false, name = "alarmType", defaultValue = "-1") int alarmType,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<Alarm>> json;
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			AlarmManager am = messageServer.getAlarmManager();
			PageSet<Alarm> ps = alarmType > 0 ? am.getAlarms(term.id(), alarmType, page, pageSize)
					: am.getAlarms(term.id(), page, pageSize);
			json = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getTerminalAlarms", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>不再提醒告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 告警或逗号分隔的id列表
	 * @return 是否成功
	 */
	@RequestMapping(value = "/cancelRemind", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> cancelRemind(@RequestParam(required = true, name = "id") String id) {
		Result<Boolean> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			List<Integer> list = new ArrayList<>(id.length() / 2 + 1);
			StringUtil.split(id, 0, id.length(), ',', (index, value) -> list.add(Integer.valueOf(value.toString())));
			int[] arr = new int[list.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = list.get(i);
			messageServer.getAlarmManager().cancelRemind(arr);
			json = Result.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("cancelRemind", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>不再提醒告警信息 for 终端。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 只影响已经产生的告警消息。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 是否成功
	 */
	@RequestMapping(value = "/cancelAllRemind", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> cancelAllRemind(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code) {
		Result<Boolean> json;
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			messageServer.getAlarmManager().cancelAllRemind(term.id());
			json = Result.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("cancelAllRemind", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>不再提醒告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 告警或逗号分隔的id列表
	 * @return 是否成功
	 */
	@RequestMapping(value = "/deleteAlarm", method = { RequestMethod.DELETE, RequestMethod.POST })
	public @ResponseBody Result<Boolean> deleteAlarm(@RequestParam(required = true, name = "id") String id) {
		Result<Boolean> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			List<Integer> list = new ArrayList<>(id.length() / 2 + 1);
			StringUtil.split(id, 0, id.length(), ',', (index, value) -> list.add(Integer.valueOf(value.toString())));
			int[] arr = new int[list.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = list.get(i);
			messageServer.getAlarmManager().deleteAlarm(arr);
			json = Result.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("deleteAlarm", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>删除终端所有告警信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 是否成功
	 */
	@RequestMapping(value = "/deleteAllAlarm", method = { RequestMethod.DELETE, RequestMethod.POST })
	public @ResponseBody Result<Boolean> deleteAllAlarm(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code) {
		Result<Boolean> json;
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			messageServer.getAlarmManager().deleteAllAlarm(term.id());
			json = Result.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("deleteAllAlarm", e.getMessage(), e);
		}
		return json;
	}
}
