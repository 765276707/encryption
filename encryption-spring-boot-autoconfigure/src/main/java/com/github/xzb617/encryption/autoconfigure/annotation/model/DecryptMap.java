package com.github.xzb617.encryption.autoconfigure.annotation.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 对请求中的Map类型参数进行解密
 * @author xzb617
 * @date 2022/5/4 16:40
 * @description: 需要指定要解密的keys，否则默认不进行解密操作
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface DecryptMap {

    /**
     * 需要进行解密的 key
     * @return
     */
    String[] keys() default {};

    /**
     * 参数是否为必传参数
     * @return
     */
    boolean required() default false;

    /**
     * 当参数为必传参数时而未传时，抛出异常的错误消息
     * @return
     */
    String message() default "";

}
