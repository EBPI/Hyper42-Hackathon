package nl.ebpi.hyperpoort.backend.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class PollerService {
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private TaskExecutor taskExecutor;

	public void executeAsynchronously(String aanleverkenmer) {
		Poller poller = applicationContext.getBean(Poller.class);
		poller.setAanleverKenmerk(aanleverkenmer);
		taskExecutor.execute(poller);
	}
}
