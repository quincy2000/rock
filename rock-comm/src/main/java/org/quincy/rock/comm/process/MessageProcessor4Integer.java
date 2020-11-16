package org.quincy.rock.comm.process;

import org.quincy.rock.comm.util.CommUtils;

/**
 * <b>MessageProcessor4Integer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月5日 下午11:37:45</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class MessageProcessor4Integer<M> extends AbstractMessageProcessor<Integer, M> {

	/** 
	 * getFunctionCode。
	 * @see org.quincy.rock.comm.process.AbstractMessageProcessor#getFunctionCode()
	 */
	@Override
	public final Integer getFunctionCode() {
		Integer code = super.getFunctionCode();
		if (code == null) {
			String key = CommUtils.parseSuffix(this.getClass(), true);
			if (key != null)
				code = Integer.valueOf(key);
		}
		return code;
	}

}
