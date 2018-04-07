package nl.ebpi.hyperpoort.backend.hyperledger;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AanleveringenGetter {
	private static final int WAIT_TIME_IN_MILLIES = 5000;
	private static final String URL = "http://10.0.169.30:3000/api/queries/selectAanleveringenOpKenmerk";
	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	public Aanlevering retrieveAanlevering(String aanleverKenmerk) {
		int counter = 0;
		while (counter < 60) {
			counter++;
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL).queryParam("aanleverkenmerk", aanleverKenmerk);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			ParameterizedTypeReference<List<Aanlevering>> aanleveringen = new ParameterizedTypeReference<List<Aanlevering>>() {
			};
			ResponseEntity<List<Aanlevering>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, aanleveringen,
					(Map<String, ?>) Collections.EMPTY_MAP);

			if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				if (responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
					List<Aanlevering> list = responseEntity.getBody();
					return list.get(0);
				} else {
					try {
						Thread.sleep(WAIT_TIME_IN_MILLIES);
						System.out.println(LocalDateTime.now().toString() + " wachten op aanleverkenmerk");
					} catch (InterruptedException e) {
						// ignore
					}
				}
			} else {
				System.out.println(LocalDateTime.now().toString() + " error bij ophalen aanlevering " + responseEntity.getStatusCode());
			}
		}
		return null;
	}
}
