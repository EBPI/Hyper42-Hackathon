package nl.ebpi.hyperpoort.backend.thread;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.ebpi.hyperpoort.backend.FinalStatusSetter;
import nl.ebpi.hyperpoort.backend.StatusSetter;
import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The Class Poller queries the hyperledger the the supplied aanleverkenmker.
 * Note that the poller is NOT threadsafe.
 */
@Component
@Scope("prototype")
public class Poller implements Runnable {

	private static final String URL = "http://10.0.169.30:3000/api/queries/selectAanleveringenOpKenmerk";

	/** The aanlever kenmerk. */
	private String aanleverKenmerk;

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The aanlevering. */
	private Aanlevering aanlevering;

	@Autowired
	private StatusSetter statusSetter;

	@Autowired
	private FinalStatusSetter finalStatusSetter;

	/**
	 * Sets the aanlever kenmerk.
	 *
	 * @param aanleverKenmerk the new aanlever kenmerk
	 */
	public void setAanleverKenmerk(String aanleverKenmerk) {
		this.aanleverKenmerk = aanleverKenmerk;
	}

	@Override
	public void run() {
		while (aanlevering == null) {
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
					aanlevering = list.get(0);
					System.out.println(LocalDateTime.now().toString() + " aanleverkenmerk gevonden: zet status 200");
					statusSetter.setStatus(aanleverKenmerk, "200", aanlevering.getAanleveraar());
					finalStatusSetter.setFinalStatus(aanleverKenmerk, "500", aanlevering.getAanleveraar());
				} else {
					try {
						Thread.sleep(10000);
						System.out.println(LocalDateTime.now().toString() + " wachten op aanleverkenmerk");
					} catch (InterruptedException e) {
						// ignore
					}
				}
			} else {
				System.out.println(LocalDateTime.now().toString() + " error bij ophalen aanlevering " + responseEntity.getStatusCode());
			}
		}
	}
}
