package priv.algorithm.sort;

/**
 * 简单选择排序
 * Created by zhangkai on 16-11-1.
 */
public class SimpleSelectionSort extends Sort {
    void sort() {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            int minKey = i;
            for (int j = i + 1; j < length; j++) {
                if (array[j] < array[minKey]) {
                    minKey = j;
                }
            }
            swap(array, i, minKey);
        }
    }
}
