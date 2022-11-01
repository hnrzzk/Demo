package priv.demo.algorithm;

import priv.algorithm.sort.HeapSort;
import priv.algorithm.sort.ProxySort;
import priv.algorithm.sort.Sort;
import org.junit.Test;
import priv.collection.GetArray;

/**
 * 冒泡排序单元测试
 * Created by zhangkai on 16-10-29.
 */
public class TestSort {

    @Test
    public void test() {
        Sort sortTest = getSortClass(10);

        run(sortTest);
    }

    Sort getSortClass(int arrayLength) {
        int[] array = GetArray.getIntArray(true, arrayLength);
        Sort sortTest = new HeapSort();
        sortTest.setArray(array);
        return sortTest;
    }

    void run(Sort sort) {
        ProxySort proxySort = new ProxySort(sort);
        proxySort.run();
    }
}
