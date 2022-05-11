package com.github.xzb617.encryption.autoconfigure.annotation.param;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 解密请求头参数
 * @author xzb617
 * @date 2022/5/4 16:41
 * @description: 支持必传校验
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptHeader {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    /**
     * 是否必传
     * @return
     */
    boolean required() default true;

    /**
     * 当参数为必传参数时而未传时，抛出异常的错误消息
     * @return
     */
    String message() default "missing required header";

    /**
     * 默认值
     * @return
     */
    String defaultValue() default "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n";

}
