package org.quincy.rock.comm.netty;

/**
 * <b>简单NettyChannel。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * netty原始通道和自定义通道是一对一的关系，一个物理连接对应一个用户通道。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年6月1日 下午10:50:47</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
class SimpleNettyChannel extends AbstractNettyChannel {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * isMatched。
	 * @see org.quincy.rock.core.util.HasPattern#isMatched(java.lang.Object)
	 */
	@Override
	public boolean isMatched(Object obj) {
		return this.equals(obj);
	}

	/** 
	 * isPattern。
	 * @see org.quincy.rock.core.util.HasPattern#isPattern()
	 */
	@Override
	public boolean isPattern() {
		return false;
	}
}
