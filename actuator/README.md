# actuator 监控服务

## Actuator 介绍

Actuator 是 SpringBoot 项目中一个强大的服务监控功能，有助于对应用程序进行监视和管理，通过 restful api 请求来监管、审计、收集应用的运行情况。

Spring Boot Actuator端点通过 JMX 和HTTP 公开暴露给外界访问，大多数时候我们使用基于HTTP的Actuator端点，因为它们很容易通过浏览器、CURL命令、shell脚本等方式访问。

一些有用的执行器端点是：

- /beans：此端点返回应用程序中配置的所有bean的列表。
- /env：提供有关Spring Environment属性的信息。
- /health：显示应用程序运行状况
- /info：显示应用程序信息，我们可以在Spring环境属性中配置它。
- /mappings：显示所有 @RequestMapping 路径 的列表 。
- /shutdown：允许我们正常关闭应用程序。
- /threaddump：提供应用程序的线程转储。
- 你可以从[此处](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)获得Spring执行器端点的完整列表。

## 如何使用

### 1.pom.xml添加maven依赖

```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
```

### 2.application.yml配置

````yaml
server:
  port: 8080
spring:
  application:
    name: prometheus-test #应用名称
management:
  endpoints:
    web:
      exposure:
        include: '*'  # 设置端点暴露的哪些内容，默认["health","info"]，设置"*"代表暴露所有可访问的端点
  endpoint:
    health:
      show-details: always  # 端点健康情况，默认值"never"，设置为"always"可以显示硬盘使用情况和线程情况
    shutdown:
      enabled: true
  metrics:
    tags:
      application: ${spring.application.name}
````

### 3.找到SpringBootApplication.java并启动

在浏览器输入==>http://127.0.0.1:8080/actuator/health，会获得如下一串json数据

```json
{
    status: "UP",
    details: {
        diskSpace: {
            status: "UP",
            details: {
                total: 280247660544,
                free: 273128345600,
                threshold: 10485760
                }
        }
    }
}
```

status："UP"，表示服务正常运行状态。diskSpace表示硬盘空间等

## 自定义Endpoint

如果添加一个Endpoint类型的@Bean，Spring Boot会自动通过JMX和HTTP（如果有可用服务器）将该端点暴露出去。通过创建MvcEndpoint类型的bean可进一步定义HTTP端点，虽然该bean不是@Controller，但仍能使用@RequestMapping（和@Managed*）暴露资源。

如果你需要一个单独的管理端口或地址，你可以将注解@ManagementContextConfiguration的配置类添加到/META-INF/spring.factories中，且key为org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration，这样该端点将跟其他MVC端点一样移动到一个子上下文中，通过WebConfigurerAdapter可以为管理端点添加静态资源。


## 资料

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/actuator/README.md)
- [参考资料](https://www.kancloud.cn/george96/springboot/613541)
- [参考资料](http://www.ityouknow.com/springboot/2018/02/06/spring-boot-actuator.html)