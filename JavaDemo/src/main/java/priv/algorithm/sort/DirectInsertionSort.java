package priv.algorithm.sort;

/**
 * 直接插入排序
 * Created by zhangkai on 16-10-31.
 */
public class DirectInsertionSort extends Sort {

    void sort() {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            insertArray(array, i, i + 1);
        }
    }

    private void insertArray(int[] array, int x, int y) {
        for (int i = x; i >= 0; i--) {
            if (array[y] < array[i]) {
                swap(array, y, i);
                y=i;
            }
        }
    }
}
