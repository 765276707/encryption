package com.github.xzb617.encryption.autoconfigure.proxy;

import com.github.xzb617.encryption.autoconfigure.consoles.EncryptionContextHolder;
import com.github.xzb617.encryption.autoconfigure.consoles.details.DecryptedDetail;
import com.github.xzb617.encryption.autoconfigure.consoles.details.EncryptedDetail;
import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import com.github.xzb617.encryption.autoconfigure.envirs.RequestHeaders;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对加密器进行代理
 * @author xzb617
 * @date 2022/5/8 0:31
 * @description:
 */
public class EncryptorProxyManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(EncryptorProxyManager.class);

    private final ArgumentEncryptor proxyTarget;
    private final Configurator configurator;

    public EncryptorProxyManager(ArgumentEncryptor argumentEncryptor, Configurator configurator) {
        this.proxyTarget = argumentEncryptor;
        this.configurator = configurator;
    }

    /**
     * 加密
     * @param plainText 明文
     * @param responseHeaders 响应头
     * @return
     */
    public String encrypt(String plainText, ResponseHeaders responseHeaders, String retModelName, String retModelFieldName) {
        Boolean showDetails = this.configurator.getShowDetails();
        Boolean showDetailsHeader = this.configurator.getShowDetailsHeader();
        if (showDetails) {
            long stime = System.currentTimeMillis();
            String result = this.proxyTarget.encrypt(plainText, responseHeaders);
            long ftime = System.currentTimeMillis();
            EncryptedDetail encryptedDetails = new EncryptedDetail();
            encryptedDetails.setRetModelName(retModelName);
            encryptedDetails.setRetModelFieldName(retModelFieldName);
            encryptedDetails.setBeforeEncryptedValue(plainText);
            encryptedDetails.setAfterEncryptedValue(result);
            encryptedDetails.setCostEncryptedTime(ftime-stime);
            encryptedDetails.setHttpResponseHeaders(Boolean.TRUE.equals(showDetailsHeader)?responseHeaders.getAllHeaders():"<Disabled>");
            EncryptionContextHolder.get().getEncryptedDetails().add(encryptedDetails);
            return result;
        } else {
            return this.proxyTarget.encrypt(plainText, responseHeaders);
        }
    }


    /**
     * 解密
     * @param cipherText 密文
     * @param requestHeaders 请求头
     * @return
     */
    public String decrypt(String cipherText, RequestHeaders requestHeaders, MethodParameter methodParameter, String parameterName) {
        Boolean showDetails = this.configurator.getShowDetails();
        Boolean showDetailsHeader = this.configurator.getShowDetailsHeader();
        if (showDetails) {
            long stime = System.currentTimeMillis();
            String result = this.proxyTarget.decrypt(cipherText, requestHeaders);
            long ftime = System.currentTimeMillis();
            DecryptedDetail decryptedDetails = new DecryptedDetail();
            decryptedDetails.setParameterName(parameterName);
            decryptedDetails.setBeforeDecryptedValue(cipherText);
            decryptedDetails.setAfterDecryptedValue(result);
            decryptedDetails.setCostDecryptedTime(ftime-stime);
            decryptedDetails.setHttpRequestHeaders(Boolean.TRUE.equals(showDetailsHeader)?requestHeaders.getAllHeaders():"<Disabled>");
            EncryptionContextHolder.get().getDecryptedDetails().add(decryptedDetails);
            return result;
        } else {
            return this.proxyTarget.decrypt(cipherText, requestHeaders);
        }
    }

    private String getMethodName(Method method) {
        return method==null?"<Undefined>":method.getName() + "(*)";
    }

    private String getClassName(Method method) {
        return method==null?"<Undefined>":method.getDeclaringClass().getName();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(date);
    }
}
