# spring-boot-demo

 >本项目示例基于spring boot 最新版本（2.1.9）实现，Spring Boot、Spring Cloud 学习示例，将持续更新……

在基于Spring Boot、Spring Cloud 分布微服务开发过程中，根据实际项目环境，需要选择、集成符合项目需求的各种组件和积累各种解决方案。基于这样的背景下，我开源了本示例项目，方便大家快速上手Spring Boot、Spring Cloud 。

每个示例都带有详细的介绍文档、作者在使用过程中踩过的坑、解决方案及参考资料，方便快速上手为你提供学习捷径，少绕弯路，提高开发效率。

有需要写关于spring boot、spring cloud示例，可以给我提issue哦

## 项目介绍

spring boot demo 是一个Spring Boot、Spring Cloud的项目示例，根据市场主流的后端技术，共集成了30+个demo，未来将持续更新。该项目包含helloworld(快速入门)、web(ssh项目快速搭建）、aop(切面编程)、data-redis(redis缓存)、quartz(集群任务实现)、shiro（权限管理）、oauth2（四种认证模式）、shign(接口参数防篡改重放）、encoder（用户密码设计）、actuator（服务监控）、cloud-config（配置中心）、cloud-gateway（服务网关）、email（邮件发送）、cloud-alibaba（微服务全家桶）等模块

### 开发环境

- JDK1.8 +
- Maven 3.5 +
- IntelliJ IDEA ULTIMATE 2019.1
- MySql 5.7 +

### Spring Boot模块

模块名称|主要内容
---|---
helloworld|[spring mvc,Spring Boot项目创建,单元测试](https://github.com/smltq/spring-boot-demo/blob/master/helloworld/HELP.md)
web|[ssh项目,spring mvc,过滤器,拦截器,监视器,thymeleaf,lombok,jquery,bootstrap,mysql](https://github.com/smltq/spring-boot-demo/blob/master/web/HELP.md)
aop|[aop,正则,前置通知,后置通知,环绕通知](https://github.com/smltq/spring-boot-demo/blob/master/aop/HELP.md)
data-redis|[lettuce,redis,session redis,YAML配置,连接池,对象存储](https://github.com/smltq/spring-boot-demo/blob/master/data-redis/HELP.md)
quartz|[Spring Scheduler,Quartz,分布式调度,集群,mysql持久化等](https://github.com/smltq/spring-boot-demo/blob/master/quartz/HELP.md)
shiro|[授权、认证、加解密、统一异常处理](https://github.com/smltq/spring-boot-demo/blob/master/shiro/HELP.md)
sign|[防篡改、防重放、文档自动生成](https://github.com/smltq/spring-boot-demo/blob/master/sign/HELP.md)
security|[授权、认证、加解密、mybatis plus使用](https://github.com/smltq/spring-boot-demo/blob/master/security/HELP.md)
mybatis-plus-generator|[基于mybatisplus代码自动生成](https://github.com/smltq/spring-boot-demo/blob/master/mybatis-plus-generator)
mybatis-plus-crud|[基于mybatisplus实现数据库增、册、改、查](https://github.com/smltq/spring-boot-demo/blob/master/mybatis-plus-crud)
encoder|[主流加密算法介绍、用户加密算法推荐](https://github.com/smltq/spring-boot-demo/blob/master/encoder/HELP.md)
actuator|[autuator介绍](https://github.com/smltq/spring-boot-demo/blob/master/actuator/README.md)
admin|[可视化服务监控、使用](https://github.com/smltq/spring-boot-demo/blob/master/admin/README.md)
security-oauth2-credentials|[oath2实现密码模式、客户端模式](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-credentials/README.md)
security-oauth2-auth-code|[基于spring boot实现oath2授权模式](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-auth-code/README.md)
mybatis-multi-datasource|[mybatis、数据库集群、读写分离、读库负载均衡](https://github.com/smltq/spring-boot-demo/blob/master/mybatis-multi-datasource)
template-thymeleaf|[thymeleaf实现应用国际化示例](https://github.com/smltq/spring-boot-demo/blob/master/template-thymeleaf)
mq-redis|[redis之mq实现，发布订阅模式](https://github.com/smltq/spring-boot-demo/blob/master/mq-redis)
email|[email实现邮件发送](https://github.com/smltq/spring-boot-demo/blob/master/email)

### Spring Cloud 模块

模块名称|主要内容
---|---
cloud-oauth2-auth-code|[基于spring cloud实现oath2授权模式](https://github.com/smltq/spring-boot-demo/blob/master/cloud-oauth2-auth-code)
cloud-gateway|[API主流网关、gateway快速上手](https://github.com/smltq/spring-boot-demo/blob/master/cloud-gateway)
cloud-config|[配置中心(服务端、客户端)示例](https://github.com/smltq/spring-boot-demo/blob/master/cloud-config)
cloud-feign|[Eureka服务注册中心、负载均衡、声明式服务调用](https://github.com/smltq/spring-boot-demo/blob/master/cloud-feign)
cloud-hystrix|[Hystrix服务容错、异常处理、注册中心示例](https://github.com/smltq/spring-boot-demo/blob/master/cloud-hystrix)
cloud-zuul|[zuul服务网关、过滤器、路由转发、服务降级、负载均衡](https://github.com/smltq/spring-boot-demo/blob/master/cloud-zuul)
cloud-alibaba|[nacos服务中心、配置中心、限流等使用(系列示例整理中...)](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba)

### 其它

模块名称|主要内容
---|---
java-gather|[java问题收集目录](https://github.com/smltq/spring-boot-demo/blob/master/java-gather)

## Spring Boot 概述

Spring Boot简化了基于Spring的应用开发，通过少量的代码就能创建一个独立的、产品级别的Spring应用。 Spring Boot为Spring平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。多数Spring Boot应用只需要很少的Spring配置。

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是Spring Boot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，Spring Boot整合了所有的框架。

Spring Boot的核心思想就是约定大于配置，一切自动完成。采用Spring Boot可以大大的简化你的开发模式，所有你想集成的常用框架，它都有对应的组件支持。

## Spring Cloud 概述

SpringCloud是基于SpringBoot的一整套实现微服务的框架。他提供了微服务开发所需的配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等组件。最重要的是跟Spring Boot框架一起使用的话，会让你开发微服务架构的云服务非常好的方便。

## Spring Boot与Spring Cloud关系

Spring boot 是 Spring 的一套快速配置脚手架，可以基于Spring Boot 快速开发单个微服务，Spring Cloud是一个基于Spring Boot实现的云应用开发工具；Spring boot专注于快速、方便集成的单个个体，Spring Cloud是关注全局的服务治理框架；Spring Boot使用了默认大于配置的理念，很多集成方案已经帮你选择好了，能不配置就不配置，Spring Cloud很大的一部分是基于Spring Boot来实现。

Spring boot可以离开Spring Cloud独立使用开发项目，但是Spring Cloud离不开Spring Boot，属于依赖的关系。

    spring -> spring boot > spring cloud 这样的关系。

## 关于项目

- 码云仓库：[https://gitee.com/tqlin/spring-boot-demo.git](https://gitee.com/tqlin/spring-boot-demo.git)
- GitHub仓库：[https://github.com/smltq/spring-boot-demo.git](https://github.com/smltq/spring-boot-demo.git)

## 关注和交流

- 开发交流QQ群（230017570）
- 微信公众号

    ![微信公众号](http://49.235.170.100:8090/upload/2019/10/qrcode-92534a5bf579459eaea982a6bcc83e9c.jpg)