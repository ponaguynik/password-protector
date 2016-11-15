package com.ponaguynik.passwordprotector.other;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;


public class Password {

    //Variables for hashing
    private static final int iterations = 2000;
    private static final int saltLen = 16;
    private static final int desiredKeyLen = 128;
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    //Variables for encryption and decryption
    private static final Key key = new SecretKeySpec(new byte[] {'H', 'e', '1', '/', 'I', '8', 'U',
            'M', '.', '4', '\\',';', 'o', 'J', 'S', 'y'} , "AES");

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

    public static String encrypt(String data) {
        if (data == null || data.isEmpty())
            return "";
        byte[] encVal = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            encVal = c.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return DatatypeConverter.printBase64Binary(encVal);
    }

    public static String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty())
            return "";
        byte[] decValue;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = DatatypeConverter.parseBase64Binary(encryptedData);;
            decValue = c.doFinal(decodedValue);
        } catch (Exception e) {
            return null;
        }
        return new String(decValue);
    }

    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen)
        );

        return ENCODER.encodeToString(key.getEncoded());
    }
}
