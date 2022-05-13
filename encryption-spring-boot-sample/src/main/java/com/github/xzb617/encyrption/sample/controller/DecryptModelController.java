package com.github.xzb617.encyrption.sample.controller;

import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptModel;
import com.github.xzb617.encyrption.sample.dto.ModelEntity;
import com.github.xzb617.encyrption.sample.dto.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xzb617
 * @date 2022/5/6 16:58
 * @description:
 */
@RestController
@RequestMapping("/decryptModel")
public class DecryptModelController {


    @PostMapping("/index")
    public Result index(@Validated @DecryptModel ModelEntity modelEntity) {
        // 返回结果
        return Result.success("操作成功", modelEntity);
    }
}
