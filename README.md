# spring-boot-demo

本项目示例基于spring boot 最新版本（2.1.7）实现，Spring Boot 学习示例,将持续更新...

## 什么是spring-boot

Spring Boot简化了基于Spring的应用开发，通过少量的代码就能创建一个独立的、产品级别的Spring应用。 Spring Boot为Spring平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。多数Spring Boot应用只需要很少的Spring配置。

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是Spring Boot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，Spring Boot整合了所有的框架。

Spring Boot的核心思想就是约定大于配置，一切自动完成。采用Spring Boot可以大大的简化你的开发模式，所有你想集成的常用框架，它都有对应的组件支持。

## 开发环境

JDK1.8 +
Maven 3.5 +
IntelliJ IDEA ULTIMATE 2019.1
mysql 5.7 +

## 模块介绍

模块名称|主要内容|详细
---|---|---|
helloworld|spring mvc,Spring Boot项目创建,单元测试|[详细](https://github.com/smltq/spring-boot-demo/blob/master/helloworld/HELP.md)|
web|ssh项目,spring mvc,过滤器,拦截器,监视器,thymeleaf,lombok,jquery,bootstrap,mysql|[详细](https://github.com/smltq/spring-boot-demo/blob/master/web/HELP.md)|
aop|aop,正则,前置通知,后置通知,环绕通知|[详细](https://github.com/smltq/spring-boot-demo/blob/master/aop/HELP.md)|
data-redis|lettuce,redis,session redis,YAML配置,连接池,对象存储|[详细](https://github.com/smltq/spring-boot-demo/blob/master/data-redis/HELP.md)|
quartz|Spring Scheduler,Quartz,分布式调度,集群,高可用,可扩展性实现,mysql持久化|[详细](https://github.com/smltq/spring-boot-demo/blob/master/quartz/HELP.md)|
shiro|授权、认证、加解密、统一异常处理|[详细](https://github.com/smltq/spring-boot-demo/blob/master/shiro/HELP.md)|
sign|防篡改、防重放、文档自动生成|[详细](https://github.com/smltq/spring-boot-demo/blob/master/sign/HELP.md)|
security|授权、认证、加解密、mybatis plus使用|[详细](https://github.com/smltq/spring-boot-demo/blob/master/security/HELP.md)|
mybatis-plus-generator|基于mybatisplus代码自动生成|...|
encoder|主流加密算法介绍、用户加密算法推荐|[详细](https://github.com/smltq/spring-boot-demo/blob/master/encoder/HELP.md)|
actuator|autuator介绍|...|
admin|可视化服务监控、使用|[详细](https://github.com/smltq/spring-boot-demo/blob/master/admin/README.md)|
security-oauth2-credentials|oath2密码模式、客户端认证模式实现|...|
security-oauth2-auth-code|基于spring boot实现oath2授权模式|...|
cloud-oauth2-auth-code|基于spring cloud实现oath2授权模式|...|

## 关于项目

有需要写关于spring boot示例的，可以给我提issue