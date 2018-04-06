package nl.ebpi.hyperpoort.backend;

import nl.ebpi.hyperpoort.backend.hyperledger.UpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class StatusSetter {
    @Autowired
    RestTemplate restTemplate;

    public boolean setStatus(String kenmerk, String newStatus, String aanleveraar) throws URISyntaxException {
        UpdateStatus updateStatus = new UpdateStatus();
        updateStatus.setKenmerk(kenmerk);
        updateStatus.setStatus(newStatus);
        updateStatus.setAanleveraar(aanleveraar);
        URI uri = new URI("asd");

        RequestEntity<UpdateStatus> requestEntity = new RequestEntity<>(HttpMethod.POST, uri);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, void.class);
        return responseEntity.getStatusCode().is2xxSuccessful();

    }


}
