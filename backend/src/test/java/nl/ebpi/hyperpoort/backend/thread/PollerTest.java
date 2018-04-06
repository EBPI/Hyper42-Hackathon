package nl.ebpi.hyperpoort.backend.thread;

import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class PollerTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private Poller fixture;

	@Before
	public void createPoller() throws Exception {
		fixture = new Poller();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

	@Test
	public void testFindInFirstTry() throws Exception {
		fixture.setAanleverKenmerk("aanleverkenmerk");
		Capture<RequestEntity<String>> requestCapture = Capture.newInstance();
		Aanlevering aanlevering = new Aanlevering();
		ResponseEntity<Aanlevering> response = new ResponseEntity<>(aanlevering, HttpStatus.OK);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

		mocksControl.replay();
		fixture.run();
		mocksControl.verify();

		Assert.assertEquals("aanleverkenmerk", requestCapture.getValue().getBody());
		Assert.assertEquals(aanlevering, ReflectionTestUtils.getField(fixture, "aanlevering"));
	}

	@Test
	public void testFindInSecondTry() throws Exception {
		fixture.setAanleverKenmerk("aanleverkenmerk");
		Capture<RequestEntity<String>> requestCapture = Capture.newInstance();
		Aanlevering aanlevering = new Aanlevering();
		ResponseEntity<Aanlevering> responseEmpy = new ResponseEntity<>(null, HttpStatus.OK);
		ResponseEntity<Aanlevering> response = new ResponseEntity<>(aanlevering, HttpStatus.OK);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(responseEmpy);
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

		mocksControl.replay();
		fixture.run();
		mocksControl.verify();

		Assert.assertEquals("aanleverkenmerk", requestCapture.getValue().getBody());
		Assert.assertEquals(aanlevering, ReflectionTestUtils.getField(fixture, "aanlevering"));
	}

}
