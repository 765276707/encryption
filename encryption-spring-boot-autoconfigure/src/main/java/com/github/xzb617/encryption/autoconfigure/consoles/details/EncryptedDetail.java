package com.github.xzb617.encryption.autoconfigure.consoles.details;

import java.io.Serializable;

/**
 * 加密详情
 * @author Pristine Xu
 * @date 2022/5/10 12:52
 * @description:
 */
public class EncryptedDetail implements Serializable {

    /**
     * 响应的实体类名称
     */
    private String retModelName;

    /**
     * 加密的字段名称，如果没有则是整个实体类进行加密
     */
    private String retModelFieldName;

    /**
     * 加密之前的值
     */
    private String beforeEncryptedValue;

    /**
     * 解加密之后的值
     */
    private String afterEncryptedValue;

    /**
     * 加密耗时，毫秒
     */
    private Long costEncryptedTime = 0L;

    /**
     * 响应头信息
     */
    private String httpResponseHeaders;


    public String getRetModelName() {
        return retModelName;
    }

    public void setRetModelName(String retModelName) {
        this.retModelName = retModelName;
    }

    public String getRetModelFieldName() {
        return retModelFieldName;
    }

    public void setRetModelFieldName(String retModelFieldName) {
        this.retModelFieldName = retModelFieldName;
    }

    public String getBeforeEncryptedValue() {
        return beforeEncryptedValue;
    }

    public void setBeforeEncryptedValue(String beforeEncryptedValue) {
        this.beforeEncryptedValue = beforeEncryptedValue;
    }

    public String getAfterEncryptedValue() {
        return afterEncryptedValue;
    }

    public void setAfterEncryptedValue(String afterEncryptedValue) {
        this.afterEncryptedValue = afterEncryptedValue;
    }

    public Long getCostEncryptedTime() {
        return costEncryptedTime;
    }

    public void setCostEncryptedTime(Long costEncryptedTime) {
        this.costEncryptedTime = costEncryptedTime;
    }

    public String getHttpResponseHeaders() {
        return httpResponseHeaders;
    }

    public void setHttpResponseHeaders(String httpResponseHeaders) {
        this.httpResponseHeaders = httpResponseHeaders;
    }
}
