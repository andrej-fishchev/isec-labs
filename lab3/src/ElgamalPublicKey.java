import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class ElgamalPublicKey implements PublicKey {

    private final String algo = "Elgamal";

    private final BigInteger g;

    private final BigInteger p;

    private final BigInteger y;

    public ElgamalPublicKey(BigInteger g, BigInteger p, ElgamalPrivateKey privateKey) {
        this.g = g;
        this.p = p;
        this.y = g.modPow(privateKey.getKey(), p);
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getKey() {
        return y;
    }

    @Override
    public String getAlgorithm() {
        return algo;
    }

    @Override
    public String getFormat() {
        return "%s-%s-%s";
    }

    @Override
    public byte[] getEncoded() {
        return toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return String.format(getFormat(), getG(), getP(), getKey());
    }
}
