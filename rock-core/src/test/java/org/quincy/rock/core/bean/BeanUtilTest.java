package org.quincy.rock.core.bean;

import org.junit.Test;

public class BeanUtilTest {

	@Test
	public void testCompatibleOf() {
		Class<?>[] types=new Class<?>[] {Integer.TYPE,CharSequence.class};
		Class<?> type=BeanUtil.compatibleOf(String.class, types);
		System.out.println(type);
	}

}
