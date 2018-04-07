package nl.ebpi.hyperpoort.backend.thread;

import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import nl.ebpi.hyperpoort.backend.hyperledger.AanleveringenGetter;
import nl.ebpi.hyperpoort.backend.hyperledger.FinalStatusSetter;
import nl.ebpi.hyperpoort.backend.hyperledger.StatusSetter;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PollerTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private Poller fixture;
	private StatusSetter statusSetter;
	private FinalStatusSetter finalStatusSetter;
	private AanleveringenGetter aanleveringenGetter;

	@Before
	public void createPoller() throws Exception {
		fixture = new Poller();

		aanleveringenGetter = mocksControl.createMock(AanleveringenGetter.class);
		ReflectionTestUtils.setField(fixture, "aanleveringenGetter", aanleveringenGetter);

		statusSetter = mocksControl.createMock(StatusSetter.class);
		ReflectionTestUtils.setField(fixture, "statusSetter", statusSetter);

		finalStatusSetter = mocksControl.createMock(FinalStatusSetter.class);
		ReflectionTestUtils.setField(fixture, "finalStatusSetter", finalStatusSetter);
	}

	@Test
	public void testPoll() throws Exception {
		fixture.setAanleverKenmerk("aanleverkenmerk");
		Aanlevering aanlevering = new Aanlevering();
		aanlevering.setAanleveraar("aanleveraar");

		Capture<String> aanleverKenmerkCapture = Capture.newInstance(CaptureType.ALL);
		EasyMock.expect(aanleveringenGetter.retrieveAanlevering(EasyMock.capture(aanleverKenmerkCapture))).andReturn(aanlevering);

		Capture<String> statusCapture = Capture.newInstance(CaptureType.ALL);
		Capture<String> aanleveraarCapture = Capture.newInstance();
		EasyMock.expect(statusSetter.setStatus(EasyMock.capture(aanleverKenmerkCapture), EasyMock.capture(statusCapture)))
				.andReturn(Boolean.TRUE);

		EasyMock.expect(finalStatusSetter.setFinalStatus(EasyMock.capture(aanleverKenmerkCapture), EasyMock.capture(statusCapture))).andReturn(Boolean.TRUE);

		mocksControl.replay();
		fixture.run();
		mocksControl.verify();

		Assert.assertEquals(3, aanleverKenmerkCapture.getValues().size());
		Assert.assertEquals("aanleverkenmerk", aanleverKenmerkCapture.getValues().get(0));
		Assert.assertEquals("aanleverkenmerk", aanleverKenmerkCapture.getValues().get(1));
		Assert.assertEquals("aanleverkenmerk", aanleverKenmerkCapture.getValues().get(2));

		Assert.assertEquals(2, statusCapture.getValues().size());
		Assert.assertEquals("200", statusCapture.getValues().get(0));
		Assert.assertEquals("500", statusCapture.getValues().get(1));
	}

}
