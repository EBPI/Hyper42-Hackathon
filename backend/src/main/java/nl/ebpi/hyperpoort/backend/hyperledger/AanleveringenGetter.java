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
	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	public Aanlevering retrieveAanlevering(String aanleverKenmerk) {
		int counter = 0;
		while (counter < 60) {
			counter++;
			ResponseEntity<List<Aanlevering>> responseEntity = callBlockchain(aanleverKenmerk);

			if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				if (responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
					List<Aanlevering> list = responseEntity.getBody();
					return list.get(0);
				} else {
					waitForNextCall();
				}
			} else {
				System.out.println(LocalDateTime.now().toString() + " error bij ophalen aanlevering " + responseEntity.getStatusCode());
			}
		}
		return null;
	}

	private void waitForNextCall() {
		try {
			System.out.println(LocalDateTime.now().toString() + " wachten op aanleverkenmerk");
			Thread.sleep(WAIT_TIME_IN_MILLIES);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	private ResponseEntity<List<Aanlevering>> callBlockchain(String aanleverKenmerk) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlConstants.getAanleveringenGetterUrl()).queryParam("aanleverkenmerk",
				aanleverKenmerk);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ParameterizedTypeReference<List<Aanlevering>> aanleveringen = new ParameterizedTypeReference<List<Aanlevering>>() {
		};
		String uriString = builder.toUriString();
		System.out.println(uriString);
		ResponseEntity<List<Aanlevering>> responseEntity = restTemplate.exchange(uriString, HttpMethod.GET, entity, aanleveringen,
				(Map<String, ?>) Collections.EMPTY_MAP);
		return responseEntity;
	}
}
