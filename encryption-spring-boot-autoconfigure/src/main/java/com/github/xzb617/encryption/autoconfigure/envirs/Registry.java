package com.github.xzb617.encryption.autoconfigure.envirs;

import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import org.springframework.util.Assert;

/**
 * 注册器
 * @author xzb617
 * @date 2022/5/7 13:59
 * @description:
 */
public class Registry {

    /**
     * 自定义的加密器
     */
    private ArgumentEncryptor argumentEncryptor = null;

    public Registry registerArgumentEncryptor(ArgumentEncryptor argumentEncryptor) {
        Assert.notNull(argumentEncryptor, "the registered component of ArgumentEncryptor cannot be null.");
        this.argumentEncryptor = argumentEncryptor;
        return this;
    }

    public ArgumentEncryptor getArgumentEncryptor() {
        return argumentEncryptor;
    }

}
