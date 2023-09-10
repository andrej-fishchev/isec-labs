import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;

public class RSA {

    private RSA() {}

    public static RSAKey getPairKeys() { return new KeyGenerator(); }

    public static Encoder getEncoder() { return new Encoder(); }

    public static Decoder getDecoder() { return new Decoder(); }

    public static class KeyGenerator implements RSAKey {

        private RSAPublicKey publicKey;

        private RSAPrivateKey privateKey;

        private KeyGenerator() { }

        @Override
        public BigInteger getModulus() {
            return publicKey.getModulus().multiply(privateKey.getModulus());
        }

        @Override
        public AlgorithmParameterSpec getParams() {
            return RSAKey.super.getParams();
        }
    }

    public static class Encoder {

        private Encoder() {
            // ...
        }

        public char[] encode(char[] value, RSAPublicKey key) {
            return value; // ...
        }

    }

    public static class Decoder {

        private Decoder() {
            // ...
        }

        public char[] decode(char[] value, RSAPrivateKey key) {
            return value; // ...
        }
    }
}
