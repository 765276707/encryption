package com.github.xzb617.encyrption.sample.config;

import com.github.xzb617.encryption.autoconfigure.config.EncryptionConfigure;
import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import com.github.xzb617.encryption.autoconfigure.envirs.Registry;
import org.springframework.context.annotation.Configuration;

/**
 * 拓展配置
 * @author xzb617
 * @date 2022/5/7 14:46
 * @description:
 */
@Configuration
public class EncryptionConfig extends EncryptionConfigure {

    @Override
    public void configure(Registry registry) {
        super.configure(registry);
    }

    @Override
    public void configure(Configurator configurator) {
        super.configure(configurator);
    }

}
