package com.github.xzb617.encryption.autoconfigure.serializer;

/**
 * Json 序列化器
 * @author xzb617
 * @date 2022/5/4 18:28
 * @description:
 */
public interface EncryptionJsonSerializer {

    /**
     * 序列化
     * @param obj 对象
     * @return String
     */
    public String serialize(Object obj);

    /**
     * 反序列化
     * @param jsonStr json字符串
     * @param type 反序列化的类型
     * @return T
     */
    public <T> T deserialize(String jsonStr, Class<T> type);

}
