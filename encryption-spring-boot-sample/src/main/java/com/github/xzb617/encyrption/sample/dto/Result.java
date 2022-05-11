package com.github.xzb617.encyrption.sample.dto;


import com.github.xzb617.encryption.autoconfigure.annotation.body.EncryptBodyModel;

/**
 * 统一的响应数据模型
 * @author xzb617
 * @date 2022/4/8 14:07
 * @description:
 */
@EncryptBodyModel(encryptFields = {"data"})
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 执行成功
     * @return
     */
    public static Result success(String message) {
        return new Result<>(200, message, null);
    }

    /**
     * 执行成功
     * @param data 数据
     * @param <T> 数据类型
     * @return
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 执行失败
     * @return
     */
    public static Result failure(String message) {
        return new Result<>(100, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
