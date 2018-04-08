package ebpi.hackathon.hyper42.hyperpoort.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class LedgerServiceTest {
    private final IMocksControl mocksControl = EasyMock.createControl();
    private RestTemplate restTemplate;
    private LedgerService fixture;
    private ObjectMapper mapper;

    @Before
    public void createAanleveringStarter() throws Exception {
        fixture = new LedgerService();
        mapper = new ObjectMapper();

        restTemplate = mocksControl.createMock(RestTemplate.class);
        ReflectionTestUtils.setField(fixture, "restTemplate", restTemplate);
    }

    @Test
    public void successfullFirstRegistration() throws IOException, URISyntaxException {
        String kvkNumber = "1234";
        String adres = "Testdreef 94";

        Capture<RequestEntity<AanleverendePartij>> requestEntityCapture1 = Capture.newInstance();
        EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestEntityCapture1), EasyMock.anyObject(Class.class))).andReturn(EasyMock.anyString());

        Capture<RequestEntity<Identity>> requestEntityCapture2 = Capture.newInstance();

        byte[] testBytes = "test".getBytes();
        ResponseEntity<byte[]> response = new ResponseEntity<>(testBytes, HttpStatus.OK);
        EasyMock.expect(restTemplate.exchange(EasyMock.capture(requestEntityCapture2), byte[].class)).andReturn(response);

        mocksControl.replay();
        fixture.makeCard("1234", "Testdreef 94");
        mocksControl.verify();

        Assert.assertTrue(requestEntityCapture1.hasCaptured());
        AanleverendePartij capturedPartij = requestEntityCapture1.getValue().getBody();
        Assert.assertTrue(capturedPartij.getKvknummer().equals(kvkNumber));
        Assert.assertTrue(capturedPartij.getAdres().equals(adres));
        Assert.assertTrue(requestEntityCapture2.hasCaptured());
        String fileName = kvkNumber + ".card";
        File file = new File(fileName);
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.delete());
    }
}
