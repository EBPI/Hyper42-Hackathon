package nl.ebpi.hyperpoort.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import nl.ebpi.hyperpoort.backend.model.Aanlevering;
import nl.ebpi.hyperpoort.backend.thread.PollerService;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test voor {@link BackendController}.
 */
public class BackendControllerTest {

	private BackendController backendController;
	private Aanlevering aanlevering;
	private PollerService pollerService;
	IMocksControl mocksControl = EasyMock.createControl();

	@Before
	public void init() {
		backendController = new BackendController();
		pollerService = mocksControl.createMock(PollerService.class);
		ReflectionTestUtils.setField(backendController, "pollerService", pollerService);

		aanlevering = new Aanlevering();
		aanlevering.setBericht("Ook korte aanleveringen zijn belangrijk");

	}

	@Test
	public void leveraan() throws IOException, URISyntaxException {
		Capture<String> kenmerkCapture = Capture.newInstance();
		pollerService.executeAsynchronously(EasyMock.capture(kenmerkCapture = Capture.newInstance()));
		EasyMock.expectLastCall();

		mocksControl.replay();
		String kenmkerk = backendController.aanleveren(aanlevering);
		mocksControl.verify();
		Assert.assertFalse(kenmkerk.isEmpty());
		Assert.assertEquals(kenmkerk, kenmerkCapture.getValue());
	}
}
