# encryption

#### 介绍
这是一个基于SpringBoot2.x的HTTP数据传输加密框架，该框架是为了给仍在使用HTTP进行数据传输的项目提供一层有效的数据安全防护，
有效了避免重要敏感数据在传输过程中被窃取的风险，框架开箱即用，只需简单的配置就可以融入你的项目之中。


#### 特性
- 内置超过8种常用的算法或模式（对称、非对称、混合模式），基本上可以覆盖大部分项目的加密需求
- 支持如@DecryptParam、@DecryptBody等在内的丰富的注解
- 兼容Spring的Validation校验体系，可以无缝使用了其进行参数校验
- 兼容SpringBoot2.x的JSON体系，对支持Jackson、Gson直接集成，
  提供了Fastjson的直接集成类，也可拓展其他Json框架的集成，做到整个项目的Json风格的统一
- 内置调试模式，可方便查看和调试加解密时的数据，可配置详细输出类目
- 支持自定义参数加密器


#### 使用手册
详见本项目下的Wiki文档


#### Maven坐标
支持Maven依赖安装，如果中央仓库（后续会上传）没有，则需要打包编译安装到本地仓库或私服（版本号为最新版本号）：
~~~xml
<!-- 导入 starter 包-->
<dependency>
    <groupId>com.github.xzb617</groupId>
    <artifactId>encryption-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
~~~


#### 使用方法

1.  引入 encryption-spring-boot-starter
    ~~~xml
    <dependency>
        <groupId>com.github.xzb617</groupId>
        <artifactId>encryption-spring-boot-starter</artifactId>
        <version>1.x.x</version>
    </dependency>
    ~~~

