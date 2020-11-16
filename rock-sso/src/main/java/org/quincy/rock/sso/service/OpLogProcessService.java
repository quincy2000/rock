package org.quincy.rock.sso.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.quincy.rock.core.concurrent.BoundedQueueProcessService;
import org.quincy.rock.core.concurrent.Processor;
import org.quincy.rock.core.exception.NotFoundException;
import org.quincy.rock.core.lang.Recorder;
import org.quincy.rock.core.lang.TraceRecorder;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.JsonUtil;
import org.quincy.rock.core.util.RockUtil;
import org.quincy.rock.core.vo.Result;
import org.quincy.rock.sso.OpLog;
import org.quincy.rock.sso.SSOException;
import org.quincy.rock.sso.util.SSOUtils;
import org.slf4j.Logger;

/**
 * <b>操作日志处理服务。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2020年3月10日 下午2:58:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class OpLogProcessService extends BoundedQueueProcessService<String, OpLog> implements Processor<OpLog> {
	/**
	 * logger。
	 */
	private static final Logger logger = RockUtil.getLogger(OpLogProcessService.class);

	private static final Recorder RECORDER = new TraceRecorder() {

		@Override
		public boolean canWrite() {
			return super.canWrite() && logger.isDebugEnabled();
		}

		@Override
		protected void trace(String message, Throwable t) {
			if (t == null)
				logger.debug(message);
			else
				logger.error(message, t);
		}

	};

	/**
	 * 连接池。
	 */
	private final PoolingHttpClientConnectionManager pool;

	/**
	 * SSO服务器url。
	 */
	private String ssoServerUrl;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public OpLogProcessService() {
		this(2, 1024, 2);
	}

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param maxThreadCount 最大线程数
	 * @param capacity 每个线程持有队列容量
	 * @param timeToLive http连接存活秒数
	 */
	public OpLogProcessService(int maxThreadCount, int capacity, int timeToLive) {
		super(maxThreadCount, capacity);
		this.pool = new PoolingHttpClientConnectionManager(timeToLive, TimeUnit.SECONDS);
		this.pool.setDefaultMaxPerRoute(maxThreadCount);
		this.pool.setMaxTotal(maxThreadCount);
		this.setRecorder(RECORDER);
	}

	/**
	 * <b>获得SSO服务器url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return SSO服务器url
	 */
	public String getSsoServerUrl() {
		return ssoServerUrl;
	}

	/**
	 * <b>设置SSO服务器url。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param ssoServerUrl SSO服务器url
	 */
	public void setSsoServerUrl(String ssoServerUrl) {
		this.ssoServerUrl = ssoServerUrl.endsWith("/") ? ssoServerUrl.substring(0, ssoServerUrl.length() - 1)
				: ssoServerUrl;
	}

	/**
	 * <b>写操作日志。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param opLog 操作日志
	 */
	public void writeOpLog(OpLog opLog) {
		try {
			this.put(opLog.getLogonName(), opLog);
		} catch (Exception e) {
			recorder.write(e, "Put operation log failed!");
		}
	}

	/** 
	 * getProcessor。
	 * @see org.quincy.rock.core.concurrent.ProcessService#getProcessor(java.lang.Object)
	 */
	@Override
	protected Processor<OpLog> getProcessor(String key) throws NotFoundException {
		return this;
	}

	/** 
	 * awaitTermination。
	 * @see org.quincy.rock.core.concurrent.QueueProcessService#awaitTermination()
	 */
	@Override
	protected void awaitTermination() throws Exception {
		super.awaitTermination();
		IOUtil.closeQuietly(this.pool);
	}

	/** 
	 * process。
	 * @see org.quincy.rock.core.concurrent.Processor#process(java.lang.Object)
	 */
	@Override
	public void process(OpLog opLog) {
		String json = SSOUtils.executeUrl(httpClient(), getSsoServerUrl(), "/mis", "/writeOplog_json",
				JsonUtil.toJson(opLog));
		Result<Boolean> result = Result.toResult(json, Boolean.class);
		if (!Boolean.TRUE.equals(result.getResult()))
			throw new SSOException("Write operation log failed!");
	}

	/** 
	 * createBlockingQueue。
	 * @see org.quincy.rock.core.concurrent.BoundedQueueProcessService#createBlockingQueue(int)
	 */
	@Override
	protected BlockingQueue<DataClosure<String, OpLog>> createBlockingQueue(int capacity) {
		return new ArrayBlockingQueue<>(capacity);
	}

	/**
	 * <b>获得HttpClient。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return HttpClient
	 */
	private HttpClient httpClient() {
		return HttpClients.custom().setConnectionManager(this.pool).build();
	}
}
