package com.github.xzb617.encryption.autoconfigure.exceptions.framework;

/**
 * 实例化 Bean 异常
 * @author xzb617
 * @date 2022/5/5 15:03
 * @description:
 */
public class InstanceBeanException extends EncryptionException {

    public InstanceBeanException(String message) {
        super(message);
    }

    public InstanceBeanException(String message, Throwable cause) {
        super(message, cause);
    }

}
