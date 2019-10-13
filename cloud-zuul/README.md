# Spring Cloud Zuul

## 开发环境

- idea 2019.1.2
- jdk1.8.0_201
- Spring Boot 2.1.9.RELEASE
- Spring Cloud Greenwich SR3

## 使用示例

### 分别启动zuul-eureka-server、zuul-server-gateway、zuul-server-provider三个服务

- 访问地址：http://localhost:8888/zuul-server-provider/hello?name=yuntian，返回：token is empty ，请求被拦截返回。
- 访问地址：http://localhost:8888/zuul-server-provider/hello?name=yuntian&token=xx，返回：hello yuntian，this is first messge，说明请求正常响应。

### 启动zuul-server-provider2
- 多次访问http://localhost:8888/zuul-server-provider/hello?name=yuntian&token=xx，此时会交替返回

```cfml
hello yuntian，this is first messge
The service is unavailable
...

```
从返回结果可以看出：zuul-server-provider2项目已经启用了熔断，返回:The service is unavailable.