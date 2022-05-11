package com.github.xzb617.encyrption.sample.controller;

import com.github.xzb617.encryption.autoconfigure.annotation.param.DecryptHeader;
import com.github.xzb617.encyrption.sample.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 针对 DecryptParam 注解进行测试
 * @author xzb617
 * @date 2022/5/5 15:43
 * @description:
 */
@RestController
@RequestMapping("/decryptHeader")
public class DecryptHeaderController {

    /**
     * 测试 @DecryptHeader 注解
     * @param headerKey 模拟一个请求头的key
     * @return
     */
    @GetMapping("/index")
    public Result index(@DecryptHeader String headerKey) {
        return Result.success("解密成功", String.format("headerKey: %s", headerKey));
    }

}
