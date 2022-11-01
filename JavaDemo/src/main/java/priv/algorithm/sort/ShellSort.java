package priv.algorithm.sort;

/**
 * 希尔排序
 * Created by zhangkai on 16-11-2.
 */
public class ShellSort extends Sort {

    void sort() {
        int length = array.length;
        int step = length / 2;
        while (step > 0) {
            for (int i = 0; i < step; i++) {
                for (int j = i + step; j < length; j += step) {
                    for (int k = j; k-step >= 0; k -= step) {
                        if (array[k] < array[k - step]) {
                            swap(array, k, k - step);
                        }
                    }
                }
            }
            step /= 2;
        }
    }
}
