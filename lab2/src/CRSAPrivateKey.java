import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;

public class CRSAPrivateKey implements RSAPrivateKey {
    private final BigInteger expo;

    private final BigInteger mod;

    public CRSAPrivateKey(BigInteger exponent, BigInteger modulus) {
        expo = exponent;
        mod  = modulus;
    }

    @Override
    public BigInteger getPrivateExponent() {
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
        return String.format(getFormat(), getPrivateExponent(), getModulus()).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BigInteger getModulus() {
        return mod;
    }
}