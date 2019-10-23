# Spring Cloud Alibaba（二） 配置中心多项目、多配置文件、分目录实现

## 介绍

之前[Spring Cloud Config基础篇](https://github.com/smltq/spring-boot-demo/tree/master/cloud-config)这篇文章介绍了Spring Cloud Config 配置中心基础的实现，今天继续聊下Spring Cloud Config 并结合nacos做服务注册中心，实现多项目、多配置文件、按项目目录划分等功能的配置服务中心。

阅读本篇文章之前，最好要有nacos基础；关于nacos是什么，如何使用，可以参考我的上一篇文章 [Spring Cloud Alibaba（一） 如何使用nacos服务注册和发现](README1.md)，或者直接链接到官网教程[Nacos 快速开始](https://nacos.io/zh-cn/docs/quick-start.html)

## 本示例主要内容

- 采用nacos做服务注册中心，Spring Cloud Config做配置服务中心，在上一篇基础上新建了ali-nacos-config-server配置服务中心项目、ali-nacos-config-client配置客户端项目、并把ali-nacos-consumer-feign配置也调整成从配置中心加载配置
- 支持多项目，config-repo配置文件目录按项目名称来规划，在配置中心 searchPaths: /cloud-alibaba/config-repo/{application}/ 使用application自动识别查找目录
- 支持单项目多配置文件，ali-nacos-config-client项目的配置文件 spring.cloud.config.name=${spring.application.name},myconfig，通过指定多个name实现多配置文件

## 实现示例过程

### 新建ali-nacos-config-server项目

该项目用来做配置服务中心，以下贴出关键部分代码

pom.xml
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

    </dependencies>
```

application.yml
```yaml
server:
  port: 8001

spring:
  application:
    name: ali-nacos-config-server
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    config:
      server:
        git:
          #uri: https://github.com/smltq/spring-boot-demo.git
          uri: https://gitee.com/tqlin/spring-boot-demo.git
          searchPaths: /cloud-alibaba/config-repo/{application}/
          force-pull: true
```

启动类AnConfigServerApplication.java
```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class AnConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnConfigServerApplication.class, args);
    }
}
```

### 新建ali-nacos-config-client项目

该项目用来做配置中心客户端测试之一，以下贴出几处关键代码

pom.xml
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

    </dependencies>
```

bootstrap.yml
```yaml
spring:
  application:
    name: ali-nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    config:
      name: ${spring.application.name},myconfig
      uri: http://localhost:8001/ # config server 配置服务地址
      profile: ${spring.profiles.active}
      label: master
  profiles:
    active: pro                  # 配置文件版本（该示例分为test,dev,pro）
```

写个配置读取测试类HelloController.java
```java
@RestController
public class HelloController {
    @Value("${easy.hello}")
    private String hello;

    @Value("${easy.myconfig}")
    private String myconfig;

    @RequestMapping("/hello")
    public Map hello() {
        Map map = new HashMap<>();
        map.put("hello", hello);
        map.put("myconfig", myconfig);
        return map;
    }
}
```

启动类AnConfigClientApplication.java

```java
@SpringBootApplication
@EnableDiscoveryClient
public class AnConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnConfigClientApplication.class, args);
    }
}
```

### 调整ali-nacos-consumer-feign项目

以下贴出调整部分代码

pom.xml增加spring-cloud-starter-config依赖
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

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
    </dependencies>
```

yml配置文件增加bootstrap.yml，把核心配置移到该配置文件
bootstrap.yml
```yaml
spring:
  application:
    name: ali-nacos-consumer-feign
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    config:
      name: ${spring.application.name}
      uri: http://localhost:8001/ # config server 配置服务地址
      profile: ${spring.profiles.active}
      label: master
  profiles:
    active: dev                  # 配置文件版本（该示例分为test,dev,pro）
```

编写配置读写测试类HomeController.java

```java
@RestController
@Slf4j
public class HomeController {

    @Autowired
    private HelloService helloService;

    @Value("${easy.hello}")
    private String hello;

    @GetMapping(value = "/", produces = "application/json")
    public String home() {
        log.info("-----------------consumer调用开始-----------------");
        String param = "云天";
        log.info("消费者传递参数：" + param);
        String result = helloService.hello(param);
        log.info("收到提供者响应：" + result);
        return "feign消费者" + result;
    }

    @RequestMapping("/hello")
    public Map hello() {
        Map map = new HashMap<>();
        map.put("hello", hello);
        return map;
    }
}
```

### 最后放上配置文件目录规划

[config-repo配置总目录](https://gitee.com/tqlin/spring-boot-demo/tree/master/cloud-alibaba/config-repo)
[ali-nacos-config-server 项目GIT的配置目录](https://gitee.com/tqlin/spring-boot-demo/tree/master/cloud-alibaba/config-repo/ali-nacos-config-client)
[ali-nacos-consumer-feign 项目GIT的配置目录](https://gitee.com/tqlin/spring-boot-demo/tree/master/cloud-alibaba/config-repo/ali-nacos-consumer-feign)

## 使用示例

### 在上一篇基础上，我们新建了2个项目，并调整ali-nacos-consumer-feign项目使它支持配置远程读取，有以下三个项目做测试。
        
ali-nacos-config-server：配置服务中心，服务名：ali-nacos-config-server，端口：8001
ali-nacos-config-client：配置客户端1（消费端），服务名：ali-nacos-config-client，端口：8002
ali-nacos-consumer-feign：配置客户端2（消费端），服务名：ali-nacos-consumer-feign，端口：9101

### 运行测试

首先要启动服务注册中心 nacos

#### 启动ali-nacos-config-server服务，配置服务中心测试

- 访问：http://localhost:8001/ali-nacos-config-client/dev ，返回：

```json
{
    name: "ali-nacos-config-client",
    profiles: [
    "dev"
    ],
    label: null,
    version: "5456d7ca31d46e91464b6efd3a0831a8208413d9",
    state: null,
    propertySources: [ ]
}
```

- 访问：http://localhost:8001/ali-nacos-config-client/test ，返回：
```json
{
    name: "ali-nacos-config-client",
    profiles: [
    "test"
    ],
    label: null,
    version: "5456d7ca31d46e91464b6efd3a0831a8208413d9",
    state: null,
    propertySources: [ ]
}
```
这表示配置能正确从git上加载到了。

#### 启动ali-nacos-config-client服务，运行客户端测试1

- bootstrap.yml的active调成dev，访问：http://localhost:8002/hello ，返回：

```json
{
    hello: "ali-nacos-config-client 项目的 dev config",
    myconfig: "ali-nacos-config-client 项目的 myconfig config"
}
```

- bootstrap.yml的active调成test，访问：http://localhost:8002/hello ，返回：

```json
{
hello: "ali-nacos-config-client 项目的 test config",
myconfig: "ali-nacos-config-client 项目的 myconfig config"
}
```
表示我git上该项目的2个配置文件都成功读取到了。

#### 启动ali-nacos-consumer-feign项目，测试客户端测试2

访问：http://localhost:9101/hello

返回结果
```json
{
  hello: "ali-nacos-consumer-feign 项目的 dev config"
}
```
表示该项目的配置文件加载成功了

## 资料

- [Spring Cloud Alibaba 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba)
- [原文地址](README2.md)