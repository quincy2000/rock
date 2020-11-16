package org.quincy.rock.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.function.Function;
import org.quincy.rock.core.vo.PageSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * <b>DaoUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月20日 下午2:50:28</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class DaoUtil {
	/**
	 * <b>修正查询结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param queryResult 查询结果
	 * @return 修正后的结果
	 */
	public static <T> List<T> reviseQueryResult(List<?> queryResult) {
		List<T> list = new ArrayList<>(queryResult.size());
		for (Object ele : queryResult) {
			list.add(reviseQueryResult(ele));
		}
		return list;
	}

	/**
	 * <b>修正查询结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param queryResult 查询结果
	 * @return 修正后的结果
	 */
	public static <T> Page<T> reviseQueryResult(Page<?> queryResult) {
		List<T> list = new ArrayList<>(queryResult.getNumberOfElements());
		for (Object ele : queryResult) {
			list.add(reviseQueryResult(ele));
		}
		return new PageImpl<>(list, queryResult.getPageable(), queryResult.getTotalElements());
	}

	/**
	 * <b>修正单值结果。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param queryResult 单值结果
	 * @return 修正后的单值结果
	 */
	public static <T> T reviseQueryResult(Object result) {
		return (T) (result != null && result.getClass().isArray() ? Array.get(result, 0) : result);
	}

	/**
	 * <b>解析排序字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param _order 排序字符串
	 * @return Sort
	 */
	public static Sort toSort(String _order) {
		if (StringUtils.isEmpty(_order))
			return Sort.unsorted();
		List<Order> list = new ArrayList<>();
		StringUtil.splitLine(_order, ',', '\\', (index, ele) -> list.add(toOrder(ele.toString())));
		return list.isEmpty() ? Sort.unsorted() : Sort.by(list);
	}

	/**
	 * <b>解析排序字符串。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param _order 排序字符串
	 * @return Order
	 */
	public static Order toOrder(String _order) {
		_order = _order.trim();
		int pos = _order.indexOf(' ');
		if (pos > 0) {
			String property = _order.substring(0, pos).trim();
			Direction direction = Direction.fromString(_order.substring(pos + 1).trim());
			return new Order(direction, property);
		} else
			return new Order(Direction.ASC, _order.trim());
	}

	/**
	 * <b>toOrder。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param order
	 * @return
	 */
	public static String toOrder(Order order) {
		return order.getProperty() + StringUtil.CHAR_SPACE + (order.isAscending() ? "asc" : "desc");
	}

	/**
	 * <b>toOrder。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param order
	 * @return
	 */
	public static String toOrder(Sort sort) {
		StringBuilder sb = new StringBuilder();
		if (sort.isSorted()) {
			boolean addComma = false;
			for (Order order : sort) {
				if (addComma) {
					sb.append(StringUtil.CHAR_COMMA);
				} else {
					addComma = true;
				}
				sb.append(toOrder(order));
			}
		}
		return sb.toString();
	}

	/**
	 * <b>页数据转换。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page Page
	 * @return PageSet
	 */
	public static <T> PageSet<T> toPageSet(Page<T> page) {
		return toPageSet(page, (vo) -> vo);
	}

	/**
	 * <b>页数据转换。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param page Page
	 * @param conv Function
	 * @return PageSet
	 */
	public static <T, R> PageSet<R> toPageSet(Page<T> page, Function<T, R> conv) {
		PageSet<R> pageSet = new PageSet<>(page.getNumber(), page.getSize());
		pageSet.setTotalCount(page.getTotalElements());
		if (page.hasContent()) {
			List<R> list = new ArrayList<>(page.getSize());
			for (T ele : page.getContent()) {
				list.add(conv.call(ele));
			}
			pageSet.setContent(list);
		} else {
			pageSet.setContent(Collections.EMPTY_LIST);
		}
		return pageSet;
	}
}
