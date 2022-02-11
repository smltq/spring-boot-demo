package com.easy.securityOauth2AuthCodeServer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String RESOURCE_ID = "account";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

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
                .autoApprove("get_user_info")
                .and()

                .withClient("client2")
                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("get_user_info", "get_childlist")
                .secret("secret")
                .redirectUris("http://localhost:8082/client/account/redirect");
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
        // redis配置
        // return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)

                // Spring Security oAuth 2.0，刷新token报错Internal Server Error
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.realm(RESOURCE_ID).allowFormAuthenticationForClients();
    }
}