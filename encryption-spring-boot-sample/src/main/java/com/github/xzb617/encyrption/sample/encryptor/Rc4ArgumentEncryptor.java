package com.github.xzb617.encyrption.sample.encryptor;

import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encyrption.sample.utils.Rc4Util;

/**
 * 基于RC4算法的加密器
 * @author xzb617
 * @date 2022/5/7 14:47
 * @description:
 */
public class Rc4ArgumentEncryptor implements ArgumentEncryptor {

    /**
     * 自己配置的密钥
     */
    private String secret;

    @Override
    public String encrypt(String plainText, ResponseHeaders responseHeaders) {
        String res = Rc4Util.encryptToString(plainText, this.secret);
        return res;
    }

    @Override
    public String decrypt(String cipherText, RequestHeaders requestHeaders) {
        // 如果首尾有双引号，则去除
        if (cipherText.startsWith("\"") && cipherText.endsWith("\"")) {
            cipherText = cipherText.substring(1, cipherText.length() - 1);
        }
        String res = Rc4Util.decrypt(cipherText, this.secret);
        return res;
    }

    @Override
    public void initConfig(AlgorithmEnvironments environments) {
        this.secret = environments.getAlgorithmConfigElseThrow("secret");
    }

}
