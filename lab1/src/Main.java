
// var 24
public class Main {

    public static final char[] Alphabet = ("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" + " ")
            .toCharArray();

    public static final char[] Key = "КЛЮЧЕВОЕ СЛоВО"
            .toCharArray();

    public static final int Offset = 3;

    public static final char[] Source = "Съешь ещё этих мягких французских булочек"
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

        // example output
        // source:  Съешь ещё этих мягких французских булочек
        // encoded: БтЫрфшЫсЬшхйамшдчЩвамшлзХенкЯивамшЦкгёпЫв (key: КЛЮЧЕВОЕ СЛоВО; offset: 3)
        // decoded: Съешь ещё этих мягких французских булочек
        System.out.printf("[CAESAR]\nsource  = %s\nencoded = %s (key: %s; offset: %d)\ndecoded = %s \nequal: %b\n[/CAESAR]\n",
                String.valueOf(Source),
                String.valueOf(encoded),
                String.valueOf(Key),
                Offset,
                String.valueOf(decoded),
                String.valueOf(Source).equals(String.valueOf(decoded))
        );
    }
}