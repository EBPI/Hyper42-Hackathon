package ebpi.hackathon.hyper42.hyperpoort.web.util;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import nl.ebpi.hyperpoort.backend.Hasher;
import org.junit.Assert;
import org.junit.Test;

public class HasherTest {
	  @Test
	    public void hashString() {
	        String toHash = "I am a simple man";


	        MessageDigest digest = null;
	        try {
	            digest = MessageDigest.getInstance("SHA-256");
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        byte[] expectation = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
	        Hasher hasher = new Hasher();
	        byte[] result = hasher.giveHash(toHash);
	        int lenget = result.length;
	        for (int i = 0; i < lenget; i++) {
	            Assert.assertEquals(expectation[i], result[i]);
	        }


	    }
}
