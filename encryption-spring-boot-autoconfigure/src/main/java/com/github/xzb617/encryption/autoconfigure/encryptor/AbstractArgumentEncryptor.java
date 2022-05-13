package com.github.xzb617.encryption.autoconfigure.encryptor;

import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.impl.ConfigurableAlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encryption.autoconfigure.exceptions.framework.IllegalAlgorithmConfigException;
import com.github.xzb617.encryption.autoconfigure.exceptions.framework.MissingAlgorithmConfigException;
import com.github.xzb617.encryption.autoconfigure.exceptions.spring.HttpMessageDecryptException;
import com.github.xzb617.encryption.autoconfigure.exceptions.spring.HttpMessageEncryptException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * 抽象的加密器
 * @author xzb617
 * @date 2022/5/6 15:34
 * @description:
 */
public abstract class AbstractArgumentEncryptor implements ArgumentEncryptor {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractArgumentEncryptor.class);

    /**
     * 字符集
     */
    protected Charset charset;

    /**
     * 各个算法内部配置初始化
     * @param environments 环境变量
     * @throws Exception
     */
    protected abstract void initConfigRespective(AlgorithmEnvironments environments) throws Exception;
    /**
     * 加密
     * @param plainText 明文
     * @param responseHeaders 响应头
     * @return
     * @throws Exception
     */
    protected abstract String encryptInternal(String plainText, ResponseHeaders responseHeaders) throws Exception;
    /**
     * 解密
     * @param cipherText 密文
     * @param requestHeaders 请求头
     * @return
     * @throws Exception
     */
    protected abstract String decryptInternal(String cipherText, RequestHeaders requestHeaders) throws Exception;

    @Override
    public void initConfig(AlgorithmEnvironments environments) {
        try {
            this.charset = Charset.forName(environments.getCharset());
            this.initConfigRespective(environments);
        } catch (MissingAlgorithmConfigException var1) {
            throw var1;
        } catch (Exception var2) {
            throw new IllegalAlgorithmConfigException("Illegal algorithm config value.", var2);
        }
    }

    @Override
    public String encrypt(String plainText, ResponseHeaders responseHeaders) {
        try {
            return this.encryptInternal(plainText, responseHeaders);
        }
        catch (IllegalBlockSizeException | BadPaddingException var1) {
            throw new HttpMessageEncryptException("Http message encrypt error: " + var1.getMessage(), var1);
        }
        catch (Exception var2) {
            throw new HttpMessageEncryptException(var2.getMessage(), var2);
        }
    }

    @Override
    public String decrypt(String cipherText, RequestHeaders requestHeaders) {
        try {
            return this.decryptInternal(cipherText, requestHeaders);
        }
        catch (IllegalBlockSizeException | BadPaddingException var1) {
            throw new HttpMessageDecryptException("Http message decrypt error: " + var1.getMessage(), var1);
        }
        catch (Exception var2) {
            throw new HttpMessageDecryptException(var2.getMessage(), var2);
        }
    }

    /**
     * 获取 CBC 模式下的 Cipher
     * @param alg 算法名称
     * @param padding 填充模式
     * @param secret 秘钥
     * @param iv 偏移量
     * @param cipherMode 加密/解密模式
     * @return
     * @throws Exception
     */
    protected Cipher getCBCModeCipherInstance(String alg, String padding, String secret, String iv, int cipherMode) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(padding);
        cipher.init(cipherMode, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    /**
     * 获取 ECB 模式下的 Cipher
     * @param alg 算法名称
     * @param padding 填充模式
     * @param secret 秘钥
     * @param cipherMode 加密/解密模式
     * @return
     * @throws Exception
     */
    protected Cipher getECBModeCipherInstance(String alg, String padding, String secret, int cipherMode) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), alg);
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(padding, new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher;
    }

}
