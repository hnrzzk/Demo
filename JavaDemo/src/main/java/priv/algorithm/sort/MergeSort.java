package priv.algorithm.sort;

/**
 * Created by zk on 16-11-14.
 */
public class MergeSort extends Sort {
    void sort() {
        mergeSort(array, 0, array.length - 1);
    }

    void mergeSort(int[] array, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            mergeArray(array, start, mid, end);
        }
    }

    void mergeArray(int[] a, int start, int mid, int end) {
        int[] result = new int[end-start+1];
        int x = start, y = mid+1, z = 0;
        while (x <= mid && y <= end) {
            if (a[x] < a[y]) {
                result[z++] = a[x++];
            } else {
                result[z++] = a[y++];
            }
        }

        while (x <=mid) {
            result[z++] = a[x++];
        }
        while (y <=end) {
            result[z++] = a[y++];
        }

        z=0;
        for (int i=start;i<=end;i++){
            array[i]=result[z++];
        }
    }
}
