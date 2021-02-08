package org.quincy.rock.message.server;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.comm.CommunicateException;
import org.quincy.rock.comm.DefaultMessageService;
import org.quincy.rock.comm.MessageListener;
import org.quincy.rock.comm.MessageSender;
import org.quincy.rock.comm.RpcMessageSender;
import org.quincy.rock.comm.cmd.CommandStation;
import org.quincy.rock.comm.cmd.TerminalCommand;
import org.quincy.rock.comm.communicate.CommunicateAdapter;
import org.quincy.rock.comm.communicate.CommunicateListener;
import org.quincy.rock.comm.communicate.CommunicateServer;
import org.quincy.rock.comm.communicate.CommunicateServerListener;
import org.quincy.rock.comm.communicate.DeviceProvider;
import org.quincy.rock.comm.communicate.DeviceProvider.DeviceInfo;
import org.quincy.rock.comm.communicate.TerminalChannel;
import org.quincy.rock.comm.communicate.TerminalChannelMapping;
import org.quincy.rock.comm.communicate.TerminalId;
import org.quincy.rock.comm.process.QueueMessageProcessService;
import org.quincy.rock.core.bean.BeanUtil;
import org.quincy.rock.core.cache.Alarm;
import org.quincy.rock.core.cache.AlarmManager;
import org.quincy.rock.core.cache.HasTimestamp;
import org.quincy.rock.core.function.EachFunction;
import org.quincy.rock.core.function.ValueConsumer;
import org.quincy.rock.core.os.ProcessServiceInfo;
import org.quincy.rock.core.util.CoreUtil;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.message._Utils;
import org.quincy.rock.message.vo.DeviceTerminalInfo;
import org.quincy.rock.message.vo.MessageProcessThreadInfo;
import org.quincy.rock.message.vo.ServerInfo;
import org.quincy.rock.message.vo.TerminalInfo;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * <b>报文服务器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月11日 下午5:40:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class MessageServer<CMD extends TerminalCommand<TERM, TYPE, CODE>, TERM extends TerminalId<TYPE, CODE>, TYPE, CODE>
		implements HasTimestamp {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(MessageServer.class);

	/**
	 * 报文消息服务。
	 */
	private DefaultMessageService messageService;
	/**
	 * 服务器名称。
	 */
	private String name;
	/**
	 * 服务器创建时间。
	 */
	private final long createTime = System.currentTimeMillis();
	/**
	 * 服务器启动或停止时间。
	 */
	private long timestamp;
	/**
	 * 操作正忙，不可中断。
	 */
	private boolean busy;
	/**
	 * 正在运行标志。
	 */
	private boolean runningFlag;
	/**
	 * 重启等待秒数。
	 */
	private int resetSecs = 1;
	/**
	 * 告警管理器。
	 */
	private AlarmManager alarmManager;
	/**
	 * 通讯服务器监听器。
	 */
	private CommunicateServerListener<? extends TerminalChannel> communicateServerListener;
	/**
	 * 通讯器监听。
	 */
	private CommunicateListener<? extends TerminalChannel> communicateListener;

	/**
	 * 报文监听器。
	 */
	private MessageListener<?> messageListener;
	/**
	 * 终端信息拦截器。
	 */
	private Function<TerminalInfo, TerminalInfo> terminalInterceptor;

	/**
	 * 要显示的通讯器属性列表(格式:maxAtive:最大连接数,activeCount:活动连接数)。
	 */
	private String commAttrs;

	/**
	 * 设备信息提供者。
	 */
	private DeviceProvider<?> deviceProvider;

	/**
	 * <b>获得报文消息服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文消息服务
	 */
	public DefaultMessageService getMessageService() {
		return messageService;
	}

	/**
	 * <b>设置报文消息服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageService 报文消息服务
	 */
	public void setMessageService(DefaultMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * <b>获得服务器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>设置服务器名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 服务器名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <b>获得告警管理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 告警管理器
	 */
	public AlarmManager getAlarmManager() {
		return alarmManager;
	}

	/**
	 * <b>设置告警管理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param alarmManager 告警管理器
	 */
	public void setAlarmManager(AlarmManager alarmManager) {
		this.alarmManager = alarmManager;
	}

	/**
	 * <b>获得通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯服务器监听器
	 */
	public CommunicateServerListener<? extends TerminalChannel> getCommunicateServerListener() {
		return communicateServerListener;
	}

	/**
	 * <b>设置通讯服务器监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param communicateServerListener 通讯服务器监听器
	 */
	public void setCommunicateServerListener(
			CommunicateServerListener<? extends TerminalChannel> communicateServerListener) {
		this.communicateServerListener = communicateServerListener;
	}

	/**
	 * <b>获得通讯器监听。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯器监听
	 */
	public CommunicateListener<? extends TerminalChannel> getCommunicateListener() {
		return communicateListener;
	}

	/**
	 * <b>设置通讯器监听。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param communicateListener 通讯器监听
	 */
	public void setCommunicateListener(CommunicateListener<? extends TerminalChannel> communicateListener) {
		this.communicateListener = communicateListener;
	}

	/**
	 * <b>获得报文监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文监听器
	 */
	public MessageListener<?> getMessageListener() {
		return messageListener;
	}

	/**
	 * <b>设置报文监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param messageListener 报文监听器
	 */
	public void setMessageListener(MessageListener<?> messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * <b>获得终端信息拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端信息拦截器
	 */
	public Function<TerminalInfo, TerminalInfo> getTerminalInterceptor() {
		return terminalInterceptor;
	}

	/**
	 * <b>设置终端信息拦截器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param terminalInterceptor 终端信息拦截器
	 */
	public void setTerminalInterceptor(Function<TerminalInfo, TerminalInfo> terminalInterceptor) {
		this.terminalInterceptor = terminalInterceptor;
	}

	/**
	 * <b>获得要显示的通讯器属性列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 格式:maxAtive:最大连接数,activeCount:活动连接数。
	 * @return 要显示的通讯器属性列表
	 */
	public String getCommAttrs() {
		return commAttrs;
	}

	/**
	 * <b>设置要显示的通讯器属性列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 格式:maxAtive:最大连接数,activeCount:活动连接数。
	 * @param commAttrs 要显示的通讯器属性列表
	 */
	public void setCommAttrs(String commAttrs) {
		this.commAttrs = commAttrs;
	}

	/**
	 * <b>获得设备信息提供者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 设备信息提供者
	 */
	public DeviceProvider<?> getDeviceProvider() {
		return deviceProvider;
	}

	/**
	 * <b>设置设备信息提供者。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param deviceProvider 设备信息提供者
	 */
	public void setDeviceProvider(DeviceProvider<?> deviceProvider) {
		this.deviceProvider = deviceProvider;
	}

	/**
	 * <b>获得重启等待秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 重启等待秒数
	 */
	public int getResetSecs() {
		return resetSecs;
	}

	/**
	 * <b>设置重启等待秒数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param resetSecs 重启等待秒数
	 */
	public void setResetSecs(int resetSecs) {
		this.resetSecs = resetSecs;
	}

	/**
	 * <b>告警。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 告警源
	 * @param type 告警类型
	 * @param code 告警代码
	 * @param descr 告警信息
	 * @param params 告警信息参数
	 */
	public void alarm(Object source, int type, String code, String descr, Object... params) {
		if (alarmManager != null) {
			try {
				if (params != null && params.length > 0)
					descr = MessageFormat.format(descr, params);
				String name = _Utils.MSA.getMessage("alarm." + code);
				alarmManager.addAlarm(Alarm.of(type, code, name, descr, source));
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
	}

	/**
	 * <b>获得通讯服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 通讯服务器
	 */
	protected CommunicateServer getCommunicateServer() {
		return (CommunicateServer) this.getMessageService().getCommunicator();
	}

	/**
	 * <b>获得报文队列处理服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文队列处理服务
	 */
	protected QueueMessageProcessService getMessageProcessService() {
		return (QueueMessageProcessService) this.getMessageService().getMessageProcessService();
	}

	/**
	 * <b>获得终端通道映射器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 终端通道映射器
	 */
	protected TerminalChannelMapping<TerminalChannel> getTerminalChannelMapping() {
		TerminalChannelMapping mapping = this.getMessageService().getTerminalChannelMapping();
		return mapping;
	}

	/**
	 * <b>获得指令站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 指令站台
	 */
	public CommandStation<TERM> getCommandStation() {
		CommandStation<TERM> station = this.getMessageService().getCommandStation();
		return station;
	}

	/**
	 * <b>获得处理服务信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理服务信息
	 */
	public ProcessServiceInfo getProcessServiceInfo() {
		QueueMessageProcessService qmps = this.getMessageProcessService();
		ProcessServiceInfo psi = new ProcessServiceInfo("mainMessageProcessService", getName());
		psi.setMaxThreadCount(qmps.getMaxThreadCount());
		psi.setThreadCount(qmps.activeThreadCount());
		psi.setTimeout(qmps.getTimeout());
		psi.setCapacity(qmps.getCapacity());
		for (int i = 0; i < psi.getThreadCount(); i++) {
			MessageProcessThreadInfo pti = new MessageProcessThreadInfo(i);
			pti.setWaitCount(qmps.count(i));
			pti.setWaitSecond(qmps.waitSeconds(i));
			pti.setProcessMillis(qmps.processMillis(i));
			pti.setResolveMillis(qmps.resolveMillis(i));
			pti.setTransferMillis(qmps.transferMillis(i));
			pti.setProcessCount(qmps.processCount(i));
			psi.addProcessThread(pti);
		}
		return psi;
	}

	/**
	 * <b>获得服务器信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器信息
	 */
	public ServerInfo getServerInfo() {
		ServerInfo si = new ServerInfo();
		si.setName(name); //报文服务器名称。
		si.setRunning(running()); //是否运行
		si.setReceiverPausing(receiverPausing()); //是否服务器数据接收者处于暂停状态。
		si.setBusy(busy()); //操作正忙，不可中断。
		si.setCreateTime(createTime()); // 服务器创建时间。
		si.setTimestamp(timestamp);// 服务器启动或停止时间。
		si.setHost(getCommunicateServer().getHost());// 服务器主机。
		si.setPort(getCommunicateServer().getPort());// 服务器端口号。
		si.setCommType(getCommunicateServer().getClass().getName());//通讯器类型。
		Map<String, Object> srcMap = BeanUtil.toMap(getCommunicateServer(), true, Integer.class, String.class);
		if (StringUtil.isBlank(commAttrs)) {
			si.setCommAttrs(srcMap);//连接器属性集合。
		} else {
			Map<String, Object> map = new HashMap<>();
			StringUtil.split(commAttrs, 0, commAttrs.length(), new char[] { ':', ',' },
					new EachFunction<CharSequence, Boolean>() {
						private String key;

						@Override
						public Boolean each(int index, CharSequence ele) {
							if (index % 2 == 0) {
								key = ele.toString();
							} else if (srcMap.containsKey(key)) {
								map.put(ele.toString(), srcMap.get(key));
								key = null;
							}
							return false;
						}
					});
			si.setCommAttrs(map);//连接器属性集合。
		}

		si.setMaxTermCount(getTerminalChannelMapping().getMaxCount());//最大终端数。
		si.setTermCount(getTerminalChannelMapping().count());//当前终端数。
		si.setExpire(getTerminalChannelMapping().getExpire()); //报文过期秒数
		//默认报文内容类型。
		si.setContentType(getMessageService().getMessageParserFactory().getMessageHeadParser().getDefaultContentType());
		return si;
	}

	/**
	 * <b>返回在线终端数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termType 终端类型，如果为null则返回全部数量
	 * @return 在线终端数量
	 */
	public int onlineTerminalCount(String termType) {
		int count = 0;
		if (StringUtil.isBlank(termType))
			count = getTerminalChannelMapping().count();
		else {
			for (TerminalChannel tc : getTerminalChannelMapping().channels()) {
				String type = String.valueOf(tc.getRemoteType());
				if (type.equals(termType)) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * <b>获得所有的终端类型列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 终端类型格式:代码或者代码|名称。
	 * @return 终端类型列表
	 */
	public Collection<String> getAllTerminalType() {
		Collection<String> set = new HashSet<>();
		List<TerminalInfo> list = getOnlineTerminals(null, null);
		for (TerminalInfo ti : list) {
			set.add(ti.getType());
		}
		return set;
	}

	/**
	 * <b>获得所有在线终端列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param termType 终端类型，如果为空则查询所有
	 * @param code 终端代码,支持like模糊查询(例如:%01%),如果为空则查询所有
	 * @return 在线终端列表
	 */
	public List<TerminalInfo> getOnlineTerminals(String termType, String code) {
		List<TerminalInfo> list = new ArrayList<>();
		if (StringUtil.isBlank(termType)) {
			for (TerminalChannel tc : getTerminalChannelMapping().channels()) {
				TerminalInfo ti = new TerminalInfo(tc);
				if (!StringUtil.isBlank(code) && !StringUtil.isLiked(ti.getCode(), code, "_", "%")) {
					continue; //
				}
				if (this.terminalInterceptor != null)
					ti = this.terminalInterceptor.apply(ti);
				list.add(ti);
			}
		} else {
			for (TerminalChannel tc : getTerminalChannelMapping().channels()) {
				String type = String.valueOf(tc.getRemoteType());
				if (type.equals(termType)) {
					TerminalInfo ti = new TerminalInfo(tc);
					if (!StringUtil.isBlank(code) && !StringUtil.isLiked(ti.getCode(), code, "_", "%")) {
						continue; //
					}
					if (this.terminalInterceptor != null)
						ti = this.terminalInterceptor.apply(ti);
					list.add(ti);
				}
			}
		}
		return list;
	}

	/**
	 * <b>获得所有的终端类型列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设备类型格式:代码或者代码|名称。
	 * @return 终端类型列表
	 */
	public Collection<String> getAllDeviceType() {
		return deviceProvider == null ? Collections.EMPTY_LIST : deviceProvider.getAllDeviceType();
	}

	/**
	 * <b>获得终端设备信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 唯一设备代码
	 * @return 终端设备信息,如果没有则返回null
	 */
	public DeviceTerminalInfo getDeviceInfo(String code) {
		if (deviceProvider != null) {
			DeviceInfo<?> di = deviceProvider.findDevice(code);
			if (di != null) {
				TerminalChannel ch = null;
				for (TerminalChannel c : getTerminalChannelMapping().channels()) {
					if (StringUtils.equals(code, CoreUtil.toString(c.getRemoteCode()))) {
						ch = c;
						break;
					}
				}
				DeviceTerminalInfo dti = new DeviceTerminalInfo(ch);
				dti.fromDeviceInfo(di);
				if (this.terminalInterceptor != null)
					dti = (DeviceTerminalInfo) this.terminalInterceptor.apply(dti);
				return dti;
			}
		}
		return null;
	}

	/**
	 * <b>查询所有的合法终端设备信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param typeCode 设备类型代码,如果为空则查询所有
	 * @param code 设备代码,支持like模糊查询(例如:%01%),如果为空则查询所有
	 * @param page 页码(从0开始)
	 * @param pageSize 页大小
	 * @return 一页合法终端设备
	 */
	public PageSet<DeviceTerminalInfo> getAllDeviceInfos(String typeCode, String code, int page, int pageSize) {
		PageSet<DeviceTerminalInfo> ps = new PageSet<>(page, pageSize);
		if (deviceProvider != null) {
			//先获得所有的在线终端
			Map<Object, TerminalChannel> map = new HashMap<>();
			for (TerminalChannel c : getTerminalChannelMapping().channels()) {
				map.put(CoreUtil.toString(c.getRemoteCode()), c);
			}
			//查询所有合法设备
			PageSet<? extends DeviceInfo<?>> list = deviceProvider.findAllDevice(typeCode, code, page, pageSize);
			ps.setTotalCount(list.getTotalCount());
			for (DeviceInfo di : list) {
				TerminalChannel ch = map.get(di.getCode());
				DeviceTerminalInfo dti = new DeviceTerminalInfo(ch);
				dti.fromDeviceInfo(di);
				if (this.terminalInterceptor != null)
					dti = (DeviceTerminalInfo) this.terminalInterceptor.apply(dti);
				ps.getContent().add(dti);
			}
		} else {
			ps.setTotalCount(0);
		}
		return ps;
	}

	/**
	 * <b>操作正忙(不可中断)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 操作正忙(不可中断)
	 */
	public boolean busy() {
		return busy;
	}

	/**
	 * <b>检查是否操作正忙(不可中断)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void checkBusy() {
		if (busy()) {
			String msg = _Utils.MSA.getMessage("rock.message.MessageServer.busy.error");
			alarm("messageServer", Alarm.ALARM_TYPE_WARN, "checkBusy", msg);
			throw new CommunicateException(msg);
		}
	}

	private void busy(boolean busy) {
		synchronized (this) {
			this.busy = busy;
		}
	}

	/**
	 * <b>使终端下线。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param term 要下线的终端
	 */
	public void offlineTerminal(TerminalId term) {
		this.getMessageService().offlineTerminal(term);
	}

	/**
	 * <b>终端是否是活动的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param term 终端id
	 * @return 终端是否是活动的
	 */
	public boolean isActived(TerminalId term) {
		return getTerminalChannelMapping().hasTerminal(term);
	}

	/**
	 * <b>启动报文服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void start() {
		busy(true);
		try {
			//确保监听器已经配置
			this.configListener();
			//启动
			getMessageProcessService().start();
			getCommunicateServer().start();
			this.runningFlag = true; //通讯器已经运行
			this.updateTimestamp();
			logger.info("报文服务器[{}]已启动运行", this.getName());
		} finally {
			busy(false);
		}
	}

	/**
	 * <b>停止报文服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void stop() {
		busy(true);
		try {
			//停止
			getCommunicateServer().stop();
			getMessageProcessService().stop();
			this.runningFlag = false; //通讯器未运行
			this.updateTimestamp();
			logger.info("报文服务器[{}]已停止运行", this.getName());
		} finally {
			busy(false);
		}
	}

	/**
	 * <b>重新启动报文服务器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void reset() {
		busy(true);
		try {
			//停止
			getCommunicateServer().stop();
			getMessageProcessService().reset();
			//n秒后再重启
			DateUtil.sleep(1000 * this.resetSecs);
			//启动
			this.configListener();
			getCommunicateServer().start();
			this.runningFlag = true; //通讯器已经运行
			this.updateTimestamp();
			logger.info("报文服务器[{}]已重新启动运行", this.getName());
		} finally {
			busy(false);
		}
	}

	/**
	 * <b>返回报文服务器是否正在运行。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报文服务器是否正在运行
	 */
	public boolean running() {
		return getCommunicateServer().isRunning();
	}

	/**
	 * <b>服务器数据接收者暂停接收数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void pauseReceiver() {
		busy(true);
		try {
			getMessageProcessService().stop();
		} finally {
			busy(false);
		}
	}

	/**
	 * <b>>服务器数据接收者恢复接收数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void resumeReceiver() {
		busy(true);
		try {
			getMessageProcessService().start();
		} finally {
			busy(false);
		}
	}

	/**
	 * <b>返回是否服务器数据接收者处于暂停状态。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否服务器数据接收者处于暂停状态
	 */
	public boolean receiverPausing() {
		return getMessageProcessService().isStopped();
	}

	/**
	 * <b>返回服务器创建时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器创建时间
	 */
	public long createTime() {
		return createTime;
	}

	/**
	 * <b>返回服务器启动或停止时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务器启动或停止时间
	 */
	@Override
	public long timestamp() {
		return timestamp;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * <b>销毁服务器实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void destroy() {
		busy(true);
		try {
			getCommunicateServer().destroy();
			getMessageProcessService().destroy();
			logger.info("报文服务器[{}]已关闭.", this.getName());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} finally {
			busy(false);
		}
	}

	/**
	 * <b>创建终端实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 终端类型
	 * @param code 终端代码
	 * @return 终端实例
	 */
	public abstract TERM terminalOf(@Nullable String type, @NonNull String code);

	/**
	 * <b>定时检查报文服务器运行状态。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 如果服务器是损坏的则自动重启。
	 */
	//@Scheduled(cron = "0 0/2 * * * ?")
	public boolean checkServerAndRepair() {
		boolean ok = true;
		if (runningFlag && !running()) {
			//应该处于运行状态但通讯器却损坏了，则重新起动
			try {
				logger.info("检测到报文服务器异常终止，尝试重新启动...");
				this.checkBusy();
				this.reset();
				logger.info("经过我的努力尝试，异常终止的报文服务器终于重新启动成功了。");
				alarm("messageServer", Alarm.ALARM_TYPE_WARN, "checkServer.repair", "成功监测到报文服务器异常终止并成功修复重启。");
			} catch (Exception e) {
				logger.error("尝试重新启动异常终止的报文服务器出现错误：" + e.getMessage(), e);
				alarm("messageServer", Alarm.ALARM_TYPE_ERROR, "checkServer.error", "成功监测到报文服务器异常终止但修复重启失败！");
				ok = false;
			}
		} else
			logger.debug("检测报文服务器状态:" + (running() ? "正常运行状态." : "正常停止状态."));
		return ok;
	}

	/**
	 * <b>给单个终端发送报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param command 指令对象
	 * @return 返回值
	 */
	public <T> T sendRpcMessage(CMD cmd) {
		if (running()) {
			this.checkBusy();
			cmd = correction(cmd);
			TERM term = cmd.getTerminal();
			RpcMessageSender sender;
			if (cmd instanceof RpcMessageSender) {
				sender = (RpcMessageSender) cmd; //使用指令模板自定义的发送器发送报文
			} else {
				sender = (RpcMessageSender) getMessageService();
			}
			//send
			if (sender.isOnline(term)) {
				SendConsumer sendConsumer = new SendConsumer(term, cmd.getCmdCode(), cmd.getMessage());
				Object rtn = sender.sendRpcMessage(term, cmd.getMsgId(), cmd.getCmdCode(), cmd.getMessage(),
						cmd.getAttachment());
				sendConsumer.call(Boolean.TRUE);
				return (T) rtn;
			} else
				throw new CommunicateException("设备不在线!");
		} else
			throw new CommunicateException("服务器未运行!");
	}

	/**
	 * <b>给终端发送报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param command 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功,3-设备不在线,已经提交到指令站台)
	 */
	public int sendMessage(CMD cmd) {
		if (running()) {
			this.checkBusy();
			cmd = correction(cmd);
			TERM term = cmd.getTerminal();
			//sender
			MessageSender sender;
			if (cmd instanceof MessageSender) {
				sender = (MessageSender) cmd;
			} else {
				sender = (MessageSender) getMessageService();
			}
			//
			SendConsumer sendConsumer = new SendConsumer(term, cmd.getCmdCode(), cmd.getMessage());
			if (term.isPattern()) {
				//群发
				sender.sendMessage(term, cmd.getMsgId(), cmd.getCmdCode(), cmd.getMessage(), cmd.getAttachment(),
						cmd.isAsync(), sendConsumer);
				return cmd.isAsync() ? 1 : (sendConsumer.value() ? 2 : 0);
			} else if (sender.isOnline(term)) {
				//终端在线
				sender.sendMessage(term, cmd.getMsgId(), cmd.getCmdCode(), cmd.getMessage(), cmd.getAttachment(),
						cmd.isAsync(), sendConsumer);
				return cmd.isAsync() ? 1 : (sendConsumer.value() ? 2 : 0);
			} else if (cmd.isAsync()) {
				cmd.consumer(sendConsumer);
				//添加到指令站台
				getCommandStation().putCommand(term, cmd);
				//
				String msg = _Utils.MSA.getMessage("rock.message.MessageServer.sendMessage.station",
						new Object[] { term, cmd.getCmdCode(), cmd.getMessage() });
				alarm(term.id(), Alarm.ALARM_TYPE_NOTICE, "sendMessage.station", msg);
				return 3;
			} else
				throw new CommunicateException("设备不在线!");
		} else
			throw new CommunicateException("服务器未运行!");
	}

	/**
	 * <b>直接发送消息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不检查是否在线。
	 * @param command 指令对象
	 * @return 发送返回值(0-发送报文失败,1-提交异步发送,2-同步发送成功)
	 */
	public int directMessage(CMD cmd) {
		if (running()) {
			this.checkBusy();
			cmd = correction(cmd);
			TERM term = cmd.getTerminal();
			//sender
			MessageSender sender;
			if (cmd instanceof MessageSender) {
				sender = (MessageSender) cmd;
			} else {
				sender = (MessageSender) getMessageService();
			}
			//send
			SendConsumer sendConsumer = new SendConsumer(term, cmd.getCmdCode(), cmd.getMessage());
			sender.sendMessage(term, cmd.getMsgId(), cmd.getCmdCode(), cmd.getMessage(), cmd.getAttachment(),
					cmd.isAsync(), sendConsumer);
			return cmd.isAsync() ? 1 : (sendConsumer.value() ? 2 : 0);
		} else
			throw new CommunicateException("服务器未运行!");
	}

	/**
	 * <b>提交消息到站台。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 暂时不发送，仅仅提交消息到站台。
	 * @param cmd 指令对象
	 * @return 提交是否成功
	 */
	public boolean submitMessage(CMD cmd) {
		if (running()) {
			this.checkBusy();
			//添加到指令站台
			cmd = correction(cmd);
			TERM term = cmd.getTerminal();
			cmd.consumer(new SendConsumer(term, cmd.getCmdCode(), cmd.getMessage()));
			getCommandStation().putCommand(term, cmd);
			//
			String msg = _Utils.MSA.getMessage("rock.message.MessageServer.submitMessage.success",
					new Object[] { term, cmd.getCmdCode(), cmd.getMessage() });
			alarm(term.id(), Alarm.ALARM_TYPE_NOTICE, "sendMessage.station", msg);
			return true;
		} else
			throw new CommunicateException("服务器未运行!");
	}

	/**
	 * <b>修正指令。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param cmd 指令
	 * @return 修正后的指令
	 */
	private CMD correction(CMD cmd) {
		TERM term = cmd.getTerminal();
		Map<String, Object> attachment = null;
		if (getTerminalChannelMapping().hasTerminal(term)) {
			attachment = getTerminalChannelMapping().sendedContext(term);
		}
		return cmd.correction(attachment);
	}

	/**
	 * <b>配置监听器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	private void configListener() {
		if (noConfigListener) {
			if (communicateServerListener != null) {
				getCommunicateServer().addCommunicateServerListener(communicateServerListener);
			}
			if (communicateListener != null) {
				getCommunicateServer().addCommunicateListener(0, communicateListener);
			}
			if (messageListener != null) {
				getMessageService().addMessageListener(messageListener);
			}
			getCommunicateServer().addCommunicateListener(new CommunicateAdapter<TerminalChannel>() {
				@Override
				public void exceptionCaught(TerminalChannel channel, Throwable e) {
					TerminalId term = channel.remote();
					String msg = _Utils.MSA.getMessage("rock.message.MessageServer.channel.error",
							new Object[] { term.getType(), term.getCode(), term.getAddress(), e.getMessage() });
					alarm(term.id(), Alarm.ALARM_TYPE_WARN, "channelError", msg);
				}
			});
			noConfigListener = false;
		}
	}

	/**
	 * 监听器还未配置。
	 */
	private boolean noConfigListener = true;

	/**
	 * 报文发送回调Consumer。
	 */
	private class SendConsumer implements ValueConsumer<Boolean> {
		private TerminalId term;
		private Object cmdCode;
		private Object message;

		/**
		 * 是否发送成功。
		 */
		private boolean success;

		public SendConsumer(TerminalId term, Object cmdCode, Object message) {
			this.term = term;
			this.cmdCode = cmdCode;
			this.message = message;
		}

		@Override
		public Boolean value() {
			return success;
		}

		@Override
		public void call(Boolean ok) {
			success = Boolean.TRUE.equals(ok);
			if (success) {
				String msg = _Utils.MSA.getMessage("rock.message.MessageServer.sendMessage.success",
						new Object[] { term, cmdCode, message });
				alarm(term.id(), Alarm.ALARM_TYPE_NOTICE, "sendMessage.success", msg);
			} else {
				String msg = _Utils.MSA.getMessage("rock.message.MessageServer.sendMessage.fail",
						new Object[] { term, cmdCode, message });
				alarm(term.id(), Alarm.ALARM_TYPE_ERROR, "sendMessage.fail", msg);
			}
		}
	}
}