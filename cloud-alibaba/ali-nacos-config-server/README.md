# 配置服务使用示例

## 配置服务测试

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

## 其它介绍

HTTP服务具有以下格式的资源

    /{application}/{profile}[/{label}]
    /{application}-{profile}.yml
    /{label}/{application}-{profile}.yml
    /{application}-{profile}.properties
    /{label}/{application}-{profile}.properties
    
- {application}映射到客户端的“spring.application.name”;
- {profile}映射到客户端上的“spring.profiles.active”（逗号分隔列表）;
- {label}这是一个服务器端功能，标记“版本”的一组配置文件。

## 资料

- [官方文档](https://cloud.spring.io/spring-cloud-config/reference/html/)
- [中文文档](https://www.springcloud.cc/spring-cloud-config.html)
