package com.github.xzb617.encryption.autoconfigure.envirs;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.constant.Constants;
import com.github.xzb617.encryption.autoconfigure.exceptions.framework.MissingAlgorithmConfigException;
import com.github.xzb617.encryption.autoconfigure.utils.SpellUtil;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

/**
 * 算法配置环境
 * @author xzb617
 * @date 2022/5/5 11:46
 * @description:
 */
public class AlgorithmEnvironments {

    /**
     * 默认的编码类型
     */
    public final static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 可配置项环境
     */
    private final ConfigurableEnvironment configurableEnvironment;

    public AlgorithmEnvironments(ConfigurableEnvironment configurableEnvironment) {
        Assert.notNull(configurableEnvironment, "ConfigurableEnvironment not be applied");
        this.configurableEnvironment = configurableEnvironment;
    }

    /**
     * 获取算法类型
     * @return Algorithm
     */
    public Algorithm getAlgorithm() {
        String algorithmKey = Constants.CONFIG_PROPERTIES_PREFIX + ".algorithm";
        return this.configurableEnvironment.getProperty(algorithmKey, Algorithm.class);
    }

    /**
     * 获取编码类型
     * @return String
     */
    public String getCharset() {
        String charsetKey = Constants.CONFIG_PROPERTIES_PREFIX + ".charset";
        return this.configurableEnvironment.getProperty(charsetKey);
    }

    /**
     * 获取算法的配置
     * @param configKey 键，符合驼峰模式，如：aomStringBean
     * @return String
     */
    public String getAlgorithmConfig(String configKey) {
        String key = Constants.CONFIG_PROPERTIES_PREFIX + ".configs." + SpellUtil.camelToLine(configKey);
        return this.configurableEnvironment.getProperty(key);
    }

    /**
     * 获取算法的配置，未获取到则抛出异常
     * @param configKey 键，符合驼峰模式，如：aomStringBean
     * @throws MissingAlgorithmConfigException 确实必要算法配置异常
     * @return String
     */
    public String getAlgorithmConfigElseThrow(String configKey) {
        String value = this.getAlgorithmConfig(configKey);
        if (value==null || "".equals(value)) {
            throw new MissingAlgorithmConfigException(configKey);
        }
        return value;
    }
}
