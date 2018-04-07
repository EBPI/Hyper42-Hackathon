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

public class FinalStatusSetterTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private FinalStatusSetter fixture;

	@Before
	public void createFinalStatusSetter() throws Exception {
		fixture = new FinalStatusSetter();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

	@Test
	public void testSetFinalStatusOK() throws Exception {
		Capture<RequestEntity<FinalStatusUpdate>> requestCapture = Capture.newInstance();
		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

		mocksControl.replay();
		boolean result = fixture.setFinalStatus("kenmerk", "500");
		mocksControl.verify();

		Assert.assertTrue(result);
		Assert.assertEquals("kenmerk", requestCapture.getValue().getBody().getKenmerk());
		Assert.assertEquals("500", requestCapture.getValue().getBody().getNewStatus());

	}

	@Test
	public void testSetFinalStatusError() throws Exception {
		Capture<RequestEntity<FinalStatusUpdate>> requestCapture = Capture.newInstance();
		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

		mocksControl.replay();
		boolean result = fixture.setFinalStatus("kenmerk", "500");
		mocksControl.verify();

		Assert.assertFalse(result);

		Assert.assertEquals("kenmerk", requestCapture.getValue().getBody().getKenmerk());
		Assert.assertEquals("500", requestCapture.getValue().getBody().getNewStatus());
	}

}
