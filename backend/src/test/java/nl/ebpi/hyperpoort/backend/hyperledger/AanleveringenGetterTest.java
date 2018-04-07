package nl.ebpi.hyperpoort.backend.hyperledger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.easymock.Capture;
import org.easymock.CaptureType;
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

public class AanleveringenGetterTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private AanleveringenGetter fixture;

	@Before
	public void createPoller() throws Exception {
		fixture = new AanleveringenGetter();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

	@Test
	public void testFindInFirstTry() throws Exception {
		Aanlevering aanlevering = new Aanlevering();
		aanlevering.setAanleveraar("aanleveraar");
		ResponseEntity<List<Aanlevering>> response = new ResponseEntity<>(Collections.singletonList(aanlevering), HttpStatus.OK);

		Capture<String> urlCapture = Capture.newInstance();
		Capture<HttpMethod> httpMethodeCapture = Capture.newInstance();
		Capture<HttpEntity<?>> entityCapture = Capture.newInstance();
		Capture<ParameterizedTypeReference<List<Aanlevering>>> parameterizedTypeReferenceCapture = Capture.newInstance();
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(urlCapture), EasyMock.capture(httpMethodeCapture), EasyMock.capture(entityCapture),
				EasyMock.capture(parameterizedTypeReferenceCapture), EasyMock.anyObject(Map.class))).andReturn(response);

		mocksControl.replay();
		Aanlevering retrieveAanlevering = fixture.retrieveAanlevering("kenmerk");
		mocksControl.verify();

		Assert.assertEquals(aanlevering, retrieveAanlevering);
		Assert.assertTrue(urlCapture.getValue().endsWith("/api/queries/selectAanleveringenOpKenmerk?aanleverkenmerk=kenmerk"));
		Assert.assertEquals(HttpMethod.GET, httpMethodeCapture.getValue());
		Assert.assertNotNull(entityCapture.getValue());
		Assert.assertNotNull(parameterizedTypeReferenceCapture.getValue());
	}

	@Test
	public void testFindInSecondTry() throws Exception {
		Aanlevering aanlevering = new Aanlevering();
		aanlevering.setAanleveraar("aanleveraar");
		ResponseEntity<List<Aanlevering>> responseEmpty = new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
		ResponseEntity<List<Aanlevering>> response = new ResponseEntity<>(Collections.singletonList(aanlevering), HttpStatus.OK);

		Capture<String> urlCapture = Capture.newInstance(CaptureType.ALL);
		Capture<HttpMethod> httpMethodeCapture = Capture.newInstance(CaptureType.ALL);
		Capture<HttpEntity<?>> entityCapture = Capture.newInstance(CaptureType.ALL);
		Capture<ParameterizedTypeReference<List<Aanlevering>>> parameterizedTypeReferenceCapture = Capture.newInstance(CaptureType.ALL);
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(urlCapture), EasyMock.capture(httpMethodeCapture), EasyMock.capture(entityCapture),
				EasyMock.capture(parameterizedTypeReferenceCapture), EasyMock.anyObject(Map.class))).andReturn(responseEmpty);
		EasyMock.expect(restTemplate.exchange(EasyMock.capture(urlCapture), EasyMock.capture(httpMethodeCapture), EasyMock.capture(entityCapture),
				EasyMock.capture(parameterizedTypeReferenceCapture), EasyMock.anyObject(Map.class))).andReturn(response);

		mocksControl.replay();
		Aanlevering retrieveAanlevering = fixture.retrieveAanlevering("kenmerk");
		mocksControl.verify();

		Assert.assertEquals(aanlevering, retrieveAanlevering);
		Assert.assertEquals(2, urlCapture.getValues().size());
		Assert.assertTrue(urlCapture.getValues().get(0).endsWith("/api/queries/selectAanleveringenOpKenmerk?aanleverkenmerk=kenmerk"));
		Assert.assertTrue(urlCapture.getValues().get(1).endsWith("/api/queries/selectAanleveringenOpKenmerk?aanleverkenmerk=kenmerk"));
		Assert.assertEquals(2, httpMethodeCapture.getValues().size());
		Assert.assertEquals(HttpMethod.GET, httpMethodeCapture.getValues().get(0));
		Assert.assertEquals(HttpMethod.GET, httpMethodeCapture.getValues().get(1));
		Assert.assertEquals(2, entityCapture.getValues().size());
		Assert.assertNotNull(entityCapture.getValues().get(0));
		Assert.assertNotNull(entityCapture.getValues().get(1));
		Assert.assertEquals(2, parameterizedTypeReferenceCapture.getValues().size());
		Assert.assertNotNull(parameterizedTypeReferenceCapture.getValues().get(0));
		Assert.assertNotNull(parameterizedTypeReferenceCapture.getValues().get(1));
	}
}
