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
public class FinalStatusSetter {
	@Autowired
	RestTemplate restTemplate;

	public boolean setFinalStatus(String kenmerk, String newStatus) {
		FinalStatusUpdate updateStatus = new FinalStatusUpdate();
		updateStatus.setKenmerk(kenmerk);
		updateStatus.setNewStatus(newStatus);
		URI uri = null;
		try {
			uri = new URI(UrlConstants.getSetFinalStatusUrl());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}

		RequestEntity<FinalStatusUpdate> requestEntity = new RequestEntity<>(updateStatus, HttpMethod.POST, uri);
		ResponseEntity<FinalStatusUpdate> responseEntity = restTemplate.exchange(requestEntity, FinalStatusUpdate.class);
		return responseEntity.getStatusCode().is2xxSuccessful();
	}

}
