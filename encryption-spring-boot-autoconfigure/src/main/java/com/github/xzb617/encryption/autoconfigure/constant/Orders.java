package com.github.xzb617.encryption.autoconfigure.constant;

import org.springframework.core.Ordered;

/**
 * 框架内所有Spring Bean的排序
 * @author xzb617
 * @date 2022/5/4 14:13
 * @description:
 */
public class Orders {

    /**
     * 自动
     */
    public final static int PRECEDENCE_ENCRYPTION_AUTO_CONFIGURATION = Ordered.HIGHEST_PRECEDENCE + 99;

}
