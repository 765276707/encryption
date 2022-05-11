package com.github.xzb617.encryption.autoconfigure.annotation;

import com.github.xzb617.encryption.autoconfigure.config.EncryptionArgumentResolversConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 开启加密功能注解
 * @author xzb617
 * @date 2022/5/4 16:32
 * @description:
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Import(value = {EncryptionArgumentResolversConfig.class})
public @interface EnableEncryption {

}
