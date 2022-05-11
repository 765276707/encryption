package com.github.xzb617.encryption.autoconfigure.core.resolver;

import com.github.xzb617.encryption.autoconfigure.annotation.param.DecryptParam;
import com.github.xzb617.encryption.autoconfigure.envirs.ParamTypeValidator;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.servlet.MissingServletDecryptParameterException;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于 @DecryptParam 注解解密参数
 * @author xzb617
 * @date 2022/5/4 18:10
 * @description:
 */
public class DecryptParamHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    private final EncryptorProxyManager encryptorProxyManager;

    public DecryptParamHandlerMethodArgumentResolver(EncryptorProxyManager encryptorProxyManager) {
        this.encryptorProxyManager = encryptorProxyManager;
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter methodParameter) {
        DecryptParam ann = (DecryptParam) methodParameter.getParameterAnnotation(DecryptParam.class);
        Assert.state(ann != null, "No DecryptHeader annotation");
        return new DecryptParamHandlerMethodArgumentResolver.DecryptParamNamedValueInfo(ann);
    }

    @Override
    protected Object resolveName(String name, MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {
        // 获取 HttpServletRequest
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Assert.state(request != null, "No HttpServletRequest");

        // 获取参数上的 DecryptParam 注解
        DecryptParam ann = methodParameter.getParameterAnnotation(DecryptParam.class);
        Assert.state(ann != null, "No DecryptParam annotation");

        String res = null;
        Class<?> parameterType = methodParameter.getParameterType();
        String parameterName = methodParameter.getParameterName();

        // 判断参数类型是否受支持
        if (!ParamTypeValidator.support(parameterType)) {
            throw new HttpMessageNotReadableException(
                    String.format("Parameter type '%s' of property [%s] is not supported to decrypt.", parameterType.getTypeName(), parameterName));
        }

        if (!StringUtils.isEmpty(parameterName)) {
            String parameterValue = nativeWebRequest.getParameter(parameterName);
            // 判断是否未必传参数，为传递则抛出异常
            if (parameterValue == null) {
                return null;
            } else {
                String strBody = parameterValue
                        .replaceAll(" ", "+");
                res = this.encryptorProxyManager.decrypt(strBody, new RequestHeaders(request), methodParameter, name);
            }
        }

        // 解析参数类型
        return res;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(DecryptParam.class);
    }

    /**
     * 处理空值
     * @param name
     * @param parameter
     * @param request
     * @throws Exception 抛出的异常均为 ServletRequestBindingException 的子类异常
     */
    @Override
    protected void handleMissingValue(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        DecryptParam ann = parameter.getParameterAnnotation(DecryptParam.class);
        String errMessage = ann.message();
        if (StringUtils.isEmpty(errMessage)) {
            errMessage = "Missing argument '" + name + "' for method parameter of type " + parameter.getNestedParameterType().getSimpleName();
        }
        throw new MissingServletDecryptParameterException(errMessage);
    }

    private static class DecryptParamNamedValueInfo extends NamedValueInfo {

        private DecryptParamNamedValueInfo(DecryptParam annotation) {
            super(annotation.name(), annotation.required(), annotation.defaultValue());
        }

    }

}
