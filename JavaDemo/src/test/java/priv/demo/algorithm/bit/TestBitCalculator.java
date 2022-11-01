package priv.demo.algorithm.bit;

import org.junit.Test;
import priv.algorithm.bit.BitCalculator;
import priv.exception.ArgumentOutOfRangeException;

public class TestBitCalculator {
    @Test
    public void testLowBit() throws ArgumentOutOfRangeException {
        testLowBitOnce(0, 0, 22);
        testLowBitOnce(0, 2, 22);
        testLowBitOnce(0, Integer.MAX_VALUE, 32);
        testLowBitOnce(1, 2, 30);
        testLowBitOnce(Integer.MAX_VALUE, 50, 32);
        testLowBitOnce(Integer.MAX_VALUE, Integer.MAX_VALUE, 32);
    }

    private void testLowBitOnce(long high, long low, int lowBit) throws ArgumentOutOfRangeException {
        long src = genLong(high, low, lowBit);
        assert BitCalculator.getLowBit(src, lowBit) == low;
    }

    private long genLong(long high, long low, int lowBit) {
        long result = high << lowBit;
        result |= low;
        return result;
    }
}
