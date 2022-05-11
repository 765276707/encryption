package com.github.xzb617.encryption.autoconfigure.constant;

/**
 * 加密器算法
 * @author xzb617
 * @date 2022/5/4 16:30
 * @description:
 */
public enum Algorithm {

    /** AES */
    AES("com.github.xzb617.encryption.autoconfigure.encryptor.symmetric.AesArgumentEncryptor"),
    /** DES3 */
    DES3("com.github.xzb617.encryption.autoconfigure.encryptor.symmetric.Des3ArgumentEncryptor"),
    /** IDEA */
    IDEA("com.github.xzb617.encryption.autoconfigure.encryptor.symmetric.IdeaArgumentEncryptor"),
    /** SM4 */
    SM4("com.github.xzb617.encryption.autoconfigure.encryptor.symmetric.Sm4ArgumentEncryptor"),
    /** RSA */
    RSA("com.github.xzb617.encryption.autoconfigure.encryptor.asymmetric.RsaArgumentEncryptor"),
    /** RSA_WITH_AES */
    RSA_WITH_AES("com.github.xzb617.encryption.autoconfigure.encryptor.mixed.RsaWithAesArgumentEncryptor"),
    /** RSA_WITH_DES3 */
    RSA_WITH_DES3("com.github.xzb617.encryption.autoconfigure.encryptor.mixed.RsaWithDes3ArgumentEncryptor"),
    /** RSA_WITH_SM4 */
    RSA_WITH_SM4("com.github.xzb617.encryption.autoconfigure.encryptor.mixed.RsaWithSm4ArgumentEncryptor");


    private String value;

    Algorithm(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
