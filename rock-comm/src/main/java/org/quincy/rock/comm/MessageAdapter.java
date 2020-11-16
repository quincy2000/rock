package org.quincy.rock.comm;

import java.util.Map;

/**
 * <b>MessageAdapter。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月9日 下午11:56:38</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MessageAdapter<K> implements MessageListener<K> {

	/** 
	 * terminalOnline。
	 * @see org.quincy.rock.comm.MessageListener#terminalOnline(org.quincy.rock.comm.MessageSender, java.lang.Object)
	 */
	@Override
	public void terminalOnline(MessageSender<K> sender, Object terminalId) {

	}

	/** 
	 * terminalOffline。
	 * @see org.quincy.rock.comm.MessageListener#terminalOffline(org.quincy.rock.comm.MessageSender, java.lang.Object)
	 */
	@Override
	public void terminalOffline(MessageSender<K> sender, Object terminalId) {

	}

	/** 
	 * messageArrived。
	 * @see org.quincy.rock.comm.MessageListener#messageArrived(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void messageArrived(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx) {

	}

	/** 
	 * messageSended。
	 * @see org.quincy.rock.comm.MessageListener#messageSended(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, boolean)
	 */
	@Override
	public void messageSended(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx, boolean success) {

	}

	/** 
	 * messageHeadParserException。
	 * @see org.quincy.rock.comm.MessageListener#messageHeadParserException(org.quincy.rock.comm.MessageSender, java.lang.Object, java.util.Map, java.lang.Throwable)
	 */
	@Override
	public void messageHeadParserException(MessageSender<K> sender, Object data, Map<String, Object> ctx, Throwable e) {

	}

	/** 
	 * messageParserException。
	 * @see org.quincy.rock.comm.MessageListener#messageParserException(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Throwable)
	 */
	@Override
	public void messageParserException(MessageSender<K> sender, Object terminalId, Object msgId, K functionCode,
			Object content, Map<String, Object> ctx, Throwable e) {

	}

	/** 
	 * checkLegalityException。
	 * @see org.quincy.rock.comm.MessageListener#checkLegalityException(org.quincy.rock.comm.MessageSender, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public void checkLegalityException(MessageSender<K> sender, Object msgId, K functionCode, Object content,
			Map<String, Object> ctx) {

	}
}
