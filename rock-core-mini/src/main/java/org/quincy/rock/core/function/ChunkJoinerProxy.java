package org.quincy.rock.core.function;

/**
 * <b>ChunkJoiner代理类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年4月23日 下午4:29:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ChunkJoinerProxy implements IChunkJoiner {
	/**
	 * joiner。
	 */
	private final IChunkJoiner joiner;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joiner 要代理的IChunkJoiner
	 */
	public ChunkJoinerProxy(IChunkJoiner joiner) {
		this.joiner = joiner;
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	@Override
	public long timestamp() {
		return joiner.timestamp();
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		joiner.updateTimestamp();
	}

	/** 
	 * joinConsumer。
	 * @see org.quincy.rock.core.function.IChunkJoiner#joinConsumer(org.quincy.rock.core.function.Consumer)
	 */
	@Override
	public void joinConsumer(Consumer<Boolean> joinConsumer) {
		joiner.joinConsumer(joinConsumer);
	}

	/** 
	 * chunkCount。
	 * @see org.quincy.rock.core.function.IChunkJoiner#chunkCount()
	 */
	@Override
	public int chunkCount() {
		return joiner.chunkCount();
	}

	/** 
	 * append。
	 * @see org.quincy.rock.core.function.IChunkJoiner#append(int, java.lang.Object)
	 */
	@Override
	public void append(int index, Object chunk) {
		joiner.append(index, chunk);
	}

	/** 
	 * remove。
	 * @see org.quincy.rock.core.function.IChunkJoiner#remove(int)
	 */
	@Override
	public void remove(int index) {
		joiner.remove(index);
	}

	/** 
	 * success。
	 * @see org.quincy.rock.core.function.IChunkJoiner#success()
	 */
	@Override
	public void success() {
		joiner.success();
	}

	/** 
	 * failure。
	 * @see org.quincy.rock.core.function.IChunkJoiner#failure()
	 */
	@Override
	public void failure() {
		joiner.failure();
	}

	/** 
	 * success。
	 * @see org.quincy.rock.core.function.IChunkJoiner#success(int)
	 */
	@Override
	public void success(int index) {
		joiner.success(index);
	}

	/** 
	 * failure。
	 * @see org.quincy.rock.core.function.IChunkJoiner#failure(int)
	 */
	@Override
	public void failure(int index) {
		joiner.failure(index);
	}

	/** 
	 * isDone。
	 * @see org.quincy.rock.core.function.IChunkJoiner#isDone()
	 */
	@Override
	public boolean isDone() {
		return joiner.isDone();
	}

	/** 
	 * elapsedSecond。
	 * @see org.quincy.rock.core.function.IChunkJoiner#elapsedSecond()
	 */
	@Override
	public int elapsedSecond() {
		return joiner.elapsedSecond();
	}

	/** 
	 * chunks。
	 * @see org.quincy.rock.core.function.IChunkJoiner#chunks()
	 */
	@Override
	public Object[] chunks() {
		return joiner.chunks();
	}

	/** 
	 * valid。
	 * @see org.quincy.rock.core.function.IChunkJoiner#valid(int)
	 */
	@Override
	public boolean valid(int index) {
		return joiner.valid(index);
	}

	/** 
	 * chunkResult。
	 * @see org.quincy.rock.core.function.IChunkJoiner#chunkResult(int)
	 */
	@Override
	public Boolean chunkResult(int index) {
		return joiner.chunkResult(index);
	}

	/** 
	 * chunk。
	 * @see org.quincy.rock.core.function.IChunkJoiner#chunk(int)
	 */
	@Override
	public Object chunk(int index) {
		return joiner.chunk(index);
	}

	/** 
	 * chunk。
	 * @see org.quincy.rock.core.function.IChunkJoiner#chunk(int, java.lang.Object)
	 */
	@Override
	public void chunk(int index, Object chunk) {
		joiner.chunk(index, chunk);
	}

	/**
	 * <b>返回代理的ChunkJoiner。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 代理的ChunkJoiner
	 */
	@SuppressWarnings("unchecked")
	public <T extends IChunkJoiner> T joiner() {
		return (T) joiner;
	}
}
