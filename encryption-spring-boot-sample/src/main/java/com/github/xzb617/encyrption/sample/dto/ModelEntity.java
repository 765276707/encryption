package com.github.xzb617.encyrption.sample.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptModelField;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * 一个请求的Model实体类
 * @author xzb617
 * @date 2022/5/11 14:26
 * @description: 只有标记了 @DecryptModelField 的属性才会被解密
 */
public class ModelEntity {

    @DecryptModelField
    private Long longKey;

    @DecryptModelField
    private String strKey;

    @DecryptModelField
    private Integer intKey;

    @DecryptModelField
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateKey;

    public Long getLongKey() {
        return longKey;
    }

    public void setLongKey(Long longKey) {
        this.longKey = longKey;
    }

    public String getStrKey() {
        return strKey;
    }

    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    public Integer getIntKey() {
        return intKey;
    }

    public void setIntKey(Integer intKey) {
        this.intKey = intKey;
    }

    public Date getDateKey() {
        return dateKey;
    }

    public void setDateKey(Date dateKey) {
        this.dateKey = dateKey;
    }

    @Override
    public String toString() {
        return "ModelEntity{" +
                "longKey=" + longKey +
                ", strKey='" + strKey + '\'' +
                ", intKey=" + intKey +
                ", dateKey=" + dateKey +
                '}';
    }
}
