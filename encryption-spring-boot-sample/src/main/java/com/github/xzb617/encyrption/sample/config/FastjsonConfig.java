//package com.github.xzb617.encyrption.sample.config;
//
//import com.alibaba.fastjson.serializer.SerializeConfig;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.serializer.ToStringSerializer;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
//import com.github.xzb617.encryption.autoconfigure.serializer.impl.FastjsonEncryptionJsonSerializer;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author xzb617
// * @date 2022/5/6 10:47
// * @description:
// */
//@Configuration
//public class FastjsonConfig implements WebMvcConfigurer {
//
//    @Bean
//    public HttpMessageConverter fastJsonHttpMessageConverter() {
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        // FastJson全局自定义配置...
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(
//                // 是否输出值为null的字段
//                //SerializerFeature.WriteMapNullValue,
//                // 数组为null.输出[]
//                SerializerFeature.WriteNullListAsEmpty,
//                // 这里防止BigDecimal可能出现的精度丢失
//                SerializerFeature.WriteBigDecimalAsPlain,
//                // 时间格式化
//                SerializerFeature.WriteDateUseDateFormat,
//                // 防止循环引用
//                SerializerFeature.DisableCircularReferenceDetect);
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        SerializeConfig serializeConfig = config.getSerializeConfig();
//        serializeConfig.put(Long.class, ToStringSerializer.instance);
//        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
//        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
//
//        // 新版本的 fastjson 必须设置 MediaType
//        List<MediaType> supportedMediaTypes = new ArrayList<>();
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
//        supportedMediaTypes.add(MediaType.IMAGE_GIF);
//        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//
//        fastJsonHttpMessageConverter.setFastJsonConfig(config);
//        return fastJsonHttpMessageConverter;
//    }
//
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(fastJsonHttpMessageConverter());
//    }
//
//}
