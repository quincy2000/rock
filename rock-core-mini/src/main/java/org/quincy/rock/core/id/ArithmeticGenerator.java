package org.quincy.rock.core.id;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>可定制算法的ID生成器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2016年5月30日 下午11:29:09</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ArithmeticGenerator implements IdentifierGenerator {
	/**
	 * ID生成算法。
	 */
	private IdentifierArithmetic arithmetic = new IncArithmetic();
	/**
	 * idMap。
	 */
	private Map<Object, Object> idMap = new HashMap<>();

	/** 
	 * generate。
	 * @see org.quincy.rock.core.id.IdentifierGenerator#generate(java.lang.Object)
	 */
	@Override
	public synchronized Object generate(Object key) {
		Object id = idMap.get(key);
		id = arithmetic.getNextId(id);
		idMap.put(key, id);
		return id;
	}

	/**
	 * <b>获得ID生成算法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return ID生成算法
	 */
	public IdentifierArithmetic getArithmetic() {
		return arithmetic;
	}

	/**
	 * <b>设置ID生成算法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param arithmetic ID生成算法
	 */
	public void setArithmetic(IdentifierArithmetic arithmetic) {
		this.arithmetic = arithmetic;
	}
}
