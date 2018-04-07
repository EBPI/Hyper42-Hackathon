package ebpi.hackathon.hyper42.hyperpoort.web.util;

import org.junit.Assert;
import org.junit.Test;

public class HasherTest {

	@Test
	public void testHashing() {
		byte[] hash = Hasher.giveHash("abc".getBytes());
		Assert.assertNotNull(hash);
	}

}
