package ebpi.hackathon.hyper42.hyperpoort.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.common.io.ByteSink;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


@Service
public class LedgerService {
    @Autowired
    private RestTemplate restTemplate;

    public void makeCard(String kvkNumber, String adres) throws URISyntaxException, IOException {
        File card;

        AanleverendePartij aanleverendePartij = new AanleverendePartij(kvkNumber, adres);

        RequestEntity<AanleverendePartij> request = new RequestEntity<>(aanleverendePartij, HttpMethod.POST,
                new URL("http://10.0.169.30:3000/api/org.ebpi.hackathon.AanleverendePartij").toURI());
        ResponseEntity<String> responseEntity = restTemplate.exchange(request, String.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            String participantId = "resource:org.ebpi.hackathon.AanleverendePartij#" + kvkNumber;
            String userId = "User" + kvkNumber;

            Identity identity = new Identity(participantId, userId);

            RequestEntity<Identity> request2 = new RequestEntity<>(identity, HttpMethod.POST,
                    new URL("http://10.0.169.30:3000/api/system/identities/issue").toURI());
            ResponseEntity<byte[]> responseEntity2 = restTemplate.exchange(request2, byte[].class);
            card = new File(kvkNumber + ".card");
            ByteSink sink = Files.asByteSink(card);
            sink.write(responseEntity2.getBody());
        } else {
            throw (new IOException("Getting organisation failed."));
        }
    }
}
