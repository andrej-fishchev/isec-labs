import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;


// var 27
public class Main {

    public static final int MessageBitLength = 256;

    public static final IElgamalHashFunc HashFuncInstance = new SimpleMessageHasher();

    public static final String ToFilePath = "/data/some.txt";

    public static void main(String[] args) {

        int iBlockInBytes = getBlockSizeInBytes(MessageBitLength);

        try(FileInputStream inputStream = new FileInputStream(getAbsolutePathToFile())) {

            ElgamalPublicKey publicKey;
            ElgamalPrivateKey privateKey;
            Elgamal.Signature signerInstance;

            int iBytes;
            int iOffset = 0;

            byte[] buffer = new byte[iBlockInBytes];

            while((iBytes = inputStream.readNBytes(buffer, iOffset, Math.min(iBlockInBytes, inputStream.available()))) > 0) {

                if(iBytes != iBlockInBytes)
                    buffer = Arrays.copyOf(buffer, iBlockInBytes);

                privateKey = new ElgamalPrivateKey(Elgamal.getKey(MessageBitLength, new SecureRandom()));

                publicKey = getPublicKey(privateKey);

                signerInstance = Elgamal.getSignature(publicKey, HashFuncInstance);

                verify(sign(buffer, signerInstance, privateKey), signerInstance);

                buffer = new byte[iBlockInBytes];
            }

            System.out.println();

        } catch (IOException ignored) {        }
    }

    static String getAbsolutePathToFile() {
        return System.getProperty("user.dir") +  ToFilePath;
    }

    static int getBlockSizeInBytes(int bits) {
        return bits / Byte.SIZE;
    }

    public static ElgamalSignedMessage sign(byte[] value, Elgamal.Signature signer, ElgamalPrivateKey key) {

        ElgamalSignedMessage signed = signer.sign(value, key);

        System.out.printf("Source: %s (total bytes: %d) \nSigned: %s\n\n", new String(value, StandardCharsets.UTF_8), value.length, signed);

        return signed;
    }

    public static void verify(ElgamalSignedMessage message, Elgamal.Signature signer) {
        System.out.printf("Verify state: %b\n\n", signer.verify(message));
    }

    public static ElgamalPublicKey getPublicKey(ElgamalPrivateKey pri) {
        BigInteger g = pri.getKey().nextProbablePrime();

        return new ElgamalPublicKey(
                g,
                g.multiply(pri.getKey()).nextProbablePrime(),
                pri
        );
    }
}