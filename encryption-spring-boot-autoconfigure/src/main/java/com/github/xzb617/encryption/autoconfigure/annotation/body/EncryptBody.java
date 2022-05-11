package com.github.xzb617.encryption.autoconfigure.annotation.body;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 加密响应体的内容
 * @author xzb617
 * @date 2022/5/4 16:38
 * @description: 配合 @ResponseBody 使用
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface EncryptBody {

    /**
     * 如果指定了要加密的字段，则只对该字段进行加密处理
     * @return
     */
    String[] encryptFields() default {};

}
