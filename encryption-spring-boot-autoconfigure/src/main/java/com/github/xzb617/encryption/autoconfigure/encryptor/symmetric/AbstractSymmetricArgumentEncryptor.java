package com.github.xzb617.encryption.autoconfigure.encryptor.symmetric;

import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.encryptor.AbstractArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;


/**
 * 抽象的对称算法加密器
 * @author xzb617
 * @date 2022/5/6 13:32
 * @description:
 */
public abstract class AbstractSymmetricArgumentEncryptor extends AbstractArgumentEncryptor {

    /**
     * 加密器
     */
    protected Cipher encryptCipher;
    /**
     * 解密器
     */
    protected Cipher decryptCipher;

    /**
     * 各自算法内的配置初始化方法
     * @param environments 环境变量
     * @throws Exception
     */
    protected abstract void initConfigInternal(AlgorithmEnvironments environments) throws Exception;

    /**
     * 提供一个加密的Cipher
     * @param secret 秘钥
     * @return
     * @throws Exception
     */
    protected abstract Cipher createEncryptCipher(String secret) throws Exception;

    /**
     * 提供一个解密的Cipher
     * @param secret
     * @return
     * @throws Exception
     */
    protected abstract Cipher createDecryptCipher(String secret) throws Exception;


    @Override
    protected void initConfigRespective(AlgorithmEnvironments environments) throws Exception {
        // 获取密钥和偏移量
        String secret = environments.getAlgorithmConfigElseThrow(SymmetricConfigKey.SECRET);
        // 指定加密的工作模式、填充方式
        this.initConfigInternal(environments);
        this.encryptCipher = this.createEncryptCipher(secret);
        this.decryptCipher = this.createDecryptCipher(secret);
    }

    @Override
    protected String encryptInternal(String plainText, ResponseHeaders responseHeaders) throws Exception {
        byte[] encryptedBytes = this.encryptCipher.doFinal(plainText.getBytes());
        return Base64.encodeBase64URLSafeString(encryptedBytes);
    }

    @Override
    protected String decryptInternal(String cipherText, RequestHeaders requestHeaders) throws Exception {
        byte[] var1 = Base64.decodeBase64(cipherText);
        byte[] var2 = this.decryptCipher.doFinal(var1);
        return new String(var2);
    }

}
