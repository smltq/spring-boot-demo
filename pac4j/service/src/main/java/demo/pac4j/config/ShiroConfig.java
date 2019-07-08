package demo.pac4j.config;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Slf4j
public class ShiroConfig {

    @Value("#{ @environment['cas.prefixUrl'] ?: null }")
    private String prefixUrl;
    @Value("#{ @environment['cas.loginUrl'] ?: null }")
    private String casLoginUrl;
    @Value("#{ @environment['cas.callbackUrl'] ?: null }")
    private String callbackUrl;

    //jwt秘钥
    @Value("${jwt.salt}")
    private String salt;

    @Bean
    public Realm pac4jRealm() {
        return new Pac4jRealm();
    }

    /**
     * cas核心过滤器，把支持的client写上，filter过滤时才会处理，clients必须在casConfig.clients已经注册
     *
     * @return
     */
    @Bean
    public Filter casSecurityFilter() {
        SecurityFilter filter = new SecurityFilter();
        filter.setClients("CasClient,rest,jwt");
        filter.setConfig(casConfig());
        return filter;
    }

    /**
     * JWT Token 生成器，对CommonProfile生成然后每次携带token访问
     *
     * @return
     */
    @Bean
    protected JwtGenerator jwtGenerator() {
        return new JwtGenerator(new SecretSignatureConfiguration(salt), new SecretEncryptionConfiguration(salt));
    }

    /**
     * 通过rest接口可以获取tgt，获取service ticket，甚至可以获取CasProfile
     *
     * @return
     */
    @Bean
    protected CasRestFormClient casRestFormClient() {
        CasRestFormClient casRestFormClient = new CasRestFormClient();
        casRestFormClient.setConfiguration(casConfiguration());
        casRestFormClient.setName("rest");
        return casRestFormClient;
    }

    @Bean
    protected Clients clients() {
        //可以设置默认client
        Clients clients = new Clients();

        //token校验器，可以用HeaderClient更安全
        ParameterClient parameterClient = new ParameterClient("token", jwtAuthenticator());
        parameterClient.setSupportGetRequest(true);
        parameterClient.setName("jwt");
        //支持的client全部设置进去
        clients.setClients(casClient(), casRestFormClient(), parameterClient);
        return clients;
    }

    /**
     * JWT校验器，也就是目前设置的ParameterClient进行的校验器，是rest/或者前后端分离的核心校验器
     *
     * @return
     */
    @Bean
    protected JwtAuthenticator jwtAuthenticator() {
        JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
        jwtAuthenticator.addSignatureConfiguration(new SecretSignatureConfiguration(salt));
        jwtAuthenticator.addEncryptionConfiguration(new SecretEncryptionConfiguration(salt));
        return jwtAuthenticator;
    }

    @Bean
    protected Config casConfig() {
        Config config = new Config();
        config.setClients(clients());
        return config;
    }

    /**
     * cas的基本设置，包括或url等等，rest调用协议等
     *
     * @return
     */
    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration casConfiguration = new CasConfiguration(casLoginUrl);
        casConfiguration.setProtocol(CasProtocol.CAS30);
        casConfiguration.setPrefixUrl(prefixUrl);
        return casConfiguration;
    }

    @Bean
    public CasClient casClient() {
        CasClient casClient = new CasClient();
        casClient.setConfiguration(casConfiguration());
        casClient.setCallbackUrl(callbackUrl);
        return casClient;
    }

    /**
     * 路径过滤设置
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/callback", "callbackFilter");
        definition.addPathDefinition("/logout", "logoutFilter");
        definition.addPathDefinition("/**", "casSecurityFilter");
        return definition;
    }

    /**
     * 由于cas代理了用户，所以必须通过cas进行创建对象
     *
     * @return
     */
    @Bean
    protected SubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    /**
     * 对shiro的过滤策略进行明确
     *
     * @return
     */
    @Bean
    protected Map<String, Filter> filters() {
        //过滤器设置
        Map<String, Filter> filters = new HashMap<>();
        filters.put("casSecurityFilter", casSecurityFilter());
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(casConfig());
        filters.put("callbackFilter", callbackFilter);
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(casConfig());
        filters.put("logoutFilter", logoutFilter);
        return filters;
    }
}