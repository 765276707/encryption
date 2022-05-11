package com.github.xzb617.encryption.autoconfigure.encryptor.symmetric;

import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;

import javax.crypto.Cipher;


/**
 * Aes加密器
 * @author xzb617
 * @date 2022/5/4 18:05
 * @description: 密钥长度： 128，192，256 bit 即 18，24，32 byte
 *               偏移量长度：128 bit 即 16 byte
 */
public class AesArgumentEncryptor extends AbstractSymmetricArgumentEncryptor {

    /**
     * 算法名称
     */
    private final static String ALG = "AES";
    /**
     * 填充方式
     */
    private final static String PADDING = "AES/CBC/PKCS5Padding";
    /**
     * 偏移量
     */
    private String ivFromConfig;

    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments) throws Exception {
        // 获取密钥和偏移量
        this.ivFromConfig = environments.getAlgorithmConfigElseThrow(SymmetricConfigKey.IV);
    }

    @Override
    protected Cipher createEncryptCipher(String secret) throws Exception {
        return getCBCModeCipherInstance(ALG, PADDING, secret, this.ivFromConfig, Cipher.ENCRYPT_MODE);
    }

    @Override
    protected Cipher createDecryptCipher(String secret) throws Exception {
        return getCBCModeCipherInstance(ALG, PADDING, secret, this.ivFromConfig, Cipher.DECRYPT_MODE);
    }

}
