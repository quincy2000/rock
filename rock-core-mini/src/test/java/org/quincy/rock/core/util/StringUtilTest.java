package org.quincy.rock.core.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.quincy.rock.core.function.EachConsumer;

public class StringUtilTest {

	@Test
	public void testSplit() {
		String str = ",1,2,3,4,5,";
		final List<String> list = new ArrayList<>();
		int count = StringUtil.split(str, StringUtil.CHAR_COMMA, new EachConsumer<CharSequence>() {

			@Override
			public void each(int index, CharSequence cs) {
				list.add(cs.toString());
			}
		});
		assertEquals(count, list.size());
		System.out.println(Integer.toString(1234567890, 32));
	}

}
