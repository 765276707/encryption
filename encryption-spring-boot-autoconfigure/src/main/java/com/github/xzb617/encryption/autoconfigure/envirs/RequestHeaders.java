package com.github.xzb617.encryption.autoconfigure.envirs;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 请求头
 * @author xzb617
 * @date 2022/5/8 14:16
 * @description:
 */
public class RequestHeaders {

    private HttpHeaders httpHeaders;

    private HttpServletRequest httpServletRequest;

    private RequestHeaders() {}

    public RequestHeaders(HttpHeaders httpHeaders) {
        Assert.notNull(httpHeaders,  "HttpHeaders can not be null.");
        this.httpHeaders = httpHeaders;
    }

    public RequestHeaders(HttpServletRequest httpServletRequest) {
        Assert.notNull(httpServletRequest,  "HttpServletRequest can not be null.");
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 获取请求头
     * @param key 键
     * @return
     */
    public String getHeader(String key) {
        if (httpServletRequest != null) {
            return httpServletRequest.getHeader(key);
        }
        if (httpHeaders != null) {
            List<String> values = httpHeaders.get(key);
            if (values!=null && !values.isEmpty()) {
                return values.get(0);
            }
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

    public String getAllHeaders() {
        final String blankSpacePlaceholder = "                   ";
        if (this.httpHeaders != null) {
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
        } else {
            StringBuilder builder = new StringBuilder();
            Enumeration<String> headerNames = this.httpServletRequest.getHeaderNames();
            builder.append("{\n");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = this.httpServletRequest.getHeader(headerName);
                builder.append(blankSpacePlaceholder).append("        ").append(headerName).append(": ").append(headerValue).append("\n");
            }
            builder.append(blankSpacePlaceholder).append("      }");
            return builder.toString();
        }
    }

    private String formatHeaderListToString(List<String> headerValues) {
        if (headerValues.size() == 1) {
            return headerValues.get(0);
        }
        return headerValues.toString();
    }

}
