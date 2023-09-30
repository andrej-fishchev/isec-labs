import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

// var 11
public class Main {

    public static final int KeyBitLength = 4096;

    public static final RSA.Decoder DecoderInstance = RSA.getDecoder();

    public static final RSA.Encoder EncoderInstance = RSA.getEncoder();

    public static final RSA.KeyPair Keys = RSA.getKeys(KeyBitLength, 1, new SecureRandom());

    public static final String ToFilePath = "/data/some.txt";

    public static void main(String[] args) {

        try(FileInputStream inputStream = new FileInputStream(getAbsolutePathToFile())) {

            int iBlockInBytes = getBlockSizeInBytes(Keys.getModulus().bitLength());

            byte[] encoded;
            byte[] decoded  =   new byte[0];
            byte[] buffer   =   new byte[iBlockInBytes];

            while((inputStream.readNBytes(buffer, 0, Math.min(iBlockInBytes, inputStream.available()))) > 0) {

                encoded = encode(buffer, EncoderInstance, Keys.getPublicKey());

                decoded = copyWithResizeCustom(decoded, decode(encoded, DecoderInstance, Keys.getPrivateKey()));

                buffer = new byte[iBlockInBytes];
            }

            System.out.println("Decoded: \n" + new String(decoded, StandardCharsets.UTF_8).replaceAll("\\x00", ""));

        } catch (IOException ignored) {}
    }

    static byte[] copyWithResizeCustom(byte[] source, byte[] value) {

        source = Arrays.copyOf(source, source.length + value.length);

        System.arraycopy(value, 0, source, source.length - value.length, value.length);

        return source;
    }

    static String getAbsolutePathToFile() {
        return System.getProperty("user.dir") +  ToFilePath;
    }

    static int getBlockSizeInBytes(int realKeyBits) {
        return realKeyBits / Byte.SIZE;
    }

    public static byte[] encode(byte[] value, RSA.Encoder encoder, CRSAPublicKey key) {

        byte[] encoded = encoder.encode(value, key);

        System.out.printf("Source: %s \nSourceInBytes: %s (total: %d) \nEncodedInBytes: %s (total: %d); \nEncoded: %s\n\n",
                new String(value, StandardCharsets.UTF_8),
                Arrays.toString(value), value.length,
                Arrays.toString(encoded), encoded.length,
                new String(encoded, StandardCharsets.UTF_8));

        return encoded;
    }

    public static byte[] decode(byte[] value, RSA.Decoder decoder, CRSAPrivateKey key) {

        byte[] decoded = decoder.decode(value, key);

        System.out.printf("Source: %s \nSourceInBytes: %s (total: %d) \nDecodedInBytes: %s (total: %d); \nDecoded: %s\n\n",
                new String(value, StandardCharsets.UTF_8),
                Arrays.toString(value), value.length,
                Arrays.toString(decoded), decoded.length,
                new String(decoded, StandardCharsets.UTF_8));

        return decoded;
    }
}