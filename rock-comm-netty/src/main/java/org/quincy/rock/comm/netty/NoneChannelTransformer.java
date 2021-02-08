package org.quincy.rock.comm.netty;

/**
 * <b>NoneChannelTransformer。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月30日 下午11:12:12</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class NoneChannelTransformer<UChannel> implements ChannelTransformer<UChannel, UChannel> {
	/** 
	 * transform。
	 * @see org.quincy.rock.comm.communicate.ChannelTransformer#transform(java.lang.Object, org.quincy.rock.comm.communicate.ChannelTransformer.STransformPoint)
	 */
	@Override
	public UChannel transform(UChannel source, STransformPoint point) {
		return source;
	}

	/** 
	 * transform。
	 * @see org.quincy.rock.comm.communicate.ChannelTransformer#transform(java.lang.Object, org.quincy.rock.comm.communicate.ChannelTransformer.UTransformPoint)
	 */
	@Override
	public UChannel transform(UChannel userdefine, UTransformPoint point) {
		return userdefine;
	}

	/** 
	 * retrieveSendLock。
	 * @see org.quincy.rock.comm.communicate.ChannelTransformer#retrieveSendLock(java.lang.Object)
	 */
	@Override
	public Object retrieveSendLock(UChannel userdefine) {
		return userdefine;
	}
}
