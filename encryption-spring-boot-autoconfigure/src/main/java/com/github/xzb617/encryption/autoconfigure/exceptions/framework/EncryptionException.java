package com.github.xzb617.encryption.autoconfigure.exceptions.framework;

/**
 * 消息加密异常基类
 * @author xzb617
 * @date 2022/5/4 18:25
 * @description:
 */
public class EncryptionException extends RuntimeException {

    public EncryptionException() {
    }

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptionException(Throwable cause) {
        super(cause);
    }

    public EncryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
