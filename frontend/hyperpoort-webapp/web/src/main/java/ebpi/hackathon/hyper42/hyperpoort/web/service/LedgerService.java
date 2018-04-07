package ebpi.hackathon.hyper42.hyperpoort.web.service;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class LedgerService {
	@Autowired
	private RestTemplate restTemplate;

	public void makeCard(String kvkNumber, String adres) throws URISyntaxException, IOException {
		AanleverendePartij aanleverendePartij = new AanleverendePartij();
		aanleverendePartij.setKvknummer(kvkNumber);
		aanleverendePartij.setAdres(adres);
		System.out.println(aanleverendePartij);

		RequestEntity<AanleverendePartij> request = new RequestEntity<>(aanleverendePartij, HttpMethod.POST,
				new URL("http://10.0.169.30:3000/api/AanleverendePartij").toURI());
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(request, String.class);
		} catch (HttpServerErrorException e) {
			// already exist
		}

		Identity identity = new Identity();
		identity.setParticipant("resource:org.ebpi.hackathon.AanleverendePartij#" + kvkNumber);
		identity.setUserID("User" + kvkNumber);

		RequestEntity<Identity> request2;

		ResponseEntity<byte[]> responseEntity2;
		try {
			request2 = new RequestEntity<>(identity, HttpMethod.POST, new URL("http://10.0.169.30:3000/api/system/identities/issue").toURI());
			responseEntity2 = restTemplate.exchange(request2, byte[].class);
		} catch (HttpServerErrorException e) {
			// If identity already exists, change the userId
			identity.setUserID("User" + kvkNumber + UUID.randomUUID());
			request2 = new RequestEntity<>(identity, HttpMethod.POST, new URL("http://10.0.169.30:3000/api/system/identities/issue").toURI());
			responseEntity2 = restTemplate.exchange(request2, byte[].class);
		}
		File card = new File(kvkNumber + ".card");
		ByteSink sink = Files.asByteSink(card);
		sink.write(responseEntity2.getBody());
	}
}
