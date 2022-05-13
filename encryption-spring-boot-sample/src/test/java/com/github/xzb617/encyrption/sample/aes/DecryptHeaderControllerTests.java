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
 * DecryptHeader测试
 * @author xzb617
 * @date 2022/5/11 12:26
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DecryptHeaderControllerTests {

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
        // 加密的请求头参数
        String headValue = mockEncryption.encryptValue("这是请求头");

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .get("/decryptHeader/index")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                // 添加请求头
                .header("headerKey", headValue)
        );

        // 解决返回值中文乱码问题
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
