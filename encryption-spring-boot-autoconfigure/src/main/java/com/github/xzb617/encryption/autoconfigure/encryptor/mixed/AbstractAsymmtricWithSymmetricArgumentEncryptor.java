package com.github.xzb617.encryption.autoconfigure.encryptor.mixed;

import com.github.xzb617.encryption.autoconfigure.constant.AsymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.mock.MockConstant;
import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
import com.github.xzb617.encryption.autoconfigure.encryptor.AbstractArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.spring.HttpMessageDecryptException;
import com.github.xzb617.encryption.autoconfigure.factories.SecretFactory;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;

/**
 * 抽象的混合模式加密器
 * @author xzb617
 * @date 2022/5/6 14:13
 * @description: 对称算法与非对称算法结合
 */
public abstract class AbstractAsymmtricWithSymmetricArgumentEncryptor extends AbstractArgumentEncryptor {

    /**
     * 对称算法密钥在header的key
     */
    protected String secretKeyInHeader = "secret";
    /**
     * 非对称算法公钥加密器
     */
    protected Cipher publicDecryptCipher;
    /**
     * 非对称算法私钥解密器
     */
    protected Cipher privateEncryptCipher;


    protected abstract void initConfigInternal(AlgorithmEnvironments environments, String strPublicKey, String strPrivateKey) throws Exception;

    protected abstract Cipher createEncryptCipher(String secret) throws Exception;

    protected abstract Cipher createDecryptCipher(String secret) throws Exception;

    @Override
    protected void initConfigRespective(AlgorithmEnvironments environments) throws Exception {
        // 获取配置
        String key = environments.getAlgorithmConfig(SymmetricConfigKey.SECRET_KEY_IN_HEADER);
        if (key!=null && !"".equals(key)) {
            this.secretKeyInHeader = key;
        }
        String strPublicKey = environments.getAlgorithmConfigElseThrow(AsymmetricConfigKey.PUBLIC_KEY);
        String strPrivateKey = environments.getAlgorithmConfigElseThrow(AsymmetricConfigKey.PRIVATE_KEY);
        // 指定加密的工作模式、填充方式
        this.initConfigInternal(environments, strPublicKey, strPrivateKey);
    }

    @Override
    protected String encryptInternal(String plainText, ResponseHeaders responseHeaders) throws Exception {
        String secret = responseHeaders.getHeader(MockConstant.MOCK_SECRET_KEY);
        if (secret == null) {
            secret = SecretFactory.generateRandomUUIDSecret(16);
        }
        // RSA加密密钥(客户端给的公钥进行加密)，并添加到响应头
        byte[] secretBytes = this.privateEncryptCipher.doFinal(secret.getBytes(charset));
        String encryptedSecret = Base64.encodeBase64String(secretBytes);
        this.setSecretIntoHeader(responseHeaders, encryptedSecret);
        // 移除Mock数据
        responseHeaders.removeHeader(MockConstant.MOCK_SECRET_KEY);
        // 获取 Cipher
        Cipher cipher = this.createEncryptCipher(secret);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(charset));
        // 对加密后数据进行 Base64 编码
        return Base64.encodeBase64URLSafeString(encryptedBytes);
    }

    @Override
    protected String decryptInternal(String cipherText, RequestHeaders requestHeaders) throws Exception {
        // 获取请求头中经过RSA加密的AES secret的值
        String aesSecret = this.getSecretFromHeader(requestHeaders);
        // 解密（服务端利用客户端给的公钥解密） AES secret
        byte[] secretBytes = Base64.decodeBase64(aesSecret);
        byte[] bytes = this.publicDecryptCipher.doFinal(secretBytes);
        String secret = new String(bytes);
        // 获取 Cipher
        Cipher cipher = this.createDecryptCipher(secret);
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)));
    }


    protected String getSecretFromHeader(RequestHeaders requestHeaders) {
        String secret = requestHeaders.getHeader(this.secretKeyInHeader);
        if (secret == null) {
            throw new HttpMessageDecryptException(
                    String.format("Http message decrypt error cause by missing required request header: %s", this.secretKeyInHeader));
        }
        return secret;
    }

    protected void setSecretIntoHeader(ResponseHeaders responseHeaders, String secret) {
        responseHeaders.addHeader(this.secretKeyInHeader, secret);
    }

}
