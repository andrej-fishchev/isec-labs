import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class ElgamalSignedMessage {

    private final byte[] message;

    private final BigInteger r;

    private final BigInteger s;

    public ElgamalSignedMessage(byte[] message, BigInteger r, BigInteger s) {
        this.message = message;
        this.r = r;
        this.s = s;
    }

    public byte[] getMessage() {
        return message;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getS() {
        return s;
    }

    @Override
    public String toString() {
        return String.format("%s [%s; %s]", new String(getMessage(), StandardCharsets.UTF_8), getR(), getS());
    }
}
