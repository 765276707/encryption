package com.github.xzb617.encryption.autoconfigure.mock;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.impl.ConfigurableAlgorithmEnvironments;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

/**
 * Encryption的Mock支持
 * @author xzb617
 * @date 2022/5/13 10:37
 * @description: 提供Mock功能，让使用者可以快速Mock出所需要的模拟数据
 */
public class MockEncryption {

    private final ArgumentEncryptor argumentEncryptor;
    private final ConfigurableAlgorithmEnvironments configurableAlgorithmEnvironments;
    private final ResponseHeaders responseHeaders;

    public MockEncryption(ArgumentEncryptor argumentEncryptor, ConfigurableEnvironment configurableEnvironment) {
        this.argumentEncryptor = argumentEncryptor;
        this.configurableAlgorithmEnvironments = new ConfigurableAlgorithmEnvironments(configurableEnvironment);
        this.responseHeaders = new ResponseHeaders(new HttpHeaders());
        this.argumentEncryptor.initConfig(this.configurableAlgorithmEnvironments);
    }

    public static MockEncryption configurableEnvironmentContextSetup(ArgumentEncryptor argumentEncryptor, ConfigurableEnvironment configurableEnvironment) {
        return new MockEncryption(argumentEncryptor, configurableEnvironment);
    }


    public String getSecretKeyInHeader() {
        return this.configurableAlgorithmEnvironments.getAlgorithmConfigElseThrow(SymmetricConfigKey.SECRET_KEY_IN_HEADER);
    }

    public Algorithm getAlgorithm() {
        return this.configurableAlgorithmEnvironments.getAlgorithm();
    }

    /**
     * mock一个请求头的secret，获取加密后的值
     * @param mockSecretInHeader 混合模式下，需要在请求头中放入的模拟对称算法的密钥
     * @return
     */
    public String encryptedHeaderSecret(String mockSecretInHeader) {
        String secretKeyInHeader = this.getSecretKeyInHeader();
        this.responseHeaders.addHeader(MockConstant.MOCK_SECRET_KEY, mockSecretInHeader);
        this.argumentEncryptor.encrypt("MOCK_VALUE_RANDOM_"+ UUID.randomUUID().toString(), responseHeaders);
        String encryptedSecretValue = this.responseHeaders.getHeader(secretKeyInHeader);
        this.responseHeaders.removeHeader(MockConstant.MOCK_SECRET_KEY);
        return encryptedSecretValue;
    }

    /**
     * 请求头带模拟密钥的加密
     * @param var
     * @param mockSecretInHeader
     * @return
     */
    public String encryptValue(String var, String mockSecretInHeader) {
        this.responseHeaders.addHeader(MockConstant.MOCK_SECRET_KEY, mockSecretInHeader);
        String result = this.argumentEncryptor.encrypt(var, responseHeaders);
        this.responseHeaders.removeHeader(MockConstant.MOCK_SECRET_KEY);
        return result;
    }

    /**
     * 请求头不带模拟密钥的加密
     * @param var
     * @return
     */
    public String encryptValue(String var) {
        return this.argumentEncryptor.encrypt(var, responseHeaders);
    }
}
