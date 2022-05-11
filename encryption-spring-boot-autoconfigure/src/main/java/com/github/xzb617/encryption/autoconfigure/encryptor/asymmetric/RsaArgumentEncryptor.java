package com.github.xzb617.encryption.autoconfigure.encryptor.asymmetric;

import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.utils.RsaUtil;
import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA加密器
 * @author xzb617
 * @date 2022/5/4 18:06
 * @description:
 */
public class RsaArgumentEncryptor extends AbstractAsymmetricArgumentEncryptor {
    /**
     * 公钥
     */
    private PublicKey publicKey;
    /**
     * 撕咬
     */
    private PrivateKey privateKey;

    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments, String strPublicKey, String strPrivateKey) throws Exception {
        this.publicKey = RsaUtil.string2PublicKey(strPublicKey);
        this.privateKey = RsaUtil.string2PrivateKey(strPrivateKey);
    }

    @Override
    protected Cipher createEncryptCipher(String secret) throws Exception {
        return RsaUtil.getCipher(Cipher.ENCRYPT_MODE, this.privateKey);
    }

    @Override
    protected Cipher createDecryptCipher(String secret) throws Exception {
        return RsaUtil.getCipher(Cipher.DECRYPT_MODE, this.publicKey);
    }

}
