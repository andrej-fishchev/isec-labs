public class LZW {

    public final static String ALGO = "LZW";

    public static final String ALGO_FILE_EXTENSION = ".lzw";

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