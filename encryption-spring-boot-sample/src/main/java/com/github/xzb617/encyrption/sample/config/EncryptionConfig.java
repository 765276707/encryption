package com.github.xzb617.encyrption.sample.config;

import com.github.xzb617.encryption.autoconfigure.config.EncryptionConfigure;
import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import com.github.xzb617.encryption.autoconfigure.envirs.Registry;
import com.github.xzb617.encyrption.sample.encryptor.Rc4ArgumentEncryptor;
import org.springframework.context.annotation.Configuration;

/**
 * 拓展配置
 * @author xzb617
 * @date 2022/5/7 14:46
 * @description:
 */
//@Configuration
public class EncryptionConfig extends EncryptionConfigure {

    @Override
    public void configure(Registry registry) {
        // 注册自定义加密器
        registry.registerArgumentEncryptor(new Rc4ArgumentEncryptor());
    }

    @Override
    public void configure(Configurator configurator) {
        // 开启调试模式
        configurator.enableShowDetails()
                // 禁止展示头信息
                .disableShowDetailsHeader()
                // 设置调试模式追踪的编号，在请求头中，由客户端传入，若未传则服务端会自动生成一个随机编号
                .setRequestIdKey("X-Request-Id");
    }

}
