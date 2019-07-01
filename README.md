# spring-boot-demo

Spring Boot 学习示例,将持续更新...

本项目基于spring boot 最新版本（2.1.5）实现

## 什么是spring-boot

Spring Boot简化了基于Spring的应用开发，通过少量的代码就能创建一个独立的、产品级别的Spring应用。 Spring Boot为Spring平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。多数Spring Boot应用只需要很少的Spring配置。

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是Spring Boot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，Spring Boot整合了所有的框架（不知道这样比喻是否合适）。

Spring Boot的核心思想就是约定大于配置，一切自动完成。采用Spring Boot可以大大的简化你的开发模式，所有你想集成的常用框架，它都有对应的组件支持。

## 目录介绍

模块名称|说明|主要内容|详细
---|---|---|---|
helloworld|spring boot 入门|spring mvc,Spring Boot项目创建,单元测试|[详细](https://github.com/smltq/spring-boot-demo/blob/master/helloworld/HELP.md)|
web|ssh项目|spring mvc,过滤器,拦截器,监视器,thymeleaf,lombok,jquery,bootstrap,mysql|[详细](https://github.com/smltq/spring-boot-demo/blob/master/web/HELP.md)|
aop|spring boot aop|aop,正则,前置通知,后置通知,环绕通知|[详细](https://github.com/smltq/spring-boot-demo/blob/master/aop/HELP.md)|
data-redis|spring boot redis|lettuce,redis,session redis,YAML配置,连接池,对象存储|[详细](https://github.com/smltq/spring-boot-demo/blob/master/data-redis/HELP.md)|
quartz|spring boot quartz|Spring Scheduler,Quartz,分布式调度,集群,高可用,可扩展性实现,mysql持久化|[详细](https://github.com/smltq/spring-boot-demo/blob/master/quartz/HELP.md)|
shiro|spring boot shiro|授权、认证、单点登录、加解密、集成oauth2|...|
