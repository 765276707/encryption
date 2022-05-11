package com.github.xzb617.encryption.autoconfigure.consoles.details;

import java.util.Collection;

/**
 * @author Pristine Xu
 * @date 2022/5/10 14:27
 * @description:
 */
public class ConsoleDetails {

    /**
     * 请求编号
     */
    private final String requestId;
    /**
     * 请求路径
     */
    private final String requestUri;
    /**
     * 请求时间
     */
    private final String requestTime;
    /**
     * 解密详情
     */
    private Collection<DecryptedDetail> decryptedDetails;
    /**
     * 加密详情
     */
    private Collection<EncryptedDetail> encryptedDetails;

    public ConsoleDetails(String requestId, String requestUri, String requestTime, Collection<DecryptedDetail> decryptedDetails, Collection<EncryptedDetail> encryptedDetails) {
        this.requestId = requestId;
        this.requestUri = requestUri;
        this.requestTime = requestTime;
        this.decryptedDetails = decryptedDetails;
        this.encryptedDetails = encryptedDetails;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public Collection<DecryptedDetail> getDecryptedDetails() {
        return decryptedDetails;
    }

    public Collection<EncryptedDetail> getEncryptedDetails() {
        return encryptedDetails;
    }

    /**
     * 合并结果
     * @return
     */
    public String mergeResult() {
        // 合并请求信息
        long decryptedTime = 0;
        String httpRequestHeaders = "";
        StringBuilder decryptedParameters = new StringBuilder();
        if (decryptedDetails.size() > 0) {
            decryptedParameters.append("{\n");
            for (DecryptedDetail dd : decryptedDetails) {
                decryptedTime += dd.getCostDecryptedTime();
                httpRequestHeaders = dd.getHttpRequestHeaders();
                decryptedParameters
                        .append("                           ").append(dd.getParameterName()).append(" : \n")
                        .append("                             {\n")
                        .append("                               Decrypted Before: ").append(dd.getBeforeDecryptedValue()).append("\n")
                        .append("                               Decrypted After : ").append(dd.getAfterDecryptedValue()).append("\n")
                        .append("                             }\n");
            }
            decryptedParameters.append("                         }");
        } else {
            httpRequestHeaders = "{}";
            decryptedParameters.append("<No Request Parameters Decrypted>");
        }


        // 合并响应信息
        long encryptedTime = 0;
        String httpResponseHeaders = "";
        StringBuilder encryptedRetModel = new StringBuilder();
        if (encryptedDetails.size() > 0) {
            encryptedRetModel.append("{\n");
            for (EncryptedDetail ed : encryptedDetails) {
                encryptedTime += ed.getCostEncryptedTime();
                httpResponseHeaders = ed.getHttpResponseHeaders();
                encryptedRetModel
                        .append("                           ").append(ed.getRetModelName()).append(" : \n")
                        .append("                             {\n")
                        .append("                               Encrypted Field : ").append(ed.getRetModelFieldName()).append("\n")
                        .append("                               Encrypted Before: ").append(ed.getBeforeEncryptedValue()).append("\n")
                        .append("                               Encrypted After : ").append(ed.getAfterEncryptedValue()).append("\n")
                        .append("                             }\n");
            }
            encryptedRetModel.append("                         }");
        } else {
            httpResponseHeaders = "{}";
            encryptedRetModel.append("<No Response RetModel Encrypted>");
        }


        // 打印合并后的结果
        return String.format("Decrypted request arguments completed，show details ==> " +
                        "\n{" +
                        "\n  Request Id           : %s" +
                        "\n  Request Uri          : %s" +
                        "\n  Request Time         : %s" +
                        "\n  Decrypted Parameters : %s" +
                        "\n  Decrypted Time       : %d ms" +
                        "\n  Encrypted RetModel   : %s" +
                        "\n  Encrypted Time       : %d ms" +
                        "\n  Http Request Headers : %s" +
                        "\n  Http Response Headers: %s" +
                        "\n}",
                requestId,
                requestUri,
                requestTime,
                decryptedParameters.toString(),
                decryptedTime,
                encryptedRetModel.toString(),
                encryptedTime,
                httpRequestHeaders,
                httpResponseHeaders
        );
    }
}
