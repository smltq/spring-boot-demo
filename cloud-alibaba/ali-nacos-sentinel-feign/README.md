## 访问地址： http://localhost:9102/hello-feign/yuntian

返回
```json
我是服务提供者，见到你很高兴==>yuntian
```

关闭ali-nacos-provider服务，访问： http://localhost:9102/hello-feign/yuntian

返回
```json
服务调用失败，降级处理。异常信息：com.netflix.client.ClientException: Load balancer does not have available server for client: ali-nacos-provider
```