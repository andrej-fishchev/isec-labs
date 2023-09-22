import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Elgamal {

    public static BigInteger getKey(int bitLen, Random rnd) { return BigInteger.probablePrime(bitLen, rnd); }

    public static Signature getSignature(ElgamalPublicKey key, IElgamalHashFunc hashFunc) { return new Signature(key, hashFunc); }

    public static class Signature {

        private final ElgamalPublicKey publicKey;

        private final IElgamalHashFunc functor;

        private Signature (ElgamalPublicKey publicKey, IElgamalHashFunc functor) {
            this.publicKey = publicKey;
            this.functor = functor;
        }

        public ElgamalSignedMessage sign(byte[] message, ElgamalPrivateKey pri) {

            BigInteger sub = publicKey.getP().subtract(BigInteger.ONE);

            BigInteger hashcode = functor.hash(message, publicKey);

            if(!Elgamal.InRange(BigInteger.ONE, hashcode, sub))
                return null;

            BigInteger k = getRandomK(sub, new SecureRandom());

            BigInteger r = publicKey.getG().modPow(k, publicKey.getP());

            BigInteger s = hashcode.subtract(pri.getKey().multiply(r)).multiply(k.modInverse(sub));

            return new ElgamalSignedMessage(message, r, s);
        }

        public boolean verify(ElgamalSignedMessage elgamalSignedMessage) {

            BigInteger hashcode = functor.hash(elgamalSignedMessage.getMessage(), publicKey);

            // (y^r * r^s) mod p == g^m mod p
            // -->
            // ((y^r mod p) * (r^s mod p)) mod p == g^m mod p
            return publicKey.getKey().modPow(elgamalSignedMessage.getR(), publicKey.getP())
                    .multiply(elgamalSignedMessage.getR().modPow(elgamalSignedMessage.getS(), publicKey.getP())).mod(publicKey.getP())
                    .compareTo(publicKey.getG().modPow(hashcode, publicKey.getP())) == 0;
        }

        private BigInteger getRandomK(BigInteger subP, Random rnd) {
            BigInteger k;

            do    k = BigInteger.probablePrime(subP.bitLength(), rnd);
            while(k.compareTo(subP) > 0 || !k.gcd(subP).equals(BigInteger.ONE));

            return k;
        }

    }

    private static boolean InRange(BigInteger l, BigInteger x, BigInteger r) {
        return l.compareTo(x) < 0 && x.compareTo(r) < 0;
    }
}
