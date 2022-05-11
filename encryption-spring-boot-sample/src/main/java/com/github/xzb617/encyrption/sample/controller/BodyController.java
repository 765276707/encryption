package com.github.xzb617.encyrption.sample.controller;

import com.github.xzb617.encryption.autoconfigure.annotation.body.DecryptBody;
import com.github.xzb617.encyrption.sample.dto.ModelEntity;
import com.github.xzb617.encyrption.sample.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzb617
 * @date 2022/5/6 17:51
 * @description:
 */
@RestController
@RequestMapping("/body")
public class BodyController {

    //@EncryptBody
    @PostMapping("/index")
    public Result<Object> index(@DecryptBody @RequestBody ModelEntity modelEntity) {
        // 返回结果
        return Result.success("操作成功", modelEntity);
    }

}
