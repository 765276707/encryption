package com.github.xzb617.encryption.autoconfigure.encryptor.symmetric;

import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.impl.ConfigurableAlgorithmEnvironments;

import javax.crypto.Cipher;


/**
 * DES3加密器
 * @author xzb617
 * @date 2022/5/4 18:06
 * @description:
 */
public class Des3ArgumentEncryptor extends AbstractSymmetricArgumentEncryptor {

    /**
     * 算法名称
     */
    private final static String ALG = "DESede";
    /**
     * 填充方式
     */
    private final static String PADDING = "DESede/CBC/PKCS5Padding";
    /**
     * 偏移量
     */
    private String ivFromConfig;

    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments) throws Exception {
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
