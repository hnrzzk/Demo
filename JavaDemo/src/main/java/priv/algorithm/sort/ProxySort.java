package priv.algorithm.sort;

import priv.print.Collection;

/**
 * 排序算法打代理
 * Created by zhangkai on 16-10-31.
 */
public class ProxySort extends Sort {

    Sort sort;

    public ProxySort(Sort sort) {
        this.sort = sort;
    }

    void sort() {
        String before = String.format("Before sort:\n %s", Collection.arrayToString(sort.array));
        System.out.println(before);
        long starTime=System.currentTimeMillis();
        sort.sort();
        long endTime=System.currentTimeMillis();
        String after = String.format("After sort:\n %s", Collection.arrayToString(sort.array));
        System.out.println(after);
        System.out.println(String.format("运行时间：%s ms",endTime-starTime));
    }


}
