package com.github.xzb617.encryption.autoconfigure.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonContainer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基于 Fastjson 的序列化器
 * @author xzb617
 * @date 2022/5/4 18:39
 * @description:
 */
public class FastjsonEncryptionJsonSerializer implements EncryptionJsonSerializer {

    private FastJsonConfig fastJsonConfig;

    public FastjsonEncryptionJsonSerializer(FastJsonHttpMessageConverter converter) {
        Assert.notNull(converter, "FastJsonHttpMessageConverter unable to inject.");
        this.fastJsonConfig = converter.getFastJsonConfig();
    }

    @Override
    public String serialize(Object obj) {
        // 过滤器
        SerializeFilter[] globalFilters = this.fastJsonConfig.getSerializeFilters();
        List<SerializeFilter> allFilters = new ArrayList(Arrays.asList(globalFilters));
        Object value = this.strangeCodeForJackson(obj);
        if (value instanceof FastJsonContainer) {
            FastJsonContainer fastJsonContainer = (FastJsonContainer)value;
            PropertyPreFilters filters = fastJsonContainer.getFilters();
            allFilters.addAll(filters.getFilters());
        }
        try {
            String res = JSON.toJSONString(obj, this.fastJsonConfig.getSerializeConfig(), (SerializeFilter[])allFilters.toArray(new SerializeFilter[allFilters.size()]),
                    this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
            return res;
        } catch (JSONException var) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var.getMessage(), var);
        }
    }

    @Override
    public <T> T deserialize(String jsonStr, Class<T> type) {
        try {
            return JSON.parseObject(jsonStr, type, this.fastJsonConfig.getParserConfig(),
                    this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
        } catch (JSONException var1) {
            throw new HttpMessageNotReadableException("JSON parse error: " + var1.getMessage(), var1);
        }
    }

    /**
     * 对 Jackson 的部分属性进行兼容
     * @param obj
     * @return
     */
    private Object strangeCodeForJackson(Object obj) {
        if (obj != null) {
            String className = obj.getClass().getName();
            if ("com.fasterxml.jackson.databind.node.ObjectNode".equals(className)) {
                return obj.toString();
            }
        }
        return obj;
    }

}
