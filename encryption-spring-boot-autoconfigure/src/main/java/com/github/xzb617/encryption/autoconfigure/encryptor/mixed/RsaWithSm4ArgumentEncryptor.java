package com.github.xzb617.encryption.autoconfigure.encryptor.mixed;

import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;


import javax.crypto.Cipher;


/**
 * RsaWithSm4 加密器
 * @author xzb617
 * @date 2022/5/4 18:07
 * @description:
 */
public class RsaWithSm4ArgumentEncryptor extends AbstractRsaWithSymmetricArgumentEncryptor {

    /**
     * 算法名称
     */
    private final static String ALG = "SM4";
    /**
     * 填充方式
     */
    private final static String PADDING = "SM4/ECB/PKCS5Padding";

    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments) throws Exception {

    }

    @Override
    protected Cipher createEncryptCipher(String secret) throws Exception {
        // 初始化 Cipher
        return this.getECBModeCipherInstance(ALG, PADDING, secret, Cipher.ENCRYPT_MODE);
    }

    @Override
    protected Cipher createDecryptCipher(String secret) throws Exception {
        // 初始化 Cipher
        return this.getECBModeCipherInstance(ALG, PADDING, secret, Cipher.DECRYPT_MODE);
    }

}
