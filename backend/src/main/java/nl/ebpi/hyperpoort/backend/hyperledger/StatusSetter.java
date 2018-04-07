package nl.ebpi.hyperpoort.backend.hyperledger;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StatusSetter {
	@Autowired
	RestTemplate restTemplate;

	public boolean setStatus(String kenmerk, String newStatus) {
		StatusUpdate updateStatus = new StatusUpdate();
		updateStatus.setKenmerk(kenmerk);
		updateStatus.setNewStatus(newStatus);
		URI uri = null;
		try {
			uri = new URI(UrlConstants.getStatusUpdateUrl());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}

		RequestEntity<StatusUpdate> requestEntity = new RequestEntity<>(updateStatus, HttpMethod.POST, uri);
		ResponseEntity<StatusUpdate> responseEntity = restTemplate.exchange(requestEntity, StatusUpdate.class);
		return responseEntity.getStatusCode().is2xxSuccessful();

	}

}
