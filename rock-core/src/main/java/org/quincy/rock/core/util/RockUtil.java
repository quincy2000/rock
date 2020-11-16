package org.quincy.rock.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.quincy.rock.core.exception.NoSuchMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.support.MethodInvokingRunnable;

/**
 * <b>RockUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月18日 下午4:56:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class RockUtil {

	/**
	 * <b>建立互斥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 互斥名称
	 * @return 是否成功建立互斥
	 */
	@SuppressWarnings("resource")
	public static boolean createMutex(String name) {
		boolean b = true;

		//利用文件锁实现互斥
		final File file = new File(SystemUtils.getJavaIoTmpDir(), name + "_mutex");
		try {
			if (!file.exists() || file.delete()) {
				final OutputStream os = new FileOutputStream(file);
				//退出时删除该文件
				Runtime.getRuntime().addShutdownHook(new Thread() {
					private OutputStream mutexOs = os;
					private File mutexFile = file;

					public void run() {
						try {
							mutexOs.close();
							mutexFile.delete();
						} catch (Exception e) {
						}
					}
				});
			} else {
				b = false;
			}
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * <b>判断程序是否是双重启动。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 通过使用该方法可以防止用户同时运行程序的多个实例。
	 * @param appName 应用程序名称
	 * @return 如果已经有一个程序实例在运行，则返回true
	 */
	public static boolean isDupleStartup(String appName) {
		return !createMutex(appName);
	}

	/**
	 * <b>执行简单的异步任务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param task 任务
	 * @param isDaemon 是否是后台任务
	 */
	public static Thread executeTask(Runnable task, boolean isDaemon) {
		Thread t = new Thread(task);
		t.setDaemon(isDaemon);
		t.start();
		return t;
	}

	/**
	 * <b>addShutdownHook。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param targetObject 目标对象
	 * @param methodNames 方法名（可以猜测多个）
	 */
	public static void addShutdownHook(Object targetObject, String... methodNames) {
		MethodInvokingRunnable invoking = null;
		for (String name : methodNames) {
			if (ReflectUtil.isMethod(targetObject, name)) {
				invoking = ReflectUtil.makeMethodInvoker(targetObject, name, true);
				break;
			}
		}
		if (invoking == null)
			throw new NoSuchMethodException(StringUtils.join(methodNames, ','));
		try {
			invoking.prepare();
		} catch (Exception e) {
			throw new NoSuchMethodException(e.getMessage(), e);
		}
		Runtime.getRuntime().addShutdownHook(new Thread(invoking));
	}

	/**
	 * <b>addShutdownHook。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param hook 钩子
	 */
	public static void addShutdownHook(Runnable hook) {
		Runtime.getRuntime().addShutdownHook(new Thread(hook));
	}

	/**
	 * <b>获得消息资源访问器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param basenames 资源名
	 * @return 消息资源访问器
	 */
	public static MessageSourceAccessor getMessageSourceAccessor(String... basenames) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(basenames);
		messageSource.setDefaultEncoding(StringUtil.DEFAULT_ENCODING.name());
		return new MessageSourceAccessor(messageSource);
	}

	/**
	 * <b>getLogger。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * <b>将指定值按照指定的格式转化成字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param valueToFormat 要格式化的值
	 * @param formatString 格式化字符串
	 * @return 格式化后的字符串
	 */
	public static String formatValue(Object valueToFormat, String formatString) {
		return formatValue(valueToFormat, formatString, Locale.getDefault());
	}

	/**
	 * <b>将指定值按照指定的格式转化成字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param valueToFormat 要格式化的值
	 * @param formatString 格式化字符串
	 * @param locale 本地化对象
	 * @return 格式化后的字符串
	 */
	public static String formatValue(Object valueToFormat, String formatString, Locale locale) {
		String value = null;
		if (valueToFormat instanceof String)
			value = (String) valueToFormat;
		else if (valueToFormat instanceof Number) {
			DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(locale);
			format.applyPattern(formatString);
			value = format.format(valueToFormat);
		} else if (valueToFormat instanceof TemporalAccessor) {
			DateTimeFormatter format = DateTimeFormatter.ofPattern(formatString, locale);
			value = format.format((TemporalAccessor) valueToFormat);
		} else if (valueToFormat instanceof Date) {
			DateFormat format = new SimpleDateFormat(formatString, locale);
			value = format.format((Date) valueToFormat);
		} else if (valueToFormat instanceof Calendar) {
			DateFormat format = new SimpleDateFormat(formatString, locale);
			value = format.format(((Calendar) valueToFormat).getTime());
		} else if (valueToFormat != null) {
			value = valueToFormat.toString();
		}
		return value;
	}

}
