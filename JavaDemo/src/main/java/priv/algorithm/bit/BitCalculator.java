package priv.algorithm.bit;

public class BitCalculator {
    public static long getLowBit(long src, int bitCount) {
        return src & ((1L << bitCount) -1);
    }
}
