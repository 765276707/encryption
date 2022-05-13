package com.github.xzb617.encryption.autoconfigure.mock;

/**
 * 密钥模拟常量
 * @author xzb617
 * @date 2022/5/11 15:20
 * @description:
 */
public class MockConstant {

    /**
     * 响应头中如果包含该值，则对称算法的secret则会确定为该key的值，而不会有Secret工厂去生成
     */
    public static final String MOCK_SECRET_KEY = "Mock-Secret";

}
