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

配置文件命名规则

    /{application}/{profile}[/{label}]
    /{application}-{profile}.yml
    /{label}/{application}-{profile}.yml
    /{application}-{profile}.properties
    /{label}/{application}-{profile}.properties

## 资料

[官方文档](https://cloud.spring.io/spring-cloud-config/reference/html/)
