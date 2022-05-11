package com.github.xzb617.encryption.autoconfigure.utils;

/**
 * 数组工具类
 * @author xzb617
 * @date 2022/5/4 18:18
 * @description:
 */
public class ArrayUtil {

    /**
     * 数组是否包含某个元素
     * @param arr
     * @param var
     * @return
     */
    public static boolean contain(String[] arr, String var) {
        boolean result = false;
        for (String v : arr) {
            if (v!=null && v.equals(var)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 数组是否包含某个元素
     * @param arr
     * @param var
     * @return
     */
    public static boolean contain(Object[] arr, Object var) {
        boolean result = false;
        for (Object v : arr) {
            if (v!=null && v.equals(var)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
