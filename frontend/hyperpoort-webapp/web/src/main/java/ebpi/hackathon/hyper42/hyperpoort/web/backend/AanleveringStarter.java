package ebpi.hackathon.hyper42.hyperpoort.web.backend;

import ebpi.hackathon.hyper42.hyperpoort.web.backend.model.Aanleveren;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AanleveringStarter {

	@Autowired
	private RestTemplate restTemplate;

	public String submit(byte[] hash, String ontvanger) {
		Aanleveren aanleveren = new Aanleveren();
		aanleveren.setBericht(ontvanger);

		URI url;
		try {
			url = new URI("http://10.0.170.193:8080/hyperpoort/aanleveren");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		RequestEntity<Aanleveren> requestEntity = new RequestEntity<>(aanleveren, HttpMethod.POST, url);
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		return responseEntity.getBody();
	}

}
