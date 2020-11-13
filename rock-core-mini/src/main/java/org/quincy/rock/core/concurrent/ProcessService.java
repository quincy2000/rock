package org.quincy.rock.core.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.quincy.rock.core.cache.HasTimestamp;
import org.quincy.rock.core.exception.BlcokingException;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>数据处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年5月25日 下午1:19:10</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public abstract class ProcessService<K, V> {
	/**
	 * psRefMap。
	 */
	private static final WeakHashMap<String, ProcessService<?, ?>> psRefMap = new WeakHashMap<>();

	/**
	 * 处理服务名称。
	 */
	private String name;
	/**
	 * 处理服务描述。
	 */
	private String describe;
	/**
	 * 停止的。
	 */
	private boolean stopped = true;
	/**
	 * 日志记录器。
	 */
	protected Recorder recorder = Recorder.EMPTY;

	/**
	 * 处理服务唯一id
	 */
	private final String id = StringUtil.getUniqueIdentifierName("ps");

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public ProcessService() {
		psRefMap.put(id, this);
	}

	/**
	 * <b>获得处理服务唯一id。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理服务唯一id
	 */
	public String getId() {
		return new String(id.getBytes());
	}

	/**
	 * <b>获得处理服务名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理服务名称
	 */
	public String getName() {
		return name == null ? this.getClass().getSimpleName() : name;
	}

	/**
	 * <b>设置处理服务名称。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param name 处理服务名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <b>获得处理服务描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理服务描述
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * <b>设置处理服务描述。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param describe 处理服务描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * <b>日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 日志记录器
	 */
	public Recorder getRecorder() {
		return recorder;
	}

	/**
	 * <b>日志记录器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param recorder 日志记录器
	 */
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	/**
	 * <b>放置数据，等待执行。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 键
	 * @param data 要处理的数据
	 */
	public final void put(K key, V data) {
		if (isStopped())
			throw new BlcokingException("ProcessService is stopped!");
		Processor<V> processor = this.getProcessor(key);
		handleDataClosure(this.createDataClosure(key, data, processor));
	}

	/**
	 * <b>创建DataClosure。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 键
	 * @param data 要处理的数据
	 * @param processor 处理器
	 * @return DataClosure
	 */
	protected DataClosure<K, V> createDataClosure(K key, V data, Processor<V> processor) {
		return new DataClosure<K, V>(key, data, processor);
	}

	/**
	 * <b>收到数据时调用该方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 子类继承该方法处理收到的数据。
	 * @param dataClosure 数据处理闭包
	 * @exception BlcokingException
	 */
	protected abstract void handleDataClosure(DataClosure<K, V> dataClosure) throws BlcokingException;

	/**
	 * <b>获得处理器。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 键
	 * @return 处理器
	 * @exception NotFoundException
	 */
	protected abstract Processor<V> getProcessor(K key) throws NotFoundException;

	/**
	 * <b>处理DataClosure。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param dataClosure DataClosure
	 */
	protected final void processDataClosure(DataClosure<K, V> dataClosure) {
		try {
			dataClosure.process();
		} catch (Exception e) {
			recorder.write(e, "ProcessService({0}):{1}", this.getName(), e.getMessage());
		}
	}

	/**
	 * <b>开始处理报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void start() {
		if (isStopped()) {
			this.stopped = false;
			recorder.write("Start the processing service:[{0}].", this.getName());
		}
	}

	/**
	 * <b>停止处理报文。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public void stop() {
		if (!isStopped()) {
			this.stopped = true;
			recorder.write("Stop the processing service:[{0}].", this.getName());
		}
	}

	/**
	 * <b>服务是否是停止的。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 服务是否是停止的
	 */
	public final boolean isStopped() {
		return this.stopped;
	}

	/**
	 * <b>销毁处理服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public final void destroy() {
		this.stop();
		//等待完成
		try {
			this.awaitTermination();
		} catch (Exception e) {
			recorder.write(e, e.getMessage());
		}
		recorder.write("Exit the processing service:[{0}].", this.getName());
	}

	/**
	 * <b>等待缓存数据处理完成。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	protected abstract void awaitTermination() throws Exception;

	/**
	 * <b>获得所有处理服务列表。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 所有处理服务列表
	 */
	public static Collection<ProcessService<?, ?>> getAllProcessService() {
		List<ProcessService<?, ?>> list = new ArrayList<>(psRefMap.size());
		for (ProcessService<?, ?> ps : psRefMap.values()) {
			list.add(ps);
		}
		return list;
	}

	/**
	 * <b>获得处理服务。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param id 处理服务id
	 * @return 处理服务实例
	 */
	public static ProcessService<?, ?> getProcessService(String id) {
		return psRefMap.get(id);
	}

	/**
	 * <b>返回处理服务数量。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 处理服务数量
	 */
	public static int psCount() {
		return psRefMap.size();
	}

	/**
	 * 数据处理闭包
	 */
	protected static class DataClosure<K, V> implements HasTimestamp, Delayed {
		/**
		 * key。
		 */
		private final K key;
		/**
		 * 数据。
		 */
		private final V data;
		/**
		 * 处理器。
		 */
		private final Processor<V> processor;
		/**
		 * 时间戳。
		 */
		private long timestamp = System.currentTimeMillis();
		/**
		 * 延迟毫秒数。
		 */
		private final int delayMillis;

		public DataClosure(K key, V data, Processor<V> processor) {
			this(key, data, processor, HasDelay.noDelay);
		}

		public DataClosure(K key, V data, Processor<V> processor, int delayMillis) {
			this.key = key;
			this.data = data;
			this.processor = processor;
			this.delayMillis = delayMillis;
		}

		public void process() {
			processor.process(data);
		}

		@Override
		public long timestamp() {
			return timestamp;
		}

		@Override
		public void updateTimestamp() {
			this.timestamp = System.currentTimeMillis();
		}

		public K key() {
			return key;
		}

		public V data() {
			return data;
		}

		@Override
		public int compareTo(Delayed o) {
			if (o == null)
				return 1;
			else if (this == o) {
				return 0;
			} else {
				long v = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
				return (int) v;
			}
		}

		@Override
		public long getDelay(TimeUnit unit) {
			//剩余延迟
			if (data instanceof Delayed)
				return ((Delayed) data).getDelay(unit);
			else if (data instanceof HasDelay) {
				int delay = ((HasDelay) data).getDelayMillis();
				return getDelay(unit, delay == HasDelay.invalidDelay ? delayMillis : delay);
			} else {
				return getDelay(unit, delayMillis);
			}
		}

		//获得剩余延迟
		private long getDelay(TimeUnit unit, int delayMillis) {
			if (delayMillis == HasDelay.noDelay)
				return 0;
			else {
				long remaining = delayMillis - (System.currentTimeMillis() - timestamp());
				return remaining > 0 ? unit.convert(remaining, TimeUnit.MILLISECONDS) : 0;
			}
		}
	}
}
