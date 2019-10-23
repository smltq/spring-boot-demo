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

## 配置文件介绍

### 配置文件映射关系

    /{application}/{profile}[/{label}]
    /{application}-{profile}.yml
    /{label}/{application}-{profile}.yml
    /{application}-{profile}.properties
    /{label}/{application}-{profile}.properties

- {application}通常使用微服务名称，对应Git仓库中文件名的前缀，spring.application.name
- {profile}对应{application}-后面的dev、pro、test等，spring.profiles.active
- {label}对应Git仓库的分支名，默认为master

## 资料

- [官方文档](https://cloud.spring.io/spring-cloud-config/reference/html/)
- [中文文档](https://www.springcloud.cc/spring-cloud-config.html)
