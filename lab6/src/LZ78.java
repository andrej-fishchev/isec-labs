
// based on https://users.math-cs.spbu.ru/~okhotin/teaching/tcs2_2018/okhotin_tcs2alg_2018_lz78_example.pdf
// lectures https://users.math-cs.spbu.ru/~okhotin/teaching/tcs2_2019/okhotin_tcs2alg_2019_l4.pdf
public class LZ78 {

    public final static String ALGO = "LZ78";

    public final static String ALGO_FILE_EXTENSION = ".lz";

    public static Compressor getCompressor() { return new Compressor(); }

    public static Decompressor getDecompressor() { return new Decompressor(); }

    public static class Compressor implements ICompressible {

        private Compressor() {}

        public byte[] compress(byte[] source) {
            return source;
        }
    }

    public static class Decompressor implements IDecompressible {

        private Decompressor() {}

        public byte[] decompress(byte[] source) {
            return source;
        }

    }

}
