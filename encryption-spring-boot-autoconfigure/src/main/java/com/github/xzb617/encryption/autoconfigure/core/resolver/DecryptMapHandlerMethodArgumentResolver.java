package com.github.xzb617.encryption.autoconfigure.core.resolver;

import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptMap;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.servlet.MissingDecryptMapKeyException;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import com.github.xzb617.encryption.autoconfigure.utils.ArrayUtil;
import com.github.xzb617.encryption.autoconfigure.utils.TypeUtil;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于 @DecryptParam 注解 Map类型解密参数
 * @author xzb617
 * @date 2022/5/4 18:11
 * @description:
 */
public class DecryptMapHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final EncryptorProxyManager encryptorProxyManager;

    public DecryptMapHandlerMethodArgumentResolver(EncryptorProxyManager encryptorProxyManager) {
        this.encryptorProxyManager = encryptorProxyManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return methodParameter.hasParameterAnnotation(DecryptMap.class) && TypeUtil.isMap(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 获取 HttpServletRequest
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Assert.state(request != null, "No HttpServletRequest");

        // 获取注解
        DecryptMap anno = methodParameter.getParameterAnnotation(DecryptMap.class);
        String[] keys = anno.keys();
        RequestHeaders requestHeaders = new RequestHeaders(request);

        Class<?> paramType = methodParameter.getParameterType();
        Map<String, String[]> parameterMap = nativeWebRequest.getParameterMap();
        // 校验keys是否必传
        if (anno.required()) {
            validateExistsKeysInParamMap(keys, parameterMap, anno);
        }
        Iterator var8;
        Map.Entry entry;
        if (!MultiValueMap.class.isAssignableFrom(paramType)) {
            Map<String, String> result = new LinkedHashMap(parameterMap.size());
            var8 = parameterMap.entrySet().iterator();

            while(var8.hasNext()) {
                entry = (Map.Entry)var8.next();
                if (((String[])entry.getValue()).length > 0) {
                    String key = (String) entry.getKey();
                    String value = ((String[])entry.getValue())[0];
                    // 解密操作
                    String decryptedValue = this.decryptKeyValue(key, value, requestHeaders, keys, methodParameter);
                    result.put(key, decryptedValue);
                }
            }

            return result;
        } else {
            MultiValueMap<String, String> result = new LinkedMultiValueMap(parameterMap.size());
            var8 = parameterMap.entrySet().iterator();

            while(var8.hasNext()) {
                entry = (Map.Entry)var8.next();
                String[] var10 = (String[])entry.getValue();
                int var11 = var10.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String key = (String) entry.getKey();
                    String value = var10[var12];
                    // 解密操作
                    String decryptedValue = this.decryptKeyValue(key, value, requestHeaders, keys, methodParameter);
                    result.add(key, decryptedValue);
                }
            }

            return result;
        }
    }

    private void validateExistsKeysInParamMap(String[] keys, Map paramMap, DecryptMap anno) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            if (!paramMap.containsKey(key)) {
                builder.append(key).append(",");
            }
        }
        String missingKeys = builder.toString();
        if (!"".equals(missingKeys)) {
            String message = anno.message();
            if ("".equals(message)) {
                missingKeys = missingKeys.substring(0, missingKeys.length() - 1);
                message = String.format("Missing element of param map, keys is [%s]", missingKeys);
            }
            throw new MissingDecryptMapKeyException(message);
        }
    }

    private String decryptKeyValue(String key, String value, RequestHeaders requestHeaders, String[] keys, MethodParameter methodParameter) {
        if (value==null || "".equals(value)) {
            return value;
        }
        if (ArrayUtil.contain(keys, key)) {
            return this.encryptorProxyManager.decrypt(value, requestHeaders, methodParameter, key);
        }
        return value;
    }

}
