package com.github.xzb617.encryption.autoconfigure.exceptions.framework;

/**
 * 参数配置异常
 * @author xzb617
 * @date 2022/5/6 15:38
 * @description:
 */
public class AlgorithmConfigException extends EncryptionException {

    public AlgorithmConfigException() {
    }

    public AlgorithmConfigException(String message) {
        super(message);
    }

    public AlgorithmConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
