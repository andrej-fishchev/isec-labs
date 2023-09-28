import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

// var 23
public class Main {

    // note: KeyBitLength = 64 is the requirement of the task
    public static final int KeyBitLength = 64;

    public static final int MessageBlockMaxBitLength = 32;

    public static final DES.Decoder DecoderInstance = DES.getDecoder();

    public static final DES.Encoder EncoderInstance = DES.getEncoder();

    public static final BigInteger Key = DES.getKey(KeyBitLength, new SecureRandom());

    public static final String ToFilePath = "/data/some.txt";

    public static void main(String[] args) {

        byte[] decoded = new byte[0];

        int iBlockInBytes = getBlockSizeInBytes(MessageBlockMaxBitLength);

        try(FileInputStream inputStream = new FileInputStream(getAbsolutePath(ToFilePath))) {

            int iBytes;
            int iOffset = 0;

            byte[] encoded;
            byte[] buffer = new byte[iBlockInBytes];

            while((iBytes = inputStream.readNBytes(buffer, iOffset, Math.min(iBlockInBytes, inputStream.available()))) > 0) {

                if(iBytes != iBlockInBytes)
                    buffer = Arrays.copyOf(buffer, iBlockInBytes);

                encoded = encode(buffer, EncoderInstance, Key);

                decoded = copyWithResizeCustom(decoded, decode(encoded, DecoderInstance, Key));

                buffer = new byte[iBlockInBytes];
            }

            System.out.println();

        } catch (IOException ignored) {        }

        System.out.println("Decoded: \n" + new String(decoded, StandardCharsets.UTF_8).replaceAll("\\x00", ""));
    }

    static byte[] copyWithResizeCustom(byte[] source, byte[] value) {

        source = Arrays.copyOf(source, source.length + value.length);

        System.arraycopy(value, 0, source, source.length - value.length, value.length);

        return source;
    }

    static String getAbsolutePath(String path) {
        return System.getProperty("user.dir") +  path;
    }

    static int getBlockSizeInBytes(int bits) {
        return bits / Byte.SIZE;
    }

    static int getTotalBlocks(byte[] buffer, int blockSize) {
        return buffer.length / blockSize + 1;
    }

    public static byte[] encode(byte[] value, DES.Encoder encoder, BigInteger key) {

        byte[] encoded = encoder.encode(value, key);

        System.out.printf("Source: %s \nSourceInBytes: %s (total: %d) \nEncodedInBytes: %s (total: %d); \nEncoded: %s\n\n",
                new String(value, StandardCharsets.UTF_8),
                Arrays.toString(value), value.length,
                Arrays.toString(encoded), encoded.length,
                new String(encoded, StandardCharsets.UTF_8));

        return encoded;
    }

    public static byte[] decode(byte[] value, DES.Decoder decoder, BigInteger key) {

        byte[] decoded = decoder.decode(value, key);

        System.out.printf("Source: %s \nSourceInBytes: %s (total: %d) \nDecodedInBytes: %s (total: %d); \nDecoded: %s\n\n",
                new String(value, StandardCharsets.UTF_8),
                Arrays.toString(value), value.length,
                Arrays.toString(decoded), decoded.length,
                new String(decoded, StandardCharsets.UTF_8));

        return decoded;
    }
}