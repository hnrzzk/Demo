package priv.algorithm.sort;

/**
 * 堆排序
 * Created by zk on 16-11-9.
 */
public class HeapSort extends Sort {
    void sort() {
        buildHeap(array);
        System.out.println("build heap finish");
        heapSort(array);
    }

    /**
     * 构建堆
     *
     * @param array
     */
    void buildHeap(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int childPosition = i;
            int parentPosition = (childPosition + 1) / 2 - 1;

            while (parentPosition >= 0 && array[childPosition] > array[parentPosition]) {
                swap(array, childPosition, parentPosition);
                childPosition = parentPosition;
                parentPosition = (childPosition + 1) / 2 - 1;
            }
            printHeap(array,i+1);
        }
    }

    void printHeap(int[] heap, int length) {
        System.out.println("============");
        int layer = 1;
        int key = 0;
        while (key < length) {
            String printStr = "";
            for (int i = 0; i < layer; i++) {
                if (key < length) {
                    printStr += heap[key++] + " ";
                } else {
                    break;
                }
            }
            layer*=2;
            System.out.println(printStr);
        }
        System.out.println("============");
    }

    /**
     * 堆排序
     *
     * @param heap
     */
    void heapSort(int[] heap) {
        for (int i = heap.length - 1; i >= 0; i--) {
            swap(array, 0, i);
            adjustHeap(heap, 0, i);
        }
    }

    /**
     * 调整堆
     *
     * @param heap  堆
     * @param start 开始下标
     * @param end   结束下标
     */
    void adjustHeap(int[] heap, int start, int end) {
        int parentPosition = start;
        while (parentPosition < end) {
            int childPosition = (parentPosition + 1) * 2 - 1;
            if (childPosition + 1 <= end && heap[childPosition] < heap[childPosition + 1]) {
                childPosition++;
            }
            if (heap[childPosition] > heap[parentPosition]) {
                swap(heap, childPosition, parentPosition);
                parentPosition = childPosition;
            }
        }
    }
}
