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

