import java.math.BigInteger;

public class SimpleMessageHasher implements IElgamalHashFunc {

    @Override
    public BigInteger hash(byte[] message, ElgamalPublicKey publicKey) {
        return new BigInteger(message).mod(publicKey.getP().subtract(BigInteger.ONE)).abs();
    }
}
