package priv.algorithm.sort;

/**
 * 冒泡排序
 * Created by zhangkai on 16-10-29.
 */
public class BubbleSort extends Sort {
    void sort() {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = length - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                }
            }
        }
    }
}
