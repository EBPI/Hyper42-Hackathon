package nl.ebpi.hyperpoort.backend.hyperledger;

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

public class StatusSetterTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private StatusSetter fixture;

	@Before
	public void init() throws Exception {
		fixture = new StatusSetter();
		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

	@Test
	public void setStatusSucces() {
		Capture<RequestEntity<StatusUpdate>> requestCapture = Capture.newInstance();

		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

		mocksControl.replay();
		Assert.assertTrue(fixture.setStatus("Five", "5"));
		mocksControl.verify();

		Assert.assertEquals("Five", requestCapture.getValue().getBody().getKenmerk());
		Assert.assertEquals("5", requestCapture.getValue().getBody().getNewStatus());

	}

	@Test
	public void setStatusError() {
		Capture<RequestEntity<StatusUpdate>> requestCapture = Capture.newInstance();

		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.FORBIDDEN);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

		mocksControl.replay();
		Assert.assertFalse(fixture.setStatus("5", "5"));
		mocksControl.verify();

	}

}