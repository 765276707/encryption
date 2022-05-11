package com.github.xzb617.encryption.autoconfigure.exceptions.spring;

import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * 在Http Message中解密异常
 * @author xzb617
 * @date 2022/5/6 13:46
 * @description:
 */
public class HttpMessageDecryptException extends HttpMessageNotReadableException {

    public HttpMessageDecryptException(String msg) {
        super(msg);
    }

    public HttpMessageDecryptException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
