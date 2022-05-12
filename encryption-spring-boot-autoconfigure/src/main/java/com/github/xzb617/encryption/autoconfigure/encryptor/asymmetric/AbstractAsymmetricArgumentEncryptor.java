package com.github.xzb617.encryption.autoconfigure.encryptor.asymmetric;

import com.github.xzb617.encryption.autoconfigure.constant.AsymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.encryptor.AbstractArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;


/**
 * 抽象的非对称算法加密器
 * @author xzb617
 * @date 2022/5/6 16:01
 * @description:
 */
public abstract class AbstractAsymmetricArgumentEncryptor extends AbstractArgumentEncryptor {

    /**
     * 公钥解密器
     */
    protected Cipher publicDecryptCipher;
    /**
     * 私钥加密器
     */
    protected Cipher privateEncryptCipher;

    protected abstract void initConfigInternal(AlgorithmEnvironments environments, String strPublicKey, String strPrivateKey) throws Exception;

    protected abstract Cipher createEncryptCipher(String secret) throws Exception;

    protected abstract Cipher createDecryptCipher(String secret) throws Exception;

    @Override
    protected void initConfigRespective(AlgorithmEnvironments environments) throws Exception {
        // 获取公钥、私钥
        String strPublicKey = environments.getAlgorithmConfigElseThrow(AsymmetricConfigKey.PUBLIC_KEY);
        String strPrivateKey = environments.getAlgorithmConfigElseThrow(AsymmetricConfigKey.PRIVATE_KEY);
        // 每个算法内部配置
        this.initConfigInternal(environments, strPublicKey, strPrivateKey);
        // 实例化Cipher
        this.publicDecryptCipher = this.createDecryptCipher(strPublicKey);
        this.privateEncryptCipher = this.createEncryptCipher(strPrivateKey);
    }

    @Override
    protected String encryptInternal(String plainText, ResponseHeaders responseHeaders) throws Exception {
        byte[] var1 = plainText.getBytes(charset);
        byte[] var2 = this.privateEncryptCipher.doFinal(var1);
        return Base64.encodeBase64String(var2);
    }

    @Override
    protected String decryptInternal(String cipherText, RequestHeaders requestHeaders) throws Exception {
        byte[] var1 = Base64.decodeBase64(cipherText);
        byte[] var2 = this.publicDecryptCipher.doFinal(var1);
        return new String(var2);
    }

}
