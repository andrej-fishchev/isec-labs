import java.math.BigInteger;
import java.util.Random;

public class CryptoGamma {

    public static BigInteger getKey(int bitLen, Random rnd) { return BigInteger.probablePrime(bitLen, rnd); }

    public static Encoder getEncoder() { return new Encoder(); }

    public static Decoder getDecoder() { return new Decoder(); }


    public static class Encoder {

        private Encoder() {}

        public byte[] encode(byte[] value, BigInteger key) {

            if(value.length == 0)
                return value;

            return new BigInteger(value).xor(key).toByteArray();
        }
    }

    public static class Decoder {

        private Decoder() {}

        public byte[] decode(byte[] value, BigInteger key) {

            if(value.length == 0)
                return value;

            return new BigInteger(value).xor(key).toByteArray();
        }
    }
}
