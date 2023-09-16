import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;

// var 11
public class Main {

    public static final int KeyBitLength = 32;

    public static final int Certainty = 1;

    public static final byte[] Source = "ABCD. 123. Съешь ещё этих мягких французских булочек"
            .getBytes();

    public static void main(String[] args) {
        Optional<RSAKeyPairGenerator.RSAKeyPair> value = RSA.getRSAKeyPair(
                KeyBitLength, Certainty, new SecureRandom()
        );

        if(value.isEmpty())
            return;

        System.out.println("Public key bits: " + value.get().getPublicKey().getPublicExponent().bitLength());
        System.out.println("Private key bits: " + value.get().getPrivateKey().getPrivateExponent().bitLength());

        int bits = value.get().getModulus().bitLength();
        int blockSize = bits/Byte.SIZE;
        int blockCount = Source.length/blockSize + 1;

        byte[] buffer;
        byte[] encoded;
        byte[] decoded = new byte[0];
        for(int i = 0, end; i < blockCount; i++) {

            buffer = new byte[blockSize];

            end = (i * blockSize) + blockSize;

            if(end > Source.length)
                end = Source.length;

            System.arraycopy(Source, i * blockSize, buffer, 0, end - (i * blockSize));

            encoded = RSA.getEncoder().encode(buffer, value.get().getPublicKey());

            System.out.printf("Encoded block #%d: %s\n", i + 1, Arrays.toString(encoded));

            buffer = RSA.getDecoder().decode(encoded, value.get().getPrivateKey());

            System.out.printf("Decoded block #%d: %s\n", i + 1, Arrays.toString(buffer));

            decoded = Arrays.copyOf(decoded, decoded.length + buffer.length);

            System.arraycopy(buffer, 0, decoded, decoded.length - buffer.length, buffer.length);

            System.out.println();
        }

        System.out.println("source: " + new String(Source, StandardCharsets.UTF_8));
        System.out.println("decoded: " + new String(decoded, StandardCharsets.UTF_8).replaceAll("\\x00", ""));
    }
}