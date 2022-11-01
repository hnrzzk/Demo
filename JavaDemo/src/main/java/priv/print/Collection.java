package priv.print;

/**
 * 将数据输出到控制台
 * Created by zhangkai on 16-10-30.
 */
public class Collection {
    /**
     * 输出数组
     * @param array
     */
    public static void printIntArray(int[] array) {
        System.out.println(arrayToString(array));
    }

    /**
     * 将数组
     * @param array
     * @return
     */
    public static String arrayToString(int[] array) {
        String result = "";
        for (int item : array) {
            result += item + " ";
        }
        return result;
    }
}
