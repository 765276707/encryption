package com.github.xzb617.encryption.autoconfigure.exceptions.spring;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 参数类型不受解密支持异常
 * @author xzb617
 * @date 2022/5/4 18:24
 * @description:
 */
public class ArgumentTypeNotSupportedException extends BindException {

    public ArgumentTypeNotSupportedException(BindingResult bindingResult, String objectName, String errorMsg) {
        super(bindingResult);
        addError(new ObjectError(objectName, errorMsg));
    }

    public ArgumentTypeNotSupportedException(BindingResult bindingResult, ObjectError objectError) {
        super(bindingResult);
        addError(objectError);
    }

    public ArgumentTypeNotSupportedException(BindingResult bindingResult) {
        super(bindingResult);
    }

}
