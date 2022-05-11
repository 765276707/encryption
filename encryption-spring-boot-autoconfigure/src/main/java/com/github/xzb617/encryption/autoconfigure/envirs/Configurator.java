package com.github.xzb617.encryption.autoconfigure.envirs;

/**
 * 配置器
 * @author xzb617
 * @date 2022/5/8 0:35
 * @description:
 */
public class Configurator {

    /**
     * 是否开启
     */
    private Boolean showDetails = false;
    /**
     * 是否展示请求头信息
     */
    private Boolean showDetailsHeader = true;

    /**
     * 请求编号在请求头的中key
     */
    private String requestIdKey = "Request-Id";

    public Configurator enableShowDetails() {
        this.showDetails = true;
        return this;
    }

    public Configurator disableShowDetailsHeader() {
        this.showDetailsHeader = false;
        return this;
    }

    public Configurator setRequestIdKey(String requestIdKey) {
        this.requestIdKey = requestIdKey;
        return this;
    }

    public Boolean getShowDetails() {
        return showDetails;
    }

    public Boolean getShowDetailsHeader() {
        return showDetailsHeader;
    }

    public String getRequestIdKey() {
        return requestIdKey;
    }
}
