package com.github.xzb617.encryption.autoconfigure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 类型工具类
 * @author xzb617
 * @date 2022/4/25 11:41
 * @description:
 */
public class TypeUtil {

    /**
     * 是否为基础类型或其包装类
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz==String.class || clazz==Character.class
                || Number.class.isAssignableFrom(clazz) || clazz==Boolean.class;
    }

    /**
     * 是否为时间类型
     * @param clazz
     * @return
     */
    public static boolean isDateTime(Class<?> clazz) {
        return  clazz== Date.class || clazz== LocalDate.class || clazz== LocalDateTime.class;
    }

    /**
     * 是否为枚举类
     * @param clazz
     * @return
     */
    public static boolean isEnum(Class<?> clazz) {
        return clazz.isEnum();
    }

    /**
     * 是否为注解类
     * @param clazz
     * @return
     */
    public static boolean isAnnotation(Class<?> clazz) {
        return clazz.isAnnotation();
    }

    /**
     * 是否为集合类
     * @param clazz
     * @return
     */
    public static boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 判断是否为数组，非基础类型或其包装类
     * @param clazz
     * @return
     */
    public static boolean isArray(Class<?> clazz) {
        return clazz.isArray();
    }

    /**
     * 是否为Map
     * @param clazz
     * @return
     */
    public static boolean isMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    /**
     * 是否为接口类
     * @param clazz
     * @returns
     */
    public static boolean isInterface(Class<?> clazz) {
        return clazz.isInterface();
    }

    /**
     * 是否为Model
     * @param clazz
     * @return
     */
    public static boolean isModel(Class<?> clazz) {
        return !isPrimitive(clazz) && !isMap(clazz) && !isCollection(clazz)
                && !isArray(clazz) && !isDateTime(clazz) && !isEnum(clazz) && !isAnnotation(clazz) && !isInterface(clazz);
    }
}
