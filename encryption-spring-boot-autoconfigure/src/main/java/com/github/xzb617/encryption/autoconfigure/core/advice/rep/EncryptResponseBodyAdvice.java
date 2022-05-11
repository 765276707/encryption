package com.github.xzb617.encryption.autoconfigure.core.advice.rep;

import com.github.xzb617.encryption.autoconfigure.annotation.body.EncryptBody;
import com.github.xzb617.encryption.autoconfigure.annotation.body.EncryptBodyModel;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.spring.HttpMessageEncryptException;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import com.github.xzb617.encryption.autoconfigure.utils.ReflectUtil;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 加密响应增强
 * @author xzb617
 * @date 2022/5/4 18:13
 * @description:
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    protected final EncryptorProxyManager encryptorProxyManager;
    protected final EncryptionJsonSerializer jsonSerializer;

    public EncryptResponseBodyAdvice(EncryptorProxyManager encryptorProxyManager, EncryptionJsonSerializer jsonSerializer) {
        this.encryptorProxyManager = encryptorProxyManager;
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(EncryptBody.class);
    }

    /**
     * 要加密的字段优先级：EncryptBody.encryptFields() > EncryptBodyModel.encryptFields()
     * @param originData
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object originData, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (originData == null) {
            return originData;
        }
        try {
            String retModelName = originData.getClass().getName();
            // 判断是否只加密某个字段
            Method method = methodParameter.getMethod();
            EncryptBody anno = AnnotationUtils.getAnnotation(method, EncryptBody.class);
            String[] fields = anno.encryptFields();
            if (fields.length > 0) {
                for (String fieldName : fields) {
                    Object fieldValue = ReflectUtil.getFieldValue(fieldName, originData);
                    String encryptedFieldValue = this.encrypt(fieldValue, retModelName, fieldName, serverHttpRequest, serverHttpResponse);
                    ReflectUtil.setFieldValue(fieldName, originData, encryptedFieldValue);
                }
                return originData;
            }
            else {
                // 判断响应类是否有 @EncryptBodyModel 注解
                EncryptBodyModel annotation = originData.getClass().getAnnotation(EncryptBodyModel.class);
                if (annotation != null) {
                    String[] fieldsInModel = annotation.encryptFields();
                    if (fieldsInModel.length > 0) {
                        for (String fieldName : fieldsInModel) {
                            Object fieldValue = ReflectUtil.getFieldValue(fieldName, originData);
                            String encryptedFieldValue = this.encrypt(fieldValue, retModelName, fieldName, serverHttpRequest, serverHttpResponse);
                            ReflectUtil.setFieldValue(fieldName, originData, encryptedFieldValue);
                        }
                        return originData;
                    }
                }
                return this.encrypt(originData, retModelName, "<ALL>", serverHttpRequest, serverHttpResponse);
            }
        }
        catch (Exception e) {
            throw new HttpMessageEncryptException(e.getMessage(), e);
        }
    }


    /**
     * 加密数据
     * @param data 要加密的数据
     * @param response
     * @return
     * @throws IOException
     */
    protected String encrypt(Object data, String retModelName, String retModelFieldName, ServerHttpRequest request, ServerHttpResponse response) throws Exception {
        // 获取需要加密的数据，并序列化成字符串
        if (data == null) {
            return null;
        }
        if ("".equals(data)) {
            return "";
        }
        String strNeedEncData = this.jsonSerializer.serialize(data);
        // 加密数据
        return this.encryptorProxyManager.encrypt(strNeedEncData,  new ResponseHeaders(response), retModelName, retModelFieldName);
    }

}
