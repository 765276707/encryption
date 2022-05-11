package com.github.xzb617.encryption.autoconfigure.factories;

import java.util.UUID;

/**
 * 秘钥生成工厂
 * @author xzb617
 * @date 2022/5/6 13:59
 * @description:
 */
public class SecretFactory {

    /**
     * 生成基于UUID的随机密钥
     * @param length 密钥的长度，0 < length < 32
     * @return
     */
    public static String generateRandomUUIDSecret(int length) {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, length)
                .toUpperCase();
    }

}