2.  配置加密器使用的算法模式，其中configs的配置是根据不同算法模式有不同的配置项，参考：
    ~~~yaml
    # 加密配置
    encryption:
      charset: 'UTF-8'
      algorithm: rsa_with_aes
      # 不同算法模式有不同的配置项
      configs:
        public-key: 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5jqX3qcr0xHfYvb1xh7Th4BzOg8hFChgpp4Zcb1LTcIXiCN1FYFpZkMHbwtZzRY4M+ZLSMAKx3ExNc1XLAQsoErHIzt3RXr1PoOt+vN/YeV7r2D55LOJh3HJBT1xhf2B9Q/EAQfnYoMQ68o5NV34m3nMPtRFa5D01b9DD8i/xTAfUfWXKJ4XklFxoWQ6f3ltguZlqfsNeXdwmbcukm9NVY83/R8KJNtkelqMDYkEB0nPvM+Jxjp+BJ54Y6cG904V57Tswv/7OZg0AWOqt3TzLK3gBbpYINLIgxotwY0lFwz3NfJyYs9xmp4ZM3/+wlgbbGywz3K+9MDOziwN/vWuIwIDAQAB'
        private-key: 'MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDmOpfepyvTEd9i9vXGHtOHgHM6DyEUKGCmnhlxvUtNwheII3UVgWlmQwdvC1nNFjgz5ktIwArHcTE1zVcsBCygSscjO3dFevU+g636839h5XuvYPnks4mHcckFPXGF/YH1D8QBB+digxDryjk1Xfibecw+1EVrkPTVv0MPyL/FMB9R9ZconheSUXGhZDp/eW2C5mWp+w15d3CZty6Sb01Vjzf9Hwok22R6WowNiQQHSc+8z4nGOn4Ennhjpwb3ThXntOzC//s5mDQBY6q3dPMsreAFulgg0siDGi3BjSUXDPc18nJiz3Ganhkzf/7CWBtsbLDPcr70wM7OLA3+9a4jAgMBAAECggEABRDQ+qMvAavpAGJysfNHsDmRT3u5vJnO1puv76K8n29f2Sz+jISWbxuWdEkJpsuQXQP6MBWZpx3EeWyVOHC3EqfSjeHnE+5Kqx92moj1CpBkEk3N8cxJgGNuiuD5OHuFeoDoTSWBX9eGbcm7TINOzwz1A4TkKiO8X1+f+B7wqUQFHN7gzVzPp8bKlM/YnY9f7zR8/dXpi8fpu3hW1U75OF7nxZCxfEN6YvSbkzCLUuOqnTSLm4kx/A7QVD/wXZlv08AmFlD5l1k+G+uzzHi+5+LaZRKUCnAt0I3wzNDim1O4ytQs1q5Sh0cc5vDsxF4R8PyO3RPTVQ/r8x9JXaYgUQKBgQD3qUzDis92xg3vCPWbAQ2NRPklzpQw3rFhobTiRafRHXVegwOLKG7tQ95s6KAmgLY+UzkQATXg5XgFJ6b4S9xBCJvJN/o1k9+y+PlOVjMddnL7ZcOuA0nEMY2lj+mmAFul7TQGsndXFnjPwTXDpL+EvbFjChMSEkqiWmCVtrRpTQKBgQDt+wkbS9s6ymi6aSIH/NNJ8pQYCFWSzlqPnGPi6K+aKSqDHP+dWf03OYD/lGDJCFp9urD2BurHgbc/NNn01QWjqXzwr8KugsbNhRNrneBNnFpxmQtqYPVEGX4Wy2/Cg0iNjAiwEFwmTeKA05uXjrZb6N+L+LxGHS3qlJib+js9LwKBgA0b9AlBtruVvjUR51Y+FwaMSRfcOHHhx8fgNF/pyflCsuy+yJg8GqsKdaKUKa4AECV6aEHVnpF59AFp2Oe5tD3pA74B7YeafTPPA/tGiswbcfimqVXzrJrq+JFz7a0wxakhxig4mCKO+PQeSQdDGDQhilswtFO3jiXL3OLv2drdAoGACKRxNDiRAZWQMBTZU4ju82SH1EeZM/eiekyno/nnRqXwEUrgTYqTE4pXEPEGgsZ24tIA0y51IEGpsfXtZGLIDaV+EA+R9lxxc809Y08ccjUXY8C3FWnn/k0esx04Ncwmul03g41Ui1+QtjT5FYvtO3E9jQu/apxsqnQzBpcTx6kCgYB3HlWpJzevkl2m0I+YlxDHPR0ec/MBM/cr7x3ebjm+2sbYmPkza+AfclZ1mc5yxza/WDixLbsT3TrffYV7zKHP5Ggq0k5jZlIiVkU9GVUIb0BApyMNXCvEmV9esQcmIKzozxaC3EqhM0cy4j8Vi2j5CvZmlwMvBeOSHjeT+MSpTw=='
        secret: 'ABCDEFGHIJKL0123'
        iv: '1234567890123456'
    ~~~
3.  在启动类或配置类上添加开启注解 @EnableEncryption
    ~~~java
    @EnableEncryption
    @SpringBootApplication
    public class EncryptionSpringBootSampleApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(EncryptionSpringBootSampleApplication.class, args);
        }
    
    }
    ~~~
4.  控制台打印出下面的信息就代表，框架可以正常使用了
    ~~~shell script
    2022-xx-xx 23:07:39.782  INFO 14864 --- [           main] c.g.x.e.a.EncryptionAutoConfiguration    : Encryption has take effective successfully
    ~~~
  
    
#### 入门案例
~~~java
@RestController
public class ParamController {

    /**
     * 测试 @DecryptParam 注解
     * @param data 数据
     * @param id 编号
     * @return
     */
    @GetMapping("/method01")
    public Result method01(@DecryptParam(required = true, message = "数据不能为空") String data,
                           @DecryptParam(required = true, message = "编号不能为空") Integer id) {
        return Result.success("解密成功");
    }

    /**
     * 测试 @DecryptHeader 注解
     * @param headerKey 模拟一个请求头的key
     * @return
     */
    @GetMapping("/method02")
    public Result method02(@DecryptHeader String headerKey) {
        return Result.success("解密成功");
    }
    
}
~~~

更多的使用说明详见使用手册