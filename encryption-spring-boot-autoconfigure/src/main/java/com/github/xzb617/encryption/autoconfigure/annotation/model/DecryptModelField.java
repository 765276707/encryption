package com.github.xzb617.encryption.autoconfigure.annotation.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用来标识实体类（Model）中需要解密的属性
 * @author xzb617
 * @date 2022/5/4 16:41
 * @description:
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface DecryptModelField {
}
