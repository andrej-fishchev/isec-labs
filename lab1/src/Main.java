public class Main {

    public static final char[] Alphabet = "ABCDEFGHIJKLMNOP"
            .toCharArray();

    public static final char[] Key = "MY KEY"
            .toCharArray();

    public static final int Offset = 3;

    public static final char[] Source = "NOPE"
            .toCharArray();

    public static void main(String[] args) {

        char[] encoded = Caesar.getEncoder(Alphabet).encode(
                Source,
                Key,
                Offset
        );

        char[] decoded = Caesar.getDecoder(Alphabet).decode(
                encoded,
                Key,
                Offset
        );

        System.out.printf("[CAESAR] source = %s; encoded = %s (%s; %d); decoded = %s \n",
                String.valueOf(Source),
                String.valueOf(encoded),
                String.valueOf(Key),
                Offset,
                String.valueOf(decoded)
        );
    }
}