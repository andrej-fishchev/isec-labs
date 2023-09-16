import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
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

            BigInteger bigInteger = new BigInteger(value).modPow(key.getPublicExponent(), key.getModulus());

            if(value[0] < 0)
                bigInteger = bigInteger.negate();

            return bigInteger.toByteArray();
        }
    }

    public static class Decoder {

        private Decoder() {}

        public byte[] decode(byte[] value, RSAPrivateKey key) {

            if(value.length == 0)
                return value;

            BigInteger decoder = new BigInteger(value).modPow(key.getPrivateExponent(), key.getModulus());

            if(value[0] < 0)
                decoder = decoder.negate();

            return decoder.toByteArray();
        }
    }
}
