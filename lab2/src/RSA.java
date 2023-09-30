import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Optional;
import java.util.Random;

public class RSA {

    private RSA() {}

    public static RSA.KeyPair getKeys(int bitLen, int certainty, Random rnd)
        { return KeysGenerator.generate(bitLen, certainty, rnd); }

    public static Encoder getEncoder() { return new Encoder(); }

    public static Decoder getDecoder() { return new Decoder(); }

    public static final class KeyPair implements RSAKey {

        private final CRSAPublicKey publicKey;

        private final CRSAPrivateKey privateKey;

        private KeyPair(CRSAPublicKey pubKey, CRSAPrivateKey priKey) {
            publicKey = pubKey;
            privateKey = priKey;
        }

        public CRSAPublicKey getPublicKey() { return publicKey; }

        public CRSAPrivateKey getPrivateKey() { return privateKey; }

        @Override
        public BigInteger getModulus() {
            return publicKey.getModulus();
        }

        @Override
        public AlgorithmParameterSpec getParams() {
            return RSAKey.super.getParams();
        }
    }

    public static class KeysGenerator {

        private KeysGenerator() {}

        public static Optional<RSA.KeyPair> tryGenerate(int bitLen, int certainty, Random rnd) {

            bitLen /= 2;

            try {
                BigInteger p = new BigInteger(bitLen, certainty, rnd);
                BigInteger q = new BigInteger(bitLen, certainty, rnd);

                BigInteger n = p.multiply(q);

                BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

                BigInteger e;

                do    e = BigInteger.probablePrime(phi.bitLength(), rnd);
                while(e.compareTo(n) > 0 || !e.gcd(phi).equals(BigInteger.ONE));

                BigInteger d = e.modInverse(phi);

                if(!BigInteger.TEN.modPow(e.multiply(d), n).equals(BigInteger.TEN))
                    throw new ArithmeticException();

                return Optional.of(new RSA.KeyPair(new CRSAPublicKey(e, n), new CRSAPrivateKey(d, n)));
            }
            catch (Exception ignored) {}

            return Optional.empty();
        }

        public static RSA.KeyPair generate(int bitLen, int certainty, Random rnd) {
            Optional<RSA.KeyPair> pair;

            do pair = tryGenerate(bitLen, certainty, rnd);
            while(pair.isEmpty());

            return pair.get();
        }
    }

    public static class Encoder {

        private Encoder() {}

        public byte[] encode(byte[] value, CRSAPublicKey key) {

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

        public byte[] decode(byte[] value, CRSAPrivateKey key) {

            if(value.length == 0)
                return value;

            BigInteger decoder = new BigInteger(value).modPow(key.getPrivateExponent(), key.getModulus());

            if(value[0] < 0)
                decoder = decoder.negate();

            return decoder.toByteArray();
        }
    }
}
