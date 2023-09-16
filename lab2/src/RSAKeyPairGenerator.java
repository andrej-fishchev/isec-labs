import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;
import java.util.Random;

public final class RSAKeyPairGenerator {

    private RSAKeyPairGenerator() {}

    public static final class RSAKeyPair implements RSAKey {

        private final CustomRSAPublicKey publicKey;

        private final CustomRSAPrivateKey privateKey;

        private RSAKeyPair(CustomRSAPublicKey pubKey, CustomRSAPrivateKey priKey) {
            publicKey = pubKey;
            privateKey = priKey;
        }

        public CustomRSAPublicKey getPublicKey() { return publicKey; }

        public CustomRSAPrivateKey getPrivateKey() { return privateKey; }

        @Override
        public BigInteger getModulus() {
            return publicKey.getModulus();
        }
    }

    public static Optional<RSAKeyPair> generate(int bitLen, int certainty, Random rnd) {

        Optional<RSAKeyPair> pair = Optional.empty();

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

            pair = Optional.of(new RSAKeyPair(
               new CustomRSAPublicKey(e, n),
               new CustomRSAPrivateKey(d, n)
            ));
        }
        catch (Exception ignored) {}

        return pair;
    }
}
