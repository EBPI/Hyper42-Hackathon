package nl.ebpi.hyperpoort.backend.thread;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The Class Poller queries the hyperledger the the supplied aanleverkenmker.
 * Note that the poller is NOT threadsafe.
 */
@Component
@Scope("prototype")
public class Poller implements Runnable {

	/** The aanlever kenmerk. */
	private String aanleverKenmerk;

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The aanlevering. */
	private Aanlevering aanlevering;

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
			RequestEntity<String> request;
			try {
				request = new RequestEntity<>(aanleverKenmerk, HttpMethod.POST,
						new URL("http://10.0.169.30:3000/api/queries/selectAanleveringenOpKenmerk").toURI());
			} catch (MalformedURLException | URISyntaxException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			ResponseEntity<Aanlevering> responseEntity = restTemplate.exchange(request, Aanlevering.class);
			if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null) {
				aanlevering = responseEntity.getBody();
			}
		}
	}
}
