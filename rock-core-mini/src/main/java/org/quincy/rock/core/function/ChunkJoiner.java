package org.quincy.rock.core.function;

/**
 * <b>ChunkJoiner。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 处理数据块的分组合并逻辑。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月8日 下午3:09:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ChunkJoiner implements IChunkJoiner {
	/**
	 * chunks。
	 */
	private final Object[] chunks;
	/**
	 * joiner。
	 */
	private ConsumerJoiner joiner;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 接收数据块时使用。
	 * @param chunkCount 准备接收的块数
	 */
	public ChunkJoiner(int chunkCount) {
		this(new Object[chunkCount]);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 发送数据块时使用。
	 * @param chunks 发送的数据块数组
	 */
	public ChunkJoiner(Object[] chunks) {
		this.chunks = chunks;
		this.joiner = new ConsumerJoiner(chunks.length);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 接收数据块时使用。
	 * @param chunkCount 准备接收的块数
	 * @param joinConsumer 全部接收完成后调用该Consumer
	 */
	public ChunkJoiner(int chunkCount, Consumer<Boolean> joinConsumer) {
		this(new Object[chunkCount], joinConsumer);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 发送数据块时使用。
	 * @param chunks 发送的数据块数组
	 * @param joinConsumer 全部发送完成后调用该Consumer
	 */
	public ChunkJoiner(Object[] chunks, Consumer<Boolean> joinConsumer) {
		this.chunks = chunks;
		this.joiner = new ConsumerJoiner(chunks.length, joinConsumer);
	}

	/**
	 * <b>设置joinConsumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joinConsumer Consumer
	 */
	public void joinConsumer(Consumer<Boolean> joinConsumer) {
		this.joiner.joinConsumer(joinConsumer);
	}

	/**
	 * <b>返回joinConsumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return joinConsumer
	 */
	public Consumer<Boolean> joinConsumer() {
		return this.joiner.joinConsumer();
	}

	/**
	 * <b>返回块数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 块数
	 */
	public int chunkCount() {
		return chunks.length;
	}

	/**
	 * <b>添加对象块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 * @param chunk 对象块
	 */
	public void append(int index, Object chunk) {
		this.chunk(index, chunk);
		this.joiner.chunkConsumer(index).call(true);
	}

	/**
	 * <b>移走对象块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 */
	public void remove(int index) {
		this.chunks[index] = null;
		this.joiner.chunkConsumer(index).call(null);
	}

	/**
	 * <b>剩余的全部成功。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void success() {
		for (int i = 0, l = joiner.chunkCount(); i < l; i++) {
			if (joiner.chunkResult(i) == null) {
				joiner.chunkConsumer(i).call(true);
			}
		}
	}

	/**
	 * <b>剩余的全部失败。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void failure() {
		for (int i = 0, l = joiner.chunkCount(); i < l; i++) {
			if (joiner.chunkResult(i) == null) {
				joiner.chunkConsumer(i).call(false);
			}
		}
	}

	/**
	 * <b>成功。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 */
	public void success(int index) {
		this.joiner.chunkConsumer(index).call(true);
	}

	/**
	 * <b>失败。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 位置索引
	 */
	public void failure(int index) {
		this.joiner.chunkConsumer(index).call(false);
	}

	/**
	 * <b>是否所有块操作已经完成。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否所有块操作已经完成
	 */
	public boolean isDone() {
		return this.joiner.isDone();
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	public long timestamp() {
		return this.joiner.timestamp();
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.joiner.updateTimestamp();
	}

	/**
	 * <b>流逝的时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 流逝的时间
	 */
	public int elapsedSecond() {
		return this.joiner.elapsedSecond();
	}

	/**
	 * <b>返回对象块数组。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 对象块数组
	 */
	public Object[] chunks() {
		return chunks;
	}

	/**
	 * <b>指定的索引和数据块是否有效。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 该方法不会抛出索引越界异常。
	 * @param index 索引
	 * @return 索引和数据块是否有效
	 */
	public boolean valid(int index) {
		return index > -1 && index < chunks.length && chunks[index] != null;
	}

	/** 
	 * chunkResult。
	 * @see org.quincy.rock.core.function.IChunkJoiner#chunkResult(int)
	 */
	@Override
	public Boolean chunkResult(int index) {
		return this.joiner.chunkResult(index);
	}

	/**
	 * <b>返回指定索引的数据块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 索引
	 * @return 数据块
	 */
	public Object chunk(int index) {
		return this.chunks[index];
	}

	/**
	 * <b>设置指定索引的数据块。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param index 索引
	 * @param chunk 数据块
	 */
	public void chunk(int index, Object chunk) {
		this.chunks[index] = chunk;
	}
}
