# Spring Cloud Alibaba（一） 如何使用nacos服务注册和发现

## Nacos介绍

Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。

Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施。

### Nacos 的关键特性

- 服务发现和服务健康监测
- 动态配置服务，带管理界面，支持丰富的配置维度。
- 动态 DNS 服务
- 服务及其元数据管理

## Nacos下载及部署

官方介绍文档：[Nacos 快速开始](https://nacos.io/zh-cn/docs/quick-start.html)或者直接下载zip包，部署[下载](https://github.com/alibaba/nacos/releases)

### windows环境部署过程遇到的问题汇总

#### jdk版本要求

一定要注意，jdk版本要求 64bit JDK 1.8+

#### 运行报错

运行startup.cmd，一闪而过。打开startup.cmd脚本，在最后一行添加 pause 使报错不会立即结束方便查看报错信息，这时会发现以下报错信息：

```cfml
Exception in thread "main" java.lang.UnsupportedClassVersionError: org/springfra
mework/boot/loader/PropertiesLauncher : Unsupported major.minor version 52.0
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:800)
        at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:14
2)
        at java.net.URLClassLoader.defineClass(URLClassLoader.java:449)
        at java.net.URLClassLoader.access$100(URLClassLoader.java:71)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:361)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:355)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(URLClassLoader.java:354)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:425)
        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:308)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:358)
        at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:482)
```

产生以上问题的原因是我电脑上有些老项目使用jdk 1.7，所以项目是jdk1.7和jdk1.8交叉着使用。在cmd里查看版本 java -version  输出然后是1.8.0_211（只会输出最高的版本）

#### 解决方案  

- 配置java_home，增加jdk1.8环境变量，怎么添加环境变量需要自行百度，以下贴出我的java环境变量

```cfml
C:\Users\Administrator>set java_home
JAVA_HOME=D:\Program Files\Java\jdk1.7.0_71

C:\Users\Administrator>set java8_home
JAVA8_HOME=D:\Program Files\Java\jdk1.8.0_201

C:\Users\Administrator>
```

- 调整startup.cmd脚本，使用java8_home变量，使它能正确调用jdk1.8而非jdk1.7，以下是调整部分代码

```cfml
if not exist "%JAVA8_HOME%\bin\java.exe" echo Please set the JAVA8_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA8_HOME%\bin\java.exe"
```

这里只做个替换，把原来的JAVA_HOME规划成JAVA8_HOME，运行startup.cmd，此时能正确运行Nacos服务了

### 查看界面

启动成功，在浏览器上访问：http://localhost:8848/nacos ，会跳转到登陆界面，默认的登陆用户名为nacos，密码也为nacos。

登陆成功后，就可以操作管理界面了

## 使用Nacos服务注册和发现

要使用nacos，需要在pom.xml添加必要的依赖
```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR3</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### 服务注册与发现示例代码创建

在本案例中，使用3个服务注册到Nacos上，分别为服务提供者ali-nacos-provider和负载均衡ribbon消费者ali-nacos-consumer-ribbon、申明式服务调用feign消费者ali-nacos-consumer-feign。

什么是ribbon和feign，及使用示例这里不重复介绍，需要了解可以[查看示例](https://github.com/smltq/spring-boot-demo/blob/master/cloud-feign)

#### 创建服务提供者ali-nacos-provider

pom.xml添加nacos依赖
```xml
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
```

application.yml配置
```yaml
server:
  port: 9000  #指定为9000端口

spring:
  application:
    name: ali-nacos-provider  #服务名
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848 #服务注册地址(nacos默认为8848端口)

management:
  endpoints:
    web:
      exposure:
        include: '*'
```

启动类增加 @EnableDiscoveryClient 注解
```java
package com.easy.aliNacosProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AliNacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliNacosProviderApplication.class, args);
    }
}
```

写个hello服务接口 HelloController.java

```java
package com.easy.aliNacosProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
    @GetMapping(value = "/hello/{str}", produces = "application/json")
    public String hello(@PathVariable String str) {
        log.info("-----------收到消费者请求-----------");
        log.info("收到消费者传递的参数：" + str);
        String result = "我是服务提供者，见到你很高兴==>" + str;
        log.info("提供者返回结果：" + result);
        return result;
    }
}
```

#### 创建ribbon服务消费者

pom.xml增加nocos和ribbon依赖
```xml
    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

    </dependencies>
