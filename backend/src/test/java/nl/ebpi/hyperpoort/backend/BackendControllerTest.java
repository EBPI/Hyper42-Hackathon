package nl.ebpi.hyperpoort.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import nl.ebpi.hyperpoort.backend.model.Aanlevering;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test voor {@link BackendController}.
 */
public class BackendControllerTest {

	private BackendController backendController;
	private Aanlevering aanlevering;

	@Before
	public void init() {
		backendController = new BackendController();
		aanlevering = new Aanlevering();
		aanlevering.setBericht("Ook korte aanleveringen zijn belangrijk");

	}

	@Test
	public void leveraan() throws IOException, URISyntaxException {
		String kenmkerk = backendController.aanleveren(aanlevering);
		Assert.assertFalse(kenmkerk.isEmpty());

	}
}
