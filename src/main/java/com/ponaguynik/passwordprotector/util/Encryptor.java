package com.ponaguynik.passwordprotector.util;

import com.ponaguynik.passwordprotector.model.DataForm;
import com.ponaguynik.passwordprotector.model.User;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


/**
 * The Encryptor class is used for encryption/decryption and hashing data.
 */
public final class Encryptor {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    /**
     * Special key for encoding and decoding data.
     */
    private static final Key KEY = new SecretKeySpec(new byte[] {'H', 'e', '1', '/', 'I', '8', 'U',
            'M', '.', '4', '\\',';', 'o', 'J', 'S', 'y'} , "AES");

    private Encryptor() {

    }

    public static void encryptUser(User user) {
        user.setUsername(encrypt(user.getUsername()));
        user.setKeyword(getSaltedHash(user.getKeyword()));
    }

    public static void encryptDataForm(DataForm dataForm) {
        dataForm.setTitle(encrypt(dataForm.getTitle()));
        dataForm.setLogin(encrypt(dataForm.getLogin()));
        dataForm.setPassword(encrypt(dataForm.getPassword()));
    }

    public static void decryptUser(User user) {
        user.setUsername(decrypt(user.getUsername()));
    }

    public static void decryptDataForm(DataForm dataForm) {
        dataForm.setTitle(decrypt(dataForm.getTitle()));
        dataForm.setLogin(decrypt(dataForm.getLogin()));
        dataForm.setPassword(decrypt(dataForm.getPassword()));
    }

    /**
     * @return a string of salt and hash of the data.
     * The returned string has salt$hash format.
     */
    public static String getSaltedHash(String data) {
        byte[] salt = new byte[0];
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }

        return ENCODER.encodeToString(salt) + "$" + hash(data, salt);
    }

    /**
     * Check whether s1 matches salted hash of s2.
     *
     * @param s1 is a regular string.
     * @param s2 is a string of salt$hash format.
     * @return return true if s1 matches s2.
     */
    public static boolean check(String s1, String s2) {
        String[] saltAndHash = s2.split("\\$");
        if (saltAndHash.length != 2) {
            throw new IllegalStateException(
                    "The stored data has the form 'salt$hash'");
        }
        String hashOfInput = hash(s1, DECODER.decode(saltAndHash[0]));
        return hashOfInput.equals(saltAndHash[1]);
    }

    /**
     * Encrypt a string.
     *
     * @param data is a string that should be encrypted.
     * @return the encrypted string of the data.
     */
    public static String encrypt(String data) {
        if (data == null || data.isEmpty())
            return "";
        byte[] encVal = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, KEY);
            encVal = c.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
        return DatatypeConverter.printBase64Binary(encVal);
    }

    /**
     * Decrypt an encrypted string.
     *
     * @param encryptedData is an encrypted data that should
     * be decrypted.
     * @return the decrypted string of the encrypted data.
     */
    public static String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty())
            return "";
        byte[] decValue;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, KEY);
            byte[] decodedValue = DatatypeConverter.parseBase64Binary(encryptedData);
            decValue = c.doFinal(decodedValue);
        } catch (Exception e) {
            return null;
        }
        return new String(decValue);
    }

    /**
     * @return a string of salted hash of the data.
     */
    private static String hash(String data, byte[] salt)  {
        if (data == null || data.isEmpty())
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f;
        SecretKey key = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = f.generateSecret(new PBEKeySpec(
                    data.toCharArray(), salt, 2000, 128)
            );
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }

        return ENCODER.encodeToString(key.getEncoded());
    }
}
