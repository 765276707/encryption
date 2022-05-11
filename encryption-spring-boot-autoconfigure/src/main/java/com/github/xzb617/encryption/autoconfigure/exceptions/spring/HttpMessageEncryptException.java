package com.github.xzb617.encryption.autoconfigure.exceptions.spring;

import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * 在 converter 中消息加密失败异常
 * @author xzb617
 * @date 2022/5/6 13:45
 * @description:
 */
public class HttpMessageEncryptException extends HttpMessageNotWritableException {

    public HttpMessageEncryptException(String msg) {
        super(msg);
    }

    public HttpMessageEncryptException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
