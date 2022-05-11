package com.github.xzb617.encryption.autoconfigure.annotation.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 对请求中类型为实体类（Model）的参数进行解密
 * @author xzb617
 * @date 2022/5/4 16:40
 * @description: 该注解需配合 @DecryptModelField 注解使用
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface DecryptModel {
}
