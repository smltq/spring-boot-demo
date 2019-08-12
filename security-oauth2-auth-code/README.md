# Oauth2认证模式之授权码模式（authorization code）

本示例实现了Oauth2之授权码模式，授权码模式（authorization code）是功能最完整、流程最严密的授权模式。它的特点就是通过客户端的后台服务器，与"服务提供商"的认证服务器进行互动。

阅读本示例之前，你需要先有以下两点基础：

- 需要对spring security有一定的配置使用经验，用户认证这一块，spring security oauth2建立在spring security的基础之上
- oauth2开放授权标准基础，可以稳步到[OAuth2 详解](https://www.cnblogs.com/linianhui/p/oauth2-authorization.html#auto_id_1)，浏览下授权码模式，理解下基本概念

## 概述

实现 oauth2，可以简易的分为三个步骤

- 配置资源服务器
- 配置认证服务器
- 配置spring security

## 代码实现

### 1.pom.xml添加maven依赖

```xml
    <dependencies>
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
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
    </dependencies>
```

### 2.配置资源服务器

```java
public class ResourceServerConfig {

    private static final String RESOURCE_ID = "account";

    @Configuration
    @EnableResourceServer()
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .requestMatchers()
                    // 保险起见，防止被主过滤器链路拦截
                    .antMatchers("/account/**").and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/account/info/**").access("#oauth2.hasScope('get_user_info')")
                    .antMatchers("/account/child/**").access("#oauth2.hasScope('get_childlist')");
        }
    }
}
```

### 3.配置认证服务器

```java
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("get_user_info", "get_childlist")
                .secret("secret")
                .redirectUris("http://localhost:8081/client/account/redirect")
                .autoApprove(true)
                .autoApprove("get_user_info");
    }
```

### 4.配置spring security

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 创建两个内存用户
        manager.createUser(User.withUsername("admin").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("lin").password("123456").authorities("USER").build());
        return manager;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 密码生成器(默认为bcrypt模式)
     *
     * @return
     */
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                requestMatchers()
                // 必须登录过的用户才可以进行 oauth2 的授权码申请
                .antMatchers("/", "/home", "/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .accessDeniedPage("/login?authorization_error=true")
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable();
    }
}
```

## 使用介绍

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

输入用户名和密码 admin  123456
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

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/security-oauth2-auth-code)
- [官方文档](https://projects.spring.io/spring-security-oauth/docs/oauth2.html)
- [OAuth2 Boot](https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/)
- [授权码模式参考](https://dzone.com/articles/spring-boot-oauth2-getting-the-authorization-code)