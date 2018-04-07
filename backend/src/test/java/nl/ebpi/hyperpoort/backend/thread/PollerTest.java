package nl.ebpi.hyperpoort.backend.thread;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.ebpi.hyperpoort.backend.StatusSetter;
import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class PollerTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private Poller fixture;
	private StatusSetter statusSetter;

	@Before
	public void createPoller() throws Exception {
		fixture = new Poller();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);

		statusSetter = mocksControl.createMock(StatusSetter.class);
		ReflectionTestUtils.setField(fixture, "statusSetter", statusSetter);
	}

	@Test
	public void testFindInFirstTry() throws Exception {
		fixture.setAanleverKenmerk("aanleverkenmerk");
		Aanlevering aanlevering = new Aanlevering();
		aanlevering.setAanleveraar("aanleveraar");
		ResponseEntity<List<Aanlevering>> response = new ResponseEntity<>(Collections.singletonList(aanlevering), HttpStatus.OK);

		Capture<String> urlCapture = Capture.newInstance();
		Capture<HttpMethod> httpMethodeCapture = Capture.newInstance();
		Capture<HttpEntity<?>> entityCapture = Capture.newInstance();
		Capture<ParameterizedTypeReference<List<Aanlevering>>> aanleveringenCapture = Capture.newInstance();
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(urlCapture), EasyMock.capture(httpMethodeCapture), EasyMock.capture(entityCapture),
				EasyMock.capture(aanleveringenCapture), EasyMock.anyObject(Map.class))).andReturn(response);

		Capture<String> aanleverKenmerkCapture = Capture.newInstance();
		Capture<String> statusCapture = Capture.newInstance();
		Capture<String> aanleveraarCapture = Capture.newInstance();
		EasyMock.expect(statusSetter.setStatus(EasyMock.capture(aanleverKenmerkCapture = Capture.newInstance()),
				EasyMock.capture(statusCapture = Capture.newInstance()),
				EasyMock.capture(aanleveraarCapture))).andReturn(Boolean.TRUE);
		mocksControl.replay();
		fixture.run();
		mocksControl.verify();

		Assert.assertEquals(aanlevering, ReflectionTestUtils.getField(fixture, "aanlevering"));
	}

	@Test
	public void testFindInSecondTry() throws Exception {
		ResponseEntity<Aanlevering> responseEmpty = new ResponseEntity<>(null, HttpStatus.OK);
		fixture.setAanleverKenmerk("aanleverkenmerk");
		Aanlevering aanlevering = new Aanlevering();
		List<Aanlevering> aanleveringen = Collections.singletonList(aanlevering);
		aanlevering.setAanleveraar("aanleveraar");
		ResponseEntity<List<Aanlevering>> response = new ResponseEntity<>(aanleveringen, HttpStatus.OK);

		Capture<String> urlCapture = Capture.newInstance();
		Capture<HttpMethod> httpMethodeCapture = Capture.newInstance();
		Capture<HttpEntity<?>> entityCapture = Capture.newInstance();

		Capture<ParameterizedTypeReference<List<Aanlevering>>> aanleveringenCapture = Capture.newInstance();
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(urlCapture), EasyMock.capture(httpMethodeCapture), EasyMock.capture(entityCapture),
				EasyMock.capture(aanleveringenCapture), EasyMock.anyObject(Map.class))).andReturn(responseEmpty);
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(urlCapture), EasyMock.capture(httpMethodeCapture), EasyMock.capture(entityCapture),
				EasyMock.capture(aanleveringenCapture), EasyMock.anyObject(Map.class))).andReturn(response);

		Capture<String> aanleverKenmerkCapture = Capture.newInstance();
		Capture<String> statusCapture = Capture.newInstance();
		Capture<String> aanleveraarCapture = Capture.newInstance();
		EasyMock.expect(statusSetter.setStatus(EasyMock.capture(aanleverKenmerkCapture = Capture.newInstance()),
				EasyMock.capture(statusCapture = Capture.newInstance()),
				EasyMock.capture(aanleveraarCapture))).andReturn(Boolean.TRUE);

		mocksControl.replay();
		fixture.run();
		mocksControl.verify();

		Assert.assertEquals(aanlevering, ReflectionTestUtils.getField(fixture, "aanlevering"));
	}

}
