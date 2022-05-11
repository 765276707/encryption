package com.github.xzb617.encryption.autoconfigure.core.resolver;

import com.github.xzb617.encryption.autoconfigure.annotation.param.DecryptHeader;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.servlet.MissingServletDecryptParameterException;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于 @DecryptHeader 注解解密参数
 * @author xzb617
 * @date 2022/5/4 18:10
 * @description:
 */
public class DecryptHeaderHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    private final EncryptorProxyManager encryptorProxyManager;

    public DecryptHeaderHandlerMethodArgumentResolver(EncryptorProxyManager encryptorProxyManager) {
        this.encryptorProxyManager = encryptorProxyManager;
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter methodParameter) {
        DecryptHeader ann = (DecryptHeader) methodParameter.getParameterAnnotation(DecryptHeader.class);
        Assert.state(ann != null, "No DecryptHeader annotation");
        return new DecryptHeaderHandlerMethodArgumentResolver.DecryptHeaderNamedValueInfo(ann);
    }

    @Override
    protected Object resolveName(String name, MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {
        // 获取 HttpServletRequest
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Assert.state(request != null, "No HttpServletRequest");

        String headerValue = nativeWebRequest.getHeader(name);
        String res = null;
        if (!StringUtils.isEmpty(headerValue)) {
            String strBody = headerValue
                    .replaceAll(" ", "+");
            res = this.encryptorProxyManager.decrypt(strBody, new RequestHeaders(request), methodParameter, name);
        }
        return res;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(DecryptHeader.class);
    }

    @Override
    protected void handleMissingValue(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        DecryptHeader ann = parameter.getParameterAnnotation(DecryptHeader.class);
        String errMessage = ann.message();
        if (StringUtils.isEmpty(errMessage)) {
            errMessage = "Missing header '" + name;
        }
        throw new MissingServletDecryptParameterException(errMessage);
    }


    private static class DecryptHeaderNamedValueInfo extends NamedValueInfo {

        private DecryptHeaderNamedValueInfo(DecryptHeader annotation) {
            super(annotation.name(), annotation.required(), annotation.defaultValue());
        }

    }
}
