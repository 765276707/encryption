package com.github.xzb617.encryption.autoconfigure.annotation.param;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 解密请求参数
 * @author xzb617
 * @date 2022/5/4 16:41
 * @description: 支持必传校验
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    /**
     * 参数是否为必传参数
     * @return
     */
    boolean required() default false;

    /**
     * 当参数为必传参数时而未传时，抛出异常的错误消息
     * @return
     */
    String message() default "missing required parameter";

    /**
     * 默认值
     * @return
     */
    String defaultValue() default "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n";

}
