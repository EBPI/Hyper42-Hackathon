package nl.ebpi.hyperpoort.backend.thread;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Poller implements Runnable {
	private String aanleverKenmerk;

	public void setAanleverKenmerk(String aanleverKenmerk) {
		this.aanleverKenmerk = aanleverKenmerk;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
