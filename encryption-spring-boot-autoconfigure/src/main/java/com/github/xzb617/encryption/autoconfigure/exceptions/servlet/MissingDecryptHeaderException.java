package com.github.xzb617.encryption.autoconfigure.exceptions.servlet;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * 缺失要解密的请求头异常
 * @author xzb617
 * @date 2022/5/5 16:33
 * @description:
 */
public class MissingDecryptHeaderException extends ServletRequestBindingException {

    public MissingDecryptHeaderException(String msg) {
        super(msg);
    }

    public MissingDecryptHeaderException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
