package org.quincy.rock.core.controller;

import org.quincy.rock.core.vo.AbstractEntity;
import org.quincy.rock.core.vo.AbstractVo;

/**
 * <b>实现reset接口的主键为Long类型MVC控制器基接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年7月7日 下午4:38:22</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class LongRestController<E extends AbstractEntity, T extends AbstractVo<E>>
		extends BaseRestController<E, T, Long> {

	/** 
	 * toId。
	 * @see org.quincy.rock.core.controller.BaseController#toId(java.lang.String)
	 */
	@Override
	protected Long toId(String id) {
		return Long.valueOf(id);
	}

}
