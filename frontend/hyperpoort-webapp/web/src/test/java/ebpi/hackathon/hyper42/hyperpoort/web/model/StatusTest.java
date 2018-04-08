package ebpi.hackathon.hyper42.hyperpoort.web.model;

import ebpi.hackathon.hyper42.hyperpoort.web.model.Status.Builder;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class StatusTest {

	@Test
	public void testCreation() {
		Builder builder = Status.builder().setKenmerk("Hyper").addStatussen(Collections.singletonList(Integer.valueOf(42)));
		Status status = builder.build();
		Assert.assertEquals(status.getKenmerk(), "Hyper");
		Assert.assertEquals(1, status.getStatussen().size());
		Assert.assertEquals(Integer.valueOf(42), status.getStatussen().get(0));

	}

}
