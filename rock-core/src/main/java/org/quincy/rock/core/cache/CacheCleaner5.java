package org.quincy.rock.core.cache;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * <b>缓存垃圾数据清理器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 每5分钟清理一次缓存。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2019年11月6日 上午10:51:13</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class CacheCleaner5 {
	/**
	 * <b>clearCache。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 每5分钟清理一次缓存。
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void clearCache() {
		CachePoolRef.clearCachePool();
	}
}
