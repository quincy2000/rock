package org.quincy.rock.comm.entrepot;

import java.util.Map;

/**
 * <b>MessageEntrepotAdapter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月10日 上午12:02:07</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MessageEntrepotAdapter implements MessageEntrepotListener {

	/** 
	 * toSentMessageAdded。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepotListener#toSentMessageAdded(org.quincy.rock.comm.entrepot.MessageEntrepot, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void toSentMessageAdded(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {

	}

	/** 
	 * toSentMessageAddDone。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepotListener#toSentMessageAddDone(org.quincy.rock.comm.entrepot.MessageEntrepot, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, boolean)
	 */
	@Override
	public void toSentMessageAddDone(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx, boolean success) {

	}

	/** 
	 * arrivedMessageAdded。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepotListener#arrivedMessageAdded(org.quincy.rock.comm.entrepot.MessageEntrepot, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void arrivedMessageAdded(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode, Object content,
			Map<String, Object> ctx) {

	}

	/** 
	 * arrivedMessageAddDone。
	 * @see org.quincy.rock.comm.entrepot.MessageEntrepotListener#arrivedMessageAddDone(org.quincy.rock.comm.entrepot.MessageEntrepot, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void arrivedMessageAddDone(MessageEntrepot me, Object terminalId, Object msgId, Object cmdCode,
			Object content, Map<String, Object> ctx) {

	}
}
