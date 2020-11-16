package org.quincy.rock.comm.netty;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyUtilTest {

	@Test
	public void testIndexOf() {

	}

	@Test
	public void testBCD() {
		String hex = "012F", rtn;
		ByteBuf buf = Unpooled.buffer(100);
		NettyUtil.writeBCD(buf, hex, 2);
		rtn = NettyUtil.readBCD(buf, 2);
		assertEquals(hex, rtn.toUpperCase());
	}

}