```

application.yml
```yaml
server:
  port: 9100

spring:
  application:
    name: ali-nacos-consumer-ribbon
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

服务调用HomeController.java

```java
package com.easy.ancRibbon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/", produces = "application/json")
    public String home() {
        log.info("-----------------consumer调用开始-----------------");
        String param = "云天";
        log.info("消费者传递参数：" + param);
        String result = restTemplate.getForObject("http://ali-nacos-provider/hello/" + param, String.class);
        log.info("收到提供者响应：" + result);
        return "ribbon消费者，" + result;
    }
}

```

启用类AncRibbonConsumerApplication.java
```java
package com.easy.ancRibbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class AncRibbonConsumerApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AncRibbonConsumerApplication.class, args);
    }

}
```

#### 创建feign服务消费者

pom.xml
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
```

application.yml
```yaml
server:
  port: 9101

spring:
  application:
    name: ali-nacos-consumer-feign
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
  main:
    allow-bean-definition-overriding: true  #允许一样的beanName
```
这里有个坑要注意下，如果allow-bean-definition-overriding为设置为true,运行会报如下错：

```cfml
错误： Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
```
这里有问题的详细介绍[问题原因](https://github.com/alibaba/nacos/issues/452)


申请服务HelloService.java
```java
package com.easy.ancFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ali-nacos-provider")
public interface HelloService {

    @RequestMapping(path = "hello/{str}")
    String hello(@RequestParam("str") String param);

}
```

服务调用HomeController.java
```java
package com.easy.ancFeign;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/", produces = "application/json")
    public String home() {
        log.info("-----------------consumer调用开始-----------------");
        String param = "云天";
        log.info("消费者传递参数：" + param);
        String result = helloService.hello(param);
        log.info("收到提供者响应：" + result);
        return "feign消费者" + result;
    }
}

```

启动类AncFeignConsumerApplication.java
```java
package com.easy.ancFeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.easy.ancFeign"})
public class AncFeignConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AncFeignConsumerApplication.class, args);
    }
}
```

## 使用示例

### 现有三个项目如下
        
ali-nacos-provider：服务提供者1，服务名：ali-nacos-provider，端口：9000
ali-nacos-consumer-ribbon：ribbon服务消费者，服务名：ali-nacos-consumer-ribbon，端口：9100
ali-nacos-consumer-feign：feign消费者，服务名：ali-nacos-consumer-feign，端口：9101

### 运行测试

首先要启动服务注册中心 nacos
其次，分别启动ali-nacos-provider、ali-nacos-consumer-ribbon、ali-nacos-consumer-feign三个服务

- 访问地址：http://localhost:9100/，返回：ribbon消费者，我是服务提供者，见到你很高兴==>云天，说明ribbon消费成功了。
- 访问地址：http://localhost:9101/，返回：feign消费者我是服务提供者，见到你很高兴==>云天，说明feign消费费成功了。
- 访问地址：http://localhost:9000/hello/yuntian，返回：我是服务提供者，见到你很高兴==>yuntian，说明服务提供者访问成功了(PS:服务提供者一般是不对外公开的，怎么隐蔽接口将在接下去的文章里介绍)
- 访问地址：http://localhost:8848/nacos/#/login，输入用户：nacos，密码：nacos。进入服务管理界面，在服务管理-服务列表里可以看到我们运行的三个服务了。

## 资料

- [Spring Cloud Alibaba 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba)
- [Nacos官网](https://nacos.io/zh-cn/)