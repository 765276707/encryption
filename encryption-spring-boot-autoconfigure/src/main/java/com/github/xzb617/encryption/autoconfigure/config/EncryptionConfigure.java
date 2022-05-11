package com.github.xzb617.encryption.autoconfigure.config;

import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import com.github.xzb617.encryption.autoconfigure.envirs.Registry;

/**
 * 拓展配置
 * @author xzb617
 * @date 2022/5/5 15:13
 * @description:
 */
public abstract class EncryptionConfigure {

    /**
     * 配置注册器
     * @param registry 注册器
     */
    public void configure(Registry registry) {};

    /**
     * 配置配置器
     * @param configurator 配置器
     */
    public void configure(Configurator configurator) {};

}
