import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;

public class CRSAPublicKey implements RSAPublicKey {

    private final BigInteger expo;

    private final BigInteger mod;

    public CRSAPublicKey(BigInteger exponent, BigInteger modulus) {
        expo = exponent;
        mod  = modulus;
    }

    @Override
    public BigInteger getPublicExponent() {
        return expo;
    }

    @Override
    public String getAlgorithm() { return "RSA"; }

    @Override
    public String getFormat() {
        return "%s.%s";
    }

    @Override
    public byte[] getEncoded() {
        return String.format(getFormat(), getPublicExponent(), getModulus()).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BigInteger getModulus() {
        return mod;
    }
}