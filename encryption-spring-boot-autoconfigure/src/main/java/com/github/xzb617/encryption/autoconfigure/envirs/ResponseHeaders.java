package com.github.xzb617.encryption.autoconfigure.envirs;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import java.util.*;

/**
 * 响应头
 * @author xzb617
 * @date 2022/5/8 14:16
 * @description:
 */
public class ResponseHeaders {

    private HttpHeaders httpHeaders;

    private ResponseHeaders() {}

    public ResponseHeaders(HttpHeaders httpHeaders) {
        Assert.notNull(httpHeaders,  "HttpHeaders can not be null.");
        this.httpHeaders = httpHeaders;
    }

    public ResponseHeaders(ServerHttpResponse serverHttpResponse) {
        Assert.notNull(serverHttpResponse,  "ServerHttpResponse can not be null.");
        this.httpHeaders = serverHttpResponse.getHeaders();
    }

    /**
     * 获取请求头
     * @param key 键
     * @return
     */
    public String getHeader(String key) {
        List<String> values = httpHeaders.get(key);
        if (values!=null && !values.isEmpty()) {
            return values.get(0);
        }
        return null;
    }

    /**
     * 获取整数型请求头
     * @param key 键
     * @return
     */
    public Integer getIntHeader(String key) {
        String value = this.getHeader(key);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }

    /**
     * 获取长整型请求头
     * @param key 键
     * @return
     */
    public Long getLongHeader(String key) {
        String value = this.getHeader(key);
        if (value == null) {
            return null;
        }
        return Long.parseLong(value);
    }

    /**
     * 移除
     * @param key
     */
    public void removeHeader(String key) {
        this.httpHeaders.remove(key);
    }

    /**
     * 添加请求头
     * @param key 键
     * @param value 值
     */
    public void addHeader(String key, String value) {
        this.httpHeaders.add(key, value);
    }

    public String getAllHeaders() {
        final String blankSpacePlaceholder = "                   ";
        Set<Map.Entry<String, List<String>>> entries = this.httpHeaders.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = entries.iterator();
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String headerValue = formatHeaderListToString(entry.getValue());
            builder.append(blankSpacePlaceholder).append("        ").append(entry.getKey()).append(": ").append(headerValue).append("\n");
        }
        builder.append(blankSpacePlaceholder).append("      }");
        return builder.toString();
    }

    private String formatHeaderListToString(List<String> headerValues) {
        if (headerValues.size() == 1) {
            return headerValues.get(0);
        }
        return headerValues.toString();
    }
}
