package com.github.xzb617.encyrption.sample.controller;

import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptMap;
import com.github.xzb617.encryption.autoconfigure.annotation.model.DecryptModel;
import com.github.xzb617.encyrption.sample.dto.ModelEntity;
import com.github.xzb617.encyrption.sample.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xzb617
 * @date 2022/5/6 16:58
 * @description:
 */
@RestController
@RequestMapping("/decryptMap")
public class DecryptMapController {

    @PostMapping("/index")
    public Result<Map> index(@DecryptMap(keys = {"intKey", "strKey"}, required = true, message = "缺失必要的Key在请求参数Map中") Map<String, Object> params) {
        return Result.success("操作成功", params);
    }

}
