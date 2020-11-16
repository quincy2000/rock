package org.quincy.rock.core.dao;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;

/**
 * <b>命名sql仓库。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月26日 下午12:34:03</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NamedSqlStorage {
	/**
	 * 命名SQL串的集合。
	 */
	private Properties namedSqls;

	/**
	 * <b>获得命名SQL串的集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 命名SQL串的集合
	 */
	public Properties getNamedSqls() {
		return namedSqls;
	}

	/**
	 * <b>设置命名SQL串的集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param namedSqls 命名SQL串的集合
	 */
	public void setNamedSqls(Properties namedSqls) {
		this.namedSqls = namedSqls;
	}

	/**
	 * <b>获得命名sql。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 名称
	 * @param arguments 格式化参数
	 * @return sql
	 * @see MessageFormat
	 */
	public String getNamedSql(String name, Object... arguments) {
		String sql = namedSqls == null ? null : namedSqls.getProperty(name, null);
		if (sql != null && ArrayUtils.isNotEmpty(arguments)) {
			sql = MessageFormat.format(sql, arguments);
		}
		return sql;
	}
}
