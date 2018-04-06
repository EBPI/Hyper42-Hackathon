package nl.ebpi.hyperpoort.backend;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public byte[] giveHash(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return hash;
    }
}
