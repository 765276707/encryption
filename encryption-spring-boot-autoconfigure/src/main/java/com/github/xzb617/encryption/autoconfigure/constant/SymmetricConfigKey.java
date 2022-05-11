package com.github.xzb617.encryption.autoconfigure.constant;

/**
 * 对称算法的配置项
 * @author xzb617
 * @date 2022/5/5 12:22
 * @description:
 */
public class SymmetricConfigKey {

    /**
     * 对称加密算法密钥
     */
    public final static String SECRET = "secret";

    /**
     * 对称加密算法偏移量
     */
    public final static String IV = "iv";

    /**
     * 对称加密算法密钥在请求头中的key
     */
    public final static String SECRET_KEY_IN_HEADER = "secret-header-key";

}
