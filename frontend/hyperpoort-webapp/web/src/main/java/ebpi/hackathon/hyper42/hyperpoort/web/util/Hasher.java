package ebpi.hackathon.hyper42.hyperpoort.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

	private Hasher() {
		// private constructor for util.
	}

	public static byte[] giveHash(byte[] input) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		byte[] hash = digest.digest(input);
		return hash;
	}
}
