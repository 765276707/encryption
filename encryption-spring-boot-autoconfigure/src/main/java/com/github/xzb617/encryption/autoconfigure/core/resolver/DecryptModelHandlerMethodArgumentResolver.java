package com.github.xzb617.encryption.autoconfigure.core.resolver;

import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptModel;
import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptModelField;
import com.github.xzb617.encryption.autoconfigure.envirs.ParamTypeValidator;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.spring.ArgumentTypeNotSupportedException;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import com.github.xzb617.encryption.autoconfigure.utils.MapUtil;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 针对 @DecryptModel 注解参数解密
 * @author xzb617
 * @date 2022/5/4 18:11
 * @description:
 */
public class DecryptModelHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final EncryptorProxyManager encryptorProxyManager;

    public DecryptModelHandlerMethodArgumentResolver(EncryptorProxyManager encryptorProxyManager) {
        this.encryptorProxyManager = encryptorProxyManager;
    }


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(DecryptModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 获取 HttpServletRequest
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Assert.state(request != null, "No HttpServletRequest");

        // 获取参数类型和参数名
        String parameterName = methodParameter.getParameterName();
        Class<?> parameterType = methodParameter.getParameterType();
        Object instance = parameterType.newInstance();

        WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, instance, parameterName);
        if (binder.getTarget() != null) {
            // 绑定参数
            if (!modelAndViewContainer.isBindingDisabled(parameterName)) {
                Set<String> decryptFields = this.obtainAllDecryptFields(parameterType, parameterName, binder.getBindingResult());
                bindRequestParameters(binder, request, methodParameter, new RequestHeaders(request), decryptFields, parameterName);
            }


            // 参数 JSR303 校验
            validateIfApplicable(binder, methodParameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, methodParameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }

        // 新增绑定结果模型
        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        modelAndViewContainer.removeAttributes(bindingResultModel);
        modelAndViewContainer.addAllAttributes(bindingResultModel);

        return instance;
    }



    private void bindRequestParameters(WebDataBinder binder, ServletRequest request,
                                       MethodParameter methodParameter, RequestHeaders requestHeaders, Set<String> decryptFields, String parameterName) throws BindException {
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> newParameterMap = MapUtil.copy(parameterMap);
        // 在绑定参数前进行解密
        for (String fieldName : decryptFields) {
            String[] values = newParameterMap.get(fieldName);
            if (values != null) {
                if (values.length == 1) {
                    String value = values[0].replaceAll(" ", "+");
                    String decryptValue = this.encryptorProxyManager.decrypt(value, requestHeaders, methodParameter, fieldName);
                    newParameterMap.replace(fieldName, new String[]{decryptValue});
                }
                else {
                    throw new ArgumentTypeNotSupportedException(binder.getBindingResult(),
                            parameterName, String.format("Parameter type of [%s.%s] is not supported to decrypted.", parameterName, fieldName));
                }
            }
        }
        // 绑定参数
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues(newParameterMap);
        servletBinder.bind(mutablePropertyValues);
    }


    private Set<String> obtainAllDecryptFields(Class<?> parameterType, String parameterName, BindingResult bindingResult) throws ArgumentTypeNotSupportedException {
        Set<String> decryptFields = new HashSet<>();
        Field[] fields = parameterType.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (isDecryptField(field, parameterName, bindingResult)) {
                decryptFields.add(field.getName());
            }
        }
        return decryptFields;
    }


    private boolean isDecryptField(Field field, String parameterName, BindingResult bindingResult) throws ArgumentTypeNotSupportedException {
        // 判断是否有 DecryptField 注解
        DecryptModelField anno = field.getAnnotation(DecryptModelField.class);
        if (anno == null) {
            return false;
        }
        // 判断参数类型是否受支持
        Class<?> type = field.getType();
        if (!ParamTypeValidator.support(type)) {
            throw new ArgumentTypeNotSupportedException(bindingResult, field.getName(),
                    String.format("Argument type '%s' of field [%s.%s] is not supported to decrypt.",
                            type.getTypeName(), parameterName, field.getName()));
        }

        return field.getAnnotation(DecryptModelField.class) != null;
    }


    private void validateIfApplicable(WebDataBinder binder, MethodParameter methodParameter) {
        Annotation[] annotations = methodParameter.getParameterAnnotations();
        for (Annotation ann : annotations) {
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
                Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                binder.validate(validationHints);
                break;
            }
        }
    }


    private boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter methodParam) {
        int i = methodParam.getParameterIndex();
        Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }

}
