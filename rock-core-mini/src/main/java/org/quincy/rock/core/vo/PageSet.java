package org.quincy.rock.core.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <b>页数据集合。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 下午3:17:03</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings({ "unchecked" })
public class PageSet<T> implements Iterable<T> {
	/**
	 * 缺省的页大小。
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 分页大小（页记录条数）
	 */
	private int pageSize;

	/**
	 * 当前页码(从0开始)
	 */
	private int pageNumber;

	/**
	 * 总数据条数
	 */
	private long totalCount;

	/**
	 * 当前页数据集合
	 */
	private List<T> content;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public PageSet() {
		this(0, DEFAULT_PAGE_SIZE);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageNumber 当前页码(从0开始)
	 */
	public PageSet(int pageNumber) {
		this(pageNumber, DEFAULT_PAGE_SIZE);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageNumber 当前页码(从0开始)
	 * @param pageSize 页大小（页记录条数）
	 */
	public PageSet(int pageNumber, int pageSize) {
		this.setContent(new ArrayList<T>());
		this.setPageSize(pageSize);
		this.setPageNumber(pageNumber);
	}

	/**
	 * <b>获得当前页码(从0开始)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前页码(从0开始)
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * <b>设置当前页码(从0开始)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageNumber 当前页码(从0开始)
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber < 0 ? 0 : pageNumber;
	}

	/**
	 * <b>获得分页大小（页记录条数）。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 分页大小（页记录条数）
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * <b>设置分页大小（页记录条数）。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param pageSize 分页大小（页记录条数）
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	/**
	 * <b>获得总数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 总数据条数
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * <b>设置总数据条数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param totalCount 总数据条数
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount < 0 ? 0 : totalCount;
	}

	/**
	 * <b>获得当前页数据集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前页数据集合
	 */
	public List<T> getContent() {
		return content == null ? Collections.EMPTY_LIST : content;
	}

	/**
	 * <b>设置当前页数据集合。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param content 当前页数据集合
	 */
	public void setContent(List<T> content) {
		this.content = content;
	}

	/**
	 * <b>获得总页数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 总页数
	 */
	public int getPageCount() {
		long i = totalCount / pageSize;
		if (totalCount % pageSize != 0) {
			i++;
		}
		return (int) (i == 0 ? 1 : i);
	}

	/**
	 * <b>获得当前页数据条数。。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前页记录条数
	 */
	public int getCount() {
		return content.size();
	}

	/**
	 * <b>获得上一页页码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 上一页页码
	 */
	public int getPreviousPage() {
		int i = this.getPageNumber();
		return isFirstPage() ? i : i - 1;
	}

	/**
	 * <b>获得下一页页码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 下一页页码
	 */
	public int getNextPage() {
		int i = this.getPageNumber();
		return isLastPage() ? i : i + 1;
	}

	/**
	 * <b>是否是第一页。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是第一页
	 */
	public boolean isFirstPage() {
		return this.getPageNumber() == 0;
	}

	/**
	 * <b>是否是最后一页。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是最后一页
	 */
	public boolean isLastPage() {
		return this.getPageNumber() >= this.getPageCount() - 1;
	}

	/**
	 * <b>返回页开始位置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 索引从0开始。
	 * @return 页开始位置索引
	 */
	public int getBeginIndex() {
		int i = this.getPageNumber() * pageSize;
		return i;
	}

	/**
	 * <b>返回页结束位置。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 索引从0开始。
	 * @return 页结束位置索引
	 */
	public int getEndIndex() {
		return getBeginIndex() + pageSize;
	}

	/**
	 * <b>是否是有效合法页。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否是有效合法页
	 */
	public boolean isValidPage() {
		int beginIndex = getBeginIndex();
		return beginIndex >= 0 && beginIndex < totalCount;
	}

	/** 
	 * iterator。
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return this.getContent().iterator();
	}

	/**
	 * <b>返回集合中指定页的数据。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param list  数据列表
	 * @param page 指定页（从0开始）
	 * @param pageSize 页大小
	 * @return 指定页的数据
	 */
	public static <T> PageSet<T> toPageSet(List<T> list, int page, int pageSize) {
		PageSet<T> pageSet = new PageSet<T>(0, pageSize);
		int total = list.size();
		pageSet.setTotalCount(total);
		int last = pageSet.getPageCount() - 1;
		pageSet.setPageNumber(page > last ? last : page);
		int i1 = pageSet.getBeginIndex();
		int i2 = pageSet.getEndIndex();
		pageSet.setContent(list.subList(i1, Math.min(i2, total)));
		return pageSet;
	}
}
