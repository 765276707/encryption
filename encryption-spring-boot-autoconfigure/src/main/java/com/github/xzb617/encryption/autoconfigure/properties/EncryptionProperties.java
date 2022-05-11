package com.github.xzb617.encryption.autoconfigure.properties;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.constant.Constants;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmConfigs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 可配置选项
 * @author xzb617
 * @date 2022/5/4 16:27
 * @description: 基于Map形式的配置，赋予了框架更大的拓展空间
 */
@ConfigurationProperties(prefix = Constants.CONFIG_PROPERTIES_PREFIX)
public class EncryptionProperties {

    /**
     * 算法类型
     */
    private Algorithm algorithm = Algorithm.AES;

    /**
     * 配置文件字符集类型，默认：UTF-8
     */
    private String charset = "UTF-8";

    /**
     * 每个算法各自所需的配置，key-value形式
     */
    private AlgorithmConfigs configs = new AlgorithmConfigs();

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public AlgorithmConfigs getConfigs() {
        return configs;
    }

    public void setConfigs(AlgorithmConfigs configs) {
        this.configs = configs;
    }
}
