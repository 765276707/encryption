package com.github.xzb617.encryption.autoconfigure.encryptor;

import com.github.xzb617.encryption.autoconfigure.envirs.AlgorithmEnvironments;

/**
 * 获取配置能力
 * @author xzb617
 * @date 2022/5/5 11:36
 * @description:
 */
public interface ArgumentConfigable {

    /**
     * 初始化配置项
     * @param environments 环境变量
     */
    public void initConfig(AlgorithmEnvironments environments);

}
