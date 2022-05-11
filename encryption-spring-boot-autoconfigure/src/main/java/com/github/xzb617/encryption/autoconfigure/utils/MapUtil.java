package com.github.xzb617.encryption.autoconfigure.utils;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Map工具类
 * @author xzb617
 * @date 2022/5/4 18:19
 * @description:
 */
public class MapUtil {

    /**
     * 拷贝一份 Map
     * @param source 被拷贝的Map
     * @return
     */
    public static Map<String, String[]> copy(Map<String, String[]> source) {
        Assert.notNull(source, "source map must not be null.");
        Map<String, String[]> target = new HashMap<>(source.size());
        source.forEach(target::put);
        return target;
    }

}
