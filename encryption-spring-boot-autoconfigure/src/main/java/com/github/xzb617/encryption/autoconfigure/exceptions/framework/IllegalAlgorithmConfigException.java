package com.github.xzb617.encryption.autoconfigure.exceptions.framework;

/**
 * 不合法的算法配置异常
 * @author xzb617
 * @date 2022/5/5 12:31
 * @description:
 */
public class IllegalAlgorithmConfigException extends AlgorithmConfigException {

    public IllegalAlgorithmConfigException(String message) {
        super(message);
    }

    public IllegalAlgorithmConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
