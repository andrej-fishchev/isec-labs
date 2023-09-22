import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

public class ElgamalPrivateKey implements PrivateKey {

    private final String algo = "Elgamal";

    private final BigInteger x;

    public ElgamalPrivateKey(BigInteger x) {
        this.x = x;
    }

    public BigInteger getKey() {
        return x;
    }

    @Override
    public String getAlgorithm() {
        return algo;
    }

    @Override
    public String getFormat() {
        return "%s";
    }

    @Override
    public byte[] getEncoded() {
        return String.format(getFormat(), getKey()).getBytes(StandardCharsets.UTF_8);
    }
}
