# spring-boot-demo

本项目示例基于spring boot 最新版本（2.1.7）实现，Spring Boot、Spring Cloud 学习示例，将持续更新……

在基于Spring Boot、Spring Cloud  微服务开发过程中，根据实际项目环境，需要选择及集成符合项目需求的各种组件。而如何使用这些组件，需要网络上查找各种资源、对比、过滤整理，然后再引入项目极其费精力和时间。基于这样的背景下，我开源了本示例项目，方便大家快速上手Spring Boot、Spring Cloud 。

每个示例都带有详细的介绍文档、作者在使用过程中踩过的坑、解决方案及参考资料，方便你快速上手提供学习捷径，少绕弯路，提高开发效率。

有需要写关于spring boot、spring cloud示例的，可以给我提issue

## Spring Boot 概述

Spring Boot简化了基于Spring的应用开发，通过少量的代码就能创建一个独立的、产品级别的Spring应用。 Spring Boot为Spring平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。多数Spring Boot应用只需要很少的Spring配置。

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是Spring Boot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，Spring Boot整合了所有的框架。

Spring Boot的核心思想就是约定大于配置，一切自动完成。采用Spring Boot可以大大的简化你的开发模式，所有你想集成的常用框架，它都有对应的组件支持。

## Spring Cloud 概述

SpringCloud是基于SpringBoot的一整套实现微服务的框架。他提供了微服务开发所需的配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等组件。最重要的是跟Spring Boot框架一起使用的话，会让你开发微服务架构的云服务非常好的方便。

## 开发环境

- JDK1.8 +
- Maven 3.5 +
- IntelliJ IDEA ULTIMATE 2019.1
- MySql 5.7 +

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
mybatis-plus-generator|基于mybatisplus代码自动生成|[详细](https://github.com/smltq/spring-boot-demo/blob/master/mybatis-plus-generator)|
mybatis-plus-crud|基于mybatisplus实现数据库增、册、改、查|[详细](https://github.com/smltq/spring-boot-demo/blob/master/mybatis-plus-crud)|
encoder|主流加密算法介绍、用户加密算法推荐|[详细](https://github.com/smltq/spring-boot-demo/blob/master/encoder/HELP.md)|
actuator|autuator介绍|[详细](https://github.com/smltq/spring-boot-demo/blob/master/actuator/README.md)|
admin|可视化服务监控、使用|[详细](https://github.com/smltq/spring-boot-demo/blob/master/admin/README.md)|
security-oauth2-credentials|oath2实现密码模式、客户端模式|[详细](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-credentials/README.md)|
security-oauth2-auth-code|基于spring boot实现oath2授权模式|[详细](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-auth-code/README.md)|
cloud-oauth2-auth-code|基于spring cloud实现oath2授权模式|[详细](https://github.com/smltq/spring-boot-demo/blob/master/cloud-oauth2-auth-code)|
cloud-gateway|API主流网关、gateway快速上手|[详细](https://github.com/smltq/spring-boot-demo/blob/master/cloud-gateway)|
cloud-config|配置中心(服务端、客户端)示例|[详细](https://github.com/smltq/spring-boot-demo/blob/master/cloud-config)|
mybatis-multi-datasource|mybatis、数据库集群、读写分离、读库负载均衡|[详细](https://github.com/smltq/spring-boot-demo/blob/master/mybatis-multi-datasource)|

## 关于项目

- 码云仓库：[https://gitee.com/tqlin/spring-boot-demo.git](https://gitee.com/tqlin/spring-boot-demo.git)
- GitHub仓库：[https://github.com/smltq/spring-boot-demo.git](https://github.com/smltq/spring-boot-demo.git)

## 关注和交流

- 开发交流QQ群（230017570）
- 微信公众号

    ![微信公众号](qrcode.jpg)
