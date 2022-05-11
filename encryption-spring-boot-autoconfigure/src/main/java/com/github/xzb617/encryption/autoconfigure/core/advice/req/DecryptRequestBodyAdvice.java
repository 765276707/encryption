package com.github.xzb617.encryption.autoconfigure.core.advice.req;

import com.github.xzb617.encryption.autoconfigure.annotation.body.DecryptBody;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.spring.HttpMessageDecryptException;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import com.github.xzb617.encryption.autoconfigure.utils.IOUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 解密请求增强
 * @author xzb617
 * @date 2022/5/4 18:13
 * @description:
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    protected final EncryptorProxyManager encryptorProxyManager;

    public DecryptRequestBodyAdvice(EncryptorProxyManager encryptorProxyManager) {
        this.encryptorProxyManager = encryptorProxyManager;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        // 当且仅当有 DecryptBody 注解时才解密请求体
        return methodParameter.hasParameterAnnotation(DecryptBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        DecryptBody annotation = methodParameter.getParameterAnnotation(DecryptBody.class);
        if (annotation != null) {
            return new DecryptHttpInputMessage(httpInputMessage, methodParameter);
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    /**
     * 实现并重写HttpInputMessage
     * @author xzb
     */
    class DecryptHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;
        MethodParameter method;

        DecryptHttpInputMessage(HttpInputMessage httpInputMessage, MethodParameter method) throws IOException {
            this.headers = httpInputMessage.getHeaders();
            this.body = httpInputMessage.getBody();
            this.method = method;
        }

        @Override
        public InputStream getBody() throws IOException {
            // 为 null 时直接返回 null
            if (this.body == null) {
                return null;
            }

            // 输入流不为空的时候
            String strBody = IOUtil.toString(this.body, StandardCharsets.UTF_8)
                    .replaceAll("%2B", "+")
                    .replaceAll("%3D", "=");

            // 解析参数
            String decryptedStrBody = this.decrypt(strBody);

            // 返回解密后的数据流
            return IOUtil.toInputStream(decryptedStrBody, "UTF-8");
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        private String decrypt(String needDecryptStrBody) {
            // 解析参数
            String decryptStrBody = "";
            String parameterName = "";
            try
            {
                decryptStrBody = encryptorProxyManager.decrypt(needDecryptStrBody, new RequestHeaders(headers), method, method.getParameterName());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new HttpMessageDecryptException(
                        String.format("decrypted request body failure. cause by %s", e.getMessage()), e.getCause());
            }
            return decryptStrBody;
        }
    }
}
