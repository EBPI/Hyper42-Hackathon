package nl.ebpi.hyperpoort.backend.thread;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.util.ReflectionTestUtils;

public class PollerServiceTest {
	private final IMocksControl mocksControl = EasyMock.createControl();
	private ApplicationContext applicationContext;

	private TaskExecutor taskExecutor;
	private PollerService fixture;

	@Before
	public void createPollerService() throws Exception {
		fixture = new PollerService();

		applicationContext = mocksControl.createMock(ApplicationContext.class);

		taskExecutor = mocksControl.createMock(TaskExecutor.class);
		ReflectionTestUtils.setField(fixture, "applicationContext", applicationContext);
		ReflectionTestUtils.setField(fixture, "taskExecutor", taskExecutor);
	}

	@Test
	public void testExecuteAsynchronously() throws Exception {
		Capture<Class> classCapture = Capture.newInstance();
		Poller poller = new Poller();
		EasyMock.expect(applicationContext.getBean(EasyMock.capture(classCapture))).andReturn(poller);
		Capture<Poller> pollerCapture = Capture.newInstance();
		taskExecutor.execute(EasyMock.capture(pollerCapture = Capture.newInstance()));
		EasyMock.expectLastCall();

		mocksControl.replay();
		fixture.executeAsynchronously("aanleverkenmerk");
		mocksControl.verify();

		Assert.assertEquals(poller, pollerCapture.getValue());
		Assert.assertEquals("aanleverkenmerk", ReflectionTestUtils.getField(poller, "aanleverKenmerk"));
	}

}
