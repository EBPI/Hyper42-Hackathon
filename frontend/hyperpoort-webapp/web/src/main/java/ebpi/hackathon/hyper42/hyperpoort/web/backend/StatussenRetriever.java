package ebpi.hackathon.hyper42.hyperpoort.web.backend;

import ebpi.hackathon.hyper42.hyperpoort.web.hyperledger.Aanlevering;
import ebpi.hackathon.hyper42.hyperpoort.web.model.Status;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StatussenRetriever {

	@Autowired
	private RestTemplate restTemplate;

	public List<Status> getStatussen() {
		URI uri = URI.create("http://10.0.169.30:3002/api/Aanlevering");
		String uriString = uri.toString();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ParameterizedTypeReference<List<Aanlevering>> returnType = new ParameterizedTypeReference<List<Aanlevering>>() {
		};

		ResponseEntity<List<Aanlevering>> responseEntity = restTemplate.exchange(uriString, HttpMethod.GET, entity, returnType,
				(List<Aanlevering>) Collections.EMPTY_LIST);
		List<Aanlevering> aanleveringen = responseEntity.getBody();
		return aanleveringen.stream().map(a -> Status.builder().addStatussen(a.getStatus()).setKenmerk(a.getAanleverKenmerk()).build())
				.collect(Collectors.toList());
	}

}
