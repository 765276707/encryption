package com.github.xzb617.encyrption.sample.aes_with_rsa;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.constant.SecretMocks;
import com.github.xzb617.encryption.autoconfigure.constant.SymmetricConfigKey;
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
        // 判断是否为 RsaWithAes 算法
        Algorithm algorithm = encryptionProperties.getAlgorithm();
        if (!Algorithm.RSA_WITH_AES.equals(algorithm)) {
            throw new IllegalArgumentException("当前的算法不是本测试用力指定的算法：RsaWithAes");
        }
    }

    @Test
    public void mock() throws Exception {
        // 构造请求参数，序列化后加密
        String jsonEntity = getSerializeModelEntity();
        // 模拟一个对称密钥的值
        String secret = "ABCDEFGHIJKL0123";
        // 加密请求参数
        ResponseHeaders responseHeaders = new ResponseHeaders(new HttpHeaders());
        responseHeaders.addHeader(SecretMocks.MOCK_SECRET_KEY, secret);
        String content = argumentEncryptor.encrypt(jsonEntity, responseHeaders);
        // 获取加密后的请求头中由服务端RSA公钥加密的Aes密钥的KEY
        String secretKeyInHeader = encryptionProperties.getConfigs().getOrDefault(SymmetricConfigKey.SECRET_KEY_IN_HEADER, "secret");
        // 获取加密后的请求头中由服务端RSA公钥加密的Aes密钥，用来模拟客户端反向操作（为了方便演示，服务端客户端的公钥私钥均一致）
        String encrypedSecret = responseHeaders.getHeader(secretKeyInHeader);

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post("/body/index")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                // 添加header
                .header(secretKeyInHeader, encrypedSecret)
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
