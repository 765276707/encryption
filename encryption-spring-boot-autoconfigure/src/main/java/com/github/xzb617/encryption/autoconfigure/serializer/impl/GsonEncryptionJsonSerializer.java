package com.github.xzb617.encryption.autoconfigure.serializer.impl;

import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 基于 GSON 的序列化器
 * @author xzb617
 * @date 2022/5/4 18:39
 * @description:
 */
public class GsonEncryptionJsonSerializer implements EncryptionJsonSerializer {

    private Gson gson;

    public GsonEncryptionJsonSerializer(GsonHttpMessageConverter converter) {
        Assert.notNull(converter, "GsonHttpMessageConverter unable to inject.");
        this.gson = converter.getGson();
    }

    @Override
    public String serialize(Object obj) {
        try {
            return this.gson.toJson(obj);
        } catch (JsonIOException var) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var.getMessage(), var);
        }
    }

    @Override
    public <T> T deserialize(String jsonStr, Class<T> type) {
        try {
            return this.gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException var1) {
            throw new HttpMessageNotReadableException("JSON parse error: " + var1.getMessage(), var1);
        } catch (JsonIOException var2) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", var2);
        }
    }

}
