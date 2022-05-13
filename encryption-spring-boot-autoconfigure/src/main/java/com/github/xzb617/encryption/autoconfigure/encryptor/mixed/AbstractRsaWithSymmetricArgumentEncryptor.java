package com.github.xzb617.encryption.autoconfigure.encryptor.mixed;

import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.impl.ConfigurableAlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.utils.RsaUtil;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 抽象的RSA与对称算法加密器
 * @author xzb617
 * @date 2022/5/6 15:10
 * @description:
 */
public abstract class AbstractRsaWithSymmetricArgumentEncryptor extends AbstractAsymmtricWithSymmetricArgumentEncryptor {

    @Override
    protected void initConfigInternal(AlgorithmEnvironments environments, String strPublicKey, String strPrivateKey) throws Exception {
        // 转换RSA密钥
        PublicKey publicKey = RsaUtil.string2PublicKey(strPublicKey);
        PrivateKey privateKey = RsaUtil.string2PrivateKey(strPrivateKey);
        // 实例化RSA Cipher
        this.publicDecryptCipher = RsaUtil.getCipher(Cipher.DECRYPT_MODE, publicKey);
        this.privateEncryptCipher = RsaUtil.getCipher(Cipher.ENCRYPT_MODE, privateKey);
        // 其他配置
        this.initConfigInternal(environments);
    }

    /**
     * 内部配置
     * @param environments 环境变量
     * @throws Exception
     */
    protected abstract void initConfigInternal(AlgorithmEnvironments environments) throws Exception;

}
