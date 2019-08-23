# spring-cloud-config 配置中心实现

Spring Cloud Config 用于为分布式系统中的基础设施和微服务应用提供集中化的外部配置支持，分为server端和client端。
server端为分布式配置中心，是一个独立的微服务应用；client端为分布式系统中的基础设置或微服务应用，通过指定配置中心来管理相关的配置。
Spring Cloud Config 构建的配置中心，除了适用于 Spring 构建的应用外，也可以在任何其他语言构建的应用中使用。
Spring Cloud Config 默认采用 Git 存储配置信息，支持对配置信息的版本管理。

## 本示例主要内容

- 配置中心演示client端和server端实现
- 配置文件放在git(因github有时候不太稳定，我放到了国内服务器)
- 版本切换（test、pro、dev）

## Spring Cloud Config 特点

- 提供server端和client端支持(Spring Cloud Config Server和Spring Cloud Config Client);
- 集中式管理分布式环境下的应用配置;
- 基于Spring环境，实现了与Spring应用无缝集成;
- 可用于任何语言开发的程序;
- 默认实现基于Git仓库(也支持SVN)，从而可以进行配置的版本管理；同时也支持配置从本地文件或数据库读取。

## 代码构建

### server端实现

#### 1.pom.xml添加maven依赖

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
    </dependencies>
```

#### 2.application.yml配置

```yaml
server:
  port: 8001
spring:
  application:
    name: cloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/tqlin/spring-boot-demo.git #因为国内github不稳定，我这里改到了码云仓
          searchPaths: /cloud-config/config-repo/           #配置文件目录
          force-pull: true
```

#### 3.CloudConfigServerApplication.java启动类

```java
@EnableConfigServer
@SpringBootApplication
public class CloudConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudConfigServerApplication.class, args);
    }
}
```

### client端实现

#### 1.pom.xml添加maven依赖

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
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
```

#### 2.bootstrap.properties配置文件

```properties
spring.cloud.config.name=easy-config
spring.cloud.config.profile=test
spring.cloud.config.uri=http://localhost:8001/
spring.cloud.config.label=master
```

- spring.application.name：对应{application}部分
- spring.cloud.config.profile：对应{profile}部分
- spring.cloud.config.label：对应git的分支。如果配置中心使用的是本地存储，则该参数无用
- spring.cloud.config.uri：配置中心的具体地址(sever端地址)
- spring.cloud.config.discovery.service-id：指定配置中心的service-id，便于扩展为高可用配置集群。

    特别注意：Spring Cloud 构建于 Spring Boot 之上，在 Spring Boot 中有两种上下文，一种是 bootstrap, 另外一种是 application, bootstrap 是应用程序的父上下文，也就是说 bootstrap 加载优先于 applicaton。bootstrap 主要用于从额外的资源来加载配置信息，还可以在本地外部配置文件中解密属性。这两个上下文共用一个环境，它是任何Spring应用程序的外部属性的来源。bootstrap 里面的属性会优先加载，它们默认也不能被本地相同配置覆盖。

#### 3.application.properties配置文件

```properties
spring.application.name=cloud-config-client
server.port=8002
```

## 运行示例

### 1.首先在码云上面创建一个文件夹config-repo用来存放配置文件，我们创建以下三个配置文件：
      
      // 开发环境
      easy-config-dev.properties    内容为：easy.hello=dev config
      // 测试环境
      easy-config-test.properties   内容为：easy.hello=test config
      // 生产环境
      easy-config-pro.properties    内容为：easy.hello=pro config
      
根据上面构建的代码指定的项目地址为：https://gitee.com/tqlin/spring-boot-demo.git  目录为： /cloud-config/config-repo/

### 2.分别运行server端和client端

找到CloudConfigServerApplication.java、CloudConfigClientApplication.java分别运行

### 3.测试server端

直接访问：http://localhost:8001/easy-config/dev

我们看到成功返回了开发配置文件信息

```json
{
name: "easy-config",
profiles: [
"dev"
],
label: null,
version: "6053b4c1c2343ac27e822b2a9b60c6343be72f96",
state: null,
propertySources: [
{
name: "https://gitee.com/tqlin/spring-boot-demo.git/cloud-config/config-repo/easy-config-dev.properties",
source: {
easy.hello: "dev config"
}
}
]
}
```

访问：http://localhost:8001/easy-config/test、http://localhost:8001/easy-config/pro，相应的会返回测试及正式环境的配置

仓库中的配置文件会被转换成web接口，访问可以参照以下的规则：

- /{application}/{profile}[/{label}]
- /{application}-{profile}.yml
- /{label}/{application}-{profile}.yml
- /{application}-{profile}.properties
- /{label}/{application}-{profile}.properties

以easy-config-dev.properties为例子，它的application是easy-config，profile是dev。client会根据填写的参数来选择读取对应的配置。

### 4.测试client端

访问：http://localhost:8002/hello  我们发现界面成功返回了 test config，说明测试配置文件client端读取成功了

我们修改bootstrap.properties配置的spring.cloud.config.profile的值为dev，重启client端，访问：http://localhost:8002/hello 这时候界面返回 dev config，表示开发配置访问成功。

## 资料

- [Spring Cloud Config 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-config)
- [官网文档](https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html)