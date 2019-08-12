# Spring Boot SecurityOauth2

## Oauth2认证模式之授权码模式（authorization code）

## 使用说明

找到AuthResServerApplication.java运行server服务，默认端口：8080
找到ClientApplication.java运行client客户端，端口：8081

### 1.尝试直接访问用户信息

http://localhost:8080/account/info/testAccount1/

返回未授权错误

```xml
<oauth>
<error_description>
Full authentication is required to access this resource
</error_description>
<error>unauthorized</error>
</oauth>
```

### 2.尝试获取授权码

http://localhost:8080/oauth/authorize?client_id=client1&response_type=code&redirect_uri=http://localhost:8081/client/account/redirect

结果被主过滤器拦截，302 跳转到登录页，因为 /oauth/authorize 端点是受保护的端点，必须登录的用户才能申请 code。

### 3.输入用户名和密码

输入用户名和密码
username=admin

passpord=123456

如上用户名密码是交给 SpringSecurity 的主过滤器用来认证的

### 4.登录成功后，真正进行授权码的申请

oauth/authorize 认证成功，会根据 redirect_uri 执行 302 重定向，并且带上生成的 code，注意重定向到的是 8001 端口，这个时候已经是另外一个应用了。

localhost:8081/client/account/redirect?code=xxxx
代码中封装了一个 http 请求，使得 client1 使用 restTemplate 向 server 发送 token 的申请，当然是使用 code 来申请的，并最终成功获取到 access_token

```json
{
access_token: "59a25558-f714-4ca8-aa87-c36f93c120bf",
token_type: "bearer",
refresh_token: "92436849-7ef7-4923-8270-5a2c9b464556",
expires_in: 43199,
scope: "get_user_info get_childlist"
}
```

### 5.携带 access_token 访问account信息

http://localhost:8080/account/info/testAccount1?access_token=59a25558-f714-4ca8-aa87-c36f93c120bf

### 6.正常返回信息

```json
{
name: "testAccount1",
nickName: "测试用户1",
remark: "备注1",
childAccount: [
{
name: "testChild1_0",
nickName: "测试子用户1_0",
remark: "0",
childAccount: null
},
{
name: "testChild1_1",
nickName: "测试子用户1_1",
remark: "1",
childAccount: null
},
{
name: "testChild1_2",
nickName: "测试子用户1_2",
remark: "2",
childAccount: null
},
{
name: "testChild1_3",
nickName: "测试子用户1_3",
remark: "3",
childAccount: null
},
{
name: "testChild1_4",
nickName: "测试子用户1_4",
remark: "4",
childAccount: null
},
{
name: "testChild1_5",
nickName: "测试子用户1_5",
remark: "5",
childAccount: null
},
{
name: "testChild1_6",
nickName: "测试子用户1_6",
remark: "6",
childAccount: null
},
{
name: "testChild1_7",
nickName: "测试子用户1_7",
remark: "7",
childAccount: null
},
{
name: "testChild1_8",
nickName: "测试子用户1_8",
remark: "8",
childAccount: null
},
{
name: "testChild1_9",
nickName: "测试子用户1_9",
remark: "9",
childAccount: null
}
]
}
```

## 资料

[官方文档](https://projects.spring.io/spring-security-oauth/docs/oauth2.html)
[官方文档](https://spring.io/guides/tutorials/spring-boot-oauth2/)
[OAuth2 Boot](https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/)
[oauth2-demo](https://github.com/lexburner/oauth2-demo)
https://dzone.com/articles/spring-boot-oauth2-getting-the-authorization-code