package nl.ebpi.hyperpoort.backend.hyperledger;

import nl.ebpi.hyperpoort.backend.hyperledger.Aanlevering;
import nl.ebpi.hyperpoort.backend.hyperledger.StatusSetter;
import nl.ebpi.hyperpoort.backend.thread.Poller;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class StatusSetterTest {
    private final IMocksControl mocksControl = EasyMock.createControl();
    private RestTemplate restTemplate;
    private StatusSetter fixture;

    @Before
    public void init() throws Exception {
        fixture = new StatusSetter();
        restTemplate = mocksControl.createMock(RestTemplate.class);
        ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
    }


    @Test
    public void setStatusSucces() {
        Capture<RequestEntity<String>> requestCapture = Capture.newInstance();

        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);

        EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

        mocksControl.replay();
      Assert.assertTrue( fixture.setStatus("5","5"));
        mocksControl.verify();

    }


    @Test
    public void setStatusError() {
        Capture<RequestEntity<String>> requestCapture = Capture.newInstance();

        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestCapture), EasyMock.anyObject(Class.class))).andReturn(response);

        mocksControl.replay();
        Assert.assertFalse(fixture.setStatus("5","5"));
        mocksControl.verify();

    }


}