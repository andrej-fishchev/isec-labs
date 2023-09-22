import java.math.BigInteger;

public interface IElgamalHashFunc {

    BigInteger hash(byte[] message, ElgamalPublicKey publicKey);

}
