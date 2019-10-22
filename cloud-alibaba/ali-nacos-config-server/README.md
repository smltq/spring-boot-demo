# 配置客户端使用示例

## 配置客户端测试

### bootstrap.yml的active调成dev，访问：http://localhost:8002/hello ，返回：

```json
{
    hello: "ali-nacos-config-client 项目的 dev config",
    myconfig: "ali-nacos-config-client 项目的 myconfig config"
}
```

### bootstrap.yml的active调成test，访问：http://localhost:8002/hello ，返回：

```json
{
hello: "ali-nacos-config-client 项目的 test config",
myconfig: "ali-nacos-config-client 项目的 myconfig config"
}
```

### bootstrap.yml的active调成pro，访问：http://localhost:8002/hello ，返回：
