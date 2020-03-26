# spring-boot-boilerplate

该仓库提供一个干净、整洁、安全的 spring boot 项目的模板，保持规范的代码风格和单元测试。

## 环境要求

- 安装 JDK
- 推荐使用 IntelliJ IDEA 并安装 lombok 插件

## 启动项目

- 进入 `env` 目录
- 通过 docker 启动 MySQL 和 Redis `docker-compose --file ./env/local/docker-compose.yml up`
- 进入根目录，通过 gradle 启动项目 `./gradlew bootRun`

## 主要特性

- Spring boot actuator 用于健康检查
- JPA 基本配置
- flyway 数据库迁移工具
- lombok 简化 getter/setter 样板代码
- Pagination 分页
- Bean validation 基本数据验证
- Password Hash
- git hook 用于提交代码前自我检查
- checkstyle
- findbugs

## Focus on 

- 自定义注解

## 参考文档 

这套技术栈涉及的官方文档，推荐按顺序阅读

- Java 8 https://docs.oracle.com/javase/8/docs/
- Spring https://docs.spring.io/autorepo/docs/spring-framework/5.0.0.M1/spring-framework-reference
- Spring Boot https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
- Mysql https://dev.mysql.com/doc/refman/5.7/en/
- Spring security https://docs.spring.io/spring-security/site/docs/5.0.6.RELEASE/reference/htmlsingle
- Junit 4 https://junit.org/junit4/
- flyway https://flywaydb.org/
- modelmapper http://modelmapper.org/
- gradle https://docs.gradle.org/current/userguide/userguide.html
- spring-data https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
- jwt https://jwt.io/introduction/
- DDD_Reference_2015 https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf
- checkstyle https://checkstyle.org/xref/index.html

### 代码架构方式

主要参考下层，唯一不认同的是组件划分的方式，每层的分包逻辑肯定不一定样

![](https://upload-images.jianshu.io/upload_images/4099-368086f81e5fa319.png?imageMogr2/auto-orient/strip|imageView2/2/format/webp)

单体系统在逻辑上和微服务是一致的，因此可以对单体良好的架构，可以对未来的架构拆分起到作用。

分层参考：

- 接入层 
    - 关心视图和对外的服务，Restful、页面渲染、websocket、XMPP 连接等
    - 如果我们没有多种接入方式，可以和应用层合并
    - 对应到分布式系统中的网关、BFF、前台等概念
    - 只允许接入异常，例如数据校验，对应状态码 400、415 等
    - 一个应用可以由多个接入层
    - 按照连接方式分包
- 应用层 
    - 关心处理完一个完整的业务
    - 不关心请求从何处来，但是关心谁来、做什么、有没有权限做
    - 集成不同的领域模型解决问题
    - 最终一致性事务放到这层
    - 对应到分布式系统中的中台等概念
    - 基础设施的 interface 放到这层
    - 读模型放到这层，对应到分布式系统就是搜索引擎
    - 方法权限控制放到这层
    - 应用异常，403、401
    - 按照应用分包
- 领域层
    - 不关心业务，关心模型
    - 不关心谁来，不关心做什么，只关心对自己模型状态是否有影响
    - 强事务放到这层
    - 对应到分布式系统中的 domain service、后台等概念
    - 聚合根之间不互相依赖，依赖由应用层编排
    - 只允许产生业务规则异常，用户退款条件不满足，对应状态码 412 等
    - 对象权限放到这层（比如只允许删除自己创建的商品）
    - 按照你聚合分包
- 基础设施层
    - 关心存储、通知和外部设施
    - 一般由 ORM 提供
    - 基础设施异常，对应状态码 500
    - 按照 adapter 分包
    
分层部署的代价和收益，每层的部署关系：

- 单体
    - 所有层部署到一起
    - 收益为部署简单、无分布式事务问题，用户权限简单
    - 代价为无法解耦
    - 适合小型工程，无需适配多端、多类型应用
    - 无需分布式鉴权
- 小型服务化项目
    - 应用层 + 接口层部署，例如 app端、用户端、boss 系统
    - 领域层、基础设施层部署到一起，例如订单中心、用户中心
    - 收益为可以一定程度上解耦
    - 一定程度上无需处理分布式事务
    - 接口层和应用层使用 OAuth 分布式鉴权
- 大型中台项目
    - 接口层单独部署
    - 应用层单独部署
    - 领域层和基础设施层部署到一起
    - 接口层和应用层使用 OAuth 分布式鉴权
    - 应用层和领域层使用 AK/SK 服务间鉴权
- 超大型项目
    - 接口层单独部署
    - 应用层单独部署
    - 领域层单独部署
    - 基础设施层部署
    - 接口层和应用层使用 OAuth 分布式鉴权
    - 应用层和领域层使用 AK/SK 服务间鉴权
    - 领域层和基础设施层 使用 AK/SK 服务间鉴权

## 参考项目

- DDD 例子 https://gitee.com/xingfly/DDD_JPA_In_Action
- onemall https://github.com/YunaiV/onemall
