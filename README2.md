# encryption

#### 介绍
这是一个基于SpringBoot2.x的HTTP数据传输加密框架，该框架是为了给仍在使用HTTP进行数据传输的项目提供一层有效的数据安全防护，
有效了避免重要敏感数据在传输过程中被窃取的风险，框架开箱即用，只需简单的配置就可以融入你的项目之中。

#### 特性
- 内置超过8种常用的算法或模式（对称、非对称、混合模式），基本上可以覆盖大部分项目的加密需求
- 支持如@DecryptParam、@DecryptBody等在内的丰富的注解
- 兼容SpringMVC的Validation校验体系，可以无缝使用了其进行参数校验
- 兼容SpringBoot2.x的JSON框架体系，默认集成了Jackson、Gson、Fastjson，也可拓展其他Json框架的集成
- 内置调试模式，可方便查看和调试加解密时的数据，可配置详细输出类目
- 支持自定义参数加密器
- 新增Mock功能，支持快速模拟请求数据进行测试

#### 下个版本前瞻
预计新增：
- 响应字段值脱敏
- 请求体的只解密部分字段的方式

预计优化：
- 优化反射性能

#### 使用手册
[使用手册](https://gitee.com/xu_zhibin/encryption/wikis/%E7%94%A8%E5%89%8D%E5%BF%85%E8%AF%BB)


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