package com.github.xzb617.encryption.autoconfigure.utils;

import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

/**
 * 消息转换器工具类
 * @author xzb617
 * @date 2022/5/7 10:33
 * @description:
 */
public class HttpMessageConverterUtil {

    /**
     * 根据类型寻找对应的转换器
     * @param converters
     * @param aClass
     * @return
     */
    public static HttpMessageConverter getConverterByType(List<HttpMessageConverter> converters, Class<? extends HttpMessageConverter> aClass) {
        HttpMessageConverter res = null;
        for (HttpMessageConverter converter : converters) {
            if (converter.getClass() == aClass) {
                res = converter;
                break;
            }
        }
        return res;
    }

}
