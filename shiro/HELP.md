# Spring Boot Shiro

## 本示例要内容

- 基于RBAC，授权、认证
- 加密、解密
- 统一异常处理
- redis session支持

## 介绍

Apache Shiro 是一个功能强大且易于使用的Java安全框架，可执行身份验证，授权，加密和会话管理。借助Shiro易于理解的API，您可以快速轻松地保护任何应用程序(从最小的移动应用程序到最大的Web和企业应用程序)。

## 开始使用

### 添加依赖

```xml
    <!-- shiro -->
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
        <version>1.4.0</version>
    </dependency>
```

### RBAC

RBAC 是基于角色的访问控制（Role-Based Access Control ）在 RBAC 中，权限与角色相关联，用户通过成为适当角色的成员而得到这些角色的权限。这样管理都是层级相互依赖的，权限赋予给角色，而把角色又赋予用户，这样的权限设计很清楚，管理起来很方便。

#### 创建实体类

- SysPermission.java
- SysRole.java
- UserInfo.java
    
#### 采用 Jpa 技术会自动生成5张基础表格，分别是：

- user_info（用户信息表）
- sys_role（角色表）
- sys_permission（权限表）
- sys_user_role（用户角色表）
- sys_role_permission（角色权限表）

#### 初始化数据

```sql
INSERT INTO `user_info` (`uid`,`username`,`name`,`password`,`salt`,`state`) VALUES ('1', 'admin', '管理员', '7430bfdcc59212b32d78aacd42c7fe33', 'md5!@#', 0);
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (1,0,'用户管理',0,'0/','userInfo:view','menu','userInfo/userList');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (2,0,'用户添加',1,'0/1','userInfo:add','button','userInfo/userAdd');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (3,0,'用户删除',1,'0/1','userInfo:del','button','userInfo/userDel');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (1,0,'管理员','admin');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (2,0,'VIP会员','vip');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (3,1,'test','test');
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (1,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (2,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (3,2);
INSERT INTO `sys_user_role` (`role_id`,`uid`) VALUES (1,1);
```

### Shiro配置

首先要配置的是 ShiroConfig 类，Apache Shiro 核心通过 Filter 来实现。使用 Filter是可以通过 URL 规则来进行过滤和权限校验，所以我们需要定义一系列关于 URL 的规则和访问权限。

```java
@Configuration
@Slf4j
public class ShiroConfig {

    @Autowired
    private IgnoreAuthUrlProperties ignoreAuthUrlProperties;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        log.info("Shiro过滤器开始处理");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置登录页
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();

        //anon:所有url都都可以匿名访问
        Set<String> urlSet = new HashSet<>(ignoreAuthUrlProperties.getIgnoreAuthUrl());
        urlSet.stream().forEach(temp -> filterMap.put(temp, "anon"));

        //用户未登录不进行跳转,返回错误信息
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new MyFormAuthenticationFilter());

        //配置退出 过滤器
        filterMap.put("/logout", "logout");

        //authc:所有url都必须认证通过才可以访问
        filterMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authRealm;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm());
        return securityManager;
    }

    /**
     * 启用shiro注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 异常处理
     *
     * @return
     */
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("UnauthorizedException", "403");
        r.setExceptionMappings(mappings);

        r.setDefaultErrorView("error");
        r.setExceptionAttribute("ex");
        return r;
    }
}
```

### Shiro 内置的两个主要 Filter介绍

- anon:所有 url 都都可以匿名访问
- authc: 需要认证才能进行访问

### 认证和授权

```java

@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("调用授权方法");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        for (SysRole role : userInfo.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 认证(主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确)
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("调用认证方法");
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        if (username == null) {
            throw new AuthenticationException("账号名为空，登录失败！");
        }

        log.info("credentials:" + token.getCredentials());
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            throw new AuthenticationException("不存在的账号，登录失败！");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,                                               //用户
                userInfo.getPassword(),                                 //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),   //加盐后的密码
                getName()                                               //指定当前 Realm 的类名
        );
        return authenticationInfo;
    }
}

```

### 登录

```java
    /**
     * 登录
     *
     * @param username
     * @param password
     * @param map      如果出错，回传给前端的map
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, Map<String, Object> map) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        String msg = "";
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            msg = "账号不存在！";
        } catch (DisabledAccountException e) {
            msg = "账号未启用！";
        } catch (IncorrectCredentialsException e) {
            msg = "密码错误！";
        } catch (Throwable e) {
            msg = "未知错误！";
        }

        //判断登录是否出现错误
        if (msg.length() > 0) {
            map.put("msg", msg);
            return "/login";
        } else {
            return "redirect:index";
        }
    }    
```

### 方法增加权限验证
```java
    /**
     * 用户添加
     *
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    public String userInfoAdd() {
        return "userInfoAdd";
    }
```

这样配置完，执行程序。只有用户拥有userAdd权限才允许访问userAdd接口，否则会提示“未授权”访问

### 资料

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/shiro/HELP.md)
- [shiro参考](http://wiki.jikexueyuan.com/project/shiro/)
- [shiro官网](https://shiro.apache.org/)


