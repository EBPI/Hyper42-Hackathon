package ebpi.hackathon.hyper42.hyperpoort.web.backend;

import ebpi.hackathon.hyper42.hyperpoort.web.hyperledger.Aanleveren;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AanleveringRegistrar {
	@Autowired
	private RestTemplate restTemplate;

	public boolean submit(byte[] hash, String ontvanger, String kenmerk) {
		Aanleveren aanleveren = new Aanleveren();
		aanleveren.setAanleverKenmerk(kenmerk);
		aanleveren.setHash(new String(hash));
		aanleveren.setOntvangerId(ontvanger);

		URI url;
		try {
			url = new URI("http://10.0.169.30:3001/api/Aanleveren");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		RequestEntity<Aanleveren> requestEntity = new RequestEntity<>(aanleveren, HttpMethod.POST, url);
		ResponseEntity<Aanleveren> responseEntity = restTemplate.exchange(requestEntity, Aanleveren.class);
		return responseEntity.getStatusCode().equals(HttpStatus.OK);

	}

}
