# Spring Boot 入门

## 创建项目

    http://start.spring.io/
    
## 目录规划

    - src/main/java 程序开发以及主程序入口
    - src/main/resources 配置文件
    - src/test/java 测试程序
    
### java代码目录规划
 
 Spring Boot 建议的目录规划如下：
    
    com
      +- easy
        +- helloworld
          +- HelloworldApplication.java
          |
          +- model
          |  +- User.java
          |  +- UserRepository.java
          |
          +- service
          |  +- UserService.java
          |
          +- controller
          |  +- UserController.java
          |
          
  - 1、Application.java 建议放到根目录下面,主要用于做一些框架配置
  - 2、model 目录主要用于实体与数据访问层（Repository）
  - 3、service 层主要是业务类代码
  - 4、controller 负责页面访问控制
  
## 引入web模块
  
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    也可以在创建项目的时候直接选择web依赖
    
    spring-boot-starter ：核心模块，包括自动配置支持、日志和 YAML，如果引入了 spring-boot-starter-web web 模块可以去掉此配置，因为 spring-boot-starter-web 自动依赖了 spring-boot-starter。
    spring-boot-starter-test ：测试模块，包括 JUnit、Hamcrest、Mockito
    
## 编写代码

  Controller 内容：
     
     @RestController
     public class HelloWorldController {
         @RequestMapping("/hello")
         public String index() {
             return "Hello World";
         }
     }
     
     @RestController默认都只提供Rest风格接口返回值，针对不需要返回页面的Controller都采用RestController进行注解     

## 启动主程序
    
    打开浏览器访问 http://localhost:8080/hello，就可以看到效果了，是不是非常简单快速！
    
## 单元测试

打开的src/test/下的测试入口，编写简单的 http 请求来测试；使用 mockmvc 进行，代码如下。
 
```java
    package com.easy.helloworld;
    
    import HelloWorldController;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.http.MediaType;
    import org.springframework.test.context.junit4.SpringRunner;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;
    
    import static org.hamcrest.Matchers.equalTo;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    
    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class HelloTests {
        private MockMvc mvc;
    
        @Before
        public void setUp() throws Exception {
            mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
        }
    
        @Test
        public void getHello() throws Exception {
            mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(equalTo("Hello World")));
        }
    }
```

MockMvc使用参考官网，这里不做介绍

## 添加热启动支持

    <!--开发环境热部署插件-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    
## 总结

使用 Spring Boot 可以非常方便、快速搭建项目，使我们不用关心框架之间的兼容性，适用版本等各种问题，我们想使用任何东西，仅仅添加一个配置就可以，所以使用 Spring Boot 非常适合构建微服务。

## 资料

[示例代码-github](https://github.com/smltq/spring-boot-demo/tree/master/helloworld)
[Spring Boot应用 打包与部署教程](https://ken.io/note/springboot-package-deploy#H3-11)