package com.github.xzb617.encryption.autoconfigure.encryptor.symmetric;

import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;

import javax.crypto.Cipher;


/**
 * IDEA加密器
 * @author xzb617
 * @date 2022/5/4 18:06
 * @description:
 */
public class IdeaArgumentEncryptor extends AbstractSymmetricArgumentEncryptor {

    /**
     * 算法名称
     */
    private final static String ALG = "IDEA";
    /**
     * 填充方式
     */
    private final static String PADDING = "IDEA/ECB/PKCS5Padding";


    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments) throws Exception {
    }

    @Override
    protected Cipher createEncryptCipher(String secret) throws Exception {
        return getECBModeCipherInstance(ALG, PADDING, secret, Cipher.ENCRYPT_MODE);
    }

    @Override
    protected Cipher createDecryptCipher(String secret) throws Exception {
        return getECBModeCipherInstance(ALG, PADDING, secret, Cipher.DECRYPT_MODE);
    }

}
