package com.github.xzb617.encryption.autoconfigure.annotation.body;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 解密请求体参数
 * @author xzb617
 * @date 2022/4/3 1:22
 * @description: 针对 @RequestBody 的加密参数进行解密
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface DecryptBody {
}
