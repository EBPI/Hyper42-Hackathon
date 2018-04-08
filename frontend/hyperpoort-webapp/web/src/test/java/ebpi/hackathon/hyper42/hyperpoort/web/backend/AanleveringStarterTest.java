package ebpi.hackathon.hyper42.hyperpoort.web.backend;

import ebpi.hackathon.hyper42.hyperpoort.web.hyperledger.Aanleveren;
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

public class AanleveringStarterTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private AanleveringStarter fixture;

	@Before
	public void createAanleveringStarter() throws Exception {
		fixture = new AanleveringStarter();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

	@Test
	public void testSubmit() throws Exception {
		Capture<RequestEntity<Aanleveren>> requestEntityCapture = Capture.newInstance();
		Aanleveren aanleveren = new Aanleveren();
		aanleveren.setAanleverKenmerk("myKenmerk");
		aanleveren.setOntvangerId("123");
		aanleveren.setHash("123");
		ResponseEntity<String> responseEntity = new ResponseEntity<>("myKenmerk", HttpStatus.OK);
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestEntityCapture), EasyMock.anyObject(Class.class))).andReturn(responseEntity);

		mocksControl.replay();
		String kenmerk = fixture.submit("dit is een berichtje".getBytes(), "123");
		mocksControl.verify();
		Assert.assertEquals("myKenmerk", kenmerk);
	}

}
