package com.ponaguynik.passwordprotector.other;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;


public class Password {

    private static final int iterations = 2000;
    private static final int saltLen = 16;
    private static final int desiredKeyLen = 128;
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);

        return ENCODER.encodeToString(salt) + "$" + hash(password, salt);
    }

    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, DECODER.decode(saltAndPass[0]));

        return hashOfInput.equals(saltAndPass[1]);
    }

    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen)
        );

        return ENCODER.encodeToString(key.getEncoded());
    }
}
