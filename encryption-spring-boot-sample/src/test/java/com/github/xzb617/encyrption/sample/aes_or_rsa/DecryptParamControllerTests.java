package com.github.xzb617.encyrption.sample.aes_or_rsa;

import com.github.xzb617.encryption.autoconfigure.constant.Algorithm;
import com.github.xzb617.encryption.autoconfigure.encryptor.ArgumentEncryptor;
import com.github.xzb617.encryption.autoconfigure.envirs.ResponseHeaders;
import com.github.xzb617.encryption.autoconfigure.properties.EncryptionProperties;
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

/**
 * DecryptParam 测试
 * @author xzb617
 * @date 2022/5/11 12:26
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DecryptParamControllerTests {

    private MockMvc mockMvc;

    @Resource
    private WebApplicationContext webApplicationContext;
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
        // 获取加密的请求参数
        ResponseHeaders responseHeaders = new ResponseHeaders(new HttpHeaders());
        String intKeyValue = argumentEncryptor.encrypt("1", responseHeaders);
        String longKeyValue = argumentEncryptor.encrypt("15230", responseHeaders);
        String strKeyValue = argumentEncryptor.encrypt("strKeyValue", responseHeaders);
        String dateKeyValue = argumentEncryptor.encrypt("2020-12-10 10:10:15", responseHeaders);
        String boolKeyValue = argumentEncryptor.encrypt("true", responseHeaders);
        String doubleKeyValue = argumentEncryptor.encrypt("2.123456789123", responseHeaders);

        // 模拟请求
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .get("/decryptParam/index")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                // 添加请求参数
                .param("intKey", intKeyValue)
                .param("longKey", longKeyValue)
                .param("doubleKey", doubleKeyValue)
                .param("strKey", strKeyValue)
                .param("boolKey", boolKeyValue)
                .param("dateKey", dateKeyValue)
        );

        // 解决返回值中文乱码问题
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
