package com.github.xzb617.encryption.autoconfigure.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.xzb617.encryption.autoconfigure.consoles.EncryptionContextInterceptor;
import com.github.xzb617.encryption.autoconfigure.core.advice.rep.EncryptResponseBodyAdvice;
import com.github.xzb617.encryption.autoconfigure.core.advice.req.DecryptRequestBodyAdvice;
import com.github.xzb617.encryption.autoconfigure.core.resolver.DecryptHeaderHandlerMethodArgumentResolver;
import com.github.xzb617.encryption.autoconfigure.core.resolver.DecryptMapHandlerMethodArgumentResolver;
import com.github.xzb617.encryption.autoconfigure.core.resolver.DecryptModelHandlerMethodArgumentResolver;
import com.github.xzb617.encryption.autoconfigure.core.resolver.DecryptParamHandlerMethodArgumentResolver;
import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import com.github.xzb617.encryption.autoconfigure.exceptions.framework.EncryptionException;
import com.github.xzb617.encryption.autoconfigure.properties.EncryptionProperties;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import com.github.xzb617.encryption.autoconfigure.serializer.impl.FastjsonEncryptionJsonSerializer;
import com.github.xzb617.encryption.autoconfigure.serializer.impl.GsonEncryptionJsonSerializer;
import com.github.xzb617.encryption.autoconfigure.serializer.impl.JacksonEncryptionJsonSerializer;
import com.github.xzb617.encryption.autoconfigure.utils.HttpMessageConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 整合Spring的相关配置
 * @author xzzb617
 * @date 2022/5/4 16:34
 * @description:
 */
@Configuration
@EnableConfigurationProperties({EncryptionProperties.class})
public class EncryptionArgumentResolversConfig implements WebMvcConfigurer {

    private final EncryptorProxyManager encryptorProxyManager;
    private final Configurator configurator;

    public EncryptionArgumentResolversConfig(EncryptorProxyManager encryptorProxyManager, Configurator configurator) {
        this.encryptorProxyManager = encryptorProxyManager;
        this.configurator = configurator;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new DecryptParamHandlerMethodArgumentResolver(encryptorProxyManager));
        resolvers.add(new DecryptHeaderHandlerMethodArgumentResolver(encryptorProxyManager));
        resolvers.add(new DecryptModelHandlerMethodArgumentResolver(encryptorProxyManager));
        resolvers.add(new DecryptMapHandlerMethodArgumentResolver(encryptorProxyManager));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (configurator.getShowDetails()) {
            registry.addInterceptor(new EncryptionContextInterceptor(configurator));
        }
    }

    @Bean
    @ConditionalOnMissingBean(EncryptionJsonSerializer.class)
    public EncryptionJsonSerializer encryptionJsonSerializer(List<HttpMessageConverter> converters) {
        HttpMessageConverter jacksonConverter = HttpMessageConverterUtil.getConverterByType(converters, MappingJackson2HttpMessageConverter.class);
        if (jacksonConverter != null) {
            return new JacksonEncryptionJsonSerializer((MappingJackson2HttpMessageConverter) jacksonConverter);
        }
        HttpMessageConverter gsonConverter = HttpMessageConverterUtil.getConverterByType(converters, GsonHttpMessageConverter.class);
        if (gsonConverter != null) {
            return new GsonEncryptionJsonSerializer((GsonHttpMessageConverter) gsonConverter);
        }
        HttpMessageConverter fastjsonConverter = HttpMessageConverterUtil.getConverterByType(converters, FastJsonHttpMessageConverter.class);
        if (fastjsonConverter != null) {
            return new FastjsonEncryptionJsonSerializer((FastJsonHttpMessageConverter) fastjsonConverter);
        }
        throw new EncryptionException("the encryption framework must be provided with an implementation instance of EncryptionJsonSerializer.");
    }

    @Bean
    public RequestBodyAdvice decryptRequestBodyAdvice() {
        return new DecryptRequestBodyAdvice(encryptorProxyManager);
    }

    @Bean
    public ResponseBodyAdvice encryptResponseBodyAdvice(List<HttpMessageConverter> converters) {
        return new EncryptResponseBodyAdvice(encryptorProxyManager, encryptionJsonSerializer(converters));
    }
}
