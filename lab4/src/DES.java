import java.math.BigInteger;
import java.util.Random;

public class DES {

    public static BigInteger getKey(int bitLen, Random rnd) { return BigInteger.probablePrime(bitLen, rnd); }

    public static Encoder getEncoder() { return new Encoder(); }

    public static Decoder getDecoder() { return new Decoder(); }


    public static class Encoder {

        private Encoder() {}

        public byte[] encode(byte[] value, BigInteger key) {
            return value;
        }
    }

    public static class Decoder {

        private Decoder() {}

        public byte[] decode(byte[] value, BigInteger key) {
            return value;
        }
    }
}
