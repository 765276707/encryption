package com.github.xzb617.encryption.autoconfigure.exceptions.framework;

/**
 * 确实必要的算法配置异常
 * @author xzb617
 * @date 2022/5/5 12:29
 * @description:
 */
public class MissingAlgorithmConfigException extends AlgorithmConfigException {

    public MissingAlgorithmConfigException(String configKey) {
        super(String.format("Missing Algorithm config key [ %s ]", configKey));
    }
}
