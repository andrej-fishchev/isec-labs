package com.example.lab4.cipher;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class DES {

    public static SecretKey genKey(int bitLen, Random rnd) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("DES");

        generator.init(bitLen, (SecureRandom) rnd);

        return generator.generateKey();
    }

    public static Encoder getEncoder() throws NoSuchPaddingException, NoSuchAlgorithmException { return new Encoder(); }

    public static Decoder getDecoder() throws NoSuchPaddingException, NoSuchAlgorithmException { return new Decoder(); }

    public static class Encoder {

        private final Cipher cipher = Cipher.getInstance("DES");

        private Encoder() throws NoSuchPaddingException, NoSuchAlgorithmException {}

        public byte[] encode(byte[] value, SecretKey key) throws
                InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

            cipher.init(Cipher.ENCRYPT_MODE, key, new SecureRandom());

            return cipher.doFinal(value);
        }
    }

    public static class Decoder {

        private final Cipher cipher = Cipher.getInstance("DES");

        private Decoder() throws NoSuchPaddingException, NoSuchAlgorithmException {}

        public byte[] decode(byte[] value, SecretKey key) throws
                InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

            cipher.init(Cipher.DECRYPT_MODE, key, new SecureRandom());

            return cipher.doFinal(value);
        }
    }
}
