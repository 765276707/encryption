package com.github.xzb617.encryption.autoconfigure.factories;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import org.springframework.beans.factory.BeanCreationException;
import java.lang.reflect.Constructor;

/**
 * 加密器工厂
 * @author xzb617
 * @date 2022/5/5 14:59
 * @description:
 */
public class EncryptorFactory {

    /**
     * 生成内置的加密器
     * @param algorithm 选择的算法
     * @return
     */
    public static ArgumentEncryptor delegateInternalEncryptor(Algorithm algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("No Algorithm applied");
        }

        String clazzRef = algorithm.getValue();
        return delegateInternalEncryptor(clazzRef);
    }

    /**
     * 根据类全路径生成加密器
     * @param clazzRef 加密器类全路径
     * @return
     */
    public static ArgumentEncryptor delegateInternalEncryptor(String clazzRef) {
        if (clazzRef == null) {
            throw new IllegalArgumentException("No Algorithm applied");
        }

        try
        {
            Class<?> algClazz = Class.forName(clazzRef);
            Constructor<?> constructor = algClazz.getDeclaredConstructor();
            return (ArgumentEncryptor) constructor.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new BeanCreationException(String.format(" [%s] can not be found, check this class path or it exists.", clazzRef));
        }
        catch (Exception e)
        {
            throw new BeanCreationException("DesensitizeEncoder bean init failure.", e);
        }
    }
}
