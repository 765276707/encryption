package com.github.xzb617.encryption.autoconfigure.encryptor;

import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;

/**
 * 参数加密器接口
 * @author xzb617
 * @date 2022/5/4 16:34
 * @description:
 */
public interface ArgumentEncryptor extends ArgumentConfigable {

    /**
     * 加密
     * @param plainText 明文
     * @param responseHeaders 响应头
     * @return
     */
    String encrypt(String plainText, ResponseHeaders responseHeaders);


    /**
     * 解密
     * @param cipherText 密文
     * @param requestHeaders 请求头
     * @return
     */
    String decrypt(String cipherText, RequestHeaders requestHeaders);

}
