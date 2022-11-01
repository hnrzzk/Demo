package priv.collection;

import priv.math.GetRandom;

/**
 * 获取数组
 * Created by zhangkai on 16-10-30.
 */
public class GetArray {
    /**
     * 得到int型数组
     * @param random    是否是随机数
     * @param length    数组长度
     * @return  生产的数组
     */
    public static int[] getIntArray(boolean random, int length) {
        int[] array=new int[length];
        if (random){
            for (int i=0;i<length;i++){
                array[i]= GetRandom.getInt(1,length);
            }
        }else {
            for (int i=0;i<length;i++){
                array[i]=i+1;
            }
        }
        return array;
    }
}
