package com.github.xzb617.encryption.autoconfigure.consoles.details;

import java.io.Serializable;

/**
 * 解密详情
 * @author Pristine Xu
 * @date 2022/5/10 12:44
 * @description:
 */
public class DecryptedDetail implements Serializable {

    /**
     * 参数名称
     */
    private String parameterName;

    /**
     * 解密之前的值
     */
    private String beforeDecryptedValue;

    /**
     * 解密之后的值
     */
    private String afterDecryptedValue;

    /**
     * 解密耗时，毫秒
     */
    private Long costDecryptedTime = 0L;

    /**
     * 请求头信息
     */
    private String httpRequestHeaders;


    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getBeforeDecryptedValue() {
        return beforeDecryptedValue;
    }

    public void setBeforeDecryptedValue(String beforeDecryptedValue) {
        this.beforeDecryptedValue = beforeDecryptedValue;
    }

    public String getAfterDecryptedValue() {
        return afterDecryptedValue;
    }

    public void setAfterDecryptedValue(String afterDecryptedValue) {
        this.afterDecryptedValue = afterDecryptedValue;
    }

    public Long getCostDecryptedTime() {
        return costDecryptedTime;
    }

    public void setCostDecryptedTime(Long costDecryptedTime) {
        this.costDecryptedTime = costDecryptedTime;
    }

    public String getHttpRequestHeaders() {
        return httpRequestHeaders;
    }

    public void setHttpRequestHeaders(String httpRequestHeaders) {
        this.httpRequestHeaders = httpRequestHeaders;
    }
}
