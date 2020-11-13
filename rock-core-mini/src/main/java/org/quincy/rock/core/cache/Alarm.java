package org.quincy.rock.core.cache;

import java.util.Comparator;

import org.quincy.rock.core.vo.Vo;

/**
 * <b>警告信息。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年8月16日 上午10:46:14</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class Alarm extends Vo<Integer> implements HasTimestamp {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -5062100041496485954L;
	/**
	 * 一般通知消息。
	 */
	public static final int ALARM_TYPE_NOTICE = 1;
	/**
	 * 警告消息。
	 */
	public static final int ALARM_TYPE_WARN = 2;
	/**
	 * 错误消息。
	 */
	public static final int ALARM_TYPE_ERROR = 3;

	/**
	 * alarmComparator。
	 */
	public static Comparator<Alarm> alarmComparator = new Comparator<Alarm>() {
		@Override
		public int compare(Alarm a1, Alarm a2) {
			return (int) (a2.getTime() - a1.getTime());
		}
	};

	/**
	 * 警告信息id。
	 */
	private int id;
	/**
	 * 告警代码。
	 */
	private String code;
	/**
	 * 告警名称。
	 */
	private String name;
	/**
	 * 报警类型。
	 */
	private int type = ALARM_TYPE_NOTICE;
	/**
	 * 是否需要提醒。
	 */
	private boolean remind = true;
	/**
	 * 报警源。
	 */
	private Object source;
	/**
	 * 告警时间。
	 */
	private long time = System.currentTimeMillis();
	/**
	 * 告警描述文本。
	 */
	private String descr;

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Integer id() {
		return getId();
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	@Override
	public long timestamp() {
		return time;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.time = System.currentTimeMillis();
	}

	/**
	 * <b>获得警告信息id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 警告信息id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <b>设置警告信息id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 警告信息id
	 */
	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * <b>获得告警代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 告警代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * <b>设置告警代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 告警代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * <b>获得告警名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 告警名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>设置告警名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 告警名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <b>获得报警类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报警类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * <b>设置报警类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 报警类型
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * <b>获得是否需要提醒。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否需要提醒
	 */
	public boolean isRemind() {
		return remind;
	}

	/**
	 * <b>设置是否需要提醒。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @param remind 是否需要提醒
	 */
	public void setRemind(boolean remind) {
		this.remind = remind;
	}

	/**
	 * <b>获得报警源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 报警源
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * <b>设置报警源。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param source 报警源
	 */
	public void setSource(Object source) {
		this.source = source;
	}

	/**
	 * <b>获得告警时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 告警时间
	 */
	public long getTime() {
		return time;
	}

	/**
	 * <b>设置告警时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param time 告警时间
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * <b>获得告警描述文本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 告警描述文本
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * <b>设置告警描述文本。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param descr 告警描述文本
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * <b>判断是否超时了。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxAge 最大毫秒数
	 * @return 是否超时了
	 */
	public boolean timeout(long maxAge) {
		return System.currentTimeMillis() > (timestamp() + maxAge);
	}

	/**
	 * <b>判断是否更老。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param alarm 告警对象
	 * @return 是否更老
	 */
	public boolean older(Alarm alarm) {
		return alarm == null || alarm.timestamp() > this.timestamp();
	}

	/**
	 * <b>创建告警信息实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param type 告警类型
	 * @param code 告警代码
	 * @param name 告警名称
	 * @param descr 告警描述
	 * @param source 告警源
	 * @return 告警对象
	 */
	public static Alarm of(int type, String code, String name, String descr, Object source) {
		Alarm alarm = new Alarm();
		alarm.setType(type);
		alarm.setSource(code);
		alarm.setCode(code);
		alarm.setName(name);
		alarm.setDescr(descr);
		alarm.setRemind(true);
		alarm.setTime(System.currentTimeMillis());
		return alarm;
	}

	/**
	 * <b>errorOf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 告警代码
	 * @param name 告警名称
	 * @param descr 告警描述
	 * @param source 告警源
	 * @return 告警对象
	 */
	public static Alarm errorOf(String code, String name, String descr, Object source) {
		return of(ALARM_TYPE_ERROR, code, name, descr, source);
	}

	/**
	 * <b>warnOf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 告警代码
	 * @param name 告警名称
	 * @param descr 告警描述
	 * @param source 告警源
	 * @return 告警对象
	 */
	public static Alarm warnOf(String code, String name, String descr, Object source) {
		return of(ALARM_TYPE_WARN, code, name, descr, source);
	}

	/**
	 * <b>noticeOf。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 告警代码
	 * @param name 告警名称
	 * @param descr 告警描述
	 * @param source 告警源
	 * @return 告警对象
	 */
	public static Alarm noticeOf(String code, String name, String descr, Object source) {
		return of(ALARM_TYPE_NOTICE, code, name, descr, source);
	}
}
