# Spring Cloud Alibaba（五）RocketMQ 异步通信实现

本文探讨如何使用 RocketMQ Binder 完成 Spring Cloud 应用消息的订阅和发布。

## 介绍

[RocketMQ](https://rocketmq.apache.org/) 是一款开源的分布式消息系统，基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务，广泛应用于多个领域，包括异步通信解耦、企业解决方案、金融支付、电信、电子商务、快递物流、广告营销、社交、即时通信、移动应用、手游、视频、物联网、车联网等。

RocketMQ 是阿里巴巴在2012年开源的分布式消息中间件，目前已经捐赠给 Apache 软件基金会，并于2017年9月25日成为 Apache 的顶级项目。作为经历过多次阿里巴巴双十一这种“超级工程”的洗礼并有稳定出色表现的国产中间件，以其高性能、低延时和高可靠等特性近年来已经也被越来越多的国内企业使用。

### RocketMQ特点

- 是一个队列模型的消息中间件，具有高性能、高可靠、高实时、分布式等特点
- Producer、Consumer、队列都可以分布式
- Producer 向一些队列轮流发送消息，队列集合称为 Topic，Consumer 如果做广播消费，则一个 Consumer 实例消费这个 Topic 对应的所有队列，如果做集群消费，则多个 Consumer 实例平均消费这个 Topic 对应的队列集合
- 能够保证严格的消息顺序
- 支持拉（pull）和推（push）两种消息模式
- 高效的订阅者水平扩展能力
- 实时的消息订阅机制
- 亿级消息堆积能力
- 支持多种消息协议，如 JMS、OpenMessaging 等
- 较少的依赖

## Spring Cloud Stream

Spring Cloud Stream 是一个构建消息驱动微服务的框架。

Spring Cloud Stream 提供了消息中间件配置的统一抽象，推出了 pub/sub，consumer groups，semantics，stateful partition 这些统一的模型支持。

Spring Cloud Stream 核心构件有：Binders、Bindings和Message，应用程序通过 inputs 或者 outputs 来与 binder 交互，通过我们配置来 binding ，而 binder 负责与中间件交互，Message为数据交换的统一数据规范格式。

- Binding: 包括 Input Binding 和 Output Binding。

Binding 在消息中间件与应用程序提供的 Provider 和 Consumer 之间提供了一个桥梁，实现了开发者只需使用应用程序的 Provider 或 Consumer 生产或消费数据即可，屏蔽了开发者与底层消息中间件的接触。

- Binder: 跟外部消息中间件集成的组件，用来创建 Binding，各消息中间件都有自己的 Binder 实现。

比如 `Kafka` 的实现 `KafkaMessageChannelBinder`，`RabbitMQ` 的实现 `RabbitMessageChannelBinder` 以及 `RocketMQ` 的实现 `RocketMQMessageChannelBinder`。

- Message：是 Spring Framework 中的一个模块，其作用就是统一消息的编程模型。

比如消息 Messaging 对应的模型就包括一个消息体 Payload 和消息头 Header。

[spring-cloud-stream 官网](https://spring.io/projects/spring-cloud-stream)

## Window搭建部署RocketMQ 

### 下载

[当前最新版本为4.6.0](http://mirror.bit.edu.cn/apache/rocketmq/4.6.0/)

下载出来解压到：D:\rocketmq 目录，目录最好不要带空格和太深，否则服务运行可能会报错

### 启动NameServer服务

在启动之前需要配置系统环境，不然会报错。

```cfml
Please set the ROCKETMQ_HOME variable in your environment! 
```

系统环境变量名：ROCKETMQ_HOME

根据你解压的目录配置环境变量，比如我的变量值为：D:\rocketmq

进入window命令窗口，进入D:\rocketmq\bin目录下，执行

```cfml
start mqnamesrv.cmd
```

如上则NameServer启动成功。使用期间，窗口不要关闭。

### 启动Broker服务

进入bin目录下，输入

```cfml
start mqbroker.cmd -n localhost:9876
```

如上的 ip+port 是rocketmq的服务地址和端口。

运行如上命令，可能会报如下错误。找不到或无法加载主类

如果出此情况，打开bin-->runbroker.cmd，修改%CLASSPATH%成"%CLASSPATH%"

保存再次执行如上命令。执行成功后，提示boot success 代表成功。

## 示例

本示例实现三种消息的发布以及订阅接收。

### 创建 RocketMQ 消息生产者

创建 ali-rocketmq-producer 工程，端口为：28081

- pom.xml添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <parent>
        <artifactId>cloud-alibaba</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <modelVersion>4.0.0</modelVersion>
    <artifactId>ali-rocketmq-producer</artifactId>
    <packaging>jar</packaging>

    <dependencies>

        <!--rocketmq依赖-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rocketmq</artifactId>
        </dependency>

        <!--web依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
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

- 配置 Output 的 Binding 信息并配合 `@EnableBinding` 注解使其生效

application.yml配置

```yaml
server:
  port: 28081

spring:
  application:
    name: ali-rocketmq-producer
  cloud:
    stream:
      rocketmq:
        binder:
          # RocketMQ 服务器地址
          name-server: 127.0.0.1:9876
      bindings:
        output1: {destination: test-topic1, content-type: application/json}
        output2: {destination: test-topic2, content-type: application/json}

management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: always
```

ArProduceApplication.java
```java
@SpringBootApplication
@EnableBinding({MySource.class})
public class ArProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArProduceApplication.class, args);
    }
}
```

- 消息生产者服务

MySource.java
```java
package com.easy.arProduce;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    @Output("output1")
    MessageChannel output1();

    @Output("output2")
    MessageChannel output2();
}
```

SenderService.java
```java
package com.easy.arProduce;

import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class SenderService {

    @Autowired
    private MySource source;

    /**
     * 发送字符串
     *
     * @param msg
     */
    public void send(String msg) {
        Message message = MessageBuilder.withPayload(msg)
                .build();
        source.output1().send(message);
    }

    /**
     * 发送带tag的字符串
     *
     * @param msg
     * @param tag
     */
    public void sendWithTags(String msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .build();
        source.output1().send(message);
    }

    /**
     * 发送对象
     *
     * @param msg
     * @param tag
     * @param <T>
     */
    public <T> void sendObject(T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        source.output2().send(message);
    }
}
```

编写 TestController.java 控制器方便测试
```java
package com.easy.arProduce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    SenderService senderService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(String msg) {
        senderService.send(msg);
        return "字符串消息发送成功!";
    }

    @RequestMapping(value = "/sendWithTags", method = RequestMethod.GET)
    public String sendWithTags(String msg) {
        senderService.sendWithTags(msg, "tagStr");
        return "带tag字符串消息发送成功!";
    }

    @RequestMapping(value = "/sendObject", method = RequestMethod.GET)
    public String sendObject(int index) {
        senderService.sendObject(new Foo(index, "foo"), "tagObj");
        return "Object对象消息发送成功!";
    }
}
```

### 创建 RocketMQ 消息消费者

创建 ali-rocketmq-consumer 工程，端口为：28082

- pom.xml添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <parent>
        <artifactId>cloud-alibaba</artifactId>
        <groupId>com.easy</groupId>
        <version>1.0.0</version>
    </parent>

    <modelVersion>4.0.0</modelVersion>

    <artifactId>ali-rocketmq-consumer</artifactId>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rocketmq</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
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

-配置 Input 的 Binding 信息并配合 `@EnableBinding` 注解使其生效

application.yml配置
```yaml
server:
  port: 28082

spring:
  application:
    name: ali-rocketmq-consumer
  cloud:
    stream:
      rocketmq:
        binder:
          name-server: 127.0.0.1:9876 #rocketmq 服务地址
        bindings:
          input1: {consumer.orderly: true}  #是否排序
          input2: {consumer.tags: tagStr}   #订阅 带tag值为tagStr的字符串
          input3: {consumer.tags: tagObj}   #订阅 带tag值为tabObj的字符串
      bindings:
        input1: {destination: test-topic1, content-type: text/plain, group: test-group1, consumer.maxAttempts: 1}
        input2: {destination: test-topic1, content-type: application/plain, group: test-group2, consumer.maxAttempts: 1}
        input3: {destination: test-topic2, content-type: application/plain, group: test-group3, consumer.maxAttempts: 1}

management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: always
```

ArConsumerApplication.java
```java
package com.easy.arConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({MySource.class})
public class ArConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArConsumerApplication.class, args);
    }
}
```

- 消息消费者服务

MySource.java
```java
package com.easy.arConsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySource {
    @Input("input1")
    SubscribableChannel input1();

    @Input("input2")
    SubscribableChannel input2();

    @Input("input3")
    SubscribableChannel input3();
}
```

ReceiveService.java
```java
package com.easy.arConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiveService {

    @StreamListener("input1")
    public void receiveInput1(String receiveMsg) {
        log.info("input1 接收到了消息：" + receiveMsg);
    }

    @StreamListener("input2")
    public void receiveInput2(String receiveMsg) {
        log.info("input2 接收到了消息：" + receiveMsg);
    }

    @StreamListener("input3")
    public void receiveInput3(@Payload Foo foo) {
        log.info("input3 接收到了消息：" + foo);
    }
}
```

## 使用示例

### 示例关联项目

本示例我们创建了两个项目实现
        
- ali-rocketmq-producer：RocketMQ 消息服务生产者，服务名：ali-rocketmq-producer，端口：28081

- ali-rocketmq-consumer：RocketMQ 消息服务消费者，服务名：ali-rocketmq-producer，端口：28082

### 运行示例测试

首先要启动ali-rocketmq-producer服务及ali-rocketmq-consumer服务

- 访问消息服务生产者地址： http://localhost:28081/test/send?msg=yuntian

查看服务消费者控制台，输出
```cfml
2019-12-04 15:37:47.859  INFO 6356 --- [MessageThread_1] com.easy.arConsumer.ReceiveService       : input1 接收到了消息：yuntian
2019-12-04 15:37:47.859  INFO 6356 --- [MessageThread_1] s.b.r.c.RocketMQListenerBindingContainer : consume C0A8096E200818B4AAC212CDA70E0014 cost: 1 ms
```
表示字符串消费成功被input1消费了

- 访问消息服务生产者地址： http://localhost:28081/test/sendWithTags?msg=tagyuntian

查看服务消费者控制台，输出
```cfml
2019-12-04 15:38:09.586  INFO 6356 --- [MessageThread_1] com.easy.arConsumer.ReceiveService       : input2 接收到了消息：tagyuntian
2019-12-04 15:38:09.592  INFO 6356 --- [MessageThread_1] com.easy.arConsumer.ReceiveService       : input1 接收到了消息：tagyuntian
2019-12-04 15:38:09.592  INFO 6356 --- [MessageThread_1] s.b.r.c.RocketMQListenerBindingContainer : consume C0A8096E200818B4AAC212CDFCD30015 cost: 6 ms
```
表示带tag的字符串成功被input2和input1消费了，因为input1也订阅了test-topic1，并且没有我们没有加tag过滤，默认表示接收所有消息，所以也能成功接收tagyuntian字符串

- 访问消息服务生产者地址： http://localhost:28081/test/sendObject?index=1

查看服务消费者控制台，输出
```cfml
2019-12-04 15:41:15.285  INFO 6356 --- [MessageThread_1] com.easy.arConsumer.ReceiveService       : input3 接收到了消息：Foo{id=1, bar='foo'}
```
表示input3成功接收到了tag带tagObj的对象消息了，而input1却没有输出消息，这是因为sendObject发布的消息走的是test-topic2消息管道，所以不会发布给input1及input2订阅者

### 资料

- [Spring Cloud Alibaba 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba)
- [原文地址](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba/README5.md)
- [RocketMQ 项目](https://github.com/apache/rocketmq)