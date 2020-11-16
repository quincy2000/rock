package org.quincy.rock.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.scheduling.support.MethodInvokingRunnable;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.TypeUtils;

/**
 * <b>ReflectUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月20日 下午4:42:29</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class ReflectUtil {

	/**
	 * <b>makeMethodInvoker。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param targetObject 目标对象实例
	 * @param methodName 方法名称
	 * @param isRunnable 返回的MethodInvoker是否实现Runnable接口
	 * @param arguments 参数
	 * @return MethodInvoker或MethodInvokingRunnable的实例
	 * @see MethodInvoker
	 * @see MethodInvokingRunnable
	 */
	public static <T extends MethodInvoker> T makeMethodInvoker(Object targetObject, String methodName,
			boolean isRunnable, Object... arguments) {
		MethodInvoker invoker = isRunnable ? new MethodInvokingRunnable() : new MethodInvoker();
		invoker.setTargetObject(targetObject);
		invoker.setTargetMethod(methodName);
		invoker.setArguments(arguments);
		return (T) invoker;
	}

	/**
	 * <b>判断是否是方法名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param target 目标类或实例
	 * @param name 方法名
	 * @param arguments 方法参数类或参数值
	 * @return 是否是方法名
	 */
	public static boolean isMethod(Object target, String name, Object... arguments) {
		Class<?>[] args = new Class<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			Object obj = arguments[i];
			if (obj == null || obj instanceof Class)
				args[i] = (Class<?>) obj;
			else
				args[i] = obj.getClass();
		}
		return ReflectionUtils.findMethod(target instanceof Class ? (Class<?>) target : target.getClass(), name,
				args) != null;
	}

	/**
	 * <b>获得泛型的参数化类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clazz Class
	 * @return 参数化类型
	 */
	public static ParameterizedType getParameterizedType(Class<?> clazz) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType)
			return (ParameterizedType) type;
		else if (type instanceof Class)
			return getParameterizedType(clazz.getSuperclass());
		else
			return null;
	}

	/**
	 * <b>获得泛型变量对应的真实类型。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param clazz Class
	 * @param index 泛型位置索引
	 * @return 泛型真实的类型
	 */
	public static Class<?> getGenericActualType(Class<?> clazz, int index) {
		ParameterizedType type = getParameterizedType(clazz);
		if (type != null) {
			return (Class<?>) type.getActualTypeArguments()[index];
		}
		return null;
	}

	/**
	 * <b>发现指定类型的字段。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param voClazz 值对象类
	 * @param fieldType 字段类型
	 * @return 字段
	 */
	public static Field findField(Class<?> voClazz, Class<?> fieldType) {
		Field[] fs = voClazz.getDeclaredFields();
		for (Field f : fs) {
			if (TypeUtils.isAssignable(fieldType, f.getType())) {
				return f;
			}
		}
		return null;
	}
}
