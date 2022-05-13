package com.github.xzb617.encyrption.sample.aes_with_rsa;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.encryptor.mixed.RsaWithAesArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.mock.MockEncryption;
import com.github.xzb617.encryption.autoconfigure.serializer.EncryptionJsonSerializer;
import com.github.xzb617.encyrption.sample.dto.ModelEntity;
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
    private MockEncryption mockEncryption;

    @Resource
    private WebApplicationContext webApplicationContext;
    @Resource
    private EncryptionJsonSerializer jsonSerializer;


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
        // 序列化请求体
        String jsonEntity = getSerializeModelEntity();
        // 模拟一个对称密钥的值
        String mockSecretValue = "ABCDEFGHIJKL0123";

        // 创建一个 Mock 实例，获取加密后的请求头中由服务端RSA公钥加密的Aes密钥，用来模拟客户端反向操作（为了方便演示，服务端客户端的公钥私钥均一致）
        String secretKeyInHeader   = mockEncryption.getSecretKeyInHeader();
        String secretValueInValue  = mockEncryption.encryptedHeaderSecret(mockSecretValue);

        String content             = mockEncryption.encryptValue(jsonEntity, mockSecretValue);

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post("/body/index")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加header，应与 setSecretKeyInHeader() 赋的值一致
                .header(secretKeyInHeader, secretValueInValue)
                // 添加body
                .content(content)
        );

        // 解决返回值中文乱码问题
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 模拟一个请求实体的序列化字符串
     * @return
     */
    private String getSerializeModelEntity() {
        ModelEntity entity = new ModelEntity();
        entity.setIntKey(1);
        entity.setLongKey(1658613L);
        entity.setStrKey("这是字符串参数");
        entity.setDateKey(new Date());
        return jsonSerializer.serialize(entity);
    }

}
