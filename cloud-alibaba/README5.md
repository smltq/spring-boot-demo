# Spring Cloud Alibaba（五）实现RocketMQ消息生产和消费





- 地址输入： http://localhost:28081/test/send?msg=yuntian

返回
```cfml
input1 receive: yuntian
```

- 地址输入： http://localhost:28081/test/sendWithTags?msg=tagyuntian

返回
```cfml
input1 receive: tagyuntian
```

- 地址输入： http://localhost:28081/test/sendObject?index=1

返回
```cfml
input1 receive: {"id":1,"bar":"foo"}
```

### 资料

- [Spring Cloud Alibaba 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba)
- [原文地址](https://github.com/smltq/spring-boot-demo/blob/master/cloud-alibaba/README5.md)