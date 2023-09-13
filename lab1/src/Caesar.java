public class Caesar {

    private Caesar() {}

    public static Encoder getEncoder(char[] alphabet) {
        return new Encoder(alphabet);
    }

    public static Decoder getDecoder(char[] alphabet) { return new Decoder(alphabet); }

    public static class Encoder {

        private char[] alphabet;

        private Encoder(char[] alphabet) {
            this.alphabet = alphabet;
        }

        public char[] encode(char[] value, char[] secret, int offset) {

            if(alphabet.length < secret.length)
                return value;

            // from offset in range (-minInt; +maxInt)
            // to offset in range [0, alphabet.length)
            offset = shift(offset, alphabet.length);

            char[] encodedAlphabet = makeEncodedAlphabet(alphabet, secret, offset);

            StringBuilder output = new StringBuilder();

            for(int i = 0, pos; i < value.length; i++)
                output.append(
                        ((pos = indexOf(alphabet, value[i])) == -1)
                            ? value[i]                          // unknown character
                            : encodedAlphabet[pos]              // encoded character (using encoded alphabet)
                );

            return output.toString().toCharArray();
        }
    }

    private static int indexOf(char[] buffer, char value) {
        int idx = -1;

        for(int i = 0; idx == -1 && i < buffer.length; i++)
            if(buffer[i] == value)
                idx = i;

        return idx;
    }

    public static class Decoder {

        private char[] alphabet;

        private Decoder(char[] alpha) {
            alphabet = alpha;
        }

        public char[] decode(char[] value, char[] secret, int offset) {

            if(alphabet.length < secret.length)
                return value;

            offset = shift(offset, alphabet.length);

            char[] encodedAlphabet = makeEncodedAlphabet(alphabet, secret, offset);

            StringBuilder output = new StringBuilder();

            for(int i = 0, pos; i < value.length; i++)
                output.append(
                        ((pos = indexOf(encodedAlphabet, value[i])) == -1)
                                ? value[i]                          // unknown character
                                : alphabet[pos]                     // decoded character (using alphabet)
                );

            return output.toString().toCharArray();
        }
    }

    private static char[] makeEncodedAlphabet(char[] alpha, char[] secret, int offset) {

        char[] row = new char[alpha.length];

        for(int i = 0; i < secret.length; i++)
            row[shift(i + offset, alpha.length)] = secret[i];

        for(int i = 0,
            j = 0,
            offsetEnd = shift(offset + secret.length, alpha.length);
            j < alpha.length && i < alpha.length - secret.length; j++)
        {
            if(indexOf(row, alpha[j]) != -1)
                continue;

            row[shift(offsetEnd + i, alpha.length)] = alpha[j];
            i++;
        }

        return row;
    }

    private static int shift(int offset, int maxl) {
        return ((offset % maxl) + maxl) % maxl;
    }
}


