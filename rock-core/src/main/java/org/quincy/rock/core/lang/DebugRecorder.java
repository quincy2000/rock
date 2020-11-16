package org.quincy.rock.core.lang;

import org.quincy.rock.core.lang.TraceRecorder;
import org.quincy.rock.core.util.RockUtil;
import org.slf4j.Logger;

/**
 * <b>DebugRecorder。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月25日 上午12:30:10</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class DebugRecorder extends TraceRecorder {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(DebugRecorder.class);

	/** 
	 * canWrite。
	 * @see org.quincy.rock.core.lang.TraceRecorder#canWrite()
	 */
	@Override
	public boolean canWrite() {
		return super.canWrite() && logger.isDebugEnabled();
	}

	/** 
	 * trace。
	 * @see org.quincy.rock.core.lang.TraceRecorder#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String message, Throwable t) {
		if (t == null)
			logger.debug(message);
		else
			logger.warn(message, t);
	}
}
