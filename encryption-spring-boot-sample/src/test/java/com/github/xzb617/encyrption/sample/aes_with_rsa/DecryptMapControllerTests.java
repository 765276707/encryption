package com.github.xzb617.encyrption.sample.aes_with_rsa;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.encryptor.mixed.RsaWithAesArgumentEncryptor;
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
 * DecryptMap测试
 * @author xzb617
 * @date 2022/5/11 12:26
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DecryptMapControllerTests {

    private MockMvc mockMvc;
    private MockEncryption mockEncryption;

    @Resource
    private WebApplicationContext webApplicationContext;


    @Before
    public void init() {
        // 实例化
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockEncryption = MockEncryption.configurableEnvironmentContextSetup(new RsaWithAesArgumentEncryptor(), (ConfigurableEnvironment) webApplicationContext.getEnvironment());
        // 判断是否为用例要求的算法
        if (!Algorithm.RSA_WITH_AES.equals(this.mockEncryption.getAlgorithm())) {
            throw new IllegalArgumentException("本测试用例要求采用算法模式为 RsaWithAes，您尚未配置该算法");
        }
    }

    @Test
    public void mock() throws Exception {
        // 模拟一个对称密钥的值
        String mockSecretValue = "ABCDEFGHIJKL0123";
        // 创建一个 Mock 实例，获取加密后的请求头中由服务端RSA公钥加密的Aes密钥，用来模拟客户端反向操作（为了方便演示，服务端客户端的公钥私钥均一致）
        String secretKeyInHeader   = mockEncryption.getSecretKeyInHeader();
        String secretValueInValue  = mockEncryption.encryptedHeaderSecret(mockSecretValue);

        String intKeyValue  = mockEncryption.encryptValue("1", mockSecretValue);
        String strKeyValue  = mockEncryption.encryptValue("strKeyValue", mockSecretValue);

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post("/decryptMap/index")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                // 添加header
                .header(secretKeyInHeader, secretValueInValue)
                // 添加参数
                .param("intKey", intKeyValue)
                .param("longKey", "66")
                .param("strKey", strKeyValue)
        );

        // 解决返回值中文乱码问题
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
