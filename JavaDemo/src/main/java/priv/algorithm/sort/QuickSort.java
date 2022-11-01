package priv.algorithm.sort;

/**
 * 开速排序
 * Created by zk on 16-11-9.
 */
public class QuickSort extends Sort {

    void sort() {
        quickSort(array, 0, array.length - 1);
    }

    int getKeyPosition(int[] array, int start, int end) {
        int keyPosition = start;
        while (start < end) {

            while (array[end] >= array[keyPosition] && start < end) {
                end--;
            }
            swap(array, keyPosition, end);
            keyPosition=end;

            while (array[start] <= array[keyPosition] && start < end) {
                start++;
            }
            swap(array, keyPosition, start);
            keyPosition=start;
        }
        return keyPosition;
    }

    void quickSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int keyPosition = getKeyPosition(array, start, end);
        quickSort(array, start, keyPosition - 1);
        quickSort(array, keyPosition + 1, end);
    }
}
