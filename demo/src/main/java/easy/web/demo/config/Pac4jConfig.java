package easy.web.demo.config;

import io.buji.pac4j.context.ShiroSessionStore;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pac4jConfig {
    /**
     * 地址为：cas地址
     */
    @Value("${cas.server.url}")
    private String casServerUrl;

    /**
     * 地址为：验证返回后的项目地址：http://localhost:8081
     */
    @Value("${cas.project.url}")
    private String projectUrl;

    /**
     * 相当于一个标志，可以随意
     */
    @Value("${cas.client-name}")
    private String clientName;


    /**
     * pac4j配置
     *
     * @param casClient
     * @param shiroSessionStore
     * @return
     */
    @Bean("authcConfig")
    public Config config(CasClient casClient, ShiroSessionStore shiroSessionStore) {
        Config config = new Config(casClient);
        config.setSessionStore(shiroSessionStore);
        return config;
    }

    /**
     * 自定义存储
     *
     * @return
     */
    @Bean
    public ShiroSessionStore shiroSessionStore() {
        return new ShiroSessionStore();
    }

    /**
     * cas 客户端配置
     *
     * @param casConfig
     * @return
     */
    @Bean
    public CasClient casClient(CasConfiguration casConfig) {
        CasClient casClient = new CasClient(casConfig);
        //客户端回调地址
        casClient.setCallbackUrl(projectUrl + "/callback?client_name=" + clientName);
        casClient.setName(clientName);
        return casClient;
    }

    /**
     * 请求cas服务端配置
     */
    @Bean
    public CasConfiguration casConfig() {
        final CasConfiguration configuration = new CasConfiguration();
        //CAS server登录地址
        configuration.setLoginUrl(casServerUrl + "/login");
        //CAS 版本，默认为 CAS30，我们使用的是 CAS20
        configuration.setProtocol(CasProtocol.CAS20);
        configuration.setAcceptAnyProxy(true);
        configuration.setPrefixUrl(casServerUrl + "/");
        return configuration;
    }
}
