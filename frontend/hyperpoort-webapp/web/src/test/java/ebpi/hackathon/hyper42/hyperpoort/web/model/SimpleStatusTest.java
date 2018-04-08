package ebpi.hackathon.hyper42.hyperpoort.web.model;

import org.junit.Assert;
import org.junit.Test;

public class SimpleStatusTest {

	@Test
	public void testCreation() {
		SimpleStatus simpleStatus = new SimpleStatus("Hyper", 42);
		Assert.assertEquals("Hyper", simpleStatus.getKenmerk());
		Assert.assertEquals(Integer.valueOf(42), simpleStatus.getStatusnr());
	}
}
