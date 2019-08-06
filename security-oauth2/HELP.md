# Spring Boot SecurityOauth2

## Oauth2四种认证模式

- 授权码模式（authorization code）
- 简化模式（implicit）
- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

### 端点（endpoints）,默认的defaultPath

- /oauth/authorize：授权端点
- /oauth/token：令牌端点
- /oauth/confirm_access：用户确认授权提交端点
- /oauth/error：授权服务错误信息端点
- /oauth/check_token：用于资源服务访问的令牌解析端点
- /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话

## 资料

[官方文档](https://spring.io/guides/tutorials/spring-boot-oauth2/)
[OAuth2 Boot](https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/)
[oauth2-demo](https://github.com/lexburner/oauth2-demo)