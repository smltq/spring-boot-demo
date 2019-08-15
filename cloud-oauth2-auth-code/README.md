# cloud-oauth2-auth-code 授权码模式(Finchley版本)

## 示例使用介绍

### 1.运行auth-server、resource-server服务

### 2.获取授权码

http://localhost:8080/oauth/authorize?response_type=code&client_id=client1&redirect_uri=http://baidu.com

授权码为：1BHxWt

### 3.根据授权码获取访问令牌

var settings = {
  "async": true,
  "crossDomain": true,
  "url": "http://localhost:8080/oauth/token?grant_type=authorization_code&code=1BHxWt&client_id=client1&client_secret=123456&redirect_uri=http://baidu.com&scope=test",
  "method": "POST",
  "headers": {
    "User-Agent": "PostmanRuntime/7.15.2",
    "Accept": "*/*",
    "Cache-Control": "no-cache",
    "Postman-Token": "13e40b5d-2e86-43c4-965e-da210e011906,d4c4f88b-ac15-45d9-862a-65dbcf839c6a",
    "Host": "localhost:8080",
    "Cookie": "merchant.session.id=6b82e720-8198-4f19-9b56-5faf2d00a389; JSESSIONID=9D4447918C453A0CB47E1393696D6342",
    "Accept-Encoding": "gzip, deflate",
    "Content-Length": "",
    "Connection": "keep-alive",
    "cache-control": "no-cache"
  }
}

$.ajax(settings).done(function (response) {
  console.log(response);
});

### 4.从资源服务获取资源

携带access_token参数请求资源

http://localhost:8088/user?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZXN0IjoiaGVsbG8iLCJzY29wZSI6WyJ0ZXN0Il0sImRldGFpbHMiOnsicmVtb3RlQWRkcmVzcyI6IjA6MDowOjA6MDowOjA6MSIsInNlc3Npb25JZCI6IkJFRTNBODgzRkE3OEQwRjUyQjk4QTQ0MkIwRDRFM0M5In0sImV4cCI6MTU2NTUxNjUzOSwidXNlck5hbWUiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJqdGkiOiI5YjdjZDdmOC01NjNjLTRkYTEtOWZlYi1lNTZiY2E4NmYxYTgiLCJjbGllbnRfaWQiOiJjbGllbnQxIn0.Iw8zaTzNa-LslTWxEqKCl-Pdf5hlqHz7A152mySHQxabT781CErG91QbmU-h-9ejeqHPfEPPaOZKSQKL7v1bHaf4Old8geqzQozZos-zkK1-jHQQCutBImckzjNWLh-kP5uRBBZhogx6KcC3S2iJCIW6_T4t0OaL-pV_ueZ8O-KVkVZSQOeie-hSByF7Dzx8gu7DIM4-2Pg250rsA5fbviwDCskq9izo8Y8KWgeFBSlrqkKKxsxNL8_KdUS82HxhBGbicxXipCrN-7a5wh_bT_Eoy6tF7YEPT6Ga_voFkwkVUERhwhQHo6V6YNdrjoa2eGKaZnWMzvPMpy4Br7r2uA

### 5.刷新token


## 资料

[示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/cloud-oauth2-auth-code/README.md)
[参考](https://blog.csdn.net/AaronSimon/article/details/83546827)