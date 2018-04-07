package ebpi.hackathon.hyper42.hyperpoort.web.backend;

import ebpi.hackathon.hyper42.hyperpoort.web.hyperledger.Aanlevering;
import ebpi.hackathon.hyper42.hyperpoort.web.model.Status;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class StatussenRetrieverTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private StatussenRetriever fixture;

	@Before
	public void createStatussenRetriever() throws Exception {
		fixture = new StatussenRetriever();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

	@Test
	public void testGetStatussen() throws Exception {
		Capture<String> uriCapture = Capture.newInstance();
		Capture<HttpMethod> httpMethodCapture = Capture.newInstance();
		Capture<RequestEntity<List<Aanlevering>>> requestEntityCapture = Capture.newInstance();
		Capture<ParameterizedTypeReference<List<Aanlevering>>> responseType = Capture.newInstance();
		Capture<Map<String, ?>> parameterCapture = Capture.newInstance();

		Aanlevering aanlevering = new Aanlevering();
		aanlevering.setAanleveraar("aanleveraar");
		aanlevering.setAanleverKenmerk("Kenmerk");
		aanlevering.setHash("hashie");
		aanlevering.setOntvanger("ontv");
		List<Integer> statussen = Collections.singletonList(100);
		aanlevering.setStatus(statussen);
		List<Aanlevering> aanleveringen = Collections.singletonList(aanlevering);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(uriCapture), EasyMock.capture(httpMethodCapture),
				EasyMock.capture(requestEntityCapture), EasyMock.capture(responseType), EasyMock.capture(parameterCapture)))
				.andReturn(new ResponseEntity<List<Aanlevering>>(aanleveringen, HttpStatus.OK));

		mocksControl.replay();
		List<Status> statussenList = fixture.getStatussen("kenmerk");
		mocksControl.verify();

		Assert.assertEquals(1, statussenList.size());
		Assert.assertEquals(1, statussenList.get(0).getStatussen().size());
		Assert.assertEquals(Integer.valueOf(100), statussenList.get(0).getStatussen().get(0));
	}

	@Test
	public void testGetStatussenZonderKenmerk() throws Exception {
		Capture<String> uriCapture = Capture.newInstance();
		Capture<HttpMethod> httpMethodCapture = Capture.newInstance();
		Capture<RequestEntity<List<Aanlevering>>> requestEntityCapture = Capture.newInstance();
		Capture<ParameterizedTypeReference<List<Aanlevering>>> responseType = Capture.newInstance();
		Capture<List<String>> parameterCapture = Capture.newInstance();

		Aanlevering aanlevering = new Aanlevering();
		aanlevering.setAanleveraar("aanleveraar");
		aanlevering.setAanleverKenmerk("Kenmerk");
		aanlevering.setHash("hashie");
		aanlevering.setOntvanger("ontv");
		List<Integer> statussen = Collections.singletonList(100);
		aanlevering.setStatus(statussen);
		List<Aanlevering> aanleveringen = Collections.singletonList(aanlevering);

		EasyMock.expect(restTemplate.exchange(EasyMock.capture(uriCapture), EasyMock.capture(httpMethodCapture),
				EasyMock.capture(requestEntityCapture), EasyMock.capture(responseType), EasyMock.capture(parameterCapture)))
				.andReturn(new ResponseEntity<List<Aanlevering>>(aanleveringen, HttpStatus.OK));

		mocksControl.replay();
		List<Status> statussenList = fixture.getStatussen(null);
		mocksControl.verify();

		Assert.assertEquals(1, statussenList.size());
		Assert.assertEquals(1, statussenList.get(0).getStatussen().size());
		Assert.assertEquals(Integer.valueOf(100), statussenList.get(0).getStatussen().get(0));
	}

}
