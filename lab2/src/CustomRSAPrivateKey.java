import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public class CustomRSAPrivateKey implements RSAPrivateKey {

    private final String algo = "RSA";

    private BigInteger expo;

    private BigInteger mod;

    public CustomRSAPrivateKey(BigInteger exponent, BigInteger modulus) {
        expo = exponent;
        mod  = modulus;
    }

    @Override
    public BigInteger getPrivateExponent() {
        return expo;
    }

    @Override
    public String getAlgorithm() {
        return algo;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public byte[] getEncoded() {
        return null;
    }

    @Override
    public BigInteger getModulus() {
        return mod;
    }
}