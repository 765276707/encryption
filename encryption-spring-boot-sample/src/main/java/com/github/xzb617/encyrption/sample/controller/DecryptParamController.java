package com.github.xzb617.encyrption.sample.controller;

import com.github.xzb617.encryption.autoconfigure.annotation.param.DecryptParam;
import com.github.xzb617.encyrption.sample.dto.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 针对 DecryptParam 注解进行测试
 * @author xzb617
 * @date 2022/5/5 15:43
 * @description:
 */
@RestController
@RequestMapping("/decryptParam")
public class DecryptParamController {

    /**
     * 测试 @DecryptParam 注解
     * @param intKey
     * @param longKey
     * @param doubleKey
     * @param strKey
     * @param boolKey
     * @param dateKey
     * @return
     */
    @GetMapping("/index")
    public Result index( @DecryptParam(required = true, message = "intKey不能为空") Integer intKey,
                                    @DecryptParam(required = true, message = "longKey不能为空") Long longKey,
                                    @DecryptParam(required = true, message = "doubleKey不能为空") Double doubleKey,
                                    @DecryptParam(required = true, message = "strKey不能为空") String strKey,
                                    @DecryptParam(required = true, message = "boolKey不能为空") Boolean boolKey,
                                    @DecryptParam(required = true, message = "dateKey不能为空") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dateKey
                           ) {
        return Result.success("解密参数成功",
                String.format(
                    "intKey: %d, longKey: %d, doubleKey: %f, strKey: %s, boolKey: %s, dateKey: %s",
                    intKey, longKey, doubleKey, strKey, boolKey, dateKey
                )
        );
    }

}
