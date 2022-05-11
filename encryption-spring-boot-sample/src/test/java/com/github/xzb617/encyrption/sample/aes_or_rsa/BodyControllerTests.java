package com.github.xzb617.encyrption.sample.aes_or_rsa;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encryption.autoconfigure.properties.EncryptionProperties;
import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import com.github.xzb617.encyrption.sample.dto.ModelEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Body测试
 * @author xzb617
 * @date 2022/5/11 14:39
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BodyControllerTests {

    private MockMvc mockMvc;

    @Resource
    private WebApplicationContext webApplicationContext;
    @Resource
    private EncryptionJsonSerializer jsonSerializer;
    @Resource
    private ArgumentEncryptor argumentEncryptor;
    @Resource
    private EncryptionProperties encryptionProperties;

    @Before
    public void init() {
        // 实例化
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // 判断是否为 Aes 算法
        Algorithm algorithm = encryptionProperties.getAlgorithm();
        if (!Algorithm.AES.equals(algorithm)) {
            throw new IllegalArgumentException("当前的算法不是本测试用力指定的算法：Aes");
        }
    }

    @Test
    public void mock() throws Exception {
        // 构造请求参数，序列化后加密
        ModelEntity entity = new ModelEntity();
        entity.setIntKey(1);
        entity.setLongKey(1658613L);
        entity.setStrKey("这是字符串参数");
        entity.setDateKey(new Date());
        String strJson = jsonSerializer.serialize(entity);
        ResponseHeaders responseHeaders = new ResponseHeaders(new HttpHeaders());
        String content = argumentEncryptor.encrypt(strJson, responseHeaders);

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post("/body/index")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加body
                .content(content)
        );

        // 解决返回值中文乱码问题
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
