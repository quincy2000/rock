package org.quincy.rock.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Properties;

import org.quincy.rock.core.exception.RockException;

/**
 * <b>IOUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 上午11:40:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class IOUtil {
	/**
	 * IO_BINARY_UNIT_K。
	 */
	public static final long IO_BINARY_UNIT_K = 1024;
	/**
	 * IO_BINARY_UNIT_M。
	 */
	public static final long IO_BINARY_UNIT_M = IO_BINARY_UNIT_K * 1024;
	/**
	 * IO_BINARY_UNIT_G。
	 */
	public static final long IO_BINARY_UNIT_G = IO_BINARY_UNIT_M * 1024;
	/**
	 * IO_BINARY_UNIT_T。
	 */
	public static final long IO_BINARY_UNIT_T = IO_BINARY_UNIT_G * 1024;

	/**
	 * <b>获得唯一的文件名。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 唯一的文件名
	 */
	public static String getUniqueFileName(String prefix, String suffix) {
		return StringUtil.getUniqueIdentifierName(prefix) + suffix;
	}

	/**
	 * <b>获得唯一的文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dir 目录
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 唯一的文件,该文件已经自动创建
	 */
	public static File getUniqueFile(File dir, String prefix, String suffix) {
		File f = null;
		do {
			String fName = getUniqueFileName(prefix, suffix);
			f = new File(dir, fName);
		} while (f.exists());
		try {
			f.createNewFile();
		} catch (Exception e) {
			throw new RockException(e.getMessage(), e);
		}
		return f;
	}

	/**
	 * <b>创建目录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param file 父目录
	 * @param dirs 每个级别的目录名
	 * @return 完整的目录对象
	 */
	public static File makeDirs(File parent, String... dirs) {
		File dir = joinDirs(parent, dirs);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * <b>拼接目录。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param parent 父目录
	 * @param dirs 每个级别的目录名
	 * @return 完整的目录对象
	 */
	public static File joinDirs(File parent, String... dirs) {
		for (String dir : dirs) {
			parent = new File(parent, dir);
		}
		return new File(parent.getAbsolutePath());
	}

	/**
	 * <b>安静的关闭IO流。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 不抛出任何异常。
	 * @param io 要关闭的IO流
	 */
	public static void closeQuietly(AutoCloseable io) {
		try {
			if (io != null)
				io.close();
		} catch (Exception e) {
		}
	}

	/**
	 * <b>装载属性文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param f 属性文件url
	 * @param encoding 字符集,可以为null
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadProperties(URL f, String encoding) throws IOException {
		Properties p = new Properties();
		//从文件读取属性
		InputStream is = null;
		try {
			is = new BufferedInputStream(f.openStream());
			if (encoding == null)
				p.load(is);
			else
				p.load(new InputStreamReader(is, encoding));
		} finally {
			closeQuietly(is);
		}
		return p;
	}

	/**
	 * <b>装载属性文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param f 属性文件
	 * @param encoding 字符集,可以为null
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadProperties(File f, String encoding) throws IOException {
		Properties p = new Properties();
		//从文件读取属性
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(f));
			if (encoding == null)
				p.load(is);
			else
				p.load(new InputStreamReader(is, encoding));
		} finally {
			closeQuietly(is);
		}
		return p;
	}

	/**
	 * <b>装载属性文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param f 属性文件url
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadPropertiesFromXml(URL f) throws IOException {
		Properties p = new Properties();
		//从文件读取属性
		InputStream is = null;
		try {
			is = new BufferedInputStream(f.openStream());
			p.loadFromXML(is);
		} finally {
			closeQuietly(is);
		}
		return p;
	}

	/**
	 * <b>装载属性文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param f 属性文件
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadPropertiesFromXml(File f) throws IOException {
		Properties p = new Properties();
		//从文件读取属性
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(f));
			p.loadFromXML(is);
		} finally {
			closeQuietly(is);
		}
		return p;
	}

	/**
	 * <b>保存属性文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param p Properties对象
	 * @param f 属性文件
	 * @param header 头注释
	 * @param encoding 字符集,可以为null
	 * @throws IOException
	 */
	public static void saveProperties(Properties p, File f, String header, String encoding) throws IOException {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(f));
			if (encoding == null)
				p.store(os, header);
			else
				p.store(new OutputStreamWriter(os, encoding), header);
		} finally {
			closeQuietly(os);
		}
	}

	/**
	 * <b>保存属性文件。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param p Properties对象
	 * @param f 属性文件
	 * @param header 头注释
	 * @param encoding 字符集,可以为null
	 * @throws IOException
	 */
	public static void savePropertiesToXml(Properties p, File f, String header, String encoding) throws IOException {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(f));
			if (encoding == null)
				p.storeToXML(os, header);
			else
				p.storeToXML(os, header, encoding);
		} finally {
			closeQuietly(os);
		}
	}

	/**
	 * <b>将文字字节大小解析成数字。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param displaySize 字节数的文字形式(如2B,2K,2M,2G,2T)
	 * @return 字节数
	 */
	public static long displaySizeToByteCount(String displaySize) {
		long size = 0;
		if (!StringUtil.isBlank(displaySize)) {
			displaySize = displaySize.trim().toUpperCase();
			int pos;
			long l = 1;
			if ((pos = displaySize.indexOf('T')) != -1) {
				l = IO_BINARY_UNIT_T;
			} else if ((pos = displaySize.indexOf('G')) != -1) {
				l = IO_BINARY_UNIT_G;
			} else if ((pos = displaySize.indexOf('M')) != -1) {
				l = IO_BINARY_UNIT_M;
			} else if ((pos = displaySize.indexOf('K')) != -1) {
				l = IO_BINARY_UNIT_K;
			} else if ((pos = displaySize.indexOf('B')) == -1) {
				pos = displaySize.length();
			}
			size = Long.parseLong(displaySize.substring(0, pos));
			size *= l;
		}
		return size;
	}
}
