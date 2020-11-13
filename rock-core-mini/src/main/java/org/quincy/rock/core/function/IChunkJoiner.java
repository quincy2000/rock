package org.quincy.rock.core.function;

import org.quincy.rock.core.cache.HasTimestamp;

/**
 * <b>块合并处理接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 处理数据块的分组合并逻辑。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年4月23日 下午4:12:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public interface IChunkJoiner extends HasTimestamp {
	/**
	 * <b>设置合并回调joinConsumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joinConsumer 合并回调Consumer
	 */
	public void joinConsumer(Consumer<Boolean> joinConsumer);

	/**
	 * <b>返回块数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 块数
	 */
	public int chunkCount();

	/**
	 * <b>添加对象块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 * @param chunk 对象块
	 */
	public void append(int index, Object chunk);

	/**
	 * <b>移走对象块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 */
	public void remove(int index);

	/**
	 * <b>剩余的全部成功。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void success();

	/**
	 * <b>剩余的全部失败。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void failure();

	/**
	 * <b>成功。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 */
	public void success(int index);

	/**
	 * <b>失败。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 */
	public void failure(int index);

	/**
	 * <b>是否所有块操作已经完成。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否所有块操作已经完成
	 */
	public boolean isDone();

	/**
	 * <b>流逝的时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 流逝的时间
	 */
	public int elapsedSecond();

	/**
	 * <b>返回对象块数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 对象块数组
	 */
	public Object[] chunks();

	/**
	 * <b>指定的索引和数据块是否有效。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法不会抛出索引越界异常。
	 * @param index 索引
	 * @return 索引和数据块是否有效
	 */
	public boolean valid(int index);

	/**
	 * 返回分块结果(null=没结果,false=失败结果,true=成功结果)。
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param chunkIndex 块索引
	 * @return 分块结果(null=没结果,false=失败结果,true=成功结果)
	 */
	public Boolean chunkResult(int index);

	/**
	 * <b>返回指定索引的数据块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 索引
	 * @return 数据块
	 */
	public Object chunk(int index);

	/**
	 * <b>设置指定索引的数据块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 索引
	 * @param chunk 数据块
	 */
	public void chunk(int index, Object chunk);
}
