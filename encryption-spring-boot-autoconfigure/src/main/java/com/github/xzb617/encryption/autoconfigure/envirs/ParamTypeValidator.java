package com.github.xzb617.encryption.autoconfigure.envirs;

import com.github.xzb617.encryption.autoconfigure.utils.CollectionUtil;
import org.springframework.lang.NonNull;
import java.util.Set;

/**
 * 参数类型校验器
 * @author xzb617
 * @date 2022/5/5 13:20
 * @description: 校验参数类型是否受支持
 */
public class ParamTypeValidator {

    /**
     * 可以进行数据加密解密的参数类型集合
     */
    private static final Set<String> SUPPORTED_PARAM_TYPES = CollectionUtil.toSet(new String[]{"String", "Character", "Integer", "Long", "Byte",
            "Float", "Double", "Short", "Boolean", "Date", "LocalDate", "LocalDateTime"});


    /**
     * 是否是受支持的参数类型
     * @param paramType 参数类型
     * @return
     */
    public static boolean support(@NonNull Class<?> paramType) {
        return support(paramType.getSimpleName());
    }


    /**
     * 是否是受支持的参数类型
     * @param paramTypeName 参数类型名
     * @return
     */
    public static boolean support(String paramTypeName) {
        return SUPPORTED_PARAM_TYPES.contains(paramTypeName);
    }

}
