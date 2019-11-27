# Spring Cloud Alibaba（四）实现Dubbo服务消费

本项目演示如何使用 Spring Cloud Alibaba 完成 Dubbo 的RPC调用。

## Spring Cloud与Dubbo

- Spring Cloud是一套完整的微服务架构方案

- Dubbo是国内目前非常流行的服务治理与RPC实现方案

由于Dubbo在国内有着非常大的用户群体，但是其周边设施与组件相对来说并不那么完善（比如feign，ribbon等等）。很多开发者使用Dubbo，又希望享受Spring Cloud的生态，因此也会有一些Spring Cloud与Dubbo一起使用的案例与方法出现。

Spring Cloud Alibaba的出现，实现了Spring Cloud与Dubbo的完美融合。在之前的教程中，我们已经介绍过使用Spring Cloud Alibaba中的Nacos来作为服务注册中心，并且在此之下可以如传统的Spring Cloud应用一样地使用Ribbon或Feign来实现服务消费。这篇，我们就来继续说说Spring Cloud Alibaba 下额外支持的RPC方案：Dubbo

## 代码实现

我们通过一个简单的例子，使用Nacos做服务注册中心，利用Dubbo来实现服务提供方与服务消费方。这里省略Nacos的安装与使用，下面就直接进入Dubbo的使用步骤。

### 定义 Dubbo 服务接口

创建 ali-nacos-dubbo-api 工程

Dubbo 服务接口是服务提供方与消费方的远程通讯契约，通常由普通的 Java 接口（interface）来声明，如 HelloService 接口：

```java
public interface HelloService {
    String hello(String name);
}
```

为了确保契约的一致性，推荐的做法是将 Dubbo 服务接口打包在jar包中，如以上接口就存放在 ali-nacos-dubbo-api 之中。 对于服务提供方而言，不仅通过依赖 artifact 的形式引入 Dubbo 服务接口，而且需要将其实现。对应的服务消费端，同样地需要依赖该 artifact， 并以接口调用的方式执行远程方法。接下来进一步讨论怎样实现 Dubbo 服务提供方和消费方。

### 实现 Dubbo 服务提供方

创建 ali-nacos-dubbo-provider，端口：9001 工程

pom.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud-alibaba</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <modelVersion>4.0.0</modelVersion>

    <artifactId>ali-nacos-dubbo-provider</artifactId>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>

        <!-- API -->
        <dependency>
            <groupId>com.easy</groupId>
            <artifactId>ali-nacos-dubbo-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <!--dubbo-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-dubbo</artifactId>
        </dependency>

        <!--nacos-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

bootstrap.yaml配置
```yaml
dubbo:
  scan:
    # dubbo 服务扫描基准包
    base-packages: com.easy.andProvider.service

  #Dubbo 服务暴露的协议配置，其中子属性 name 为协议名称，port 为协议端口（ -1 表示自增端口，从 20880 开始）
  protocol:
    name: dubbo
    port: -1

  #Dubbo 服务注册中心配置，其中子属性 address 的值 “spring-cloud://localhost”，说明挂载到 Spring Cloud 注册中心
  registry:
    address: spring-cloud://localhost

spring:
  application:
    # Dubbo 应用名称
    name: ali-nacos-dubbo-provider
  main:
    allow-bean-definition-overriding: true
  cloud:
    # Nacos 服务发现与注册配置
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

> Dubbo Spring Cloud 继承了 Dubbo Spring Boot 的外部化配置特性，也可以通过标注 @DubboComponentScan 来实现基准包扫描。

#### 实现 Dubbo 服务

HelloService 作为暴露的 Dubbo 服务接口，服务提供方 ali-nacos-dubbo-provider 需要将其实现：

```java
package com.easy.andProvider.service;

import com.easy.and.api.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "你好 " + name;
    }
}
```

> import org.apache.dubbo.config.annotation.Service 是 Dubbo 服务注解，仅声明该 Java 服务实现为 Dubbo 服务

贴上启动类代码：

```java
@EnableDiscoveryClient
@EnableAutoConfiguration
public class AndProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AndProviderApplication.class);
    }
}
```

### 实现 Dubbo 服务消费方

创建 ali-nacos-dubbo-consumer，端口：9103 工程

pom.xml依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud-alibaba</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <modelVersion>4.0.0</modelVersion>

    <artifactId>ali-nacos-dubbo-consumer</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>com.easy</groupId>
            <artifactId>ali-nacos-dubbo-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-dubbo</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

yaml配置文件
```yaml
dubbo:
  registry:
    address: spring-cloud://localhost
  cloud:
    subscribed-services: ali-nacos-dubbo-provider

spring:
  application:
    name: ali-nacos-dubbo-consumer
  main:
    allow-bean-definition-overriding: true
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

#### 实现 Dubbo 服务消费方

HomeController.java
```java
package com.easy.andConsumer;

import com.easy.and.api.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Reference
    HelloService helloService;

    @GetMapping("/hello")
    public String hello(String name) {
        return helloService.hello("云天");
    }
}
```

AndConsumerApplication.java启动类
```java
package com.easy.andConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AndConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AndConsumerApplication.class, args);
    }
}
```

## 使用示例

### 示例关联项目

本示例我们创建了三个项目实现

- ali-nacos-dubbo-api：定义Dubbo服务接口工程
        
- ali-nacos-dubbo-provider：Dubbo服务提供方并向nacos注册服务，服务名：ali-nacos-dubbo-provider，端口：9001

- ali-nacos-dubbo-consumer：Dubbo服务消费方并向nacos注册服务，服务名：ali-nacos-dubbo-consumer，端口：9103

### 运行示例测试

首先要启动服务注册中心 nacos、ali-nacos-dubbo-provider服务及ali-nacos-dubbo-consumer服务

- 访问服务消费方地址： http://localhost:9103/hello

返回
```json
你好 云天
```

或者你也可以通过 curl 命令执行 HTTP GET 方法

```cfml
$curl http://127.0.0.1:9103/hello
```

HTTP 响应为：

```cfml
你好 云天
```

以上结果说明应用 ali-nacos-dubbo-consumer 通过消费 Dubbo 服务，返回服务提供方 ali-nacos-dubbo-provider 运算后的内容。

以上我们完成了 Dubbo 服务提供方和消费方的入门运用，源代码请直接参考模块：

- [ali-nacos-dubbo-provider](https://github.com/smltq/spring-boot-demo/tree/master/cloud-alibaba/ali-nacos-dubbo-provider)
- [ali-nacos-dubbo-consumer](https://github.com/smltq/spring-boot-demo/tree/master/cloud-alibaba/ali-nacos-dubbo-consumer)

### 资料

- [Spring Cloud Alibaba 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba)
- [原文地址](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba/README4.md)