package com.github.xzb617.encryption.autoconfigure.annotation.body;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标识响应实体
 * @author xzb617
 * @date 2022/5/6 18:09
 * @description: 可以指定要加密的字段
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface EncryptBodyModel {

    /**
     * 如果指定了要加密的字段，则只对该字段进行加密处理
     * @return
     */
    String[] encryptFields() default {};

}
