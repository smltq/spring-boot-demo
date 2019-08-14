# Spring Boot SecurityOauth2

## 示例主要内容

1.多认证模式（密码模式、客户端模式）
2.redis缓存功能支持
3.资源保护
4.使用说明

## Oauth2四种认证模式

- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

### 端点（endpoints）,默认的defaultPath

- /oauth/authorize：授权端点
- /oauth/token：令牌端点
- /oauth/confirm_access：用户确认授权提交端点
- /oauth/error：授权服务错误信息端点
- /oauth/check_token：用于资源服务访问的令牌解析端点
- /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话

## 示例使用

### 1.客户端模式获取授权

1.http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456

## 资料

[官方文档](https://projects.spring.io/spring-security-oauth/docs/oauth2.html)
[官方文档](https://spring.io/guides/tutorials/spring-boot-oauth2/)
[OAuth2 Boot](https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/)
[oauth2-demo](https://github.com/lexburner/oauth2-demo)