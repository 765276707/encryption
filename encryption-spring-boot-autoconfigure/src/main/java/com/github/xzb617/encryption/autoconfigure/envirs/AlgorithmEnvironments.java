package com.github.xzb617.encryption.autoconfigure.envirs;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;

/**
 * @author xzb617
 * @date 2022/5/13 11:16
 * @description:
 */
public interface AlgorithmEnvironments {

    public Algorithm getAlgorithm();

    public String getCharset();

    public String getAlgorithmConfig(String configKey);

    public String getAlgorithmConfigElseThrow(String configKey);

}
