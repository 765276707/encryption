package com.github.xzb617.encyrption.sample.aes;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.encryptor.symmetric.AesArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.mock.MockEncryption;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
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

/**
 * DecryptModel 测试
 * @author xzb617
 * @date 2022/5/11 12:26
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DecryptModelControllerTests {

    private MockMvc mockMvc;
    private MockEncryption mockEncryption;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        // 实例化
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockEncryption = MockEncryption.configurableEnvironmentContextSetup(new AesArgumentEncryptor(), (ConfigurableEnvironment) webApplicationContext.getEnvironment());
        // 判断是否为用例要求的算法
        if (!Algorithm.AES.equals(this.mockEncryption.getAlgorithm())) {
            throw new IllegalArgumentException("本测试用例要求采用算法模式为 AES，您尚未配置该算法");
        }
    }


    @Test
    public void mock() throws Exception {
        // 获取加密的请求参数
        String intKeyValue  = mockEncryption.encryptValue("1");
        String longKeyValue = mockEncryption.encryptValue("15230");
        String strKeyValue  = mockEncryption.encryptValue("strKeyValue");
        String dateKeyValue = mockEncryption.encryptValue("2020-12-10 10:10:15");

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post("/decryptModel/index")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                // 添加参数
                .param("intKey",  intKeyValue)
                .param("longKey", longKeyValue)
                .param("strKey",  strKeyValue)
                .param("dateKey", dateKeyValue)
        );

        // 解决返回值中文乱码问题
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
