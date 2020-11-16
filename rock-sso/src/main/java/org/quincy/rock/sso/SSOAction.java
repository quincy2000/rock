package org.quincy.rock.sso;

import java.util.Arrays;
import java.util.Collection;

/**
 * <b>SSOAction。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年6月21日 下午1:44:48</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class SSOAction extends SSONode<SSOAction> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1199839276441140381L;

	/**
	 * 功能插件文件名。
	 */
	private String fileName;

	/**
	 * 功能类名。
	 */
	private String className;

	/**
	 * 功能方法名。
	 */
	private String methodName;

	/**
	 * 功能路径名。
	 */
	private String pathName;

	/**
	 * 功能参数。
	 */
	private String paramter;

	/**
	 * tag。
	 */
	private String tag;

	/**
	 * 获得功能插件文件名
	 * @return 功能插件文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置功能插件文件名
	 * @param fileName 功能插件文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获得功能类名
	 * @return 功能类名
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 设置功能类名
	 * @param className 功能类名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 获得功能方法名
	 * @return 功能方法名
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * 设置功能方法名
	 * @param methodName 功能方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * 获得功能路径名
	 * @return 功能路径名
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * 设置功能路径名
	 * @param pathName 功能路径名
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * 获得功能参数
	 * @return 功能参数
	 */
	public String getParamter() {
		return paramter;
	}

	/**
	 * 设置功能参数
	 * @param paramter 功能参数
	 */
	public void setParamter(String paramter) {
		this.paramter = paramter;
	}

	/**
	 * <b>getTag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * <b>setTag。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/** 
	 * propertyNames4ToString。
	 * @see org.quincy.rock.sso.SSONode#propertyNames4ToString()
	 */
	@Override
	protected Collection<String> propertyNames4ToString() {
		Collection<String> cs = super.propertyNames4ToString();
		cs.addAll(Arrays.asList("fileName", "className", "methodName", "pathName", "paramter"));
		return cs;
	}

}
