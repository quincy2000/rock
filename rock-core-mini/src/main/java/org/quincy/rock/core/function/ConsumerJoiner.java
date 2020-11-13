package org.quincy.rock.core.function;

import org.quincy.rock.core.cache.HasTimestamp;

/**
 * <b>ConsumerJoiner。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年7月20日 下午12:51:40</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class ConsumerJoiner implements HasTimestamp {
	/**
	 * 总体。
	 */
	private Consumer<Boolean> joinConsumer;
	/**
	 * 分块。
	 */
	private Consumer<Boolean>[] chunkConsumers;
	/**
	 * 分块结果(null=没结果,false=失败结果,true=成功结果)。
	 */
	private Boolean chunkResult[];
	/**
	 * 时间戳。
	 */
	private long timestamp;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param chunkCount 块数
	 */
	public ConsumerJoiner(int chunkCount) {
		this.chunkConsumers = new Consumer[chunkCount];
		this.chunkResult = new Boolean[chunkCount];
		for (int i = 0; i < chunkCount; i++) {
			this.chunkConsumers[i] = new ChunkConsumer(i);
		}
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param chunkCount 块数
	 * @param joinConsumer 异步操作回调
	 */
	public ConsumerJoiner(int chunkCount, Consumer<Boolean> joinConsumer) {
		this(chunkCount);
		this.joinConsumer(joinConsumer);
	}

	/** 
	 * timestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#timestamp()
	 */
	public long timestamp() {
		return timestamp;
	}

	/** 
	 * updateTimestamp。
	 * @see org.quincy.rock.core.cache.HasTimestamp#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * <b>流逝的时间。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 流逝的时间
	 */
	public int elapsedSecond() {
		return (int) (System.currentTimeMillis() - timestamp) / 1000;
	}

	/**
	 * <b>设置joinConsumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param joinConsumer Consumer
	 */
	public void joinConsumer(Consumer<Boolean> joinConsumer) {
		this.joinConsumer = joinConsumer;
	}

	/**
	 * <b>返回joinConsumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return joinConsumer Consumer
	 */
	public Consumer<Boolean> joinConsumer() {
		return this.joinConsumer;
	}

	/**
	 * <b>chunkConsumer。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param chunkIndex 块索引
	 * @return
	 */
	public Consumer<Boolean> chunkConsumer(int chunkIndex) {
		return this.chunkConsumers[chunkIndex];
	}

	/**
	 * <b>返回块数。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 块数
	 */
	public int chunkCount() {
		return chunkResult.length;
	}

	/**
	 * <b>是否所有块操作已经完成。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否所有块操作已经完成
	 */
	public boolean isDone() {
		for (int i = 0, count = chunkResult.length; i < count; i++) {
			if (chunkResult[i] == null)
				return false;
		}
		return true;
	}

	/**
	 * <b>返回分块结果(null=没结果,false=失败结果,true=成功结果)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param chunkIndex 块索引
	 * @return 分块结果(null=没结果,false=失败结果,true=成功结果)
	 */
	public Boolean chunkResult(int chunkIndex) {
		return this.chunkResult[chunkIndex];
	}

	/**
	 * <b>检查全部操作是否完成。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	private void checkDone() {
		if (isDone()) {
			boolean ok = true;
			for (int i = 0, count = chunkResult.length; i < count; i++) {
				if (chunkResult[i].booleanValue() == false) {
					ok = false;
					break;
				}
			}
			joinConsumer.call(ok);
		}
	}

	//分块ChunkConsumer
	private class ChunkConsumer implements Consumer<Boolean> {
		/**
		 * 当前块索引。
		 */
		private int chunkIndex;

		/**
		 * <b>构造方法。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param chunkIndex 当前块索引
		 */
		public ChunkConsumer(int chunkIndex) {
			this.chunkIndex = chunkIndex;
		}

		/** 
		 * call。
		 * @see org.quincy.rock.core.function.Consumer#call(java.lang.Object)
		 */
		@Override
		public void call(Boolean success) {
			chunkResult[chunkIndex] = success;
			if (success != null)
				checkDone();
		}
	}
}
