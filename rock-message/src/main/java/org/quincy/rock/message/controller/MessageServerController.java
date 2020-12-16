package org.quincy.rock.message.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quincy.rock.comm.cmd.TerminalCommand;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.cache.Alarm;
import org.quincy.rock.core.os.ProcessServiceInfo;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.message._Utils;
import org.quincy.rock.message.server.MessageServer;
import org.quincy.rock.message.vo.DeviceTerminalInfo;
import org.quincy.rock.message.vo.ServerInfo;
import org.quincy.rock.message.vo.TerminalInfo;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>报文消息服务器提供给客户端的接口。</b>
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
@SuppressWarnings({ "rawtypes" })
public abstract class MessageServerController<CMD extends TerminalCommand<TERM, TYPE, CODE>, TERM extends TerminalId<TYPE, CODE>, TYPE, CODE>
		extends MessageController<CMD, TERM, TYPE, CODE> {
	/**
	 * logger。
	 */
	protected static final Logger logger = RockUtil.getLogger(MessageServerController.class);

	/**
	 * <b>启动服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器信息
	 */
	@RequestMapping(value = "/start", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ServerInfo> start() {
		Result<ServerInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			messageServer.checkBusy();
			messageServer.start();
			messageServer.alarm("messageServer", Alarm.ALARM_TYPE_NOTICE, "server.start.ok", "用户手动启动报文服务器成功.");
			json = Result.toResult(messageServer.getServerInfo());
		} catch (Exception e) {
			messageServer.alarm("messageServer", Alarm.ALARM_TYPE_ERROR, "server.start.fail", "用户手动启动报文服务器失败!");
			logger.error(e.getMessage(), e);
			json = Result.toResult("start", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>停止服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器信息
	 */
	@RequestMapping(value = "/stop", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ServerInfo> stop() {
		Result<ServerInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			messageServer.checkBusy();
			messageServer.stop();
			messageServer.alarm("messageServer", Alarm.ALARM_TYPE_NOTICE, "server.stop.ok", "用户手动停止报文服务器成功.");
			json = Result.toResult(messageServer.getServerInfo());
		} catch (Exception e) {
			messageServer.alarm("messageServer", Alarm.ALARM_TYPE_ERROR, "server.stop.fail", "用户手动停止报文服务器失败!");
			logger.error(e.getMessage(), e);
			json = Result.toResult("stop", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>重启服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器信息
	 */
	@RequestMapping(value = "/restart", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ServerInfo> restart() {
		Result<ServerInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			messageServer.checkBusy();
			messageServer.reset();
			messageServer.alarm("messageServer", Alarm.ALARM_TYPE_NOTICE, "server.restart.ok", "用户手动重启报文服务器成功.");
			json = Result.toResult(messageServer.getServerInfo());
		} catch (Exception e) {
			messageServer.alarm("messageServer", Alarm.ALARM_TYPE_ERROR, "server.restart.fail", "用户手动重启报文服务器失败!");
			logger.error(e.getMessage(), e);
			json = Result.toResult("restart", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>暂停接收数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器信息
	 */
	@RequestMapping(value = "/pauseReceiver", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ServerInfo> pauseReceiver() {
		Result<ServerInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			messageServer.checkBusy();
			messageServer.pauseReceiver();
			json = Result.toResult(messageServer.getServerInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("pauseReceiver", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>恢复接收数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器信息
	 */
	@RequestMapping(value = "/resumeReceiver", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ServerInfo> resumeReceiver() {
		Result<ServerInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			messageServer.checkBusy();
			messageServer.resumeReceiver();
			json = Result.toResult(messageServer.getServerInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("resumeReceiver", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得服务器运行信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器运行信息
	 */
	@RequestMapping(value = "/getServerInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ServerInfo> getServerInfo() {
		Result<ServerInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			json = Result.toResult(messageServer.getServerInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getServerInfo", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得报文处理服务信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文处理服务信息
	 */
	@RequestMapping(value = "/getProcessServiceInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<ProcessServiceInfo> getProcessServiceInfo() {
		Result<ProcessServiceInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			json = Result.toResult(messageServer.getProcessServiceInfo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getProcessServiceInfo", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得所有的设备类型列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设备类型格式:代码或者代码|名称。
	 * @return 设备类型列表
	 */
	@RequestMapping(value = "/getAllDeviceType", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<String>> getAllDeviceType() {
		Result<Collection<String>> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			Collection<String> list = messageServer.getAllDeviceType();
			json = Result.toResult(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getAllDeviceType", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得所有的终端类型列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 终端类型格式:代码或者代码|名称。
	 * @return 终端类型列表
	 */
	@RequestMapping(value = "/getAllTerminalType", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<String>> getAllTerminalType() {
		Result<Collection<String>> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			Collection<String> list = messageServer.getAllTerminalType();
			json = Result.toResult(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getAllTerminalType", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得终端设备信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 唯一设备代码
	 * @return 终端设备信息,如果没有则返回null
	 */
	@RequestMapping(value = "/getDeviceInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<DeviceTerminalInfo> getDeviceInfo(
			@RequestParam(required = true, name = "code") String code) {
		Result<DeviceTerminalInfo> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			DeviceTerminalInfo dti = messageServer.getDeviceInfo(code);
			json = Result.toResult(dti);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("getDeviceInfo", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>查询所有的合法终端设备信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 包括在线设备和离线设备。 
	 * @param typeCode 设备类型代码,如果为空则查询所有
	 * @param code 设备代码,支持like模糊查询,如果为空则查询所有
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 合法终端设备信息列表
	 */
	@RequestMapping(value = "/findAllDeviceInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<DeviceTerminalInfo>> findAllDeviceInfo(
			@RequestParam(required = false, name = "typeCode") String typeCode,
			@RequestParam(required = false, name = "code") String code,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<DeviceTerminalInfo>> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			PageSet<DeviceTerminalInfo> ps = messageServer.getAllDeviceInfos(typeCode, code, page, pageSize);
			json = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("findAllDeviceInfo", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>获得在线终端信息列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param type 终端类型，如果为空则查询所有
	 * @param code 终端代码,支持like模糊查询,如果为空则查询所有
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 在线终端信息列表
	 */
	@RequestMapping(value = "/findAllOnlineTerminal", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<TerminalInfo>> findAllOnlineTerminal(
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = false, name = "code") String code,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<TerminalInfo>> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			if (!StringUtil.isBlank(code)) {
				code = "%" + code + "%";
			}
			PageSet<TerminalInfo> ps = PageSet.toPageSet(messageServer.getOnlineTerminals(type, code), page, pageSize);
			json = Result.toResult(ps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("findAllOnlineTerminal", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>返回在线终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型，如果为null则返回全部数量
	 * @return 在线终端数量
	 */
	@RequestMapping(value = "/onlineTerminalCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> onlineTerminalCount(
			@RequestParam(required = false, name = "type") String type) {
		Result<Integer> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			int count = messageServer.onlineTerminalCount(type);
			json = Result.toResult(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("onlineTerminalCount", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>终端下线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 是否成功
	 */
	@RequestMapping(value = "/offlineTerminal", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> offlineTerminal(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code) {
		Result<Boolean> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			messageServer.offlineTerminal(term);
			messageServer.alarm(term.id(), Alarm.ALARM_TYPE_NOTICE, "terminal.offline.ok",
					"成功迫使终端【type:{0},code:{1}】下线.", term.getType(), term.getCode());
			json = Result.TRUE;
		} catch (Exception e) {
			messageServer.alarm(term.id(), Alarm.ALARM_TYPE_ERROR, "terminal.offline.fail", "主动迫使终端[{0}]下线失败，原因：{1}",
					code, e.getMessage());
			logger.error(e.getMessage(), e);
			json = Result.toResult("offlineTerminal", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>查询终端是否在线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null,如果查询多个终端则使用逗号分割
	 * @return 终端是否在线，如果查询一个终端则返回Boolean,查询多个则返回List<Boolean>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/terminalActived", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<?> terminalActived(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code) {
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		List list = new ArrayList<>();
		StringUtil.split(code, StringUtil.CHAR_COMMA, (index, ele) -> list.add(ele));
		int len = list.size();
		if (len > 1) {
			for (int i = 0; i < len; i++) {
				TerminalId term = messageServer.terminalOf(type, list.get(i).toString());
				list.set(i, messageServer.isActived(term));
			}
			return Result.toResult(list);
		} else {
			TerminalId term = messageServer.terminalOf(type, list.get(0).toString());
			return Result.of(messageServer.isActived(term));
		}
	}

	/**
	 * <b>检查服务器是否损坏并修复。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 经过检查修复后服务器是否正常
	 */
	@RequestMapping(value = "/checkServerAndRepair", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> checkServerAndRepair() {
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		boolean ok = messageServer.checkServerAndRepair();
		return Result.of(ok);
	}

	/**
	 * <b>给终端发送Rpc指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法是阻塞方法，会等待终端返回的报文对象。
	 * @param cmd 指令对象
	 * @return 指令返回的报文对象
	 */
	@RequestMapping(value = "/sendRpcMessage", method = { RequestMethod.POST })
	public @ResponseBody Result<Object> sendRpcMessage(CMD cmd) {
		Result<Object> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			Object rtn = messageServer.sendRpcMessage(cmd);
			json = Result.toResult(rtn);
		} catch (Exception e) {
			String msg = _Utils.MSA.getMessage("rock.message.MessageServerController.sendMessage.fail",
					new Object[] { cmd.getTerminal(), e.getMessage(), cmd.getCmdCode(), cmd.getMessage() });
			messageServer.alarm(cmd.getTerminal().id(), Alarm.ALARM_TYPE_ERROR, "sendMessage.fail", msg);
			logger.error(e.getMessage(), e);
			json = Result.toResult("sendRpcMessage", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>给终端发送Rpc指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法是阻塞方法，会等待终端返回的报文对象。
	 * @param cmd 指令对象
	 * @return 指令返回的报文对象
	 */
	@RequestMapping(value = "/sendRpcMessage_json", method = { RequestMethod.POST })
	public @ResponseBody Result<Object> sendRpcMessage_json(@RequestBody CMD cmd) {
		return sendRpcMessage(cmd);
	}

	/**
	 * <b>给终端发送指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 同步指令不会提交到指令站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功,3-设备不在线,已经提交到指令站台)
	 */
	@RequestMapping(value = "/sendMessage", method = { RequestMethod.POST })
	public @ResponseBody Result<Integer> sendMessage(CMD cmd) {
		Result<Integer> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			Integer flag = messageServer.sendMessage(cmd);
			json = Result.toResult(flag);
		} catch (Exception e) {
			String msg = _Utils.MSA.getMessage("rock.message.MessageServerController.sendMessage.fail",
					new Object[] { cmd.getTerminal(), e.getMessage(), cmd.getCmdCode(), cmd.getMessage() });
			messageServer.alarm(cmd.getTerminal().id(), Alarm.ALARM_TYPE_ERROR, "sendMessage.fail", msg);
			logger.error(e.getMessage(), e);
			json = Result.toResult(Integer.valueOf(0), e);
		}
		return json;
	}

	/**
	 * <b>给终端发送指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 同步指令不会提交到指令站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功,3-设备不在线,已经提交到指令站台)
	 */
	@RequestMapping(value = "/sendMessage_json", method = { RequestMethod.POST })
	public @ResponseBody Result<Integer> sendMessage_json(@RequestBody CMD cmd) {
		return sendMessage(cmd);
	}

	/**
	 * <b>给终端直接发送消息指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不检查是否在线，也不会提交到站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功)
	 */
	@RequestMapping(value = "/directMessage", method = { RequestMethod.POST })
	public @ResponseBody Result<Integer> directMessage(CMD cmd) {
		Result<Integer> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			Integer flag = messageServer.directMessage(cmd);
			json = Result.toResult(flag);
		} catch (Exception e) {
			String msg = _Utils.MSA.getMessage("rock.message.MessageServerController.sendMessage.fail",
					new Object[] { cmd.getTerminal(), e.getMessage(), cmd.getCmdCode(), cmd.getMessage() });
			messageServer.alarm(cmd.getTerminal().id(), Alarm.ALARM_TYPE_ERROR, "sendMessage.fail", msg);
			logger.error(e.getMessage(), e);
			json = Result.toResult(Integer.valueOf(0), e);
		}
		return json;
	}

	/**
	 * <b>给终端直接发送消息指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不检查是否在线，也不会提交到站台。
	 * @param cmd 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功)
	 */
	@RequestMapping(value = "/directMessage_json", method = { RequestMethod.POST })
	public @ResponseBody Result<Integer> directMessage_json(@RequestBody CMD cmd) {
		return directMessage(cmd);
	}

	/**
	 * <b>提交消息指令到站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 暂时不发送，仅仅提交消息到站台。
	 * @param cmd 指令对象
	 * @return 提交是否成功
	 */
	@RequestMapping(value = "/submitMessage", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> submitMessage(CMD cmd) {
		Result<Boolean> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		try {
			boolean ok = messageServer.submitMessage(cmd);
			json = Result.of(ok);
		} catch (Exception e) {
			String msg = _Utils.MSA.getMessage("rock.message.MessageServerController.submitMessage.fail",
					new Object[] { cmd.getTerminal(), e.getMessage(), cmd.getCmdCode(), cmd.getMessage() });
			messageServer.alarm(cmd.getTerminal().id(), Alarm.ALARM_TYPE_ERROR, "submitMessage.fail", msg);
			logger.error(e.getMessage(), e);
			json = Result.toResult("submitMessage", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>提交消息指令到站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 暂时不发送，仅仅提交消息到站台。
	 * @param cmd 指令对象
	 * @return 提交是否成功
	 */
	@RequestMapping(value = "/submitMessage_json", method = RequestMethod.POST)
	public @ResponseBody Result<Boolean> submitMessage_json(@RequestBody CMD cmd) {
		return submitMessage(cmd);
	}

	/**
	 * <b>返回站台上指定终端的待发送指令数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 终端的待发送指令数
	 */
	@RequestMapping(value = "/commandCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> commandCount(@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code) {
		Result<Integer> json;
		MessageServer<CMD, TERM, TYPE, CODE> messageServer = this.getMessageServer();
		TERM term = messageServer.terminalOf(type, code);
		try {
			int count = messageServer.getCommandStation().count(term);
			json = Result.toResult(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("commandCount", e.getMessage(), e);
		}
		return json;
	}

}
