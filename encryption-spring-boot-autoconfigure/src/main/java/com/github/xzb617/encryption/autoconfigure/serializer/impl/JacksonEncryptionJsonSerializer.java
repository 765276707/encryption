package com.github.xzb617.encryption.autoconfigure.serializer.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * 基于 Jackson 的序列化器
 * @author xzb617
 * @date 2022/5/4 18:28
 * @description: SpringBoot2.x 默认依赖的Json解析框架
 */
public class JacksonEncryptionJsonSerializer implements EncryptionJsonSerializer {

    private ObjectMapper objectMapper;

    public JacksonEncryptionJsonSerializer(MappingJackson2HttpMessageConverter converter) {
        Assert.notNull(converter, "MappingJackson2HttpMessageConverter unable to inject.");
        this.objectMapper = converter.getObjectMapper();
    }

    @Override
    public String serialize(Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException var) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var.getMessage(), var);
        }
    }

    @Override
    public <T> T deserialize(String jsonStr, Class<T> type) {
        try {
            return this.objectMapper.readValue(jsonStr, type);
        } catch (JsonParseException | JsonMappingException var1) {
            throw new HttpMessageNotReadableException("JSON parse error: " + var1.getMessage(), var1);
        } catch (IOException var2) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", var2);
        }
    }

}
