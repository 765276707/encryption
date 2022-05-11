package com.github.xzb617.encryption.autoconfigure.envirs;

import java.util.HashMap;

/**
 * 加密器算法配置
 * @author xzb617
 * @date 2022/5/4 18:26
 * @description:
 */
public class AlgorithmConfigs extends HashMap<String, String> {

    public Integer getInt(String key) {
        return Integer.parseInt(this.get(key));
    }

    public Long getLong(String key) {
        return Long.parseLong(this.get(key));
    }

}
