package com.github.xzb617.encryption.autoconfigure.exceptions.servlet;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * 缺失要解密的参数异常
 * @author xzb617
 * @date 2022/5/5 16:31
 * @description:
 */
public class MissingServletDecryptParameterException extends ServletRequestBindingException {

    public MissingServletDecryptParameterException(String msg) {
        super(msg);
    }

    public MissingServletDecryptParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
