# Spring Boot Security Oauth2之客户端模式及密码模式实现

## 示例主要内容

- 1.多认证模式（密码模式、客户端模式）
- 2.redis缓存功能支持
- 3.资源保护
- 4.密码模式用户及权限存到数据库
- 4.使用说明

[示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-credentials)

## 介绍

### oauth2 client credentials 客户端模式获取access_token流程

客户端模式（Client Credentials Grant）指客户端以自己的名义，而不是以用户的名义，向"服务提供商"进行认证。严格地说，客户端模式并不属于OAuth框架所要解决的问题。在这种模式中，用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务，其实不存在授权问题。

- （A）客户端向认证服务器进行身份认证，并要求一个访问令牌。客户端发出的HTTP请求，包含以下参数：
        granttype：表示授权类型，此处的值固定为"clientcredentials"，必选项。
        scope：表示权限范围，可选项。

- （B）认证服务器确认无误后，向客户端提供访问令牌。

### oauth2 password 密码模式获取access_token流程

密码模式（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。
在这种模式中，用户必须把自己的密码给客户端，但是客户端不得储存密码。这通常用在用户对客户端高度信任的情况下，比如客户端是操作系统的一部分，或者由一个著名公司出品。而认证服务器只有在其他授权模式无法执行的情况下，才能考虑使用这种模式。

- （A）用户向客户端提供用户名和密码。

- （B）客户端将用户名和密码发给认证服务器，向后者请求令牌。 客户端发出的HTTP请求，包含以下参数：
        grant_type：表示授权类型，此处的值固定为"password"，必选项。
        username：表示用户名，必选项。
        password：表示用户的密码，必选项。
        scope：表示权限范围，可选项。

- （C）认证服务器确认无误后，向客户端提供访问令牌。

## Oauth2提供的默认端点（endpoints）

- /oauth/authorize：授权端点
- /oauth/token：令牌端点
- /oauth/confirm_access：用户确认授权提交端点
- /oauth/error：授权服务错误信息端点
- /oauth/check_token：用于资源服务访问的令牌解析端点
- /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话

## 示例使用介绍

### 1.端模式获取access_token

http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456

返回结果

```json
{
    "access_token": "67a2c8f6-bd08-4409-a0d6-6ba61a4be950",
    "token_type": "bearer",
    "expires_in": 41203,
    "scope": "select"
}
```

### 2.密码模式获取access_token

http://localhost:8080/oauth/token?username=user&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456

返回结果

```json
{
    "access_token": "b3d2c131-1225-45b4-9ff5-51ec17511cee",
    "token_type": "bearer",
    "refresh_token": "8495d597-0560-4598-95ef-143c0855363c",
    "expires_in": 42417,
    "scope": "select"
}
```

### 3.刷新access_token

http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=8495d597-0560-4598-95ef-143c0855363c&client_id=client_2&client_secret=123456

返回结果

```json
{
    "access_token": "63de6c71-672f-418c-80eb-0c9abc95b67c",
    "token_type": "bearer",
    "refresh_token": "8495d597-0560-4598-95ef-143c0855363c",
    "expires_in": 43199,
    "scope": "select"
}
```

### 4.访问受保护的资源

http://localhost:8080/order/1?access_token=b3d2c131-1225-45b4-9ff5-51ec17511cee

正确返回数据

## spring security oauth2代码过程

security oauth2 整合的3个核心配置类

- 1.资源服务配置 ResourceServerConfiguration
- 2.授权认证服务配置 AuthorizationServerConfiguration
- 3.security 配置 SecurityConfiguration

### 1.pom.xml添加maven依赖

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.security.oauth</groupId>
            <artifactId>spring-security-oauth2</artifactId>
            <version>2.3.6.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.17</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.1.2</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>4.6.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

主要使用了：security、oauth2、redis、mysql、mybatis-plus等组件

### 2.认证授权配置AuthorizationServerConfigurerAdapter.java

```java
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String RESOURCE_IDS = "order";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
        //配置两个客户端,一个用于password认证一个用于client认证
        clients.inMemory()

                //client模式
                .withClient("client_1")
                .resourceIds(RESOURCE_IDS)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(finalSecret)

                .and()

                //密码模式
                .withClient("client_2")
                .resourceIds(RESOURCE_IDS)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(finalSecret);
    }

    /**
     * 认证服务端点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //用户管理
                .userDetailsService(userDetailsService)
                //token存到redis
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
                //启用oauth2管理
                .authenticationManager(authenticationManager)
                //接收GET和POST
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients();
    }
}
```

### 3.资源配置ResourceServerConfig.java

```java
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_IDS = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_IDS).stateless(true);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/order/**").authenticated();      //配置order访问控制，必须认证过后才可以访问

    }
}
```

### 4.密码模式的用户及权限存到了数据库，UserDetailsServiceImpl.java

```java

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.queryUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        //用户权限列表
        Collection<? extends GrantedAuthority> authorities = userService.queryUserAuthorities(user.getId());

        return new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}

```

### 5.WebSecurityConfig配置

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 注入AuthenticationManager接口，启用OAuth2密码模式
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    /**
     * 通过HttpSecurity实现Security的自定义过滤配置
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll();
    }
}
```

### 6.application.yml配置

```yaml
server:
  port: 8080

spring:
  thymeleaf:
    encoding: UTF-8
    cache: false

  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/easy_web?useSSL=false&serverTimezone=UTC
    username: root
    password: 123456

  redis:
    host: 127.0.0.1
    port: 6379
    password:

logging.level.org.springframework.security: DEBUG
```

### 7.inital.sql数据库初始化脚本

```sql
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `permission`;

CREATE TABLE `user` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`username` varchar(255) NOT NULL,
`password` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `user_role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`user_id` bigint(11) NOT NULL,
`role_id` bigint(11) NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE `role_permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`role_id` bigint(11) NOT NULL,
`permission_id` bigint(11) NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE `permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`url` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL,
`description` varchar(255) NULL,
`pid` bigint(11) NOT NULL,
PRIMARY KEY (`id`) 
);

INSERT INTO user (id, username, password) VALUES (1,'user','{bcrypt}$2a$10$Tme77eHtXzcB8ghQUepYguJr7P7ESg0Y7XHMnk60s.kf2A.BWBD9m');
INSERT INTO user (id, username , password) VALUES (2,'admin','{bcrypt}$2a$10$Tme77eHtXzcB8ghQUepYguJr7P7ESg0Y7XHMnk60s.kf2A.BWBD9m');
INSERT INTO role (id, name) VALUES (1,'USER');
INSERT INTO role (id, name) VALUES (2,'ADMIN');
INSERT INTO permission (id, url, name, pid) VALUES (1,'/user/common','common',0);
INSERT INTO permission (id, url, name, pid) VALUES (2,'/user/admin','admin',0);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 2);
```

经过以上七个步骤，我们快速实现了Oauth2的密码模式和客户模式功能。

## 资料

[示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-credentials)
[阮一峰 理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)