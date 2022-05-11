package com.github.xzb617.encryption.autoconfigure.encryptor.mixed;

import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;

import javax.crypto.Cipher;


/**
 * RsaWithDes3 加密器
 * @author xzb617
 * @date 2022/5/4 18:07
 * @description:
 */
public class RsaWithDes3ArgumentEncryptor extends AbstractRsaWithSymmetricArgumentEncryptor {

    /**
     * 算法名称
     */
    private final static String ALG = "DESede";
    /**
     * 填充方式
     */
    private final static String PADDING = "DESede/CBC/PKCS5Padding";

    /**
     * AES偏移量
     */
    private String ivFromConfig;


    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments) throws Exception {
        // CBC模式下需要配置偏移量
        this.ivFromConfig = environments.getAlgorithmConfigElseThrow(SymmetricConfigKey.IV);
    }

    @Override
    protected Cipher createEncryptCipher(String secret) throws Exception {
        return this.getCBCModeCipherInstance(ALG, PADDING, secret, this.ivFromConfig, Cipher.ENCRYPT_MODE);
    }

    @Override
    protected Cipher createDecryptCipher(String secret) throws Exception {
        return this.getCBCModeCipherInstance(ALG, PADDING, secret, this.ivFromConfig, Cipher.DECRYPT_MODE);
    }
}
