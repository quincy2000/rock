package org.quincy.rock.core;

import org.quincy.rock.core.util.RockUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <b>SpringContext。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年1月8日 上午9:48:24</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SpringContext {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(SpringContext.class);

	/**
	 * 默认的Spring配置文件名。
	 */
	public static final String DEFAULT_SPRING_CONFIG_NAME = "/spring-context.xml";

	/**
	 * springConfigFile。
	 */
	private String springConfigFile;

	/**
	 * context。
	 */
	private ClassPathXmlApplicationContext context;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public SpringContext() {
		this(null);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param configFile Spring配置文件名
	 */
	public SpringContext(String configFile) {
		springConfigFile = configFile == null ? DEFAULT_SPRING_CONFIG_NAME : configFile;
	}

	/**
	 * <b>返回Spring容器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Spring容器
	 */
	public ApplicationContext context() {
		if (context == null) {
			try {
				//装载spring配置文件,进行初始化
				context = new ClassPathXmlApplicationContext(springConfigFile);
				RockUtil.addShutdownHook(() -> closeContext());
				logger.info("The Spring container load is complete:{}。", springConfigFile);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return context;
	}

	/**
	 * <b>卸载Spring容器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void closeContext() {
		if (context != null) {
			context.close();
			context = null;
			logger.info("The Spring container unload is complete:{}。", springConfigFile);
		}
	}

	/**
	 * <b>从容器中获得类型的实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param requiredType 类型
	 * @return 实例
	 */
	public <T> T getBean(Class<T> requiredType) {
		return context().getBean(requiredType);
	}

	/**
	 * <b>从容器中获得实例。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 * @param requiredType 类型
	 * @return 实例
	 */
	public <T> T getBean(String name, Class<T> requiredType) {
		return context().getBean(name, requiredType);
	}

	/**
	 * <b>创建Spring上下文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param configFile spring配置文件
	 * @return SpringContext
	 */
	public static SpringContext createSpringContext(String configFile) {
		return new SpringContext(configFile);
	}
}
