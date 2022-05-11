package com.github.xzb617.encryption.autoconfigure.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 * @author xzb617
 * @date 2022/5/5 13:25
 * @description:
 */
public class CollectionUtil {

    /**
     * 字符串数组转字符串Set集合
     * @param arr 数组
     * @return
     */
    public static Set<String> toSet(String[] arr) {
        Set<String> sets = new HashSet<>(arr.length);
        for (String s : arr) {
            sets.add(s);
        }
        return sets;
    }

    /**
     * 字符串数组转字符串List集合
     * @param arr 数组
     * @return
     */
    public static List<String> toList(String[] arr) {
        List<String> list = new ArrayList<>(arr.length);
        for (String s : arr) {
            list.add(s);
        }
        return list;
    }
}
