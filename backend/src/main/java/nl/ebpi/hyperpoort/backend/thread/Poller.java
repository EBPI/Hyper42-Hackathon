package nl.ebpi.hyperpoort.backend.thread;

import java.time.LocalDateTime;
import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import nl.ebpi.hyperpoort.backend.hyperledger.AanleveringenGetter;
import nl.ebpi.hyperpoort.backend.hyperledger.FinalStatusSetter;
import nl.ebpi.hyperpoort.backend.hyperledger.StatusSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The Class Poller queries the hyperledger the the supplied aanleverkenmker.
 * Note that the poller is NOT threadsafe.
 */
@Component
@Scope("prototype")
public class Poller implements Runnable {

	/** The aanlever kenmerk. */
	private String aanleverKenmerk;

	@Autowired
	private AanleveringenGetter aanleveringenGetter;

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
		Aanlevering aanlevering = aanleveringenGetter.retrieveAanlevering(aanleverKenmerk);
		if (aanlevering != null) {
			System.out.println(LocalDateTime.now().toString() + " aanleverkenmerk gevonden: zet status 200");
			statusSetter.setStatus(aanleverKenmerk, "200");
			finalStatusSetter.setFinalStatus(aanleverKenmerk, "500");
		}
	}
}
