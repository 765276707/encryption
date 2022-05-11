package com.github.xzb617.encryption.autoconfigure;

import com.github.xzb617.encryption.autoconfigure.config.EncryptionArgumentResolversConfig;
import com.github.xzb617.encryption.autoconfigure.config.EncryptionConfigure;
import com.github.xzb617.encryption.autoconfigure.constant.Orders;
import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;
import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import com.github.xzb617.encryption.autoconfigure.envirs.Registry;
import com.github.xzb617.encryption.autoconfigure.exceptions.framework.InstanceBeanException;
import com.github.xzb617.encryption.autoconfigure.factories.EncryptorFactory;
import com.github.xzb617.encryption.autoconfigure.proxy.EncryptorProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import java.lang.reflect.Constructor;

/**
 * 核心类，自动装配
 * @author xzb617
 * @date 2022/5/4 12:28
 * @description: Encryption基于Spring的自动装配
 */
public class EncryptionAutoConfiguration implements BeanFactoryPostProcessor, EnvironmentAware, Ordered {

    private final Logger LOGGER = LoggerFactory.getLogger(EncryptionAutoConfiguration.class);

    /**
     * 配置环境
     */
    private ConfigurableEnvironment configurableEnvironment;

    /**
     * 注入Spring容器的默认Bean名称
     */
    private final static String ENCRYPTOR_BEAN_NAME = "argumentEncryptor";
    private final static String CONFIGURATOR_BEAN_NAME = "configurator";
    private final static String ENCCRYPTOR_PROXY_BEAN_NAME = "encryptorProxyManager";


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        // 自动化的配置
        AlgorithmEnvironments environments = new AlgorithmEnvironments(configurableEnvironment);

        // 自定义配置类
        ArgumentEncryptor argumentEncryptor = null;
        final Registry registry = new Registry();
        final Configurator configurator = new Configurator();
        String[] beanNames = factory.getBeanNamesForType(EncryptionConfigure.class);
        int configBeanCount = beanNames.length;
        if (configBeanCount > 0) {
            if (configBeanCount > 1) {
                throw new InstanceBeanException("Multiple encryption config bean in your configurations are not allowed");
            }
            for (String beanName : beanNames)
            {
                BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
                if (beanDefinition != null)
                {
                    this.postAndInvokeEncryptionConfigAndRegistry(registry, configurator, beanDefinition.getBeanClassName());
                }
            }
            argumentEncryptor = registry.getArgumentEncryptor();
        }

        // 根据算法生成加密器
        if (argumentEncryptor == null) {
            argumentEncryptor = EncryptorFactory.delegateInternalEncryptor(environments.getAlgorithm());
        }

        // 初始化注册（必须）
        argumentEncryptor.initConfig(environments);
        factory.registerSingleton(ENCRYPTOR_BEAN_NAME, argumentEncryptor);

        // 注册加密器代理类
        factory.registerSingleton(CONFIGURATOR_BEAN_NAME, configurator);
        factory.registerSingleton(ENCCRYPTOR_PROXY_BEAN_NAME, new EncryptorProxyManager(argumentEncryptor, configurator));

        // 验证框架的各种组件是否启用
        if (hasBeanDefinition(factory, EncryptionArgumentResolversConfig.class)) {
            LOGGER.info("Encryption has take effective successfully");
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.configurableEnvironment = (ConfigurableEnvironment) environment;
    }

    @Override
    public int getOrder() {
        return Orders.PRECEDENCE_ENCRYPTION_AUTO_CONFIGURATION;
    }

    private boolean hasBeanDefinition(ConfigurableListableBeanFactory factory, Class<?> beanType) {
        return factory.getBeanNamesForType(beanType).length > 0;
    }

    private void postAndInvokeEncryptionConfigAndRegistry(Registry registry, Configurator configurator, String configClazz) {
        try
        {
            // 实例化指定配置类
            Class<?> algClazz = Class.forName(configClazz);
            Constructor<?> constructor = algClazz.getDeclaredConstructor();
            EncryptionConfigure encryptionConfig = (EncryptionConfigure) constructor.newInstance();
            // 执行自定义配置方法
            encryptionConfig.configure(registry);
            encryptionConfig.configure(configurator);
        }
        catch (ClassNotFoundException e) {
            throw new InstanceBeanException(String.format(" [%s] can not be found, check EncryptionConfigure class path or it exists.", configClazz));
        }
        catch (Exception e) {
            throw new InstanceBeanException(e.getMessage(), e);
        }
    }
}
