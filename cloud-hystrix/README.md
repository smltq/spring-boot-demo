# Spring Cloud Hystrix 容错保护

本示例主要介绍 Spring Cloud 系列中的 Eureka，如何使用Hystrix熔断器容错保护我们的应用程序。

在微服务架构中，系统被拆分成很多个服务单元，各个服务单元的应用通过 HTTP 相互调用、依赖，在某个服务由于网络或其他原因自身出现故障、延迟时，调用方也会出现延迟。若调用方请求不断增加，可能会形成任务积压，最终导致调用方服务瘫痪，服务不可用现象逐渐放大。

## 解决方案

Spring Cloud Hystrix 是一个专用于服务熔断处理的开源项目，实现了一系列服务保护措施，当依赖的服务方出现故障不可用时，hystrix实现服务降级、服务熔断等功能，对延迟和故障提供强大的容错能力，从而防止故障进一步扩大。

## Hystrix 主要作用介绍

- 保护和控制底层服务的高延迟和失效对上层服务的影响。
- 避免复杂分布式中服务失效的雪崩效应。在大型的分布式系统中，存在各种复杂的依赖关系。如果某个服务失效，很可能会对其他服务造成影响，形成连锁反应。
- 快速失效和迅速恢复。以Spring为例，一般在实现controller的时候，都会以同步的逻辑调用依赖的服务。如果服务失效，而且没有客户端失效机制，就会导致请求长时间的阻塞。如果不能快速的发现失效，而就很难通过高可用机制或者负载均衡实现迅速的恢复。
- 实现服务降级。这一点是从用户体验来考虑的，一个预定义默认返回会比请求卡死或者500好很多。
- 实现了服务监控、报警和运维控制。Hystrix Dashboard和Turbine可以配合Hystrix完成这些功能。

### Hystrix 主要特性：

- 服务熔断

    Hystrix 会记录各个服务的请求信息，通过 成功、失败、拒绝、超时 等统计信息判断是否打开断路器，将某个服务的请求进行熔断。一段时间后切换到半开路状态，如果后面的请求正常则关闭断路器，否则继续打开断路器。

- 服务降级

    服务降级是请求失败时的后备方法，故障时执行降级逻辑。

- 线程隔离

    Hystrix 通过线程池实现资源的隔离，确保对某一服务的调用在出现故障时不会对其他服务造成影响。
    
## 代码实现

创建三个项目来完成示例，分别为：服务注册中心hystrix-eureka-server，服务提供者hystrix-service-provider，服务消费者hystrix-service-consumer

### 1.创建hystrix-eureka-server服务注册中心

#### pom.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.easy</groupId>
    <artifactId>hystrix-eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>hystrix-eureka-server</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <artifactId>cloud-hystrix</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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

#### application.yml配置文件

```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  instance:
    hostname: localhost   # eureka 实例名称
  client:
    register-with-eureka: false # 不向注册中心注册自己
    fetch-registry: false       # 是否检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/  # 注册中心访问地址
```

#### HystrixEurekaServerApplication.java启动类

```java
package com.easy.eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HystrixEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixEurekaServerApplication.class, args);
    }
}
```

### 2.创建hystrix-service-provider服务提供者

#### pom.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.easy</groupId>
    <artifactId>hystrix-service-provider</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>hystrix-service-provider</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <artifactId>cloud-hystrix</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
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

#### application.yml配置文件

```yaml
spring:
  application:
    name: hystrix-service-provider

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  # 实例一
server:
  port: 8081
```

#### HelloController.java提供一个hello接口

````java
package com.easy.serviceProvider.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(@RequestParam String p1, @RequestParam String p2) throws Exception {
//      用来测试服务超时的情况
//      int sleepTime = new Random().nextInt(2000);
//      System.out.println("hello sleep " + sleepTime);
//      Thread.sleep(sleepTime);
        return "hello, " + p1 + ", " + p2;
    }
}
````

#### 最后贴上启动类HystrixServiceProviderApplication.java

```java
package com.easy.serviceProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HystrixServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixServiceProviderApplication.class, args);
    }
}

```

### 3.创建hystrix-service-consumer服务消费者

#### pom.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.easy</groupId>
    <artifactId>hystrix-service-consumer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>hystrix-service-consumer</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <artifactId>cloud-hystrix</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <dependencies>
        <!-- eureka 客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- ribbon -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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

#### application.yml配置文件

```yaml
spring:
  application:
    name: hystrix-eureka-server
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000 # 默认超时时间
```

#### 相关代码

异常处理类NotFallbackException.java
```java
package com.easy.serviceConsumer.exception;

public class NotFallbackException extends Exception {
}
```

服务层HelloService.java
```java
package com.easy.serviceConsumer.service;

import com.easy.serviceConsumer.exception.NotFallbackException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    private static final String HELLO_SERVICE = "http://hystrix-service-provider/";

    @HystrixCommand(fallbackMethod = "helloFallback", ignoreExceptions = {NotFallbackException.class}
            , groupKey = "hello", commandKey = "str", threadPoolKey = "helloStr")
    public String hello(String p1, String p2) {
        return restTemplate.getForObject(HELLO_SERVICE + "hello?p1=" + p1 + "&p2=" + p2, String.class);
    }

    private String helloFallback(String p1, String p2, Throwable e) {
        System.out.println("class: " + e.getClass());
        return "error, " + p1 + ", " + p2;
    }
}
```

控制器ConsumerController.java
```java
package com.easy.serviceConsumer.web;

import com.easy.serviceConsumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @GetMapping("hello")
    public String hello(@RequestParam String p1, @RequestParam String p2) {
        System.out.println("hello");
        return helloService.hello(p1, p2);
    }
}
```

#### 4.启动类HystrixServiceConsumerApplication.java

```java
package com.easy.serviceConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringCloudApplication
public class HystrixServiceConsumerApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixServiceConsumerApplication.class, args);
    }
}
```

## 使用示例

分别运行3个服务，HystrixEurekaServerApplication.java（服务注册中心）,HystrixServiceProviderApplication.java（服务提供者）,HystrixServiceConsumerApplication.java（服务消费者）

- 1.访问 http://localhost:8080/hello?p1=a&p2=b ，正常情况下响应为 hello, a, b
- 2.关闭 hystrix-service-provider 或在 sleepTime 超过 1000ms 时，访问 http://localhost:8080/hello?p1=a&p2=b，执行降级逻辑，返回 error, a, b

## 资料

- [Spring Cloud Hystrix 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-hystrix)
- [Spring Boot、Spring Cloud示例学习](https://github.com/smltq/spring-boot-demo)
- [Hystrix源码](https://github.com/Netflix/Hystrix)