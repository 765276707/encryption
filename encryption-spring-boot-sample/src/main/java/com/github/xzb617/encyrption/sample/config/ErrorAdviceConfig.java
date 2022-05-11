package com.github.xzb617.encyrption.sample.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.*;

/**
 * 捕获参数异常
 * @author xzb617
 * @date 2022/4/24 9:51
 * @description:
 */
@RestControllerAdvice
public class ErrorAdviceConfig {

    /**
     * 参数错误
     * @HttpStatus 400
     * @param e
     * @return
     */
    @ExceptionHandler(value={
            ValidationException.class,
            BindException.class,
            ServletRequestBindingException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotWritableException.class
    })
    public ResponseEntity<Map> handlerParametersException(Exception e) {
        // 获取错误消息
        String errorMessage = "非法参数，请求已被拒绝";
        if (e instanceof ValidationException || e instanceof ServletRequestBindingException) {
            errorMessage = e.getMessage();
        }
        else if (e instanceof MethodArgumentNotValidException) {
            errorMessage = getParameterErrorMessage((MethodArgumentNotValidException) e, errorMessage);
        }
        else if (e instanceof BindException) {
            errorMessage = getParameterErrorMessage((BindException) e, errorMessage);
        }
        else if (e instanceof HttpMessageNotReadableException
                || e instanceof MethodArgumentTypeMismatchException) {
//            errorMessage = "参数类型或格式错误";
            errorMessage = e.getMessage();
        }
        else if (e instanceof HttpMessageNotWritableException) {
            e.printStackTrace();
        }
        // 响应
        Map<String, Object> res = new HashMap<>(3);
        res.put("errorCode", HttpStatus.BAD_REQUEST.value());
        res.put("errorMessage", errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(res);
    }


    /**
     * 获取参数错误信息
     * @param e
     * @param defaultMessage
     * @return
     */
    private String getParameterErrorMessage(ConstraintViolationException e, String defaultMessage) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Optional<ConstraintViolation<?>> firstConstraintViolation = constraintViolations.stream().findFirst();
        if (firstConstraintViolation.isPresent()) {
            return firstConstraintViolation.get().getMessage();
        }
        return defaultMessage;
    }

    /**
     * 获取参数错误信息
     * @param e
     * @param defaultMessage
     * @return
     */
    private String getParameterErrorMessage(MethodArgumentNotValidException e, String defaultMessage) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            return allErrors.get(0).getDefaultMessage();
        }

        return defaultMessage;
    }

    /**
     * 获取参数错误信息
     * @param e
     * @param defaultMessage
     * @return
     */
    private String getParameterErrorMessage(BindException e, String defaultMessage) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            return allErrors.get(0).getDefaultMessage();
        }
        return defaultMessage;
    }

}
