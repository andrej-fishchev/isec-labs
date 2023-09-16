import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class RSA {

    private RSA() {}

    public static Optional<RSAKeyPairGenerator.RSAKeyPair> getRSAKeyPair(int bitL, int certainty, Random rnd) {
        return RSAKeyPairGenerator.generate(bitL/2, certainty, rnd);
    }

    public static Encoder getEncoder() { return new Encoder(); }

    public static Decoder getDecoder() { return new Decoder(); }

    public static class Encoder {

        private Encoder() {}

        public byte[] encode(byte[] value, RSAPublicKey key) {

            if(value.length == 0)
                return value;

            // abs is just for signed bytes < 0 there high bit is 1
            byte[] encoded = new BigInteger(value).abs().modPow(
                  key.getPublicExponent(),
                  key.getModulus()
            ).toByteArray();

            encoded = Arrays.copyOf(encoded, encoded.length + 1);

            // hack just for abs() tracking :/
            encoded[encoded.length - 1]  =
                    (byte) (Math.random() * (Byte.MAX_VALUE - 1) + 1); // [1; 127)

            if(value[0] > 0)
                encoded[encoded.length - 1] *= -1;

            return encoded;
        }
    }

    public static class Decoder {

        private Decoder() {}

        public byte[] decode(byte[] value, RSAPrivateKey key) {

            if(value.length == 0)
                return value;

            BigInteger decoder = new BigInteger(value, 0, value.length - 1)
                    .modPow(key.getPrivateExponent(), key.getModulus());

            if(value[value.length - 1] > 0)
                decoder = decoder.negate();

            return decoder.toByteArray();
        }
    }
}
