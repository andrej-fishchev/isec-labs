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

            if(alphabet.length < secret.length || offset < 0 || offset >= alphabet.length)
                return value;

            int secretEnd = secret.length + offset;

            if(secretEnd > alphabet.length)
                return value;

            char[] secretRow = makeSecretRow(alphabet, secret, offset);

            StringBuilder output = new StringBuilder();

            for(int i = 0, pos; i < value.length; i++)
            {
                if((pos = Caesar.indexOf(alphabet, value[i])) == -1) {
                    output.append(value[i]);
                    continue;
                }

                output.append(secretRow[(pos + alphabet.length - offset) % alphabet.length]);
            }

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

            if(alphabet.length < secret.length || offset < 0 || offset >= alphabet.length)
                return value;

            int secretEnd = secret.length + offset;

            if(secretEnd > alphabet.length)
                return value;

            char[] secretRow = makeSecretRow(alphabet, secret, offset);

            StringBuilder output = new StringBuilder();

            for(int i = 0, pos; i < value.length; i++)
            {
                if((pos = Caesar.indexOf(secretRow, value[i])) == -1) {
                    output.append(value[i]);
                    continue;
                }

                output.append(alphabet[(pos + offset) % alphabet.length]);
            }

            return output.toString().toCharArray();
        }
    }

    private static char[] makeSecretRow(char[] alpha, char[] secret, int offset) {

        char[] row = new char[alpha.length];

        System.arraycopy(secret, 0, row, 0, secret.length);

        for(int i = 0, j = 0; i < alpha.length; i++) {

            if(indexOf(row, alpha[i]) != -1)
                continue;

            if(secret.length + j >= alpha.length)
                break;

            row[secret.length + j] = alpha[i];
            j++;
        }

        return row;
    }
}


