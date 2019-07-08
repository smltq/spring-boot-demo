package demo.pac4j.config;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.matching.PathMatcher;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Slf4j
public class ShiroConfiguration extends AbstractShiroWebFilterConfiguration {
    @Value("#{ @environment['cas.prefixUrl'] ?: null }")
    private String prefixUrl;

    @Value("#{ @environment['cas.loginUrl'] ?: null }")
    private String casLoginUrl;

    @Value("#{ @environment['cas.callbackUrl'] ?: null }")
    private String callbackUrl;

    //jwt秘钥
    @Value("${jwt.salt}")
    private String salt;


    /**
     * JWT Token 生成器，对CommonProfile生成然后每次携带token访问
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    protected JwtGenerator jwtGenerator() {
        return new JwtGenerator(new SecretSignatureConfiguration(salt), new SecretEncryptionConfiguration(salt));
    }

    @Bean
    protected JwtAuthenticator jwtAuthenticator() {
        JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
        jwtAuthenticator.addSignatureConfiguration(new SecretSignatureConfiguration(salt));
        jwtAuthenticator.addEncryptionConfiguration(new SecretEncryptionConfiguration(salt));
        return jwtAuthenticator;
    }

    /**
     * cas的基本设置，包括或url等等，rest调用协议等
     *
     * @return
     */
    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration casConfiguration = new CasConfiguration(casLoginUrl);
        casConfiguration.setProtocol(CasProtocol.CAS20);
        casConfiguration.setPrefixUrl(prefixUrl);
        return casConfiguration;
    }

    /**
     * 不拦截的路径
     *
     * @return
     */
    @Bean
    public PathMatcher pathMatcher() {
        PathMatcher pathMatcher = new PathMatcher();
        pathMatcher.excludePath("/html/**");
        return pathMatcher;
    }

    /**
     * pac4jRealm
     *
     * @return
     */
    @Bean(name = "pac4jRealm")
    public Realm pac4jRealm() {
        return new ShiroPac4jRealm();
    }

    /**
     * 通过rest接口可以获取tgt，获取service ticket，甚至可以获取CasProfile
     *
     * @return
     */
    @Bean
    protected CasRestFormClient casRestFormClient(CasConfiguration casConfiguration) {
        CasRestFormClient casRestFormClient = new CasRestFormClient();
        casRestFormClient.setConfiguration(casConfiguration);
        casRestFormClient.setName("rest");
        return casRestFormClient;
    }

    /**
     * casClient
     *
     * @return
     */
    @Bean
    public CasClient casClient(CasConfiguration casConfiguration) {
        CasClient casClient = new CasClient();
        casClient.setConfiguration(casConfiguration);
        casClient.setCallbackUrl(callbackUrl);
        casClient.setName("cas");
        return casClient;
    }

    /**
     * token校验相关
     *
     * @return
     */
    @Bean
    protected Clients clients(CasClient casClient, CasRestFormClient casRestFormClient) {
        //可以设置默认client
        Clients clients = new Clients();
        //token校验器，可以用HeaderClient更安全
        ParameterClient parameterClient = new ParameterClient("token", jwtAuthenticator());
        parameterClient.setSupportGetRequest(true);
        parameterClient.setName("jwt");
        //支持的client全部设置进去
        clients.setClients(casClient, casRestFormClient, parameterClient);
        return clients;
    }

    @Bean
    protected Config casConfig(Clients clients) {
        Config config = new Config();
        config.setClients(clients);
        return config;
    }

    /**
     * 由于cas代理了用户，所以必须通过cas进行创建对象
     *
     * @return
     */
    @Bean(name = "subjectFactory")
    protected SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    /**
     * 单点登出的listener
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }

    /**
     * 单点登出filter
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(prefixUrl);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        bean.setFilter(singleSignOutFilter);
        bean.addUrlPatterns("/*");
        bean.setEnabled(true);
        return bean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(Realm pac4jRealm, SubjectFactory subjectFactory) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(pac4jRealm);
        defaultWebSecurityManager.setSubjectFactory(subjectFactory);
        return defaultWebSecurityManager;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean(name = "shiroFilter")
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, Config config) {
        ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);

        //过滤器设置
        Map<String, Filter> filters = new HashMap<String, Filter>();
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setClients("cas,rest,jwt");
        securityFilter.setConfig(config);
        filters.put("casSecurityFilter", securityFilter);

        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        filters.put("callbackFilter", callbackFilter);

        filterFactoryBean.setFilters(filters);


        return filterFactoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/callback", "callbackFilter");
        definition.addPathDefinition("/you", "anon");
        definition.addPathDefinition("/**", "casSecurityFilter");

        return definition;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启 shiro aop注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
}