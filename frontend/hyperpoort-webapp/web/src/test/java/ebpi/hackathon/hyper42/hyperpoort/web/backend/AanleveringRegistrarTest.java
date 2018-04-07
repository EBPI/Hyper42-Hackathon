package ebpi.hackathon.hyper42.hyperpoort.web.backend;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class AanleveringRegistrarTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private RestTemplate restTemplate;
	private AanleveringRegistrar fixture;

	@Before
	public void createAanleveringRegistrar() throws Exception {
		fixture = new AanleveringRegistrar();

		restTemplate = mocksControl.createMock(RestTemplate.class);
		ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
	}

}
