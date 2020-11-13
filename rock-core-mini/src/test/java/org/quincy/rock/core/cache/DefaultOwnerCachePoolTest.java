package org.quincy.rock.core.cache;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DefaultOwnerCachePoolTest {
	/**
	 * pool。
	 */
	private OwnerCachePool<Object> pool = new DefaultOwnerCachePool<>();

	@Test
	public void test() {
		pool.putBufferValue("a", "a", "me");
		boolean ok = pool.hasBufferValue("a", "a");
		assertTrue(ok);
	}

}
