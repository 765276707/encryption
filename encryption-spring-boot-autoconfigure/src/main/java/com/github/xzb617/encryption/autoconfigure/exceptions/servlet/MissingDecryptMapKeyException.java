package com.github.xzb617.encryption.autoconfigure.exceptions.servlet;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * 缺失要解密的Map的key
 * @author xzb617
 * @date 2022/5/6 17:06
 * @description:
 */
public class MissingDecryptMapKeyException extends ServletRequestBindingException {

    public MissingDecryptMapKeyException(String msg) {
        super(msg);
    }

    public MissingDecryptMapKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
