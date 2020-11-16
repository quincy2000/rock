package org.quincy.rock.message.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quincy.rock.comm.cmd.Command;
import org.quincy.rock.comm.cmd.CommandStation;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.message.server.MessageServer;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>消息指令站台提供给客户端的接口。</b>
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
@RequestMapping("station")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CommandStationController extends MessageController {
	/**
	 * logger。
	 */
	protected static final Logger logger = RockUtil.getLogger(CommandStationController.class);

	/**
	 * <b>返回站台上等待发送消息的终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型，如果为null则返回全部数量
	 * @return 等待发送消息的终端数量
	 */
	@RequestMapping(value = "/terminalCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> terminalCount(@RequestParam(required = false, name = "type") String type) {
		Result<Integer> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			int count;
			if (type == null) {
				count = messageServer.getCommandStation().count();
			} else {
				count = 0;
				CommandStation<TerminalId> station = messageServer.getCommandStation();
				Iterable<TerminalId> it = station.terminals();
				for (TerminalId term : it) {
					if (term.getType().toString().equals(type))
						count++;
				}
			}
			json = Result.toResult(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("terminalCount", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>返回站台上总的待发送指令数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型，如果为null则返回全部数量
	 * @return 终端的待发送指令数
	 */
	@RequestMapping(value = "/totalCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Integer> totalCount(@RequestParam(required = false, name = "type") String type) {
		Result<Integer> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			int count = 0;
			CommandStation<TerminalId> station = messageServer.getCommandStation();
			Iterable<TerminalId> it = station.terminals();
			if (type == null) {
				for (TerminalId term : it) {
					count += station.count(term);
				}
			} else {
				for (TerminalId term : it) {
					if (term.getType().toString().equals(type)) {
						count += station.count(term);
					}
				}
			}
			json = Result.toResult(count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("totalCount", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>返回站台上等待发送消息的终端列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型，如果为null则返回全部数量
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页等待发送消息的终端列表
	 */
	@RequestMapping(value = "/retrieveTerminals", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<PageSet<TerminalId>> retrieveTerminals(
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "page") int page,
			@RequestParam(required = true, name = "pageSize") int pageSize) {
		Result<PageSet<TerminalId>> json;
		MessageServer messageServer = this.getMessageServer();
		try {
			CommandStation<TerminalId> station = messageServer.getCommandStation();
			Iterable<TerminalId> it = station.terminals();
			List<TerminalId> list = new ArrayList<>(station.count());
			for (TerminalId term : it) {
				if (type == null || term.getType().toString().equals(type)) {
					list.add(term);
				}
			}
			json = Result.toResult(PageSet.toPageSet(list, page, pageSize));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("retrieveTerminals", e.getMessage(), e);
		}
		return json;
	}

	/**
	 * <b>从站台检索终端待发送的指令列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型,可以为null
	 * @param code 终端代码,不能为null
	 * @return 终端待发送的指令列表
	 */
	@RequestMapping(value = "/retrieveCommands", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Collection<Command<TerminalId>>> retrieveCommands(
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = true, name = "code") String code) {
		Result<Collection<Command<TerminalId>>> json;
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
		try {
			Collection list = CoreUtil.toList(messageServer.getCommandStation().getCommands(term, -1));
			json = Result.toResult(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Result.toResult("retrieveCommands", e.getMessage(), e);
		}
		return json;
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
		MessageServer messageServer = this.getMessageServer();
		TerminalId term = messageServer.terminalOf(type, code);
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
