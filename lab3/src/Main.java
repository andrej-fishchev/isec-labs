import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;


// var 27
public class Main {

    // bits
    public static final int KeyBitLength = 256;

    // bytes
    public static final int MessageLength = 256;

    public static final IElgamalHashFunc HashFuncInstance = new SimpleMessageHasher();

    public static final String ToFilePath = "/data/some.txt";

    public static void main(String[] args) {

        try(FileInputStream inputStream = new FileInputStream(getAbsolutePathToFile())) {

            ElgamalPrivateKey privateKey;
            Elgamal.Signature signerInstance;

            byte[] buffer = new byte[MessageLength];

            while((inputStream.readNBytes(buffer, 0, Math.min(MessageLength, inputStream.available()))) > 0) {

                privateKey = new ElgamalPrivateKey(Elgamal.getKey(KeyBitLength, new SecureRandom()));

                signerInstance = Elgamal.getSignature(getPublicKey(privateKey), HashFuncInstance);

                verify(sign(buffer, signerInstance, privateKey), signerInstance);

                buffer = new byte[MessageLength];
            }

        } catch (IOException ignored) {}
    }

    static String getAbsolutePathToFile() {
        return System.getProperty("user.dir") +  ToFilePath;
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